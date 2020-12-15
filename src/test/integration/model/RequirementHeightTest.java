package test.integration.model;

import model.requirements.RequirementHeight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequirementHeightTest {
    private RequirementHeight height;

    @BeforeEach
    void setup() {
        height = new RequirementHeight();
    }

    @Test
    void testCanValidateHeight() throws Exception {
        // Arrange
        int input = 150;

        // Act
        height.setValue(input);
        
        // Assert
        height.validate();
    }

    @Test
    void testCantValidateInvalidHeight() {
        // Arrange
        int input = -1;

        // Act
        height.setValue(input);

        // Assert
        assertThrows(Exception.class, () -> height.validate());
    }

    @Test
    void testCantValidateEmptyHeight() {
        // Act
        height.setValue(null);

        // Assert
        assertThrows(Exception.class, () -> height.validate());
    }
}
