package local_domain.css;

import java.util.LinkedList;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import local_domain.delegates.BookshelfUIDelegate;
import local_domain.model.EMedium;
import local_domain.model.EMediumPropertiesData;
import local_domain.model.EMediumType;
import local_domain.model.LibraryFacade;
import local_domain.model.ShelvesFacade;
import local_domain.model.events.RentalCollectionEvent;
import local_domain.model.events.ShelfCollectionEvent;
import local_domain.model.lendables.Lendable;
import local_domain.model.rentals.Rental;

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
		//return shelvesHandler.getShelfRentals(selectedShelf);
		return null;
	}
	
	@Override
	public Iterable<EMedium> getRentals() {
		
		List <EMedium> l = new LinkedList<EMedium>();
		//return shelvesHandler.getRentals();
		return l;
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
		//shelvesHandler.removeRental(shelfName, (Rental) eMedium);
		removeEMediumFromPanel (eMedium);
	}
	
	@Override
	public Iterable<String> getShelves() {
		return shelvesHandler.getShelves();
	}

	
	@Override
	public boolean addRentalEMedium(EMedium eMedium) {
		boolean result = libraryHandler.canBeRent(eMedium);
		//boolean result1 = shelvesHandler.isRented(eMedium);
		if (true) {
			libraryHandler.rent(eMedium);
			return true;// shelvesHandler.addRental(eMedium);
		}
		return false;
	}

	
	@Override
	public boolean addEMediumShelf(String shelfName, EMedium eMedium) throws OperationNotSupportedException {
		//return shelvesHandler.addShelfRental(shelfName, (Rental) eMedium);
		return false;
	}
		

	@Override
	public String getEMediumTitle(EMedium d) {
		return d.getTitle();
	}
	
	@Override
	public void RentalAdded(RentalCollectionEvent event) {
		addToEMediaPanel("MyRentals", (Lendable) event.getSource());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revokeLending(EMedium eMedium) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addEMediumLibrary(String type, EMediumPropertiesData lendableProperties) {
		boolean result = libraryHandler.addLendable(EMediumType.valueOf(type.toUpperCase()), lendableProperties);
		if (result)
			addToEMediaPanel("Library", libraryHandler.getLastAddedLendable());
		return result;
	}

	@Override
	public boolean canBeViewed(EMedium eMedium) {
//		boolean isRented = shelvesHandler.isRented(eMedium);
//		boolean isExpired = true;
//		// Needs to check to honor the contract
//		if (isRented)
//			isExpired = shelvesHandler.isRentalExpired(eMedium);
//		return isRented && !isExpired;
		return false;
	}

	@Override
	public boolean isRented(EMedium eMedium) {
		//return shelvesHandler.isRented(eMedium);
		return false;
	}

	@Override
	public void shelfSelected(String selectedShelfName) {
		// Do nothing		
	}

}
