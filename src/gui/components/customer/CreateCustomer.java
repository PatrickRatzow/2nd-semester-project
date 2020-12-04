package gui.components.customer;

import exception.DataAccessException;
import gui.components.core.PanelManager;

public class CreateCustomer extends ManipulateCustomer {
	public CreateCustomer(PanelManager panelManager) {
		super(panelManager, "Opret kunde", "Gaa tilbage");
		
		btnAdd.setText("Opret kunde");
	}
	
	public void onSave() {
		int parseStreet = Integer.parseInt(txtStreetNumber.getText());
		int parseZip = Integer.parseInt(txtZipCode.getText());	
		
		customerController.setCustomerInformation(txtFirstName.getText(),
			txtLastName.getText(), txtEmail.getText(), 
			txtPhoneNumber.getText(), txtCity.getText(), txtStreetName.getText(), 
			parseStreet, parseZip); 
		
		try {
			customerController.create();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
}
