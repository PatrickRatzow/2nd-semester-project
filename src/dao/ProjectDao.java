package dao;


import entity.Project;
import exception.DataAccessException;

import java.util.List;

public interface ProjectDao {
	List<Project> findByName(String name, boolean fullAssociation) throws DataAccessException;
	Project findById(int id, boolean fullAssociation) throws DataAccessException;
	Project create(Project project, boolean fullAssociation) throws DataAccessException;
}
