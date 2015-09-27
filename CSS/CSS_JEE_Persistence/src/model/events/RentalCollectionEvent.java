package model.events;

import java.util.EventObject;

public class RentalCollectionEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 945504911542242329L;
	
	public RentalCollectionEvent (Object document) {
		super (document);
	}
}
