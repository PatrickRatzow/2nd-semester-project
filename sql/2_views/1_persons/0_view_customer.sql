CREATE VIEW view_customer AS
SELECT
    c.id AS id,
    c.first_name AS first_name,
    c.last_name AS last_name,
    c.email AS email,
    c.phone_number AS phone_number,
    s.name AS street_name,
    sn.street_number AS street_number,
    cz.zip_code AS zip_code,
    c2.name AS city
FROM customer c
INNER JOIN address a on a.id = c.address_id
INNER JOIN city_zip cz on a.city_zip_id = cz.id
INNER JOIN city c2 on cz.city_id = c2.id
INNER JOIN street_number sn on a.street_number_id = sn.id
INNER JOIN street s on sn.street_id = s.id