package domain;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.lendables.LibraryInterface;
import domain.bridge.EMediumAdapter;

@Stateless
public class LibraryRemote implements LibraryRemoteFacade {
	
	@EJB 
	private LibraryInterface library;
	
	//An EMedium DTO Adapter  EMediumRemote <> EMedium
	private EMediumAdapter adapter;
	
	
	public LibraryRemote() {
		adapter = new EMediumAdapter();
	}
	
	
	@Override
	public Iterable<EMediumRemote> getItems() {
		return adapter.eMediumToDTOList(library.getLendables());
	}
	

}
