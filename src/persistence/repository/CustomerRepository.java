package persistence.repository;

import exception.DataAccessException;
import model.Customer;

public interface CustomerRepository {
    Customer findById(int id) throws DataAccessException;
}
