CREATE VIEW GetProductsWithPriceHistory AS
SELECT
    p.id AS productId,
    p.name AS productName,
    p.description AS productDesc,
    pp.price AS productPrice,
    pp.startTime as productPriceStartTime,
    pp.endTime as productPriceEndTime
FROM products p
LEFT JOIN products_prices pp ON p.id = pp.productId