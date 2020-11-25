package service;

import exception.DataAccessException;
import exception.DataWriteException;
import entity.Order;
import entity.Project;

public interface OrderService {
    Order findById(int id) throws DataAccessException;
    Order create(Order order, Project project) throws DataWriteException;
}
