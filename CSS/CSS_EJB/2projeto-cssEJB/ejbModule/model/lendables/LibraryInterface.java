package model.lendables;

import java.util.Iterator;
import java.util.List;

import model.EMediumProperties;
import model.shelves.EMedium;

public interface LibraryInterface {
	
	public boolean addLendable(EMediumProperties properties);

	public boolean revokeLending(EMedium eMedium) throws Exception;

	public Iterator<Lendable> iterator();
	
	public List<Lendable> getLendables();
	
	public Lendable getLastAddedLendable();

	public EMediumProperties readProperties(EMedium eMedium) throws Exception;
	

}
