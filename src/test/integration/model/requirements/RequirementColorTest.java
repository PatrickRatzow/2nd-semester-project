package test.integration.model.requirements;

import model.requirements.RequirementColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequirementColorTest {
	private RequirementColor color;
	
	@BeforeEach
	void setup() {
		color = new RequirementColor();
	}
	
	@Test
	void testCanValidateColor() throws Exception {
		// Arrange
		String input = "Gul";

		// Act
		color.setValue(input);
		
		// Assert
		color.validate();
	}
	
	
	@Test
	void testCantValidateInvalidColor() {
		// Arrange
		String input = "";

		// Act
		color.setValue(input);

		// Assert
		assertThrows(Exception.class, () -> color.validate());
	}

	@Test
	void testCantValidateEmptyColor() {
		// Act
		color.setValue(null);

		// Assert
		assertThrows(Exception.class, () -> color.validate());
	}
}
