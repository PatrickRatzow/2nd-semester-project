package service.mssql;

import exception.DataAccessException;
import exception.DataWriteException;
import model.Order;
import model.OrderLine;
import model.Product;
import persistance.OrderLineDao;
import persistance.OrderLineDto;
import persistance.ProductDao;
import persistance.mssql.OrderLineDaoMsSql;
import persistance.mssql.ProductDaoMsSql;

import java.util.ArrayList;
import java.util.List;

public class OrderLineService {
    private final OrderLineDao orderLineDao = new OrderLineDaoMsSql();
    private final ProductDao productDao = new ProductDaoMsSql();

    public List<OrderLine> findByOrderId(int id) throws DataAccessException {
        final List<OrderLine> orderLines = new ArrayList<>();
        final List<OrderLineDto> orderLinesDto = orderLineDao.findByOrderId(id);
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
