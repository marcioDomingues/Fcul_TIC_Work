//package model.shelves.criteria;
//
//import model.rentals.Rental;
//
//public class AndCriteria extends CompoundCriteria {
//
//	public AndCriteria(Criterion leftCriteria, Criterion rightCriteria) {
//		super(leftCriteria, rightCriteria);
//	}
//
//	@Override
//	public boolean satisfies(Rental document) {
//		return leftCriteria.satisfies(document) && 
//			   rightCriteria.satisfies(document);
//	}
//
//}
