CREATE PROCEDURE InsertEmployee
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Role NVARCHAR(255),
    @PersonId INT = NULL OUTPUT
AS
    EXEC InsertPerson @FirstName, @LastName,@PersonId OUT;
    IF NOT EXISTS (SELECT * FROM employee_role WHERE name = @Role)
        BEGIN
            INSERT INTO employee_role(name)
            VALUES (@Role)
        END
    ;
    INSERT INTO employee(id, role_id)
    VALUES (@PersonId, (SELECT id FROM employee_role WHERE name = @Role))
;