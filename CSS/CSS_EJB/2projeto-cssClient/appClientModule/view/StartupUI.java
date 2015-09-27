package view;

import java.awt.EventQueue;


import view.swing.BookshelfUIImpl;
import local_domain.controller.swing.BookshelfEMediaControllerImpl;
import local_domain.controller.swing.BookshelfTreeControllerImpl;
import local_domain.controller.swing.IBookshelfEMediaController;
import local_domain.controller.swing.IBookshelfTreeController;
import local_domain.delegates.BookshelfUIDelegate;
import local_domain.delegates.EMediumMetadataUIDelegate;
import local_domain.delegates.EMediumUIDelegate;

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
