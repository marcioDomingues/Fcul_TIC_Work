package persistence.utils;

/**
 * This interface runs the logic of a persistent business operation. Clients
 * should use this interface to isolate business operations from transaction
 * handling.
 *
 * @author mguimas
 */

public interface PersistentOperation {

	void run() throws Exception;

}
