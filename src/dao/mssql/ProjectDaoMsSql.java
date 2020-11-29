package dao.mssql;

import dao.CustomerDao;
import dao.EmployeeDao;
import dao.ProjectDao;
import datasource.DBConnection;
import entity.Customer;
import entity.Employee;
import entity.Project;
import exception.DataAccessException;
import util.ConnectionThread;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ProjectDaoMsSql implements ProjectDao {
	private static final String FIND_ALL_Q = "SELECT * FROM project";
	private static final String FIND_BY_NAME_Q = FIND_ALL_Q + "WHERE name LIKE CONCAT('%', ?, '%')";
	private PreparedStatement findByNamePS;
	private static final String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE id = ?";
	private PreparedStatement findByIdPS;

	public ProjectDaoMsSql(DBConnection conn) {
		init(conn);
	}
	
	private void init(DBConnection conn) {
		try {
			findByNamePS = conn.prepareStatement(FIND_BY_NAME_Q);
			findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Project> findByName(String name, boolean fullAssociation) throws DataAccessException {
		List<Project> projects;

		try {
			findByNamePS.setString(1, name);
			ResultSet rs = findByNamePS.executeQuery();

			projects = buildObjects(rs, fullAssociation);
		} catch (SQLException e) {
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
		} catch(SQLException e) {
			throw new DataAccessException("Unable to find any project with id " + id);
		}

		return project;
	}
	
	private Project buildObject(ResultSet rs, boolean fullAssociation) throws SQLException, DataAccessException {
		final int id = rs.getInt("id");
		final String name = rs.getString("name");
		final Project project = new Project(id, name);

		if (fullAssociation) {
			// Setup atomic references
			AtomicReference<DataAccessException> exception = new AtomicReference<>();
			AtomicReference<Customer> customer = new AtomicReference<>();
			AtomicReference<Employee> employee = new AtomicReference<>();
			// Setup ids
			int customerId = rs.getInt("customer_id");
			int employeeId = rs.getInt("employee_id");

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
	}}
