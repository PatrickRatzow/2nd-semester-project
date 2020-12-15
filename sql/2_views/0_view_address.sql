CREATE VIEW view_address AS
SELECT
    a.street_name AS street_name,
    a.street_number AS street_number,
    a.zip_code AS zip_code,
    c.name AS city
FROM address a
INNER JOIN city c on a.zip_code = c.zip_code

/*SELECT
    s.street_name AS street_name,
    s.zip_code AS zip_code,
    c.name AS city_name
FROM street s
INNER JOIN city c on c.zip_code = s.zip_code*/