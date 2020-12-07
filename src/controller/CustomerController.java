package controller;

import dao.CustomerDao;
import datasource.DBConnection;
import datasource.DBManager;
import exception.DataAccessException;
import model.Address;
import model.Customer;
import util.ConnectionThread;
import util.Converter;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CustomerController {
    private Customer customer;
    private final List<Consumer<List<Customer>>> onFindListeners = new LinkedList<>();
    private final List<Consumer<Customer>> onSaveListeners = new LinkedList<>();
    private final List<Consumer<String>> onErrorListeners = new LinkedList<>();

    public void addFindListener(Consumer<List<Customer>> consumer) {
        onFindListeners.add(consumer);
    }

    public void addSaveListener(Consumer<Customer> consumer) {
        onSaveListeners.add(consumer);
    }

    public void addErrorListener(Consumer<String> error) {
        onErrorListeners.add(error);
    }

    public void getAll() {
        new Thread(() -> {
            try {
                List<Customer> customers = findAll();
                onFindListeners.forEach(l -> l.accept(customers));
            } catch (DataAccessException e) {
                onErrorListeners.forEach(l -> l.accept("Noget gik galt, kan ikke finde all kunder"));
            }
        }).start();
    }

    public void getSearch(String search) {
        new Thread(() -> {
            try {
                List<Customer> customers = findByPhoneNumberOrEmail(search);
                onFindListeners.forEach(l -> l.accept(customers));
            } catch (DataAccessException e) {
                onErrorListeners.forEach(l -> l.accept("Noget gik galt, kan ikke s�ge i kunder"));
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

    public boolean setCustomerInformation(int id, String firstName, String lastName, String email, String phoneNumber,
                                          String city, String streetName, String streetNumber, String zipCode) {
        if (setCustomerInformation(firstName, lastName, email, phoneNumber, city, streetName, streetNumber, zipCode)) {
            customer.setId(id);

            return true;
        }

        return false;
    }

    public boolean setCustomerInformation(String firstName, String lastName, String email, String phoneNumber, String city,
                                          String streetName, String streetNumber, String zipCode) {
        if (customer == null) {
            customer = new Customer();
        }

        int streetNumberInt = Converter.tryParse(streetNumber);
        int zipCodeInt = Converter.tryParse(zipCode);

        Address address = new Address(streetName, streetNumberInt, city, zipCodeInt);
        Customer temp = new Customer(firstName, lastName, email, phoneNumber, address);
        try {
            temp.validate();

            temp.setId(customer.getId());
            customer = temp;

            return true;
        } catch (Exception e) {
            onErrorListeners.forEach(l -> l.accept(e.getMessage()));

            return false;
        }
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void save() {
        try {
            if (customer.getId() <= 0) {
                create();
            } else {
                update();
            }
        } catch (Exception e) {
            onErrorListeners.forEach(l -> l.accept(e.getMessage()));
        }
    }

    private void create() {
        if (customer == null) throw new IllegalArgumentException("No customer set");
        // TODO: Could improve this?
        //if (!isCustomerValid(customer)) throw new IllegalArgumentException("Customer isn't valid");
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
        //if (!isCustomerValid(customer)) throw new IllegalArgumentException("Customer isn't valid");

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
        }).start();
    }
}
