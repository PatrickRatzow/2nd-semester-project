package model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Order {
    private int id;
    private LocalDateTime date;
    private OrderStatus status;
    private Set<OrderLine> orderLines;
    private Set<OrderInvoice> orderInvoices;
    private Customer customer;
    private Employee employee;

    public Order() {
        this.orderLines = new HashSet<>();
        this.orderInvoices = new HashSet<>();
    }

    public Order(int id, LocalDateTime date, OrderStatus status, Set<OrderLine> orderLines,
                 Set<OrderInvoice> orderInvoices, Customer customer, Employee employee) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.orderLines = orderLines;
        this.orderInvoices = orderInvoices;
        this.customer = customer;
        this.employee = employee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void setOrderInvoices(Set<OrderInvoice> orderInvoices) {
        this.orderInvoices = orderInvoices;
    }

    public Set<OrderInvoice> getOrderInvoices() {
        return orderInvoices;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
