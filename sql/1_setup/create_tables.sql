CREATE TABLE suppliers (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE products_categories (
    id INT IDENTITY(1, 1),
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX) NOT NULL,
    PRIMARY KEY(id)
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