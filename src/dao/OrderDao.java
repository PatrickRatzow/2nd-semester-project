package dao;

import entity.Order;
import entity.Project;
import exception.DataAccessException;

public interface OrderDao {
    Order findById(int id, boolean fullAssociation) throws DataAccessException;

    Order create(Order order, Project project) throws DataAccessException;
}
