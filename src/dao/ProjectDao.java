package dao;


import dto.ProjectDto;
import exception.DataAccessException;

import java.util.List;

public interface ProjectDao {
	List<ProjectDto> findAll() throws DataAccessException;
	ProjectDto findById(int id) throws DataAccessException;
}
