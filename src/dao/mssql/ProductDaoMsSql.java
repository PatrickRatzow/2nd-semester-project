package dao.mssql;

import dao.ProductDao;
import datasource.DBConnection;
import datasource.DataAccessException;
import model.Price;
import model.Product;
import model.Requirement;
import model.Specification;

import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDaoMsSql implements ProductDao {
    private DBConnection connection;

    public ProductDaoMsSql(DBConnection conn) {
        init(conn);
    }
    
    private void init(DBConnection conn) {
        connection = conn;
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
            String fieldId = rs.getString("field_id");
            if (fieldId != null) {
                String fieldValue = rs.getString("field_value");
                product.setField(fieldId, fieldValue);
            }

            products.put(id, product);
        }

        return new LinkedList<>(products.values());
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

                        return "(pf2.field_id = ? AND pf2.field_value = ?)";
                    })
                    .collect(Collectors.joining(" OR "));

            String query = "SELECT TOP " + requirements.size() + "\n" +
                    "    p.id AS id,\n" +
                    "    p.description AS description,\n" +
                    "    p.name AS name,\n" +
                    "    pf.field_id AS field_id,\n" +
                    "    pf.field_value AS field_value,\n" +
                    "    pp.price AS price,\n" +
                    "    pp.start_time AS price_start_time,\n" +
                    "    pp.end_time AS price_end_time\n" +
                    "FROM product p\n" +
                    "INNER JOIN product_price pp ON p.id = pp.product_id AND GETUTCDATE() BETWEEN pp.start_time AND pp.end_time\n" +
                    "INNER JOIN product_category pc ON p.category_id = pc.id\n" +
                    "INNER JOIN product_field pf ON p.id = pf.product_id\n" +
                    "WHERE p.category_id IN (\n" +
                    "    SELECT\n" +
                    "        stpc.product_category_id\n" +
                    "    FROM specification s\n" +
                    "    INNER JOIN specification_to_product_category stpc ON s.id = stpc.specification_id\n" +
                    "    WHERE s.id = ?\n" +
                    ") AND (\n" +
                    "    SELECT COUNT(*)\n" +
                    "    FROM product_field pf2\n" +
                    "    WHERE pf2.product_id = p.id\n" +
                    "      AND (" + whereStr + ")\n" +
                    "  ) = ?\n" +
                    "ORDER BY pp.price, p.id";
            PreparedStatement ps = connection.prepareStatement(query);
            int i = 0;
            ps.setString(++i, specificationId);
            for (String value : parameters) {
                ps.setString(++i, new String(value.getBytes(), Charset.forName("UTF-8")));
            }
            ps.setInt(++i, requirements.size());
            ResultSet rs = ps.executeQuery();

            List<Product> products = buildObjects(rs);
            if (!products.isEmpty()) {
                product = products.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Unable to find any products for specification " + specification);
        }

        return product;
    }

    @Override
    public List<Product> findByIds(List<Integer> ids) throws DataAccessException {
        List<Product> products = new LinkedList<>();

        try {
            String idParameters = ids.stream().map(x -> "?").collect(Collectors.joining(","));       
            String query = "SELECT * FROM view_product WHERE id IN (" + idParameters + ") " +
                    "AND GETUTCDATE() BETWEEN price_start_time AND price_end_time " +
                    "ORDER BY price_end_time DESC";
            PreparedStatement stmt = connection.prepareStatement(query);
            int i = 0;
            for (Integer id : ids) {
                stmt.setInt(++i, id);
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
}
