DECLARE @TagstenId INT = (SELECT id FROM product_category WHERE name = 'Tagsten');
DECLARE @TeglstenId INT = (SELECT id FROM product_category WHERE name = 'Teglsten');

EXEC InsertProduct 'Lille tagsten', '', @TagstenId, 20000;
EXEC InsertProduct 'Tagsten', '', @TagstenId, 250000;
EXEC InsertProduct 'Stor tagsten', '', @TagstenId, 300000;
EXEC InsertProduct 'Lille teglsten', '', @TeglstenId, 100000;
EXEC InsertProduct 'Teglsten', '', @TeglstenId, 150000;
EXEC InsertProduct 'Stor teglsten', '', @TeglstenId, 200000;
EXEC UpdateProduct 1, 'Lille tagsten', '', @TagstenId, 25000;