CREATE VIEW view_address AS
SELECT
    s.name AS street_name,
    sn.street_number AS street_number,
    cz.zip_code AS zip_code,
    c.name AS city
FROM address a
INNER JOIN city_zip cz on a.city_zip_id = cz.id
INNER JOIN city c on cz.city_id = c.id
INNER JOIN street_number sn on a.street_number_id = sn.id
INNER JOIN street s on sn.street_number = s.id