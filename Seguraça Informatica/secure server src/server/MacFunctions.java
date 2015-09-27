package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.spec.KeySpec;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class MacFunctions {
	
	void addMacString(File f) throws Exception{
		
		System.out.println();
		FileWriter file = new FileWriter("server/"+f.getName(), true);
		BufferedWriter buf = new BufferedWriter(file);
		
		buf.append(macString(f, "java"));
		buf.close();
	}

	boolean verifyMacString(File f, String pass) throws Exception{
		
		if( f.length() == 0 )
			return true;
		
		String temp, temp2 = new String();
 		
		BufferedReader reader = new BufferedReader( new FileReader(f));

		FileWriter file = new FileWriter("server/tempFile", true);
		BufferedWriter buf = new BufferedWriter(file);
		
		while( (temp = reader.readLine()) != null )
		{
			if( !temp2.isEmpty() )
			{
				buf.append(temp2);
				buf.newLine();
			}
			temp2 = temp;
		}
		
		reader.close();
		buf.close();
		
		File f2 = new File("server/tempFile");
		boolean b = temp2.compareTo(macString(f2, pass)) == 0;
		
		f2.delete();
		
		return b;
	}
	
	String macString(File f, String pass) throws Exception{
		
		String temp, total = new String(), nl = System.getProperty("line.separator");
		
		BufferedReader reader = new BufferedReader( new FileReader(f));
		while( (temp = reader.readLine()) != null )
		{
			total += temp + nl;
		}
		reader.close();
		
		byte[] all = total.getBytes();
		
		char b[] = pass.toCharArray();
		KeySpec pbeKs = new PBEKeySpec(b);
		SecretKeyFactory pbeSkf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey pbeKey = pbeSkf.generateSecret(pbeKs);
		
		Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(pbeKey);
	    mac.update(all);
	    
		
		return byteToString(mac.doFinal());
	}
 	
	String byteToString(byte[] bytes) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
			int parteBaixa = bytes[i] & 0xf;
			
			if (parteAlta == 0)
				s.append('0');
			
			s.append(Integer.toHexString(parteAlta | parteBaixa));
	   }
	   return s.toString();
	}
}