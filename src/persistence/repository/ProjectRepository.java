package persistence.repository;

import exception.DataAccessException;
import model.Project;

public interface ProjectRepository {
    Project findById(int id) throws DataAccessException;
}
