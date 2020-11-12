package model.order_invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderInvoiceDao {
    List<OrderInvoice> findAllByOrderId(int id);
    OrderInvoice create(int orderId, LocalDateTime createdAt, LocalDate dueDate, int toPay, int hasPaid);
}
