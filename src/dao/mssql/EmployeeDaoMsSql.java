package dao.mssql;

import dao.EmployeeDao;
import datasource.DBConnection;
import datasource.DataAccessException;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDaoMsSql implements EmployeeDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM view_employees WHERE id = ?";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_ROLE_Q = "SELECT * FROM view_employees WHERE role_name = ?";
    private PreparedStatement findByRolePS;
    private DBConnection connection;

    public EmployeeDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        connection = conn;
        
        try {
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
            findByRolePS = conn.prepareStatement(FIND_BY_ROLE_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Employee buildObject(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("role_name")
        );
    }

    private List<Employee> buildObjects(ResultSet rs) throws SQLException {
        List<Employee> employees = new LinkedList<>();

        while (rs.next()) {
            employees.add(buildObject(rs));
        }

        return employees;
    }

    @Override
    public Employee findById(int id) throws DataAccessException {
        Employee employee = null;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (rs.next()) {
                employee = buildObject(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find an employee with id " + id);
        }

        return employee;
    }

    @Override
    public List<Employee> findByRole(String role) throws DataAccessException {
        List<Employee> employees;

        try {
            findByRolePS.setString(1, role);
            ResultSet rs = findByRolePS.executeQuery();

            employees = buildObjects(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find an employee by role " + role);
        }

        return employees;
    }
}
