package model.project;


import exception.DataAccessException;
import model.Person;

import java.util.List;

public interface ProjectDao {
	List<ProjectDto> findAll() throws DataAccessException;
	ProjectDto findById(int id) throws DataAccessException;
	void findProjectByCustomer();
	Person findPersonOnProject(Person p);
	void addPersonToProject();
	void create();
	void update();
	void delete();
}
