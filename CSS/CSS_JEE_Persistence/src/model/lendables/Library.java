package model.lendables;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Transient;

import model.EMedium;
import model.EMediumPropertiesData;
import model.EMediumType;
import model.transactions.DBHelper;

public class Library implements Iterable<Lendable> {

	@Transient
	private Lendable lastAddedLendable;

	public Library() {}

	public boolean addLendable(EMediumType type,
			EMediumPropertiesData properties) {
		// creates  and tries to add the new lendable
		return DBHelper.INSTANCE.store(new Lendable(type, properties));
	}

	@Override
	public Iterator<Lendable> iterator() {
		return getAllLendables().iterator();
	}

	public Lendable getLastAddedLendable() {
		return getLatestLendable();
	}

	public void returnLendable(EMedium eMedium) {
		((Lendable) eMedium).returned();
		DBHelper.INSTANCE.update(eMedium);
	}

	// pre: canBeRent(eMedium)
	public void rent(EMedium eMedium) {
		((Lendable) eMedium).rent();
		DBHelper.INSTANCE.update(eMedium);
	}

	public boolean canBeRent(EMedium eMedium) {
		return getLendableByID(eMedium).hasLicensesAvailable();
	}

	private Lendable getLendableByID(EMedium eMedium) {
		return getLendableBy(Lendable.FIND_ID, eMedium.getID());
	}

	private Lendable getLendableBy(String searchParam,
			Object paramValue) {
		return DBHelper.INSTANCE.getSingle(Lendable.FIND_BY_ID, searchParam,
				paramValue, Lendable.class);
	}
	
	private Lendable getLatestLendable() {
		return DBHelper.INSTANCE.getSingle(Lendable.FIND_LAST_ADDED, null,
				null, Lendable.class);
	}

	private List<Lendable> getAllLendables() {
		return DBHelper.INSTANCE.get(Lendable.FIND_ALL, null, null,
				Lendable.class);
	}

}
