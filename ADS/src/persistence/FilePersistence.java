package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import domain.Ordem;

// TODO: Auto-generated Javadoc
/**
 * The Class FilePersistence.
 */
public class FilePersistence implements Persistence {

	/* (non-Javadoc)
	 * @see persistence.Persistence#addOrdem(java.lang.String)
	 */
	public void addOrdem(String ordem) {
		
		FileWriter file = null;
		try {
			file = new FileWriter("Ordens.csv", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter buf = new BufferedWriter(file);
		
		try {
			buf.append(ordem + System.getProperty("line.separator"));
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	

	/* (non-Javadoc)
	 * @see persistence.Persistence#getOrdens()
	 */
	public ArrayList<Ordem> getOrdens() {
		
		ArrayList<Ordem> ordens = new ArrayList<Ordem>();
		
		File f = new File("Ordens.csv");
		
		if( f.exists() ){
			
			BufferedReader reader = null;
			
			try {
				reader = new BufferedReader( new FileReader(f));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			String temp;
			String[] temp2;
			try {
				while( (temp = reader.readLine()) != null ){
					temp2 = temp.split(";");
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					Date date = formatter.parse(temp2[5]);
					
					ordens.add(new Ordem (Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]),
							Integer.parseInt(temp2[2]), temp2[3], temp2[4], date,
							Integer.parseInt(temp2[6])));
				}
				
				reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return ordens;
	}
}