package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
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
    @DisplayName("compareTo() returns -1 if the hash is not the same")
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

    @Test
    @DisplayName("compareTo() should return -1 if two Password objects of the same input does not have a salt provided")
    // This is essentially checking if compareTo fails with different salts, but this is implicit instead of explicit
    void compareToFailsWithTwoPasswordObjectsWithoutSpecifiedSalts() {
        // Arrange
        String input = "hunter2";
        Password password1 = new Password(input);
        Password password2 = new Password(input);
        int comparison;
        int expectedComparison = -1;

        // Act
        comparison = password1.compareTo(password2);

        // Assert
        assertEquals(comparison, expectedComparison);
    }

    // FIXME: Somewhat questionable tests as we don't know the users hardware? Consult teacher?
    @Test
    @DisplayName("A new Password object should be constructed in under 350ms")
    void constructPasswordInUnder350ms() {
        Duration timeout = Duration.ofMillis(350);
        String input = "a beautiful test string";

        assertTimeout(timeout, () -> new Password(input));
    }

    @Test
    @DisplayName("A new Password object should be constructed in over 30ms")
    void constructPasswordInOver30ms() {
        // Arrange
        long startTime;
        long endTime;
        String input = "a beautiful test string";

        // Act
        startTime = System.nanoTime();
        new Password(input);
        endTime = System.nanoTime();

        // Assert
        double durationInMs = (endTime - startTime) / 1e6;
        assertTrue(durationInMs > 50);
    }
}