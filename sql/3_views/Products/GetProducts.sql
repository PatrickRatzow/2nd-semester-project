CREATE VIEW GetProducts AS
SELECT
    p.id AS productId,
    p.name AS productName,
    p.description AS productDesc,
    pp.price AS productPrice,
    pt.name AS productCategoryName,
    pt.description AS productCategoryDesc,
    pt.id AS productCategoryId
FROM products p
LEFT JOIN products_categories pt ON p.categoryId = pt.id
LEFT JOIN products_prices pp ON p.id = pp.productId WHERE pp.endTime > GETUTCDATE();