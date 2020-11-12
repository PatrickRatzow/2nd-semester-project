package database;

import model.OrderInvoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderInvoiceDaoMsSql implements OrderInvoiceDao {
    @Override
    public List<OrderInvoice> findAllByOrderId(int id) {
        return null;
    }

    @Override
    public OrderInvoice create(int orderId, LocalDateTime createdAt, LocalDate dueDate, int toPay, int hasPaid) {
        return null;
    }
}
