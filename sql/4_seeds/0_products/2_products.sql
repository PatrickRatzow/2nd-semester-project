DECLARE @TagstenId INT = (SELECT id FROM products_categories WHERE name = 'Tagsten');
DECLARE @TeglstenId INT = (SELECT id FROM products_categories WHERE name = 'Teglsten');
DECLARE @BygmaId INT = (SELECT id FROM suppliers WHERE name = 'Bygma');
DECLARE @XLBygId INT = (SELECT id FROM suppliers WHERE name = 'XL Byg');

EXEC InsertProduct 'Lille tagsten', '', @TagstenId, @BygmaId, 20000;
EXEC InsertProduct 'Tagsten', '', @TagstenId, @BygmaId, 250000;
EXEC InsertProduct 'Stor tagsten', '', @TagstenId, @XLBygId, 300000;
EXEC InsertProduct 'Lille teglsten', '', @TeglstenId, @BygmaId, 100000;
EXEC InsertProduct 'Teglsten', '', @TeglstenId, @BygmaId, 150000;
EXEC InsertProduct 'Stor teglsten', '', @TeglstenId, @BygmaId, 200000;