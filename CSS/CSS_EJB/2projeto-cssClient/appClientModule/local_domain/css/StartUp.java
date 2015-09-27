package local_domain.css;

import java.io.IOException;

import java.util.List;

import local_domain.model.CSS;
import local_domain.model.EMedium;
import local_domain.model.lendables.Lendable;
//import model.shelves.criteria.Criterion;
//import model.shelves.criteria.RecentlyAddedCriteria;
import view.StartupUI;

/**
 * The main project class
 * 
 * @author fmartins
 *
 */
public class StartUp {
	
	public static void main(String[] args) throws IOException {
		CSS lei = new CSS();
		
		//Criterion c = new RecentlyAddedCriteria(1000 * 60);  // documents rented last minute
		//lei.shelvesHandler.addSmartShelf("Recently Added", c);	
		StartupUI.run(new CSSBookshelfUIDelegate(lei.shelvesHandler, lei.libraryHandler), 
				new CSSEMediaUIDelegate(), new CSSEMediumMetadataUIDelegate());
	}
}
