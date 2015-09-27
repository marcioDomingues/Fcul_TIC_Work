package view;

import java.awt.EventQueue;

import view.swing.BookshelfUIImpl;
import controller.swing.BookshelfEMediaControllerImpl;
import controller.swing.BookshelfTreeControllerImpl;
import controller.swing.IBookshelfEMediaController;
import controller.swing.IBookshelfTreeController;
import delegates.BookshelfUIDelegate;
import delegates.EMediumMetadataUIDelegate;
import delegates.EMediumUIDelegate;

/**
 * The main UI classe
 * 
 * @author fmartins
 *
 */
public class StartupUI {

	/**
	 * Launch user interface.
	 * @param appProperties 
	 */
	public static void run(final BookshelfUIDelegate bookshelfUIDelegate, 
			final EMediumUIDelegate eMediumUIDelegate, 
			final EMediumMetadataUIDelegate eMediumMetadataUIDelegate) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// create the controllers
					IBookshelfTreeController bookshelfTreeController = new BookshelfTreeControllerImpl(bookshelfUIDelegate);
					IBookshelfEMediaController bookshelfEMediumController = 
							new BookshelfEMediaControllerImpl(bookshelfUIDelegate, 
									eMediumUIDelegate, eMediumMetadataUIDelegate);
					
					// create the UI
					IBookshelfUI bookshelfUI = new BookshelfUIImpl(bookshelfTreeController,
							bookshelfEMediumController,	bookshelfUIDelegate);
					
					// connect the controller to the UI
					bookshelfTreeController.setBookshelfUI(bookshelfUI);
					bookshelfEMediumController.setBookshelfUI(bookshelfUI);
					
					// connect bookshelfDelegate to the UI
					bookshelfUIDelegate.setBookshelfUI(bookshelfUI);			
					
					// let the show begin!!
					bookshelfUI.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
