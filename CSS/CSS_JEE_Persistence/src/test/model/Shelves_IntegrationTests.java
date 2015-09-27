package test.model;


import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.*;

import java.util.Iterator;

import javax.naming.OperationNotSupportedException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.EMediumAttribute;
import model.EMediumPropertiesData;
import model.EMediumType;
import model.ShelvesFacade;
import model.lendables.Lendable;
import model.lendables.Library;
import model.rentals.Rental;
import model.rentals.RentalFactory;
import model.shelves.MainShelf;
import model.shelves.NormalShelf;
import model.shelves.Shelf;
import model.shelves.Shelves;
import model.shelves.criteria.Criteria;
import model.shelves.criteria.RecentlyAddedCriteria;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.DatabaseConnection;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.operation.Operation;

/**
 * testes de integracao aos metodos da classe Shelves. 
 * Pretende-se testar se a classe funciona bem em interacao com as 
 * classes NormalShelf, SmartSelf e Library.
 * 
 * Nao testamos metodos private
 */
public class Shelves_IntegrationTests {

	private static DbSetupTracker dbSetupTracker;
	private static DbSetup dbSetup;
	private static EntityManagerFactory emf;


	private MainShelf mainShelf;
	private Shelves shelves;

	private Rental rental;
	private Lendable lendable;

	private Criteria criteria;


	@BeforeClass
	public static void setup() {

		//ONLY SOLUTION THAT SEEM TO WORK
		//turning the BLOB from the Database into byte array
		//byte arrays can be inserted in the DbSetup query 
		//tirar o bin da BD e converter o tipo para byte arrays  
		byte[] lendableFileValue1 = javax.xml.bind.DatatypeConverter.parseHexBinary("aced00057372000c6a6176612e696f2e46696c65042da4450e0de4ff0300014c0004706174687400124c6a6176612f6c616e672f537472696e673b787074001e2f55736572732f4d446f2f4465736b746f702f6861636b6572312e6a70677702002f78");
		byte[] lendableAtributesValue1 = javax.xml.bind.DatatypeConverter.parseHexBinary("aced0005737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c770800000010000000087e7200166d6f64656c2e454d656469756d41747472696275746500000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400055449544c457372001c6d6f64656c2e454d656469756d50726f706572747957726170706572bace358631c379db0200014c000870726f70657274797400124c6a6176612f6c616e672f4f626a6563743b7870740004746869737e71007e000274000b4d454449554d5f545950457371007e0006740005496d6167657e71007e0002740006415554484f527371007e0006740004746861747e71007e0002740004504154487371007e000674001e2f55736572732f4d446f2f4465736b746f702f6861636b6572312e6a70677e71007e00027400084c4943454e5345537371007e0006737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000027e71007e00027400084c414e47554147457371007e00067400007e71007e00027400094d494d455f545950457371007e0006740009696d6167652f6a70677e71007e0002740004544147537371007e0006737200146a6176612e7574696c2e4c696e6b65644c6973740c29535d4a60882203000078707704000000007878");

		byte[] lendableFileValue2 = javax.xml.bind.DatatypeConverter.parseHexBinary("aced00057372000c6a6176612e696f2e46696c65042da4450e0de4ff0300014c0004706174687400124c6a6176612f6c616e672f537472696e673b787074006b2f55736572732f4d446f2f4465736b746f702f47616d652044657369676e20576f726b73686f702d4120706c617963656e7472696320617070726f61636820746f206372656174696e6720696e6e6f7661746976652067616d65732d326e642045646974696f6e2e7064667702002f78");
		byte[] lendableAtributesValue2 = javax.xml.bind.DatatypeConverter.parseHexBinary("aced0005737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c770800000010000000087e7200166d6f64656c2e454d656469756d41747472696275746500000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400055449544c457372001c6d6f64656c2e454d656469756d50726f706572747957726170706572bace358631c379db0200014c000870726f70657274797400124c6a6176612f6c616e672f4f626a6563743b787074000567616d65737e71007e000274000b4d454449554d5f545950457371007e0006740008446f63756d656e747e71007e0002740006415554484f527371007e0006740007646f47616d65737e71007e0002740004504154487371007e000674006b2f55736572732f4d446f2f4465736b746f702f47616d652044657369676e20576f726b73686f702d4120706c617963656e7472696320617070726f61636820746f206372656174696e6720696e6e6f7661746976652067616d65732d326e642045646974696f6e2e7064667e71007e00027400084c4943454e5345537371007e0006737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000057e71007e00027400084c414e47554147457371007e00067400007e71007e00027400094d494d455f545950457371007e000674000f6170706c69636174696f6e2f7064667e71007e0002740004544147537371007e0006737200146a6176612e7574696c2e4c696e6b65644c6973740c29535d4a60882203000078707704000000007878");

		byte[] lendableFileValue3 = javax.xml.bind.DatatypeConverter.parseHexBinary("aced00057372000c6a6176612e696f2e46696c65042da4450e0de4ff0300014c0004706174687400124c6a6176612f6c616e672f537472696e673b787074003c2f55736572732f4d446f2f4465736b746f702f507974686f6e20666f7220536563726574204167656e7473205b5061636b5420323031345d2e7064667702002f78");
		byte[] lendableAtributesValue3 = javax.xml.bind.DatatypeConverter.parseHexBinary("aced0005737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c770800000010000000087e7200166d6f64656c2e454d656469756d41747472696275746500000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400055449544c457372001c6d6f64656c2e454d656469756d50726f706572747957726170706572bace358631c379db0200014c000870726f70657274797400124c6a6176612f6c616e672f4f626a6563743b7870740006707974686f6e7e71007e000274000b4d454449554d5f545950457371007e0006740008446f63756d656e747e71007e0002740006415554484f527371007e000674000a626f6f6b206175746f727e71007e0002740004504154487371007e000674003c2f55736572732f4d446f2f4465736b746f702f507974686f6e20666f7220536563726574204167656e7473205b5061636b5420323031345d2e7064667e71007e00027400084c4943454e5345537371007e0006737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000027e71007e00027400084c414e47554147457371007e00067400007e71007e00027400094d494d455f545950457371007e000674000f6170706c69636174696f6e2f7064667e71007e0002740004544147537371007e0006737200146a6176612e7574696c2e4c696e6b65644c6973740c29535d4a60882203000078707704000000007878");


		Operation operations = sequenceOf(	
				//apaga do primeiro para o ultimo 
				//cuidado com a ordem por causa das chaves estrangueiras
				deleteAllFrom("Page_ANNOTATIONS","PAGE","Rental_ANNOTATIONS","SHELF_RENTALS","RENTAL","LENDABLE","NORMALSHELF","SMARTSHELF" ),
				//deleteAllFrom("SHELF_RENTALS","RENTAL","NORMALSHELF","SMARTSHELF"),
				insertInto("LENDABLE").
				columns("ID", "DATE_ADDED", "FILE", "TYPE", "ATTRIBUTES").
				//inserting binary strings by hand
				//Does not work because DbSetup doesn't work with blobs
				values(1, "2015-05-09 14:04:06", lendableFileValue1 ,null, lendableAtributesValue1).
				values(2, "2015-05-09 14:04:32", lendableFileValue2 ,null, lendableAtributesValue2).
				values(3, "2015-05-09 14:05:03", lendableFileValue3 ,null, lendableAtributesValue3).
				build(),
				insertInto("NORMALSHELF").
				columns("ID", "NAME").
				values( 1, "livros_informatica" ).
				values( 2, "livros_escultura" ).
				values( 3, "livros_BD" ).build(),
				insertInto("RENTAL").
				columns("ID", "TYPE", "EXPIRED", "ISRENTED", "DATE_RENTED", "LENDABLE_ID", "lastPageVisited").
				values( 1, "Rental", 0, 1, "2015-05-09 14:04:36", 1, null).
				values( 2, "Rental", 0, 1, "2015-05-09 14:05:07", 3, null).build(),
				insertInto("SHELF_RENTALS").
				columns("NormalShelf_ID", "rentals_ID", "Rental").
				values(1, 2, 2).build()
				);


		dbSetup = new DbSetup(DatabaseConnection.DESTINATION, operations);
		dbSetupTracker = new DbSetupTracker();
		dbSetupTracker.launchIfNecessary(dbSetup);

		emf = Persistence.createEntityManagerFactory("CommunityShareSystem");
	}

	@AfterClass
	public static void tearDown() {
		emf.close();
	}

	@Before
	public void prepare() {
		//nao sabemos a ordem dos testes e 
		//este comando corre de novo os commandos que apagam a db
		//Usar o metodo dbSetupTracker.skipNextLaunch(); para não correr o setup 
		// nos metodos que nao modificam a BD
		dbSetupTracker.launchIfNecessary(dbSetup);

		mainShelf = new MainShelf( "Use for tests" );
		shelves = new Shelves( mainShelf );

		////////////////////////////////////////////////////////////
		//create one of the strings in the ENUM EMediumType 
		//nao pode ser um Document por causa da persistencia da class Pages
		//Ou enviar null no construtor do Lendable
		String eMediumTypeString = "Image";
		//instantiate EMediumType  
		EMediumType type = EMediumType.valueOf(eMediumTypeString.toUpperCase());

		//instantiate EMediumType EMediumPropertiesData
		EMediumPropertiesData properties = new EMediumPropertiesData();
		//fill some attributes PATH and LICENSES is mandatory
		properties.addAttribute( EMediumAttribute.PATH, "SOME FILE PATH");
		properties.addAttribute( EMediumAttribute.TITLE, "css");
		properties.addAttribute( EMediumAttribute.MIME_TYPE, null);
		properties.addAttribute( EMediumAttribute.AUTHOR, "Marcio Domingues");
		properties.addAttribute( EMediumAttribute.LICENSES, 10);
		
		//criar um Lendable
		lendable = new Lendable( type,  properties);
		//criar um Rental
		rental = new Rental ( lendable );


	}

	////////////////////////////////////////////////////////////
	// TEST METHODS THAT INTERACTION WITH SHELFS
	@Test
	public void getShelfSuccess() {
		dbSetupTracker.skipNextLaunch();

		String shelfName = "livros_informatica";

		Shelf s = shelves.getShelf( shelfName );
		assertEquals(shelfName, s.getName());
	}
	
	@Test(expected=NullPointerException.class) 
	public void getShelfDoesntExist() {
		String shelfName = "livros_medicos";

		Shelf s = shelves.getShelf( shelfName );
		assertEquals(shelfName, s.getName());
	}
	
	@Test
	public void addNormalShelfSuccess( ) {	
		String shelfName = "comics";

		shelves.addNormalShelf( shelfName );
		Shelf s = shelves.getShelf( shelfName );

		assertEquals(shelfName, s.getName());		 	
	}

	@Test
	public void addSmartShelfSuccess( ) {
		String shelfName = "Recent";
		criteria = new RecentlyAddedCriteria(1000 * 60); 

		shelves.addSmartShelf("Recent", criteria);
		Shelf s = shelves.getShelf( shelfName );

		assertEquals(shelfName, s.getName());	
	}

	@Test
	//tentar criar catalogos para interagir com as classes que preciso
	public void removeShelfSuccess( ) {
		String shelfName = "livros_BD";

		Shelf sTry = shelves.getShelf( shelfName );

		shelves.removeShelf( shelfName );
		Shelf sResult = shelves.getShelf( shelfName );

		assertNotEquals( sTry, sResult );
	}
	////////////////////////////////////////////////////////////
	// END SHELF METHODS


	////////////////////////////////////////////////////////////
	// TEST METHODS THAT INTERACTION WITH LIBRARY AND RENTALS

	@Test
	public void removeRentalFromShelfSuccess() throws OperationNotSupportedException{
		
		String shelfName = "livros_informatica";
		String bookName = "python";

		Shelf sBefore =  null;
		Shelf sAfter =  null;

		Rental rentalBefore = null;
		Rental rentalAfter = null; 

		sBefore = shelves.getShelf( shelfName );
		//gets a iterator with all the rentals
		//finds the one we want by name
		Iterator<Rental> rental_Itr_before= sBefore.iterator();
		while(rental_Itr_before.hasNext()) {
			Rental element = rental_Itr_before.next();
			//System.out.println("RENTALS IN BEFORE  " + sBefore.getName() +" <_> "+ element.getTitle() + " ID " + element.getID());
			if( element.getTitle().equals( bookName )){
				rentalBefore=element;
				//System.out.println( rentalBefore.getID()  );
			}
		}

		//Deletes that one
		shelves.removeRentalFromShelf( sBefore.getName(), rentalBefore);

		
		sAfter = shelves.getShelf( shelfName );
		//gets a iterator with all the rentals
		//finds the one we want by name
		Iterator<Rental> rental_Itr_after= sAfter.iterator();
		while(rental_Itr_after.hasNext()) {
			Rental element = rental_Itr_after.next();
			//System.out.println("RENTALS IN AFTER " + sAfter.getName() +" <_> "+ element.getTitle() + " ID " + element.getID());
			if( element.getTitle().equals( bookName )){
				rentalAfter=element;
				//System.out.println( rentalAfter.getID()  );
			}
		}

		//checks if the shelf as the same state
		//after delete
		assertNotEquals( rentalBefore, rentalAfter );

	}

	
	@Test
	public void removeRentalFromShelf_AddedBookSuccess() throws OperationNotSupportedException{
		
		String shelfName = "livros_escultura";

		Shelf sBefore =  null;
		Shelf sAfter =  null;

		Rental rentalBefore = null;
		Rental rentalAfter = null; 
		
		shelves.addRentalToShelf(shelfName, rental);
		
		//gets a iterator with all the shelves
		//finds the one we want by name
		sBefore = shelves.getShelf( shelfName );

		//gets a iterator with all the rentals
		//finds the one we want by name
		Iterator<Rental> rental_Itr_before= sBefore.iterator();
		while(rental_Itr_before.hasNext()) {
			Rental element = rental_Itr_before.next();
			if( element.getTitle().equals(  rental.getTitle() )){
				rentalBefore=element;
			}
		}

		//Deletes that one
		shelves.removeRentalFromShelf( sBefore.getName(), rentalBefore);

		sAfter = shelves.getShelf( shelfName );

		//gets a iterator with all the rentals
		//finds the one we want by name
		Iterator<Rental> rental_Itr_after= sAfter.iterator();
		while(rental_Itr_after.hasNext()) {
			Rental element = rental_Itr_after.next();
			if( element.getTitle().equals(  rental.getTitle() )){
				rentalAfter=element;
			}
		}

		//checks if the shelf as the same state
		//after delete
		assertNotEquals( rentalBefore, rentalAfter );

	}

	
//	@Test(expected=NullPointerException.class)
//	public void removeRentalFromShelfBookNotInShelf() throws OperationNotSupportedException{
//		dbSetupTracker.skipNextLaunch();
//		
//		Shelf sTry = shelves.getShelf( "livros_informatica" );
//		Rental rtry = null;
//		
//		Iterator<Rental> itr = sTry.iterator();;
//		while(itr.hasNext()) {
//			Rental element = itr.next();
//			System.out.println(element.getTitle() + " " + element.getAuthor() );
//			rtry = element;
//		}
//		
//		//Deletes that one
//		shelves.removeRentalFromShelf( sTry.getName(), rtry);
//		
//		shelves.removeRentalFromShelf( sTry.getName(), rtry);
//	}


	@Test
	public void getShelfRentalsSucess(){
		dbSetupTracker.skipNextLaunch();

		String shelfName = "livros_informatica";
		Shelf s = shelves.getShelf( shelfName );

		assertEquals(shelfName, s.getName());
	}


	@Test
	public void addRentalToShelfSucess() throws OperationNotSupportedException{		
		Rental rentalRetrived=null;

		String shelfName = "livros_escultura";

		//NormalShelf shelf = (NormalShelf) shelves.getShelf( shelfName );
		//shelf.addRental(rental);
		boolean added = shelves.addRentalToShelf(shelfName, rental);

		Shelf shelf = shelves.getShelf( shelfName );

		Iterator<Rental> itr = shelf.iterator();
		while(itr.hasNext()) {
			Rental element = itr.next();
			//System.out.println("RENTAL teste 1 " +element.getTitle() + " " + element.getAuthor() );
			if( element.getTitle().equals( rental.getTitle())){
				//System.out.println("PING" );
				rentalRetrived = element;
			}

		}
		assertEquals( rental.getLendable(), rentalRetrived.getLendable());
	}


	@Test(expected=NullPointerException.class) 
	public void addRentalToShelf_ShelfDoesntExist() throws OperationNotSupportedException{

		String shelfName = "livros";
		boolean added = shelves.addRentalToShelf(shelfName, rental);
	}

	////////////////////////////////////////////////////////////
	//END LIBRARY AND RENTALS METHODS
}

