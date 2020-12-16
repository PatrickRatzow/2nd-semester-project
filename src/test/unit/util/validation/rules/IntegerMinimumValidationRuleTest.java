package test.unit.util.validation.rules;

import org.junit.jupiter.api.Test;
import util.validation.rules.IntegerMinimumValidationRule;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerMinimumValidationRuleTest {
	@Test
	void testSucceedsWhenValueIsAtMinimumLimit() throws Exception {
		// Arrange
		int minValue = 10;
		int input = minValue;
		IntegerMinimumValidationRule rule = new IntegerMinimumValidationRule(input, minValue);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsWhenValueIsBelowMinimumLimit() {
		// Arrange
		int minValue = 10;
		int input = minValue - 1;
		IntegerMinimumValidationRule rule = new IntegerMinimumValidationRule(input, minValue);
		
		// Act
		assertThrows(Exception.class, rule::validate);
	}
}
