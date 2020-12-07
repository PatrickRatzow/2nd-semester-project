package dao;

import exception.DataAccessException;
import model.Order;
import model.Project;

public interface OrderDao {
    Order findById(int id, boolean fullAssociation) throws DataAccessException;

    Order create(Order order, Project project) throws DataAccessException;
}
