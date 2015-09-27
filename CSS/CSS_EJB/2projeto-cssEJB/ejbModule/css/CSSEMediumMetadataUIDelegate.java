package css;

import java.util.ArrayList;

import model.events.EMediumEvent;
import model.rentals.AnnotationsFacade;
import model.rentals.BookRental;
import model.rentals.Rental;
import model.shelves.EMedium;
import delegates.EMediumMetadataUIDelegate;

/**
 * The document's metadata ui delegate default implementation
 * 
 * @author fmartins, mguimas
 *
 */
public class CSSEMediumMetadataUIDelegate extends EMediumMetadataUIDelegate {
	
	private EMedium eMedium;
	private final AnnotationsFacade facade;

	public CSSEMediumMetadataUIDelegate (AnnotationsFacade facade) {
		this.facade = facade;
	}
	
	@Override
	public void setEMedium (EMedium eMedium) {
		this.eMedium = eMedium;
	}
	
	@Override
	public EMedium getEMedium() {
		return eMedium;
	}
	
	@Override
	public void setObservers() {
		facade.addEMediumListener(this);
	}
	
	@Override
	public void deleteObservers() {
		facade.removeEMediumListener(this);
	}	

	@Override
	public Iterable<Integer> getDocumentBookmarks() {
		if (eMedium.canBookmarkPage())
			return ((BookRental) eMedium).getBookmarkedPageNumbers();
		else
			return new ArrayList<Integer>();
	}

	@Override
	public Iterable<String> getPageAnnotations(int pageNum) {
		if (eMedium.canAnnotatePage())
			return eMedium instanceof BookRental ?
					((BookRental) eMedium).getAnnotations(pageNum)
					: ((Rental) eMedium).getAnnotations();
		else 
			return new ArrayList<String>();
	}

	@Override
	public String getDocumentTitle() {
		return eMedium.getTitle();
	}

	@Override
	public void addAnnotation(int pageNum, String text) throws Exception {
		if (eMedium instanceof BookRental)
			setEMedium(facade.addAnnotation((BookRental) eMedium, pageNum, text));
		else if (eMedium instanceof Rental)
			setEMedium(facade.addAnnotation((Rental) eMedium, text));
	}

	@Override
	public void removeAnnotation(int pageNum, int annotNum) throws Exception {
		if (eMedium instanceof BookRental)
			setEMedium(facade.removeAnnotation((BookRental) eMedium, pageNum, annotNum));
		else if (eMedium instanceof Rental)
			setEMedium(facade.removeAnnotation((Rental) eMedium, annotNum));
	}

	@Override
	public void toggleBookmark(int pageNum) throws Exception {
		if (eMedium instanceof BookRental)
			setEMedium(facade.toggleBookmark((BookRental) eMedium, pageNum));
	}

	@Override
	public String getAnnotationText(int pageNum, int annotNum) {
		if (eMedium instanceof BookRental)
			return ((BookRental) eMedium).getAnnotationText(pageNum, annotNum);
		else
			return "";
	}

	@Override
	public void annotationAdded(EMediumEvent event) {
		//addAnnotationTreeNode(event.getAnnotationText());
	}

	@Override
	public void annotationRemoved(EMediumEvent event) {
		//removeAnnotationTreeNode(event.getAnnotationNum());
	}

	@Override
	public void bookmarkToggled(EMediumEvent event) {
//		if (event.isBookmarked()) 
//			//addBookmarkTreeNode(event.getPageNum());
//		else 
//			//removeBookmarkTreeNode(event.getPageNum());
	}

}
