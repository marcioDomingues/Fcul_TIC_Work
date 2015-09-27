package model.shelves;

import java.util.Iterator;
import java.util.LinkedList;

import javax.naming.OperationNotSupportedException;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import model.events.RentalCollectionEvent;
import model.rentals.Rental;
import model.shelves.criteria.Criteria;
import model.transactions.DBHelper;

@Entity
public class SmartShelf extends Shelf {
	
	@JoinColumn (name="CRITERIA_ID")
	private Criteria criteria;
	
	public SmartShelf(){};
	
	public SmartShelf(String name, Shelf myRentals, Criteria criteria) {
		super(name);
		this.criteria = criteria;
	}

	@Override
	public Iterator<Rental> iterator() {
		LinkedList<Rental> result = new LinkedList<Rental> ();
		for (Rental r : DBHelper.INSTANCE.get(Rental.FIND_ALL, null, null, Rental.class))
			if (criteria.satisfies(r))
				result.add(r);
		return result.iterator();
	}

	@Override
	public boolean addRental(Rental rental)
			throws OperationNotSupportedException {
		throw new OperationNotSupportedException ();
	}

	@Override
	public boolean removeRental(Rental rental)
			throws OperationNotSupportedException {
		throw new OperationNotSupportedException ();
	}

	@Override
	public void RentalAdded(RentalCollectionEvent event) {
		// nothing to be done
	}

	@Override
	public void RentalRemoved(RentalCollectionEvent event) {
		// nothing to be done
	}

}
