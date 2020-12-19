package model;

import util.validation.Validatable;
import util.validation.Validator;
import util.validation.rules.IntegerMinimumValidationRule;
import util.validation.rules.NotNullValidationRule;
import util.validation.rules.StringNotEmptyValidationRule;

public class Project implements Validatable {
    private int id;
    private String name;
    private Order order;
    private Employee employee;
    private Price price;
    private int estimatedHours;
    private ProjectStatus status;
    private Customer customer;

    public Project() {
    }

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Project(int id, String name, Order order,
                   Employee employee, Price price, int estimatedHours,
                   ProjectStatus status, Customer customer) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.employee = employee;
        this.price = price;
        this.estimatedHours = estimatedHours;
        this.status = status;
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

	@Override
	public void validate() throws Exception {
		Validator validator = new Validator();
		validator.addRule(new StringNotEmptyValidationRule(name, "Projekt navn er tomt!"));
		validator.addRule(new NotNullValidationRule<Price>(price, "Projektet har ikke nogen pris!"));
		validator.addRule(new IntegerMinimumValidationRule(price.getAmount(), 
				"Pris er for lavt! Skal være mindst 1", 1));
		validator.addRule(new IntegerMinimumValidationRule(estimatedHours, 
				"Estimerede timer er for lavt! Skal være mindst 1", 1));
		validator.addRule(new NotNullValidationRule<ProjectStatus>(status, "Projekt status er ikke sat!"));
		validator.addRule(new NotNullValidationRule<Customer>(customer, "Projektet har ikke nogen kunde tilknyttet!"));
		validator.addRule(new NotNullValidationRule<Employee>(employee, 
				"Projektet har ikke nogen medarbejder tilknyttet!"));
		validator.addRule(new NotNullValidationRule<Order>(order, "Projektet har ikke nogen order tilknyttet!"));
		
		if (validator.hasErrors()) {
			throw validator.getCompositeException();
		}
	}

}
