CREATE PROCEDURE InsertInvoice
    @CreatedAt DATETIME2,
    @DueDate DATE,
    @ToPay INT,
    @HasPaid INT,
    @InvoiceId INT = NULL OUTPUT
AS
    INSERT INTO invoice(created_at, due_date, to_pay, has_paid)
    VALUES(@CreatedAt, @DueDate, @ToPay, @HasPaid);
    SET @InvoiceId = (SELECT IDENT_CURRENT('invoice'));
;