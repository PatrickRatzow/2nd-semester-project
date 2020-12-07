package gui.components.customer;

import controller.CustomerController;

public class CreateCustomer extends ManipulateCustomer {
    public CreateCustomer(CustomerController customController) {
        super(customController);

        btnAdd.setText("Opret kunde");
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

        boolean isValid = customerController.setCustomerInformation(firstName, lastName, email, phoneNumber, city,
                address, streetNumber, zipCode);
        if (isValid) {
            customerController.save();
        }
    }
}
