package dao;

import entity.Customer;
import exception.DataAccessException;

public interface CustomerDao {
    Customer findByPhoneNumber(String phoneNumber) throws DataAccessException;
    Customer findById(int id) throws DataAccessException;
    void update(Customer customer) throws DataAccessException;
    Customer create(Customer customer) throws DataAccessException;
}
