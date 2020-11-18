package persistence.repository;

import exception.DataAccessException;
import model.Employee;

public interface EmployeeRepository {
    Employee findById(int id) throws DataAccessException;
}
