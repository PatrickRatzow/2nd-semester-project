package entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int id;
    private LocalDateTime date;
    private boolean delivered;
    private final Map<Integer, OrderLine> orderLines;
    private Customer customer;
    private Employee employee;

    public Order() {
        orderLines = new HashMap<>();
    }

    public Order(int id, LocalDateTime date, boolean delivered) {
        this.id = id;
        this.date = date;
        this.delivered = delivered;

        orderLines = new HashMap<>();
    }

    public Order(int id, LocalDateTime date, boolean delivered, Map<Integer, OrderLine> orderLines, Customer customer, Employee employee) {
        this.id = id;
        this.date = date;
        this.delivered = delivered;
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

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
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
