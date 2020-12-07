package model;

import util.validation.Validatable;
import util.validation.Validator;
import util.validation.rules.*;

public class Customer extends Person implements Validatable {
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
        validator.addRule(new EmptyValidationRule(getFirstName(), "Fornavn er tomt!"));
        validator.addRule(new EmptyValidationRule(getLastName(), "Efternavn er tomt!"));
        validator.addRule(new EmailValidationRule(getEmail()));
        validator.addRule(new PhoneValidationRule(getPhoneNumber()));
        Address address = getAddress();
        validator.addRule(new EmptyValidationRule(address.getCity(), "By er tom!"));
        validator.addRule(new EmptyValidationRule(address.getStreetName(), "Adresse er tom!"));
        validator.addRule(new IntegerRangeValidationRule(address.getStreetNumber(),
                "Addresse nummer er ugyldig. Skal v�re mellem 0-100000", 0, 100000));
        validator.addRule(new ZipCodeValidationRule(address.getZipCode()));

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
