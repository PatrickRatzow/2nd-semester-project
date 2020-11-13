package persistance;

import exception.DataAccessException;
import exception.DataWriteException;
import model.Order;
import model.OrderStatus;

import java.time.LocalDateTime;

public interface OrderDao {
    OrderDto findById(int id) throws DataAccessException;
    Order create(LocalDateTime createdAt, OrderStatus status, int customerId, int employeeId, int projectId)
            throws DataWriteException;
    // I don't see why you'd need update + delete tbh, can discuss this.
}
