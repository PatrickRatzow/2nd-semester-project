package model.project;

import exception.DataAccessException;
import model.DBConnection;
import model.Person;
import model.Price;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public ProjectDaoMsSql() {
		init();
	}
	
	private void init() {
		DBConnection con = DBConnection.getInstance();

		try {
			findAllPS = con.prepareStatement(FIND_ALL_Q);
			findByIdPS = con.prepareStatement(FIND_BY_ID_Q);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<ProjectDto> findAll() throws DataAccessException {
		final List<ProjectDto> projects;
		
		try {
			ResultSet rs = this.findAllPS.executeQuery();
			projects = buildObjects(rs);
		} catch(SQLException e) {
			throw new DataAccessException("Unable to find any projects");
		}
		
		return projects;
		
	}

	@Override
	public ProjectDto findById(int id) throws DataAccessException {
		final ProjectDto project;

		try {
			findByIdPS.setInt(1, id);
			ResultSet rs = findByIdPS.executeQuery();
			project = buildObjects(rs).get(0);
		} catch(SQLException e) {
			throw new DataAccessException("Unable to find any projects");
		}

		return project;
	}
	
	@Override
	public void findProjectByCustomer() {
		
	}
	
	//Not sure if this should stay
	@Override
	public Person findPersonOnProject(Person p) {
		
		return p;
	}
	
	@Override
	public void create() {
		
	}
	
	@Override
	public void addPersonToProject() {
		
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	//Is an int id we want to find project by?
	public void delete() {
		
	}
	
	private ProjectDto buildObject(ResultSet rs) throws SQLException {
		final ProjectDto project = new ProjectDto();
		project.setId(rs.getInt("projectId"));
		project.setName(rs.getString("projectName"));
		project.setPrice(new Price(rs.getInt("projectPrice")));

		return mergeObject(project, rs);
	}

	private ProjectDto mergeObject(ProjectDto projectDto, ResultSet rs) throws SQLException {
		int customerId = rs.getInt("customerId");
		int employeeId = rs.getInt("employeeId");
		int orderId = rs.getInt("orderId");

		if (customerId != 0) {
			projectDto.addCustomerId(customerId);
		}
		if (employeeId != 0) {
			projectDto.addEmployeeId(employeeId);
		}
		if (orderId != 0) {
			projectDto.addOrderId(orderId);
		}

		return projectDto;
	}

	private List<ProjectDto> buildObjects(ResultSet rs) throws DataAccessException {
		final Map<Integer, ProjectDto> projectDtoMap = new HashMap<>();

		try {
			while (rs.next()) {
				int projectId = rs.getInt("projectId");
				ProjectDto projectDto = projectDtoMap.get(projectId);

				if (projectDto == null) {
					projectDtoMap.put(projectId, buildObject(rs));
				} else {
					projectDtoMap.put(projectId, mergeObject(projectDto, rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			throw new DataAccessException("Unable to build project");
		}

		return new ArrayList<>(projectDtoMap.values());
	}}
