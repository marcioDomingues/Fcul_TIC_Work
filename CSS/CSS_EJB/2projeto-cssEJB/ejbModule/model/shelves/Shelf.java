package model.shelves;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import model.lendables.Lendable;
import model.rentals.Rental;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@NamedQueries({
	@NamedQuery(name = Shelf.FIND_BY_ID, query = "SELECT s FROM Shelf s WHERE s.id = :" + Shelf.ID),
	@NamedQuery(name = Shelf.FIND_ALL, query = "SELECT s FROM Shelf s"),
	@NamedQuery(name = Shelf.FIND_BY_NAME, query = "SELECT s FROM Shelf s WHERE s.name = :" + Shelf.NAME),
	@NamedQuery(name = Shelf.FIND_ALL_BUT_MYRENTALS, query = "SELECT s FROM Shelf s where s.name <> :" + Shelf.NAME)
})

abstract class Shelf implements Iterable<Rental> {

	public static final String ID = "ID";
	public static final String NAME = "NAME";
	public static final String FIND_BY_ID = "Shelf.findById";
	public static final String FIND_ALL = "Shelf.findAll";
	public static final String FIND_BY_NAME = "Shelf.findByName";
	public static final String FIND_ALL_BUT_MYRENTALS = "Shelf.findAllButMyRentals";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(nullable=false)
	private String name;
	
	Shelf() {
	}
	
	public Shelf (String name) {
		this.name = name;
	}
	
	public String getName () {
		return name;
	}
	
	@Override
	public String toString () {
		return getClass().getSimpleName() + "[" + name + "]";
	}
	
	public void addRental(Rental rental) {
		throw new UnsupportedOperationException ("not supported");
	}

	public boolean removeRental(Rental rental) {
		throw new UnsupportedOperationException ();
	}
	
	public boolean returnRental(Rental rental) {
		throw new UnsupportedOperationException ();
	}

	public Boolean hasNonExpiredRental(Lendable lendable) {
		throw new UnsupportedOperationException ();
	}

	public boolean returnRentalForLendable(Lendable lendable) {
		throw new UnsupportedOperationException ();
	}

}
