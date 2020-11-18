package persistence.dao;

import exception.DataAccessException;
import exception.DataWriteException;
import model.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll() throws DataAccessException;
    Customer findByPhoneNo(String name) throws DataAccessException;
    Customer findById(int id) throws DataAccessException;
    void update(int id, String firstName, String lastName, String email, String phoneNo) throws DataWriteException, DataAccessException;
	Customer create(String firstName, String lastName, String email, String phoneNo) throws DataWriteException;
}
