CREATE VIEW view_employee AS
SELECT
    e.id AS id,
    e.first_name AS first_name,
    e.last_name AS last_name,
    er.name AS role_name
FROM employee e
INNER JOIN employee_role er on e.role_id = er.id