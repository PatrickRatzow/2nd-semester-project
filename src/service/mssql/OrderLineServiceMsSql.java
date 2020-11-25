package service.mssql;

import dao.OrderLineDao;
import dao.ProductDao;
import dao.mssql.OrderLineDaoMsSql;
import dao.mssql.ProductDaoMsSql;
import dto.OrderLineDto;
import exception.DataAccessException;
import exception.DataWriteException;
import entity.OrderLine;
import entity.Product;
import service.OrderLineService;

import java.util.ArrayList;
import java.util.List;

public class OrderLineServiceMsSql implements OrderLineService {
    private final OrderLineDao orderLineDao = new OrderLineDaoMsSql();
    private final ProductDao productDao = new ProductDaoMsSql();

    @Override
    public List<OrderLine> findById(final int id) throws DataAccessException {
        final List<OrderLine> orderLines = new ArrayList<>();
        final List<Thread> threads = new ArrayList<>();

        final List<OrderLineDto> orderLinesDto = orderLineDao.findByOrderId(id);
        for (OrderLineDto orderLineDto : orderLinesDto) {
            final Thread thread = new Thread(() -> {
                try {
                    final Product product = productDao.findById(orderLineDto.getProductId());
                    final OrderLine orderLine = new OrderLine(product, orderLineDto.getOrderId());
                    orderLines.add(orderLine);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return orderLines;
    }

    @Override
    public OrderLine create(final int orderId, final OrderLine orderLine) throws DataWriteException {
        return orderLineDao.create(orderId, orderLine.getProduct().getId(), orderLine.getQuantity());
    }
}
