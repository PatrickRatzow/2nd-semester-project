CREATE PROCEDURE UpdatePerson
    @PersonId INT,
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Email NVARCHAR(320),
    @PhoneNo NVARCHAR(50)
AS
    UPDATE persons
    SET firstName = @FirstName,
        lastName = @LastName,
        email = @Email,
        phoneNo = @PhoneNo
    WHERE id = @PersonId
;

