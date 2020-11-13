package model;

import java.util.Set;

public class Project {
    private int id;
    private String name;
    private Price price;
    private Set<Order> orderSet;
    private Set<Customer> customerSet;
    private Set<Employee> employeeSet;

    public Project() {}

    public Project(int id, String name, Price price, Set<Order> orderSet,
    		Set<Customer> customerSet, Set<Employee> employeeSet) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.orderSet = orderSet;
        this.customerSet = customerSet;
        this.employeeSet = employeeSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	public Set<Customer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<Customer> customerSet) {
		this.customerSet = customerSet;
	}

	public Set<Employee> getEmployeeSet() {
		return employeeSet;
	}

	public void setEmployeeSet(Set<Employee> employeeSet) {
		this.employeeSet = employeeSet;
	}
}
