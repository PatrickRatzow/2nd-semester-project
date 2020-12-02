CREATE VIEW GetOrders AS
SELECT
    o.id AS id,
    o.delivered AS delivered,
    o.created_at AS created_at,
    o.employee_id AS employee_id,
    o.project_id AS project_id,
    c.id AS customer_id
FROM [order] o
LEFT JOIN project p on o.project_id = p.id
LEFT JOIN customer c on p.customer_id = c.id
