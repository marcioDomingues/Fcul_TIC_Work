package controller.swing;

import javax.naming.OperationNotSupportedException;

import model.EMedium;
import model.EMediumPropertiesData;
import view.IBookshelfUI;
import view.swing.EMediumLabel;
import view.swing.EMediumMetadataUI;
import view.swing.EMediumShowUI;
import delegates.BookshelfUIDelegate;
import delegates.EMediumMetadataUIDelegate;
import delegates.EMediumUIDelegate;

public class BookshelfEMediaControllerImpl extends BookshelfEMediaController {
	
	/**
	 * Reference to the UI's delegate
	 */
	private BookshelfUIDelegate bookshelfUIDelegate;
	private EMediumUIDelegate eMediumUIDelegate;
	private EMediumMetadataUIDelegate eMediumMetadataUIDelegate;
	
	public BookshelfEMediaControllerImpl(BookshelfUIDelegate bookshelfUIDelegate,
			EMediumUIDelegate eMediumUIDelegate, 
			EMediumMetadataUIDelegate eMediumMetadataUIDelegate) {
		this.bookshelfUIDelegate = bookshelfUIDelegate;
		this.eMediumUIDelegate = eMediumUIDelegate;
		this.eMediumMetadataUIDelegate = eMediumMetadataUIDelegate;
	}
	
	@Override
	public void revokeLending(EMedium eMedium) {
		bookshelfUIDelegate.revokeLending(eMedium);
	}

	@Override
	public void returnRental(EMedium eMedium) {
		bookshelfUIDelegate.returnRental(eMedium);		
	}

	@Override
	public void removeEMediumShelf(String shelfName, EMedium eMedium) throws OperationNotSupportedException {
		bookshelfUIDelegate.removeEMediumShelf(shelfName, eMedium);
	}

	@Override
	public void updateRental(EMedium eMedium, EMediumPropertiesData eMediumProperties) {
		bookshelfUIDelegate.updateRental (eMedium, eMediumProperties);
	}

	@Override
	public void eMediumShow(EMediumLabel selectedEMediaLabel) {
		eMediumUIDelegate.setEMedia(selectedEMediaLabel.getEMedium());
		eMediumUIDelegate.setObservers();
		new EMediumShowUI (bookshelfUI, 
				selectedEMediaLabel.getEMediumViewer(), 
				new EMediumShowController(eMediumUIDelegate, 
						eMediumMetadataUIDelegate, selectedEMediaLabel),
				eMediumUIDelegate);
	}

	@Override
	public boolean canBeViewed(EMedium eMedium) {
		return bookshelfUIDelegate.canBeViewed(eMedium);
	}

	@Override
	public void metadataShow(EMediumLabel selectedEMediaLabel, IBookshelfUI bookshelfUI) {
		eMediumMetadataUIDelegate.setEMedium(selectedEMediaLabel.getEMedium());
		new EMediumMetadataUI (bookshelfUI, 1, // show metadata over the first page
				eMediumMetadataUIDelegate, null);
		eMediumMetadataUIDelegate.deleteObservers();
	}	
}
