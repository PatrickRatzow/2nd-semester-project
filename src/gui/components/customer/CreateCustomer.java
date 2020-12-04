package gui.components.customer;

import exception.DataAccessException;
import gui.components.core.PanelManager;

public class CreateCustomer extends ManipulateCustomer {
	public CreateCustomer(PanelManager panelManager) {
		super(panelManager, "Opret kunde", "Gaa tilbage");
		
		btnAdd.setText("Opret kunde");
	}
	
	public void onSave() {
		String firstName = this.firstName.getContentText();
		String lastName = this.lastName.getContentText();
		String email = this.email.getContentText();
		String phoneNumber = this.phoneNumber.getContentText();
		String city = this.city.getContentText();
		String address = this.address.getContentText();
		int streetNumber = Integer.parseInt(this.addressNumber.getContentText());
		int zipCode = Integer.parseInt(this.zipCode.getContentText());
		
		customerController.setCustomerInformation(firstName, lastName, email, phoneNumber, city, address, 
				streetNumber, zipCode); 
		
		try {
			customerController.create();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
}
