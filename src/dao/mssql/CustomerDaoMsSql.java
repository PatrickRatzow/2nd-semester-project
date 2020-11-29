package dao.mssql;

import dao.CustomerDao;
import datasource.DBConnection;
import entity.Customer;
import exception.DataAccessException;
import exception.DataWriteException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDaoMsSql implements CustomerDao {
    private static final String FIND_BY_PHONE_NUMBER_Q = "SELECT * FROM GetCustomers WHERE phone_number = ?";
    private PreparedStatement findByPhoneNumberPS;
    private static final String FIND_ID_Q = "SELECT * FROM GetCustomers WHERE id = ?";
    private PreparedStatement findIdPS;
    private static final String INSERT_Q = "{CALL InsertCustomer(?, ?, ?, ?, ?)}";
    private CallableStatement insertPC;
    private static final String UPDATE_Q = "{CALL UpdateCustomer(?, ?, ?, ?, ?)}";
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
                rs.getString("phone_number")
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
        final Customer customer;

        try {
            findIdPS.setInt(1, id);
            ResultSet rs = this.findIdPS.executeQuery();

            if (!rs.next()) {
                throw new DataAccessException("Unable to find a customer");
            }

            customer = buildObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to find a customer");
        }
        
        return customer;
    }

    @Override
    public Customer create(String firstName, String lastName, String email, String phoneNumber) throws DataWriteException {
        Customer customer = null;
    	
    	try {
    		insertPC.setString(1, firstName);
    		insertPC.setString(2, lastName);
    		insertPC.setString(3, email);
    		insertPC.setString(4, phoneNumber);
    		insertPC.registerOutParameter(5, Types.INTEGER);
    		insertPC.execute();

            customer = new Customer(
                insertPC.getInt(5),
                firstName,
                lastName,
                email,
                phoneNumber
            );
    	} catch(SQLException e) {
    		e.printStackTrace();

    		throw new DataWriteException("Unable to create customer");
    	}

    	return customer;
    }

    @Override
    public void update(int id, String firstName, String lastName, String email, String phoneNo)
            throws DataWriteException, DataAccessException {
        try {
            updatePC.setInt(1, id);
            updatePC.setString(2, firstName);
            updatePC.setString(3, lastName);
            updatePC.setString(4, firstName);
            updatePC.setString(5, firstName);
            final int affected = updatePC.executeUpdate();
            if (affected == 0) {
                throw new DataAccessException("Unable to find any customer to update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataWriteException("Unable to update customer");
        }
    }
}
