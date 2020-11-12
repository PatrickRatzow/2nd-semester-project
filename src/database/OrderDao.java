package database;

import model.Order;
import model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao {
    List<OrderDto> findAllByProjectId(int id) throws DataAccessException;
    OrderDto findById(int id) throws DataAccessException;
    Order create(LocalDateTime createdAt, OrderStatus status, int customerId, int employeeId, int projectId)
            throws DataWriteException;
    // I don't see why you'd need update + delete tbh, can discuss this.
}
