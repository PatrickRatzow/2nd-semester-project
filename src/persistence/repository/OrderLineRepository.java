package persistence.repository;

import exception.DataAccessException;
import exception.DataWriteException;
import model.OrderLine;

import java.util.List;

public interface OrderLineRepository {
    List<OrderLine> findById(int id) throws DataAccessException;
    OrderLine create(int orderId, OrderLine orderLine) throws DataWriteException;
}
