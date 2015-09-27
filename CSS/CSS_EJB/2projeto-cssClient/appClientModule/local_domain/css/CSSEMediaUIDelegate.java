package local_domain.css;

import java.io.File;


import local_domain.model.EMedium;
import local_domain.model.events.EMediaEvent;
import local_domain.model.events.EMediumListener;
import local_domain.model.rentals.BookRental;
import local_domain.delegates.EMediumUIDelegate;

/**
 * The e-media viewer ui delegate default implementation
 * 
 * @author fmartins
 *
 */
public class CSSEMediaUIDelegate extends EMediumUIDelegate {
	
	private EMedium eMedia;
	private BookRental bookRental;

	private EMediumListener listener;
	
	public CSSEMediaUIDelegate () {
		listener = new EMediumListener () {

			@Override
			public void annotationAdded(EMediaEvent event) {
				updateAnnotationsLabel (event.getPageNum(), true);
			}

			@Override
			public void annotationRemoved(EMediaEvent event) {
				updateAnnotationsLabel (event.getPageNum(), event.isHasAnnotations());
			}

			@Override
			public void bookmarkToggled(EMediaEvent event) {
				updateBookmarkLabel(event.getPageNum(), event.isBookmarked());
			}
		};
	}
	
	public void setEMedia (EMedium eMedia) {
		this.eMedia = eMedia;
		if (eMedia instanceof BookRental)
			bookRental = (BookRental) eMedia;
		else 
			bookRental = null;
	}
	
	@Override
	public void setObservers() {
		eMedia.addEMediumListener(listener);
	}


	@Override
	public void deleteObservers() {
		eMedia.removeEMediumListener(listener);
	}


	@Override
	public void setLastPageVisited(int pageNum) {
		if (bookRental != null)
			bookRental.setLastPageVisited(pageNum);
	}


	@Override
	public boolean isBookmarked(int pageNum) {
		if (bookRental != null)
			return bookRental.isBookmarked(pageNum);
		else
			return false;
	}


	@Override
	public int getLastPageVisited() {
		if (bookRental != null)
			return bookRental.getLastPageVisited();
		else
			return 1;
	}

	@Override
	public File getEMediaFile() {
		return eMedia.getFile();
	}


	@Override
	public boolean hasAnnotations(int pageNum) {
		if (bookRental != null)
			return bookRental.hasAnnotations(pageNum);
		else
			return false;
	}


	@Override
	public void toggleBookmark(int pageNum) {
		if (bookRental != null)
			bookRental.toggleBookmark(pageNum);
	}
	
	@Override
	public EMedium getEMedia() {
		return eMedia;
	}

}
