package test.integration.model;

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
		//Arrange
		String input = "Gul";

		//Act
		color.setValue(input);
		color.validate();
	}
	
	
	@Test
	void testCantValidateInvalidColor() {
		//Arrange
		String input = "";

		//Act
		color.setValue(input);

		//Assert
		assertThrows(Exception.class, () -> color.validate());
	}
}
