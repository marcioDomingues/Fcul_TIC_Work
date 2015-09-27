package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import model.events.EMediaCollectionListener;
import model.events.ShelfCollectionListener;
import model.rentals.Rental;
import model.rentals.RentalFactory;
import model.shelves.Shelf;
import model.shelves.Shelves;
import model.shelves.criteria.AuthorCriteria;
import model.shelves.criteria.Criteria;
import model.shelves.criteria.OrCriteria;
import model.shelves.criteria.RecentlyAddedCriteria;



public class ShelvesFacade {
	
		private Shelves shelves;
		private RentalFactory rentalFactory;
		private Map<String, Criteria> iShelfCriteria;

		
		public ShelvesFacade (Shelves shelves, RentalFactory rentalFactory) {
			this.shelves = shelves;
			this.rentalFactory = rentalFactory;
			iShelfCriteria = new HashMap<>();
			iShelfCriteriaSetup();
		}
		
		private void iShelfCriteriaSetup() {
			iShelfCriteria.put("RecentlyAdded", new RecentlyAddedCriteria(1000 * 60));
			iShelfCriteria.put("AuthorProfessorCSS", new AuthorCriteria("ProfCSS"));
		}
		
		public boolean addNormalShelf (String name) {
			return shelves.addNormalShelf (name);
		}
		
		public boolean addSmartShelf (String name, String criteria) {
			if ( ! iShelfCriteria.containsKey(criteria))
				return false;
			
 			return shelves.addSmartShelf (name, iShelfCriteria.get(criteria));
		}
		
		public Iterable<String> getShelves () {
			LinkedList <String> result = new LinkedList<String>();
			for (Shelf s : shelves)
				result.add(s.getName());
			return result;
		}

		public void removeShelf(String name) {
			shelves.removeShelf (name);
		}
		
		
		//TODO
		public void removeRental (String selfName, EMedium rental) throws OperationNotSupportedException {
			shelves.removeRentalFromShelf (selfName, (Rental) rental);
		}
		//TODO
		// pre: !isRented(rental)
		public boolean addRental(EMedium rental) {
			return shelves.addRental(rentalFactory.createRental(rental));
		}
		
		public void returnRental(EMedium rental) {
			shelves.returnRental(rentalFactory.createRental(rental));
		}
		//TODO
		public boolean addShelfRental(String shelfName, EMedium rental) throws OperationNotSupportedException {
			return shelves.addRentalToShelf (shelfName, rentalFactory.createRental(rental));
		}
		//TODO
		public Iterable<EMedium> getShelfRentals (String shelfName) {
			LinkedList<EMedium> emedia = new LinkedList<EMedium> ();
			for (Rental r : shelves.getShelfRentals(shelfName))
				emedia.add(r);
			return emedia; 
		}
		//TODO
		public Iterable<EMedium> getRentals () {
			LinkedList<EMedium> emedia = new LinkedList<EMedium> ();
			for (Rental r : shelves.getRentals())
				emedia.add(r);
			return emedia; 
		}
		
		public boolean isRented(EMedium rental) {
			return shelves.isRented(rentalFactory.createRental(rental));
		}
		
		public boolean isRentalExpired(EMedium rental) {
			return shelves.isExpired(rentalFactory.createRental(rental));
		}		
		
		public void addShelfCollectionListener(ShelfCollectionListener listener) {
			shelves.addShelfCollectionListener(listener);
	    }

	    public void removeShelfCollectionListener(ShelfCollectionListener listener) {
	    	shelves.removeShelfCollectionListener(listener);
	    }
	    
	  //TODO
	    public void addRentalCollectionListener(String shelfName, EMediaCollectionListener listener) {
	    	shelves.addRentalCollectionListener(shelfName, listener);
	    }
	  //TODO
	    public void removeRentalCollectionListener(String shelfName, EMediaCollectionListener listener) {
	    	shelves.removeRentalCollectionListener(shelfName, listener);
	    }
	     
}