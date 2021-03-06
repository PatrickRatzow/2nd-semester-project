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
		
		// Assert
		assertThrows(Exception.class, rule::validate);
	}
	
	@Test
	void testFailsIfPhoneNumberContainsNonNumbers() {
		// Arrange
		String input = "99test99";
		PhoneValidationRule rule = new PhoneValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
	
	@Test
	void testFailsIfNegativeNumbers() {
		// Arrange
		String input = "-9999999";
		PhoneValidationRule rule = new PhoneValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
	
	@Test
	void testSucceedsIfContainsAValidPhoneNumberWithSpaces() throws Exception {
		// Arrange
		String input = "45 45 45 45";
		PhoneValidationRule rule = new PhoneValidationRule(input);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsIfContainsAnInvalidPhoneNumberWithSpaces() {
		// Arrange
		String input = "232323 233";
		PhoneValidationRule rule = new PhoneValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
}
