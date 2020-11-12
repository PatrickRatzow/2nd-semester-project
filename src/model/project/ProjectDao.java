package model.project;


import java.util.List;

import exception.DataAccessException;
import model.Person;

public interface ProjectDao {

	public List<Project> findAll() throws DataAccessException;
	
	public void findProjectByCustomer();
	
	public Person findPersonOnProject(Person p);
	
	public void addPersonToProject();
	
	public void create();
	
	public void update();
	
	public void delete();
	
	
	
}
