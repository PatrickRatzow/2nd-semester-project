CREATE TABLE suppliers (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE products_categories (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL UNIQUE,
    description NVARCHAR(MAX) NOT NULL,
    parentCategoryId INT,
    PRIMARY KEY(id),
    FOREIGN KEY(parentCategoryId) REFERENCES products_categories
);

CREATE TABLE products (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX) NOT NULL,
    categoryId INT NOT NULL,
    supplierId INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(categoryId) REFERENCES products_categories(id),
    FOREIGN KEY(supplierId) REFERENCES suppliers(id)
);

CREATE TABLE products_prices (
    productId INT,
    startTime DATETIME DEFAULT GETUTCDATE(),
    endTime DATETIME DEFAULT DATEFROMPARTS(9999, 12, 30),
    price INT NOT NULL,
    PRIMARY KEY(productId, startTime),
    FOREIGN KEY(productId) REFERENCES products(id)
);

CREATE TABLE persons (
    id INT IDENTITY(1, 1),
    firstName NVARCHAR(127) NOT NULL,
    lastName NVARCHAR(127) NOT NULL,
    email NVARCHAR(320) NOT NULL,
    phoneNo NVARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE employees (
    id INT,
    username NVARCHAR(63) NOT NULL UNIQUE,
    password BINARY(80) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id) REFERENCES persons(id)
);

CREATE TABLE customers (
    id INT,
    PRIMARY KEY(id),
    FOREIGN KEY(id) REFERENCES persons(id)
);