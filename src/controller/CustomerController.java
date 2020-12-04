package controller;

import dao.CustomerDao;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Customer;
import exception.DataAccessException;
import util.ConnectionThread;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CustomerController {
    private Customer customer;
    private List<Consumer<List<Customer>>> onFindListeners = new LinkedList<>();
    private List<Consumer<Customer>> onSaveListeners = new LinkedList<>();
    
    public void addFindListener(Consumer<List<Customer>> consumer) {
    	onFindListeners.add(consumer);
    }
    
    public void addSaveListener(Consumer<Customer> consumer) {
    	onSaveListeners.add(consumer);
    }
    
    public void getAll() {
    	new Thread(() -> {
	    	try {
	    		List<Customer> customers = findAll();
	    		onFindListeners.forEach(l -> l.accept(customers));
	    	} catch (DataAccessException e) {
	    		// Ignore for now
	    	}
    	}).start();
    }
    
    public void getSearch(String search) {
        new Thread(() -> {
            try {
                List<Customer> customers = findByPhoneNumberOrEmail(search);
                onFindListeners.forEach(l -> l.accept(customers));
            } catch (DataAccessException e) {
                // Ignore for now
            }
        }).start();
    }

    private List<Customer> findAll() throws DataAccessException {
        final DBConnection connection = DBManager.getPool().getConnection();
        final CustomerDao dao = DBManager.getDaoFactory().createCustomerDao(connection);
        final List<Customer> customers = dao.findAll();

        connection.release();

        return customers;
    }

    private List<Customer> findByPhoneNumberOrEmail(String search) throws DataAccessException {
        final DBConnection connection = DBManager.getPool().getConnection();
        final CustomerDao dao = DBManager.getDaoFactory().createCustomerDao(connection);
        final List<Customer> customers = dao.findByPhoneNumberOrEmail(search, search);

        connection.release();

        return customers;
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

    public void setCustomerInformation(int id, String firstName, String lastName, String email, String phoneNumber, String city,
                                       String streetName, int streetNumber, int zipCode) {
    	setCustomerInformation(firstName, lastName, email, phoneNumber, city, streetName, streetNumber, zipCode);
    	customer.setId(id);
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

    public void save() {
    	if (customer.getId() <= 0) {
    		create();
    	} else {
    		update();
    	}
    }

    private void create() {
        if (customer == null) throw new IllegalArgumentException("No customer set");
        // TODO: Could improve this?
        if (!isCustomerValid(customer)) throw new IllegalArgumentException("Customer isn't valid");
        final Customer customerTemp = this.customer;

        new ConnectionThread(conn -> {
        	CustomerDao dao = DBManager.getDaoFactory().createCustomerDao(conn);

        	try {
        		conn.startTransaction();
        		Customer customer = dao.create(customerTemp);
        		conn.commitTransaction();

        		onSaveListeners.forEach(l -> l.accept(customer));
        	} catch (SQLException | DataAccessException e) {
        		e.printStackTrace();

        		try {
        			conn.rollbackTransaction();
        		} catch (SQLException e2) {
        			e2.printStackTrace();
        		}
        	}
        }).start();
    }

    private void update() {
        if (customer == null) throw new IllegalArgumentException("No customer set");
        // TODO: Could improve this?
        if (!isCustomerValid(customer)) throw new IllegalArgumentException("Customer isn't valid");

        final Customer customerTemp = this.customer;

        new ConnectionThread(conn -> {
        	CustomerDao dao = DBManager.getDaoFactory().createCustomerDao(conn);

        	try {
        		conn.startTransaction();
        		dao.update(customerTemp);
        		conn.commitTransaction();

        		onSaveListeners.forEach(l -> l.accept(customerTemp));
        	} catch (SQLException | DataAccessException e) {
        		e.printStackTrace();
        		
        		try {
        			conn.rollbackTransaction();
        		} catch (SQLException e2) {
        			e2.printStackTrace();
        		}
        	}
        });
    }
}
