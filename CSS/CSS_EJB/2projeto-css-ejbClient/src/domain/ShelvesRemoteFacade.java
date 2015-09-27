package domain;

import javax.ejb.Remote;

@Remote
public interface ShelvesRemoteFacade {
	
	
	public void startUp();

	public boolean addNormalShelf(String name) throws Exception;

	public void addRental(String shelfName, EMediumDTO rental) throws Exception;
	
	public boolean removeShelf(String name) throws Exception;

	public String getMainShelf() throws Exception;

	public Iterable<String> getShelves() throws Exception;

	public boolean addOrRenewRental(EMediumDTO lendableDTO);

	public Iterable<EMediumRemote> getShelfRentals(String shelfName);

	public void removeRental(EMediumRemote lendableDTO, String shelfName);

}
