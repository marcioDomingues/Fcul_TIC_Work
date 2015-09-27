package network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SendReceive {

	/**
	* [fileReceive description]
	* Receives file through the network stream
	* 
	* @param file file to Receive
	* @param inStream InputStream
	* @throws ClassNotFoundException 
	*/
	public static void fileReceive( String path, ObjectInputStream objStream ) throws IOException, ClassNotFoundException{
		
		String name = (String)objStream.readObject();
		long length = (long)objStream.readObject();
		long lastmod = (long)objStream.readObject();
		
		File f = new File( path+"/"+name );

		//envia o input stream para um output stream para o file
		FileOutputStream fos = new FileOutputStream( f );
		BufferedOutputStream bos = new BufferedOutputStream( fos );

		long fileTotalSize = 0;
		int count;

		byte[] buffer = new byte[1024];

		// ciclo escreve blocos de 1024 bytes no
		// buffer stream ate total fileSize
		while ( fileTotalSize < length && (count = objStream.read(buffer, 0, (int)Math.min(buffer.length, length-fileTotalSize))) > 0 )
		{
			bos.write(buffer, 0, count);
			fileTotalSize += count;
		}

		//fecha buffer depois da escrita
		bos.flush();
		bos.close();
		
		f.setLastModified(lastmod);
	}


	/**
	* [fileSend description]
	* Sends file through the network stream 
	* 
	* @param file file to send
	* @param outStream OutputStream
	* @throws InterruptedException 
	*/
	public static void fileSend( File file, ObjectOutputStream objStream ) throws IOException{
		//sending file
		FileInputStream fis = new FileInputStream( file );
		BufferedInputStream bis = new BufferedInputStream( fis );

		objStream.writeObject( file.getName() );
		objStream.writeObject( file.length() );
		objStream.writeObject( file.lastModified() );
		
		int count;
		byte[] buffer = new byte[1024];

		// ciclo escreve blocos de 1024 bytes no
		// buffer stream ate total fileSize
		while ( (count = bis.read(buffer)) > 0 )
		{
			objStream.write(buffer, 0, count);
		}

		objStream.flush();
		bis.close();
	}
	
	public static long compareTimeStamps( long timeClient, long timeServer ){
		return timeClient - timeServer;
	}
}
