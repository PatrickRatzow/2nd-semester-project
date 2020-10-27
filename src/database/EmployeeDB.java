package database;

import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDB implements IEmployeeDB {

    private static final String FIND_EMPLOYEE_BY_USERNAME_Q = "";
    private static final String FIND_ALL_EMPLOYEE_Q = "";
    private static final String CREATE_EMPLOYEE_Q = "";
    private static final String UPDATE_EMPLOYEE_Q = "";
    private static final String DELETE_EMPLOYEE_Q = "";
    private PreparedStatement findEmployeeByUsernamePS;
    private PreparedStatement findAllEmployeePS;
    private PreparedStatement createEmployeePS;
    private PreparedStatement updateEmployeePS;
    private PreparedStatement deleteEmployeePS;


    public EmployeeDB() {
        init();
    }


    private void init() {
        DBConnection con = DBConnection.getInstance();
        try {
            findEmployeeByUsernamePS = con.prepareStatement(FIND_EMPLOYEE_BY_USERNAME_Q);
            findAllEmployeePS = con.prepareStatement(FIND_ALL_EMPLOYEE_Q);

            createEmployeePS = con.prepareStatement(CREATE_EMPLOYEE_Q);
            updateEmployeePS = con.prepareStatement(UPDATE_EMPLOYEE_Q);
            deleteEmployeePS = con.prepareStatement(DELETE_EMPLOYEE_Q);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //TODO
        }
    }


    private Employee buildObjectEmployee(ResultSet rs) {
        Employee employee = new Employee();
        try {
            employee.setPersonId(rs.getInt("personID"));
            employee.setFirstName(rs.getString("firstName"));
            employee.setLastName(rs.getString("lastName"));
            employee.setEmail(rs.getString("email"));
            employee.setPhoneNo(rs.getString("phoneNo"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("password"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //TODO
        }

        return employee;
    }


    private List<Employee> buildObjectsEmployee(ResultSet rs) {
        List<Employee> employeeList = new ArrayList<>();
        try {
            while (rs.next()) {
                Employee employee = buildObjectEmployee(rs);
                employeeList.add(employee);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //TODO
        }

        return employeeList;
    }

    @Override
    public Employee findEmployeeByUsername(String username) throws SQLException {
        ResultSet rs;
        Employee employee = null;

        findEmployeeByUsernamePS.setString(1, username);
        rs = this.findEmployeeByUsernamePS.executeQuery();

        if (rs.next()) {
            employee = buildObjectEmployee(rs);
        }

        return employee;
    }

    @Override
    public List<Employee> findAllEmployee() throws SQLException {
        ResultSet rs;

        rs = this.findAllEmployeePS.executeQuery();
        List<Employee> employeeList = buildObjectsEmployee(rs);

        return employeeList;
    }

    @Override
    public void createEmployee(String firstName, String lastName,
                               String email, String phoneNo, String username, String password) {
        try {
            createEmployeePS.setString(1, firstName);
            createEmployeePS.setString(2, lastName);
            createEmployeePS.setString(3, email);
            createEmployeePS.setString(4, phoneNo);
            createEmployeePS.setString(5, username);
            createEmployeePS.setString(6, password);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO
        }
    }

    @Override
    public void updateEmployee(String firstName, String lastName,
                               String email, String phoneNo, String username, String password) {
        try {
            updateEmployeePS.setString(1, firstName);
            updateEmployeePS.setString(2, lastName);
            updateEmployeePS.setString(3, email);
            updateEmployeePS.setString(4, phoneNo);
            updateEmployeePS.setString(5, username);
            updateEmployeePS.setString(6, password);

            updateEmployeePS.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO
        }
    }

    @Override
    public void deleteEmployee(String username) {
        try {
            deleteEmployeePS.setString(1, username);
            deleteEmployeePS.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO
        }

    }
}
