package dao;

import entity.Order;
import entity.OrderLine;
import exception.DataAccessException;

import java.util.List;

public interface OrderLineDao {
    List<OrderLine> findByOrderId(int id) throws DataAccessException;
    void create(Order order, OrderLine orderLine) throws DataAccessException;
}
