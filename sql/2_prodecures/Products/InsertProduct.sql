CREATE PROCEDURE InsertProduct
    @Name NVARCHAR(255),
    @Description NVARCHAR(MAX),
    @CategoryId INT,
    @SupplierId INT,
    @Price INT,
    @ProductId INT = NULL OUTPUT
AS
    BEGIN TRANSACTION;

    INSERT INTO products(name, description, categoryId, supplierId)
    VALUES(@Name, @Description, @CategoryId, @SupplierId);
    SET @ProductId = (SELECT IDENT_CURRENT('products'));
    EXEC AddPriceToProduct @ProductId, @Price;

    COMMIT TRANSACTION;
;