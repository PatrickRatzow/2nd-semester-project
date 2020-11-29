CREATE VIEW GetOrderInvoices AS
SELECT
    ol.order_id AS order_id,
    i.created_at AS created_at,
    i.due_date AS due_date,
    i.to_pay AS to_pay,
    i.has_paid AS has_paid
FROM order_invoice ol
LEFT JOIN invoice i on ol.id = i.id
