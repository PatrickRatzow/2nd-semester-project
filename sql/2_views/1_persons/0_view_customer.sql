CREATE VIEW view_customer AS
SELECT
    p.id AS id,
    p.first_name AS first_name,
    p.last_name AS last_name,
    c.email AS email,
    c.phone_number AS phone_number,
    a.street_name AS street_name,
    a.street_number AS street_number,
    a.zip_code AS zip_code,
    c2.name AS city
FROM person p
INNER JOIN customer c ON c.id = p.id
INNER JOIN address a ON c.street_name = a.street_name AND c.zip_code = a.zip_code AND c.street_number = a.street_number
INNER JOIN city c2 ON a.zip_code = c2.zip_code