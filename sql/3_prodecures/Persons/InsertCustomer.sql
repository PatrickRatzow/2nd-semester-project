CREATE PROCEDURE InsertCustomer
    @FirstName NVARCHAR(127),
    @LastName NVARCHAR(127),
    @Email NVARCHAR(320),
    @PhoneNo NVARCHAR(50),
    @ZipCode INT,
    @City NVARCHAR(255),
    @StreetName NVARCHAR(255),
    @StreetNumber INT,
    @PersonId INT = NULL OUTPUT
AS
    DECLARE @AddressId INT;
    EXEC InsertAndGetAddress @StreetName, @StreetNumber, @ZipCode, @City, @AddressId OUT;
    INSERT INTO customer(first_name, last_name, email, phone_number, address_id)
    VALUES (@FirstName, @LastName, @Email, @PhoneNo, @AddressId)
    SET @PersonId = @@IDENTITY
;