CREATE PROCEDURE InsertAndGetAddress
    @StreetName NVARCHAR(255),
    @StreetNumber INT,
    @ZipCode INT,
    @CityName NVARCHAR(255),
    @AddressId INT OUT
AS
    BEGIN TRANSACTION;
        DECLARE @CityId INT = (SELECT id FROM city WHERE name = @CityName)
        IF @CityId IS NULL
        BEGIN
            INSERT INTO city(name)
            VALUES (@CityName);
            SET @CityId = @@IDENTITY
        END;

        DECLARE @CityZipId INT = (SELECT id FROM city_zip WHERE zip_code = @ZipCode AND city_id = @CityId)
        IF @CityZipId IS NULL
        BEGIN
            INSERT INTO city_zip(zip_code, city_id)
            VALUES (@ZipCode, @CityId);
            SET @CityZipId = @@IDENTITY
        END;

        DECLARE @StreetId INT = (SELECT id FROM street WHERE name = @StreetName)
        IF @StreetId IS NULL
        BEGIN
            INSERT INTO street(name)
            VALUES (@StreetName);
            SET @StreetId = @@IDENTITY
        END;

        DECLARE @StreetNumberId INT = (SELECT id FROM street_number WHERE street_number = @StreetNumber
                                                                      AND street_id = @StreetId)
        IF @StreetNumberId IS NULL
        BEGIN
            INSERT INTO street_number(street_id, street_number)
            VALUES (@StreetId, @StreetNumber);
            SET @StreetNumberId = @@IDENTITY
        END;

        SET @AddressId = (SELECT id FROM address WHERE street_number_id = @StreetNumberId AND city_zip_id = @CityZipId)
        IF @AddressId IS NULL
        BEGIN
            INSERT INTO address(street_number_id, city_zip_id)
            VALUES (@StreetNumberId, @CityZipId);
            SET @AddressId = @@IDENTITY
        END;
    COMMIT TRANSACTION;
;