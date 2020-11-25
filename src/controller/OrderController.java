package controller;

import datasource.DataSourceManager;
import entity.Order;
import entity.OrderLine;
import entity.Product;
import entity.Project;
import exception.DataAccessException;
import exception.DataWriteException;
import service.OrderService;

import java.util.List;

public class OrderController {
    private OrderService orderService;
    private ProductController productController;
    private Project project;
    private Order order;

    public OrderController(Project project, ProductController productController, OrderService orderService) {
        this.project = project;
        this.productController = new ProductController();
        this.orderService = DataSourceManager.getServiceFactory().createOrderService();
    }

    public OrderController(Project project, ProductController productController) {
        this(project, productController, DataSourceManager.getServiceFactory().createOrderService());
    }

    public OrderController(Project project) {
        this(project, new ProductController(), DataSourceManager.getServiceFactory().createOrderService());
    }

    public List<Product> findProductsByName(String name) throws DataAccessException {
        return productController.findByName(name);
    }

    public void addProductById(int id, int quantity) throws DataAccessException {
        if (order == null) {
            order = new Order();
        }

        Product product = productController.findById(id);
        OrderLine newOrderLine = new OrderLine(product, quantity);
        OrderLine orderLine = order.getOrderLines().get(newOrderLine);

        if (orderLine == null) {
            orderLine = newOrderLine;
        } else {
            orderLine.setQuantity(orderLine.getQuantity() + quantity);
        }

        order.addOrderLine(orderLine);
    }

    public Order findById(int id) throws DataAccessException {
        return orderService.findById(id);
    }

    public void create() throws DataWriteException {
        orderService.create(order, project);
    }

    public void cancel() {
        order = null;
    }

    public Order getOrder() {
        return order;
    }
}
