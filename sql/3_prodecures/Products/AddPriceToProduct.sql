CREATE PROCEDURE AddPriceToProduct
    @ProductId INT,
    @Price Int
AS
    UPDATE product_price
    SET end_time = GETUTCDATE()
    WHERE product_id = @ProductId AND end_time > GETUTCDATE();
    INSERT INTO product_price(product_id, price)
    VALUES (@ProductId, @Price);
;