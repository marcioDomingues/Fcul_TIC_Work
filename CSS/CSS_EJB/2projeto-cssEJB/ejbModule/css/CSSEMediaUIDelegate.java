package css;

import java.io.File;

import model.events.EMediumEvent;
import model.events.EMediumListener;
import model.rentals.AnnotationsFacade;
import model.rentals.BookRental;
import model.shelves.EMedium;
import delegates.EMediumUIDelegate;

/**
 * The e-media viewer ui delegate default implementation
 * 
 * @author fmartins, mguimas
 *
 */
public class CSSEMediaUIDelegate extends EMediumUIDelegate {
	
	private EMedium eMedium;
	private final AnnotationsFacade facade;

	private EMediumListener listener;
	
	public CSSEMediaUIDelegate (AnnotationsFacade facade) {
		this.facade = facade;
		listener = new EMediumListener () {
			@Override
			public void annotationAdded(EMediumEvent event) {
				//updateAnnotationsLabel (event.getPageNum(), true);
			}

			@Override
			public void annotationRemoved(EMediumEvent event) {
				//updateAnnotationsLabel (event.getPageNum(), event.hasAnnotations());
			}

			@Override
			public void bookmarkToggled(EMediumEvent event) {
				//updateBookmarkLabel(event.getPageNum(), event.isBookmarked());
			}
		};
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
		facade.addEMediumListener(listener);
	}

	@Override
	public void deleteObservers() {
		facade.removeEMediumListener(listener);
	}

	@Override
	public void setLastPageVisited(int pageNum) {
		if (eMedium instanceof BookRental)
			((BookRental) eMedium).setLastPageVisited(pageNum);
	}

	@Override
	public boolean isBookmarked(int pageNum) {
		return eMedium instanceof BookRental ? ((BookRental) eMedium).isBookmarked(pageNum) : false;
	}

	@Override
	public int getLastPageVisited() {
		return eMedium instanceof BookRental ? ((BookRental) eMedium).getLastPageVisited() : 1;
	}

	@Override
	public File getEMediaFile() {
		return eMedium.getFile();
	}

	@Override
	public boolean hasAnnotations(int pageNum) {
		return eMedium instanceof BookRental ? ((BookRental) eMedium).hasAnnotations(pageNum) : false;
	}

	@Override
	public void toggleBookmark(int pageNum) throws Exception {
		if (eMedium instanceof BookRental)
			setEMedium(facade.toggleBookmark(((BookRental) eMedium), pageNum));
	}

}
