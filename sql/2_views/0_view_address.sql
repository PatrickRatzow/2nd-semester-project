CREATE VIEW view_address AS
SELECT
    s.street AS street_name,
    s.zip_code AS zip_code,
    c.name AS city_name
FROM street s
INNER JOIN city c on c.zip_code = s.zip_code