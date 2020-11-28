DECLARE @CurrentDate DATETIME2 = GETUTCDATE();
DECLARE @WeekForward DATETIME2 = DATEADD(DAY, 7, GETUTCDATE());

EXEC InsertOrderInvoice @CurrentDate, @WeekForward, 3, 500000, 1;
EXEC InsertOrderInvoice @CurrentDate, @WeekForward, 4, 150000, 2;