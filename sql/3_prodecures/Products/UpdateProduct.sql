CREATE PROCEDURE UpdateProduct
    @Id INT,
    @Name NVARCHAR(255),
    @Description NVARCHAR(MAX),
    @CategoryId INT,
    @Price INT
AS
    UPDATE product
    SET name = @Name,
        description = @Description,
        category_id = @CategoryId
    WHERE id = @Id;
    DECLARE @CurrentPrice INT = (SELECT price FROM product_price WHERE product_id = @Id AND end_time > GETUTCDATE());
    IF @CurrentPrice != @Price
        EXEC AddPriceToProduct @Id, @Price
;