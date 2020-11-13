package model.order_line;

import exception.DataAccessException;
import exception.DataWriteException;

import java.util.List;

public interface OrderLineDao {
    List<OrderLineDto> findByOrderId(int id) throws DataAccessException;
    List<List<OrderLineDto>>findByOrderId(List<Integer> ids) throws DataAccessException;
    OrderLineDto create(int orderId, int productId, int quantity) throws DataWriteException;
}
