package local_domain.css;

import java.util.LinkedList;


import local_domain.delegates.EMediumMetadataUIDelegate;
import local_domain.model.EMedium;
import local_domain.model.events.EMediaEvent;
import local_domain.model.rentals.BookRental;
import local_domain.model.rentals.Rental;

/**
 * The document's metadata ui delegate default implementation
 * 
 * @author fmartins
 *
 */
public class CSSEMediumMetadataUIDelegate extends EMediumMetadataUIDelegate {
	
	private EMedium document;
	private Rental rental;


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
		if (document.canAnnotatePage())
			return rental.getAnnotations(pageNum);
		else 
			return new LinkedList<String>();
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
		if (rental instanceof BookRental)
			return ((BookRental) document).getAnnotationText(pageNum, annotNum);
		else
			return "";
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
