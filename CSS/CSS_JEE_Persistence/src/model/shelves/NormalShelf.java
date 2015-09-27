package model.shelves;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.PostLoad;
import javax.swing.event.EventListenerList;

import model.events.EMediaCollectionListener;
import model.events.RentalCollectionEvent;
import model.rentals.Rental;



@Entity
public class NormalShelf extends Shelf {
	
	// need the Map from Rental to Rental 
	// because I want to sure I change the Rental in the
	// Shelf and not other "equal" to it.
	//o default e lazy se o objecto shelf estiver detached o
	@ManyToMany( fetch=FetchType.LAZY, cascade = {CascadeType.MERGE} )
	@JoinTable(name="SHELF_RENTALS")
	@MapKeyJoinColumn(name="Rental")
	private Map<Rental, Rental> rentals;
	

	public NormalShelf(){};
	
	public NormalShelf (String name) {
		super(name);
		rentals = new TreeMap<Rental, Rental> ();
	}
	
	@PostLoad
	private void initialize() {
		listeners = new EventListenerList();
	}
	
	public boolean addRental( Rental rental ) {
		Rental thisRental = rentals.get(rental);
		boolean canBeAdded = thisRental == null;
		
		if (canBeAdded) {
			rentals.put (rental, rental);
			fireRentalAdded (new RentalCollectionEvent (rental));	
		}
		
		return canBeAdded;
	}
	
	
	public boolean removeRental (Rental rental) {
		boolean wasRemoved = rentals.remove(rental) != null;
		if ( wasRemoved )
			fireRentalRemoved (new RentalCollectionEvent (rental));
		
		return wasRemoved;
	}
	
	public boolean contains (Rental rental) {
		return rentals.containsKey(rental);
	}
	
	//pre: contains(rental)
	public boolean isExpired(Rental rental) {
		return rentals.get(rental).isExpired();
	}
	
	@Override
	public Iterator<Rental> iterator() {
		return rentals.values().iterator();
	}

	@Override
	public void RentalAdded(RentalCollectionEvent event) {
		// Nothing to be done here!
	}

	@Override
	public void RentalRemoved(RentalCollectionEvent event) {
		rentals.remove(event.getSource());
	}
	
    protected void fireRentalAdded(RentalCollectionEvent event) {
    	for (EMediaCollectionListener listener : listeners.getListeners(EMediaCollectionListener.class)) {
    		listener.RentalAdded(event);
    	}
    }

    protected void fireRentalRemoved(RentalCollectionEvent event) {
    	for (EMediaCollectionListener listener : listeners.getListeners(EMediaCollectionListener.class))
    		listener.RentalRemoved(event);
    }
    
    
    /* utils */

}
