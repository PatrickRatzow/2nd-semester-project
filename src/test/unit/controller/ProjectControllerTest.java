package test.unit.controller;

import controller.ProjectController;
import dao.DaoFactory;
import dao.ProjectDao;
import datasource.DBConnection;
import datasource.DBConnectionPool;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UnitTest")
public class ProjectControllerTest {
	@InjectMocks
	private ProjectController projectController;
	private ProjectDao dao;
	private DBConnection connection;
	
	@BeforeEach
	void setup() {
		dao = mock(ProjectDao.class);
		connection = mock(DBConnection.class);
		DaoFactory factory = mock(DaoFactory.class);
		when(connection.getDaoFactory()).thenReturn(factory);
		when(factory.createProjectDao()).thenReturn(dao);
		projectController = new ProjectController();
	}
	
	@Test
	void testSucceedsFindingProjectByIdIfItExists() throws DataAccessException, InterruptedException {
		try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
			// Arrange
			CountDownLatch lock = new CountDownLatch(1);
			int id = 1;
			boolean fullAssociation = true;
			Project project = new Project();
			project.setId(id);
			AtomicReference<Project> returnProject = new AtomicReference<>();
			DBManager manager = mock(DBManager.class);
			mocked.when(DBManager::getInstance).thenReturn(manager);
			DBConnectionPool pool = mock(DBConnectionPool.class);
			when(manager.getPool()).thenReturn(pool);
			when(pool.getConnection()).thenReturn(connection);
			when(dao.findById(id, fullAssociation)).thenReturn(new Project());
			when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
				Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
				callback.accept(connection);
				
				if (connection != null) {
					connection.release();
				}
			}));
			
			// Act
			projectController.addFindProjectListener(p ->  {
				returnProject.set(p);
				
				lock.countDown();
			});
			projectController.getFullProject(project);
			
			lock.await(200, TimeUnit.MILLISECONDS);
			
			// Assert
			assertNotNull(returnProject.get());
			verify(dao, times(1)).findById(id, fullAssociation);
		}
	}
	
	@Test
	void testFailsToFindProjectByIdIfDoesntExist() throws DataAccessException, InterruptedException {
		try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
			// Arrange
			CountDownLatch lock = new CountDownLatch(1);
			int id = 1;
			boolean fullAssociation = true;
			Project project = new Project();
			project.setId(id);
			AtomicReference<Project> returnProject = new AtomicReference<>(project);
			DBManager manager = mock(DBManager.class);
			mocked.when(DBManager::getInstance).thenReturn(manager);
			DBConnectionPool pool = mock(DBConnectionPool.class);
			when(manager.getPool()).thenReturn(pool);
			when(pool.getConnection()).thenReturn(connection);
			when(dao.findById(id, fullAssociation)).thenReturn(null);
			when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
				Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
				callback.accept(connection);
				
				if (connection != null) {
					connection.release();
				}
			}));
			
			// Act
			projectController.addFindProjectListener(p ->  {
				returnProject.set(p);
				
				lock.countDown();
			});
			projectController.getFullProject(project);
			
			lock.await(200, TimeUnit.MILLISECONDS);
			
			// Assert
			assertNull(returnProject.get());
			verify(dao, times(1)).findById(id, fullAssociation);
		}
	}
	
	@Test
	void testSucceedsFindingAllProjects() throws DataAccessException, InterruptedException {
		try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
			// Arrange
			CountDownLatch lock = new CountDownLatch(1);
			boolean fullAssociation = false;
			List<Project> projects = new LinkedList<>();
			projects.add(new Project(1, ""));
			projects.add(new Project(2, ""));
			AtomicReference<List<Project>> returnProjects = new AtomicReference<>();
			
			DBManager manager = mock(DBManager.class);
			mocked.when(DBManager::getInstance).thenReturn(manager);
			DBConnectionPool pool = mock(DBConnectionPool.class);
			when(manager.getPool()).thenReturn(pool);
			when(pool.getConnection()).thenReturn(connection);
			when(dao.findAll(fullAssociation)).thenReturn(projects);
			when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
				Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
				callback.accept(connection);
				
				if (connection != null) {
					connection.release();
				}
			}));
			
			// Act
			projectController.addFindListener(ps -> {
				returnProjects.set(ps);
				
				lock.countDown();
			});
			projectController.getAll();
			
			lock.await(200, TimeUnit.MILLISECONDS);
			
			// Assert
			assertNotNull(returnProjects.get());
			assertEquals(returnProjects.get().size(), projects.size());
			verify(dao, times(1)).findAll(fullAssociation);
		}
	}
	
	@Test
	void testSucceedsFindingProjectsByName() throws DataAccessException, InterruptedException {
		try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
			// Arrange
			CountDownLatch lock = new CountDownLatch(1);
			boolean fullAssociation = false;
			String name = "Test";
			List<Project> projects = new LinkedList<>();
			projects.add(new Project(4, name));
			AtomicReference<List<Project>> returnProjects = new AtomicReference<>();
			
			DBManager manager = mock(DBManager.class);
			mocked.when(DBManager::getInstance).thenReturn(manager);
			DBConnectionPool pool = mock(DBConnectionPool.class);
			when(manager.getPool()).thenReturn(pool);
			when(pool.getConnection()).thenReturn(connection);
			when(dao.findByName(name, fullAssociation)).thenReturn(projects);
			when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
				Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
				callback.accept(connection);
				
				if (connection != null) {
					connection.release();
				}
			}));
			
			// Act
			projectController.addFindListener(ps -> {
				returnProjects.set(ps);
				
				lock.countDown();
			});
			projectController.getSearchByName("Test");
			
			lock.await(200, TimeUnit.MILLISECONDS);
			
			// Assert
			assertNotNull(returnProjects.get());
			assertEquals(returnProjects.get().size(), projects.size());
			verify(dao, times(1)).findByName(name, fullAssociation);
		}
	}
	
	@Test
	void testSucceedsCreatingProject() throws Exception {
		try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
			// Arrange
			CountDownLatch lock = new CountDownLatch(1);
			Project project = mock(Project.class);
			doNothing().when(project).validate();
			projectController = new ProjectController(project);
			AtomicReference<Project> returnProject = new AtomicReference<>();
			DBManager manager = mock(DBManager.class);
			mocked.when(DBManager::getInstance).thenReturn(manager);
			DBConnectionPool pool = mock(DBConnectionPool.class);
			when(manager.getPool()).thenReturn(pool);
			when(pool.getConnection()).thenReturn(connection);
			when(dao.create(project)).thenReturn(project);
			when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
				Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
				callback.accept(connection);
				
				if (connection != null) {
					connection.release();
				}
			}));
			
			// Act
			projectController.addSaveListener(p -> {
				returnProject.set(p);
				
				lock.countDown();
			});
			projectController.save();
			
			lock.await(200, TimeUnit.MILLISECONDS);
			
			// Assert
			assertNotNull(returnProject.get());
			verify(dao, times(1)).create(project);
		}
	}
	
	@Test
	void testSucceedsUpdatingProject() throws Exception {
		try (MockedStatic<DBManager> mocked = mockStatic(DBManager.class)) {
			// Arrange
			CountDownLatch lock = new CountDownLatch(1);
			Project project = mock(Project.class);
			when(project.getId()).thenReturn(1);
			doNothing().when(project).validate();
			projectController = new ProjectController(project);
			AtomicReference<Project> returnProject = new AtomicReference<>();
			DBManager manager = mock(DBManager.class);
			mocked.when(DBManager::getInstance).thenReturn(manager);
			DBConnectionPool pool = mock(DBConnectionPool.class);
			when(manager.getPool()).thenReturn(pool);
			when(pool.getConnection()).thenReturn(connection);
			doNothing().when(dao).update(project);
			when(manager.getConnectionThread(any(Consumer.class))).thenAnswer(ans -> new Thread(() -> {
				Consumer<DBConnection> callback = (Consumer<DBConnection>) ans.getArguments()[0];
				callback.accept(connection);
				
				if (connection != null) {
					connection.release();
				}
			}));
			
			// Act
			projectController.addSaveListener(p -> {
				returnProject.set(p);
				
				lock.countDown();
			});
			projectController.save();
			
			lock.await(200, TimeUnit.MILLISECONDS);
			
			// Assert
			assertNotNull(returnProject.get());
			verify(dao, times(1)).update(project);
		}
	}
	
	@AfterEach
	void teardown() {
		reset(dao);
		reset(connection);
	}
}
