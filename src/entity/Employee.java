package entity;

public class Employee extends Person {
    private EmployeePosition position;

    public Employee() {}
    public Employee(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public Employee(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }
}

