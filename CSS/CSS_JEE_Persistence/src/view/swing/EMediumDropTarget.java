package view.swing;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import css.AppProperties;
import delegates.BookshelfUIDelegate;

abstract class EMediumDropTarget implements DropTargetListener {

	final JFrame frame;
	final BookshelfUIDelegate uiDelegate;
	final JTree tree;

	EMediumDropTarget(JFrame frame, JTree tree, BookshelfUIDelegate uiDelegate) {
	    this.tree = tree;
	    this.frame = frame;
	    this.uiDelegate = uiDelegate;
	}

    boolean isLibrarySelected(DropTargetEvent event) {
    	TreePath path = (TreePath) tree.getSelectionPath();
    	if (path == null) {
       		return false;
    	} 		
    	
    	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
    	TreeNodeUserData nodeUserData = (TreeNodeUserData) selectedNode.getUserObject(); 
    	if (nodeUserData.getCaption() == AppProperties.INSTANCE.LIBRARY_NAME) {
       		return true;
    	}

    	return false;
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }


}
