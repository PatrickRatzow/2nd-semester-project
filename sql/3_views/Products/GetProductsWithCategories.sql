CREATE VIEW GetProductsWithCategories AS
SELECT
    p.id AS productId,
    p.name AS productName,
    p.description AS productDesc,
    pp.price AS productPrice,
    pc.id AS productCategoryId,
    pc.name AS productCategoryName,
    pc.description AS productCategoryDesc
FROM products_categories pc
LEFT JOIN products p ON p.categoryId = pc.id
LEFT JOIN products_prices pp ON p.id = pp.productId WHERE pp.endTime > GETUTCDATE();