package css;

import model.lendables.Library;

import model.rentals.AnnotationsFacade;
import model.shelves.Shelves;
//import view.StartupUI;

/**
 * The main project class
 * 
 * @author fmartins
 *
 */
public class StartUp {
	
	public static void main(String[] args) throws Exception {
		Shelves shelves = new Shelves();
		Library library = new Library(shelves);
		AnnotationsFacade facade = new AnnotationsFacade();
//		StartupUI.run(
//				new CSSBookshelfUIDelegate(shelves, library), 
//				new CSSEMediaUIDelegate(facade), 
//				new CSSEMediumMetadataUIDelegate(facade));
	}

}
