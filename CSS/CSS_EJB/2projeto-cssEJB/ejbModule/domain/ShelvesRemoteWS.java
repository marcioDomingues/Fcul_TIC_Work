package domain;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import model.shelves.ShelvesInterface;

@Stateless
@WebService
public class ShelvesRemoteWS {

	@EJB
	private ShelvesInterface shelves;

	@WebMethod
	public boolean addNormalShelf(String name) throws Exception {
		return shelves.addNormalShelf(name);
	}

//	//@WebMethod
//	public void addRental(String shelfName, Rental item)
//			throws Exception {
//		
//		//delegates the operation
//		shelves.addRental(shelfName, item );
//	}

	@WebMethod
	public boolean removeShelf(String name) throws Exception {
		return shelves.removeShelf(name);
	}

	@WebMethod 
	public String getMainShelf() throws Exception {
		return shelves.getMainShelf();
	}
	
	@WebMethod
	public List<String> getShelves() throws Exception {
		return (List<String>) shelves.getShelves();
	}

//	//@WebMethod
//	public boolean addOrRenewRental(Rental item) {
//		//delegates
//		return shelves.addOrRenewRental(item);
//	}

//	//@WebMethod
//	public List<Rental> getShelfRentals(String shelfName) throws Exception {
//			List<Rental> r = new ArrayList<>();
//			for ( Rental rrr :  shelves.getShelfRentals(shelfName) )
//				r.add(rrr);
// 
//			return r;
//	}
	
	
	
//	//@WebMethod
//	public void removeRental(Rental item, String shelfName) {
//		try {
//			//gets the correspondent lendable from DB with the adapter
//			shelves.removeRental(shelfName, item);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	

}
