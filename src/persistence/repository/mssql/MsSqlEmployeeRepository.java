package persistence.repository.mssql;

import exception.DataAccessException;
import model.Employee;
import persistence.dao.EmployeeDao;
import persistence.dao.mssql.MsSqlEmployeeDao;
import persistence.repository.EmployeeRepository;

public class MsSqlEmployeeRepository implements EmployeeRepository {
    final EmployeeDao employeeDao = new MsSqlEmployeeDao();

    @Override
    public Employee findById(final int id) throws DataAccessException {
        return employeeDao.findById(id);
    }
}
