package dao;

import datasource.DataAccessException;
import model.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll() throws DataAccessException;

    List<Customer> findByPhoneNumberOrEmail(String phoneNumber, String email) throws DataAccessException;

    Customer findById(int id) throws DataAccessException;

    void update(Customer customer) throws DataAccessException;

    Customer create(Customer customer) throws DataAccessException;
}
