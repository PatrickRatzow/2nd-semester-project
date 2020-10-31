CREATE PROCEDURE UpdateProduct
    @Id INT,
    @Name NVARCHAR(255),
    @Description NVARCHAR(MAX),
    @CategoryId INT,
    @SupplierId INT,
    @Price INT
AS
    UPDATE products
    SET name = @Name,
        description = @Description,
        categoryId = @CategoryId,
        supplierId = @SupplierId
    WHERE id = @Id;
    DECLARE @CurrentPrice INT = (SELECT price FROM products_prices WHERE productId = @Id AND endTime > GETUTCDATE());
    IF @CurrentPrice != @Price
        EXEC AddPriceToProduct @Id, @Price
;
