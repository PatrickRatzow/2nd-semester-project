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
    void testCanValidateWidth() throws Exception {
        // Arrange
        int input = 150;

        // Act
        width.setValue(input);
        
        // Assert
        width.validate();
    }

    @Test
    void testCantValidateInvalidWidth() {
        // Arrange
        int input = -1;

        // Act
        width.setValue(input);

        // Assert
        assertThrows(Exception.class, () -> width.validate());
    }

    @Test
    void testCantValidateEmptyWidth() {
        // Act
        width.setValue(null);

        // Assert
        assertThrows(Exception.class, () -> width.validate());
    }
}
