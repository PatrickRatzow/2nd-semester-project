package test.unit.util.validation.rules;

import org.junit.jupiter.api.Test;
import util.validation.rules.ZipCodeValidationRule;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ZipCodeValidationRuleTest {
	@Test
	void testSucceedsIfZipCodeIs0() throws Exception {
		// Arrange
		int input = 0;
		ZipCodeValidationRule rule = new ZipCodeValidationRule(input);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsIfZipCodeIsMinus1() {
		// Arrange
		int input = -1;
		ZipCodeValidationRule rule = new ZipCodeValidationRule(input);
		
		// Act
		assertThrows(Exception.class, rule::validate);
	}
	
	@Test
	void testSucceedsIfZipCodeIs9999() throws Exception {
		// Arrange
		int input = 9999;
		ZipCodeValidationRule rule = new ZipCodeValidationRule(input);
		
		// Act
		rule.validate();
	}
	
	@Test
	void testFailsIfZipCodeIs10000() {
		// Arrange
		int input = 10000;
		ZipCodeValidationRule rule = new ZipCodeValidationRule(input);
		
		// Act
		assertThrows(Exception.class, rule::validate);
	}
}
