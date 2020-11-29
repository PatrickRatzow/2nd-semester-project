package dao.mssql;

import dao.EmployeeDao;
import datasource.DBConnection;
import entity.Employee;
import exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoMsSql implements EmployeeDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetEmployees WHERE id = ?";
    private PreparedStatement findByIdPS;

    public EmployeeDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        try {
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Employee buildObject(ResultSet rs) {
        final Employee employee = new Employee();

        try {
            employee.setId(rs.getInt("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
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
}
