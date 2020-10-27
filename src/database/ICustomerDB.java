package database;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerDB {

    Customer findCustomerByName(String name) throws SQLException;

    List<Customer> findAllCustomers() throws SQLException;

    void createCustomer(String firstName, String lastName, String email, String phoneNo) throws SQLException;

    void updateCustomer(String firstName, String lastName, String email, String phoneNo) throws SQLException;

    void deleteCustomer(String firstName) throws SQLException;
}
