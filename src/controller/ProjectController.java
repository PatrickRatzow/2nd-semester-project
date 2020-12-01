package controller;


import dao.ProjectDao;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Project;
import exception.DataAccessException;

import java.util.List;

public class ProjectController {
    public List<Project> findByName(String name, boolean fullAssociation) throws DataAccessException {
        DBConnection connection = DBManager.getPool().getConnection();
        ProjectDao projectDao = DBManager.getDaoFactory().createProjectDao(connection);
        List<Project> projects = projectDao.findByName(name, fullAssociation);

        connection.release();

        return projects;
    }
}
