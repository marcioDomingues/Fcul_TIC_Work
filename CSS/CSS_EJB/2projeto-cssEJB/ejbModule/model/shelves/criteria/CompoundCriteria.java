package model.shelves.criteria;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public abstract class CompoundCriteria extends Criterion {

	@OneToOne @JoinColumn(nullable=false)
	protected Criterion leftCriteria;
	
	@OneToOne @JoinColumn(nullable=false)
	protected Criterion rightCriteria;
	
	CompoundCriteria() {}
	
	public CompoundCriteria(Criterion leftCriteria, Criterion rightCriteria) {
		this.leftCriteria = leftCriteria;
		this.rightCriteria = rightCriteria;
	}
}
