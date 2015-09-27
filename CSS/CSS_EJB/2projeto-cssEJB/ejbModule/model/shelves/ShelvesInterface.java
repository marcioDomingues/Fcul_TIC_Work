package model.shelves;

import javax.annotation.PostConstruct;

import model.lendables.Lendable;
import model.rentals.Rental;
import model.shelves.criteria.Criterion;
//import persistence.utils.BusinessOperation;
//import persistence.utils.BusinessOperationWithResult;
//import persistence.utils.JPAUtils;
//import domain.ApplicationException;

public interface ShelvesInterface {


	@PostConstruct
	public void startUp();
	

	public boolean addNormalShelf(String name) throws Exception;

	public boolean addSmartShelf(String name, Criterion criteria)
			throws Exception;


	public boolean addOrRenewRental(EMedium lendable);

	

	public void addRental(String shelfName, EMedium rental) throws Exception;
	
	
	

	public boolean removeRental(String shelfName, EMedium rental)
			throws Exception;
//
//	public boolean returnRental(String shelfName, EMedium rental)
//			throws Exception {
//		return JPAUtils.runInTxWithResult(new BusinessOperationWithResult() {
//			@Override
//			public Boolean execute(EntityManager em) throws Exception {
//				Shelf shelf = getShelf(em, MYRENTALS);
//				return shelf != null ? shelf.returnRental((Rental) rental)
//						: false;
//			}
//		});
//	}

	

	public boolean removeShelf(String name) throws Exception;

	public Iterable<String> getShelves();
	public String getMainShelf();


	public Iterable<String> getAllShelves();
	
	public Iterable<Rental> getShelfRentals (String shelfName) throws Exception;

	

	public boolean revokeLending(Lendable lendable) throws Exception;


	void addRentalTo(Rental rental, String shelfName) throws Exception;

	

}
