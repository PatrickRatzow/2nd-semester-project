package dao;

import entity.Order;
import entity.OrderStatus;
import entity.Project;
import exception.DataAccessException;
import exception.DataWriteException;

import java.time.LocalDateTime;

public interface OrderDao {
    Order findById(int id, boolean fullAssociation) throws DataAccessException;
    Order create(Order order) throws DataWriteException;
    Order create(Order order, Project project);
    Order create(LocalDateTime createdAt, OrderStatus status, int customerId, int employeeId, int projectId)
            throws DataWriteException;
    // I don't see why you'd need update + delete tbh, can discuss this.
}
