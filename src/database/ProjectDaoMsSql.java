package database;

import model.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDaoMsSql implements ProjectDao {
	private static final String GET_ALL_PROJECTS_Q = "";
	private PreparedStatement getAllProjectsPS;
	private static final String INSERT_PERSON_TO_PROJECT_Q = "";
	private PreparedStatement insertPersonToProjectPS;
	private static final String GET_PERSON_ON_PROJECT_Q = "";
	private PreparedStatement getPersonOnProjectPS;
	private static final String INSER_Q = "";
	private PreparedStatement insertPS;
	private static final String UPDATE_Q = "";
	private PreparedStatement updatePS;
	private static final String DELETE_Q = "";
	private PreparedStatement deletePS;
	
	public ProjectDaoMsSql() {
		init();
	}
	
	private void init() {
		final DBConnection con = DBConnection.getInstance();

		try {
			getAllProjectsPS = con.prepareStatement(GET_ALL_PROJECTS_Q);

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Project buildObject(ResultSet rs) {
		return null;
	}
	
	public List<Project> buildObjects(ResultSet rs) {
		return null;
	}
}
