CREATE PROCEDURE InsertEmployee
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Email NVARCHAR(320),
    @PhoneNo NVARCHAR(50),
    @Username NVARCHAR(63),
    @Password BINARY(32),
    @Salt BINARY(16),
    @PersonId INT = NULL OUTPUT
AS
    EXEC InsertPerson @FirstName, @LastName, @Email, @PhoneNo, @PersonId OUT;
    INSERT INTO employees(id, username, password, salt)
    VALUES (@PersonId, @Username, @Password, @Salt)
;