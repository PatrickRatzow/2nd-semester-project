package model;

public class Employee extends Person {
    private String role;

    public Employee() {}
    public Employee(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public Employee(int id, String firstName, String lastName) {
        this(id, firstName, lastName, null);
    }
    public Employee(int id, String firstName, String lastName, String role) {
        super(id, firstName, lastName);
        
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }
    
    public String toString() {
    	return getFirstName() + " " + getLastName();
    }
}

