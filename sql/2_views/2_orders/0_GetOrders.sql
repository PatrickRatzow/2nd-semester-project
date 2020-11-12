CREATE VIEW GetOrders AS
SELECT
    o.id AS orderId,
    o.status AS orderStatus,
    o.createdDate AS orderCreatedDate,
    o.projectId AS projectId,
    o.employeeId AS employeeId,
    o.customerId AS customerId
FROM orders o