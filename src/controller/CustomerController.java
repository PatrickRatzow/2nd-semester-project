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
    
    public Customer create(Customer customer) throws DataWriteException, SQLException {
    	
    	return customerDB.create(customer.getFirstName(), customer.getLastName(), 
    			customer.getEmail(), customer.getPhoneNo());
    }
    
    public void update(Customer customer) throws DataWriteException, SQLException {
    	customerDB.update(customer.getId(), customer.getFirstName(), 
    			customer.getLastName(), customer.getEmail(), 
    			customer.getPhoneNo());
    }
    
    //Make changes
    public void delete(int id) throws DataWriteException, SQLException {
    	customerDB.delete(id);
    }
    
    //Figure out if this is meant a search customer from id, or 
    //the other way around
    public void findId() {
    	
    }
    
    //Make changes
    public void findByPhoneNo(String phoneNo) throws DataAccessException, SQLException {
    	customerDB.findByPhoneNo(phoneNo);
    }
}
