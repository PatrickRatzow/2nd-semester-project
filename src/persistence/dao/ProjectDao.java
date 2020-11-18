package persistence.dao;


import exception.DataAccessException;
import persistence.repository.mssql.dto.ProjectDto;

import java.util.List;

public interface ProjectDao {
	List<ProjectDto> findAll() throws DataAccessException;
	ProjectDto findById(int id) throws DataAccessException;
}
