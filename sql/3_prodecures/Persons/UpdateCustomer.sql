CREATE PROCEDURE UpdateCustomer
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Email NVARCHAR(320),
    @PhoneNo NVARCHAR(50),
    @ZipCode INT,
    @City NVARCHAR(255),
    @StreetName NVARCHAR(255),
    @StreetNumber INT,
    @PersonId INT
AS
    EXEC InsertIgnoreAddress @StreetName, @ZipCode, @City;
    EXEC UpdatePerson @FirstName, @LastName,@PersonId;
    UPDATE customer
    SET email = @Email,
        phone_number = @PhoneNo,
        street = @StreetName,
        zip_code = @ZipCode,
        street_number = @StreetNumber
    WHERE id = @PersonId
;