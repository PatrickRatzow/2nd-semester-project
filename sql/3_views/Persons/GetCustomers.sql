CREATE VIEW GetCustomers AS
SELECT
    p.id AS personId,
    p.firstName as personFirstName,
    p.lastName as personLastName,
    p.email as personEmail,
    p.phoneNo as personPhoneNo
FROM persons p
RIGHT JOIN customers c ON c.id = p.id
