CREATE VIEW GetOrders AS
SELECT
    o.id AS orderId,
    o.status AS orderStatus,
    o.createdDate AS orderCreatedDate,
    o.projectId AS projectId,
    o.employeeId AS employeeId,
    o.customerId AS customerId,
    ol.productId AS orderLineId,
    oi.orderId AS orderInvoiceId
FROM orders o
LEFT JOIN orders_lines ol on o.id = ol.orderId
LEFT JOIN orders_invoices oi on o.id = oi.orderId