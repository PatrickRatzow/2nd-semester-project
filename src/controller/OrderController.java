package controller;

import dao.OrderDao;
import datasource.DBConnection;
import datasource.DBManager;
import datasource.DataAccessException;
import model.Order;
import model.OrderLine;
import model.Price;
import model.Product;

import java.util.Collection;

public class OrderController {
    private Order order;

    public OrderController() {}

    public OrderController(Order order) {
        this.order = order;
    }

    public Collection<OrderLine> getOrderLines() {
        return order.getOrderLines().values();
    }
    
    public boolean hasOrder() {
    	return order != null;
    }
    
    public Price getPrice() {
        return new Price(getOrderLines().stream()
                .mapToInt(ol -> ol.getProduct().getPrice().getAmount() * ol.getQuantity())
                .reduce(0, Integer::sum));
    }
    
    public void addProduct(Product product, int quantity, String displayName) {
        if (product == null) throw new IllegalArgumentException("Product was null");
        if (quantity <= 0) throw new IllegalArgumentException("You need to add at least 1 of this product");
        if (displayName.isEmpty()) throw new IllegalArgumentException("Display name cannot be empty");
        
        if (order == null) {
            order = new Order();
        }

        OrderLine orderLine = order.getOrderLines().get(product.getId());
        if (orderLine == null) {
            orderLine = new OrderLine(product, quantity, displayName);
        } else {
            orderLine.setQuantity(orderLine.getQuantity() + quantity);
        }

        order.addOrderLine(orderLine);
    }

    public Order findById(int id, boolean fullAssociation) throws DataAccessException {
        final DBConnection connection = DBManager.getInstance().getPool().getConnection();
        final OrderDao orderDao = connection.getDaoFactory().createOrderDao();

        final Order order = orderDao.findById(id, fullAssociation);

        connection.release();

        return order;
    }
    
    public Order getOrder() {
        return order;
    }
}
