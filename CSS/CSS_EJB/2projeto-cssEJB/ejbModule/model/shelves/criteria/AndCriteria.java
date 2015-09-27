package model.shelves.criteria;

import javax.persistence.Entity;

import model.rentals.Rental;

@Entity
public class AndCriteria extends CompoundCriteria {

	AndCriteria() {}
	
	public AndCriteria(Criterion leftCriteria, Criterion rightCriteria) {
		super(leftCriteria, rightCriteria);
	}

	@Override
	public boolean satisfies(Rental document) {
		return leftCriteria.satisfies(document) && 
			   rightCriteria.satisfies(document);
	}

}
