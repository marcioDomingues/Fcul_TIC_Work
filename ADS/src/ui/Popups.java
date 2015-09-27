package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Ordem;

// TODO: Auto-generated Javadoc
/**
 * The Class Popups.
 */
@SuppressWarnings("serial")
public class Popups extends JFrame {
	
	/** The frame. */
	private JFrame frame;
	
	/**
	 * Order already exists.
	 *
	 * @param dim the dim
	 */
	public void orderAlreadyExists(Dimension dim){
		
		frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel inputPanel = new JPanel();
		
		JLabel label = new JLabel();
		label.setText("An order with that ID already exists");
		
		JButton ok = new JButton("ok");
		ok.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
		        frame.dispose();
		        return;
		    }
		});
		
		inputPanel.add(label);
		inputPanel.add(ok);
		
		frame.add(inputPanel, BorderLayout.CENTER);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setSize(250, 100);
		frame.setVisible(true);
	}
	
	/**
	 * Invalid date.
	 *
	 * @param dim the dim
	 */
	public void invalidDate(Dimension dim){
		
		frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel inputPanel = new JPanel();
		
		JLabel label = new JLabel();
		label.setText("Invalid date format. Use dd-MM-yyyy");
		
		JButton ok = new JButton("ok");
		ok.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
		        frame.dispose();
		        return;
		    }
		});
		
		inputPanel.add(label);
		inputPanel.add(ok);
		
		frame.add(inputPanel, BorderLayout.CENTER);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setSize(250, 100);
		frame.setVisible(true);
	}
	
	/**
	 * Invalid number.
	 *
	 * @param dim the dim
	 */
	public void invalidNumber(Dimension dim){
		
		frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel inputPanel = new JPanel();
		
		JLabel label = new JLabel();
		label.setText("Invalid numeric field");
		
		JButton ok = new JButton("ok");
		ok.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
		        frame.dispose();
		        return;
		    }
		});
		
		inputPanel.add(label);
		inputPanel.add(ok);
		
		frame.add(inputPanel, BorderLayout.CENTER);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setSize(150, 100);
		frame.setVisible(true);
	}
	
	/**
	 * Empty fields.
	 *
	 * @param dim the dim
	 */
	public void emptyFields(Dimension dim){
		
		frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel inputPanel = new JPanel();
		
		JLabel label = new JLabel();
		label.setText("Empty fields");
		
		JButton ok = new JButton("ok");
		ok.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
		        frame.dispose();
		        return;
		    }
		});
		
		inputPanel.add(label);
		inputPanel.add(ok);
		
		frame.add(inputPanel, BorderLayout.CENTER);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setSize(100, 100);
		frame.setVisible(true);
	}
	
	/**
	 * Show price.
	 *
	 * @param dim the dim
	 * @param o the o
	 */
	public void showPrice(Dimension dim, Ordem o){
		
		frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel inputPanel = new JPanel();
		
		JLabel label = new JLabel();
		label.setText("Price: "+ (o.getDescdestino().length()+o.getDescOrigem().length()) );
		
		JButton ok = new JButton("ok");
		ok.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
		        frame.dispose();
		        return;
		    }
		});
		
		inputPanel.add(label);
		inputPanel.add(ok);
		
		frame.add(inputPanel, BorderLayout.CENTER);
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setSize(150, 100);
		frame.setVisible(true);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		//new Popups().orderAlreadyExists(new Dimension(2,2));
		//new Popups().invalidDate(new Dimension(2,2));
		//new Popups().invalidNumber(new Dimension(2,2));
		//new Popups().emptyFields(new Dimension(2,2));
		Ordem o = new Ordem(1,1,1,"Rua A", "Rua B", new java.util.Date(),1);
		new Popups().showPrice(new Dimension(2,2), o);
	}
}
