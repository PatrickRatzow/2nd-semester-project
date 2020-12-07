package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProjectInvoice {
    private int id;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
    private Price toPay;
    private Price hasPaid;

    public ProjectInvoice() {}

    public ProjectInvoice(int id, LocalDateTime createdAt, LocalDate dueDate, Price toPay, Price hasPaid) {
        this.id = id;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.toPay = toPay;
        this.hasPaid = hasPaid;
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
