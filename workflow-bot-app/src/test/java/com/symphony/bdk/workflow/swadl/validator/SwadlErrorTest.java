package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SwadlErrorTest {

  @Test
  @DisplayName("Test toString(); when lineNumber >= 0 then return formatted line message")
  void testToString_withNonNegativeLineNumber() {
    // Arrange
    SwadlError swadlError = new SwadlError(5, "Unexpected token");

    // Act
    String result = swadlError.toString();

    // Assert
    assertEquals("Line 5: Unexpected token", result);
  }

  @Test
  @DisplayName("Test toString(); when lineNumber < 0 then return message only")
  void testToString_withNegativeLineNumber() {
    // Arrange
    SwadlError swadlError = new SwadlError(-1, "Invalid workflow definition");

    // Act
    String result = swadlError.toString();

    // Assert
    assertEquals("Invalid workflow definition", result);
  }

  @Test
  @DisplayName("Test toString(); when lineNumber is 0 then return formatted line message")
  void testToString_withZeroLineNumber() {
    // Arrange
    SwadlError swadlError = new SwadlError(0, "Missing required field");

    // Act
    String result = swadlError.toString();

    // Assert
    assertEquals("Line 0: Missing required field", result);
  }
}
