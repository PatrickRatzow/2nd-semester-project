DECLARE @CurrentDate DATETIME2 = GETUTCDATE();
DECLARE @WeekForward DATETIME2 = DATEADD(DAY, 7, GETUTCDATE());

INSERT INTO project_invoice(project_id, created_at, due_date, to_pay, has_paid)
VALUES (1, @CurrentDate, @WeekForward, 500000, 0),
       (2, @CurrentDate, @WeekForward, 2500000, 0);