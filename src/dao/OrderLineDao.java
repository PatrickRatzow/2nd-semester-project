package dao;

import datasource.DataAccessException;
import model.Order;
import model.OrderLine;

import java.util.List;

public interface OrderLineDao {
    List<OrderLine> findByOrderId(int id) throws DataAccessException;

    void create(Order order, OrderLine orderLine) throws DataAccessException;
}
