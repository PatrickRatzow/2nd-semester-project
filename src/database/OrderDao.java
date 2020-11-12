package database;

import model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface OrderDao {
    List<Order> findAllByProjectId(int id) throws DataAccessException;
    Order findById(int id) throws DataAccessException;
    Order create(LocalDateTime createdAt, Set<OrderLine> orderLines, Set<OrderInvoice> invoices,
                 Project project, Employee employee, Customer customer, Price paid) throws DataWriteException;
    // I don't see why you'd need update + delete tbh, can discuss this.
}
