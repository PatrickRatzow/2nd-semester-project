package controller;

import database.CustomerDB;
import database.DataAccessException;
import database.DataWriteException;
import database.ICustomerDB;
import model.Customer;

import java.util.List;

public class CustomerController {
    ICustomerDB customerDB;

    // TODO: Discuss if we should make this an inline initialisation in the customerDB variable. Talk with teacher
    public CustomerController() {
    	customerDB = new CustomerDB();
    }

    public List<Customer> findAll() throws DataAccessException {
        return customerDB.findAll();
    }
    
    public Customer create(Customer customer) throws DataWriteException {
    	return customerDB.create(customer.getFirstName(), customer.getLastName(), 
    			customer.getEmail(), customer.getPhoneNo());
    }
    
    public void update(Customer customer) throws DataWriteException, DataAccessException {
    	customerDB.update(customer.getId(), customer.getFirstName(), 
    			customer.getLastName(), customer.getEmail(), 
    			customer.getPhoneNo());
    }

    // TODO: Can still make changes here
    public Customer findId(int id) throws DataAccessException {
    	return customerDB.findId(id);
    }
    
    // TODO: Make changes
    public void findByPhoneNo(String phoneNo) throws DataAccessException {
    	customerDB.findByPhoneNo(phoneNo);
    }
}
