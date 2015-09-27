package model.shelves.criteria;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CompoundCriteria extends Criteria {

	protected Criteria leftCriteria;
	protected Criteria rightCriteria;
	
	public CompoundCriteria() {};
	
	public CompoundCriteria(Criteria leftCriteria, Criteria rightCriteria) {
		super("CompoundCriteria");
		this.setLeftCriteria(leftCriteria);
		this.setRightCriteria(rightCriteria);
	}

	//Getters
	public Criteria getRightCriteria() {
		return rightCriteria;
	}
	
	public Criteria getLeftCriteria() {
		return leftCriteria;
	}
	
	//Setters
	public void setRightCriteria(Criteria rightCriteria) {
		this.rightCriteria = rightCriteria;
	}

	public void setLeftCriteria(Criteria leftCriteria) {
		this.leftCriteria = leftCriteria;
	}
}
