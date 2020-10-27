CREATE PROCEDURE UpdateProduct
@Id INT,
@Name NVARCHAR(255),
@Description NVARCHAR(MAX),
@CategoryName NVARCHAR(255),
@Price INT
AS
UPDATE products
SET name = @Name,
    description = @Description,
    categoryId = (SELECT id FROM products_categories WHERE name = @CategoryName)
WHERE id = @Id;
DECLARE @CurrentPrice INT = (SELECT price FROM products_prices WHERE productId = @Id AND endTime > GETUTCDATE());
IF @CurrentPrice != @Price
    EXEC AddPriceToProduct @Id, @Price
;
