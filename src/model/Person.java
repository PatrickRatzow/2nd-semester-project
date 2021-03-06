package model;

import util.validation.Validatable;
import util.validation.Validator;
import util.validation.rules.StringNotEmptyValidationRule;

public abstract class Person implements Validatable {
    private int id;
    private String firstName;
    private String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public void validate() throws Exception {
    	Validator validator = new Validator();
        validator.addRule(new StringNotEmptyValidationRule(getFirstName(), "Fornavn er tomt!"));
        validator.addRule(new StringNotEmptyValidationRule(getLastName(), "Efternavn er tomt!"));
        
        if (validator.hasErrors()) {
        	throw validator.getCompositeException();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Person) {
            Person person = (Person) object;

            return person.getId() == id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
