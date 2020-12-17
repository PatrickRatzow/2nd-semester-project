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
    EXEC InsertIgnoreAddress @StreetName, @StreetNumber,@ZipCode, @City;
    UPDATE customer
    SET first_name = @FirstName,
        last_name = @LastName,
        email = @Email,
        phone_number = @PhoneNo,
        street_name = @StreetName,
        zip_code = @ZipCode,
        street_number = @StreetNumber
    WHERE id = @PersonId
;