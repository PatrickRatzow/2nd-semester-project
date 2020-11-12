package model.order_line;

import exception.DataAccessException;
import exception.DataWriteException;
import model.order.Order;
import model.product.Product;
import model.product.ProductDao;
import model.product.ProductDaoMsSql;

import java.util.ArrayList;
import java.util.List;

public class OrderLineService {
    private final OrderLineDao orderLineDao = new OrderLineDaoMsSql();
    private final ProductDao productDao = new ProductDaoMsSql();

    public List<OrderLine> findAllByOrderId(int orderId) throws DataAccessException {
        final List<OrderLine> orderLines = new ArrayList<>();
        final List<OrderLineDto> orderLinesDto = orderLineDao.findAllByOrderId(orderId);
        for (OrderLineDto orderLineDto : orderLinesDto) {
            Product product = productDao.findById(orderLineDto.getProductId());

            OrderLine orderLine = new OrderLine(product, orderLineDto.getOrderId());
            orderLines.add(orderLine);
        }

        return orderLines;
    }

    public void create(Order order, OrderLine orderLine) throws DataWriteException {
        orderLineDao.create(order.getId(), orderLine.getProduct().getId(), orderLine.getQuantity());
    }
}
