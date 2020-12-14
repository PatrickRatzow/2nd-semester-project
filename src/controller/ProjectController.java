package controller;

import dao.ProjectDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ProjectController {
    private final Project project;
    private OrderController orderController;
    private final List<Consumer<List<Project>>> onFindListeners = new LinkedList<>();
    private final List<Consumer<Project>> onFindFullListeners = new LinkedList<>();
    private final List<Consumer<Project>> onSaveListeners = new LinkedList<>();

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
    	
    	if (orderController.hasOrder()) {
    		project.setOrder(orderController.getOrder());
		}
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
    
    public void setLeadEmployee(Employee employee) {
    	project.setEmployee(employee);
	}
	
	public Employee getLeadEmployee() {
    	return project.getEmployee();
	}
	
	public void setPrice(Price price) {
    	project.setPrice(price);
	}
	
	public void setPrice(int price) {
		this.setPrice(new Price(price * 100));
	}
	
	public Price getPrice() {
    	return project.getPrice();
	}
	
	public void setStatus(ProjectStatus status) {
    	project.setStatus(status);
	}
	
	public ProjectStatus getStatus() {
    	return project.getStatus();
	}
	
	public void setName(String name) {
    	project.setName(name);
	}
	
	public String getName() {
    	return project.getName();
	}
	
	public void setEstimatedHours(int hours) {
    	project.setEstimatedHours(hours);
	}
	
	public int getEstimatedHours() {
    	return project.getEstimatedHours();
	}

	public void addFindListener(Consumer<List<Project>> listener) {
        onFindListeners.add(listener);
    }
    
    public void addFindProjectListener(Consumer<Project> listener) {
    	onFindFullListeners.add(listener);
    }
    
    public void addSaveListener(Consumer<Project> listener) {
    	onSaveListeners.add(listener);
	}
	
    private Project findById(int id) throws DataAccessException {
        DBConnection connection = DBManager.getInstance().getPool().getConnection();
        ProjectDao projectDao = connection.getDaoFactory().createProjectDao();
        Project project = projectDao.findById(id, true);

        connection.release();

        return project;
    }
    
    public void save() throws Exception {
    	if (project == null) throw new IllegalArgumentException("Project is null!");
    	
    	project.validate();
    	
    	if (project.getId() == 0) {
    		create();
		} else {
  			update();
		}
	}
	
	private void create() {
 		DBManager.getInstance().getConnectionThread(conn -> {
			try {
				ProjectDao dao = conn.getDaoFactory().createProjectDao();
				
				conn.startTransaction();
				Project returnProject = dao.create(project);
 				conn.commitTransaction();
 				
 				onSaveListeners.forEach(l -> l.accept(returnProject));
			} catch (SQLException | DataAccessException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	private void update() {
		DBManager.getInstance().getConnectionThread(conn -> {
			try {
				ProjectDao dao = conn.getDaoFactory().createProjectDao();
				
				conn.startTransaction();
				dao.update(project);
				conn.commitTransaction();
				
				onSaveListeners.forEach(l -> l.accept(project));
			} catch (SQLException | DataAccessException e) {
				e.printStackTrace();
			}
		}).start();
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
        DBConnection connection = DBManager.getInstance().getPool().getConnection();
        ProjectDao projectDao = connection.getDaoFactory().createProjectDao();
        List<Project> projects = projectDao.findAll(false);

        connection.release();

        return projects;
    }

    private List<Project> findByName(String name, boolean fullAssociation) throws DataAccessException {
        DBConnection connection = DBManager.getInstance().getPool().getConnection();
        ProjectDao projectDao = connection.getDaoFactory().createProjectDao();
        List<Project> projects = projectDao.findByName(name, fullAssociation);

        connection.release();

        return projects;
    }
}
