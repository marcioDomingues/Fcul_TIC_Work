package view.swing;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;

import javax.naming.OperationNotSupportedException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import css.AppProperties;
import delegates.BookshelfUIDelegate;
import model.EMedium;

class ShelfDropTarget extends EMediumDropTarget {

	public ShelfDropTarget(JFrame frame, JTree tree, BookshelfUIDelegate uiDelegate) {
		super(frame, tree, uiDelegate);
		new DropTarget(tree, this);
	}
	

	/*
	 * Drop Event Handlers
	 */
	private TreeNode getNodeForEvent(DropTargetDragEvent dtde) {
		Point p = dtde.getLocation();
		DropTargetContext dtc = dtde.getDropTargetContext();
		JTree tree = (JTree) dtc.getComponent();
		TreePath path = tree.getClosestPathForLocation(p.x, p.y);
		return (TreeNode) path.getLastPathComponent();
	}
	

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		TreeNode node = getNodeForEvent(dtde);
		if (!node.isLeaf()) {
			dtde.rejectDrag();
		} else {
			dtde.acceptDrag(dtde.getDropAction());
		}
	}

	
	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		TreeNode node = getNodeForEvent(dtde);
		if (!node.isLeaf()) {
			dtde.rejectDrag();
		} else {
			dtde.acceptDrag(dtde.getDropAction());
		}
	}

	
	@Override
	public void drop(DropTargetDropEvent dtde) {
		Point pt = dtde.getLocation();
		DropTargetContext dtc = dtde.getDropTargetContext();
		JTree tree = (JTree) dtc.getComponent();
		TreePath parentpath = tree.getClosestPathForLocation(pt.x, pt.y);
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) parentpath
				.getLastPathComponent();
		if (!parent.isLeaf()) {
			dtde.rejectDrop();
			return;
		}

		try {
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				if (tr.isDataFlavorSupported(flavors[i])) {
					dtde.acceptDrop(dtde.getDropAction());
					EMedium eMedium = (EMedium) tr.getTransferData(flavors[i]);
					TreeNodeUserData userData = (TreeNodeUserData) parent.getUserObject();
					String shelfName = userData.getCaption();
					if (isLibrarySelected(dtde) && 
							shelfName.equals(AppProperties.INSTANCE.RENTALS_SHELF_NAME)) {
						if (!uiDelegate.addRentalEMedium(eMedium))
							JOptionPane.showMessageDialog(frame, "Cannot rent e-medium!", 
									"Add document error", JOptionPane.ERROR_MESSAGE);
					} else if (!isLibrarySelected(dtde) && 
							!shelfName.equals(AppProperties.INSTANCE.RENTALS_SHELF_NAME)) {
						if (!uiDelegate.addEMediumShelf(shelfName, eMedium))
							JOptionPane.showMessageDialog(frame, "Duplicated e-medium!", 
									"Add document error", JOptionPane.ERROR_MESSAGE);
					} else
						JOptionPane.showMessageDialog(frame, "Need to rent the e-medium first!", 
								"Add document error", JOptionPane.ERROR_MESSAGE);	
					dtde.dropComplete(true);
					return;
				}
			}
		} catch (OperationNotSupportedException e) {
			JOptionPane.showMessageDialog(frame, "Cannot add a document to this shelf", 
					"Add document error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
