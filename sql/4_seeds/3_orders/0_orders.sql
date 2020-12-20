INSERT INTO [order](delivered, created_at, project_id)
VALUES (1, GETUTCDATE(), 1),
       (0, GETUTCDATE(), 2),
       (0, GETUTCDATE(), 3),
       (1, GETUTCDATE(), 4)