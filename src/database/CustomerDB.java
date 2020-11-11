package database;

import model.Customer;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CustomerDB implements ICustomerDB {
    private static final String FIND_BY_PHONENO_Q = "SELECT * FROM GetCustomers WHERE customerPhoneNo = ?";
    private PreparedStatement findByPhoneNoPS;
    private static final String FIND_ALL_Q = "SELECT * FROM GetCustomers";
    private PreparedStatement findAllPS;
    private static final String FIND_ID_Q = "";
    private PreparedStatement findIdPS;
    private static final String INSERT_Q = "{CALL InsertCustomer(?, ?, ?, ?, ?)}";
    private CallableStatement insertPC;
    private static final String UPDATE_Q = "{CALL UpdateCustomer(?, ?, ?, ?, ?)}";
    private CallableStatement updatePC;
    private static final String DELETE_Q = "";
    private PreparedStatement deletePS;

    public CustomerDB() {
        init();
    }

    private void init() {
        DBConnection con = DBConnection.getInstance();

        try {
            findByPhoneNoPS = con.prepareStatement(FIND_BY_PHONENO_Q);
            findAllPS = con.prepareStatement(FIND_ALL_Q);
            findIdPS = con.prepareStatement(FIND_ID_Q);
            insertPC = con.prepareCall(INSERT_Q);
            updatePC = con.prepareCall(UPDATE_Q);
            deletePS = con.prepareStatement(DELETE_Q);
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
    public Customer findByPhoneNo(String phoneNo) throws DataAccessException, SQLException {
 
    	ResultSet rs;
    	Customer customer = null;
    	
    	findByPhoneNoPS.setString(1, phoneNo);
    	rs = this.findByPhoneNoPS.executeQuery();
    	
    	if(rs.next()) {
    		customer = buildObject(rs);
    	}
        
        return customer;
    }

    //
    @Override
    public Customer findId(int id) throws DataAccessException, SQLException {
        ResultSet rs; 
        Customer customer = null;
        
        findIdPS.setInt(1, id);
        rs = this.findIdPS.executeQuery();
        
        if(rs.next()) {
        	customer = buildObject(rs);
        }
        
        return customer;
    }

    @Override
    public Customer create(String firstName, String lastName, String email, String phoneNo) throws DataWriteException{
        
    	Customer customer = new Customer();
    	
    	try{
    		insertPC.setString(1, firstName);
    		insertPC.setString(2, lastName);
    		insertPC.setString(3, email);
    		insertPC.setString(4, phoneNo);
    		insertPC.registerOutParameter(5, Types.INTEGER);
    		insertPC.execute();
    		
    		customer.setId(insertPC.getInt(5));
    		customer.setFirstName(firstName);
    		customer.setLastName(lastName);
    		customer.setEmail(email);
    		customer.setPhoneNo(phoneNo);
    		
    	} catch(SQLException e) {
    		
    	}
    	
    	
    	return customer;
    }

    @Override
    public void update(int id, String firstName, String lastName, String email, String phoneNo) throws DataWriteException, SQLException {
    	
    	updatePC.setInt(1, id);
    	updatePC.setString(2, firstName);
    	updatePC.setString(3, lastName);
    	updatePC.setString(4, firstName);
    	updatePC.setString(5, firstName);
    	
    	updatePC.executeQuery();
    }

    @Override
    public void delete(int id) throws DataWriteException, SQLException {
    	deletePS.setInt(1, id);
    	deletePS.executeUpdate();
    }
}
