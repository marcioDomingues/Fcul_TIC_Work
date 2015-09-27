package model.shelves;

import java.util.Iterator;

import javax.naming.OperationNotSupportedException;
import javax.swing.event.EventListenerList;

import model.events.EMediaCollectionListener;
import model.events.ShelfCollectionEvent;
import model.events.ShelfCollectionListener;
import model.rentals.Rental;
import model.shelves.criteria.Criteria;
import model.shelves.criteria.RecentlyAddedCriteria;
import model.transactions.DBHelper;

/**
 * Catálogo de Shelves
 * 
 * @author mata
 *
 */
public class Shelves implements Iterable<Shelf> {

	// thy MainShelf
	private MainShelf myRentals;
	// event listeners
	private EventListenerList listeners;
	
	public Shelves() {};

	public Shelves(MainShelf myRentals) {
		// initializes the listeners
		listeners = new EventListenerList();
		// initializes the MainShelf of these Shelves
		this.myRentals = myRentals;
		//this is a default smart shelf
		addSmartShelf("Recently Added", new RecentlyAddedCriteria(1000 * 60));
	}

	/**
	 * get shelf by name
	 * 
	 * @param name
	 *            The shelf's name
	 */
	public Shelf getShelf(String name) {
		return getShelfByName(name);
	}

	/**
	 * Adds a new normal shelf
	 * 
	 * @param name
	 *            The shelf's name
	 */
	public boolean addNormalShelf(String name) {

		// flag to return
		boolean wasAdded = false;
		// creates the candidate to be added
		final Shelf shelfToAdd = new NormalShelf(name);
		// checks existence and tries do add it
		if (!existsShelf(name) && DBHelper.INSTANCE.store(shelfToAdd)) {
			myRentals.addRentalCollectionListener(shelfToAdd);
			fireShelfAdded(new ShelfCollectionEvent(name));
			wasAdded = true;
		}
		return wasAdded;
	}

	public void returnRental(Rental rental) {
		myRentals.returnRental(rental);
	}

	public boolean isTheRentalShelf(String name) {
		return myRentals.getName().equals(name);
	}

	/**
	 * Adds a new smart shelf
	 * 
	 * @param name
	 *            The shelf's name
	 * @param criteria
	 *            The shelf's Criterion
	 */
	public boolean addSmartShelf(String name, Criteria criteria) {
		//only goes forward if ! exists
		if ( ! existsShelf(name) ) {
			//if doesnt exists this criteria, stores it
			//if ( ! existsCriteria(criteria) )
				DBHelper.INSTANCE.store(criteria);
			
			criteria = getThisCriteria(criteria);
	
			//lets go save this
			SmartShelf iShelf = new SmartShelf(name, myRentals, criteria);
			myRentals.addRentalCollectionListener(iShelf);
			DBHelper.INSTANCE.store(iShelf); 
			fireShelfAdded(new ShelfCollectionEvent(name));
			return true;
		} else
			return false;
	}

	@Override
	public Iterator<Shelf> iterator() {
		return getAllShelves().iterator();
	}

	public void removeShelf(String name) {
		Shelf shelf = getShelfByName(name);
		myRentals.removeRentalCollectionListener(shelf);
		fireShelfRemoved(new ShelfCollectionEvent(name));
		DBHelper.INSTANCE.remove(shelf);
	}

	public void removeRentalFromShelf(String name, Rental rental)
			throws OperationNotSupportedException {
		Shelf shelf = getShelfByName(name);
		shelf.removeRental(rental);
		DBHelper.INSTANCE.update(shelf);
	}

	public Iterable<Rental> getShelfRentals(String shelfName) {
		return getShelfByName(shelfName);
	}

	public Iterable<Rental> getRentals() {
		return myRentals.getAllRentals();
	}

	public boolean addRentalToShelf(String targetShelf, Rental rental)
			throws OperationNotSupportedException {
		Shelf shelf = getShelfByName(targetShelf);
		boolean wasAdded = shelf.addRental(rental);
		DBHelper.INSTANCE.update(shelf);

		return wasAdded;
	}

	public boolean addRental(final Rental rental) {
		return myRentals.addRental(rental);
	}

	public void addShelfCollectionListener(ShelfCollectionListener listener) {
		listeners.add(ShelfCollectionListener.class, listener);
	}

	public void removeShelfCollectionListener(ShelfCollectionListener listener) {
		listeners.remove(ShelfCollectionListener.class, listener);
	}

	public void addRentalCollectionListener(String shelfName,
			EMediaCollectionListener listener) {
		Shelf shelf = getShelfByName(shelfName);
		shelf.removeRentalCollectionListener(listener);
	}

	public void removeRentalCollectionListener(String shelfName,
			EMediaCollectionListener listener) {
		Shelf shelf = getShelfByName(shelfName);
		shelf.removeRentalCollectionListener(listener);
	}

	private void fireShelfAdded(ShelfCollectionEvent event) {
		for (ShelfCollectionListener listener : listeners
				.getListeners(ShelfCollectionListener.class))
			listener.shelfAdded(event);
	}

	private void fireShelfRemoved(ShelfCollectionEvent event) {
		for (ShelfCollectionListener listener : listeners
				.getListeners(ShelfCollectionListener.class))
			listener.shelfRemoved(event);
	}

	public boolean isRented(Rental rental) {
		return myRentals.isRented(rental);
	}

	public boolean isExpired(Rental rental) {
		return myRentals.isExpired(rental);
	}

	/* utils */
	private Iterable<Shelf> getAllShelves() {
		return DBHelper.INSTANCE.get(Shelf.FIND_ALL, null, null, Shelf.class);
	}

	private Shelf getShelfByName(String name) {
		return DBHelper.INSTANCE.getSingle(Shelf.FIND_BY_NAME, Shelf.FIND_NAME,
				name, Shelf.class);
	}

	private boolean existsShelf(String name) {
		return DBHelper.INSTANCE.contains(Shelf.FIND_BY_NAME, Shelf.FIND_NAME, name,
				Shelf.class);
	}
	
	private boolean existsCriteria(Criteria criteria) {
		return DBHelper.INSTANCE.contains(Criteria.FIND_BY_TAG, Criteria.FIND_TAG, criteria.getTag(),
				Criteria.class);
	}
	
	private Criteria getThisCriteria(Criteria criteria) {
		return DBHelper.INSTANCE.getSingle(Criteria.FIND_BY_TAG, Criteria.FIND_TAG, criteria.getTag(),
				Criteria.class);
	}
	
}
