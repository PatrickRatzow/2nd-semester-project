package test.unit.util.validation;

import org.junit.jupiter.api.Test;
import util.validation.ValidationRule;
import util.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidatorTest {
	@Test
	void testThrowsAnExceptionIfARuleFails() throws Exception {
		// Arrange
		Validator validator = new Validator();
		ValidationRule<String> rule = mock(ValidationRule.class);
		doThrow(Exception.class).when(rule).validate();
		
		// Act
		validator.addRule(rule);
		
		// Assert
		assertTrue(validator.hasErrors());
		assertEquals(validator.getErrors().size(), 1);
	}
	
	@Test
	void testHasNoErrorsIfRuleSucceeds() throws Exception {
		// Arrange
		Validator validator = new Validator();
		ValidationRule<String> rule = mock(ValidationRule.class);
		doNothing().when(rule).validate();
		
		// Act
		validator.addRule(rule);
		
		// Assert
		assertFalse(validator.hasErrors());
		assertEquals(validator.getErrors().size(), 0);
	}
	
	@Test
	void testCombinesMultipleExceptionsIntoOne() throws Exception {
		// Arrange
		Validator validator = new Validator();
		ValidationRule<String> rule = mock(ValidationRule.class);
		doThrow(Exception.class).when(rule).validate();
		
		// Act
		validator.addRule(rule);
		validator.addRule(rule);
		
		// Assert
		assertTrue(validator.hasErrors());
		assertEquals(validator.getErrors().size(), 2);
		assertNotNull(validator.getCompositeException());
	}
}
