CREATE PROCEDURE UpdateEmployee
    @PersonId INT,
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Email NVARCHAR(320),
    @PhoneNo NVARCHAR(50),
    @Username NVARCHAR(63),
    @Password BINARY(48)
AS
    EXEC UpdatePerson @PersonId, @FirstName, @LastName, @Email, @PhoneNo;
    UPDATE employees
    SET username = @Username,
        password = @Password
    WHERE id = @PersonId;
;

