package persistence.utils;

import javax.persistence.EntityManager;

public interface BusinessOperationWithResult {

	<T> T execute(EntityManager em) throws Exception;

}
