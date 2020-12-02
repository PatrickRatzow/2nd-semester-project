package dao.mssql;

import dao.CustomerDao;
import datasource.DBConnection;
import entity.Address;
import entity.Customer;
import exception.DataAccessException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDaoMsSql implements CustomerDao {
    private static final String FIND_BY_PHONE_NUMBER_Q = "SELECT * FROM GetCustomers WHERE phone_number = ?";
    private PreparedStatement findByPhoneNumberPS;
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
            findByPhoneNumberPS = conn.prepareStatement(FIND_BY_PHONE_NUMBER_Q);
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
                new Address()
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
    public Customer findByPhoneNumber(String phoneNumber) throws DataAccessException {
        Customer customer = null;

        try {
            findByPhoneNumberPS.setString(1, phoneNumber);
            ResultSet rs = this.findByPhoneNumberPS.executeQuery();

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
    	} catch(SQLException e) {
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
