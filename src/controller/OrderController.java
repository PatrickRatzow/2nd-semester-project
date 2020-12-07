package controller;

import dao.OrderDao;
import datasource.DBConnection;
import datasource.DBManager;
import exception.DataAccessException;
import model.Order;
import model.OrderLine;
import model.Product;
import model.Project;

import java.sql.SQLException;

public class OrderController {
    private final ProductController productController;
    private final Project project;
    private Order order;

    public OrderController(Project project, ProductController productController) {
        this.project = project;
        this.productController = new ProductController();
    }

    public OrderController(Project project) {
        this(project, new ProductController());
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product was null");
        if (quantity <= 0) throw new IllegalArgumentException("You need to add at least 1 of this product");

        if (order == null) {
            order = new Order();
        }

        OrderLine orderLine = order.getOrderLines().get(product.getId());
        if (orderLine == null) {
            orderLine = new OrderLine(product, quantity);
        } else {
            orderLine.setQuantity(orderLine.getQuantity() + quantity);
        }

        order.addOrderLine(orderLine);
    }

    public Order findById(int id, boolean fullAssociation) throws DataAccessException {
        final DBConnection connection = DBManager.getPool().getConnection();
        final OrderDao orderDao = DBManager.getDaoFactory().createOrderDao(connection);

        final Order order = orderDao.findById(id, fullAssociation);

        connection.release();

        return order;
    }

    public Order create() throws DataAccessException {
        if (order.getOrderLines().size() == 0) throw new IllegalArgumentException("The order needs at least 1 product");
        if (order.getEmployee() == null) throw new IllegalArgumentException("The order needs an employee");
        if (order.getId() != 0) throw new IllegalArgumentException("Can't create an order that already exists");

        final Order newOrder;
        final DBConnection connection = DBManager.getPool().getConnection();
        final OrderDao orderDao = DBManager.getDaoFactory().createOrderDao(connection);

        try {
            connection.startTransaction();

            newOrder = orderDao.create(order, project);

            connection.commitTransaction();
            connection.release();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollbackTransaction();
            } catch (SQLException re) {
                re.printStackTrace();

                throw new DataAccessException("Unable to create Order: " + e.getMessage());
            }

            throw new DataAccessException("Unable to create Order: " + e.getMessage());
        }

        return newOrder;
    }

    public void cancel() {
        order = null;
    }

    public Order getOrder() {
        return order;
    }
}
