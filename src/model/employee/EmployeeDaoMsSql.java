package model.employee;

import exception.DataAccessException;
import exception.DataWriteException;
import model.DBConnection;
import model.Password;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoMsSql implements EmployeeDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetEmployees WHERE personId = ?";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_USERNAME_Q = "SELECT * FROM GetEmployees WHERE employeeUsername = ?";
    private PreparedStatement findByUsernamePS;
    private static final String FIND_ALL_Q = "SELECT * FROM GetEmployees";
    private PreparedStatement findAllPS;
    private static final String INSERT_Q = "{CALL InsertEmployee(?, ?, ?, ?, ?, ?, ?)}";
    private CallableStatement insertPC;
    private static final String UPDATE_Q = "{CALL UpdateEmployee(?, ?, ?, ?, ?, ?, ?)}";
    private CallableStatement updatePC;

    public EmployeeDaoMsSql() {
        init();
    }

    private void init() {
        DBConnection con = DBConnection.getInstance();

        try {
            findByIdPS = con.prepareStatement(FIND_BY_ID_Q);
            findByUsernamePS = con.prepareStatement(FIND_BY_USERNAME_Q);
            findAllPS = con.prepareStatement(FIND_ALL_Q);
            insertPC = con.prepareCall(INSERT_Q);
            updatePC = con.prepareCall(UPDATE_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Employee buildObject(ResultSet rs) {
        final Employee employee = new Employee();

        try {
            employee.setId(rs.getInt("personId"));
            employee.setFirstName(rs.getString("personFirstName"));
            employee.setLastName(rs.getString("personLastName"));
            employee.setEmail(rs.getString("personEmail"));
            employee.setPhoneNo(rs.getString("personPhoneNo"));
            employee.setUsername(rs.getString("employeeUsername"));
            employee.setPassword(new Password(rs.getBytes("employeePassword")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }


    private List<Employee> buildObjects(ResultSet rs) {
        final List<Employee> employees = new ArrayList<>();

        try {
            while (rs.next()) {
                employees.add(buildObject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public Employee findByUsername(String username) throws DataAccessException {
        Employee employee = null;

        try {
            findByUsernamePS.setString(1, username);
            ResultSet rs = this.findByUsernamePS.executeQuery();

            if (rs.next()) {
                employee = buildObject(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to find an employee");
        }

        if (employee == null) {
            throw new DataAccessException("Unable to find an employee");
        }

        return employee;
    }

    @Override
    public Employee findById(int id) throws DataAccessException {
        final Employee employee;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = this.findByIdPS.executeQuery();

            if (!rs.next()) {
                throw new DataAccessException("Unable to find an employee with id " + id);
            }

            employee = buildObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to find an employee with id " + id);
        }

        return employee;
    }

    @Override
    public List<Employee> findAll() throws DataAccessException {
        final List<Employee> employees;

        try {
            ResultSet rs = this.findAllPS.executeQuery();
            employees = buildObjects(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find any employees");
        }

        if (employees.size() == 0) {
            throw new DataAccessException("Unable to find any employees");
        }

        return employees;
    }

    @Override
    public Employee create(String firstName, String lastName,
                       String email, String phoneNo, String username, byte[] password) throws DataWriteException {
        Employee employee = new Employee();

        try {
            insertPC.setString(1, firstName);
            insertPC.setString(2, lastName);
            insertPC.setString(3, email);
            insertPC.setString(4, phoneNo);
            insertPC.setString(5, username);
            insertPC.setBytes(6, password);
            insertPC.registerOutParameter(7, Types.INTEGER);
            insertPC.execute();

            employee.setId(insertPC.getInt(7));
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employee.setPhoneNo(phoneNo);
            employee.setUsername(username);
            employee.setPassword(new Password(password));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataWriteException("Unable to create employee");
        }

        return employee;
    }

    @Override
    public void update(int id, String firstName, String lastName,
                       String email, String phoneNo, String username, byte[] password) throws DataWriteException, DataAccessException {
        try {
            updatePC.setInt(1, id);
            updatePC.setString(2, firstName);
            updatePC.setString(3, lastName);
            updatePC.setString(4, email);
            updatePC.setString(5, phoneNo);
            updatePC.setString(6, username);
            updatePC.setBytes(7, password);
            int affected = updatePC.executeUpdate();
            if (affected == 0) {
                throw new DataAccessException("Unable to find any employee to update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataWriteException("Unable to update employee");
        }
    }
}
