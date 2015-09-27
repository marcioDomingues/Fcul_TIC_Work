package client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Signature;
import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.SecretKey;

import network.SendReceive;

public class ClientOperations {

	public int commit( ObjectOutputStream oos, ObjectInputStream ois, CAuxFileData aFile ) throws Exception{
		Boolean tConfirmed;
		int status;
		File f = new File( aFile.getFullPath() );
		
		// envia dados iniciais ao servidor
		oos.writeObject(f.getPath());
		oos.writeObject(f.exists() ? f.lastModified() : -1);
		
		// esperar confirmacao
		tConfirmed = (Boolean)ois.readObject();
		if( tConfirmed ){
			File encryptedFile = encryptFile( oos, ois, f, aFile, f.lastModified() );
			SendReceive.fileSend( encryptedFile, oos );
			encryptedFile.delete();
			status = 1;
		}else{
			status = (int)ois.readObject();
		}
		
		return status;
	}
	
	public int get( ObjectOutputStream oos, ObjectInputStream ois, CAuxFileData aFile ) throws Throwable{
		int status;
		Boolean tConfirmed;
		File f = new File( aFile.getFullPath() );
		
		// enviar dados iniciais para o servidor
		oos.writeObject(aFile.getFullPath());
		oos.writeObject(f.exists() ? f.lastModified() : 0 );
		
		tConfirmed = (Boolean) ois.readObject();
		
		//assegurar que o cliente tem a pasta para o qual irao ficheiro caso seja de outro utilizador
		if( aFile.isShared() && tConfirmed ){
			File dir = new File( aFile.getPath() );
			dir.mkdir();
		}
		
		// esperar confirmacao
		if( tConfirmed ){
			SendReceive.fileReceive(aFile.getPath(), ois);
			status = verifySig( ois, aFile );
			
			if( status == 10 ){
				f = new File( aFile.getFullPath()+".cif" );
				f.delete();
				return status;
			}
			
			decryptFile( ois, aFile );
			status = 8;
		}else{
			status = (int)ois.readObject();
		}
		
		return status;
	}
	
	private int verifySig(ObjectInputStream ois, CAuxFileData aFile ) throws Exception{
		String sigName = (String) ois.readObject();
		
		String owner;
		String[] temp = sigName.split("_");
		owner = temp[0];
		
		// receber a assinatura
		SendReceive.fileReceive("client", ois);
		
		FileInputStream sigfis = new FileInputStream("client/"+sigName);
		byte[] sigToVerify = new byte[sigfis.available()]; 
		sigfis.read(sigToVerify);
		sigfis.close();
		
		// verificar a assinatura
		PublicKey pk = Cypher.getPkFromCertificate(aFile.getUser(), owner);
		
		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initVerify(pk);
		
		FileInputStream datafis = new FileInputStream(aFile.getFullPath()+".cif");
		BufferedInputStream bufin = new BufferedInputStream(datafis);

		byte[] buffer = new byte[1024];
		int len;
		while (bufin.available() != 0) {
		    len = bufin.read(buffer);
		    sig.update(buffer, 0, len);
		};

		bufin.close();
		datafis.close();
		
		boolean verifies = sig.verify(sigToVerify);
		File sigFile = new File("client/"+sigName);
		if( !verifies ){
			sigFile.delete();
			return 10;
		}
		sigFile.delete();
		return 0;
	}
	
	public void printStatus( int status, String file ){
		switch( status ){
		case 1:
			System.out.println("File "+file+" successfully sent");
			break;
		case 2:
			System.out.println("File "+file+" does not exist");
			break;
		case 3:
			System.out.println("Server has a more recent version of "+file);
			break;
		case 4:
			System.out.println("Server has the same version of "+file);
			break;
		case 5:
			System.out.println("You don't have permissions for "+file);
			break;
		case 6:
			System.out.println("Client has the same version of "+file);
			break;
		case 7:
			System.out.println("Client has a more recent version of "+file);
			break;
		case 8:
			System.out.println("File "+file+" received");
			break;
		case 9:
			System.out.println("File "+file+" is an invalid file");
			break;
		case 10:
			System.out.println("Invalid signature for file "+file);
			break;
		default:
			System.out.println("Something went very wrong!");
			break;
		}
	}
	
	private File encryptFile( ObjectOutputStream oos, ObjectInputStream ois, File file, CAuxFileData aFile, long timestamp ){
		File encryptedFile = null;
		Boolean keyExists;
		try {
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos;
			KeyPair kp;
			SecretKey key = null;
			
			//associar ao ficheiro correcto, caso seja partilhado ou nao
			fos = new FileOutputStream(aFile.getFullPath()+".cif");
			encryptedFile = new File(aFile.getFullPath()+".cif");
			
			//verificar se a chave existe para o ficheiro
			oos.writeObject(aFile.getKeyName()); // key name = owner.file.txt.key
			keyExists = (Boolean) ois.readObject();
			kp = Cypher.getKeyPairFromKeystore(aFile.getUser());
			
			File keyFile;
			if( keyExists ){
				//chave pode ser do proprio ou de outro user
				SendReceive.fileReceive("client", ois);
				key = Cypher.getKeyFromFile( "client/"+aFile.getKeyName(), kp.getPrivate() );
				keyFile = new File("client/"+aFile.getKeyName());
				keyFile.delete();
			}else{
				//so se pode gerar a chave para ficheiros proprios
				key = Cypher.generateKey();
				Cypher.saveKeyToFile( key, "client/"+aFile.getKeyName(), kp.getPublic() );
				keyFile = new File("client/"+aFile.getKeyName());
				SendReceive.fileSend(keyFile, oos);
				keyFile.delete();
			}
			
			//cifrar o ficheiro
			Cypher.encrypt(key, fis, fos);
			fis.close();
			fos.close();
			encryptedFile.setLastModified(timestamp);
			
			// gerar assinatura
			Signature rsa = Signature.getInstance("SHA1withRSA");
			rsa.initSign(kp.getPrivate());
			
			fis = new FileInputStream(aFile.getFullPath()+".cif");
			BufferedInputStream bufin = new BufferedInputStream(fis);
			
			byte[] buffer = new byte[1024];
			int len;
			while ((len = bufin.read(buffer)) >= 0) {
			    rsa.update(buffer, 0, len);
			}
			bufin.close();
			fis.close();
			
			byte[] realSig = rsa.sign();
			
			fos = new FileOutputStream("client/"+aFile.getSigName());
			fos.write(realSig);
			fos.close();
			File sig = new File("client/"+aFile.getSigName());
			
			// enviar assinatura
			SendReceive.fileSend(sig, oos);
			
			// apagar assinatura
			sig.delete();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return encryptedFile;
	}
	
	private void decryptFile( ObjectInputStream ois, CAuxFileData aFile ) throws Throwable{
		//ir buscar a chave, tem que existir no servidor
		SendReceive.fileReceive("client/", ois);
		
		KeyPair kp = Cypher.getKeyPairFromKeystore( aFile.getUser() );
		SecretKey key;
		key = Cypher.getKeyFromFile("client/"+aFile.getKeyName(), kp.getPrivate() );
		
		//decifrar o ficheiro, substituindo o existente, se existir
		FileInputStream fis = new FileInputStream( aFile.getFullPath()+".cif" );
		FileOutputStream fos = new FileOutputStream( aFile.getFullPath() );
		Cypher.decrypt(key, fis, fos);
		fis.close();
		fos.close();
		
		//actualizar o timestamp do ficheiro decifrado
		File encryptedFile = new File(aFile.getFullPath()+".cif");
		File decryptedFile = new File(aFile.getFullPath());
		decryptedFile.setLastModified(encryptedFile.lastModified());
		
		//apagar a chave e o ficheiro cifrado
		File keyFile = new File("client/"+aFile.getKeyName());
		
		encryptedFile.delete();
		keyFile.delete();
	}
	
	public void synchronize( ObjectOutputStream oos, ObjectInputStream ois, String fileList, String user ){
		Timer timer = new Timer();
		try {
			timer.schedule( new Synch( oos, ois, fileList, user ) , 0, 10000 );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int share( ObjectOutputStream oos, ObjectInputStream ois, String files, String shUser, String user ){
		int filesShared = 0;
		Boolean canContinue;
		try {
			//enviar informacao de partilha
			oos.writeObject(shUser);
			oos.writeObject(files);
			
			canContinue = (Boolean) ois.readObject();
			if( canContinue ){
				Boolean keyToEncrypt = (Boolean) ois.readObject();
				
				while( keyToEncrypt ){
					SendReceive.fileReceive("client/", ois);
					String file = (String) ois.readObject();
					File newKey = createNewKey( new File("client/"+user+"."+file+".key"), user, shUser);
					SendReceive.fileSend(newKey, oos);
					newKey.delete();
					keyToEncrypt = (Boolean) ois.readObject();
				}
				
				filesShared = (int) ois.readObject();
			}else
				filesShared = (int) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return filesShared;
	}
	
	private File createNewKey( File oldKey, String user, String shUser ){
		String keyName = oldKey.getName();
		try {
			KeyPair kp = Cypher.getKeyPairFromKeystore(user);
			SecretKey key = Cypher.getKeyFromFile( "client/"+keyName, kp.getPrivate() );
			oldKey.delete();
			
			PublicKey shUserPk = Cypher.getPkFromCertificate(user, shUser);
			Cypher.saveKeyToFile(key, "client/"+keyName, shUserPk);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return new File("client/"+keyName);
	}
	
	public void listFiles( ObjectInputStream ois ) throws Exception{
		System.out.println(" =======================================================================================================");
		System.out.println("| File name               | Date                    | Owner                   | Shared With             |");
		System.out.println(" =======================================================================================================");
		int fileNr = (Integer) ois.readObject();
		
		//ficheiros do utilizador
		for( int i = 0; i < fileNr; i++ ){
			//receber nome, data, dono
			String realName = (String) ois.readObject();
			long timestamp = (long) ois.readObject();
			String owner = (String) ois.readObject();
			
			if( realName.compareTo("keys") == 0 || realName.compareTo("signatures") == 0 )
				continue;
			
			//retirar .cif do final do ficheiro
			String name = realName.replace(".cif", "");
			
			//imprimir a linha correspondente ao ficheiro
			System.out.print("| "+String.format("%-23s", name)+" | "+String.format("%-23s", new Date(timestamp) )+" | "+String.format("%-23s", owner)+" | ");
			
			//receber nr de utilizadores com que o ficheiro esta partilhado
			int usersNr = (Integer) ois.readObject();
			
			String users = new String();
			for( int j = 0; j < usersNr; j++ ){
				String temp = (String) ois.readObject();
				users += temp + " ";
			}
			System.out.println( String.format("%-23s", users)+ " |" );
			System.out.println(" =======================================================================================================");
		}
		
		//ficheiros partilhados com o utilizador
		fileNr = (Integer) ois.readObject();
		
		for( int i = 0; i < fileNr; i++ ){
			//receber nome, data, dono
			String realName = (String) ois.readObject();
			long timestamp = (long) ois.readObject();
			String owner = (String) ois.readObject();
			
			//retirar .cif do final do ficheiro
			String name = realName.replace(".cif", "");
			
			//imprimir a linha correspondente ao ficheiro
			System.out.print("| "+String.format("%-23s", name)+" | "+String.format("%-23s", new Date(timestamp) )+" | "+String.format("%-23s", owner)+" | ");
			
			//receber nr de utilizadores com que o ficheiro partilhado esta partilhado
			int usersNr = (Integer) ois.readObject();
			
			String users = new String();
			for( int j = 0; j < usersNr; j++ ){
				String temp = (String) ois.readObject();
				users += temp + " ";
			}
			System.out.println( String.format("%-23s", users)+ " |" );
			System.out.println(" =======================================================================================================");
		}
	}
	
	class Synch extends TimerTask{

		private String[] files;
		private String user;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		
		public Synch( ObjectOutputStream oos, ObjectInputStream ois, String fileList, String user ) throws IOException{
			this.files = fileList.split(":");
			this.user = user;
			this.ois = ois;
			this.oos = oos;
		}
		
		public void run() {
			int status;
			
			for(int i = 0; i < files.length; i++ ){
				CAuxFileData aFile = new CAuxFileData( files[i], user );
				try {
					status = commit( oos, ois, aFile );
					if( status == 2 || status == 3 ){
						oos.writeObject(new Boolean(true));
						status = get( oos, ois, aFile );
						if( status == 2 )
							status = 9;
					}else
						oos.writeObject(new Boolean(false));
					
					if( status == 1 || status == 2 || status == 5 || status == 8 || status == 9 || status == 10 )
						printStatus(status, files[i]);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
}