package entity;

public class Customer extends Person {
    private String email;
    private String phoneNumber;

    public Customer() {}

    public Customer(int id, String firstName, String lastName, String email, String phoneNumber) {
        super(id, firstName, lastName);

        this.email = email;
        this.phoneNumber = phoneNumber;
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
}
