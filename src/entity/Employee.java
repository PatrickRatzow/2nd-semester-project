package entity;

public class Employee extends Person {
    public Employee() {}

    public Employee(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Employee(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }
}

