CREATE PROCEDURE AddPriceToProduct
    @ProductId INT,
    @Price Int
AS
    UPDATE products_prices
    SET endTime = GETUTCDATE()
    WHERE productId = @ProductId AND endTime > GETUTCDATE();
    INSERT INTO products_prices(productId, price)
    VALUES (@ProductId, @Price);
;