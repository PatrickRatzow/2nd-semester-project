package service;

import exception.DataAccessException;
import entity.Project;

public interface ProjectService {
    Project findById(int id) throws DataAccessException;
}
