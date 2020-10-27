CREATE PROCEDURE InsertProduct
    @Name NVARCHAR(255),
    @Description NVARCHAR(MAX),
    @CategoryName NVARCHAR(255),
    @Price INT,
    @ProductId INT = NULL OUTPUT
AS
    BEGIN TRANSACTION;

    INSERT INTO products(name, description, categoryId)
    VALUES(@Name, @Description, (SELECT id FROM products_categories WHERE name = @CategoryName));
    SET @ProductId = (SELECT IDENT_CURRENT('products'));
    EXEC AddPriceToProduct @ProductId, @Price;

    COMMIT TRANSACTION;
;