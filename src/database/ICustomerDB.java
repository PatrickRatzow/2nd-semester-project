package database;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerDB {
    List<Customer> findAll() throws DataAccessException;
    List<Customer> findByPhoneNo(String name) throws DataAccessException, SQLException;
    Customer findId(int id) throws DataAccessException, SQLException;
    void update(int id, String firstName, String lastName, String email, String phoneNo) throws DataWriteException, SQLException;
    void delete(int id) throws DataWriteException, SQLException;
	Customer create(int id, String firstName, String lastName, String email, String phoneNo)
			throws DataWriteException;
}
