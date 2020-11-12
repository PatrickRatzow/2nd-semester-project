package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderInvoice {
    private int id;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
    private Customer customer;
    private Price toPay;
    private Price hasPaid;

    public OrderInvoice() {}

    public OrderInvoice(int id, LocalDateTime createdAt, LocalDate dueDate, Customer customer, Price toPay, Price hasPaid) {
        this.id = id;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.customer = customer;
        this.toPay = toPay;
        this.hasPaid = hasPaid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Price getToPay() {
        return toPay;
    }

    public void setToPay(Price toPay) {
        this.toPay = toPay;
    }

    public Price getHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(Price hasPaid) {
        this.hasPaid = hasPaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
