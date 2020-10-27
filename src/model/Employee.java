package model;

public class Employee extends Person {
    private String username;
    private String password;


    public Employee() {
    }


    public Employee(String firstName, String lastName, String email, String phoneNo, String username, String password) {
        super(firstName, lastName, email, phoneNo);
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
