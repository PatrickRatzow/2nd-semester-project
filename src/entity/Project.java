package entity;

import java.util.Map;

public class Project {
    private int id;
    private String name;
    private Map<Integer, Order> orders;
    private Customer customer;
    private ProjectInvoice invoice;

    public Project() {}

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Project(int id, String name, Map<Integer, Order> orders,
    		Customer customer, ProjectInvoice invoice) {
        this.id = id;
        this.name = name;
        this.orders = orders;
        this.customer = customer;
        this.invoice = invoice;
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

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProjectInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(ProjectInvoice invoice) {
        this.invoice = invoice;
    }
}
