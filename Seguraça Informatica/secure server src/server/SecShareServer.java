package server;

/***************************************************************************
*   Seguranca Informatica 2013/14
***************************************************************************/

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Scanner;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * @author Alessandro Melo, Daniel Marques, Márcio Domingues
 *
 */

//Servidor do serviço SecShareServer

public class SecShareServer {

	public static void main(String[] args) {
		
		System.out.println("servidor: main");
		Scanner s = new Scanner( System.in );
		String pwd = null;
		
		Console c = System.console();
		if ( !(c == null) ){
			char[] passwordArray = c.readPassword("Pass: ");
			pwd = passwordArray.toString();
		}else{
			System.out.print("Pass: ");
			pwd = s.next();
		}
		
		System.out.print("Port: ");
		int port = s.nextInt();
		
		//criar ficheiro unico de info de partilhas
		File part = new File( "server/sh_info.perm" );

		try {
			part.createNewFile();
			
			MacFunctions mf = new MacFunctions();
			if( mf.verifyMacString(new File ("server/users.pwd"), pwd) && mf.verifyMacString(part, pwd)){
				SecShareServer server = new SecShareServer();
				server.startServer( port );
			}else
				System.out.println("Password incorreta ou sistema corrompido");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		s.close();
	}

	public void startServer ( int port ){
		ServerSocket sSoc = null;
		
		try {
			System.setProperty("javax.net.ssl.keyStore", "server/secshareserver.ks");
			System.setProperty("javax.net.ssl.keyStorePassword", "secshareserver01");
			ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
			sSoc = ssf.createServerSocket(port);
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
        
		System.out.println("Listening for requests...");
		
		while(true) {
			try {
				Socket inSoc = sSoc.accept();
				ServerThread newServerThread = new ServerThread(inSoc);
				newServerThread.start();
				System.out.println("============================");
				System.out.println(newServerThread);
		    }
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}

//Threads utilizadas para comunicação com os clientes
	class ServerThread extends Thread {
	
		private Socket socket = null;
	
		ServerThread(Socket inSoc){
			socket = inSoc;
	    }
	
		public void run(){
			try {
				ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
	
				String user = null;
				String passwd = null;
				boolean check = false;
	
				user = (String)inStream.readObject();
				passwd = (String)inStream.readObject();
				
				byte[] pass = passwd.getBytes();
				
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				byte[] hash = md.digest(pass);
				
				MacFunctions mf = new MacFunctions();
				String sintese = mf.byteToString(hash);
				
				File f = new File("server/users.pwd");
				check = hasPermission( f, user, sintese );
				
				if( check ){
					
					ServerOperations operation = new ServerOperations();
					File dir = new File("server/"+user+"/keys");
					dir.mkdirs();
					
					outStream.writeObject(new Boolean(true));
					System.out.println(user+" logged in");
					
					int op = 0;
					
					op = (Integer)inStream.readObject();
					
					System.out.print("Operation: ");
					
					if( op == 1 ){ // commit ou -c
						
						int fileNrs = (Integer)inStream.readObject();
						
						for( int i = 0; i < fileNrs; i++ ){
							operation.commit( outStream, inStream, user );
						}
						System.out.println("commit");
						
					}else if( op == 2 ){ // -p
						
						operation.share(outStream, inStream, user);
						System.out.println("share files");
						
					}else if( op == 3 ){ // -s
						
						System.out.println("synch");
						operation.synchronize(outStream, inStream, user);
						
					}else if( op == 4 ){ // -l
						
						operation.listFiles(outStream, user);
						System.out.println("list files");
						
					}else if( op == 5 ){ // -g
						
						//receber nr de ficheiros
						int fileNr = (Integer)inStream.readObject();
						
						//receber info de ficheiros
						for( int i = 0; i < fileNr; i++ )
							operation.get( outStream, inStream, user );
						
						System.out.println("get");
					}
					
				}else{
					outStream.writeObject(new Boolean(false));
					System.out.println("Permission denied for "+ user );
				}
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
	 		} catch (Throwable e) {
				e.printStackTrace();
			}
	 	}
	 	
		private boolean hasPermission(File f, String user, String sintese) throws Exception{
			String temp;
		
			BufferedReader reader = new BufferedReader( new FileReader(f) );
			while( (temp = reader.readLine()) != null )
			{
				if( temp.compareTo(user+","+sintese) == 0 ){
					reader.close();
					return true;
				}	
			}
			reader.close();
			
			return false;
		}
	}
}