package entity;

public class Customer extends Person {
    private String email;
    private String phoneNo;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String phoneNo) {
        super(firstName, lastName);
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
