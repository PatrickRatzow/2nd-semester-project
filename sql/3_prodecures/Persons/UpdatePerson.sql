CREATE PROCEDURE UpdatePerson
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @PersonId INT
AS
    UPDATE person
    SET first_name = @FirstName,
        last_name = @LastName
    WHERE id = @PersonId;
;