CREATE PROCEDURE UpdateProduct
    @Id INT,
    @Name NVARCHAR(255),
    @Description NVARCHAR(MAX),
    @CategoryId INT,
    @SupplierId INT,
    @Price INT
AS
    UPDATE product
    SET name = @Name,
        description = @Description,
        category_id = @CategoryId,
        supplier_id = @SupplierId
    WHERE id = @Id;
    DECLARE @CurrentPrice INT = (SELECT price FROM product_price WHERE product_id = @Id AND end_time > GETUTCDATE());
    IF @CurrentPrice != @Price
        EXEC AddPriceToProduct @Id, @Price
;