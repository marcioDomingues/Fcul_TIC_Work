//package model.shelves;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import javax.naming.OperationNotSupportedException;
//import javax.swing.event.EventListenerList;
//
//import model.events.EMediaCollectionListener;
//import model.events.ShelfCollectionEvent;
//import model.events.ShelfCollectionListener;
//import model.rentals.Rental;
//import model.shelves.criteria.Criterion;
//
//public class Shelves implements Iterable<Shelf> {
//
//	private Map<String, Shelf> shelves;
//	private NormalShelf myRentals;
//	
//	private EventListenerList listeners;
//	
//	public Shelves (NormalShelf myRentals) {
//		shelves = new HashMap<String, Shelf> ();
//		this.myRentals = myRentals; 
//		listeners = new EventListenerList();
//	}
//
//	public boolean addNormalShelf(String name) {
//		if (shelves.get(name) == null) {	
//			NormalShelf shelf = new NormalShelf (name);
//			myRentals.addRentalCollectionListener(shelf);
//			shelves.put(name, shelf);
//			fireShelfAdded(new ShelfCollectionEvent(name));
//			return true;
//		} else
//			return false;
//	}
//	
//	public boolean isTheRentalShelf (String name) {
//		return myRentals.getName().equals(name);
//	}
//	
//	public boolean addSmartShelf (String name, Criterion criteria) {
//		if (shelves.get(name) == null) {	
//			SmartShelf shelf = new SmartShelf (name, myRentals, criteria);
//			myRentals.addRentalCollectionListener(shelf);
//			shelves.put(name, shelf);
//			fireShelfAdded(new ShelfCollectionEvent(name));
//			return true;
//		} else
//			return false;
//	}
//
//	@Override
//	public Iterator<Shelf> iterator() {
//		return shelves.values().iterator();
//	}
//
//	public void removeShelf(String name) {
//		Shelf shelf = shelves.get(name);
//		myRentals.removeRentalCollectionListener(shelf);
//		shelves.remove(name);
//		fireShelfRemoved(new ShelfCollectionEvent(name));
//	}
//
//	public void removeRentalFromShelf(String name, Rental rental) throws OperationNotSupportedException {
//		Shelf shelf = shelves.get(name);
//		shelf.removeRental (rental);
//	}
//	
//	public Iterable<Rental> getShelfRentals (String shelfName) {
//		return shelves.get(shelfName);
//	}
//
//	public Iterable<Rental> getRentals () {
//		return myRentals;
//	}
//
//	
//	public boolean addRentalToShelf(String target, Rental rental) throws OperationNotSupportedException {
//		Shelf shelf = shelves.get(target);
//		return shelf.addRental(rental);
//	}
//	
//	public boolean addRental(Rental rental) {
//		return myRentals.addRental(rental);
//	}
//	
//	public void addShelfCollectionListener(ShelfCollectionListener listener) {
//        listeners.add(ShelfCollectionListener.class, listener);
//    }
//
//    public void removeShelfCollectionListener(ShelfCollectionListener listener) {
//        listeners.remove(ShelfCollectionListener.class, listener);
//    }
//    
//    public void addRentalCollectionListener(String shelfName, EMediaCollectionListener listener) {
//    	Shelf shelf = shelves.get(shelfName);
//    	shelf.removeRentalCollectionListener(listener);
//    }
//
//    public void removeRentalCollectionListener(String shelfName, EMediaCollectionListener listener) {
//    	Shelf shelf = shelves.get(shelfName);
//    	shelf.removeRentalCollectionListener(listener);
//    }
//    
//    private void fireShelfAdded(ShelfCollectionEvent event) {
//    	for (ShelfCollectionListener listener : listeners.getListeners(ShelfCollectionListener.class))
//    		listener.shelfAdded(event);
//    }
//
//    private void fireShelfRemoved(ShelfCollectionEvent event) {
//    	for (ShelfCollectionListener listener : listeners.getListeners(ShelfCollectionListener.class))
//    		listener.shelfRemoved(event);
//    }
//
//	public boolean isRented(Rental rental) {
//		return myRentals.contains(rental);
//	}
//
//	public boolean isExpired(Rental rental) {
//		return myRentals.isExpired(rental);
//	}
//}
