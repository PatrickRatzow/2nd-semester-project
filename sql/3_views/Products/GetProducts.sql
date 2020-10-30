CREATE VIEW GetProducts AS
SELECT
    p.id AS productId,
    p.name AS productName,
    p.description AS productDesc,
    pp.price AS productPrice
FROM products p
LEFT JOIN products_prices pp ON p.id = pp.productId WHERE pp.endTime > GETUTCDATE();