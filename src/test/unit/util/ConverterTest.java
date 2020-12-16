package test.unit.util;

import org.junit.jupiter.api.Test;
import util.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
	@Test
	void testReturnsValueIfCanParseInt() {
		// Arrange
		String input = "50";
		int output;
		
		// Act
		output = Converter.tryParse(input);
		
		// Assert
		assertEquals(output, 50);
	}
	
	@Test
	void testReturnsMinus1IfUnableToParseInt() {
		// Arrange
		String input = "10acd2";
		int output;
		
		// Act
		output = Converter.tryParse(input);
		
		// Assert
		assertEquals(output, -1);
	}
}
