//package model.shelves.criteria;
//
//import model.rentals.Rental;
//
//public class OrCriteria extends CompoundCriteria {
//
//	public OrCriteria(Criterion leftCriteria, Criterion rightCriteria) {
//		super(leftCriteria, rightCriteria);
//	}
//
//	@Override
//	public boolean satisfies(Rental rental) {
//		return leftCriteria.satisfies(rental) || 
//		   rightCriteria.satisfies(rental);
//	}
//
//}
