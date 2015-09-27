package delegates;

import model.EMediumProperties;
import model.events.ShelfCollectionListener;
//import view.IBookshelfUI;
import model.shelves.EMedium;

/**
 * Delegate that mediates the interaction between ui and domain
 * 
 * @author fmartins, mguimas
 *
 */
public abstract class BookshelfUIDelegate implements ShelfCollectionListener {
	
//	/**
//	 * Reference to the UI's delegate
//	 */
//	private IBookshelfUI bookshelf;
//	
//	
//	/**
//	 * Links the delegate back to its UI
//	 * 
//	 * @param bookshelf The bookshelf UI
//	 */
//	public void setBookshelfUI (IBookshelfUI bookshelf) {
//		this.bookshelf = bookshelf;
//	}
//	
//	
//	/**
//	 * Adds a tree node to the UI for a shelf with name shelfName
//	 * 
//	 * @param shelfName The name of the shelf to be added as a tree node
//	 */
//	protected void addShelfTreeNode (String shelfName) {
//		bookshelf.addShelfTreeNode(shelfName);
//	}
//	
//	
//	/**
//	 * Removes the selected tree node shelf from the tree of shelves
//	 */
//	protected void removeShelfTreeNode (String caption) {
//		bookshelf.removeShelfTreeNode(caption);
//	}
//	
//	
//	/**
//	 * Adds a thumbnail representing the e-medium to the e-medium's panel
//	 * 
//	 * @param EMedium The e-medium whose thumbnail will be added to the panel
//	 */
//	protected void addToEMediaPanel (String target, EMedium eMedium) {
//		bookshelf.addToEMediaPanel(target, eMedium);
//	}
//	
//	
//	/**
//	 * Removes the selected thumbnail from the document's panel
//	 */
//	protected void removeEMediumFromPanel (EMedium eMedium) {
//		bookshelf.removeEMediumFromPanel (eMedium); 
//	}
//	
//	/**
//	 * Grays the selected thumbnail in the document's panel
//	 * @param shelfName 
//	 */
//	protected void returnEMediumFromPanel (EMedium eMedium) {
//		bookshelf.returnEMediumFromPanel (eMedium); 
//	}
	
	/**
	 * Gets shelf's list of e-media
	 * 
	 * @param shelfName The name of the shelf to get the e-media from
	 * @return An iterable with the shelf's e-media.
	 * @throws Exception 
	 */
	public abstract Iterable<EMedium> getShelfRentals(String shelfName) throws Exception;
	
	public abstract Iterable<EMedium> getRentals() throws Exception;


	/**
	 * Gets library's list of e-media
	 * 
	 * @return An iterable with the library's e-media.
	 */
	public abstract Iterable<EMedium> getLibraryEMedia();

	
	/**
	 * Creates a normal shelf
	 * 
	 * @param shelfName The name of the shelf to be added
	 * @return if the shelf was added 
	 */
	public abstract boolean addNormalShelf(String shelfName);

	
	/**
	 * Removes a shelf
	 * 
	 * @param string The name of the shelf to be removed
	 * @throws Exception 
	 */
	public abstract void removeShelf(String shelfName) throws Exception;

	
	/**
	 * Updates document's properties
	 * 
	 * @param document The document to be updated
	 * @param documentProperties The new document properties
	 */
	public abstract void updateRental(EMedium eMedium, EMediumProperties documentProperties);

		
	/**
	 * Removes an e-medium from a shelf
	 * 
	 * @param shelfName The name of the shelf to remove the e-medium from
	 * @param eMedium The e-medium to be removed
	 * @throws Exception 
	 */
	public abstract void removeRental(String shelfName, EMedium eMedium) throws Exception;
	
	
	/**
	 * @return The existent shelves
	 * @throws Exception 
	 */
	public abstract Iterable<String> getShelves() throws Exception;

	
	/**
	 * Adds an e-medium to a shelf
	 * 
	 * @param shelfName The shelf's name
	 * @param eMedium The e-medium to be added
	 * @return if the e-medium was added to the shelf
	 * @throws Exception 
	 */
	public abstract void addRental(String shelfName, EMedium eMedium) throws Exception;

	public abstract EMediumProperties readEMediumProperties(EMedium eMedium) throws Exception;

	public abstract boolean revokeLending(EMedium eMedium) throws Exception;

	public abstract boolean addEMediumLibrary(EMediumProperties properties);

	public abstract boolean rentLendable(EMedium eMedium) throws Exception;

	public abstract void returnEMediumShelf(String shelfName, EMedium eMedium) throws Exception;

	public abstract boolean canBeViewed(EMedium eMedium) throws Exception;

	public abstract void shelfSelected(String selectedShelfName);

}
