package database;

import model.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDaoMsSql implements ProjectDao {
	private static final String FIND_ALL_Q = "";
	private PreparedStatement findAllPS;
	private static final String ADD_PERSON_TO_PROJECT_Q = "";
	private PreparedStatement addPersonToProjectPS;
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
		final DBConnection con = DBConnection.getInstance();

		try {
			findAllPS = con.prepareStatement(FIND_ALL_Q);
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
