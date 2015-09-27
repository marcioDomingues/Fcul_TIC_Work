package client;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class SecShareClient {

	public static void main(String[] args){
		
		System.out.println( "SecShareClient: -u <userId> -a <serverAddress> ( -c <file.txt:file2.txt> | -g <file1.txt:file2.txt> | -p <userId> <file.txt:file2.txt> | -s <file.txt:file2.txt> | -l )" );
		System.out.print( "SecShareClient: ");
		Scanner s = new Scanner( System.in );
		
		//processar o comando dado
		String command = s.nextLine();
		//System.out.println(command);
		//0 = -u
		//1 = userId
		//2 = -a
		//3 = serverAddress
		String[] arguments = verifyCommand( command );
		if( arguments == null ){
			System.out.println("Invalid command");
			s.close();
			return;
		}
		
		String pass = null;
		Console c = System.console();
		if ( !(c == null) ){
			char[] passwordArray = c.readPassword("Welcome "+arguments[1]+", please insert password: ");
			pass = passwordArray.toString();
		}else{
			System.out.print("Welcome "+arguments[1]+", please insert password: ");
			pass = s.next();
		}
		
		try {
			System.setProperty("javax.net.ssl.trustStore", "client/"+arguments[1]+"/"+arguments[1]+".ks");
			SocketFactory sf = SSLSocketFactory.getDefault();
			
			Socket echoSocket = sf.createSocket( arguments[3].split(":")[0], Integer.parseInt( arguments[3].split(":")[1] ) );
			
			ObjectInputStream in = new ObjectInputStream(echoSocket.getInputStream());
	 		ObjectOutputStream out = new ObjectOutputStream(echoSocket.getOutputStream());
			
			out.writeObject(arguments[1]);
			out.writeObject(pass);
			
			Boolean canContinue = (Boolean) in.readObject();
			int filesShared;
			
			if( canContinue ){
				
				ClientOperations operation = new ClientOperations();
				if( arguments[4].compareTo("-c") == 0 ){
					
					// enviar opcao
					sendOp( out, 1 );
					
					//enviar nr de ficheiros
					String[] files = processFileList( arguments[5] );
					out.writeObject(files.length);
					
					//enviar ficheiros
					for( int i = 0; i < files.length; i++ ){
						CAuxFileData aFile = new CAuxFileData( files[i], arguments[1] );
						int status = operation.commit( out, in, aFile );
						operation.printStatus( status, files[i] );
					}
					
					closeStuff( echoSocket, in, out );
					
				}else if( arguments[4].compareTo("-p") == 0 ){
					
					//enviar opcao
					sendOp( out, 2 );
					
					filesShared = operation.share(out, in, arguments[6], arguments[5], arguments[1]);
					
					System.out.println(filesShared + " files shared with " + arguments[5] );
					
					closeStuff( echoSocket, in, out );
					
				}else if( arguments[4].compareTo("-s") == 0 ){
					//enviar opcao
					sendOp( out, 3 );
					
					operation.synchronize(out, in, arguments[5], arguments[1]);
					
				}else if( arguments[4].compareTo("-l") == 0 ){
					sendOp( out, 4 );
					
					operation.listFiles(in);
					
					closeStuff( echoSocket, in, out );
					
				}else if( arguments[4].compareTo("-g") == 0 ){
					
					// enviar opcao
					sendOp( out, 5 );
					
					//enviar nr de ficheiros
					String[] files = processFileList( arguments[5] );
					out.writeObject(files.length);
					
					//receber ficheiros
					for( int i = 0; i < files.length; i++ ){
						CAuxFileData aFile = new CAuxFileData( files[i], arguments[1] );
						int status = operation.get( out, in, aFile );
						operation.printStatus( status, files[i] );
					}
					
					closeStuff( echoSocket, in, out );
				}
			}
			else
				System.out.println("Authentication failure!");
		} catch (Throwable e) {
			e.printStackTrace();
		} finally{
			s.close();
		}
	}
	
	private static void sendOp( ObjectOutputStream oos, int op ) throws IOException{
		oos.writeObject(op);
	}
	
	private static String[] processFileList( String files ){
		return files.split(":");
	}
	
	private static void closeStuff( Socket echoSocket, ObjectInputStream in, ObjectOutputStream out){
		try {
			out.close();
			in.close();
	 		echoSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String[] verifyCommand( String command ){
		String[] arguments = null;
		String[] temp = command.split(" ");
		
		if( temp[0].compareTo("-u") == 0 && temp[2].compareTo("-a") == 0 ){
			if( temp[4].compareTo("-c") == 0 ){
				if( temp.length == 6 )
					arguments = temp;
			}
			if( temp[4].compareTo("-g") == 0 ){
				if( temp.length == 6 )
					arguments = temp;
			}
			if( temp[4].compareTo("-p") == 0 ){
				if( temp.length == 7 )
					arguments = temp;
			}
			if( temp[4].compareTo("-s") == 0 ){
				if( temp.length == 6 )
					arguments = temp;
			}
			if( temp[4].compareTo("-l") == 0 ){
				if( temp.length == 5 )
					arguments = temp;
			}
		}
		return arguments;
	}
}