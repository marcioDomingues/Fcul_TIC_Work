package domain;

import javax.ejb.Remote;

@Remote
public interface LibraryRemoteFacade {
	
	public Iterable<EMediumRemote> getItems();

}
