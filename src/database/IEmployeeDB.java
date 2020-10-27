package database;

import model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeeDB {

    Employee findEmployeeByUsername(String username) throws SQLException;

    List<Employee> findAllEmployee() throws SQLException;

    void createEmployee(String firstName, String lastName, String email,
                        String phoneNo, String username, String password) throws SQLException;

    void updateEmployee(String firstName, String lastName, String email,
                        String phoneNo, String username, String password) throws SQLException;

    void deleteEmployee(String username) throws SQLException;

}
