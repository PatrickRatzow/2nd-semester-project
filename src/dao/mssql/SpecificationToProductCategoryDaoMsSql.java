package dao.mssql;

import dao.ProductDao;
import dao.SpecificationToProductCategoryDao;
import datasource.DBConnection;
import entity.Product;
import entity.Specification;
import exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SpecificationToProductCategoryDaoMsSql implements SpecificationToProductCategoryDao {
    private static final String FIND_BY_ID_Q = "SELECT s.id AS id, stpc.product_category_id AS category_id " +
            "FROM specification s " +
            "INNER JOIN specification_to_product_category stpc ON s.id = stpc.specification_id " +
            "WHERE s.id = ?";
    private PreparedStatement findByIdPS;
    private DBConnection connection;

    public SpecificationToProductCategoryDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        connection = conn;

        try {
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> findBySpecificationId(Specification specification) throws DataAccessException {
        List<Product> products;

        try {
            String id = specification.getId();
            findByIdPS.setString(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            List<Integer> ids = new LinkedList<>();
            while (rs.next()) {
                ids.add(rs.getInt("category_id"));
            }

            ProductDao dao = new DaoFactoryMsSql().createProductDao(connection);
            products = dao.findByCategoryId(ids, specification.getRequirements(), 5);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to load specification category data");
        }

        return products;
    }
}
