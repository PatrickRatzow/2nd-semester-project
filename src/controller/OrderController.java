package controller;

import dao.OrderDao;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Order;
import entity.Project;
import exception.DataAccessException;
import exception.DataWriteException;

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

    /*
    public List<Product> findProductsByName(String name, boolean fullAssociation) throws DataAccessException {
        return productController.findByName(name, fullAssociation);
    }

    public void addProductById(int id, int quantity, boolean fullAssociation) throws DataAccessException {
        if (order == null) {
            order = new Order();
        }

        Product product = productController.findById(id, fullAssociation);
        OrderLine newOrderLine = new OrderLine(product, quantity);
        OrderLine orderLine = order.getOrderLines().get(newOrderLine);

        if (orderLine == null) {
            orderLine = newOrderLine;
        } else {
            orderLine.setQuantity(orderLine.getQuantity() + quantity);
        }

        order.addOrderLine(orderLine);
    }
*/
    public Order findById(int id, boolean fullAssociation) throws DataAccessException {
        DBConnection connection = DBManager.getPool().getConnection();
        OrderDao orderDao = DBManager.getDaoFactory().createOrderDao(connection);

        return orderDao.findById(id, fullAssociation);
    }

    public void create() throws DataWriteException {
        DBConnection connection = DBManager.getPool().getConnection();
        OrderDao orderDao = DBManager.getDaoFactory().createOrderDao(connection);

        //orderDao.create(
        //orderService.create(order, project);
    }

    public void cancel() {
        order = null;
    }

    public Order getOrder() {
        return order;
    }
}
