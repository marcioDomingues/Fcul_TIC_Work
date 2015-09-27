package local_domain.model;

import java.util.List;

import javax.swing.event.EventListenerList;

import local_domain.model.events.ShelfCollectionEvent;
import local_domain.model.events.ShelfCollectionListener;
import local_domain.model.rentals.RentalFactory;
//import model.shelves.Shelf;
//import model.shelves.Shelves;
//import model.shelves.criteria.Criterion;
import shelvesWS.Exception_Exception;
import shelvesWS.ShelvesRemoteWS;
import shelvesWS.ShelvesRemoteWSService;


public class ShelvesFacade {

	//webservices remote classes
	ShelvesRemoteWS shelvesFacadeWS = new ShelvesRemoteWSService().getShelvesRemoteWSPort();

	private EventListenerList listeners;

	
	//private Shelves shelves;
	private RentalFactory rentalFactory;
	
	

	public ShelvesFacade (/*Shelves shelves ,*/ RentalFactory rentalFactory) {
		//this.shelves = shelves;
		this.rentalFactory = rentalFactory;
		
		//TODO CRIAR UM MY RENTALS
		
		
		listeners = new EventListenerList();
	}

	public boolean addNormalShelf (String name) {
		boolean result = false;
		try {
			result = shelvesFacadeWS.addNormalShelf(name);
			if(result){
				//System.out.println("added");
				fireShelfAdded(new ShelfCollectionEvent(name));
			}
		} catch (Exception_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	//NOT IMPLEMENTED
	//public boolean addSmartShelf (String name, Criterion criteria) {
		//return shelves.addSmartShelf (name, criteria);
	//	return false;
	//}


	public Iterable<String> getShelves () {
		List<String> shelfs = null;
		try {
			shelfs = shelvesFacadeWS.getShelves();
		} catch (Exception_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return shelfs;
	}

	public boolean removeShelf(String name) {
		boolean result = false;
		try {
			result = shelvesFacadeWS.removeShelf(name);
			if(result){
				//System.out.println("removed");
				fireShelfRemoved(new ShelfCollectionEvent(name));
			}
		} catch (Exception_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	//	public void removeRental (String selfName, EMedium rental) throws OperationNotSupportedException {
	//		shelves.removeRentalFromShelf (selfName, (Rental) rental);
	//	}

	//	// pre: !isRented(rental)
	//	public boolean addRental(EMedium rental) {
	//		return shelves.addRental(rentalFactory.createRental(rental));
	//	}

	//	public boolean addShelfRental(String shelfName, EMedium rental) throws OperationNotSupportedException {
	//		return shelves.addRentalToShelf (shelfName, rentalFactory.createRental(rental));
	//	}

	//	public Iterable<EMedium> getShelfRentals (String shelfName) {
	//		LinkedList<EMedium> emedia = new LinkedList<EMedium> ();
	//		for (Rental r : shelves.getShelfRentals(shelfName))
	//			emedia.add(r);
	//		return emedia; 
	//	}

	//	public Iterable<EMedium> getRentals () {
	//		LinkedList<EMedium> emedia = new LinkedList<EMedium> ();
	//		for (Rental r : shelves.getRentals())
	//			emedia.add(r);
	//		return emedia; 
	//	}

	//	public boolean isRented(EMedium rental) {
	//		return shelves.isRented(rentalFactory.createRental(rental));
	//	}

	//	public boolean isRentalExpired(EMedium rental) {
	//		return shelves.isExpired(rentalFactory.createRental(rental));
	//	}		




	//listeners
	public void addShelfCollectionListener(ShelfCollectionListener listener) {
		listeners.add(ShelfCollectionListener.class, listener);
	}

	public void removeShelfCollectionListener(ShelfCollectionListener listener) {
		listeners.remove(ShelfCollectionListener.class, listener);
	}
	//	public void addRentalCollectionListener(String shelfName, EMediaCollectionListener listener) {
	//		shelves.addRentalCollectionListener(shelfName, listener);
	//	}
	//
	//	public void removeRentalCollectionListener(String shelfName, EMediaCollectionListener listener) {
	//		shelves.removeRentalCollectionListener(shelfName, listener);
	//	}

	
	private void fireShelfAdded(ShelfCollectionEvent event) {
		for (ShelfCollectionListener listener : listeners.getListeners(ShelfCollectionListener.class))
			listener.shelfAdded(event);
	}

	private void fireShelfRemoved(ShelfCollectionEvent event) {
		for (ShelfCollectionListener listener : listeners.getListeners(ShelfCollectionListener.class))
			listener.shelfRemoved(event);
	}


}
