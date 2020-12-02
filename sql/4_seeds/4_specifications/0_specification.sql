INSERT INTO specification(id)
VALUES ('window'),
       ('roof');

INSERT INTO specification_to_product_category(specification_id, product_category_id)
VALUES ('window', 1),
       ('window', 2),
       ('window', 3),
       ('roof', 2);