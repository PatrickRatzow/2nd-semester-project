package test.unit.util.validation.rules;

import org.junit.jupiter.api.Test;
import util.validation.rules.NotNullValidationRule;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotNullValidationRuleTest {
	@Test
	void testSucceedsIfNotNull() throws Exception {
		// Arrange
		String input = "test";
		NotNullValidationRule<String> rule = new NotNullValidationRule<>(input);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsIfNull() {
		// Arrange
		String input = null;
		NotNullValidationRule<String> rule = new NotNullValidationRule<>(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
}
