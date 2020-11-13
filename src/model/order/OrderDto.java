package model.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderDto {
    private int id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private int customerId;
    private int employeeId;
    private int orderId;
    private Set<Integer> orderLineIds = new HashSet<>();

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void addOrderLineId(int id) {
        orderLineIds.add(id);
    }

    public Set<Integer> getOrderLineIds() {
        return orderLineIds;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
