package dao;

import entity.Customer;
import exception.DataAccessException;
import exception.DataWriteException;

public interface CustomerDao {
    Customer findByPhoneNumber(String phoneNumber) throws DataAccessException;
    Customer findById(int id) throws DataAccessException;
    void update(int id, String firstName, String lastName, String email, String phoneNo) throws DataWriteException, DataAccessException;
	Customer create(String firstName, String lastName, String email, String phoneNo) throws DataWriteException;
}
