package gui.components.customer;

import gui.components.core.TitleBar;

import javax.swing.*;
import java.awt.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.CustomerController;
import exception.DataAccessException;

import com.jgoodies.forms.layout.FormSpecs;

public class CreateCustomer extends JPanel {
	
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtStreetName;
	private JTextField txtCity;
	private JTextField txtPhoneNumber;
	private JTextField txtStreetNumber;
	private JTextField txtZipCode;
	private JTextField txtEmail;
	private CustomerController customerController; 

	/**
	 * Create the panel.
	 */
	public CreateCustomer() {
		customerController = new CustomerController();
		
		setLayout(new BorderLayout(0, 0));
		
		TitleBar titleBar = new TitleBar();
		titleBar.setTitle("Kunde Infomation");
		titleBar.setButtonName("Gå Tilbage");
		add(titleBar, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		
		JButton btnAdd = new JButton("Tilf\u00F8j Kunde");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnAdd);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblFirstName = new JLabel("Fornavn");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_2.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblAddress = new JLabel("Adresse");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.add(lblAddress);
		
		txtStreetName = new JTextField();
		panel_2.add(txtStreetName);
		txtStreetName.setColumns(10);
		
		JLabel lblCity = new JLabel("By");
		lblCity.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.add(lblCity);
		
		txtCity = new JTextField();
		panel_2.add(txtCity);
		txtCity.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Telefonnummer");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.add(lblPhoneNumber);
		
		txtPhoneNumber = new JTextField();
		panel_2.add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setHgap(40);
		panel_1.add(panel_4);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblLastName = new JLabel("Efternavn");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_3.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_3.add(txtLastName);
		txtLastName.setColumns(10);
		
		JLabel lblStreetNumber = new JLabel("Adresse Nummer");
		lblStreetNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_3.add(lblStreetNumber);
		
		txtStreetNumber = new JTextField();
		panel_3.add(txtStreetNumber);
		txtStreetNumber.setColumns(10);
		
		JLabel lblZipCode = new JLabel("Postnummer");
		lblZipCode.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_3.add(lblZipCode);
		
		txtZipCode = new JTextField();
		panel_3.add(txtZipCode);
		txtZipCode.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_3.add(lblEmail);
		
		txtEmail = new JTextField();
		panel_3.add(txtEmail);
		txtEmail.setColumns(10);
		
		
		//Will make a NumberFormatException; - needs to make a check for empty Strings
		btnAdd.addActionListener(e -> {
			int parseStreet = Integer.parseInt(txtStreetNumber.getText());
			int parseZip = Integer.parseInt(txtZipCode.getText());	
			customerController.setCustomerInformation(txtFirstName.getText(),
			txtLastName.getText(), txtEmail.getText(), 
			txtPhoneNumber.getText(), txtCity.getText(), txtStreetName.getText(), 
			parseStreet, parseZip); 
		try {
			customerController.create();
		} catch (DataAccessException e1) {
			e1.printStackTrace();
		}
			
		});
		
	}
	
}
