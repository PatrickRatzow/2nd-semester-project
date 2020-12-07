package controller;

import dao.ProjectDao;
import datasource.DBConnection;
import datasource.DBManager;
import exception.DataAccessException;
import model.Customer;
import model.Project;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ProjectController {
    private Customer customer;
    private final List<Consumer<List<Project>>> onFindListeners = new LinkedList<>();

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addFindListener(Consumer<List<Project>> listener) {
        onFindListeners.add(listener);
    }

    public void getAll() {
        new Thread(() -> {
            try {
                List<Project> projects = findAll();
                onFindListeners.forEach(l -> l.accept(projects));
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void getSearchByName(String name) {
        new Thread(() -> {
            try {
                List<Project> projects = findByName(name, false);
                onFindListeners.forEach(l -> l.accept(projects));
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private List<Project> findAll() throws DataAccessException {
        DBConnection connection = DBManager.getPool().getConnection();
        ProjectDao projectDao = DBManager.getDaoFactory().createProjectDao(connection);
        List<Project> projects = projectDao.findAll(false);

        connection.release();

        return projects;
    }

    private List<Project> findByName(String name, boolean fullAssociation) throws DataAccessException {
        DBConnection connection = DBManager.getPool().getConnection();
        ProjectDao projectDao = DBManager.getDaoFactory().createProjectDao(connection);
        List<Project> projects = projectDao.findByName(name, fullAssociation);

        connection.release();

        return projects;
    }
}
