package entity;

public class Customer extends Person {
    private String email;
    private String phoneNumber;
    private Address address;

    public Customer() {}
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
