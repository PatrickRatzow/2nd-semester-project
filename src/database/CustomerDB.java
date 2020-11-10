package database;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerDB implements ICustomerDB {
    @Override
    public Customer findCustomerByName(String name) throws SQLException {
        return null;
    }

    @Override
    public List<Customer> findAllCustomers() throws SQLException {
        return null;
    }

    @Override
    public void createCustomer(String firstName, String lastName, String email, String phoneNo) throws SQLException {

    }

    @Override
    public void updateCustomer(String firstName, String lastName, String email, String phoneNo) throws SQLException {

    }

    @Override
    public void deleteCustomer(String firstName) throws SQLException {

    }
}
