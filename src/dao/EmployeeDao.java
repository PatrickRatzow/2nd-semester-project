package dao;

import exception.DataAccessException;
import model.Employee;

import java.util.List;

public interface EmployeeDao {
    Employee findById(int id) throws DataAccessException;
    List<Employee> findByRole(String role) throws DataAccessException;
}
