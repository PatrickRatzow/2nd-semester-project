package dao;

import entity.OrderLine;
import exception.DataAccessException;
import exception.DataWriteException;

import java.util.List;

public interface OrderLineDao {
    List<OrderLine> findByOrderId(int id) throws DataAccessException;
    OrderLine create(int orderId, int productId, int quantity) throws DataWriteException;
}
