package entity;

public class Employee extends Person {
    private String username;
    private Password password;


    public Employee() {
    }

    public Employee(String firstName, String lastName, String email, String phoneNo, String username, String password) {
        super(firstName, lastName, email, phoneNo);
        this.username = username;
        this.password = new Password(password);
    }

    public Employee(String firstName, String lastName, String email, String phoneNo, String username, Password password) {
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

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
