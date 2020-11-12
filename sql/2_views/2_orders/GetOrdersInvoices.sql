CREATE VIEW GetOrdersInvoices AS
SELECT
    oi.id as orderInvoiceId,
    oi.orderId as orderInvoiceOrderId,
    oi.paid as orderInvoicePaid,
    c.*
FROM orders_invoices oi
LEFT JOIN GetCustomers c ON oi.customerId = c.personId;