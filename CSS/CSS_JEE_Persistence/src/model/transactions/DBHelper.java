package model.transactions;

import java.util.ArrayList;
import java.util.List;

/*
 * Singleton DBHelper to help us keep it DRY.
 */
public enum DBHelper {

	// singleton
	INSTANCE;

	/*
	 * Method to get a List of entities of a certain T class. It can get all the
	 * objects by specifying null in both paramName and paramValue, or to get
	 * all that match those two search parameters.
	 * 
	 * @param queryName - the @namedQuery
	 * 
	 * @param paramName - a search target column name
	 * 
	 * @param paramValue - a search target value for paramName
	 * 
	 * @param entity - the class T of the objects to get from DB
	 * 
	 * @return a list of T objects
	 */
	public <T> List<T> get(final String queryName, final String paramName,
			final Object paramValue, final Class<T> entity) {

		final List<T> storedItems = new ArrayList<T>();

		try {
			new Transactionable() {
				{
					run(new PersistentOperation() {
						public void run() throws Exception {
							storedItems.addAll(getAll(queryName, paramName,
									paramValue, entity));
						}
					});
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		// finally returns the storedItems
		return storedItems;
	}

	/*
	 * Method to get a single Object of a certain T class. It can get the object
	 * that matches those the search parameters.
	 * 
	 * @param queryName - the @namedQuery
	 * 
	 * @param paramName - a search target column name
	 * 
	 * @param paramValue - a search target value for paramName
	 * 
	 * @param entity - the class T of the objects to get from DB
	 * 
	 * @return a T object OR null if no results
	 */
	public <T> T getSingle(final String queryName, final String paramName,
			final Object paramValue, final Class<T> entity) {
		// gets all
		List<T> resultList = get(queryName, paramName, paramValue, entity);
		// returns the first or null if none
		return resultList.isEmpty() ? null : resultList.get(0);
	}
	
	public <T> boolean contains(final String queryName, final String paramName,
			final Object paramValue, final Class<T> entity) {
		// gets one
		T element = getSingle(queryName, paramName, paramValue, entity);
		// returns true if element not null (exists)
		return element != null;
	}

	/*
	 * Method to store a List of objects of a certain class.
	 * 
	 * @param itemsToPersist - all the items to be persisted
	 * 
	 * @return true if stored successfully, false otherwise.
	 */
	public <T> boolean store(final Object... itemsToPersit) {
		// flag to return
		boolean allWentWell = true;
		try {
			new Transactionable() {
				{
					run(new PersistentOperation() {
						public void run() throws Exception {
							for (Object item : itemsToPersit)
								persist(item);
						}
					});
				}
			};
		} catch (Exception e) {
			allWentWell = false;
			e.printStackTrace();
		}

		return allWentWell;
	}

	/*
	 * Method that updates in DB the given objects.
	 * 
	 * @param itemsToUpdate - all the items to be updated
	 * 
	 * @param paramName - a search target column name
	 * 
	 * @param paramValue - a search target value for paramName
	 * 
	 * @param entity - the class of the objects to get from DB
	 */
	public <T> void update(final Object... itemsToUpdate) {

		try {
			new Transactionable() {
				{
					run(new PersistentOperation() {
						public void run() throws Exception {
							for (Object item : itemsToUpdate)
								merge(item);
						}
					});
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Method that removed from the DB the given objects.
	 * 
	 * @param itemsToDelete - all the items to be deleted
	 * 
	 * @return true if deleted successfully, false otherwise.
	 */
	public <T> boolean remove(final Object... itemsToDelete) {
		// flag to return
		boolean allWentWell = true;
		try {
			new Transactionable() {
				{
					run(new PersistentOperation() {
						public void run() throws Exception {
							for (Object item : itemsToDelete) {
								remove(merge(item));
							}
						}
					});
				}
			};
		} catch (Exception e) {
			allWentWell = false;
			e.printStackTrace();
		}
		return allWentWell;
	}

}
