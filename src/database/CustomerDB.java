package database;

import model.Customer;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDB implements ICustomerDB {
    private static final String FIND_BY_PHONENO_Q = "SELECT * FROM GetCustomers WHERE customerPhoneNo = ?";
    private PreparedStatement findByPhoneNoPS;
    private static final String FIND_ALL_Q = "SELECT * FROM GetCustomers";
    private PreparedStatement findAllPS;
    private static final String INSERT_Q = "{CALL InsertCustomer(?, ?, ?, ?, ?)}";
    private CallableStatement insertPC;
    private static final String UPDATE_Q = "{CALL UpdateCustomer(?, ?, ?, ?, ?)}";
    private CallableStatement updatePC;

    public CustomerDB() {
        init();
    }

    private void init() {
        DBConnection con = DBConnection.getInstance();

        try {
            findByPhoneNoPS = con.prepareStatement(FIND_BY_PHONENO_Q);
            findAllPS = con.prepareStatement(FIND_ALL_Q);
            insertPC = con.prepareCall(INSERT_Q);
            updatePC = con.prepareCall(UPDATE_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Customer buildObject(ResultSet rs) {
        final Customer customer = new Customer();

        try {
            customer.setId(rs.getInt("personId"));
            customer.setFirstName(rs.getString("personFirstName"));
            customer.setLastName(rs.getString("personLastName"));
            customer.setEmail(rs.getString("personEmail"));
            customer.setPhoneNo(rs.getString("personPhoneNo"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }


    private List<Customer> buildObjects(ResultSet rs) {
        final List<Customer> customers = new ArrayList<>();

        try {
            while (rs.next()) {
                customers.add(buildObject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public List<Customer> findAll() throws DataAccessException {
        final List<Customer> customers;

        try {
            ResultSet rs = this.findAllPS.executeQuery();
            customers = buildObjects(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find any customers");
        }

        if (customers.size() == 0) {
            throw new DataAccessException("Unable to find any customers");
        }

        return customers;
    }

    @Override
    public List<Customer> findByPhoneNo(String phoneNo) throws DataAccessException {
        return null;
    }

    @Override
    public Customer findId(int id) throws DataAccessException {
        return null;
    }

    @Override
    public Customer create(String firstName, String lastName, String email, String phoneNo) throws DataWriteException {
        return null;
    }

    @Override
    public void update(int id, String firstName, String lastName, String email, String phoneNo) throws DataWriteException {

    }

    @Override
    public void delete(int id) throws DataWriteException {

    }
}
