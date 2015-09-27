package model.events;

import java.util.EventObject;

public class ShelfCollectionEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 945504911542242329L;
	
	public ShelfCollectionEvent (Object shelfName) {
		super (shelfName);
	}
}
