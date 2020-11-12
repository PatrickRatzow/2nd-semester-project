package database;

import model.Customer;

import java.util.List;

public interface ICustomerDB {
    List<Customer> findAll() throws DataAccessException;
    Customer findByPhoneNo(String name) throws DataAccessException;
    Customer findId(int id) throws DataAccessException;
    void update(int id, String firstName, String lastName, String email, String phoneNo) throws DataWriteException, DataAccessException;
	Customer create(String firstName, String lastName, String email, String phoneNo) throws DataWriteException;
}
