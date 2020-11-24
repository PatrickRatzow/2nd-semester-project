package dto;

import model.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderDto implements Serializable {
    private final int id;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
    private final int customerId;
    private final int employeeId;
    private final int orderId;
    private final Set<Integer> orderLineIds = new HashSet<>();

    public OrderDto(final int id, final OrderStatus status, final LocalDateTime createdAt, final int customerId,
             final int employeeId, final int orderId) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getEmployeeId() {
        return employeeId;
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
}
