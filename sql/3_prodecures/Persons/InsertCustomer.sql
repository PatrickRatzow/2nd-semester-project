CREATE PROCEDURE InsertCustomer
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Email NVARCHAR(320),
    @PhoneNo NVARCHAR(50),
    @PersonId INT = NULL OUTPUT
AS
    EXEC InsertPerson @FirstName, @LastName, @Email, @PhoneNo, @PersonId OUT;
    INSERT INTO customers(id)
    VALUES (@PersonId)
;

