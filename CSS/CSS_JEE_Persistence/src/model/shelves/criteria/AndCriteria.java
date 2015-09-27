package model.shelves.criteria;

import javax.persistence.Column;
import javax.persistence.Entity;

import model.rentals.Rental;

@Entity
public class AndCriteria extends CompoundCriteria {

	@Column (name="Type")
	private final String tag = "AndCriteria";
	
	public AndCriteria(){};
	
	public AndCriteria(Criteria leftCriteria, Criteria rightCriteria) {
		super(leftCriteria, rightCriteria);
		this.setCriteriaName(tag);
	}

	public String getCompoundCriterianame() {
		return tag;
	}
	
	@Override
	public boolean satisfies(Rental document) {
		return leftCriteria.satisfies(document) && rightCriteria.satisfies(document);
	}
}