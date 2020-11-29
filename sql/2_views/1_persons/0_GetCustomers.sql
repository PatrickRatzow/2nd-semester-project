CREATE VIEW GetCustomers AS
SELECT
    p.id AS id,
    p.first_name AS first_name,
    p.last_name AS last_name,
    c.email AS email,
    c.phone_number AS phone_number
FROM person p
RIGHT JOIN customer c ON c.id = p.id