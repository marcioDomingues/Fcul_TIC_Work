package view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import model.rentals.NoSuchPageException;
import services.viewer.Viewer;

@SuppressWarnings("serial")
public class DocumentUIFullscreen extends JFrame {

    private JLabel leftPageF;
    private JLabel rightPageF;
	private JPanel twoPagePanel;
	private JPanel onePagePanel;
	private int numPages;
	private JLabel onePage;
	protected Timer timer;
	
	
	private int pageNum;

	private Dimension screenSize;
	private EMediumShowUI documentUI;
	private Viewer eMediumViewer;
	
    
	public DocumentUIFullscreen(Viewer eMediumViewer, int slideDuration, EMediumShowUI documentUI) {
		this.eMediumViewer = eMediumViewer;
		this.documentUI = documentUI;
		
		Toolkit tk = Toolkit.getDefaultToolkit();   
        screenSize = tk.getScreenSize();
		
    	timer = new Timer (slideDuration * 1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pageDown();
			}
		});
    	setBackground(Color.black);
    	
    	this.pageNum = documentUI.getCurrentPage();
    
    	createGUIComponents ();
    	
    	onePage();
    	enterFullScreenMode();
	}
	
	
	private void createGUIComponents() {
		getContentPane().setLayout(new BorderLayout());

		addKeyListener(fullscreenKeyController());
		addMouseListener(fullscreenMouseController());		
		
		createTwoPagePanel();
		createOnePageComponent();
	}

	private void createOnePageComponent() {
		onePagePanel = new JPanel();
		GridBagLayout gbl_panel1 = new GridBagLayout();
		gbl_panel1.columnWidths = new int[]{117, 0};
		gbl_panel1.rowHeights = new int[]{29, 0};
		gbl_panel1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		onePagePanel.setLayout(gbl_panel1);
		
		onePage = new JLabel();
		GridBagConstraints gbc_btnNewButton1 = new GridBagConstraints();
		gbc_btnNewButton1.gridx = 0;
		gbc_btnNewButton1.gridy = 0;
		onePagePanel.add(onePage, gbc_btnNewButton1);
	}

	private void createTwoPagePanel() {
		twoPagePanel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{117, 117, 0};
		gbl_panel.rowHeights = new int[]{29, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		twoPagePanel.setLayout(gbl_panel);
		
		leftPageF = new JLabel();
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		twoPagePanel.add(leftPageF, gbc_btnNewButton);
		
		rightPageF = new JLabel();
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 0;
		twoPagePanel.add(rightPageF, gbc_btnNewButton_1);
	}
	
	public void onePage () {
		numPages = 1;
		getContentPane().remove(twoPagePanel);
		getContentPane().add(onePagePanel, BorderLayout.CENTER);		
		pageNum -= 1;
		pageDown();
		SwingUtilities.updateComponentTreeUI(getContentPane());
	}
	
	public void twoPages () {
		numPages = 2;
		getContentPane().remove(onePagePanel);
		getContentPane().add(twoPagePanel, BorderLayout.CENTER);	
		pageNum -= 2;
		pageDown();
		SwingUtilities.updateComponentTreeUI(getContentPane());
	}
	
	public void enterFullScreenMode () {
        setExtendedState(JFrame.MAXIMIZED_BOTH);  
		setUndecorated(true);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		gd.setFullScreenWindow(this); 
        
        setVisible(true);
    }


	private void pageDown() {
		pageNum += numPages;
		showPages();
	}
	
	public void pageUp() {
		pageNum -= numPages;
		if (pageNum < 1)
			pageNum = 1;
		showPages();
	}
		
    private void showPages () {
    	if (numPages == 2) {
    		try {
				leftPageF.setIcon(new ImageIcon((Image) eMediumViewer.getPage(pageNum, 
						screenSize.width / numPages, screenSize.height)));
			} catch (NoSuchPageException e) {
				// Ignore error in full screen mode
				pageNum -= 2;
			}
    		try {
				rightPageF.setIcon(new ImageIcon((Image) eMediumViewer.getPage(pageNum + 1, 
						screenSize.width / numPages, screenSize.height)));
			} catch (NoSuchPageException e) {
				// Ignore error in full screen mode
				pageNum -= 1;
				System.out.println(pageNum);
				try {
					leftPageF.setIcon(new ImageIcon((Image) eMediumViewer.getPage(pageNum, 
						screenSize.width / numPages, screenSize.height)));
					rightPageF.setIcon(new ImageIcon((Image) eMediumViewer.getPage(pageNum + 1, 
						screenSize.width / numPages, screenSize.height)));
				} catch (NoSuchPageException e1) {
					// One page only?!?!
					pageNum = 1;
					onePage();
				}
			}
    	} else
			try {
				onePage.setIcon(new ImageIcon((Image) eMediumViewer.getPage(pageNum, 
						screenSize.width, screenSize.height)));
			} catch (NoSuchPageException e) {
				// Ignore error in full screen mode
				pageNum -= 1;
			}
    }

    
    // Controllers
    
	private KeyAdapter fullscreenKeyController() {
		return new KeyAdapter () {
        	
        	public void keyPressed(KeyEvent evt) {
        		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        	        gd.setFullScreenWindow(null);
        	        setVisible(false);
        	        documentUI.aboutToExitFull(pageNum);
        	        if (timer.isRunning()) 
        	        	timer.stop();
        	        dispose();
        		} else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        			if (timer.isRunning()) 
        				timer.stop();
        			else
        				timer.start();
        		}
        		else if (evt.getKeyCode() == KeyEvent.VK_1)
        			onePage();
        		else if (evt.getKeyCode() == KeyEvent.VK_2)
        			twoPages();
        	}
        };
	}

	private MouseAdapter fullscreenMouseController() {
		return new MouseAdapter() {
        	
        	public void mouseClicked (MouseEvent e) {
        		if (e.getButton() == MouseEvent.BUTTON1) 
        			pageDown();
        		else if(e.getButton() == MouseEvent.BUTTON3) 
        			pageUp();
        	}
        };
	}
}
