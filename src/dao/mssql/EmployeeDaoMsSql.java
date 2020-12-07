package dao.mssql;

import dao.EmployeeDao;
import datasource.DBConnection;
import exception.DataAccessException;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoMsSql implements EmployeeDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetEmployees WHERE id = ?";
    private PreparedStatement findByIdPS;
    private DBConnection connection;

    public EmployeeDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        connection = conn;
        
        try {
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Employee buildObject(ResultSet rs) throws SQLException, DataAccessException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("role_name")
        );
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
