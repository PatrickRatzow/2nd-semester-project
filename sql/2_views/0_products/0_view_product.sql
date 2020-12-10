CREATE VIEW view_product AS
SELECT
    p.id AS id,
    p.description AS description,
    p.name AS name,
    pp.price AS price,
    pp.start_time AS price_start_time,
    pp.end_time AS price_end_time,
    pf.field_id AS field_id,
    pf.value AS field_value
FROM product p
INNER JOIN product_price pp on p.id = pp.product_id
LEFT JOIN product_field pf on p.id = pf.product_id
