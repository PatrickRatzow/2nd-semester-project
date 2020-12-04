package gui.components.customer;

import entity.Customer;
import gui.components.core.PanelManager;

public class UpdateCustomer extends ManipulateCustomer {
	private int customerId;
	
	public UpdateCustomer(PanelManager panelManager, Customer customer) {
		super(panelManager, "Opdater kunde", "Gaa tilbage");
		
		btnAdd.setText("Opdater kunde");
		setCustomer(customer);
	}
	
	private void setCustomer(Customer customer) {
		customerId = customer.getId();
		
		firstName.setContentText(customer.getFirstName());
		lastName.setContentText(customer.getLastName());
		phoneNumber.setContentText(customer.getPhoneNumber());
		email.setContentText(customer.getEmail());
		city.setContentText(customer.getAddress().getCity());
		address.setContentText(customer.getAddress().getStreetName());
		addressNumber.setContentText(String.valueOf(customer.getAddress().getStreetNumber()));
		zipCode.setContentText(String.valueOf(customer.getAddress().getZipCode()));
	}
	
	public void onSave() {
		int parseStreet = Integer.parseInt(addressNumber.getContentText());
		int parseZip = Integer.parseInt(zipCode.getContentText());	
		
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