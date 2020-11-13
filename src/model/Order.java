package model;

import java.time.LocalDateTime;
import java.util.Set;

public class Order {
    private int id;
    private LocalDateTime date;
    private OrderStatus status;
    private Set<OrderLine> orderLines;
    private OrderInvoice orderInvoice;
    private Customer customer;
    private Employee employee;

    public Order() {}

    public Order(int id, LocalDateTime date, OrderStatus status, Set<OrderLine> orderLines,
                 OrderInvoice orderInvoice, Customer customer, Employee employee) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.orderLines = orderLines;
        this.orderInvoice = orderInvoice;
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

    public void setOrderInvoice(OrderInvoice orderInvoice) {
        this.orderInvoice = orderInvoice;
    }

    public OrderInvoice getOrderInvoice() {
        return orderInvoice;
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
