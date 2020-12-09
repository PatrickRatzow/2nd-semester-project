package dao;

import datasource.DataAccessException;
import model.Project;

import java.util.List;

public interface ProjectDao {
    List<Project> findAll(boolean fullAssociation) throws DataAccessException;

    List<Project> findByName(String name, boolean fullAssociation) throws DataAccessException;

    Project findById(int id, boolean fullAssociation) throws DataAccessException;

    Project create(Project project, boolean fullAssociation) throws DataAccessException;

    void update(Project project, boolean fullAssociation) throws DataAccessException;
}
