CREATE PROCEDURE InsertProduct
    @Name NVARCHAR(255),
    @Description NVARCHAR(MAX),
    @CategoryId INT,
    @SupplierId INT,
    @Price INT,
    @ProductId INT = NULL OUTPUT
AS
    INSERT INTO product(name, description, category_id, supplier_id)
    VALUES(@Name, @Description, @CategoryId, @SupplierId);
    SET @ProductId = (SELECT IDENT_CURRENT('product'));
    EXEC AddPriceToProduct @ProductId, @Price;
;