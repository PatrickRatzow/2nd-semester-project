package controller;

import dao.EmployeeDao;
import datasource.DBManager;
import exception.DataAccessException;
import model.Employee;
import util.ConnectionThread;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class EmployeeController {
    private final List<Consumer<List<Employee>>> onFindListeners = new LinkedList<>();

    public void addFindListener(Consumer<List<Employee>> listener) {
        onFindListeners.add(listener);
    }

    public void getDirectors() {
        new ConnectionThread(conn -> {
            EmployeeDao dao = DBManager.getDaoFactory().createEmployeeDao(conn);
            try {
                List<Employee> employees = dao.findByRole("Direktør");

                onFindListeners.forEach(l -> l.accept(employees));
            } catch (DataAccessException e) {
                // XD
            }
        }).start();
    }
}
