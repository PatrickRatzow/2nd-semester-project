package service;

import exception.DataAccessException;
import model.Project;

public interface ProjectService {
    Project findById(int id) throws DataAccessException;
}
