package controller;

import database.EmployeeDB;
import database.IEmployeeDB;
import database.DataAccessException;
import database.DataWriteException;
import model.Employee;
import model.Password;
import util.Validator;

import java.util.List;

public class EmployeeController {
    IEmployeeDB employeeDB = new EmployeeDB();

    public List<Employee> findAll() throws DataAccessException {
        return employeeDB.findAll();
    }

    public void update(Employee employee, String password) throws IllegalArgumentException, DataWriteException, DataAccessException {
        if (!Validator.isEmailValid(employee.getEmail())) {
            throw new IllegalArgumentException("That email is invalid");
        }
        try {
            Employee existingEmployee = employeeDB.findByUsername(employee.getUsername());
            if (existingEmployee.getId() != employee.getId()) {
                throw new DataWriteException("A user with that username already exists");
            }
        } catch (DataAccessException ignore) {}

        byte[] hashedPassword = Password.hashPassword(password);
        employeeDB.update(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                employee.getPhoneNo(), employee.getUsername(), hashedPassword);
    }

    public Employee findByUsernameAndPassword(String username, String password) throws DataAccessException, WrongPasswordException {
        Employee employee = employeeDB.findByUsername(username);
        if (!employee.getPassword().equals(password)) {
            throw new WrongPasswordException("Was able to find the user, but the password is incorrect");
        }

        return employee;
    }

    public Employee create(Employee employee, String password) throws IllegalArgumentException, DataWriteException {
        if (!Validator.isEmailValid(employee.getEmail())) {
            throw new IllegalArgumentException("That email is invalid");
        }
        try {
            employeeDB.findByUsername(employee.getUsername());

            throw new IllegalArgumentException("A user with that username already exists");
        } catch (DataAccessException ignore) {}

        byte[] hashedPassword = Password.hashPassword(password);

        return employeeDB.create(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhoneNo(),
                employee.getUsername(), hashedPassword);
    }
}
