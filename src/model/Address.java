package model;

import util.validation.Validatable;
import util.validation.Validator;
import util.validation.rules.EmptyValidationRule;
import util.validation.rules.IntegerRangeValidationRule;
import util.validation.rules.ZipCodeValidationRule;

public class Address implements Validatable {
    private String streetName;
    private int streetNumber;
    private String city;
    private int zipCode;

    public Address() {}

    public Address(String streetName, int streetNumber, String city, int zipCode) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.zipCode = zipCode;
    }

    @Override
    public void validate() throws Exception {
    	Validator validator = new Validator();
        validator.addRule(new EmptyValidationRule(getCity(), "By er tom!"));
        validator.addRule(new EmptyValidationRule(getStreetName(), "Adresse er tom!"));
        validator.addRule(new IntegerRangeValidationRule(getStreetNumber(),
                "Addresse nummer er ugyldig. Skal v�re mellem 0-100000", 0, 100000));
        validator.addRule(new ZipCodeValidationRule(getZipCode()));
        
        if (validator.hasErrors()) {
        	throw validator.getCompositeException();
        }
    }
    
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
