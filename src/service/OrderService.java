package service;

import exception.DataAccessException;
import exception.DataWriteException;
import model.Order;
import model.OrderLine;

public interface OrderService {
    Order findById(int id) throws DataAccessException;
    Order create(Order order, OrderLine orderLine) throws DataWriteException;
}
