CREATE VIEW GetOrderLines AS
SELECT
    ol.orderId as orderId,
    ol.productId AS productId,
    ol.quantity AS quantity
FROM orders_lines ol