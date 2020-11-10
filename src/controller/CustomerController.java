package controller;

import database.CustomerDB;
import database.DataAccessException;
import database.ICustomerDB;
import model.Customer;

import java.util.List;

public class CustomerController {
    ICustomerDB customerDB = new CustomerDB();

    public List<Customer> findAll() throws DataAccessException {
        return customerDB.findAll();
    }
}
