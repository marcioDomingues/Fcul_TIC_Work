package domain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.lendables.Lendable;
import model.rentals.Rental;
import model.shelves.ShelvesInterface;
import domain.bridge.EMediumAdapter;

@Stateless
@Startup
public class ShelvesRemote implements ShelvesRemoteFacade {

	@EJB
	private ShelvesInterface shelves;
	
	private EMediumAdapter adapter;
	
	public ShelvesRemote() {
		adapter = new EMediumAdapter();
	}
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@PostConstruct
	public void startUp() {
		shelves.startUp();
	}

	@Override
	public boolean addNormalShelf(String name) throws Exception {
		return shelves.addNormalShelf(name);
	}

	@Override
	public void addRental(String shelfName, EMediumDTO dto)
			throws Exception {
		
		//gets the correspondent rental from DB with the adapter
		Rental thisRental = adapter.findRentalFromDTO(dto, em);
		
		//delegates the operation
		shelves.addRental(shelfName, thisRental );
	}

	@Override
	public boolean removeShelf(String name) throws Exception {
		return shelves.removeShelf(name);
	}

	@Override 
	public String getMainShelf() throws Exception {
		return shelves.getMainShelf();
	}
	@Override
	public Iterable<String> getShelves() throws Exception {
		return shelves.getShelves();
	}

	@Override
	public boolean addOrRenewRental(EMediumDTO dto) {
		//gets the correspondent lendable from DB with the adapter
		Lendable eMedium = adapter.findLendableFromDTO(dto, em);
		//delegates
		return shelves.addOrRenewRental(eMedium);
	}

	@Override
	public Iterable<EMediumRemote> getShelfRentals(String shelfName) {
		
		try {
			return adapter.eMediumToDTOList(shelves.getShelfRentals(shelfName));
			
		} catch (Exception e) {
			e.printStackTrace();
			EMediumDTO elem = new EMediumDTO();
			elem.setAuthor("erro no getShelfRentals");
			List<EMediumRemote> items = new ArrayList<>();
			items.add(elem);
			return items;
		}
	}

	@Override
	public void removeRental(EMediumRemote dto, String shelfName) {
		
		try {
			//gets the correspondent lendable from DB with the adapter
			Lendable eMedium = adapter.findLendableFromDTO(dto, em);
			//delegates
			shelves.removeRental(shelfName, eMedium);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
