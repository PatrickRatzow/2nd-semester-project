package test.unit.util.validation.rules;

import org.junit.jupiter.api.Test;
import util.validation.rules.EmailValidationRule;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailValidationRuleTest {
	@Test
	void testSucceedsWithLocalPartAtDomainPart() throws Exception {
		// Arrange
		String input = "test@email.com";
		EmailValidationRule rule = new EmailValidationRule(input);

		// Act
		rule.validate();
	}
	
	@Test
	void testFailsWithoutAt() {
		// Arrange
		String input = "testemail.com";
		EmailValidationRule rule = new EmailValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
	
	@Test
	void testFailsWithoutValidDomainPart() {
		// Arrange
		String input = "test@email";
		EmailValidationRule rule = new EmailValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
	
	@Test
	void testFailsWithoutLocalPart() {
		// Arrange
		String input = "@email.com";
		EmailValidationRule rule = new EmailValidationRule(input);
		
		// Assert + Act
		assertThrows(Exception.class, rule::validate);
	}
}
