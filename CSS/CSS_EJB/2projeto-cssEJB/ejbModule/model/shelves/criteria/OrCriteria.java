package model.shelves.criteria;

import javax.persistence.Entity;

import model.rentals.Rental;

@Entity
public class OrCriteria extends CompoundCriteria {

	OrCriteria() {}
	
	public OrCriteria(Criterion leftCriteria, Criterion rightCriteria) {
		super(leftCriteria, rightCriteria);
	}

	@Override
	public boolean satisfies(Rental rental) {
		return leftCriteria.satisfies(rental) || 
		   rightCriteria.satisfies(rental);
	}

}
