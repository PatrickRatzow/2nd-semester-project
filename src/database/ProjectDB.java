package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.DBConnection;
import model.Person;
import model.project.Project;

public class ProjectDB {

	
	private static final String GET_ALL_PROJECTS_Q= "";
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
	
	
	public ProjectDB() {
		init();
	}
	
	private void init() {
		DBConnection con = DBConnection.getInstance();
		try {
			getAllProjectsPS = con.prepareStatement(GET_ALL_PROJECTS_Q);

		} catch(SQLException e) {
			
		}
		
	}
	
	public void findAll() {
		
	}
	
	public Person findPerson(Person p) {
		
		return p;
	}
	
	public void create() {
		
	}
	
	public void update() {
		
	}
	
	public void delete() {
		
	}
	
	public Project buildObject(ResultSet rs) {
		return null;
	}
	
	public List<Project> buildObjects(ResultSet rs) {
		return null;
	}
	
}
