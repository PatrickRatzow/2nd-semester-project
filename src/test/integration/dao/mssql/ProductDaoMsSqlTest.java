package test.integration.dao.mssql;

import dao.mssql.ProductDaoMsSql;
import datasource.DBConnection;
import datasource.DBManager;
import exception.DataAccessException;
import model.Product;
import model.Requirement;
import model.Specification;
import model.specifications.Window;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductDaoMsSqlTest {
	private static DBConnection connection;
	private static ProductDaoMsSql dao;
	
	@BeforeAll
	static void setup() {
		connection = DBManager.getPool().getConnection();
		dao = new ProductDaoMsSql(connection);
	}
	
	@Test
	void canFindBySpecification() throws DataAccessException {
		// Arrange
		Product product = null;
		Specification spec = new Window();
		spec.setDisplayName("Test");
		spec.setResultAmount(5);
		List<Requirement> requirements = spec.getRequirements();
		for (Requirement requirement : requirements) {
			String name = requirement.getName();
			switch (name) {
				case "Color":
					requirement.setValueFromSQLValue("Roed");
					break;
				
				case "Width":
					requirement.setValueFromSQLValue("150");
					break;
				
				case "Height":
					requirement.setValueFromSQLValue("100");
					break;
			}
		}
		spec.setRequirements(requirements);
		
		// Act
		product = dao.findBySpecification(spec);
		
		// Assert
		assertNotNull(product);
	}

	@AfterAll
	static void teardown() {
		connection.release();
	}
}
