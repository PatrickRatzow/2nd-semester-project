package persistence.repository.mssql;

import exception.DataAccessException;
import model.Customer;
import persistence.dao.CustomerDao;
import persistence.dao.mssql.MsSqlCustomerDao;
import persistence.repository.CustomerRepository;

public class MsSqlCustomerRepository implements CustomerRepository {
    final CustomerDao customerDao = new MsSqlCustomerDao();

    @Override
    public Customer findById(final int id) throws DataAccessException {
        return customerDao.findById(id);
    }
}
