package model.shelves.criteria;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import model.rentals.Rental;
@Entity
public class RecentlyAddedCriteria extends Criteria {

	@Column(name="Elapsed_Time", nullable=false)
	private long elapsedTime;
	 
	public RecentlyAddedCriteria(){};

	public RecentlyAddedCriteria (long elapsedTime) {
		super("RecentlyAdded");
		this.setElapsedTime(elapsedTime);
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	@Override
	public boolean satisfies(Rental rental) {
		return rental.getRentalTimestamp().getTime() + elapsedTime > new Date().getTime() ;
	}
}
