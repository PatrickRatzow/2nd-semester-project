package test.unit.util.validation.rules;

import org.junit.jupiter.api.Test;
import util.validation.rules.IntegerRangeValidationRule;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerRangeValidationRuleTest {
	@Test
	void testSucceedsWhenValueIsAtMinimumLimit() throws Exception {
		// Arrange
		int minValue = 10;
		int maxValue = 100;
		int input = minValue;
		IntegerRangeValidationRule rule = new IntegerRangeValidationRule(input, minValue, maxValue);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsWhenValueIsBelowMinimumLimit() {
		// Arrange
		int minValue = 10;
		int maxValue = 100;
		int input = minValue - 1;
		IntegerRangeValidationRule rule = new IntegerRangeValidationRule(input, minValue, maxValue);
		
		// Act
		assertThrows(Exception.class, rule::validate);
	}
	
	@Test
	void testSucceedsWhenValueIsAtMaximumLimit() throws Exception {
		// Arrange
		int minValue = 10;
		int maxValue = 100;
		int input = maxValue;
		IntegerRangeValidationRule rule = new IntegerRangeValidationRule(input, minValue, maxValue);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsWhenValueIsAboveMaximumLimit() {
		// Arrange
		int minValue = 10;
		int maxValue = 100;
		int input = maxValue + 1;
		IntegerRangeValidationRule rule = new IntegerRangeValidationRule(input, minValue, maxValue);
		
		// Act
		assertThrows(Exception.class, rule::validate);
	}
}
