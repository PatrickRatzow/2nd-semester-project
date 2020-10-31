CREATE VIEW GetEmployees AS
SELECT
    p.id AS personId,
    p.firstName as personFirstName,
    p.lastName as personLastName,
    p.email as personEmail,
    p.phoneNo as personPhoneNo,
    e.username as employeeUsername,
    e.password as employeePassword,
    e.salt as employeeSalt
FROM persons p
LEFT JOIN employees e ON p.id = e.id
