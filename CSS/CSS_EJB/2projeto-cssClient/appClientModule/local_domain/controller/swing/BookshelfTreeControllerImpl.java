package local_domain.controller.swing;

import local_domain.delegates.BookshelfUIDelegate;
import local_domain.model.EMedium;

public class BookshelfTreeControllerImpl extends BookshelfTreeController {

	private BookshelfUIDelegate bookshelfUIDelegate;
	
	public BookshelfTreeControllerImpl(BookshelfUIDelegate bookshelfUIDelegate) {
		this.bookshelfUIDelegate = bookshelfUIDelegate;
	}
	
	@Override
	public Iterable<EMedium> getShelfRentals(String selectedShelfName) {
		return bookshelfUIDelegate.getShelfRentals(selectedShelfName);
	}

	@Override
	public Iterable<EMedium> getRentals() {
		return bookshelfUIDelegate.getRentals();
	}

	@Override
	public boolean addNormalShelf(String shelfName) {
		return bookshelfUIDelegate.addNormalShelf(shelfName);
	}

	@Override
	public Iterable<EMedium> getLibraryEMedia() {
		return bookshelfUIDelegate.getLibraryEMedia();
	}

	@Override
	public void removeShelf(String caption) {
		bookshelfUIDelegate.removeShelf (caption);
	}

	@Override
	public void shelfSelected(String selectedShelfName) {
		bookshelfUIDelegate.shelfSelected (selectedShelfName);
	}

}
