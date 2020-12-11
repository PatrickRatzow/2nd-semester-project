package test.integration.model;

import model.requirements.RequirementWidth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequirementWidthTest {
    private RequirementWidth width;

    @BeforeEach
    void setup() {
        width = new RequirementWidth();
    }

    @Test
    void testCanValidateHeight() throws Exception {
        //Arrange
        int input = 150;

        //Act
        width.setValue(input);
        width.validate();
    }

    @Test
    void testCantValidateInvalidHeight() {
        //Arrange
        int input = -1;

        //Act
        width.setValue(input);

        //Assert
        assertThrows(Exception.class, () -> width.validate());
    }
}
