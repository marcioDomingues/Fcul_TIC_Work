package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.Scanner;

public class AddUser {

	public static void main(String[] args) throws Exception {
		MacFunctions mf = new MacFunctions();
		
		boolean check = true;
		File f = new File("server/users.pwd");
		
		Scanner s = new Scanner( System.in );
		System.out.print("Server Pass: ");
		String servPass = s.next();
		
		if( f.exists() )
			check = mf.verifyMacString(f, servPass);
		else
			f.createNewFile();
		
		if(check){
			System.out.print("User name: ");
			String user = s.next();
			
			System.out.print("User pass: ");
			String pwd = s.next();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(pwd.getBytes());
			
			s.close();
			
			FileWriter file = new FileWriter("server/users.temp", true);
			BufferedWriter buf = new BufferedWriter(file);
			
			BufferedReader reader = new BufferedReader( new FileReader(f));
			
			String temp, temp2 = new String();
			while( (temp = reader.readLine()) != null )
			{
				if( temp.startsWith(user)){
					System.out.println("User "+user+" ja existe!");
					reader.close();
					buf.close();
					File f2 = new File("server/users.temp");
					f2.delete();
					System.exit(-1);
				}
					
				if( !temp2.isEmpty() )
				{
					buf.append(temp2);
					buf.newLine();
				}
				temp2 = temp;
			}
			reader.close();
			
			buf.append(user+","+mf.byteToString(hash));
			buf.newLine();
			buf.close();
			
			f.delete();
			File f2 = new File("server/users.temp");
			f2.renameTo(f);
			
			mf.addMacString(f);
			
			f = new File("server/"+user+"/keys");
			f.mkdirs();
			f = new File("server/"+user+"/signatures");
			f.mkdir();
			
			System.out.println("Usuario(a) "+user+" adicionado(a)!");
			
		}else
			System.out.println("Senha incorrecta ou ficheiro corrompido!");
	}
}
