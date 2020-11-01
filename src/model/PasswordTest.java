package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    private static final String INPUT = "test";
    private static final Password PASSWORD = new Password(INPUT);

    @Test
    @DisplayName("getBytes() returns a 80 byte long array")
    void getBytesHasDesiredLength() {
        // Arrange
        byte[] bytes;
        int desiredLength = 80;

        // Act
        bytes = PASSWORD.getBytes();

        // Arrange
        assertEquals(desiredLength, bytes.length);
    }

    @Test
    @DisplayName("equals() returns true if the input strings for both Password objects are the same")
    void equalsWorks() {
        // Arrange
        boolean equals;

        // Act
        equals = PASSWORD.equals(INPUT);

        // Assert
        assertTrue(equals);
    }

    @Test
    @DisplayName("equals() returns false if the input strings for either Password object does not match")
    void equalsDoesntWork() {
        // Arrange
        boolean equals;
        // Strip a single character
        String input = INPUT.substring(0, INPUT.length() - 1);

        // Act
        equals = PASSWORD.equals(input);

        // Assert
        assertFalse(equals);
    }

    @Test
    @DisplayName("compareTo() returns 1 if the salt is the same")
    void compareToWorksIfHashMatchesAndSaltsMatches() {
        // Arrange
        int comparison;
        int expectedComparison = 1;
        // Get first 16 bytes, which are the salt
        byte[] salt = Arrays.copyOfRange(PASSWORD.getBytes(), 0, 16);
        Password password = new Password(INPUT, salt);

        // Act
        comparison = PASSWORD.compareTo(password);

        // Assert
        assertEquals(comparison, expectedComparison);
    }

    @Test
    @DisplayName("compareTo() returns -1 if the salt is not the same")
    void compareToFailsIfHashMatchesAndSaltsDoesntMatch() {
        // Arrange
        int comparison;
        int expectedComparison = -1;
        Password password = new Password(INPUT);
        // Act
        comparison = PASSWORD.compareTo(password);

        // Assert
        assertEquals(comparison, expectedComparison);
    }

    @Test
    @DisplayName("compareTo() returns -1 if the salt is not the same")
    void compareToFailsIfHashesDontMatchEvenIfSaltsMatch() {
        // Arrange
        int comparison;
        int expectedComparison = -1;
        byte[] salt = Arrays.copyOfRange(PASSWORD.getBytes(), 0, 16);
        String input = INPUT.substring(0, INPUT.length() - 1);
        Password password = new Password(input, salt);

        // Act
        comparison = PASSWORD.compareTo(password);

        // Assert
        assertEquals(comparison, expectedComparison);
    }
}