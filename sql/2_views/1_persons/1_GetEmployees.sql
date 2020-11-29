CREATE VIEW GetEmployees AS
SELECT
    p.id AS id,
    p.first_name AS first_name,
    p.last_name AS last_name
FROM person p
RIGHT JOIN employee e on p.id = e.id