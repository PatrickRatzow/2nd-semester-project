package controller;

import dao.OrderDao;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Order;
import entity.OrderLine;
import entity.Product;
import entity.Project;
import exception.DataAccessException;
import exception.DataWriteException;

import java.sql.SQLException;
import java.util.List;

public class OrderController {
    private ProductController productController;
    private Project project;
    private Order order;

    public OrderController(Project project, ProductController productController) {
        this.project = project;
        this.productController = new ProductController();
    }
    public OrderController(Project project) {
        this(project, new ProductController());
    }

    public List<Product> findProductsByName(String name) throws DataAccessException {
        return productController.findByName(name);
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

    public Order create() throws DataWriteException {
        if (order.getOrderLines().size() == 0) throw new IllegalArgumentException("The order needs at least 1 product");
        if (order.getCustomer() == null) throw new IllegalArgumentException("The order needs a customer");
        if (order.getEmployee() == null) throw new IllegalArgumentException("The order needs an employee");
        if (order.getId() != 0) throw new IllegalArgumentException("Can't create an order that already exists");

        final Order newOrder;
        final DBConnection connection = DBManager.getPool().getConnection();
        final OrderDao orderDao = DBManager.getDaoFactory().createOrderDao(connection);

        try {
            connection.startTransaction();

            newOrder = orderDao.create(order);

            connection.commitTransaction();
            connection.release();
        } catch (SQLException e) {
            throw new DataWriteException("Unable to create Order: " + e.getMessage());
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
