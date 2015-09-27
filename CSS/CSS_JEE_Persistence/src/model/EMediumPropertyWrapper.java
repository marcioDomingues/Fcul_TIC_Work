package model;

import java.io.Serializable;

public class EMediumPropertyWrapper  implements Serializable {
	private static final long serialVersionUID = -4985988886975776293L;
	private Object property;
	
	public void set(Object property) {
		 	this.property = property;
	}
	
	public Object get() {
			return this.property;
	}
	
	public EMediumPropertyWrapper(Object property) {
			this.property = property;
	}
	

}
