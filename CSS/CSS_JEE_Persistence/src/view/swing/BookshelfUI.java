package view.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.EMedium;
import model.rentals.NoSuchPageException;
import view.IBookshelfUI;
import controller.swing.IBookshelfEMediaController;
import controller.swing.IBookshelfTreeController;
import css.AppProperties;

/**
 * @author fmartins
 *
 */
abstract class BookshelfUI extends IBookshelfUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5592203823306666309L;
	
	private JSplitPane contentPane;
	private JPanel eMediaPanel;
	private JPopupMenu shelfContextMenu;
	private JTree tree;
	private Map<String, DefaultMutableTreeNode> captionToTreeNode;
	private Map<EMedium, EMediumLabel> mediumToLabel;
	private IBookshelfTreeController bookshelfTreeController;
	private IBookshelfEMediaController bookshelfEMediaController;
	
	/**
	 * Create the main frame given the shelves and the library controllers.
	 * @param appProperties 
	 */
	public BookshelfUI(IBookshelfTreeController bookshelfTreeController,
			IBookshelfEMediaController bookshelfEMediaController) {
		this.bookshelfTreeController = bookshelfTreeController;
		this.bookshelfEMediaController = bookshelfEMediaController;
		mediumToLabel = new HashMap<EMedium, EMediumLabel>();
	}
	
	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(AppProperties.INSTANCE.APP_START_X, 
				AppProperties.INSTANCE.APP_START_Y, 
				AppProperties.INSTANCE.APP_START_WIDTH, 
				AppProperties.INSTANCE.APP_START_HEIGHT);
		
		// Create the main panel
		contentPane = new JSplitPane();
		setContentPane(contentPane);
		
		// Set the right documents view, preventing from scrolling horizontally
		eMediaPanel = new JPanel(new ModifiedFlowLayout(FlowLayout.LEFT, 
				AppProperties.INSTANCE.E_MEDIA_GAP_SIZE,
				AppProperties.INSTANCE.E_MEDIA_GAP_SIZE));
		eMediaPanel.setBackground(AppProperties.INSTANCE.E_MEDIA_BACKGROUND_COLOR);
		JScrollPane scrollDocumentsPane = new JScrollPane(eMediaPanel);
		scrollDocumentsPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.setRightComponent(scrollDocumentsPane);
		
		// Set the left tree
		contentPane.setLeftComponent(new JScrollPane(createTree()));
		
		contentPane.setDividerLocation(AppProperties.INSTANCE.DIVIDER_OFFSET);		
		setVisible(true);
	}

	/**
	 * Populates the main tree 
	 * 
	 * @return The application's tree
	 */
	private JTree createTree() {
		// create the context menus
		JPopupMenu treeContextMenu = createTreeContextMenu();
		shelfContextMenu = createShelfContextMenu();

		captionToTreeNode = new HashMap<String, DefaultMutableTreeNode>();

		// create the tree nodes
		String libraryName = AppProperties.INSTANCE.LIBRARY_NAME;
		String shelvesGroupName = AppProperties.INSTANCE.RENTALS_SHELF_GROUP_NAME; 
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(             // the root node
				new TreeNodeUserData(AppProperties.INSTANCE.APP_ROOT_NAME, treeContextMenu, null));
		internalAddShelfNode (libraryName, treeContextMenu, root,             // the library node
				bookshelfTreeController.libraryNodeSelected());	
		internalAddShelfNode (AppProperties.INSTANCE.RENTALS_SHELF_NAME,      // the My Rentals node
				treeContextMenu, root, bookshelfTreeController.RentalNodeSelected());
		internalAddShelfNode (shelvesGroupName, treeContextMenu, root, null); // the shelves group node 
				
		for (String selfName : getShelves()) {					  // a node per shelf
			internalAddShelfNode (selfName, shelfContextMenu, 
					captionToTreeNode.get(shelvesGroupName),
					bookshelfTreeController.shelfNodeSelected());
		}
		
		// create the tree and attaches a controller
		tree = new JTree(root);
		tree.setBackground(Color.white);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(bookshelfTreeController.treeNodeSelection());
		tree.expandPath(new TreePath(captionToTreeNode.get(shelvesGroupName).getPath()));
		tree.setSelectionPath(new TreePath(getTreeNode(libraryName).getPath()));

		// context menu support
		tree.addMouseListener(bookshelfTreeController.treeContextMenuOpened());
		
		// Drag and Drop Support
		addShelfDropSupport(tree);
		
		return tree;
	}

	private DefaultMutableTreeNode getTreeNode(String name) {
		return captionToTreeNode.get(name);
	}
	
	public void addShelfTreeNode(String shelfName) {
		internalAddShelfNode (shelfName, shelfContextMenu, 
				captionToTreeNode.get(AppProperties.INSTANCE.RENTALS_SHELF_GROUP_NAME), 
				bookshelfTreeController.shelfNodeSelected());
		SwingUtilities.updateComponentTreeUI(tree);
	}
	
	public void removeShelfTreeNode (String name) {
		String shelvesGroupName = AppProperties.INSTANCE.RENTALS_SHELF_GROUP_NAME;
		DefaultMutableTreeNode nodeToDelete = captionToTreeNode.get(name);
		DefaultMutableTreeNode shelvesNode = captionToTreeNode.get(shelvesGroupName);
		// find the previous shelf node
		DefaultMutableTreeNode newSelection = (DefaultMutableTreeNode) shelvesNode.getChildBefore(nodeToDelete);
		// if no such node, try to find the next shelf
		if (newSelection == null)
			newSelection = (DefaultMutableTreeNode) shelvesNode.getChildAfter(nodeToDelete);
		// if no shelfs available, show the lib
		if (newSelection == null) 
			newSelection = captionToTreeNode.get(shelvesGroupName);
		shelvesNode.remove(nodeToDelete);
		tree.setSelectionPath(new TreePath (newSelection.getPath()));
		SwingUtilities.updateComponentTreeUI(tree);
	}

	private void internalAddShelfNode(String name, JPopupMenu contextMenu, 
			DefaultMutableTreeNode parent, ActionListener action) {
		DefaultMutableTreeNode shelf = new DefaultMutableTreeNode(
				new TreeNodeUserData(name, contextMenu, action));
		parent.add(shelf);
		captionToTreeNode.put(name, shelf);
	}


	/**
	 * Show the library thumbnails 
	 */
	public void showEMedia (String target, Iterable<EMedium> eMedia) {
		// remove previously added thumbnails
		eMediaPanel.removeAll();
		mediumToLabel.clear();
		
		JPopupMenu contextMenu = getContextMenuForTarget(target);
		
		// add drop support for the library
		addLibraryDropSupport(tree, eMediaPanel);

		// load new thumbnails
		for (EMedium e : eMedia) {
			internalAddToEMediaPanel (e, contextMenu);
		}
		SwingUtilities.updateComponentTreeUI(eMediaPanel);
	}

	public void removeEMediumFromPanel (EMedium eMedium) {
		EMediumLabel label = mediumToLabel.get(eMedium);
		eMediaPanel.remove(label);
		SwingUtilities.updateComponentTreeUI(eMediaPanel);
	}
	
	public void addToEMediaPanel (String target, EMedium eMedia) {
		internalAddToEMediaPanel(eMedia, getContextMenuForTarget(target));
		SwingUtilities.updateComponentTreeUI(eMediaPanel);
	}

	
	private JPopupMenu getContextMenuForTarget(String target) {
		JPopupMenu contextMenu = null;
		if (target == AppProperties.INSTANCE.LIBRARY_NAME) {
			contextMenu = createEMediaLibraryContextMenu();
		} else if (target == AppProperties.INSTANCE.RENTALS_SHELF_NAME)
			contextMenu = createEMediaRentalShelfContextMenu();
		else if (target != AppProperties.INSTANCE.RENTALS_SHELF_GROUP_NAME)
			contextMenu = createEMediaShelfContextMenu(target);
		// at this point contextMenu is never null since clicking
		// on the selves group tree node does not show any media
		return contextMenu;
	}
	
	private void internalAddToEMediaPanel(EMedium eMedium, JPopupMenu contextMenu) {
		try {
			Thumbnail tb = ThumbnailFactory.getInstance().getThumbnail(eMedium);
			EMediumLabel thumbnailLabel = new EMediumLabel(tb.image, eMedium, tb.getViewer(), contextMenu);
			thumbnailLabel.setToolTipText(eMedium.getTitle());
			thumbnailLabel.addMouseListener(bookshelfEMediaController.thumbnailSelection());
			thumbnailLabel.addMouseMotionListener(bookshelfEMediaController.thumbnailDragMotion());
			thumbnailLabel.setTransferHandler(new TransferHandler("EMedium"));
			eMediaPanel.add(thumbnailLabel);
			mediumToLabel.put(eMedium, thumbnailLabel);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchPageException e) {
			JOptionPane.showMessageDialog(this, "Empty document", "Error reading page", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JPopupMenu createTreeContextMenu () {
		JPopupMenu contextMenu = new JPopupMenu ();
		contextMenu.add(createMenuItem("Add normal shelf...", bookshelfTreeController.addNormalShelf()));
		contextMenu.add(createMenuItem("Add smart shelf...", bookshelfTreeController.addSmartShelf()));
		return contextMenu;
	}

	private JPopupMenu createShelfContextMenu () {
		JPopupMenu contextMenu = new JPopupMenu ();
		contextMenu.add(createMenuItem("Delete", bookshelfTreeController.deleteShelf()));
		return contextMenu;
	}
	
	private JPopupMenu createEMediaLibraryContextMenu () {
		JPopupMenu contextMenu = new JPopupMenu ();
		contextMenu.add(createMenuItem("Properties...", bookshelfEMediaController.showLendableProperties()));
		contextMenu.add(createMenuItem("Revoke lending", bookshelfEMediaController.revokeLending()));
		return contextMenu;
	}
	
	private JPopupMenu createEMediaRentalShelfContextMenu () {
		JPopupMenu contextMenu = new JPopupMenu ();
		contextMenu.add(createMenuItem("Metadata...", bookshelfEMediaController.showEMediumMetadata()));
		contextMenu.add(createMenuItem("Slideshow slide duration...", 
				bookshelfEMediaController.setSlideShowDuration()));
		contextMenu.add(createMenuItem("Return rental", bookshelfEMediaController.returnRental()));
		return contextMenu;
	}

	private JPopupMenu createEMediaShelfContextMenu (String shelfName) {
		JPopupMenu contextMenu = new JPopupMenu ();
		contextMenu.add(createMenuItem("Metadata...", bookshelfEMediaController.showEMediumMetadata()));
		contextMenu.add(createMenuItem("Slideshow slide duration...",
				bookshelfEMediaController.setSlideShowDuration()));
		contextMenu.add(createMenuItem("Return rental", bookshelfEMediaController.returnRental()));
		contextMenu.add(createMenuItem("Delete", bookshelfEMediaController.deleteRental(shelfName)));
		return contextMenu;
	}

	private JMenuItem createMenuItem(String caption, ActionListener action) {
		JMenuItem menuItem = new JMenuItem (caption);
		menuItem.addActionListener(action);
		return menuItem;
	}

	public abstract Iterable<String> getShelves();
	
	public abstract void addShelfDropSupport(JTree shelvesTree);

	public abstract void addLibraryDropSupport(JTree shelvesTree, JPanel eMediaPanel);



}