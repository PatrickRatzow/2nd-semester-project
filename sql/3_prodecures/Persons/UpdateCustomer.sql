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
    DECLARE @AddressId INT;
    EXEC InsertAndGetAddress @StreetName, @StreetNumber,@ZipCode, @City, @AddressId OUT;
    UPDATE customer
    SET first_name = @FirstName,
        last_name = @LastName,
        email = @Email,
        phone_number = @PhoneNo,
        address_id = @AddressId
    WHERE id = @PersonId
;