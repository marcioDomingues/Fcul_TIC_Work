//package model.shelves.criteria;
//
//import model.rentals.Rental;
//
//public final class NotCriteria implements Criterion {
//
//	private final Criterion criteria;
//	
//	public NotCriteria(Criterion criteria) {
//		this.criteria = criteria;
//	}
//
//	@Override
//	public boolean satisfies(Rental rental) {
//		return !criteria.satisfies(rental);
//	}
//
//}
