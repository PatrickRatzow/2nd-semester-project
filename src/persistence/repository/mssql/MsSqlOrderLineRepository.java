package persistence.repository.mssql;

import exception.DataAccessException;
import exception.DataWriteException;
import model.OrderLine;
import model.Product;
import persistence.dao.OrderLineDao;
import persistence.dao.mssql.MsSqlOrderLineDao;
import persistence.repository.OrderLineRepository;
import persistence.repository.ProductRepository;
import persistence.repository.mssql.dto.OrderLineDto;

import java.util.ArrayList;
import java.util.List;

public class MsSqlOrderLineRepository implements OrderLineRepository {
    private final OrderLineDao orderLineDao = new MsSqlOrderLineDao();
    private final ProductRepository productRepository = new MsSqlProductRepository();

    @Override
    public List<OrderLine> findById(final int id) throws DataAccessException {
        final List<OrderLine> orderLines = new ArrayList<>();
        final List<Thread> threads = new ArrayList<>();

        final List<OrderLineDto> orderLinesDto = orderLineDao.findByOrderId(id);
        for (OrderLineDto orderLineDto : orderLinesDto) {
            Thread thread = new Thread(() -> {
                try {
                    final Product product = productRepository.findById(orderLineDto.getProductId());
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
