package database;

import model.Employee;

import java.util.List;

public interface IEmployeeDB {
    Employee findByUsername(String username) throws DataAccessException;
    List<Employee> findAll() throws DataAccessException;
    Employee create(String firstName, String lastName, String email,
                String phoneNo, String username, byte[] password) throws IllegalArgumentException, DataWriteException;
    void update(int id, String firstName, String lastName, String email,
                String phoneNo, String username, byte[] password) throws DataWriteException, DataAccessException;
}
