package model.shelves;

import java.util.Iterator;

import model.events.RentalCollectionEvent;
import model.rentals.Rental;
import model.transactions.DBHelper;

public class MainShelf extends NormalShelf {
	
	public MainShelf(String name) {
		super(name);
	}
	
	
	/*
	 * Once being the MainShelf, it its additionally responsible to
	 * persist the added Rental if it must be added.
	 * 
	 * @see model.shelves.NormalShelf#addRental(model.rentals.Rental)
	 */
	@Override
	public boolean addRental (Rental rental) {
		//gets the correspondent rental (if exists)
		Rental thisRental = getThisRental(rental);
		//if doesnt exist, stores it
		if ( thisRental == null ) {
			DBHelper.INSTANCE.store(rental);
		}
		//otherwise, renews thisRental and updates it
		else {
			thisRental.renew();
			DBHelper.INSTANCE.update(thisRental);
		}
		
		super.fireRentalAdded (new RentalCollectionEvent (rental));	
		return true;
	}
	
	public Iterable<Rental> getAllRentals(){
		return DBHelper.INSTANCE.get(Rental.FIND_ALL, null, null, Rental.class);
	}
	
	@Override
	public Iterator<Rental> iterator() {
		return getAllRentals().iterator();
	}
	
	public void returnRental(Rental rental) {
		Rental thisRental = getThisRental(rental);
		thisRental.returnRental();
		DBHelper.INSTANCE.update(thisRental);
	}
	
	public boolean isRented(Rental rental) {
		Rental thisRental = getThisRental(rental);
		return thisRental != null && thisRental.isRented();
	}
	
	/*
	 * Once being the MainShelf, it its additionally responsible to
	 * persist, by deleting, the given Rental.
	 * 
	 * @see model.shelves.NormalShelf#removeRental(model.rentals.Rental)
	 */
	public boolean removeRental (Rental rental) {
		// never happens
		return false;
	}
	
	
	private Rental getThisRental(Rental rental) {
		return getRentalBy(rental, Rental.FIND_LENDABLE, rental.getLendable());
	}
	
	private Rental getRentalBy(Rental rental,String paramName, Object paramValue) {
		return DBHelper.INSTANCE.getSingle(Rental.FIND_BY_LENDABLE, paramName, paramValue, Rental.class);
	}
	

}
