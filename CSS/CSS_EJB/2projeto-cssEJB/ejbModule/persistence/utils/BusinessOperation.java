package persistence.utils;

import javax.persistence.EntityManager;

/**
 * This interface runs the logic of a persistent business operation. Clients
 * should use this interface to isolate business operations from transaction
 * handling.
 *
 * @author mguimas
 */

public interface BusinessOperation {

	void execute(EntityManager em) throws Exception;

}
