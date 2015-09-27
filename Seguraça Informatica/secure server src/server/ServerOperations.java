package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import network.SendReceive;

public class ServerOperations {

	public void commit( ObjectOutputStream oos, ObjectInputStream ois, String user ) throws Exception{
		int status;
		String path = (String)ois.readObject(); // fullPath do cliente
		long timestamp = (long)ois.readObject();
		
		SAuxFileData aFile = new SAuxFileData( path, user );
		
		status = statusManager( aFile, timestamp, 1 );
		
		if( status == 0 ){
			oos.writeObject(new Boolean(true)); // indica ao cliente que a transaccao vai ser feita
			commitKeyHandler( oos, ois, aFile.getUser() );
			commitSigHandler( ois, aFile.getOwner(), aFile.getName() );
			SendReceive.fileReceive( aFile.getPath(), ois );
		}else{
			oos.writeObject(new Boolean(false));
			oos.writeObject(status);
		}
 	}
	
	private void commitKeyHandler(ObjectOutputStream oos, ObjectInputStream ois, String user) throws Exception{
		String keyName = (String) ois.readObject();
		File f = new File( "server/"+user+"/keys/"+keyName);
		if( f.exists() ){
			oos.writeObject(new Boolean(true));
			SendReceive.fileSend(f, oos);
		}else{
			f.delete();
			oos.writeObject(new Boolean(false));
			SendReceive.fileReceive("server/"+user+"/keys", ois);
		}
	}
	
	private void commitSigHandler(ObjectInputStream ois, String owner, String fileName ) throws Exception{
		File sigDir = new File("server/"+owner+"/signatures");
		File[] signatures = sigDir.listFiles();
		String sigName;
		
		for( int i = 0; i < signatures.length; i++ ){
			String[] temp = signatures[i].getName().split("_");
			sigName = temp[1];
			if( sigName.compareTo(fileName+".sig") == 0 ){
				signatures[i].delete();
				break;
			}
		}
		SendReceive.fileReceive("server/"+owner+"/signatures", ois);
	}
 	
 	public void get( ObjectOutputStream oos, ObjectInputStream ois, String user ) throws Exception{
 		int status;
 		String path = (String)ois.readObject(); // fullPath do cliente
		long timestamp = (long)ois.readObject();
		
		SAuxFileData aFile = new SAuxFileData( path, user );
		File f = new File( aFile.getFullPath()+".cif" );
		
		status = statusManager( aFile, timestamp, 2 );
		
		if( status == 0 ){
			oos.writeObject(new Boolean(true));
			SendReceive.fileSend(f, oos);
			getSigHandler( oos, aFile );
			getKeyHandler( oos, aFile );
		}else{
			oos.writeObject(new Boolean(false));
			oos.writeObject(status);
		}
 	}
 	
 	private void getKeyHandler( ObjectOutputStream oos, SAuxFileData aFile ) throws IOException{
 		File keyFile = new File( "server/"+aFile.getUser()+"/keys/"+aFile.getKeyName() );
 		SendReceive.fileSend(keyFile, oos);
 	}
 	
 	private void getSigHandler( ObjectOutputStream oos, SAuxFileData aFile ) throws Exception{
 		File sigDir = new File("server/"+aFile.getOwner()+"/signatures");
 		File[] fileList = sigDir.listFiles();
 		
 		for( int i = 0; i < fileList.length; i++ ){
 			String[] temp = fileList[i].getName().split("_");
 			if( temp[1].compareTo(aFile.getName()+".sig") == 0 ){
 				oos.writeObject(fileList[i].getName());
 				SendReceive.fileSend(fileList[i], oos);
 				break;
 			}
 		}
 	}
 	
 	private int statusManager( SAuxFileData aFile, long timestamp, int mode ) throws Exception{
		int status = 0;
		File f = new File( aFile.getFullPath()+".cif" );
		
		if( mode == 1 ){ // mode 1 == commit
			if( timestamp == -1 ){
				//System.out.println("SMCommit: ficheiro nao existe");
				status = 2;}
			else if( SendReceive.compareTimeStamps(timestamp, f.lastModified() ) < 0 ){
				//System.out.println("SMCommit: ficheiro mais recente no servidor");
				status = 3;}
			else if( SendReceive.compareTimeStamps(timestamp, f.lastModified() ) == 0 ){
				//System.out.println("SMCommit: ficheiro igual no servidor");
				status = 4;}
		}else{ // mode 2 == get
			if( aFile.isShared() ){
				if( !hasSharePermission(aFile.getName(), aFile.getUser(), aFile.getOwner()) ){
					//System.out.println("SMGet: permissoes inexistentes");
					status = 5;
					return status;
				}
			}
			
			if( !f.exists() ){
				//System.out.println("SMGet: ficheiro nao existe");
				status = 2;}
			else if( SendReceive.compareTimeStamps(timestamp, f.lastModified() ) == 0 ){
				//System.out.println("SMGet: ficheiro igual no cliente");
				status = 6;}
			else if( SendReceive.compareTimeStamps(timestamp, f.lastModified() ) > 0 ){
				//System.out.println("SMGet: ficheiro mais recente no cliente");
				status = 7;}
		}
		
		return status;
 	}
 	
 	private boolean hasSharePermission( String file, String user, String owner ) throws Exception{
 		File f = new File("server/sh_info.perm");
 		String line;
 		int isOnFile;
 		
 		BufferedReader reader = new BufferedReader( new FileReader(f) );
 		while( (line = reader.readLine()) != null )
		{
 			isOnFile = line.compareTo( owner+":"+file+":"+user );
 			if( isOnFile == 0 ){
 				reader.close();
 				return true;
 			}
 				
		}
 		reader.close();
 		return false;
 	}
 	
 	public void synchronize( ObjectOutputStream oos, ObjectInputStream ois, String user ){
 		Boolean sendFile;
 		
 		while(true){
	 		try {
				commit(oos, ois, user );
				sendFile = (Boolean) ois.readObject();
				if( sendFile )
					get(oos, ois, user);
			} catch (Exception e) {
				break; // este break é muito importante nao apagar
			}
 		}
 	}
 	
 	public void share(ObjectOutputStream oos, ObjectInputStream ois, String user ) throws Exception{
 		//receber e processar info
		String shUser = (String)ois.readObject();
		String filesNotSplit = (String)ois.readObject();
		String[] files = filesNotSplit.split(":");
		
		String line, line2 = new String(), nl = System.getProperty("line.separator"), total = new String();
		
		//verificar se os ficheiros a partilhar existem no servidor
		//se nao existir nenhum nao abre o ficheiro de partilhas = melhor performance
		Boolean files2Share = false;
		for( int i = 0; i < files.length; i++ ){
			File f = new File( "server/"+ user + "/" +files[i]+".cif" );
			if( !f.exists() )
				files[i] = "easter_egg";
			else
				files2Share = true;	
		}
		
		if( files2Share == true ){
			oos.writeObject(new Boolean(true));			
			File shInfo = new File( "server/sh_info.perm" );
			BufferedReader reader = new BufferedReader( new FileReader( shInfo ) );
			
			//ler o conteudo do ficheiro de partilhas e verificar se o ficheiro que se quer partilhar ja esta partilhado
			while( (line = reader.readLine()) != null ){
				if( !line2.isEmpty() ){
					total += line2 + nl;
					for( int j = 0; j < files.length; j++ ){
						if( line2.compareTo( user + ":" + files[j] + ":" + shUser ) == 0 )
							files[j] = "easter_egg";
					}
				}
				
				line2 = line;
				
			}
			
			reader.close();
			
			//escrever no ficheiro a info das partilhas
			
			// formato daniel:bla.txt:alex
			// em que daniel = dono
			// bla.txt = ficheiro
			// alex = usuario com quem se quer partilhar
			BufferedWriter writer = new BufferedWriter( new FileWriter( shInfo ) );
			int sharedNr = 0;
			for( int k = 0; k < files.length; k++ ){
				if( files[k].compareTo("easter_egg") != 0 ){
					total += user + ":" + files[k] + ":" + shUser + nl;
					sharedNr++;
					//enviar boolean true para o cliente
					oos.writeObject(new Boolean(true));
					//enviar a key
					File key = new File("server/"+user+"/keys/"+user+"."+files[k]+".key");
					SendReceive.fileSend(key, oos);
					oos.writeObject(files[k]);
					//receber a nova key
					SendReceive.fileReceive("server/"+shUser+"/keys", ois);
				}
			}
			
			//enviar boolean false para o cliente
			oos.writeObject(new Boolean(false));
			
			if( !total.isEmpty() )
				writer.write(total);
			
			writer.close();
			
			//enviar o nr de entradas adicionadas ao ficheiro para o cliente
			oos.writeObject(sharedNr);
			
			//recalcular MAC
			MacFunctions mf = new MacFunctions();
			mf.addMacString(shInfo);
		}else{
			oos.writeObject(new Boolean(false));
			oos.writeObject(0);
		}
 	}
 	
 	public void listFiles(ObjectOutputStream oos, String user) throws Throwable{
 		//ficheiros do utilizador
		File userDir = new File( "server/"+user );
		File[] userFiles = userDir.listFiles();
		ArrayList<String> sharedUsersList;
		ArrayList<String> filesAndOwners;
		
		//enviar tamanho
		oos.writeObject( userFiles.length );
		
		for( int i = 0; i < userFiles.length; i++ ){
			//enviar nome, data, dono
			oos.writeObject( userFiles[i].getName() );
			oos.writeObject( userFiles[i].lastModified() );
			oos.writeObject( user );
			
			if( userFiles[i].getName().compareTo("keys") == 0 || userFiles[i].getName().compareTo("signatures") == 0 )
				continue;
			
			String realName = userFiles[i].getName().replace(".cif", "");
			
			sharedUsersList = sharedUsers( realName, user );
			//enviar o nr de utilizadores com quem o ficheiro esta partilhado
			oos.writeObject( sharedUsersList.size() );
			Iterator<String> itr = sharedUsersList.iterator();
			
			//enviar os nomes dos utilizadores
			while( itr.hasNext() ){
				oos.writeObject( itr.next() );
			}
		}
		
		filesAndOwners = sharedFiles( user );
		//enviar o nr de ficheiros partilhados com o utilizador
		oos.writeObject( filesAndOwners.size()/2 );
		Iterator<String> itr2 = filesAndOwners.iterator();
		
		for( int j = 0; j < filesAndOwners.size()/2; j++ ){
			itr2.hasNext();
			String owner = itr2.next();
			itr2.hasNext();
			String name = itr2.next();
			
			File f = new File( "server/"+owner+"/"+name+".cif" );
			oos.writeObject( name );
			oos.writeObject( f.lastModified() );
			oos.writeObject( owner );
			
			sharedUsersList = sharedUsers( name, owner );
			//enviar o nr de utilizadores com quem o ficheiro esta partilhado
			oos.writeObject( sharedUsersList.size() );
			Iterator<String> itr = sharedUsersList.iterator();
			
			//enviar os nomes dos utilizadores
			while( itr.hasNext() ){
				oos.writeObject( itr.next() );
			}
		}
 	}
 	
 	//cria uma lista de utilizadores com os quais 1 dado ficheiro esta partilhado
 	private ArrayList<String> sharedUsers( String file, String owner ) throws Exception{
 		String line;
 		ArrayList<String> userList = new ArrayList<String>();
 		String[] lineArgs;
 		
 		BufferedReader reader = new BufferedReader( new FileReader( new File("server/sh_info.perm") ) );
 		while( (line = reader.readLine()) != null )
		{
 			lineArgs = line.split(":");
 			if( lineArgs[0].compareTo(owner) == 0 && lineArgs[1].compareTo(file) == 0 )
 				userList.add(lineArgs[2]);
		}
 		reader.close();
 		
 		if( userList.isEmpty() )
 			userList.add("Not Shared");
 		
 		return userList;
 	}
 	
 	// cria uma lista com os nomes dos ficheiros partilhados com user e respectivos donos
 	private ArrayList<String> sharedFiles( String user ) throws Exception{
 		String line;
 		String[] lineArgs;
 		ArrayList<String> filesAndOwners = new ArrayList<String>();
 		
 		BufferedReader reader = new BufferedReader( new FileReader( new File("server/sh_info.perm") ) );
 		while( (line = reader.readLine()) != null )
		{
 			lineArgs = line.split(":");
 			if ( lineArgs.length > 1 ){
	 			if( lineArgs[2].compareTo(user) == 0 ){
	 				//adicionar dono e respectivo ficheiro
	 				filesAndOwners.add(lineArgs[0]);
	 				filesAndOwners.add(lineArgs[1]);
	 			}
 			}
		}
 		reader.close();
 		
 		return filesAndOwners;
 	}
}