package dao.mssql;

import dao.ProductDao;
import datasource.DBConnection;
import exception.DataAccessException;
import model.Price;
import model.Product;
import model.Requirement;
import model.Specification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Product db.
 */
public class ProductDaoMsSql implements ProductDao {
    private static final String FIND_ALL_Q = "SELECT * FROM GetProducts";
    private PreparedStatement findAllPS;
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetProducts WHERE id = ? " +
            "AND GETUTCDATE() BETWEEN price_start_time AND price_end_time " +
            "ORDER BY price_end_time DESC";
    private PreparedStatement findByIdPS;
    private static final String FIND_BY_NAME_Q = "SELECT * FROM GetProducts WHERE name LIKE CONCAT('%', ?, '%') " +
            "GETUTCDATE() BETWEEN price_start_time AND price_end_time" +
            "ORDER BY price_end_time DESC";
    private PreparedStatement findByNamePS;
    private DBConnection connection;

    /**
     * Instantiates a new Product db.
     */
    public ProductDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        connection = conn;

        try {
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
            findAllPS = conn.prepareStatement(FIND_ALL_Q);
            findByNamePS = conn.prepareStatement(FIND_BY_NAME_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product buildObject(ResultSet rs) throws SQLException {
        final Product product = new Product();

        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDesc(rs.getString("description"));
        product.setPrice(new Price(rs.getInt("price")));

        return product;
    }

    private List<Product> buildObjects(ResultSet rs) throws SQLException {
        final Map<Integer, Product> products = new HashMap<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            Product product = products.get(id);
            if (product == null) {
                product = buildObject(rs);
            }
            /*
            String fieldId = rs.getString("field_id");
            if (fieldId != null) {
                String fieldValue = rs.getString("field_value");
                // TODO: Create some kind of factory so we can just inject fields
                product.setField(fieldId, fieldValue);
            }*/

            products.put(id, product);
        }

        return new LinkedList<>(products.values());
    }

    @Override
    public List<Product> findAll() throws DataAccessException {
        List<Product> products;

        try {
            ResultSet rs = findAllPS.executeQuery();
            products = buildObjects(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find all products");
        }

        return products;
    }

    @Override
    public Product findById(int id) throws DataAccessException {
        Product product = null;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();

            if (rs.next()) {
                product = buildObjects(rs).get(0);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find any product with id " + id);
        }

        return product;
    }

    @Override
    public Product findBySpecification(Specification specification) throws DataAccessException {
        Product product = null;
        
        try {
            List<String> parameters = new LinkedList<>();
            String specificationId = specification.getId();
            List<Requirement> requirements = specification.getRequirements();
            String whereStr = requirements.stream()
                    .map(e -> {
                        parameters.add(e.getSQLKey());
                        parameters.add(e.getSQLValue());

                        return "(pf2.field_id = ? AND pf2.value = ?)";
                    })
                    .collect(Collectors.joining(" OR "));

            String query = "SELECT TOP " + requirements.size() +
                    "    p.id AS id, " +
                    "    p.description AS description, " +
                    "    p.name AS name, " +
                    "    pf.field_id AS field_id, " +
                    "    pf.value AS field_value, " +
                    "    pp.price AS price, " +
                    "    pp.start_time AS price_start_time, " +
                    "    pp.end_time AS price_end_time " +
                    "FROM product p " +
                    "INNER JOIN product_price pp ON p.id = pp.product_id AND GETUTCDATE() BETWEEN pp.start_time AND pp.end_time " +
                    "INNER JOIN product_category pc ON p.category_id = pc.id " +
                    "INNER JOIN product_field pf ON p.id = pf.product_id " +
                    "WHERE p.category_id IN (" +
                    "    SELECT" +
                    "        stpc.product_category_id" +
                    "    FROM specification s" +
                    "    INNER JOIN specification_to_product_category stpc ON s.id = stpc.specification_id" +
                    "    WHERE s.id = ?" +
                    ") AND (" +
                    "    SELECT COUNT(*)" +
                    "    FROM product_field pf2" +
                    "    WHERE pf2.product_id = p.id" +
                    "      AND (" + whereStr + ")" +
                    "  ) = ? " +
                    " ORDER BY pp.price, p.id";
            PreparedStatement ps = connection.prepareStatement(query);
            int i = 0;
            ps.setString(++i, specificationId);
            for (String value : parameters) {
                ps.setString(++i, value);
            }
            ps.setInt(++i, requirements.size());
            ResultSet rs = ps.executeQuery();

            List<Product> products = buildObjects(rs);
            Iterator<Product> iterator = products.iterator();
            if (iterator.hasNext()) {
                product = iterator.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any products for specification " + specification);
        }

        return product;
    }

    @Override
    public List<Product> findByIds(List<Integer> ids) throws DataAccessException {
        if (ids.size() == 0) throw new DataAccessException("Empty list passed! You need at least 1 entry");

        List<Product> products = new LinkedList<>();

        try {
            String idParameters = ids.stream().map(x -> "?").collect(Collectors.joining(","));
            String query = "SELECT * FROM GetProducts WHERE id IN (" + idParameters + ") " +
                    "AND GETUTCDATE() BETWEEN price_start_time AND price_end_time " +
                    "ORDER BY price_end_time DESC";
            PreparedStatement stmt = connection.prepareStatement(query);
            int size = ids.size();
            for (int i = 0; i < size; i++) {
                stmt.setInt(i + 1, ids.get(i));
            }
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                products = buildObjects(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to find any product with ids " + ids.toString());
        }

        return products;
    }

    @Override
    public List<Product> findByName(String name) throws DataAccessException {
        List<Product> products;

        try {
            findByNamePS.setString(1, name);
            ResultSet rs = findByNamePS.executeQuery();
            products = buildObjects(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Unable to find any product with this name");
        }

        return products;
    }
}
