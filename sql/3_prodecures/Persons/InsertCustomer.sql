CREATE PROCEDURE InsertCustomer
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Email NVARCHAR(320),
    @PhoneNo NVARCHAR(50),
    @PersonId INT = NULL OUTPUT
AS
    EXEC InsertPerson @FirstName, @LastName, @PersonId OUT;
    INSERT INTO customer(id, email, phone_number)
    VALUES (@PersonId, @Email, @PhoneNo);
;