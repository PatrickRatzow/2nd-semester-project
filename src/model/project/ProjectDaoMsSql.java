package model.project;

import model.DBConnection;
import model.Person;
import model.Price;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DataAccessException;

public class ProjectDaoMsSql implements ProjectDao {
	private static final String FIND_ALL_Q = "select * from project";
	private PreparedStatement findAllPS;
	private static final String ADD_PERSON_TO_PROJECT_Q = "";
	private PreparedStatement addPersonToProjectPS;
	private static final String FIND_PROJECT_BY_CUSTOMER_Q = "";
	private PreparedStatement findProjectByCustomer;
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
			addPersonToProjectPS = con.prepareStatement(ADD_PERSON_TO_PROJECT_Q);
			findProjectByCustomer = con.prepareStatement(FIND_PROJECT_BY_CUSTOMER_Q);
			

		} catch(SQLException e) {
			
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
	
	private Project buildObject(ResultSet rs) {
		final Project project = new Project();
		
		try {
			project.setId(rs.getInt(""));
			project.setName(rs.getString(""));
			project.setPrice(new Price(rs.getInt("")));
		} catch(SQLException e) {
			
		}
		
		return project;
	}
	
	private List<Project> buildObjects(ResultSet rs) {
		final List<Project> projects = new ArrayList<>();
		
		try {
			while(rs.next()) {
				projects.add(buildObject(rs));
			}
		} catch(SQLException e) {
			
		}
		
		
		return projects;
	}}
