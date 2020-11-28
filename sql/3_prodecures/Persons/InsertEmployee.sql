CREATE PROCEDURE InsertEmployee
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @PersonId INT = NULL OUTPUT
AS
    EXEC InsertPerson @FirstName, @LastName, @PersonId OUT;
    INSERT INTO employee(id)
    VALUES (@PersonId)
;