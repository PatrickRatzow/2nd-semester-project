package controller;

import database.EmployeeDB;
import database.IEmployeeDB;
import database.DataAccessException;
import database.DataWriteException;
import model.Employee;
import util.Validator;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
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

        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPassword(password, salt);

        employeeDB.update(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                employee.getPhoneNo(), employee.getUsername(), hashedPassword, salt);
    }

    public Employee findByUsernameAndPassword(String username, String password) throws DataAccessException, WrongPasswordException {
        Employee employee = employeeDB.findByUsername(username);
        byte[] salt = employee.getPassword().getSalt();
        byte[] storedHash = employee.getPassword().getHash();
        byte[] hashedPassword = hashPassword(password, salt);
        if (!Arrays.equals(storedHash, hashedPassword)) {
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
        } catch (DataAccessException e) {
            // We can just ignore this
        }

        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPassword(password, salt);

        return employeeDB.create(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhoneNo(),
                employee.getUsername(), hashedPassword, salt);
    }

    private byte[] hashPassword(String password, byte[] salt) {
        byte[] bytes = new byte[32];

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, (int) Math.pow(2, 16), 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            bytes = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ignored) {}

        return bytes;
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }
}
