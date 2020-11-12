CREATE PROCEDURE InsertPerson
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Email NVARCHAR(320),
    @PhoneNo NVARCHAR(50),
    @PersonId INT = NULL OUTPUT
AS
    INSERT INTO persons(firstName, lastName, email, phoneNo)
    VALUES (@FirstName, @LastName, @Email, @PhoneNo);
    SET @PersonId = @@IDENTITY;
;

