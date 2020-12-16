package test.unit.util.validation.rules;

import org.junit.jupiter.api.Test;
import util.validation.rules.PhoneValidationRule;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhoneValidationRuleTest {
	@Test
	void testSucceedsIfPhoneNumberIs3Characters() throws Exception {
		// Arrange
		String input = "333";
		PhoneValidationRule rule = new PhoneValidationRule(input);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsIfPhoneNumberIs2Characters() {
		// Arrange
		String input = "22";
		PhoneValidationRule rule = new PhoneValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
	
	@Test
	void testSucceedsIfPhoneNumberIs8Characters() throws Exception {
		// Arrange
		String input = "88888888";
		PhoneValidationRule rule = new PhoneValidationRule(input);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsIfPhoneNumberIs9Characters() {
		// Arrange
		String input = "999999999";
		PhoneValidationRule rule = new PhoneValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
}
