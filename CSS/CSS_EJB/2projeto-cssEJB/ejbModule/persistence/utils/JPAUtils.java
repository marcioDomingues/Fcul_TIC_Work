package persistence.utils;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 * Utility class that abstracts common patterns.
 * 
 * @author mguimas
 *
 */
public final class JPAUtils {

	private static EntityManagerFactory emf;
	
	static {
		// TODO: read persistence unit name
		emf = Persistence.createEntityManagerFactory("CommunityShareSystem");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				emf.close();
			}
		});
	}
	
	public static void runInTx(BusinessOperation operation) throws Exception {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			operation.execute(em);
			tx.commit();
		} catch (Exception ex) {
			if (tx.isActive())
				tx.rollback();
			throw ex;
		} finally {
			em.close();
		}
	}

	public static <T> T runInTxWithResult(BusinessOperationWithResult operation)
			throws Exception {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			T result = operation.execute(em);
			tx.commit();
			return result;
		} catch (Exception ex) {
			if (tx.isActive())
				tx.rollback();
			throw ex;
		} finally {
			em.close();
		}
	}

	public static <T> T findEntity(EntityManager em, String queryName,
			String paramName, Object paramValue, Class<T> entity)
			throws PersistenceException {
		try {
			TypedQuery<T> query = em.createNamedQuery(queryName, entity);
			query.setParameter(paramName, paramValue);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static <T> T findEntity(String queryName, String paramName, Object paramValue, Class<T> entity)
			throws PersistenceException {
		EntityManager em = emf.createEntityManager();
		try {
			return findEntity(em, queryName, paramName, paramValue, entity);
		} finally {
			em.close();
		}
	}

	public static <T> List<T> findList(String queryName, Class<T> entity) {
		EntityManager em = emf.createEntityManager();
		try {
			return findList(em, queryName, entity);
		} finally {
			em.close();
		}
	}
	
	public static <T> List<T> findList(EntityManager em, String queryName, Class<T> entity) {
		try {
			TypedQuery<T> query = em.createNamedQuery(queryName, entity);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public static <T> List<T> findList(EntityManager em, String queryName, 
			String paramName, String paramValue, Class<T> entity) {
		try {
			TypedQuery<T> query = em.createNamedQuery(queryName, entity);
			query.setParameter(paramName, paramValue);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	public static void persist(EntityManager em, Object... objects) {
		for (Object elem : objects)
			em.persist(elem);
	}

	public static void remove(EntityManager em, Object... objects) {
		for (Object elem : objects)
			em.remove(em.merge(elem));
	}

}
