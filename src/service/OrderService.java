package service;

import exception.DataAccessException;
import exception.DataWriteException;
import model.Order;
import model.Project;

public interface OrderService {
    Order findById(int id) throws DataAccessException;
    Order create(Order order, Project project) throws DataWriteException;
}
