//package model.shelves.criteria;
//
//import java.util.Date;
//
//import model.rentals.Rental;
//
//public class RecentlyAddedCriteria implements Criterion {
//
//	private long elapsedTime;
//
//	public RecentlyAddedCriteria (long elapsedTime) {
//		this.elapsedTime = elapsedTime;
//	}
//	
//	@Override
//	public boolean satisfies(Rental rental) {
//		return rental.getRentalTimestamp().getTime() + elapsedTime > new Date().getTime() ;
//	}
//}
