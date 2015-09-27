package view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controller.swing.EMediumShowController;
import css.AppProperties;
import delegates.EMediumUIDelegate;
import model.rentals.NoSuchPageException;
import services.viewer.Viewer;

public class EMediumShowUI {

	private JFrame frame; 
	private Viewer eMediumViewer;
	private EMediumUIDelegate eMediumUIDelegate;
	private int pageNum;
	private JLabel eMediumJLabel;
	private int documentsPanelWidth;
	private int documentsPanelHeight;
	private Component oldRightComponent;
	private JLabel pageActionLabel;
	private JLabel bookmarkLabel;
	private EMediumShowController eMediumShowController;
	
	public EMediumShowUI (JFrame frame, Viewer eMediumViewer, 
			EMediumShowController eMediumShowController,
			EMediumUIDelegate eMediumUIDelegate) {
	
		this.frame = frame;
		this.eMediumViewer = eMediumViewer;
		this.eMediumShowController = eMediumShowController;
		this.eMediumUIDelegate = eMediumUIDelegate;

		// register the UI with its delegate
		eMediumShowController.setEMediumShowUI(this, frame);
		eMediumUIDelegate.setRentalViewerUI(this);
		
		createGUIComponents ();
		activateDocumentViewer();
		
		pageNum = Math.max(eMediumUIDelegate.getLastPageVisited() - 1, 0); 
		pageDown();
	}


	private void activateDocumentViewer() {
		try {
			eMediumViewer.setDocument(eMediumUIDelegate.getEMediaFile());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Cannot read document", 
					"IO Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	private void createGUIComponents() {
		JSplitPane contentPane = (JSplitPane) frame.getContentPane();
		oldRightComponent = contentPane.getRightComponent();
		Rectangle paneSize = oldRightComponent.getBounds();
		documentsPanelWidth = paneSize.width - AppProperties.INSTANCE.E_MEDIA_GAP_SIZE;
		documentsPanelHeight = paneSize.height - AppProperties.INSTANCE.E_MEDIA_GAP_SIZE * 2;

		JPanel documentPanel = createDocumentPanel();
		contentPane.setRightComponent(documentPanel);
		
		createActionsPanel(documentPanel);
	}


	private JPanel createDocumentPanel() {
		JPanel documentPanel = new JPanel(new BorderLayout(0, 0));
		documentPanel.setBackground(Color.black);
		
		eMediumJLabel = new JLabel ();
		eMediumJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		eMediumJLabel.addMouseListener(eMediumShowController.pageSelection());

		documentPanel.add(eMediumJLabel, BorderLayout.CENTER);

		return documentPanel;
	}

	private void createActionsPanel(JPanel documentPanel) {		
		JPanel actionsPanel = new JPanel();
		actionsPanel.setBackground(Color.black);
		actionsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		FlowLayout fl_panel = new FlowLayout(FlowLayout.RIGHT, 0, 0);
		actionsPanel.setLayout(fl_panel);
		documentPanel.add(actionsPanel, BorderLayout.NORTH);
		
		bookmarkLabel = new JLabel();
		updateBookmarkLabel(pageNum, eMediumUIDelegate.isBookmarked(pageNum));
		bookmarkLabel.addMouseListener(eMediumShowController.bookmarkAction());
		actionsPanel.add(bookmarkLabel);

		JLabel fullScreenLabel = new JLabel(AppProperties.INSTANCE.FULLSCREEN_ICON);
		fullScreenLabel.addMouseListener(eMediumShowController.fullScreenAction());
		actionsPanel.add(fullScreenLabel);
		
		pageActionLabel = new JLabel();
		updatePageActionLabel(pageNum, eMediumUIDelegate.hasAnnotations(pageNum));
		pageActionLabel.addMouseListener(eMediumShowController.pageAction());
		actionsPanel.add(pageActionLabel);			
		
		JLabel closeLabel = new JLabel(AppProperties.INSTANCE.CLOSE_ICON);
		closeLabel.addMouseListener(eMediumShowController.closeAction());
		actionsPanel.add(closeLabel);
	}

	
	public void showPage (int pageNum) throws NoSuchPageException {
		this.pageNum = pageNum;
   		eMediumJLabel.setIcon(new ImageIcon((Image) eMediumViewer.getPage(pageNum, 
   				documentsPanelWidth, documentsPanelHeight)));
		updateBookmarkLabel(pageNum, eMediumUIDelegate.isBookmarked(pageNum));
		updatePageActionLabel(pageNum, eMediumUIDelegate.hasAnnotations(pageNum));
	}
	
	public void closeEMediumShowUI() {
		((JSplitPane) EMediumShowUI.this.frame.getContentPane()).setRightComponent(oldRightComponent);
        SwingUtilities.updateComponentTreeUI(EMediumShowUI.this.frame.getContentPane());
	}
	
	public void pageDown() {
    	try {
			showPage(++pageNum);
		} catch (NoSuchPageException e) {
			JOptionPane.showMessageDialog(frame, "End of document", "Error reading page", JOptionPane.ERROR_MESSAGE);
			pageNum--;
		}
	}
	
	public void pageUp() {
		try {
			showPage (--pageNum);
		} catch (NoSuchPageException e) {
			JOptionPane.showMessageDialog(frame, "Begin of document", "Error reading page", JOptionPane.ERROR_MESSAGE);
			pageNum++;
		}
	}
		
	public int getCurrentPage() {
		return pageNum;
	}

	public void updatePageActionLabel(int pageN, boolean active) {
		if (pageNum == pageN)
			pageActionLabel.setIcon(active ? AppProperties.INSTANCE.PAGEINFO_ICON_TRUE : 
				AppProperties.INSTANCE.PAGEINFO_ICON_FALSE);
	}

	public void updateBookmarkLabel(int pageN, boolean active) {
		if (pageNum == pageN)
			bookmarkLabel.setIcon (active ? AppProperties.INSTANCE.BOOKMARK_ICON_TRUE : 
				AppProperties.INSTANCE.BOOKMARK_ICON_FALSE);
	}
	
	public void aboutToExitFull(int pageNum) {
		this.pageNum = pageNum - 1;
		pageDown();
		frame.setVisible(true);
	}
	
}
