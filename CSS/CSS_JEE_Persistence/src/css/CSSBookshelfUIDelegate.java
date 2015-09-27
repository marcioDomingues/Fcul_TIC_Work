package css;

import javax.naming.OperationNotSupportedException;

import delegates.BookshelfUIDelegate;
import model.EMedium;
import model.EMediumPropertiesData;
import model.EMediumType;
import model.LibraryFacade;
import model.ShelvesFacade;
import model.events.RentalCollectionEvent;
import model.events.ShelfCollectionEvent;
import model.lendables.Lendable;
import model.rentals.Rental;

/**
 * The bookshelf ui delegate default implementation
 * 
 * @author fmartins
 *
 */
public class CSSBookshelfUIDelegate extends BookshelfUIDelegate {

	private ShelvesFacade shelvesHandler;
	private LibraryFacade libraryHandler;
	
	public CSSBookshelfUIDelegate(ShelvesFacade shelvesHandler,
			LibraryFacade libraryHandler) {
		this.shelvesHandler = shelvesHandler;
		this.libraryHandler = libraryHandler;
		shelvesHandler.addShelfCollectionListener(this);
	}

	@Override
	public Iterable<EMedium> getShelfRentals(String selectedShelf) {
		return shelvesHandler.getShelfRentals(selectedShelf);
	}
	
	@Override
	public Iterable<EMedium> getRentals() {
		return shelvesHandler.getRentals();
	}


	@Override
	public Iterable<EMedium> getLibraryEMedia() {
		return libraryHandler.getEMedia();
	}

	@Override
	public boolean addNormalShelf(String shelfName) {
		return shelvesHandler.addNormalShelf(shelfName);
	}
	
	@Override
	public boolean addSmartShelf(String shelfName, String criteria) {
		return shelvesHandler.addSmartShelf(shelfName, criteria);
	}

	@Override
	public void removeShelf(String shelfName) {
		shelvesHandler.removeShelf (shelfName);
	}

	@Override
	public void updateRental(EMedium document,
			EMediumPropertiesData documentProperties) {
		//libraryHandler.updateDocument (document, documentProperties);
	}

	@Override
	public void removeEMediumShelf(String shelfName, EMedium eMedium) throws OperationNotSupportedException {
		shelvesHandler.removeRental(shelfName, (Rental) eMedium);
		removeEMediumFromPanel (eMedium);
	}
	
	@Override
	public Iterable<String> getShelves() {
		return shelvesHandler.getShelves();
	}

	
	@Override
	public boolean addRentalEMedium(EMedium eMedium) {
		boolean result = libraryHandler.canBeRent(eMedium);
		boolean result1 = shelvesHandler.isRented(eMedium);

		if (result && !result1) {
			libraryHandler.rent(eMedium);
			return shelvesHandler.addRental(eMedium);
		}
		return false;
	}

	
	@Override
	public boolean addEMediumShelf(String shelfName, EMedium eMedium) throws OperationNotSupportedException {
		return shelvesHandler.addShelfRental(shelfName, (Rental) eMedium);
	}
		

	@Override
	public String getEMediumTitle(EMedium d) {
		return d.getTitle();
	}
	
	@Override
	public void RentalAdded(RentalCollectionEvent event) {
		addToEMediaPanel(AppProperties.INSTANCE.RENTALS_SHELF_NAME, (Lendable) event.getSource());
	}

	@Override
	public void RentalRemoved(RentalCollectionEvent event) {
		// do nothing
	}

	@Override
	public void shelfAdded(ShelfCollectionEvent event) {
		addShelfTreeNode((String) event.getSource());
	}

	@Override
	public void shelfRemoved(ShelfCollectionEvent event) {
		removeShelfTreeNode((String) event.getSource());
	}

	@Override
	public void returnRental(EMedium eMedia) {
		if ( shelvesHandler.isRented(eMedia) ) {
			//firstly, returns the Rental
			shelvesHandler.returnRental( eMedia);
			//and then the library gets the returnal of the item itself
			Lendable eItem = (Lendable) eMedia.getItem();
			libraryHandler.returnLendable(eItem);
		}
	}

	@Override
	public void revokeLending(EMedium eMedium) {
		// TODO Auto-generated method stub
		this.returnRental(eMedium);
	}

	@Override
	public boolean addEMediumLibrary(String type, EMediumPropertiesData lendableProperties) {
		boolean result = libraryHandler.addLendable(EMediumType.valueOf(type.toUpperCase()), lendableProperties);
		if (result)
			addToEMediaPanel(AppProperties.INSTANCE.LIBRARY_NAME, libraryHandler.getLastAddedLendable());
		return result;
	}

	@Override
	public boolean canBeViewed(EMedium eMedium) {
		return shelvesHandler.isRented(eMedium);
	}

	@Override
	public boolean isRented(EMedium eMedium) {
		return shelvesHandler.isRented(eMedium);
	}

	@Override
	public void shelfSelected(String selectedShelfName) {
		// Do nothing		
	}

}
