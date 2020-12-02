CREATE TABLE city (
    zip_code INT,
    name NVARCHAR(255),
    PRIMARY KEY(zip_code)
);

CREATE TABLE street (
    street NVARCHAR(255),
    zip_code INT,
    PRIMARY KEY(street, zip_code),
    FOREIGN KEY(zip_code) REFERENCES city
);

CREATE TABLE person (
    id INT IDENTITY(1, 1),
    first_name NVARCHAR(127) NOT NULL,
    last_name NVARCHAR(127) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE employee_position (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255),
    PRIMARY KEY(id)
)

CREATE TABLE employee (
    id INT,
    position_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id) REFERENCES person(id),
    FOREIGN KEY(position_id) REFERENCES employee_position
);

CREATE TABLE customer (
    id INT,
    email NVARCHAR(320) NOT NULL,
    phone_number NVARCHAR(50) NOT NULL,
    street NVARCHAR(255) NOT NULL,
    zip_code INT NOT NULL,
    street_number INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id) REFERENCES person(id),
    FOREIGN KEY(street, zip_code) REFERENCES street(street, zip_code)
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

CREATE TABLE supplier (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
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
    supplier_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(category_id) REFERENCES product_category(id),
    FOREIGN KEY(supplier_id) REFERENCES supplier(id)
);

CREATE TABLE product_price (
    product_id INT,
    start_time DATETIME DEFAULT GETUTCDATE(),
    end_time DATETIME DEFAULT DATEFROMPARTS(9999, 12, 30),
    price INT NOT NULL,
    PRIMARY KEY(product_id, start_time),
    FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE [order] (
    id INT IDENTITY(1, 1),
    status INT NOT NULL,
    created_at DATETIME2 NOT NULL,
    project_id INT NOT NULL,
    employee_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(project_id) REFERENCES project(id),
    FOREIGN KEY(employee_id) REFERENCES employee(id),
);

CREATE TABLE project_invoice (
    project_id INT NOT NULL,
    created_at DATETIME2 NOT NULL,
    due_date DATE NOT NULL,
    to_pay INT NOT NULL,
    has_paid INT NOT NULL,
    PRIMARY KEY(project_id),
    FOREIGN KEY(project_id) REFERENCES project(id)
);

CREATE TABLE order_line (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(order_id, product_id),
    FOREIGN KEY(order_id) REFERENCES [order](id),
    FOREIGN KEY(product_id) REFERENCES product(id)
);
