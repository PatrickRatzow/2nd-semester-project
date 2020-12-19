package test.unit.util.validation.rules;

import org.junit.jupiter.api.Test;
import util.validation.rules.StringNotEmptyValidationRule;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringNotEmptyValidationRuleTest {
	@Test
	void testSucceedsWhenStringIsntEmpty() throws Exception {
		// Arrange
		String input = "test";
		StringNotEmptyValidationRule rule = new StringNotEmptyValidationRule(input);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsWhenStringIsEmpty() {
		// Arrange
		String input = "";
		StringNotEmptyValidationRule rule = new StringNotEmptyValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
}
