package dao;

import dto.OrderDto;
import exception.DataAccessException;
import exception.DataWriteException;
import entity.Order;
import entity.OrderStatus;

import java.time.LocalDateTime;

public interface OrderDao {
    Order findById(int id, boolean fullAssosication) throws DataAccessException;
    Order create(LocalDateTime createdAt, OrderStatus status, int customerId, int employeeId, int projectId)
            throws DataWriteException;
    // I don't see why you'd need update + delete tbh, can discuss this.
}
