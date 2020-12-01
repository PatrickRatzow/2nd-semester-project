package controller;

import dao.CustomerDao;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Customer;
import exception.DataAccessException;

public class CustomerController {
    private Customer customer;

    public Customer findByPhoneNumber(String phoneNumber) throws DataAccessException {
        final DBConnection connection = DBManager.getPool().getConnection();
        final CustomerDao dao = DBManager.getDaoFactory().createCustomerDao(connection);
        final Customer customer = dao.findByPhoneNumber(phoneNumber);

        connection.release();

        return customer;
    }

    private boolean isCustomerValid(Customer customer) {
        return !customer.getFirstName().isEmpty() &&
                !customer.getLastName().isEmpty() &&
                !customer.getEmail().isEmpty() &&
                !customer.getPhoneNumber().isEmpty() &&
                !customer.getAddress().getCity().isEmpty() &&
                !customer.getAddress().getStreetName().isEmpty() &&
                customer.getAddress().getStreetNumber() != 0 &&
                customer.getAddress().getZipCode() != 0;
    }

    public void setCustomerInformation(String firstName, String lastName, String email, String phoneNumber, String city,
                                       String streetName, int streetNumber, int zipCode) {
        if (customer == null) {
            customer = new Customer();
        }

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.getAddress().setCity(city);
        customer.getAddress().setStreetName(streetName);
        customer.getAddress().setStreetNumber(streetNumber);
        customer.getAddress().setZipCode(zipCode);
    }

    public void create() throws DataAccessException {
        if (customer == null) throw new IllegalArgumentException("No customer set");
        // TODO: Could improve this?
        if (!isCustomerValid(customer)) throw new IllegalArgumentException("Customer isn't valid");

        final DBConnection connection = DBManager.getPool().getConnection();
        final CustomerDao dao = DBManager.getDaoFactory().createCustomerDao(connection);
        customer = dao.create(customer);

        connection.release();
    }
}
