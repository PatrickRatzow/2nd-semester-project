CREATE PROCEDURE InsertIgnoreAddress
    @StreetName NVARCHAR(255),
    @StreetNumber INT,
    @ZipCode INT,
    @CityName NVARCHAR(255)
AS
    IF NOT EXISTS (
        SELECT *
        FROM street s
        INNER JOIN city c ON s.zip_code = c.zip_code
        WHERE s.street_name = @StreetName
          AND s.zip_code = @ZipCode
    )
    BEGIN
        INSERT INTO city(zip_code, name)
        VALUES (@ZipCode, @CityName);
        INSERT INTO street(street_name, zip_code)
        VALUES (@StreetName, @ZipCode);
    END;
    IF NOT EXISTS (SELECT * FROM view_address WHERE street_name = @StreetName AND zip_code = @ZipCode
                                           AND street_number = @StreetNumber)
    BEGIN
        INSERT INTO address(street_name, zip_code, street_number)
        VALUES (@StreetName, @ZipCode, @StreetNumber)
    END
;