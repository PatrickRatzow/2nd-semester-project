package controller;

import database.DataAccessException;
import database.DataWriteException;
import database.EmployeeDB;
import database.IEmployeeDB;
import model.Employee;
import util.Validator;

import java.util.List;

public class EmployeeController {
    IEmployeeDB employeeDB = new EmployeeDB();

    public List<Employee> findAll() throws DataAccessException {
        return employeeDB.findAll();
    }

    public void update(Employee employee) throws IllegalArgumentException, DataWriteException, DataAccessException {
        if (!Validator.isEmailValid(employee.getEmail())) {
            throw new IllegalArgumentException("That email is invalid");
        }
        try {
            final Employee existingEmployee = employeeDB.findByUsername(employee.getUsername());
            if (existingEmployee.getId() != employee.getId()) {
                throw new DataWriteException("A user with that username already exists");
            }
        } catch (DataAccessException ignore) {}

        employeeDB.update(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                employee.getPhoneNo(), employee.getUsername(), employee.getPassword().getBytes());
    }

    public Employee findByUsernameAndPassword(String username, String password) throws DataAccessException, WrongPasswordException {
        final Employee employee = employeeDB.findByUsername(username);
        if (!employee.getPassword().equals(password)) {
            throw new WrongPasswordException("Was able to find the user, but the password is incorrect");
        }

        return employee;
    }

    public Employee create(Employee employee) throws IllegalArgumentException, DataWriteException {
        if (!Validator.isEmailValid(employee.getEmail())) {
            throw new IllegalArgumentException("That email is invalid");
        }
        try {
            employeeDB.findByUsername(employee.getUsername());

            throw new IllegalArgumentException("A user with that username already exists");
        } catch (DataAccessException ignore) {}

        return employeeDB.create(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhoneNo(),
                employee.getUsername(), employee.getPassword().getBytes());
    }
}
