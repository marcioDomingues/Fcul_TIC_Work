package domain;

import java.util.ArrayList;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessOrders.
 */
public class ProcessOrders {

	/** The ordens. */
	private ArrayList<Ordem> ordens;
	
	/**
	 * Instantiates a new process orders.
	 */
	public ProcessOrders(){
		ordens = new ArrayList<Ordem>();
	}

	/**
	 * Gets the ordens.
	 *
	 * @return the ordens
	 */
	public ArrayList<Ordem> getOrdens() {
		return ordens;
	}
	
	/**
	 * Adicionar ordem.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	public boolean adicionarOrdem(Ordem o){
		Iterator<Ordem> i = ordens.iterator();
		while(i.hasNext()){
			if(i.next().getIdOrdem() == o.getIdOrdem())
				return false;
		}
		
		ordens.add(o);
		return true;
	}
	
}