package dao.mssql;

import dao.CustomerDao;
import dao.EmployeeDao;
import dao.OrderDao;
import dao.ProjectDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ProjectDaoMsSql implements ProjectDao {
    private static final String FIND_ALL_Q = "SELECT * FROM project";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_NAME_Q = FIND_ALL_Q + " WHERE name LIKE CONCAT('%', ?, '%')";
    private PreparedStatement findByNamePS;
    private static final String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE id = ?";
    private PreparedStatement findByIdPS;
    private static final String INSERT_Q = " INSERT INTO project(name, status, price, estimated_hours, customer_id," +
            " employee_id) VALUES (?, ?, ?, ?, ?, ?)";
    private PreparedStatement insertPS;
    private static final String UPDATE_Q = " UPDATE project SET name = ?, status = ?, price = ?, " +
            "estimated_hours = ?, employee_id = ? " +
            "WHERE id = ?";
    private PreparedStatement updatePS;
    private DBConnection connection;
    public ProjectDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        connection = conn;
        
        try {
            findAllPS = conn.prepareStatement(FIND_ALL_Q);
            findByNamePS = conn.prepareStatement(FIND_BY_NAME_Q);
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
            insertPS = conn.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
            updatePS = conn.prepareStatement(UPDATE_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> findAll(boolean fullAssociation) throws DataAccessException {
        List<Project> projects;

        try {
            ResultSet rs = findAllPS.executeQuery();

            projects = buildObjects(rs, fullAssociation);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Something went wrong while searching for projects");
        }

        return projects;
    }

    @Override
    public List<Project> findByName(String name, boolean fullAssociation) throws DataAccessException {
        List<Project> projects;

        try {
            findByNamePS.setString(1, name);
            ResultSet rs = findByNamePS.executeQuery();

            projects = buildObjects(rs, fullAssociation);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Something went wrong while searching for projects");
        }

        return projects;
    }

    @Override
    public Project findById(int id, boolean fullAssociation) throws DataAccessException {
        Project project = null;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (rs.next()) {
                project = buildObject(rs, fullAssociation);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find any project with id " + id);
        }

        return project;
    }

    @Override
    public Project create(Project project) throws DataAccessException {
        try {
            insertPS.setString(1, project.getName());
            insertPS.setInt(2, project.getStatus().getValue());
            insertPS.setInt(3, project.getPrice().getAmount());
            insertPS.setInt(4, project.getEstimatedHours());
            insertPS.setInt(5, project.getCustomer().getId());
            insertPS.setInt(6, project.getEmployee().getId());
            insertPS.executeUpdate();

            ResultSet rs = insertPS.getGeneratedKeys();
            if (!rs.next()) {
                throw new DataAccessException("Unable to get identity for project");
            }

            project.setId(rs.getInt(1));
            
            if (project.getOrder() != null) {
                OrderDao dao = connection.getDaoFactory().createOrderDao();
                Order order = dao.create(project.getOrder(), project);
                project.setOrder(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
            throw new DataAccessException("Unable to create project");
        }
        
        return project;
    }

    @Override
    public void update(Project project) throws DataAccessException {
        try {
            updatePS.setString(1, project.getName());
            updatePS.setInt(2, project.getStatus().getValue());
            updatePS.setInt(3, project.getPrice().getAmount());
            updatePS.setInt(4, project.getEstimatedHours());
            updatePS.setInt(5, project.getEmployee().getId());
            updatePS.setInt(6, project.getId());
            int affected = updatePS.executeUpdate();
            if (affected == 0) {
                throw new DataAccessException("Unable to find project to update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to find project to update");
        }
    }

    private Project buildObject(ResultSet rs, boolean fullAssociation) throws SQLException, DataAccessException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Project project = new Project(id, name);
        project.setStatus(ProjectStatus.values()[rs.getInt("status")]);
        project.setPrice(new Price(rs.getInt("price")));
        project.setEstimatedHours(rs.getInt("estimated_hours"));

        if (fullAssociation) {
            // Setup atomic references
            AtomicReference<DataAccessException> exception = new AtomicReference<>();
            AtomicReference<Customer> customer = new AtomicReference<>();
            AtomicReference<Employee> employee = new AtomicReference<>();
            AtomicReference<Order> order = new AtomicReference<>();
            // Setup ids
            int customerId = rs.getInt("customer_id");
            int employeeId = rs.getInt("employee_id");

            List<Thread> threads = new LinkedList<>();
            threads.add(DBManager.getInstance().getConnectionThread(conn -> {
                CustomerDao dao = conn.getDaoFactory().createCustomerDao();
                try {
                    customer.set(dao.findById(customerId));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));
            threads.add(DBManager.getInstance().getConnectionThread(conn -> {
                EmployeeDao dao = conn.getDaoFactory().createEmployeeDao();
                try {
                    employee.set(dao.findById(employeeId));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));
            threads.add(DBManager.getInstance().getConnectionThread(conn -> {
                OrderDao dao = conn.getDaoFactory().createOrderDao();
                try {
                    order.set(dao.findById(id, true));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new DataAccessException("An error occurred while trying to find an order");
                }
            }

            if (exception.get() != null) {
                throw exception.get();
            }

            project.setCustomer(customer.get());
            project.setEmployee(employee.get());
            project.setOrder(order.get());
        }

        return project;
    }


    private List<Project> buildObjects(ResultSet rs, boolean fullAssociation) throws DataAccessException {
        final List<Project> projects = new LinkedList<>();

        try {
            while (rs.next()) {
                projects.add(buildObject(rs, fullAssociation));
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to build project");
        }

        return projects;
    }
}
