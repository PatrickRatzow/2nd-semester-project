DECLARE @LoftsvindueId INT = (SELECT id FROM product_category WHERE name = 'Loftsvinduer');
DECLARE @DannebrogsVindueId INT = (SELECT id FROM product_category WHERE name = 'Dannebrogsvinduer');

EXEC InsertProduct N'Rødt mellem loftsvindue', '', @LoftsvindueId, 200000;
EXEC InsertProduct N'Rødt stort loftsvindue', '', @LoftsvindueId, 250000;
EXEC InsertProduct N'Rødt mellem loftsvindue - høj kvalitet', '', @LoftsvindueId, 300000;
EXEC InsertProduct N'Grøn gammeldags lille dannebrogsvindue', '', @DannebrogsVindueId, 150000;
EXEC InsertProduct N'Grøn lille dannebrogsvindue', '', @DannebrogsVindueId, 100000;
EXEC InsertProduct N'Blå stort dannebrogsvindue', '', @DannebrogsVindueId, 200000;