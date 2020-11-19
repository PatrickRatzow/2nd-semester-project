package persistence.dao.mssql;

import exception.DataAccessException;
import persistence.connection.mssql.MsSqlDataSource;
import persistence.dao.ProjectDao;
import persistence.repository.mssql.dto.ProjectDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsSqlProjectDao implements ProjectDao {
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
	
	public MsSqlProjectDao() {
		init();
	}
	
	private void init() {
		MsSqlDataSource con = MsSqlDataSource.getInstance();

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
			List<ProjectDto> projectDtos = buildObjects(rs);
			if (projectDtos.isEmpty()) {
				throw new DataAccessException("Unable to find any project with id " + id);
			}

			project = projectDtos.get(0);
		} catch(SQLException e) {
			throw new DataAccessException("Unable to find any project with id " + id);
		}

		return project;
	}
	
	private ProjectDto buildObject(final ResultSet rs) throws SQLException {
		final ProjectDto projectDto;
		final int id = rs.getInt("projectId");
		final String name = rs.getString("projectName");
		final int price = rs.getInt("projectPrice");

		projectDto = new ProjectDto(id, name, price);
		mergeObject(projectDto, rs);

		return projectDto;
	}

	private void mergeObject(final ProjectDto projectDto, final ResultSet rs) throws SQLException {
		final int customerId = rs.getInt("customerId");
		final int employeeId = rs.getInt("employeeId");
		final int orderId = rs.getInt("orderId");

		if (customerId != 0) {
			projectDto.addCustomerId(customerId);
		}
		if (employeeId != 0) {
			projectDto.addEmployeeId(employeeId);
		}
		if (orderId != 0) {
			projectDto.addOrderId(orderId);
		}
	}

	private List<ProjectDto> buildObjects(ResultSet rs) throws DataAccessException {
		final Map<Integer, ProjectDto> projectDtoMap = new HashMap<>();

		try {
			while (rs.next()) {
				final int projectId = rs.getInt("projectId");
				final ProjectDto projectDto = projectDtoMap.get(projectId);

				if (projectDto == null) {
					projectDtoMap.put(projectId, buildObject(rs));
				} else {
					mergeObject(projectDto, rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			throw new DataAccessException("Unable to build project");
		}

		return new ArrayList<>(projectDtoMap.values());
	}}
