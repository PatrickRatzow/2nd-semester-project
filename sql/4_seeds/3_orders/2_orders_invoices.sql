INSERT INTO orders_invoices(orderId, createdAt, dueDate, toPay, hasPaid)
VALUES (1, GETUTCDATE(), DATEADD(DAY, 7,GETUTCDATE()), 3, 500000),
       (2, GETUTCDATE(), DATEADD(DAY, 7,GETUTCDATE()), 4, 150000);