package test.integration.dao.mssql;

import dao.ProductDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Product;
import model.Requirement;
import model.Specification;
import model.specifications.Window;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductDaoMsSqlTest {
	private static DBConnection connection;
	private static ProductDao dao;
	
	@BeforeAll
	static void setup() {
		connection = DBManager.getInstance().getPool().getConnection();
		dao = connection.getDaoFactory().createProductDao();
		try {
			connection.startTransaction();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	
	@Test
	void testCanFindProductBySpecification() throws DataAccessException {
		// Arrange
		Product product;
		Specification spec = new Window();
		for (Requirement requirement : spec.getRequirements()) {
			String id = requirement.getId();
			switch(id) {
				case "color":
					requirement.setValueFromSQLValue("Roed");
					break;
					
				case "width":
					requirement.setValueFromSQLValue("150");
					break;
					
				case "height":
					requirement.setValueFromSQLValue("100");
					break;
			}
		}
		
		// Act
		product = dao.findBySpecification(spec);
		
		// Assert
		assertNotNull(product);
	}
	
	
	@AfterAll
	static void teardown() {
		try {
			connection.rollbackTransaction();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		connection.release();
	}
}
