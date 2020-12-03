package gui.components.customer;

import entity.Customer;
import gui.components.core.TabPanel;

public class UpdateCustomer extends ManipulateCustomer {
	private int customerId;
	
	public UpdateCustomer(TabPanel panelManager, Customer customer) {
		super(panelManager, "Opdater kunde", "Gaa tilbage");
		
		btnAdd.setText("Opdater kunde");
		setCustomer(customer);
	}
	
	private void setCustomer(Customer customer) {
		customerId = customer.getId();
		
		txtFirstName.setText(customer.getFirstName());
		txtLastName.setText(customer.getLastName());
		txtPhoneNumber.setText(customer.getPhoneNumber());
		txtEmail.setText(customer.getEmail());
		txtCity.setText(customer.getAddress().getCity());
		txtStreetName.setText(customer.getAddress().getStreetName());
		txtStreetNumber.setText(String.valueOf(customer.getAddress().getStreetNumber()));
		txtZipCode.setText(String.valueOf(customer.getAddress().getZipCode()));
	}
	
	public void onSave() {
		int parseStreet = Integer.parseInt(txtStreetNumber.getText());
		int parseZip = Integer.parseInt(txtZipCode.getText());	
		
		// TODO: Add code to update
		/*
		customerController.setCustomerInformation(txtFirstName.getText(),
			txtLastName.getText(), txtEmail.getText(), 
			txtPhoneNumber.getText(), txtCity.getText(), txtStreetName.getText(), 
			parseStreet, parseZip); 
		try {
			customerController.create();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}*/
	}
}