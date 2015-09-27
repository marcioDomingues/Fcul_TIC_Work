package model;

import model.lendables.Library;
import model.rentals.RentalFactory;
import model.shelves.MainShelf;
import model.shelves.Shelves;

public class CSS {

	private Library library;
	private Shelves shelves;
	
	public final ShelvesFacade shelvesHandler;
	public final LibraryFacade libraryHandler;
	
	public CSS() {
		library = new Library ();
		shelves = new Shelves (new MainShelf("My Rentals"));
		shelvesHandler = new ShelvesFacade(shelves, new RentalFactory());
		libraryHandler = new LibraryFacade(library);
	}
}
