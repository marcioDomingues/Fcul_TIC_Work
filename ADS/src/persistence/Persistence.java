package persistence;

import java.util.ArrayList;

import domain.Ordem;

// TODO: Auto-generated Javadoc
/**
 * The Interface Persistence.
 */
public interface Persistence {
	
	/**
	 * Adds the ordem.
	 *
	 * @param ordem the ordem
	 */
	public void addOrdem(String ordem);
	
	/**
	 * Gets the ordens.
	 *
	 * @return the ordens
	 */
	public ArrayList<Ordem> getOrdens();
	
}
