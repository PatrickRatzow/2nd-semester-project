package gui.components.customer;

import controller.CustomerController;
import entity.Customer;

public class UpdateCustomer extends ManipulateCustomer {
	private int customerId;
	
	public UpdateCustomer(CustomerController customerController, Customer customer) {
		super(customerController);
		
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
		String firstName = this.firstName.getContentText();
		String lastName = this.lastName.getContentText();
		String email = this.email.getContentText();
		String phoneNumber = this.phoneNumber.getContentText();
		String city = this.city.getContentText();
		String address = this.address.getContentText();
		String streetNumber = this.addressNumber.getContentText();
		String zipCode = this.zipCode.getContentText();
		
		boolean isValid = customerController.setCustomerInformation(customerId, firstName, lastName, email, phoneNumber, 
				city, address, streetNumber, zipCode);
		if (isValid) {
			customerController.save();
		}
	}
}