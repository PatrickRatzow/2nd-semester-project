package controller;

import database.CustomerDB;
import database.DataAccessException;
import database.DataWriteException;
import database.ICustomerDB;
import model.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerController {
    ICustomerDB customerDB;
    
    
    public CustomerController() {
    	customerDB = new CustomerDB();
    }
    

    public List<Customer> findAll() throws DataAccessException {
        return customerDB.findAll();
    }
    
    //Needs some more checks
    public Customer create(Customer customer) throws DataWriteException, SQLException {
    	
    	return customerDB.create(customer.getFirstName(), customer.getLastName(), 
    			customer.getEmail(), customer.getPhoneNo());
    }
    
    
    public void findByPhoneNo(String phoneNo) {
    	
    }
}
