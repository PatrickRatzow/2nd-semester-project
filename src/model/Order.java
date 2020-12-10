package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int id;
    private LocalDateTime date;
    private boolean delivered;
    private final Map<Integer, OrderLine> orderLines;

    public Order() {
        date = LocalDateTime.now();
        orderLines = new HashMap<>();
    }

    public Order(int id, LocalDateTime date, boolean delivered) {
        this.id = id;
        this.date = date;
        this.delivered = delivered;

        orderLines = new HashMap<>();
    }

    public Order(int id, LocalDateTime date, boolean delivered, Map<Integer, OrderLine> orderLines) {
        this.id = id;
        this.date = date;
        this.delivered = delivered;
        this.orderLines = orderLines;
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
}
