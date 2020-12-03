package gui;

import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.CustomerController;
import exception.DataAccessException;

import com.jgoodies.forms.layout.FormSpecs;

public class AddCustomer extends JPanel {
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtStreetName;
	private JTextField txtCity;
	private JTextField txtEmail;
	private JTextField txtZipCode;
	
	private CustomerController customerController; 
	private JTextField txtPhoneNumber;
	private JTextField txtStreetNumber;

	/**
	 * Create the panel.
	 */
	public AddCustomer() {
		customerController = new CustomerController();
		
		setLayout(new BorderLayout(0, 0));
		
		TitleBar title = new TitleBar();
		title.setTitle("Kunde Infomation");
		title.setButtonName("Gå Tilbage");
		add(title, BorderLayout.NORTH);
		
		JPanel buttomBar = new JPanel();
		add(buttomBar, BorderLayout.SOUTH);
		buttomBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnAdd = new JButton("Tilf\u00F8j Kunde");
		btnAdd.setHorizontalAlignment(SwingConstants.RIGHT);
		buttomBar.add(btnAdd);

		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("93dlu:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(116dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblFirstName = new JLabel("Fornavn");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblFirstName, "4, 2");
		
		JLabel lblLastName = new JLabel("Efternavn");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblLastName, "10, 2");
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(txtFirstName, "4, 4, fill, default");
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(txtLastName, "10, 4, fill, default");
		txtLastName.setColumns(10);
		
		JLabel lblAddress = new JLabel("Adresse");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblAddress, "4, 6");
		
		JLabel lblStreetNumber = new JLabel("Adresse Nummer");
		lblStreetNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblStreetNumber, "10, 6");
		
		txtStreetName = new JTextField();
		txtStreetName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(txtStreetName, "4, 8, fill, default");
		txtStreetName.setColumns(10);
		
		txtStreetNumber = new JTextField();
		txtStreetNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(txtStreetNumber, "10, 8, fill, default");
		txtStreetNumber.setColumns(10);
		
		JLabel lblCity = new JLabel("By");
		lblCity.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblCity, "4, 10");
		
		for(int i = 0; i < 10; i++) {
			panel.add(createRow(i));
		}
		int parseStreet = Integer.parseInt(txtStreetNumber.getText());
		
		JLabel lblZipCode = new JLabel("Postnummer");
		lblZipCode.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblZipCode, "10, 10");
		
		txtCity = new JTextField();
		txtCity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(txtCity, "4, 12, fill, default");
		txtCity.setColumns(10);
		
		txtZipCode = new JTextField();
		txtZipCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(txtZipCode, "10, 12, fill, default");
		txtZipCode.setColumns(10);
		int parseZip = Integer.parseInt(txtZipCode.getText());
		
		JLabel lblPhoneNumber = new JLabel("Telefonnummer");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblPhoneNumber, "4, 14");
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblEmail, "10, 14");
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(txtPhoneNumber, "4, 16, fill, default");
		txtPhoneNumber.setColumns(10);
		
		btnAdd.addActionListener(e -> { customerController.setCustomerInformation(txtFirstName.getText(),
				txtLastName.getText(), txtEmail.getText(), 
				txtPhoneNumber.getText(), txtCity.getText(), txtStreetName.getText(), 
				parseStreet, parseZip); 
		try {
			customerController.create();
		} catch (DataAccessException e1) {
			e1.printStackTrace();
		}
			
		});
		
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(txtEmail, "10, 16, fill, default");
		txtEmail.setColumns(10);
	}
	
	private Projects createRow(int i) {
		Projects rows = new Projects();
		rows.setName(String.valueOf(i));
		
		
		return rows;
	}
	
}
