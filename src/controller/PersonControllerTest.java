package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class PersonControllerTest {
    private PersonController personController = new PersonController();

    @BeforeEach
    void setup() {
    }


    @Test
    @DisplayName("Can find  customer in the database by name")
    void testFindCustomerByName_shouldReturnCustomerFromDatabase() {
        fail();
    }


    @Test
    @DisplayName("Cannot find non-existent customer in database by name")
    void testCantFindCustomerByName_shouldReturnNullValueFromDatabase() {
        fail();
    }


    @Test
    @DisplayName("Can add a person to the database")
    void testAddPersonToDatabase() {
        fail();
    }


    @Test
    @DisplayName("Can delete a person from the database")
    void testDeletePersonFromDatabase() {
        fail();
    }


    @Test
    @DisplayName("Can find an employee from the database by username and password")
    void testFindEmployeeByUsernameAndPassword() {
        fail();
    }


}
