package dao.mssql;

import dao.*;
import datasource.DBConnection;
import datasource.DataAccessException;
import model.*;
import util.ConnectionThread;

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
    private static final String INSERT_Q = " INSERT INTO [project](name, status, price, estimated_hours, customer_id," +
            " employee_id) VALUES (?, ?, ?, ?, ?, ?)";
    private PreparedStatement insertPS;
    private static final String UPDATE_Q = " UPDATE project SET name = ?, status = ?, price = ?, " +
            "estimated_hours = ?, employee_id = ? " +
            "WHERE id = ?";
    private PreparedStatement updatePS;

    public ProjectDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
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
    public Project create(Project project, boolean fullAssociation) throws DataAccessException {
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

        } catch (SQLException e) {

            throw new DataAccessException("Unable to create project");
        }
        return project;
    }

    @Override
    public void update(Project project, boolean fullAssociation) throws DataAccessException {
        try {
            updatePS.setString(1, project.getName());
            updatePS.setInt(2, project.getStatus().getValue());
            updatePS.setInt(3, project.getPrice().getAmount());
            updatePS.setInt(4, project.getEstimatedHours());
            updatePS.setInt(5, project.getCustomer().getId());
            updatePS.setInt(6, project.getEmployee().getId());
            updatePS.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find project to update");
        }
    }

    private Project buildObject(ResultSet rs, boolean fullAssociation) throws SQLException, DataAccessException {
        final int id = rs.getInt("id");
        final String name = rs.getString("name");
        final Project project = new Project(id, name);
        project.setStatus(ProjectStatus.values()[rs.getInt("status")]);
        project.setPrice(new Price(rs.getInt("price")));
        project.setEstimatedHours(rs.getInt("estimated_hours"));

        if (fullAssociation) {
            // Setup atomic references
            AtomicReference<DataAccessException> exception = new AtomicReference<>();
            AtomicReference<Customer> customer = new AtomicReference<>();
            AtomicReference<Employee> employee = new AtomicReference<>();
            AtomicReference<ProjectInvoice> invoice = new AtomicReference<>();
            AtomicReference<Order> order = new AtomicReference<>();
            // Setup ids
            final int customerId = rs.getInt("customer_id");
            final int employeeId = rs.getInt("employee_id");

            List<Thread> threads = new LinkedList<>();
            threads.add(new ConnectionThread(conn -> {
                CustomerDao dao = new CustomerDaoMsSql(conn);
                try {
                    customer.set(dao.findById(customerId));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));
            threads.add(new ConnectionThread(conn -> {
                EmployeeDao dao = new EmployeeDaoMsSql(conn);
                try {
                    employee.set(dao.findById(employeeId));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));
            threads.add(new ConnectionThread(conn -> {
                ProjectInvoiceDao dao = new ProjectInvoiceDaoMsSql(conn);
                try {
                    invoice.set(dao.findById(id));
                } catch (DataAccessException e) {
                    exception.set(e);
                }
            }));
            threads.add(new ConnectionThread(conn -> {
                OrderDao dao = new OrderDaoMsSql(conn);
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
            project.setInvoice(invoice.get());
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
