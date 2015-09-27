package view.swing;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingWorker;

import delegates.BookshelfUIDelegate;
import model.EMediumAttribute;

class LibraryDropTarget extends EMediumDropTarget {

	public LibraryDropTarget(JFrame frame, JTree tree, JPanel documentsPanel,
			BookshelfUIDelegate uiDelegate) {
		super(frame, tree, uiDelegate);
	    new DropTarget(documentsPanel, this);
	}

	@Override
    public void dragEnter(DropTargetDragEvent event) {
    	if (!isLibrarySelected(event)) {
    		event.rejectDrag();
    		return;
    	}
   		
       	boolean supportedFlavor = false;
    	DataFlavor [] dataFlavors = event.getCurrentDataFlavors();
    	for (int i = 0; i < dataFlavors.length && !supportedFlavor; i++)
    		supportedFlavor = dataFlavors[i].equals(DataFlavor.javaFileListFlavor);   		
 
    	if (supportedFlavor)
			event.acceptDrag(DnDConstants.ACTION_COPY);
    	else 
    		event.rejectDrag();
    }

	@Override
    public void dragOver(DropTargetDragEvent event) {
    	if (!isLibrarySelected(event)) {
       		event.rejectDrag();
       		return;
    	}

    	event.acceptDrag(DnDConstants.ACTION_COPY);
    }

    
	@SuppressWarnings("unchecked")
	@Override
	public void drop(DropTargetDropEvent event) {
		Transferable tr = event.getTransferable();
		if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			event.acceptDrop(DnDConstants.ACTION_COPY);
			final List<File> files;
			try {
				files = new LinkedList<File>((List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor));
				event.dropComplete(true);
				
				// asks info about files is a separate thread, so drop can conclude.
				new SwingWorker<Void, Void> () {

					@Override
					protected Void doInBackground() throws Exception {
						for (File f : files) {
							DialogData dialogData = new DialogData();
							dialogData.data.addAttribute(EMediumAttribute.PATH, f.getAbsolutePath());
							new EMediumPropertiesUI(frame, dialogData, false);
							if (dialogData.didUserAccept()) {
									boolean resultAdd = uiDelegate.addEMediumLibrary(
											(String) dialogData.data.getAttribute(EMediumAttribute.MEDIUM_TYPE), 
											dialogData.data); 
									if (!resultAdd)
										JOptionPane.showMessageDialog(frame, "Duplicated document!", 
							    			"Add document error", JOptionPane.ERROR_MESSAGE);
							}
						}
						return null;
					}
				}.execute();
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				event.rejectDrop();
			}
			
		} else
			event.rejectDrop();
	  }
	}
