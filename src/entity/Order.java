package entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int id;
    private LocalDateTime date;
    private OrderStatus status;
    private Map<Integer, OrderLine> orderLines;
    private Customer customer;
    private Employee employee;

    public Order() {
        orderLines = new HashMap<>();
    }

    public Order(int id, LocalDateTime date, OrderStatus status) {
        this.id = id;
        this.date = date;
        this.status = status;

        orderLines = new HashMap<>();
    }

    public Order(int id, LocalDateTime date, OrderStatus status, Map<Integer, OrderLine> orderLines, Customer customer, Employee employee) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.orderLines = orderLines;
        this.customer = customer;
        this.employee = employee;
    }

    public Map<Integer, OrderLine> getOrderLines() {
        return orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.put(orderLine.getProduct().getId(), orderLine);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
