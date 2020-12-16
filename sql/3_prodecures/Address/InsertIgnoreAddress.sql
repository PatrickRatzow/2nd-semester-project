CREATE PROCEDURE InsertIgnoreAddress
    @StreetName NVARCHAR(255),
    @StreetNumber INT,
    @ZipCode INT,
    @CityName NVARCHAR(255)
AS
    BEGIN TRANSACTION;
        IF NOT EXISTS (SELECT * FROM city WHERE zip_code = @ZipCode)
        BEGIN
            INSERT INTO city(zip_code, name)
            VALUES (@ZipCode, @CityName);
        END;

        IF NOT EXISTS (SELECT * FROM street WHERE street_name = @StreetName AND zip_code = @ZipCode)
        BEGIN
            INSERT INTO street(street_name, zip_code)
            VALUES (@StreetName, @ZipCode);
        END;

        IF NOT EXISTS (SELECT * FROM address WHERE street_name = @StreetName AND zip_code = @ZipCode
                                               AND street_number = @StreetNumber)
        BEGIN
            INSERT INTO address(street_name, zip_code, street_number)
            VALUES (@StreetName, @ZipCode, @StreetNumber)
        END
    COMMIT TRANSACTION;
;