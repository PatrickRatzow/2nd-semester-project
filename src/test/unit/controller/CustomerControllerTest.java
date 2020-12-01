package test.unit.controller;

import controller.CustomerController;
import exception.DataAccessException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;

    @Test
    void testPassesValidationCheckIfValidData() throws DataAccessException {
        /*
        // Arrange
        String firstName = "Allan";
        String lastName = "Jensen";
        String email = "email@email.xd";
        String phoneNumber = "45454545";
        String city = "Aalborg SV";
        String streetName = "Sofiendalsvej";
        int streetNumber = 60;
        int zipCode = 9200;
        CustomerDaoMsSql daoMock = mock(CustomerDaoMsSql.class);
        Customer customer = new Customer(firstName, lastName, email, phoneNumber,
                new Address(streetName, streetNumber, city, zipCode));
        when(daoFactory.createCustomerDao(connection)).thenReturn(daoMock);
        when(daoMock.create(customer)).thenReturn(customer);

        // Act
        customerController.setCustomerInformation(firstName, lastName, email, phoneNumber, city, streetName,
                streetNumber, zipCode);
        customerController.create();

        // Will throw if it failed
         */
    }
}
