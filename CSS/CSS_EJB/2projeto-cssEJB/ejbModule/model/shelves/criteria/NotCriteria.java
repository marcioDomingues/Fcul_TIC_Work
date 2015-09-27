package model.shelves.criteria;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import model.rentals.Rental;

@Entity
public class NotCriteria extends Criterion {

	@OneToOne @JoinColumn(nullable=false)
	private Criterion criteria;
	
	NotCriteria() {}
	
	public NotCriteria(Criterion criteria) {
		this.criteria = criteria;
	}

	@Override
	public boolean satisfies(Rental rental) {
		return !criteria.satisfies(rental);
	}

}
