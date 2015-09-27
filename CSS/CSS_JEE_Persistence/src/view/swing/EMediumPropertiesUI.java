package view.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import model.EMediumAttribute;
import model.EMediumType;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


@SuppressWarnings("serial")
public class EMediumPropertiesUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField titleTextField;
	private JTextField authorTextField;
	private JTextField mimeTypeTextField;
	private JTable table;
    private boolean addRow;

	private DialogData dialogData;

	private DefaultTableModel tableModel;
	private JTextField languageTextField;

	private JLabel lblPath;

	private JComboBox<String> eMediumTypeList;

	private JTextField numberLicensesTextField;

	/**
	 * Create the dialog.
	 * @param f 
	 * @param loadData 
	 * @param targetPanel 
	 */
	@SuppressWarnings({"unchecked"})
	public EMediumPropertiesUI(final JFrame parent, DialogData dialogData, boolean loadData) {
		super (parent, true);
		this.dialogData = dialogData;
		setBounds(100, 100, 520, 440);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("16px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("83px"),}));
		
		String[] eMediumTypes = new String[EMediumType.values().length];
		int i = 0;
		for (EMediumType t : EMediumType.values()) {
			eMediumTypes[i] = t.toString();
			i++;
		}
		 
        //Create the combo box, select the item at index 4.
        //Indices start at 0, so 4 specifies the pig.
		JLabel lblTitle = new JLabel("E-medium type:");
		contentPanel.add(lblTitle, "2, 2, right, center");	
		
		eMediumTypeList = new JComboBox<String>(eMediumTypes);
		contentPanel.add(eMediumTypeList, "4, 2, left, center");
		
		lblTitle = new JLabel("Title:");
		contentPanel.add(lblTitle, "2, 4, right, center");
		
		titleTextField = new JTextField();
		contentPanel.add(titleTextField, "4, 4, fill, top");
		titleTextField.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author:");
		contentPanel.add(lblAuthor, "2, 6, right, center");
		
		authorTextField = new JTextField();
		contentPanel.add(authorTextField, "4, 6, fill, top");
		authorTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Language:");
		contentPanel.add(lblNewLabel_1, "2, 8, right, default");
		
		languageTextField = new JTextField();
		contentPanel.add(languageTextField, "4, 8, fill, default");
		languageTextField.setColumns(10);
		
		JLabel lblDocumentType = new JLabel("Type:");
		contentPanel.add(lblDocumentType, "2, 10, right, center");
		
		mimeTypeTextField = new JTextField();
		contentPanel.add(mimeTypeTextField, "4, 10, fill, top");
		mimeTypeTextField.setColumns(10);
		
		JLabel lblNumberLicences = new JLabel("Number of Licenses:");
		contentPanel.add(lblNumberLicences, "2, 12, right, center");
		
		numberLicensesTextField = new JTextField();
		contentPanel.add(numberLicensesTextField, "4, 12, fill, top");
		numberLicensesTextField.setColumns(10);

		
		JLabel lblFile = new JLabel("File:");
		contentPanel.add(lblFile, "2, 14, right, top");
		
		lblPath = new JLabel((String) dialogData.getAttribute(EMediumAttribute.PATH));
		contentPanel.add(lblPath, "4, 14, fill, top");
		
		JLabel lblTags = new JLabel("Tags");
		contentPanel.add(lblTags, "2, 16, left, top");
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("tags");
		
		if (loadData) {			
			eMediumTypeList.setSelectedItem((String) dialogData.getAttribute(EMediumAttribute.MEDIUM_TYPE));
			titleTextField.setText((String) dialogData.getAttribute(EMediumAttribute.TITLE));
			authorTextField.setText((String) dialogData.getAttribute(EMediumAttribute.AUTHOR));
			languageTextField.setText((String) dialogData.getAttribute(EMediumAttribute.LANGUAGE));
			mimeTypeTextField.setText((String) dialogData.getAttribute(EMediumAttribute.MIME_TYPE));
			numberLicensesTextField.setText(dialogData.getAttribute(EMediumAttribute.LICENSES).toString());
			for (String tag : (List<String>) dialogData.getAttribute(EMediumAttribute.TAGS)) {
				String [] row = {tag};
				tableModel.addRow(row);
			}
		}
		
		if (tableModel.getRowCount() == 0) {
			String [] newTableRowData = {"double click to add a tag..."};
			tableModel.addRow(newTableRowData);
		} else {
		    String [] newTableRowData = {""};
		    tableModel.addRow(newTableRowData);
		}
		
		table = new JTable(tableModel);
		table.setTableHeader(null);
		table.setBorder(UIManager.getBorder("TextField.border"));
		
		table.addMouseListener(new MouseAdapter () {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				addRow = true;
			}			
		});
		
		tableModel.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int rowCount = tableModel.getRowCount() - 1;
				if (addRow && rowCount == e.getFirstRow()) {
					addRow = false;
					String [] nextRow = {""};
					tableModel.addRow(nextRow);
					table.setRowSelectionInterval(rowCount + 1, rowCount + 1);
				}
			}
		});
		
		contentPanel.add(new JScrollPane(table), "2, 18, 3, 1, fill, fill");
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{	
			ActionListener al = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("Create")) {
						EMediumPropertiesUI.this.dialogData.addAttribute(EMediumAttribute.PATH, lblPath.getText());
						EMediumPropertiesUI.this.dialogData.addAttribute(EMediumAttribute.TITLE, titleTextField.getText());
						EMediumPropertiesUI.this.dialogData.addAttribute(EMediumAttribute.AUTHOR, authorTextField.getText());
						EMediumPropertiesUI.this.dialogData.addAttribute(EMediumAttribute.LANGUAGE,languageTextField.getText());
						EMediumPropertiesUI.this.dialogData.addAttribute(EMediumAttribute.MIME_TYPE, mimeTypeTextField.getText());
						EMediumPropertiesUI.this.dialogData.addAttribute(EMediumAttribute.MEDIUM_TYPE, (String) eMediumTypeList.getModel().getSelectedItem());
						try {
							EMediumPropertiesUI.this.dialogData.addAttribute(EMediumAttribute.LICENSES,Integer.parseInt(numberLicensesTextField.getText()));
						} catch (NumberFormatException exeception) {
							JOptionPane.showMessageDialog(parent, "Invalid number of licenses", "Invalid bookmark", JOptionPane.ERROR_MESSAGE);
						}
						LinkedList<String> tags = new LinkedList<String> ();
						for (Object o : tableModel.getDataVector()) {
							String tag = ((Vector<String>) o).get(0);
							if (tag.length() != 0 && !tag.equals("double click to add a tag..."))
								tags.add(tag);
						}
						EMediumPropertiesUI.this.dialogData.addAttribute(EMediumAttribute.TAGS, tags);
						EMediumPropertiesUI.this.dialogData.userAccepted(true);
					} else {
						EMediumPropertiesUI.this.dialogData.userAccepted(false);
					}
					EMediumPropertiesUI.this.dispose();
				}
			};
			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton createButton = new JButton(loadData ? "Update" : "Create");
				createButton.setActionCommand("Create");
				createButton.addActionListener(al);
				buttonPane.add(createButton);
				getRootPane().setDefaultButton(createButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(al);
				buttonPane.add(cancelButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
