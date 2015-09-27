package local_domain.model;

import local_domain.model.lendables.Library;
import local_domain.model.rentals.RentalFactory;
//import model.shelves.NormalShelf;
//import model.shelves.Shelves;

public class CSS {

	private Library library;
	//private Shelves shelves;
	
	public final ShelvesFacade shelvesHandler;
	public final LibraryFacade libraryHandler;
	
	public CSS() {
		library = new Library ();
		//shelves = new Shelves (new NormalShelf("My Rentals"));
		//shelvesHandler = new ShelvesFacade(shelves, new RentalFactory());
		shelvesHandler = new ShelvesFacade( new RentalFactory() );
		libraryHandler = new LibraryFacade(library);
	}
}

