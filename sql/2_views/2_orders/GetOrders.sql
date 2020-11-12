CREATE VIEW GetOrders AS
SELECT
    o.id as orderId,
    o.status as orderStatus,
    o.createdDate as orderCreatedDate,
    oi.*,
    ol.*
FROM orders o
LEFT JOIN GetOrdersInvoices oi ON o.id = oi.orderInvoiceOrderId
LEFT JOIN GetOrdersLines ol ON o.id = ol.orderLineOrderId
