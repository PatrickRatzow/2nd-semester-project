CREATE VIEW view_order AS
SELECT
    o.id AS id,
    o.delivered AS delivered,
    o.created_at AS created_at,
    o.project_id AS project_id
FROM [order] o
