CREATE PROCEDURE InsertEmployee
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Position NVARCHAR(255),
    @PersonId INT = NULL OUTPUT
AS
    EXEC InsertPerson @FirstName, @LastName,@PersonId OUT;
    IF NOT EXISTS (SELECT * FROM employee_position WHERE name = @Position)
        BEGIN
            INSERT INTO employee_position(name)
            VALUES (@Position)
        END
    ;
    INSERT INTO employee(id, position_id)
    VALUES (@PersonId, (SELECT id FROM employee_position WHERE name = @Position))
;