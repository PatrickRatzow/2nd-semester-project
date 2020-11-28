DECLARE @CurrentDate DATETIME2 = GETUTCDATE();
DECLARE @WeekForward DATETIME2 = DATEADD(DAY, 7, GETUTCDATE());

EXEC InsertProjectInvoice @CurrentDate, @WeekForward, 3, 500000, 1;
EXEC InsertProjectInvoice @CurrentDate, @WeekForward, 4, 150000, 2;