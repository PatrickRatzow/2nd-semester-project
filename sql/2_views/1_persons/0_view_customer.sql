CREATE VIEW view_customer AS
SELECT
    p.id AS id,
    p.first_name AS first_name,
    p.last_name AS last_name,
    c.email AS email,
    c.phone_number AS phone_number,
    s.street AS street_name,
    c.street_number AS street_number,
    c2.name AS city,
    c2.zip_code AS zip_code
FROM person p
INNER JOIN customer c ON c.id = p.id
INNER JOIN street s on c.street = s.street and c.zip_code = s.zip_code
INNER JOIN city c2 on s.zip_code = c2.zip_code
