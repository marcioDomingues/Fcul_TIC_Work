package local_domain.model.lendables;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;

import local_domain.model.EMedium;
import local_domain.model.EMediumPropertiesData;
import local_domain.model.EMediumType;

public class Library implements Iterable<Lendable> {

	// need the Map from lendable to lendable 
	// because I want to sure I change the lendable in the
	// library and not other "equal" to it.
	private Map<Lendable, Lendable> lendables;
	private Lendable lastAddedLendable;
		
	public Library () {
		lendables = new HashMap<Lendable, Lendable> ();
	}
	
	public boolean addLendable(EMediumType type, EMediumPropertiesData properties) {
		Lendable aboutToBeAdded = new Lendable(type, properties);
		if (!lendables.containsKey(aboutToBeAdded)) {
			lastAddedLendable = aboutToBeAdded;
			lendables.put(aboutToBeAdded, aboutToBeAdded);
			return true;
		} else
			return false;
	}
	
	@Override
	public Iterator<Lendable> iterator() {
		return lendables.values().iterator();
	}

	public Lendable getLastAddedLendable() {
		return lastAddedLendable;
	}

	// pre: canBeRent(eMedium)
	public void rent(EMedium eMedium) {
		lendables.get(eMedium).rent();
	} 
	
	public boolean canBeRent(EMedium eMedium) {
		Lendable toBeRented = lendables.get(eMedium);
		return toBeRented != null && toBeRented.hasLicensesAvailable();
	}
 	
}
