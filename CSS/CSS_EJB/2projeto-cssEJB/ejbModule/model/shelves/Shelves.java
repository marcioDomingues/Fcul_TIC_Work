package model.shelves;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.swing.event.EventListenerList;

import model.events.ShelfCollectionEvent;
import model.events.ShelfCollectionListener;
import model.lendables.Lendable;
import model.rentals.Rental;
import model.shelves.criteria.Criterion;
//import persistence.utils.BusinessOperation;
//import persistence.utils.BusinessOperationWithResult;
//import persistence.utils.JPAUtils;
import css.AppProperties;
//import domain.ApplicationException;

@Stateless
@Startup
public class Shelves implements ShelvesInterface{

	@PersistenceContext
	private EntityManager em;

	public static final String MYRENTALS = "MyRentals";//AppProperties.INSTANCE.RENTALS_SHELF_NAME;
	public static final String RECENTLY_BORROWED = AppProperties.INSTANCE.RECENTLY_BORROWED_SHELF_NAME;

	private EventListenerList shelfListeners;

	
	public Shelves() {}

	@PostConstruct
	public void startUp() {
		try {
			ensureMyRentals();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ensureMyRentals() throws Exception {
		if (!existsShelf(MYRENTALS)) {
			NormalShelf shelf = new NormalShelf(MYRENTALS);
			em.persist(shelf);
		}
	}

	@Override
	public boolean addNormalShelf(String name) throws Exception {
		boolean result = false;
		try {
			NormalShelf shelf = null;
			if (!existsShelf(name)) {
				shelf = new NormalShelf(name);
				em.persist(shelf);
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		
		return result;
	}
	
	@Override
	public boolean addSmartShelf(String name, Criterion criteria)
			throws Exception {
		try {

			SmartShelf shelf = null;
			if (!existsShelf(name)) {
				Shelf myRentals = getShelf(MYRENTALS);
				shelf = new SmartShelf(name, myRentals, criteria);

				em.getTransaction().begin();
				em.persist(shelf);
				em.getTransaction().commit();
			}

			if (shelf != null) {
				fireShelfAdded(new ShelfCollectionEvent(name));
				return true;
			}
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			// throw new ApplicationException("Erro ao criar uma smartShelf",
			// e);
		}
		return false;
	}

	private boolean existsShelf(String name) {
		return getShelf(name) != null;
	}

	private Shelf getShelf(String name) {
		try {
			TypedQuery<Shelf> query = em.createNamedQuery(Shelf.FIND_BY_NAME,
					Shelf.class);
			query.setParameter(Shelf.NAME, name);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	private Shelf getShelf(EntityManager em, String name) {
		try {
			TypedQuery<Shelf> query = em.createNamedQuery(Shelf.FIND_BY_NAME,
					Shelf.class);
			query.setParameter(Shelf.NAME, name);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	@Override
	public boolean addOrRenewRental(EMedium lendable) {
		try {
			Lendable aLendable = em.merge(lendable.getLendable());
			em.refresh(aLendable);
			if (aLendable.hasLicensesAvailable()) {
				aLendable.createLending();
				getShelf(em, MYRENTALS).addRental(aLendable.makeRental());
				return true;
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public void addRental(String shelfName, EMedium rental) throws Exception {
		getShelf(em, shelfName).addRental((Rental)em.merge(rental.makeRental()));
	}
	@Override
	public void addRentalTo(Rental rental, String shelfName) throws Exception {
		getShelf(em, shelfName).addRental(rental);
	}
	
	
	@Override
	public boolean removeRental(String shelfName, EMedium rental)
			throws Exception {
			Shelf shelf = getShelf(shelfName);
			boolean removed = shelf.removeRental(rental.makeRental());
			em.merge(shelf);
			return removed;
	}
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

	
	@Override
	public boolean removeShelf(String name) throws Exception {
		// must never remove MyRentals
		if (name.equals(MYRENTALS))
			return false;

		// result flag
		boolean wasRemoved = false;

		try {
			Shelf shelf = getShelf(name);
			if (shelf != null) {
				em.remove(shelf);
				wasRemoved = true;
			}
		} catch (Exception e) {}

		// returns result of operation
		return wasRemoved;
	}

	@Override
	public List<String> getShelves() /* throws ApplicationException */{
		try {

			TypedQuery<Shelf> query = em.createNamedQuery(
					Shelf.FIND_ALL_BUT_MYRENTALS, Shelf.class);
			query.setParameter(Shelf.NAME, MYRENTALS);
			List<Shelf> theShelves = query.getResultList();

			return theShelves.stream().map(x -> x.getName())
					.collect(Collectors.toList());

		} catch (PersistenceException e) {
			return new ArrayList<>();
		}
	}
	
	@Override
	public String getMainShelf() {
		return new String(MYRENTALS);
	}

	@Override
	public Iterable<String> getAllShelves() /* throws ApplicationException */{
		try {

			TypedQuery<Shelf> query = em.createNamedQuery(Shelf.FIND_ALL,
					Shelf.class);
			List<Shelf> theShelves = query.getResultList();

			return theShelves.stream().map(x -> x.getName())
					.collect(Collectors.toList());

		} catch (PersistenceException e) {
			// throw new ApplicationException
			// ("Erro ao obter o lista de shelfs");
			return null;
		}

	}
	
	@Override
	public Iterable<Rental> getShelfRentals (String shelfName) throws Exception {
		return getShelf(em, shelfName);
	}


	public void addShelfCollectionListener(ShelfCollectionListener listener) {
		shelfListeners.add(ShelfCollectionListener.class, listener);
	}

	public void removeShelfCollectionListener(ShelfCollectionListener listener) {
		shelfListeners.remove(ShelfCollectionListener.class, listener);
	}

	private void fireShelfAdded(ShelfCollectionEvent event) {
		for (ShelfCollectionListener listener : shelfListeners
				.getListeners(ShelfCollectionListener.class))
			listener.shelfAdded(event);
	}

	private void fireShelfRemoved(ShelfCollectionEvent event) {
		for (ShelfCollectionListener listener : shelfListeners
				.getListeners(ShelfCollectionListener.class))
			listener.shelfRemoved(event);
	}

	@Override
	public boolean revokeLending(Lendable lendable) throws Exception {
		// return JPAUtils.runInTxWithResult(new BusinessOperationWithResult() {
		// @Override
		// public Boolean execute(EntityManager em) throws Exception {
		// Shelf shelf = getShelf(em, MYRENTALS);
		// return shelf != null ?
		// shelf.returnRentalForLendable(lendable) : false;
		// }
		// });
		return false;
	}

	

}
