package model.shelves.criteria;

import javax.persistence.Entity;

import model.rentals.Rental;

@Entity
public final class NotCriteria extends Criteria {

	private Criteria criteria;
		
	public NotCriteria(){};
	
	public NotCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public boolean satisfies(Rental rental) {
		return !criteria.satisfies(rental);
	}
}
