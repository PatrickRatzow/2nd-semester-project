package dao;

import dto.OrderLineDto;
import exception.DataAccessException;
import exception.DataWriteException;
import model.OrderLine;

import java.util.List;

public interface OrderLineDao {
    List<OrderLineDto> findByOrderId(int id) throws DataAccessException;
    List<List<OrderLineDto>>findByOrderId(List<Integer> ids) throws DataAccessException;
    OrderLine create(int orderId, int productId, int quantity) throws DataWriteException;
}
