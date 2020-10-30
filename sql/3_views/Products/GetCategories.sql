CREATE VIEW GetCategories AS
SELECT
    id AS productCategoryId,
    name AS productCategoryName,
    description AS productCategoryDesc
FROM products_categories

