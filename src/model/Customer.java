package model;

import util.validation.Validator;
import util.validation.rules.EmailValidationRule;
import util.validation.rules.PhoneValidationRule;

public class Customer extends Person {
    private String email;
    private String phoneNumber;
    private Address address;

    public Customer() {
        address = new Address();
    }

    public Customer(int id, String firstName, String lastName, String email, String phoneNumber, Address address) {
        super(id, firstName, lastName);

        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Customer(String firstName, String lastName, String email, String phoneNumber, Address address) {
        super(firstName, lastName);

        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
    @Override
    public void validate() throws Exception {
        Validator validator = new Validator();
        validator.addValidatable(super::validate);
        validator.addRule(new EmailValidationRule(getEmail()));
        validator.addRule(new PhoneValidationRule(getPhoneNumber()));
        validator.addValidatable(getAddress()::validate);

        if (validator.hasErrors()) {
            throw validator.getCompositeException();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
