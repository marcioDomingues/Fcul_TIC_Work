package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import persistence.FilePersistence;
import persistence.sqlPersistence;

// TODO: Auto-generated Javadoc
/**
 * The Class StartupWindow.
 */
@SuppressWarnings("serial")
public class StartupWindow extends JFrame implements ActionListener
{
    
    /** The file button. */
    private JButton fileButton;
    
    /** The sql button. */
    private JButton sqlButton;
    
    /** The frame. */
    private JFrame frame;
    
    /** The label. */
    private JLabel label;

    /**
     * Instantiates a new startup window.
     */
    public StartupWindow()
    {
    	frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JPanel inputPanel = new JPanel();
	    
	    label = new JLabel();
	    label.setText("Choose the method to save");
	    inputPanel.add(label);
	    
        fileButton = new JButton("Save to Files");
        fileButton.addActionListener(this);
        fileButton.setActionCommand("file");
        inputPanel.add(fileButton);
        
        sqlButton = new JButton("Save to MySQL");
        sqlButton.addActionListener(this);
        sqlButton.setActionCommand("sql");
        inputPanel.add(sqlButton);
        
        frame.add(inputPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setSize(255, 255);
	    frame.setVisible(true);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();

        if(cmd.equals("file"))
        {
            frame.dispose();
            new Gui( new FilePersistence() );
        }
        
        if(cmd.equals("sql"))
        {
            frame.dispose();
            //Seria um SqlPersistence
            new Gui( new sqlPersistence() );
        }
    }
}