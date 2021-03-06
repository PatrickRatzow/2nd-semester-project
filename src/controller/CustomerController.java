package controller;

import dao.CustomerDao;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Address;
import model.Customer;
import util.Converter;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CustomerController {
    private Customer customer;
    private final List<Consumer<List<Customer>>> onFindListeners = new LinkedList<>();
    private final List<Consumer<Customer>> onSaveListeners = new LinkedList<>();
    private final List<Consumer<Exception>> onErrorListeners = new LinkedList<>();

    public void addFindListener(Consumer<List<Customer>> consumer) {
        onFindListeners.add(consumer);
    }

    public void addSaveListener(Consumer<Customer> consumer) {
        onSaveListeners.add(consumer);
    }

    public void addErrorListener(Consumer<Exception> error) {
        onErrorListeners.add(error);
    }

    public void getAll() {
        DBManager.getInstance().getConnectionThread(conn -> {
            try {
                CustomerDao dao = conn.getDaoFactory().createCustomerDao();
                List<Customer> customers = dao.findAll();
                onFindListeners.forEach(l -> l.accept(customers));
            } catch (DataAccessException e) {
                onErrorListeners.forEach(l -> l.accept(new Exception("Noget gik galt, kan ikke finde all kunder")));
            }
        }).start();
    }

    public void getSearch(String search) {
        DBManager.getInstance().getConnectionThread(conn -> {
            try {
                CustomerDao dao = conn.getDaoFactory().createCustomerDao();
                List<Customer> customers = dao.findByPhoneNumberOrEmail(search, search);
                onFindListeners.forEach(l -> l.accept(customers));
            } catch (DataAccessException e) {
                onErrorListeners.forEach(l -> l.accept(new Exception("Noget gik galt, kan ikke søge i kunder")));
            }
        }).start();
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
            onErrorListeners.forEach(l -> l.accept(e));

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
            onErrorListeners.forEach(l -> l.accept(e));
        }
    }
    
    private void create() {
        if (customer == null) throw new IllegalArgumentException("No customer set");
        // TODO: Could improve this?
        //if (!isCustomerValid(customer)) throw new IllegalArgumentException("Customer isn't valid");
        final Customer customerTemp = this.customer;

        DBManager.getInstance().getConnectionThread(conn -> {
            CustomerDao dao = conn.getDaoFactory().createCustomerDao();

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
    
        DBManager.getInstance().getConnectionThread(conn -> {
            CustomerDao dao = conn.getDaoFactory().createCustomerDao();

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
