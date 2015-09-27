package model.transactions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.lendables.Lendable;

public class Transactionable {
	
	private static EntityManagerFactory emf;
	
	static {
		emf = Persistence.createEntityManagerFactory("CommunityShareSystem");
	}

	private int depth;
	private EntityManager em;
	
	protected void txBegin() {
		if (em == null) {
			em = emf.createEntityManager();
			em.getTransaction().begin();
		}
		
		depth += 1;
	}
	
	protected void txCommit() {
		if (depth == 1)
			em.getTransaction().commit();
	}

	protected void txRollback() {
		if (depth > 0) {
			EntityTransaction tx = em.getTransaction();
			if (tx.isActive())
				tx.rollback();
		}
	}

	protected void txClose() {
		checkBanlancedTx();
		if (depth == 0)
			try {
				em.close();
			} finally {
				em = null;
			}
	}

	private void checkBanlancedTx() {
		depth -= 1;
		if (depth < 0)
			throw new IllegalStateException("Unbalanced transaction");
	}
	
	protected void persist(Object... objects) {
		for (Object elem : objects)
			em.persist(elem);
	}
	
	protected void refresh(Object... objects) {
		for (Object elem : objects)
			em.refresh(elem);
	}
	
	protected <T> T merge(T object) {
		return em.merge(object);
	}
	
	protected void remove(Object object) {
		em.remove(object);
	}
	
	protected <T> List<T> getAll(String queryName,
			String paramName, Object paramValue, Class<T> entity)
			throws PersistenceException {
		TypedQuery<T> query = em.createNamedQuery(queryName, entity);
		if ( paramName != null && paramValue != null)
			query.setParameter(paramName, paramValue);
		
		return query.getResultList();
	}
	
	protected <T> void updateEntity (Object updatedObject)
			throws PersistenceException {
		em.merge(updatedObject);
		}
	
	protected <T> T findEntity(String queryName,
			String paramName, Object paramValue, Class<T> entity)
			throws PersistenceException {
		TypedQuery<T> query = em.createNamedQuery(queryName, entity);
		if ( paramName != null && paramValue != null)
			query.setParameter(paramName, paramValue);
		
		return query.getSingleResult();
	}
	
	public void run(PersistentOperation operation) throws Exception {
		try {
			txBegin();
			operation.run();
			txCommit();
		} catch (Exception ex) {
			txRollback();
			throw ex;
		} finally {
			txClose();
		}
	}
}
