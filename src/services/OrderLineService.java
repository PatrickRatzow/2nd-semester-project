package services;

import database.*;
import model.Order;
import model.OrderLine;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderLineService {
    private final OrderLineDao orderLineDao;
    private final ProductDao productDao;

    public OrderLineService(OrderLineDao orderLineDao, ProductDao productDao) {
        this.orderLineDao = orderLineDao;
        this.productDao = productDao;
    }

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
