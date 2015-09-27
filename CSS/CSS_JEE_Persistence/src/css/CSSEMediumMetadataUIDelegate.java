package css;

import java.util.LinkedList;

import model.EMedium;
import model.events.EMediaEvent;
import model.lendables.Lendable;
import model.rentals.BookRental;
import model.rentals.Rental;
import delegates.EMediumMetadataUIDelegate;

/**
 * The document's metadata ui delegate default implementation
 * 
 * @author fmartins
 *
 */
public class CSSEMediumMetadataUIDelegate extends EMediumMetadataUIDelegate {
	
	private EMedium document;
	private Rental rental;

	private final int COVER_PAGE = 1;

	public CSSEMediumMetadataUIDelegate () {
		
	}
	
	public void setEMedium (EMedium document) {
		if (document != null)
			document.removeEMediumListener(this);
		this.document = document;
		if (document instanceof Rental)
			rental = (Rental) document;
		else
			rental = null;
		document.addEMediumListener(this);
	}
	
	@Override
	public void deleteObservers() {
		document.removeEMediumListener(this);
	}

	@Override
	public Iterable<Integer> getDocumentBookmarks() {
		if (document.canBookmarkPage())
			return ((BookRental) document).getBookmarks();
		else
			return new LinkedList<Integer>();
	}

	@Override
	public Iterable<String> getPageAnnotations(int pageNum) {
		Iterable<String> annotations = null; 
		if ( document instanceof Lendable )
			return new LinkedList<String>();
		
		if (document.canAnnotatePage() || pageNum == COVER_PAGE) {
			annotations = rental.getAnnotations(pageNum);
		}
		
		return annotations == null ? new LinkedList<String>() : annotations;
	}

	@Override
	public String getDocumentTitle() {
		return document.getTitle();
	}

	@Override
	public void addAnnotation(int pageNum, String text) {
		if (rental != null)
			if (rental instanceof BookRental)
				((BookRental) rental).addAnnotation(pageNum, text);
			else
				rental.addAnnotation(text);
	}

	@Override
	public void removeAnnotation(int pageNum, int annotNum) {
		if (rental != null)
			if (rental instanceof BookRental)
				((BookRental) rental).removeAnnotation(pageNum, annotNum);
			else
				rental.removeAnnotation(annotNum);
	}

	@Override
	public void toggleBookmark(int pageNum) {
		if (rental instanceof BookRental)
			((BookRental) document).toggleBookmark(pageNum);
	}

	@Override
	public String getAnnotationText(int pageNum, int annotNum) {
		if (rental instanceof BookRental )
			return ((BookRental) document).getAnnotationText(pageNum, annotNum);
		else
			return ((Rental) document).getAnnotationText(annotNum);
	}

	@Override
	public void annotationAdded(EMediaEvent event) {
		addAnnotationTreeNode(event.getAnnotationText());
	}

	@Override
	public void annotationRemoved(EMediaEvent event) {
		removeAnnotationTreeNode(event.getAnnotationNum());
	}

	@Override
	public void bookmarkToggled(EMediaEvent event) {
		if (event.isBookmarked()) 
			addBookmarkTreeNode(event.getPageNum());
		else 
			removeBookmarkTreeNode(event.getPageNum());
	}
}
