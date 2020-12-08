package controller;

import dao.ProjectDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Customer;
import model.OrderLine;
import model.Price;
import model.Project;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ProjectController {
    private Project project;
    private OrderController orderController;
    private final List<Consumer<List<Project>>> onFindListeners = new LinkedList<>();
    private final List<Consumer<Project>> onFindFullListeners = new LinkedList<>();

    public ProjectController() {
    	project = new Project();
    	orderController = new OrderController();
    }
    public ProjectController(Project project) {
    	this.project = project;
    	this.orderController = new OrderController(project.getOrder());
    }
    
    public void setOrderController(OrderController orderController) {
    	this.orderController = orderController;
    }
    
    public OrderController getOrderController() {
    	return orderController;
    }
    
    public void setCustomer(Customer customer) {
        project.setCustomer(customer);
    }
    
    public Customer getCustomer() {
    	return project.getCustomer();
    }
    
    public Collection<OrderLine> getOrderLines() {
		return orderController.getOrderLines();
    }
    
    public Price getOrderPrice() {
    	return orderController.getPrice();
    }
    
    public String getName() {
    	return project.getName();
    }
    
    public void addFindListener(Consumer<List<Project>> listener) {
        onFindListeners.add(listener);
    }
    
    public void addFindProjectListener(Consumer<Project> listener) {
    	onFindFullListeners.add(listener);
    }
    
    private Project findById(int id) throws DataAccessException {
        DBConnection connection = DBManager.getPool().getConnection();
        ProjectDao projectDao = DBManager.getDaoFactory().createProjectDao(connection);
        Project project = projectDao.findById(id, true);

        connection.release();

        return project;
    }
    
    public void getFullProject(Project project) {
    	new Thread(() -> {
    		try {
    			Project fullProject = findById(project.getId());
    			onFindFullListeners.forEach(l -> l.accept(fullProject));
    		} catch (DataAccessException e) {
    			e.printStackTrace();
    		}
    	}).start();
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
