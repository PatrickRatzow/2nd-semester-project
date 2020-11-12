package model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Order {
    private LocalDateTime date;
    private OrderStatus status;
    private final Set<OrderLine> orderLines;
    private final Set<OrderInvoice> orderInvoices;

    public Order() {
        this.orderLines = new HashSet<>();
        this.orderInvoices = new HashSet<>();
    }

    public Order(LocalDateTime date, OrderStatus status, Set<OrderLine> orderLines, Set<OrderInvoice> orderInvoices) {
        this.date = date;
        this.status = status;
        this.orderLines = orderLines;
        this.orderInvoices = orderInvoices;
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
}
