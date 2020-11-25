package controller;

import dao.EmployeeDao;
import dao.mssql.EmployeeDaoMsSql;
import exception.DataAccessException;
import exception.DataWriteException;
import exception.WrongPasswordException;
import entity.Employee;
import util.Validator;

import java.util.List;

public class EmployeeController {
    EmployeeDao employeeDao = new EmployeeDaoMsSql();

    public List<Employee> findAll() throws DataAccessException {
        return employeeDao.findAll();
    }

    public Employee findById(int id) throws DataAccessException {
        return employeeDao.findById(id);
    }

    public void update(Employee employee) throws IllegalArgumentException, DataWriteException, DataAccessException {
        if (!Validator.isEmailValid(employee.getEmail())) {
            throw new IllegalArgumentException("That email is invalid");
        }
        try {
            final Employee existingEmployee = employeeDao.findByUsername(employee.getUsername());
            if (existingEmployee.getId() != employee.getId()) {
                throw new DataWriteException("A user with that username already exists");
            }
        } catch (DataAccessException ignore) {}

        employeeDao.update(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                employee.getPhoneNo(), employee.getUsername(), employee.getPassword().getBytes());
    }


    public Employee findByUsernameAndPassword(String username, String password) throws DataAccessException, WrongPasswordException {
        final Employee employee = employeeDao.findByUsername(username);
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
            employeeDao.findByUsername(employee.getUsername());

            throw new IllegalArgumentException("A user with that username already exists");
        } catch (DataAccessException ignore) {}

        return employeeDao.create(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhoneNo(),
                employee.getUsername(), employee.getPassword().getBytes());
    }
}
