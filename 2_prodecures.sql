CREATE OR ALTER PROCEDURE AddPriceToProduct
    @ProductId INT,
    @Price Int
AS
    UPDATE products_prices
    SET endTime = GETUTCDATE()
    WHERE productId = @ProductId AND endTime > GETUTCDATE();
    INSERT INTO products_prices(productId, price)
    VALUES (@ProductId, @Price);
GO;

CREATE OR ALTER PROCEDURE InsertProduct
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
GO;

CREATE OR ALTER PROCEDURE UpdateProduct
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
    IF @CurrentPrice IS NULL OR @CurrentPrice != @Price
        EXEC AddPriceToProduct @Id, @Price
    ;
GO;