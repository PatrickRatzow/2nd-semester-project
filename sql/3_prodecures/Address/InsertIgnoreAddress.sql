CREATE PROCEDURE InsertIgnoreAddress
    @StreetName NVARCHAR(255),
    @ZipCode INT,
    @CityName NVARCHAR(255)
AS
    IF NOT EXISTS (SELECT * FROM GetAddress WHERE street_name = @StreetName AND zip_code = @ZipCode)
    BEGIN
        INSERT INTO city(zip_code, name)
        VALUES (@ZipCode, @CityName);
        INSERT INTO street(street, zip_code)
        VALUES (@StreetName, @ZipCode);
    END
;