package dao;

import entity.Employee;
import exception.DataAccessException;

public interface EmployeeDao {
    Employee findById(int id) throws DataAccessException;
}
