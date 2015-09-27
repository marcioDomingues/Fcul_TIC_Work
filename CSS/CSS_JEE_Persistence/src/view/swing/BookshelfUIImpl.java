package view.swing;

import javax.swing.JPanel;
import javax.swing.JTree;

import controller.swing.IBookshelfEMediaController;
import controller.swing.IBookshelfTreeController;
import delegates.BookshelfUIDelegate;

/**
 * @author fmartins
 *
 */
public class BookshelfUIImpl extends BookshelfUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502070157070276115L;
	
	private BookshelfUIDelegate bookshelfUIDelegate;
	
	/**
	 * Create the main frame given the shelves and the library controllers.
	 * @param appProperties 
	 */
	public BookshelfUIImpl(IBookshelfTreeController bookshelfTreeController,
			IBookshelfEMediaController bookshelfEMediaController,
			BookshelfUIDelegate bookshelfUIDelegate) {
		super(bookshelfTreeController, bookshelfEMediaController);
		this.bookshelfUIDelegate = bookshelfUIDelegate;
	}
	

	@Override
	public Iterable<String> getShelves() {
		return bookshelfUIDelegate.getShelves();
	}


	@Override
	public void addShelfDropSupport(JTree shelvesTree) {
		new ShelfDropTarget(this, shelvesTree, bookshelfUIDelegate);		
	}


	@Override
	public void addLibraryDropSupport(JTree shelvesTree, JPanel eMediaPanel) {
		new LibraryDropTarget(this, shelvesTree, eMediaPanel, bookshelfUIDelegate);
	}

}