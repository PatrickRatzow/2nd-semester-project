package dao.mssql;

import dao.ProjectDao;
import datasource.DBConnection;
import entity.Project;
import exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProjectDaoMsSql implements ProjectDao {
	private static final String FIND_ALL_Q = "SELECT * FROM GetProjects";
	private PreparedStatement findAllPS;
	private static final String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE projectId = ?";
	private PreparedStatement findByIdPS;
	// TODO: Discuss if this is really needed? I don't see why you need to find a specific person on a specific project.
	private static final String FIND_PERSON_ON_PROJECT_Q = "";
	private PreparedStatement findPersonOnProjectPS;
	private static final String INSERT_Q = "";
	private PreparedStatement insertPS;
	private static final String UPDATE_Q = "";
	private PreparedStatement updatePS;
	// TODO: Discuss if this is needed? Due to foreign key constraints do we even allow deleting?
	private static final String DELETE_Q = "";
	private PreparedStatement deletePS;
	
	public ProjectDaoMsSql(DBConnection conn) {
		init(conn);
	}
	
	private void init(DBConnection conn) {
		try {
			findAllPS = conn.prepareStatement(FIND_ALL_Q);
			findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Project> findAll() throws DataAccessException {
		final List<Project> projects;
		
		try {
			ResultSet rs = this.findAllPS.executeQuery();
			projects = buildObjects(rs);
		} catch(SQLException e) {
			throw new DataAccessException("Unable to find any projects");
		}
		
		return projects;
		
	}

	@Override
	public Project findById(int id) throws DataAccessException {
		Project project = null;

		try {
			findByIdPS.setInt(1, id);
			ResultSet rs = findByIdPS.executeQuery();

			if (rs.next()) {
				project = buildObject(rs);
			}
		} catch(SQLException e) {
			throw new DataAccessException("Unable to find any project with id " + id);
		}

		return project;
	}
	
	private Project buildObject(final ResultSet rs) throws SQLException {
		final Project project;
		final int id = rs.getInt("projectId");
		final String name = rs.getString("projectName");
		final int price = rs.getInt("projectPrice");

		project = new Project();//id, name, price);
		//mergeObject(projectDto, rs);

		return project;
	}

	private void mergeObject(final Project project, final ResultSet rs) throws SQLException {
		final int customerId = rs.getInt("customerId");
		final int employeeId = rs.getInt("employeeId");
		final int orderId = rs.getInt("orderId");

		/*
		if (customerId != 0) {
			projectDto.addCustomerId(customerId);
		}
		if (employeeId != 0) {
			projectDto.addEmployeeId(employeeId);
		}
		if (orderId != 0) {
			projectDto.addOrderId(orderId);
		}
		 */
	}

	private List<Project> buildObjects(ResultSet rs) throws DataAccessException {
		final List<Project> projects = new LinkedList<>();

		try {
			while (rs.next()) {
				final int projectId = rs.getInt("projectId");
				projects.add(buildObject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();

			throw new DataAccessException("Unable to build project");
		}

		return projects;
	}}
