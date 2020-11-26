package dao;


import entity.Project;
import exception.DataAccessException;

import java.util.List;

public interface ProjectDao {
	List<Project> findAll() throws DataAccessException;
	Project findById(int id) throws DataAccessException;
}
