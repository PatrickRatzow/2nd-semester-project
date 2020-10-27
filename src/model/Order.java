package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private LocalDateTime date;
    private OrderStatus status;
    private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(LocalDateTime date, OrderStatus status, List<OrderLine> orderLines) {
        this.date = date;
        this.status = status;
        this.orderLines = orderLines;
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

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
