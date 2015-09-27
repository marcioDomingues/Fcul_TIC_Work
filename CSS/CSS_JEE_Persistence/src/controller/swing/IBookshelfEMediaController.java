package controller.swing;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import view.IBookshelfUI;

public interface IBookshelfEMediaController {
	
	/**
	 * Links the delegate back to its UI
	 * 
	 * @param bookshelf The bookshelf UI
	 */
	public void setBookshelfUI (IBookshelfUI bookshelfUI);

	/**
	 * @return The thumbnail controller
	 */
	public MouseAdapter thumbnailSelection();	
	public MouseAdapter thumbnailDragMotion();
	public ActionListener showLendableProperties();
	public ActionListener revokeLending();
	public ActionListener showRentalProperties();
	public ActionListener setSlideShowDuration();
	public ActionListener returnRental();
	public ActionListener deleteRental(final String shelfName);
	public ActionListener showEMediumMetadata();
	
}
