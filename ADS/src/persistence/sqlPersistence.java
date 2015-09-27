package persistence;

import java.util.ArrayList;

//The API for accessing and processing data stored in a database
import java.sql.*;
import java.text.ParseException;

//Allows you to convert from string to date or vice versa
import java.text.SimpleDateFormat;



import domain.Ordem;


// TODO: Auto-generated Javadoc
/**
 * The Class sqlPersistence.
 */
public class sqlPersistence implements Persistence {

	//holds the return set
	/** The rows. */
	static ResultSet rows;

	// Holds row values for the table
	/** The database results. */
	static Object[][] databaseResults;

	// A connection object is used to provide access to a database
	/** The conn. */
	Connection conn = null;
	
	// Statement objects executes a SQL query
	/** The sql state. */
	Statement sqlState = null;
	
	// Temporarily holds the row results
	/** The temp row. */
	Object[] tempRow;

	/**
	 * Instantiates a new sql persistence.
	 */
	public sqlPersistence(){
		
	try {
		// The driver allows you to query the database with Java
		// forName dynamically loads the class for you
		Class.forName("com.mysql.jdbc.Driver");

		// DriverManager is used to handle a set of JDBC drivers
		// getConnection establishes a connection to the database
		// You must also pass the userid and password for the database
		conn = DriverManager.getConnection("jdbc:mysql://localhost/samp_db","root","marcio1981");

		
		// Statement objects executes a SQL query
		// createStatement returns a Statement object
		//change database on jtable change 
		sqlState = conn.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE, 
				ResultSet.CONCUR_UPDATABLE);

		
	} catch (SQLException ex) {
    	// String describing the error
        System.out.println("SQLException: " + ex.getMessage());
        
        // Vendor specific error code
        System.out.println("VendorError: " + ex.getErrorCode());
    } 
    catch (ClassNotFoundException e) {
		// Executes if the driver can't be found
		e.printStackTrace();
	}
	
}	



	/* (non-Javadoc)
	 * @see persistence.Persistence#addOrdem(java.lang.String)
	 */
	@Override
	public void addOrdem(String ordem) {
		
		String[] parts = ordem.split(";");

		String  sIdOrdem = parts[0], 
				sIdFunc = parts[1], 
				sIdCliente = parts[2], 
				sOrigem = parts[3],
				sDestino = parts[4],
				sData= parts[5],
				sVolume = parts[6];
		
		
	
		java.util.Date sqlDataPedido = getADate(sData);
		//System.out.println(sqlDataPedido);
		//System.out.println((Date) sqlDataPedido);
		
		//Update DB
		try {
			// Moves the database to the row where data will be placed
			rows.moveToInsertRow();
			
			rows.updateString("idOrdem", sIdOrdem);
			
			// Update the values in the database
			rows.updateString("idFuncionario", sIdFunc);
			rows.updateString("idCliente", sIdCliente);
			
			rows.updateString("origem", sOrigem);
			rows.updateString("destino", sDestino);
			
			rows.updateDate("dataPedido", (Date) sqlDataPedido);
			
			rows.updateString("volume", sVolume);
			
			// Inserts the changes to the row values in the database
			rows.insertRow();
			
			// Directly updates the values in the database
			//rows.updateRow();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	
	/* (non-Javadoc)
	 * @see persistence.Persistence#getOrdens()
	 */
	@Override
	public ArrayList<Ordem> getOrdens() {
		
		ArrayList<Ordem> ordens = new ArrayList<Ordem>();
		
		// This is the query I'm sending to the database
		String selectStuff = "Select idOrdem, idFuncionario,idCliente, origem, destino, dataPedido, volume from ordens";

		
		try {
			
			// A ResultSet contains a table of data representing the
			// results of the query. It can not be changed and can 
			// only be read in one direction
			//ROWS REFERS TO DATABASE OPERATIONS
			rows = sqlState.executeQuery(selectStuff);
			
			// next is used to iterate through the results of a query
	        while(rows.next()){
	        	ordens.add(new Ordem ( rows.getInt(1),/*id ordem*/ 
	        							rows.getInt(2),/*id funcionario*/
	        							rows.getInt(3),/*id cliente*/
	        							rows.getString(4),/*origem*/ 
	        							rows.getString(5),/*destino*/
	        							rows.getDate(6),/*data do pedido*/
	        							rows.getInt(7) /*volume*/
	        							) );
	        }
			
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		return ordens;
	}

	
	// Will convert from string to sql date
		/**
	 * Gets the a date.
	 *
	 * @param sDate the s date
	 * @return the a date
	 */
	public static java.util.Date getADate(String sDate){
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
			
			java.util.Date dataPedido, sqlDataPedido=null;
			
			try {
				dataPedido = dateFormatter.parse(sDate);
				//System.out.println(dataPedido);
				sqlDataPedido = new java.sql.Date(dataPedido.getTime());
				//System.out.println(sqlDataPedido);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			return sqlDataPedido;
		}
}
