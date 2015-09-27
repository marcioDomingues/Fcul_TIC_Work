package model.lendables;

import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.EMediumAttribute;
import model.EMediumProperties;
import model.shelves.EMedium;
import model.shelves.Shelves;
import persistence.utils.BusinessOperationWithResult;
import persistence.utils.JPAUtils;
import domain.EMediumRemote;
import domain.LibraryRemoteFacade;
import domain.bridge.EMediumAdapter;

@Stateless
@Startup
public class Library implements Iterable<Lendable>, LibraryInterface{

	@PersistenceContext
	private EntityManager em;
	
	private Shelves shelves;
	
	private Lendable lastAddedLendable;

	public Library() {}
	
	public Library(Shelves shelves) {
		this.shelves = shelves;
	}

	public boolean addLendable(EMediumProperties properties) {
		try {
			lastAddedLendable = JPAUtils
					.runInTxWithResult(new BusinessOperationWithResult() {
						@Override
						public Lendable execute(EntityManager em)
								throws Exception {
							Lendable lendable = null;
							if (!existsLendable(em, properties)) {
								lendable = new Lendable(properties);
								JPAUtils.persist(em, lendable);
							}
							return lendable;
						}
					});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean revokeLending(EMedium eMedium) throws Exception {
		return shelves.revokeLending((Lendable) eMedium);
	}

	private boolean existsLendable(EntityManager em,
			EMediumProperties properties) {
		String path = properties.getAttribute(EMediumAttribute.PATH);
		for (Lendable lendable : this)
			if (lendable.getPath().equals(path))
				return true;
		return false;
	}

	@Override
	public Iterator<Lendable> iterator() {
		TypedQuery<Lendable> query = em.createNamedQuery(Lendable.FIND_ALL,
				Lendable.class);
		List<Lendable> allItems = query.getResultList();
		return allItems.iterator();
	}
	
	public List<Lendable> getLendables() {
		TypedQuery<Lendable> query = em.createNamedQuery(Lendable.FIND_ALL,
				Lendable.class);
		List<Lendable> allItems = query.getResultList();
		return allItems;
	}
	


	public Lendable getLastAddedLendable() {
		return lastAddedLendable;
	}

	public EMediumProperties readProperties(EMedium eMedium) throws Exception {
		Lendable lendable = (Lendable) em.merge(eMedium);
		em.refresh(lendable);
		return lendable.getEMediumProperties();
	}


}
