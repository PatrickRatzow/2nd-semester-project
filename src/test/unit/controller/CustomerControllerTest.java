package test.unit.controller;

import controller.CustomerController;
import entity.Customer;
import exception.DataAccessException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;

    @Test
    void testCanFindByPhoneNumberIfExists() throws DataAccessException {
        // Arrange
        String phoneNumber = "25252525";
        Customer customer;

        // Act
        customer = customerController.findByPhoneNumber(phoneNumber);

        // Assert
        assertNotNull(customer);
    }
}
