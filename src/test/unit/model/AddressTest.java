package test.unit.model;

import model.Address;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {
    private Address address;

    @Test
    public void testSucceedsValidationWhenAddressIsValid() throws Exception {
        // Arrange
        address = new Address("Sofiendalsvej", 6, "Aalborg SV", 9200);

        // Assert
        address.validate();
    }

    @Test
    public void testFailsValidationWhenStreetNameIsEmpty() {
        // Arrange
        address = new Address("", 6, "Aalborg SV", 9200);

        // Assert
        assertThrows(Exception.class, () -> address.validate());
    }

    @Test
    public void testFailsValidationWhenStreetNumberIsNegative() {
        // Arrange
        address = new Address("Sofiendalsvej", -1, "Aalborg SV", 9200);

        // Assert
        assertThrows(Exception.class, () -> address.validate());
    }

    @Test
    public void testFailsValidationWhenCityIsEmpty() {
        // Arrange
        address = new Address("Sofiendalsvej", 60, "", 9200);

        // Assert
        assertThrows(Exception.class, () -> address.validate());
    }

    @Test
    public void testSucceedsValidationWhenZipcodeIs0() throws Exception {
        // Arrange
        address = new Address("Sofiendalsvej", 60, "Aalborg SV", 0);

        // Assert
        address.validate();
    }

    @Test
    public void testFailsValidationWhenZipCodeIsNegative() {
        // Arrange
        address = new Address("Sofiendalsvej", 60, "Aalborg SV", -1);

        // Assert
        assertThrows(Exception.class, () -> address.validate());
    }

    @Test
    public void testSucceedsValidationWhenZipcodeIs9999() throws Exception {
        // Arrange
        address = new Address("Sofiendalsvej", 60, "Aalborg SV", 9999);

        // Assert
        address.validate();
    }

    @Test
    public void testFailsValidationWhenZipCodeIs10000() {
        // Arrange
        address = new Address("Sofiendalsvej", 60, "Aalborg SV", 10000);

        // Assert
        assertThrows(Exception.class, () -> address.validate());
    }
}
