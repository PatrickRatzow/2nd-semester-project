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
    EXEC InsertIgnoreAddress @StreetName, @StreetNumber, @ZipCode, @City;
    INSERT INTO customer(first_name, last_name, email, phone_number, street_name, zip_code, street_number)
    VALUES (@FirstName, @LastName, @Email, @PhoneNo, @StreetName, @ZipCode, @StreetNumber);
    SET @PersonId = @@IDENTITY
;