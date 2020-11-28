CREATE PROCEDURE InsertOrderInvoice
    @CreatedAt DATETIME2,
    @DueDate DATE,
    @ToPay INT,
    @HasPaid INT,
    @OrderId INT OUTPUT,
    @InvoiceId INT = NULL OUTPUT
AS
    EXEC InsertInvoice @CreatedAt, @DueDate, @ToPay, @HasPaid, @InvoiceId OUT
    INSERT INTO order_invoice(id, order_id)
    VALUES (@InvoiceId, @OrderId);
;