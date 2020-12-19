CREATE TABLE city (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255),
    PRIMARY KEY(id)
);

CREATE TABLE city_zip (
    id INT IDENTITY(1, 1),
    zip_code INT NOT NULL,
    city_id INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (zip_code, city_id),
    FOREIGN KEY (city_id) REFERENCES city(id)
)

CREATE TABLE street (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE (name)
);

CREATE TABLE street_number (
    id INT IDENTITY(1, 1),
    street_id INT NOT NULL,
    street_number INT NOT NULL,
    PRIMARY KEY(id),
    UNIQUE (street_id, street_number),
    FOREIGN KEY (street_id) REFERENCES street(id)
);

CREATE TABLE address (
    id INT IDENTITY(1, 1),
    street_number_id INT NOT NULL,
    city_zip_id INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (street_number_id, city_zip_id),
    FOREIGN KEY (street_number_id) REFERENCES street_number(id),
    FOREIGN KEY (city_zip_id) REFERENCES city_zip(id)
);


CREATE TABLE employee_role (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255),
    PRIMARY KEY(id)
);

CREATE TABLE employee (
    id INT IDENTITY(1, 1),
    first_name NVARCHAR(127) NOT NULL,
    last_name NVARCHAR(127) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(role_id) REFERENCES employee_role
);

CREATE TABLE customer (
    id INT IDENTITY(1, 1),
    first_name NVARCHAR(127) NOT NULL,
    last_name NVARCHAR(127) NOT NULL,
    email NVARCHAR(320) NOT NULL,
    phone_number NVARCHAR(50) NOT NULL,
    address_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(address_id) REFERENCES address(id)
);

CREATE TABLE project (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    status INT NOT NULL,
    price INT NOT NULL,
    estimated_hours INT NOT NULL,
    customer_id INT NOT NULL,
    employee_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(customer_id) REFERENCES customer(id),
    FOREIGN KEY(employee_id) REFERENCES employee(id)
);

CREATE TABLE product_category (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE product (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX) NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(category_id) REFERENCES product_category(id)
);

CREATE TABLE product_field (
    product_id INT,
    field_id NVARCHAR(64),
    field_value NVARCHAR(MAX),
    PRIMARY KEY(product_id, field_id),
    FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE product_price (
    product_id INT,
    start_time DATETIME DEFAULT GETUTCDATE(),
    end_time DATETIME DEFAULT DATEFROMPARTS(9999, 12, 30),
    price INT NOT NULL,
    PRIMARY KEY(product_id, start_time, end_time),
    FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE [order] (
    project_id INT,
    delivered BIT NOT NULL,
    created_at DATETIME2 NOT NULL,
    PRIMARY KEY(project_id),
    FOREIGN KEY(project_id) REFERENCES project(id)
);

CREATE TABLE order_line (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    display_name NVARCHAR(255) NOT NULL,
    PRIMARY KEY(order_id, product_id),
    FOREIGN KEY(order_id) REFERENCES [order](project_id),
    FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE specification (
    id NVARCHAR(255),
    PRIMARY KEY(id)
);

CREATE TABLE specification_to_product_category (
    specification_id NVARCHAR(255),
    product_category_id INT,
    PRIMARY KEY(specification_id, product_category_id),
    FOREIGN KEY(specification_id) REFERENCES specification(id),
    FOREIGN KEY(product_category_id) REFERENCES product_category(id)
);