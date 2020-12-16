package test.unit.util.validation.rules;

import org.junit.jupiter.api.Test;
import util.validation.rules.EmptyValidationRule;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmptyValidationRuleTest {
	@Test
	void testSucceedsWhenStringIsntEmpty() throws Exception {
		// Arrange
		String input = "test";
		EmptyValidationRule rule = new EmptyValidationRule(input);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsWhenStringIsEmpty() {
		// Arrange
		String input = "";
		EmptyValidationRule rule = new EmptyValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
}
