CREATE PROCEDURE InsertProjectInvoice
    @CreatedAt DATETIME2,
    @DueDate DATE,
    @ToPay INT,
    @HasPaid INT,
    @ProjectId INT OUTPUT,
    @InvoiceId INT = NULL OUTPUT
AS
    EXEC InsertInvoice @CreatedAt, @DueDate, @ToPay, @HasPaid, @InvoiceId OUT
    INSERT INTO project_invoice(id, project_id)
    VALUES (@InvoiceId, @ProjectId);
;