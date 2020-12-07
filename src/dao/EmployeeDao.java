package dao;

import exception.DataAccessException;
import model.Employee;

public interface EmployeeDao {
    Employee findById(int id) throws DataAccessException;
}
