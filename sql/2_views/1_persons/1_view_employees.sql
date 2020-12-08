CREATE VIEW view_employees AS
SELECT
    p.id AS id,
    p.first_name AS first_name,
    p.last_name AS last_name,
    er.name AS role_name
FROM person p
INNER JOIN employee e on p.id = e.id
INNER JOIN employee_role er on e.role_id = er.id