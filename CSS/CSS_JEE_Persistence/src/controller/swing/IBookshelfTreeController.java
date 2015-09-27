package controller.swing;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.event.TreeSelectionListener;

import view.IBookshelfUI;

public interface IBookshelfTreeController {

	public void setBookshelfUI (IBookshelfUI bookshelfUI);
	public MouseAdapter treeContextMenuOpened ();
	public ActionListener addNormalShelf();
	public ActionListener addSmartShelf();
	public ActionListener deleteShelf();	
	public TreeSelectionListener treeNodeSelection();
	public ActionListener libraryNodeSelected();
	public ActionListener shelfNodeSelected();
	public ActionListener RentalNodeSelected();
}
