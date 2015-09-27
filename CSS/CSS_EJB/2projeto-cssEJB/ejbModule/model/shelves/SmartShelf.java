package model.shelves;

import java.util.Iterator;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import model.rentals.Rental;
import model.shelves.criteria.Criterion;

@Entity
class SmartShelf extends Shelf {

	@OneToOne(cascade=CascadeType.ALL)  @JoinColumn(nullable=false)
	private Criterion criteria;
	
	@ManyToOne @JoinColumn(nullable=false)
	private Shelf myRentals;
	
	SmartShelf() {
	}

	public SmartShelf(String name, Shelf myRentals, Criterion criteria) {
		super(name);
		this.myRentals = myRentals;
		this.criteria = criteria;
	}
	
	@Override
	public Iterator<Rental> iterator() {
		LinkedList<Rental> result = new LinkedList<Rental> ();
		for (Rental r : myRentals)
			if (criteria.satisfies(r))
				result.add(r);
		return result.iterator();
	}

}
