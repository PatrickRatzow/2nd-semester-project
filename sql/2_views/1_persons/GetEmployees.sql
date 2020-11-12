CREATE VIEW GetEmployees AS
SELECT
    p.id AS personId,
    p.firstName as personFirstName,
    p.lastName as personLastName,
    p.email as personEmail,
    p.phoneNo as personPhoneNo,
    e.username as employeeUsername,
    e.password as employeePassword
FROM persons p
RIGHT JOIN employees e ON p.id = e.id
