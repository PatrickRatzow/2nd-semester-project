CREATE PROCEDURE InsertEmployee
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Role NVARCHAR(255),
    @PersonId INT = NULL OUTPUT
AS
    IF NOT EXISTS (SELECT * FROM employee_role WHERE name = @Role)
        BEGIN
            INSERT INTO employee_role(name)
            VALUES (@Role)
        END
    ;
    INSERT INTO employee(first_name, last_name, role_id)
    VALUES (@FirstName, @LastName, (SELECT id FROM employee_role WHERE name = @Role));
    SET @PersonId = @@IDENTITY
;