package model.customer;

import model.Person;

public class Customer extends Person {

    public Customer() {
    }


    public Customer(String firstName, String lastName, String email, String phoneNo) {
        super(firstName, lastName, email, phoneNo);
    }
}
