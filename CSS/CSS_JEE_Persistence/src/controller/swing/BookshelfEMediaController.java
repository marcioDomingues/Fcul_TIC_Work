package controller.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.naming.OperationNotSupportedException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import javax.swing.border.MatteBorder;

import model.EMedium;
import model.EMediumPropertiesData;
import services.viewer.Viewer;
import view.IBookshelfUI;
import view.swing.DialogData;
import view.swing.EMediumLabel;
import view.swing.EMediumPropertiesUI;

abstract class BookshelfEMediaController implements IBookshelfEMediaController {
	
	/**
	 * Reference to the UI's delegate
	 */
	IBookshelfUI bookshelfUI;
	private EMediumLabel selectedEMediaLabel;
	
	/**
	 * Links the delegate back to its UI
	 * 
	 * @param bookshelf The bookshelf UI
	 */
	public void setBookshelfUI (IBookshelfUI bookshelfUI) {
		this.bookshelfUI = bookshelfUI;
	}

	/**
	 * @return The thumbnail controller
	 */
	public MouseAdapter thumbnailSelection() {
		return new MouseAdapter () {			
			@Override
			public void mouseClicked(MouseEvent event) {
				if (selectedEMediaLabel != null)
					selectedEMediaLabel.setBorder(null);
				selectedEMediaLabel = (EMediumLabel) event.getSource();
				selectedEMediaLabel.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.YELLOW));
				if (event.getButton() == MouseEvent.BUTTON1) {
					if (event.getClickCount() >= 2) {
						// on double click, view the document
						if (canBeViewed(selectedEMediaLabel.getEMedium()))
							if (selectedEMediaLabel.getEMediumViewer() != null) {
								eMediumShow(selectedEMediaLabel);
							} else
								JOptionPane.showMessageDialog(bookshelfUI, "Cannot obtain a viewer for this type of document", 
									"Error obtaining viewer for document", JOptionPane.ERROR_MESSAGE);
						else
							JOptionPane.showMessageDialog(bookshelfUI, "You do not have a valid rental for the e-medium", 
									"Error viewing the e-medium", JOptionPane.ERROR_MESSAGE);							
					}
				}
				else if (event.getButton() == MouseEvent.BUTTON3) {
					selectedEMediaLabel = (EMediumLabel) event.getSource();
					JPopupMenu contextMenu = selectedEMediaLabel.getContextMenu(); 
					contextMenu.show (selectedEMediaLabel, event.getX(), event.getY());
				}
		      }
		};
	}
	
	public MouseAdapter thumbnailDragMotion() {
		return new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				JComponent jc = (JComponent) event.getSource();
				TransferHandler th = jc.getTransferHandler();
				th.exportAsDrag(jc, event, TransferHandler.COPY);
			}
		};
	}	

	
	public ActionListener showLendableProperties() {
		return new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent event) {
				new EMediumPropertiesUI(bookshelfUI, 
						new DialogData(selectedEMediaLabel.getEMedium().getEMediumProperties()), true);
			}
		};
	}

	public ActionListener revokeLending() {
		return new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent event) {
				revokeLending(selectedEMediaLabel.getEMedium());
			}
		};
	}

	public ActionListener showRentalProperties() {
		return new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent event) {
				EMediumPropertiesData eMediumProperties = selectedEMediaLabel.getEMedium().getEMediumProperties();
				new EMediumPropertiesUI(bookshelfUI, new DialogData(eMediumProperties), true);
				updateRental (selectedEMediaLabel.getEMedium(), eMediumProperties);
			}
		};
	}

	public ActionListener setSlideShowDuration() {
		return new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent event) {
				Viewer viewer = selectedEMediaLabel.getEMediumViewer();
				if (viewer != null && viewer.canSlideshow()) {
					String sec = (String) JOptionPane.showInputDialog(selectedEMediaLabel.getParent(), 
							"Slide duration (secs):", "Slideslow definitions", 
							JOptionPane.QUESTION_MESSAGE, null, null, selectedEMediaLabel.getSlideDuration());
					try {
						int secs = new Integer(sec);
						if (secs <= 0) {
							JOptionPane.showMessageDialog(selectedEMediaLabel.getParent(), 
									"Invalid number of seconds", "Invalid data", JOptionPane.ERROR_MESSAGE);
						} else
							selectedEMediaLabel.setSlideDuration(secs);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(selectedEMediaLabel.getParent(), 
								"Invalid number of seconds", "Invalid data", JOptionPane.ERROR_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(selectedEMediaLabel.getParent(), 
							"Medium cannot be slideshown", "Slideshow error", JOptionPane.ERROR_MESSAGE);
			}		
		};
	}

	public ActionListener returnRental() {
		return new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent event) {
				returnRental(selectedEMediaLabel.getEMedium());
			}
		};
	}
	
	public ActionListener deleteRental(final String shelfName) {
		return new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					removeEMediumShelf(shelfName, selectedEMediaLabel.getEMedium());
				} catch (OperationNotSupportedException e) {
					JOptionPane.showMessageDialog(selectedEMediaLabel.getParent(),
						    "E-medium cannot be removed from the shelf.",
						    "Remove document error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}	
	
	
	@Override
	public ActionListener showEMediumMetadata() {
		return new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent event) {
				metadataShow(selectedEMediaLabel, bookshelfUI);
			}
		};
	}

	public abstract void revokeLending(EMedium eMedium);

	public abstract void returnRental(EMedium eMedium);

	public abstract void removeEMediumShelf(String shelfName, EMedium eMedium) throws OperationNotSupportedException;

	public abstract void updateRental(EMedium eMedium, EMediumPropertiesData eMediumProperties);

	public abstract void eMediumShow(EMediumLabel selectedEMediaLabel);
	
	public abstract boolean canBeViewed(EMedium eMedium);

	public abstract void metadataShow(EMediumLabel selectedEMediaLabel, IBookshelfUI bookshelfUI);

}
