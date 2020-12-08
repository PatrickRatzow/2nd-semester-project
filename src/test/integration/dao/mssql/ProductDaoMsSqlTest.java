package test.integration.dao.mssql;

import dao.ProductDao;
import dao.mssql.DaoFactoryMsSql;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductDaoMsSqlTest {
	private static DBConnection connection;
	private static ProductDao dao;
	
	@BeforeAll
	static void setup() {
		connection = DBManager.getPool().getConnection();
		dao = new DaoFactoryMsSql().createProductDao(connection);
	}
	
	@Test
	void testCanFindProductBySpecification() throws DataAccessException {
		// Arrange
		Product product;
		Specification spec = new Window();
		spec.setDisplayName("Test");
		spec.setResultAmount(5);
		for (Requirement requirement : spec.getRequirements()) {
			String name = requirement.getName();
			switch(name) {
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
