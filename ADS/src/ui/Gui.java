package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import persistence.Persistence;
import domain.Ordem;
import domain.ProcessOrders;


/**
 * The Class Gui.
 */
@SuppressWarnings("serial")
public class Gui extends JFrame {

	/** The l volume. */
	static JLabel lIDOrdem, lIDFuncionario, lIDCliente, lDescOrigem, lDescDestino, lDataPedido, lVolume;

	/** The tf volume. */
	static JTextField tfIDOrdem, tfIDFuncionario, tfIDCliente, tfDescOrigem, tfDescDestino,
	tfDataPedido, tfVolume;

	/** The data pedido. */
	static java.util.Date dataPedido;

	// Holds row values for the table
	/** The database results. */
	static Object[][] databaseResults;

	// Holds column names for the table
	/** The columns. */
	static Object[] columns = {"ID Ordem", "ID Funcionario", "ID Cliente", "Morada de Origem",
		"Morada de Destino", "Data", "Volume"};

	// DefaultTableModel defines the methods JTable will use
	// I'm overriding the getColumnClass
	/** The d table model. */
	static DefaultTableModel dTableModel = new DefaultTableModel(databaseResults, columns){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int column) {
			Class returnValue;

			// Verifying that the column exists (index > 0 && index < number of columns
			if ((column >= 0) && (column < getColumnCount())) {
				returnValue = getValueAt(0, column).getClass();
			} else {

				// Returns the class for the item in the column
				returnValue = Object.class;
			}
			return returnValue;
		}
	};

	// Create a JTable using the custom DefaultTableModel
	/** The table. */
	static JTable table = new JTable(dTableModel);

	// Arraylist de Ordens
	/** The po. */
	static ProcessOrders po = new ProcessOrders();


	/**
	 * Instantiates a new gui.
	 *
	 * @param p the p
	 */
	public Gui( final Persistence p ){

		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//final Popups pu = new Popups();

		// Temporarily holds the row results
		Object[] tempRow;

		Iterator<Ordem> ordens = p.getOrdens().iterator();

		while(ordens.hasNext()){
			Ordem o = ordens.next();
			tempRow = new Object[]{o.getIdOrdem(), o.getIdFuncionario(), o.getIdCliente(),
					o.getDescOrigem(), o.getDescdestino(), o.getDataPedido(), o.getVolume()};

			dTableModel.addRow(tempRow);
			po.adicionarOrdem(o);
		}

		///////////////////////////////////////////////////////////////
		//DEFINING JTABLE PERFS 
		///////////////////////////////////////////////////////////////
		// Increase the font size for the cells in the table
		table.setFont(new Font("Serif", Font.PLAIN, 18));

		// Increase the size of the cells to allow for bigger fonts
		table.setRowHeight(table.getRowHeight()+12);

		//set columns width
		table.getColumnModel().getColumn(0).setMinWidth(60);
		table.getColumnModel().getColumn(0).setMaxWidth(80);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(120);
		table.getColumnModel().getColumn(2).setMinWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(120);
		table.getColumnModel().getColumn(5).setMinWidth(100);
		table.getColumnModel().getColumn(5).setMaxWidth(120);
		table.getColumnModel().getColumn(6).setMinWidth(30);
		table.getColumnModel().getColumn(6).setMaxWidth(60);

		table.setShowGrid(true);
		Color color = new Color(200,200,200);
		table.setGridColor(color);

		// Allows the user to sort the data
		table.setAutoCreateRowSorter(true);

		// Adds the table to a scrollpane
		JScrollPane scrollPane = new JScrollPane(table);

		// Adds the scrollpane to the frame
		frame.add(scrollPane, BorderLayout.CENTER);
		///////////////////////////////////////////////////////////////
		//END DEFINING JTABLE PERFS


		///////////////////////////////////////////////////////////////
		//ADDING TO ORDER AND PERSISTENCE
		///////////////////////////////////////////////////////////////
		// Creates a button that when pressed executes the code
		// in the method actionPerformed   	    
		JButton addOrdem = new JButton("Adicionar Ordem");

		addOrdem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				String sIDOrdem = "", sIDFuncionario = "", sIDCliente = "", sDescOrigem = "",
						sDescDestino = "", sDataPedido = "", sVolume = "";

				// getText returns the value in the text field		
				boolean ok = true;

				sIDOrdem = tfIDOrdem.getText();
				if(sIDOrdem.isEmpty())
					ok = false;
				
				sIDFuncionario = tfIDFuncionario.getText();
				if(sIDFuncionario.isEmpty())
					ok = false;
				
				sIDCliente = tfIDCliente.getText();
				if(sIDCliente.isEmpty())
					ok = false;
				
				sDescOrigem = tfDescOrigem.getText();
				if(sDescOrigem.isEmpty())
					ok = false;

				sDescDestino = tfDescDestino.getText();
				if(sDescDestino.isEmpty())
					ok = false;

				sVolume = tfVolume.getText();
				if(sVolume.isEmpty())
					ok = false;

				sDataPedido = tfDataPedido.getText();
				if(sDataPedido.isEmpty())
					ok = false;

				if(ok){
					// Will convert from string to date
					SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

					try {
						dataPedido = dateFormatter.parse(sDataPedido);

						//Adiciona a ordem ao ArrayList
						Ordem o = new Ordem(Integer.parseInt(sIDOrdem), Integer.parseInt(sIDFuncionario),
								Integer.parseInt(sIDCliente), sDescOrigem, sDescDestino,
								dataPedido,Integer.parseInt(sVolume) );

						if (po.adicionarOrdem(o)){
							//adicionar ordem ao ficheiro ordens.cvs
							p.addOrdem(o.toString());

							Object[] ordem = {sIDOrdem, sIDFuncionario, sIDCliente, sDescOrigem,
									sDescDestino, dataPedido, sVolume};

							dTableModel.addRow(ordem);
							new Popups().showPrice(frame.getSize(), o);
						}else{
							new Popups().orderAlreadyExists(frame.getSize());
						}

					} catch (ParseException e1) {
						//data invalida
						new Popups().invalidDate(frame.getSize());
						return;
						//e1.printStackTrace();
					} catch (NumberFormatException e1){
						//campos numericos invalidos
						new Popups().invalidNumber(frame.getSize());
						return;
						//e1.printStackTrace();
					}
				}else{
					//campos vazios
					new Popups().emptyFields(frame.getSize());
				}
				return;
			}
		});
		///////////////////////////////////////////////////////////////
		//END ADDING TO ORDER AND PERSISTENCE


		///////////////////////////////////////////////////////////////
		//DEFINING LABELS AND BUTTONS AND ADDING TO JTABLE AND JPANEL
		///////////////////////////////////////////////////////////////
		// Define values for my labels
		lIDOrdem = new JLabel("ID Ordem");
		lIDFuncionario = new JLabel("ID Funcionario");
		lIDCliente = new JLabel("ID Cliente");
		lDescOrigem = new JLabel("Morada de Origem");
		lDescDestino = new JLabel("Morada de Destino");
		lDataPedido = new JLabel("Data");
		lVolume = new JLabel("Volume");

		// Define the size of text fields
		tfIDOrdem = new JTextField(2);
		tfIDFuncionario = new JTextField(5);
		tfIDCliente = new JTextField(5);
		tfDescOrigem = new JTextField(10);
		tfDescDestino = new JTextField(10);
		tfVolume = new JTextField(2);

		// Set default text and size for text field
		tfDataPedido = new JTextField("dd-MM-yyyy", 8);

		// Create a panel to hold editing buttons and fields
		JPanel inputPanel = new JPanel();
		Font font = new Font("Serif", Font.BOLD, 12);
		Font fontIn = new Font("Serif", Font.PLAIN, 12);

		// Put components in the panel
		inputPanel.add(lIDOrdem).setFont(font);
		inputPanel.add(tfIDOrdem).setFont(fontIn);
		inputPanel.add(lIDFuncionario).setFont(font);
		inputPanel.add(tfIDFuncionario).setFont(fontIn);
		inputPanel.add(lIDCliente).setFont(font);
		inputPanel.add(tfIDCliente).setFont(fontIn);
		inputPanel.add(lDescOrigem).setFont(font);
		inputPanel.add(tfDescOrigem).setFont(fontIn);
		inputPanel.add(lDescDestino).setFont(font);
		inputPanel.add(tfDescDestino).setFont(fontIn);
		inputPanel.add(lDataPedido).setFont(font);
		inputPanel.add(tfDataPedido).setFont(fontIn);
		inputPanel.add(lVolume).setFont(font);
		inputPanel.add(tfVolume).setFont(fontIn);

		//buttons for add and remove 
		inputPanel.add(addOrdem).setFont(font);
		//inputPanel.add(removeOrdem);


		// Add the component panel to the frame
		frame.add(inputPanel, BorderLayout.SOUTH);

		///////////////////////////////////////////////////////////////
		//END DEFINING LABELS AND BUTTONS AND ADDING TO JTABLE AND JPANEL


		frame.setSize(1200, 600);
		frame.setVisible(true);
	}

}
