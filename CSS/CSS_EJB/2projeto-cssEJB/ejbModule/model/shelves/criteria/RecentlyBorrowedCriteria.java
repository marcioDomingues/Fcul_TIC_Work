package model.shelves.criteria;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import model.rentals.Rental;

@Entity
public class RecentlyBorrowedCriteria extends Criterion {

	@Column(nullable=false)
	private long elapsedTime;

	RecentlyBorrowedCriteria() {}
	
	public RecentlyBorrowedCriteria (long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	@Override
	public boolean satisfies(Rental rental) {
		return rental.getRentalTimestamp().getTime() + elapsedTime > new Date().getTime() ;
	}
}
