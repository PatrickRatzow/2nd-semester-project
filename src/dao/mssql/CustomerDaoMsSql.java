package dao.mssql;

import dao.CustomerDao;
import datasource.DBConnection;
import datasource.DataAccessException;
import model.Address;
import model.Customer;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDaoMsSql implements CustomerDao {
    private static final String FIND_ALL_Q = "SELECT * FROM GetCustomers";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_PHONE_NUMBER_OR_EMAIL_Q = FIND_ALL_Q + " WHERE phone_number = ? OR email = ?";
    private PreparedStatement findByPhoneNumberOrEmailPS;
    private static final String FIND_ID_Q = "SELECT * FROM GetCustomers WHERE id = ?";
    private PreparedStatement findIdPS;
    private static final String INSERT_Q = "{CALL InsertCustomer(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private CallableStatement insertPC;
    private static final String UPDATE_Q = "{CALL UpdateCustomer(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private CallableStatement updatePC;

    public CustomerDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        try {
            findAllPS = conn.prepareStatement(FIND_ALL_Q);
            findByPhoneNumberOrEmailPS = conn.prepareStatement(FIND_BY_PHONE_NUMBER_OR_EMAIL_Q);
            findIdPS = conn.prepareStatement(FIND_ID_Q);
            insertPC = conn.prepareCall(INSERT_Q);
            updatePC = conn.prepareCall(UPDATE_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Customer buildObject(ResultSet rs) {
        Customer customer = null;

        try {
            customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    new Address(
                            rs.getString("street_name"),
                            rs.getInt("street_number"),
                            rs.getString("city"),
                            rs.getInt("zip_code")
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }


    private List<Customer> buildObjects(ResultSet rs) {
        final List<Customer> customers = new LinkedList<>();

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
        List<Customer> customers;

        try {
            ResultSet rs = findAllPS.executeQuery();
            customers = buildObjects(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find all customers");
        }

        return customers;
    }

    @Override
    public List<Customer> findByPhoneNumberOrEmail(String phoneNumber, String email) throws DataAccessException {
        List<Customer> customers;

        try {
            findByPhoneNumberOrEmailPS.setString(1, phoneNumber);
            findByPhoneNumberOrEmailPS.setString(2, email);
            ResultSet rs = findByPhoneNumberOrEmailPS.executeQuery();
            customers = buildObjects(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find customers by phone number/email");
        }

        return customers;
    }

    @Override
    public Customer findById(int id) throws DataAccessException {
        Customer customer = null;

        try {
            findIdPS.setInt(1, id);
            ResultSet rs = this.findIdPS.executeQuery();

            if (rs.next()) {
                customer = buildObject(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to find a customer");
        }

        return customer;
    }

    @Override
    public Customer create(Customer customer) throws DataAccessException {
        try {
            insertPC.setString(1, customer.getFirstName());
            insertPC.setString(2, customer.getLastName());
            insertPC.setString(3, customer.getEmail());
            insertPC.setString(4, customer.getPhoneNumber());
            insertPC.setInt(5, customer.getAddress().getZipCode());
            insertPC.setString(6, customer.getAddress().getCity());
            insertPC.setString(7, customer.getAddress().getStreetName());
            insertPC.setInt(8, customer.getAddress().getStreetNumber());
            insertPC.registerOutParameter(9, Types.INTEGER);
            insertPC.execute();

            // Set identity on the customer object
            customer.setId(insertPC.getInt(9));
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to create customer");
        }

        return customer;
    }

    @Override
    public void update(Customer customer) throws DataAccessException {
        try {
            updatePC.setString(1, customer.getFirstName());
            updatePC.setString(2, customer.getLastName());
            updatePC.setString(3, customer.getEmail());
            updatePC.setString(4, customer.getPhoneNumber());
            updatePC.setInt(5, customer.getAddress().getZipCode());
            updatePC.setString(6, customer.getAddress().getCity());
            updatePC.setString(7, customer.getAddress().getStreetName());
            updatePC.setInt(8, customer.getAddress().getStreetNumber());
            updatePC.setInt(9, customer.getId());
            updatePC.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to update customer");
        }
    }
}
