package delegates;

import javax.naming.OperationNotSupportedException;

import model.EMedium;
import model.EMediumPropertiesData;
import model.events.EMediaCollectionListener;
import model.events.ShelfCollectionListener;
import view.IBookshelfUI;

/**
 * Delegate that mediates the interaction between ui and domain
 * 
 * @author fmartins
 *
 */
public abstract class BookshelfUIDelegate implements EMediaCollectionListener, 
				ShelfCollectionListener {
	
	/**
	 * Reference to the UI's delegate
	 */
	private IBookshelfUI bookshelf;
	
	
	/**
	 * Links the delegate back to its UI
	 * 
	 * @param bookshelf The bookshelf UI
	 */
	public void setBookshelfUI (IBookshelfUI bookshelf) {
		this.bookshelf = bookshelf;
	}
	
	
	/**
	 * Adds a tree node to the UI for a shelf with name shelfName
	 * 
	 * @param shelfName The name of the shelf to be added as a tree node
	 */
	protected void addShelfTreeNode (String shelfName) {
		bookshelf.addShelfTreeNode(shelfName);
	}
	
	
	/**
	 * Removes the selected tree node shelf from the tree of shelves
	 */
	protected void removeShelfTreeNode (String caption) {
		bookshelf.removeShelfTreeNode(caption);
	}
	
	
	/**
	 * Adds a thumbnail representing the e-medium to the e-medium's panel
	 * 
	 * @param EMedium The e-medium whose thumbnail will be added to the panel
	 */
	protected void addToEMediaPanel (String target, EMedium eMedium) {
		bookshelf.addToEMediaPanel(target, eMedium);
	}
	
	
	/**
	 * Removes the selected thumbnail from the document's panel
	 */
	protected void removeEMediumFromPanel (EMedium eMedium) {
		bookshelf.removeEMediumFromPanel (eMedium); 
	}
	
	
	/**
	 * Gets shelf's list of e-media
	 * 
	 * @param shelfName The name of the shelf to get the e-media from
	 * @return An iterable with the shelf's e-media.
	 */
	public abstract Iterable<EMedium> getShelfRentals(String shelfName);
	
	public abstract Iterable<EMedium> getRentals();


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

	public abstract boolean addSmartShelf(String shelfName, String critiria);

	
	/**
	 * Removes a shelf
	 * 
	 * @param string The name of the shelf to be removed
	 */
	public abstract void removeShelf(String shelfName);

	
	/**
	 * Updates document's properties
	 * 
	 * @param document The document to be updated
	 * @param documentProperties The new document properties
	 */
	public abstract void updateRental(EMedium eMedium, EMediumPropertiesData documentProperties);

		
	/**
	 * Removes an e-medium from a shelf
	 * 
	 * @param shelfName The name of the shelf to remove the e-medium from
	 * @param eMedium The e-medium to be removed
	 * @throws OperationNotSupportedException Thrown in case of a special shelf
	 */
	public abstract void removeEMediumShelf(String shelfName, EMedium eMedium) throws OperationNotSupportedException;
	
	
	/**
	 * @return The existent shelves
	 */
	public abstract Iterable<String> getShelves();

	
	/**
	 * Adds an e-medium to a shelf
	 * 
	 * @param shelfName The shelf's name
	 * @param eMedium The e-medium to be added
	 * @return if the e-medium was added to the shelf
	 * @throws OperationNotSupportedException Thrown in case of a special shelf
	 */
	public abstract boolean addEMediumShelf(String shelfName, EMedium eMedium) throws OperationNotSupportedException;

	
	/**
	 * Gets the e-media's title
	 * 
	 * @param eMedium The e-medium to query the title
	 * @return the title of the e-medium
	 */
	public abstract String getEMediumTitle(EMedium eMedium);

	public abstract void returnRental(EMedium eMedia);

	public abstract void revokeLending(EMedium eMedium);

	public abstract boolean addEMediumLibrary(String type, EMediumPropertiesData properties);

	public abstract boolean addRentalEMedium(EMedium eMedium);

	public abstract boolean isRented(EMedium eMedium);

	public abstract boolean canBeViewed(EMedium eMedium);

	public abstract void shelfSelected(String selectedShelfName);
}
