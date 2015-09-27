package model.shelves.criteria;

import javax.persistence.Column;
import javax.persistence.Entity;

import model.rentals.Rental;

@Entity
public class OrCriteria extends CompoundCriteria {
	
	@Column (name="Type")
	private final String tag = "OrCriteria";
	
	public OrCriteria(){};
	
	public OrCriteria(Criteria leftCriteria, Criteria rightCriteria) {
		super(leftCriteria, rightCriteria);
		this.setCriteriaName(tag);
	}

	public String getCompoundCriterianame() {
		return tag;
	}
	
	@Override
	public boolean satisfies(Rental rental) {
		return leftCriteria.satisfies(rental) ||  rightCriteria.satisfies(rental);
	}

}
