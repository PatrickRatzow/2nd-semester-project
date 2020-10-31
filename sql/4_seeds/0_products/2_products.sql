DECLARE @MurstenId INT = (SELECT id FROM products_categories WHERE name = 'Mursten');
DECLARE @TagstenId INT = (SELECT id FROM products_categories WHERE name = 'Tagsten');
DECLARE @BygmaId INT = (SELECT id FROM suppliers WHERE name = 'Bygma');
DECLARE @XLBygId INT = (SELECT id FROM suppliers WHERE name = 'XL Byg');

EXEC InsertProduct 'Lille sten', '', @MurstenId, @BygmaId, 100000;
EXEC InsertProduct 'Sten', '', @MurstenId, @BygmaId, 150000;
EXEC InsertProduct 'Stor sten', '', @MurstenId, @BygmaId, 200000;
EXEC InsertProduct 'Lille tagsten', '', @TagstenId, @BygmaId, 20000;
EXEC InsertProduct 'Tagsten', '', @TagstenId, @BygmaId, 250000;
EXEC InsertProduct 'Stor tagsten', '', @TagstenId, @XLBygId, 300000;