CREATE VIEW GetProjects AS
SELECT
    p.id AS projectId,
    p.name as projectName,
    p.price as projectPrice,
    e.id AS employeeId,
    c.id as customerId,
    o.id AS orderId
FROM projects p
LEFT JOIN projects_to_employees pte on p.id = pte.projectId
LEFT JOIN employees e on pte.employeeId = e.id
LEFT JOIN projects_to_customers ptc on p.id = ptc.projectId
LEFT JOIN customers c on ptc.customerId = c.id
LEFT JOIN orders o on o.projectId = p.id
