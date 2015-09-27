package model.shelves;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import model.lendables.Lendable;
import model.rentals.Rental;

@Entity
class NormalShelf extends Shelf {

	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="NORMALSHELF_RENTALS")
	private Set<Rental> rentals;

	NormalShelf() {
	}
	
	public NormalShelf (String name) {
		super(name);
		rentals = new HashSet<>();
	}

	@Override
	public void addRental(Rental rental) {
		Lendable lendable = rental.getLendable();
		Rental myRental = getMyRental(lendable);
		if (myRental != null)
			myRental.renew();
		else
			rentals.add(rental);
	}
	
	@Override
	public boolean removeRental (Rental rental) {
		Rental myRental = getMyRental(rental.getLendable());
		return myRental != null ? rentals.remove(myRental) : false;
	}

	@Override
	public boolean returnRental(Rental rental) {
		return returnRentalForLendable(rental.getLendable());
	}
	
	@Override
	public boolean returnRentalForLendable(Lendable lendable) {
		Rental myRental = getMyRental(lendable);
		return myRental != null && !myRental.isExpired() ?
				myRental.returnToLibrary() : false;
	}

	private Rental getMyRental(Lendable lendable) {
		for (Rental aRental : rentals)
			if (aRental.getLendable().equals(lendable))
				return aRental;
		return null;
	}

	@Override
	public Iterator<Rental> iterator() {
		return rentals.iterator();
	}

	@Override
	public Boolean hasNonExpiredRental(Lendable lendable) {
		Rental myRental = getMyRental(lendable);
		return myRental != null ? !myRental.isExpired() : false;
	}

}
