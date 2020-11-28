CREATE PROCEDURE InsertPerson
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @PersonId INT = NULL OUTPUT
AS
    INSERT INTO person(first_name, last_name)
    VALUES (@FirstName, @LastName);
    SET @PersonId = @@IDENTITY;
;