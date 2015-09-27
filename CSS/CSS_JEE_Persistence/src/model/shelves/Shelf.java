package model.shelves;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import javax.naming.OperationNotSupportedException;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.event.EventListenerList;

import model.events.EMediaCollectionListener;
import model.rentals.Rental;


@Entity
@NamedQueries({
	 @NamedQuery(name = Shelf.FIND_ALL, query = "SELECT s FROM Shelf s"),
	 @NamedQuery(name=Shelf.FIND_BY_NAME, query="SELECT s FROM Shelf s WHERE s.name = :" + Shelf.FIND_NAME)
})
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Shelf implements Iterable<Rental>, 
					Comparable<Shelf>, EMediaCollectionListener {
	
	
	// Named query name constants
	public static final String FIND_ALL = "Shelf.findAll";
	public static final String FIND_BY_NAME = "Shelf.findByName";
	public static final String FIND_NAME = "NAME";
	
	/**
	 * The Shelf id. 
	 */
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE) 
	private int id;
	
	/**
	 * Shelf name
	 */
	@Column(nullable = false, unique = true)
	private String name;
	
	@Transient
	protected EventListenerList listeners;
	
	@PostLoad
	private void initialize() {
		listeners = new EventListenerList();
	}
	
	//constructor
	public Shelf(){};
	
	public Shelf (String name) {
		this.name = name;
		listeners = new EventListenerList();
	}
	
	// getters and setters
	/**
	 * @return The Shelf name
	 */
	public String getName () {
		return name;
	}
	
	@Override
	public int compareTo (Shelf other) {
		return name.compareTo(other.name);
	}

	@Override
	public String toString () {
		return name;
	}

	
	
	//rentals operations
	public abstract boolean addRental (Rental rental)
		throws OperationNotSupportedException;
	
	public abstract boolean removeRental (Rental rental)
		throws OperationNotSupportedException;
	
	
	//listeners operations
	public void addRentalCollectionListener(EMediaCollectionListener listener) {
        listeners.add(EMediaCollectionListener.class, listener);
    }

    public void removeRentalCollectionListener(EMediaCollectionListener listener) {
        listeners.remove(EMediaCollectionListener.class, listener);
    }
    
}
