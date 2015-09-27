package controller.swing;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import css.AppProperties;
import model.EMedium;
import view.IBookshelfUI;
import view.swing.TreeNodeUserData;

abstract class BookshelfTreeController implements IBookshelfTreeController {

	/**
	 * Reference to the UI's delegate
	 */
	private IBookshelfUI bookshelfUI;
	private DefaultMutableTreeNode selectedNode;
	
	/**
	 * Links the delegate back to its UI
	 * 
	 * @param bookshelf The bookshelf UI
	 */
	public void setBookshelfUI (IBookshelfUI bookshelfUI) {
		this.bookshelfUI = bookshelfUI;
	}
	
	public MouseAdapter treeContextMenuOpened () {
		return new MouseAdapter () {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getButton() == MouseEvent.BUTTON3) {
					// detect the clicked tree node
					JTree tree = (JTree) event.getSource ();
                    TreePath path = tree.getPathForLocation (event.getX(), event.getY()); 
                    if (path == null)         // if clicked outside a tree node, get the root node
                    	path = tree.getPathForRow(0);
                    else
                    	tree.setSelectionPath(path);
                    // show the context menu
                	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent(); 
                	TreeNodeUserData nodeUserData = (TreeNodeUserData) selectedNode.getUserObject(); 
                	nodeUserData.getContextMenu().show(tree, event.getX(), event.getY());
				}
			}
		};
	}
	
	public ActionListener addNormalShelf () { 
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Container parent = ((JMenuItem) event.getSource()).getParent();
				String shelfName = JOptionPane.showInputDialog(parent, "Shelf name: ", 
						"Add normal shelf", JOptionPane.QUESTION_MESSAGE);
				if (shelfName != null) {
					if (!addNormalShelf(shelfName))
						JOptionPane.showMessageDialog(parent, "Cannot add shelf " + shelfName, 
								"Error adding shelf", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}

	public ActionListener addSmartShelf () { 
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Container parent = ((JMenuItem) event.getSource()).getParent();
				String shelfName = JOptionPane.showInputDialog(parent, "Shelf name: ", 
						"Add Smart Shelf", JOptionPane.QUESTION_MESSAGE);
				String criteria = JOptionPane.showInputDialog(parent, 
						"Choose between: RecentlyAdded, AuthorProfessorCSS", 
						"Specify the critiria", JOptionPane.QUESTION_MESSAGE);
				if (shelfName != null) {
					if (!addSmartShelf(shelfName, criteria))
						JOptionPane.showMessageDialog(parent, "Error adding smart shelf: Repeated Shelf name or invalid Criteria.", 
								"Error adding smart shelf: Repeated Shelf name or repeated Criteria.", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}


	public ActionListener deleteShelf () { 
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
            	TreeNodeUserData nodeUserData = (TreeNodeUserData) selectedNode.getUserObject(); 
				removeShelf (nodeUserData.getCaption());
			}
		};
	}

	
	/**
	 * @param libraryNode The library node
	 * @return The tree controller
	 */
	public TreeSelectionListener treeNodeSelection() {
		return new TreeSelectionListener () {
			@Override
			public void valueChanged(TreeSelectionEvent event) {
				selectedNode = 
					(DefaultMutableTreeNode) event.getNewLeadSelectionPath().getLastPathComponent();
	          	TreeNodeUserData nodeUserData = (TreeNodeUserData) selectedNode.getUserObject(); 
	          	nodeUserData.doAction(selectedNode);	
			}
		};
	}

	public ActionListener libraryNodeSelected () { 
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				shelfSelected(AppProperties.INSTANCE.LIBRARY_NAME);
				bookshelfUI.showEMedia(AppProperties.INSTANCE.LIBRARY_NAME, getLibraryEMedia());
			}
		};
	}

	public ActionListener shelfNodeSelected () { 
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
	           	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) event.getSource(); 
            	TreeNodeUserData nodeUserData = (TreeNodeUserData) selectedNode.getUserObject(); 
				String selectedShelfName = nodeUserData.getCaption();
				shelfSelected(selectedShelfName);
				bookshelfUI.showEMedia(selectedShelfName, getShelfRentals(selectedShelfName));
			}
		};
	}
	
	public ActionListener RentalNodeSelected () { 
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
	           	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) event.getSource(); 
            	TreeNodeUserData nodeUserData = (TreeNodeUserData) selectedNode.getUserObject(); 
				String selectedShelfName = nodeUserData.getCaption();
				shelfSelected(selectedShelfName);
				bookshelfUI.showEMedia(selectedShelfName, getRentals());
			}
		};
	}
	
	public abstract Iterable<EMedium> getShelfRentals(String selectedShelfName);

	public abstract Iterable<EMedium> getRentals();

	public abstract boolean addNormalShelf(String shelfName);
	
	public abstract boolean addSmartShelf(String shelfName, String critiria);

	
	public abstract Iterable<EMedium> getLibraryEMedia();
	
	public abstract void removeShelf(String caption);
	
	public abstract void shelfSelected(String selectedShelfName);
	
}
