package entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Order {
    private int id;
    private LocalDateTime date;
    private OrderStatus status;
    private Map<Integer, OrderLine> orderLines;
    private Collection<OrderInvoice> orderInvoices;
    private Customer customer;
    private Employee employee;

    public Order() {
        orderLines = new HashMap<>();
        orderInvoices = new LinkedList<>();
    }

    public Order(int id, LocalDateTime date, OrderStatus status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    public Order(int id, LocalDateTime date, OrderStatus status, Map<Integer, OrderLine> orderLines,
                 Collection<OrderInvoice> orderInvoices, Customer customer, Employee employee) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.orderLines = orderLines;
        this.orderInvoices = orderInvoices;
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

    public Collection<OrderInvoice> getOrderInvoices() {
        return orderInvoices;
    }

    public void addOrderInvoice(OrderInvoice orderInvoice) {
        orderInvoices.add(orderInvoice);
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
