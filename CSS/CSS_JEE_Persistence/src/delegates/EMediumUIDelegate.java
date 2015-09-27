package delegates;

import java.io.File;

import model.EMedium;
import view.swing.EMediumShowUI;

/**
 * Delegate that mediates the interaction between a rental viewer and the domain
 * 
 * @author fmartins
 *
 */
public abstract class EMediumUIDelegate {

	/**
	 * Reference to the UI's delegate
	 */
	private EMediumShowUI emediaViewer;
	
	
	/**
	 * Links the delegate back to its UI
	 * 
	 * @param emediaViewer The e-media viewer UI
	 */
	public void setRentalViewerUI (EMediumShowUI emediaViewer) {
		this.emediaViewer = emediaViewer;
	}
	
	
	/**
	 * Updates the bookmark ui for a given page
	 * 
	 * @param pageNum The page number for updating the bookmark
	 * @param active if the bookmark is active
	 */
	protected void updateBookmarkLabel (int pageNum, boolean active) {
		emediaViewer.updateBookmarkLabel (pageNum, active);
	}
	
	
	/**
	 * Updates the annotations ui for a given page
	 * 
	 * @param pageNum The page number for updating the annotations ui
	 * @param hasAnnotations if the page has annotations
	 */
	protected void updateAnnotationsLabel(int pageNum, boolean hasAnnotations) {
		emediaViewer.updatePageActionLabel (pageNum, hasAnnotations);
	}
	
	
	/**
	 * Deletes registered observers
	 */
	public abstract void deleteObservers();
	
	
	/**
	 * Indicates the e-media that the UI delegate is working with
	 * 
	 * @param emedia The e-media this delegate is working with
	 */
	public abstract void setEMedia (EMedium emedia);

	
	/**
	 * Sets the last page visited before e-media viewer closes
	 * 
	 * @param pageNum The last page visited
	 */
	public abstract void setLastPageVisited(int pageNum);


	public abstract int getLastPageVisited();

	/**
	 * Inquires if a given page number is bookmarked
	 * 
	 * @param pageNum The page to inquire
	 * @return if the page is bookmarked
	 */
	public abstract boolean isBookmarked(int pageNum);

	
	/**
	 * Inquires if a given page has annotations
	 * 
	 * @param pageNum The page to inquire 
	 * @return if the page has annotations
	 */
	public abstract boolean hasAnnotations(int pageNum);

	
	/**
	 * @return A File object representing the path of the 
	 * e-media being viewer 
	 */
	public abstract File getEMediaFile();

	
	/**
	 * @return The e-media being shown by the UI associated
	 * with the delegate
	 */
	public abstract EMedium getEMedia();

	
	/**
	 * Toggles (set on if off, otherwise set off) the bookmark 
	 * of a given page
	 * 
	 * @param pageNum The page number to toggle the bookmark
	 */
	public abstract void toggleBookmark(int pageNum);
	

	/**
	 * Establish the convenient observers
	 */
	public abstract void setObservers();
}
