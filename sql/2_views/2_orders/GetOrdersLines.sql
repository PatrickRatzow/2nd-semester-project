CREATE VIEW GetOrdersLines AS
SELECT
    ol.orderId as orderLineOrderId,
    ol.quantity AS orderLineQuantity,
    p.*
FROM orders_lines ol
LEFT JOIN GetProducts p ON ol.productId = p.productId;