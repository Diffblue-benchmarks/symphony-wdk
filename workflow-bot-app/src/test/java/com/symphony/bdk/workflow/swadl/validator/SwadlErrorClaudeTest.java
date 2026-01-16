package com.symphony.bdk.workflow.swadl.validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwadlErrorClaudeTest {

  @Test
  void testToString_withPositiveLineNumber() {
    // Test that toString formats correctly when lineNumber is positive
    SwadlError error = new SwadlError(10, "Invalid syntax");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 10: Invalid syntax");
  }

  @Test
  void testToString_withZeroLineNumber() {
    // Test that toString formats correctly when lineNumber is zero
    SwadlError error = new SwadlError(0, "Validation error");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 0: Validation error");
  }

  @Test
  void testToString_withNegativeLineNumber() {
    // Test that toString returns only message when lineNumber is negative
    SwadlError error = new SwadlError(-1, "General error");

    String result = error.toString();

    assertThat(result).isEqualTo("General error");
  }

  @Test
  void testToString_withNegativeLineNumber_largeValue() {
    // Test that toString returns only message when lineNumber is a large negative value
    SwadlError error = new SwadlError(-999, "Critical error");

    String result = error.toString();

    assertThat(result).isEqualTo("Critical error");
  }

  @Test
  void testToString_withLargePositiveLineNumber() {
    // Test that toString handles large positive line numbers
    SwadlError error = new SwadlError(100000, "Error at large line number");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 100000: Error at large line number");
  }

  @Test
  void testToString_withEmptyMessage() {
    // Test that toString handles empty message
    SwadlError error = new SwadlError(5, "");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 5: ");
  }

  @Test
  void testToString_withEmptyMessage_negativeLineNumber() {
    // Test that toString handles empty message with negative line number
    SwadlError error = new SwadlError(-1, "");

    String result = error.toString();

    assertThat(result).isEqualTo("");
  }

  @Test
  void testToString_withNullMessage() {
    // Test that toString handles null message
    SwadlError error = new SwadlError(10, null);

    String result = error.toString();

    assertThat(result).isEqualTo("Line 10: null");
  }

  @Test
  void testToString_withNullMessage_negativeLineNumber() {
    // Test that toString handles null message with negative line number
    SwadlError error = new SwadlError(-1, null);

    String result = error.toString();

    assertThat(result).isNull();
  }

  @Test
  void testToString_withMultilineMessage() {
    // Test that toString handles message with newlines
    SwadlError error = new SwadlError(15, "First line\nSecond line");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 15: First line\nSecond line");
  }

  @Test
  void testToString_withSpecialCharacters() {
    // Test that toString handles special characters in message
    SwadlError error = new SwadlError(20, "Error: @#$%^&*()");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 20: Error: @#$%^&*()");
  }

  @Test
  void testToString_withLongMessage() {
    // Test that toString handles long messages
    String longMessage = "This is a very long error message that contains many characters and continues for quite a while to test how the toString method handles long strings";
    SwadlError error = new SwadlError(100, longMessage);

    String result = error.toString();

    assertThat(result).isEqualTo("Line 100: " + longMessage);
  }

  @Test
  void testConstructor_createsObjectWithCorrectValues() {
    // Test that constructor properly initializes fields
    SwadlError error = new SwadlError(42, "Test message");

    assertThat(error.getLineNumber()).isEqualTo(42);
    assertThat(error.getMessage()).isEqualTo("Test message");
  }

  @Test
  void testEquals_withSameValues() {
    // Test that two SwadlError objects with same values are equal
    SwadlError error1 = new SwadlError(10, "Error message");
    SwadlError error2 = new SwadlError(10, "Error message");

    assertThat(error1).isEqualTo(error2);
  }

  @Test
  void testEquals_withDifferentLineNumbers() {
    // Test that two SwadlError objects with different line numbers are not equal
    SwadlError error1 = new SwadlError(10, "Error message");
    SwadlError error2 = new SwadlError(20, "Error message");

    assertThat(error1).isNotEqualTo(error2);
  }

  @Test
  void testEquals_withDifferentMessages() {
    // Test that two SwadlError objects with different messages are not equal
    SwadlError error1 = new SwadlError(10, "Error message 1");
    SwadlError error2 = new SwadlError(10, "Error message 2");

    assertThat(error1).isNotEqualTo(error2);
  }

  @Test
  void testHashCode_withSameValues() {
    // Test that two SwadlError objects with same values have same hash code
    SwadlError error1 = new SwadlError(10, "Error message");
    SwadlError error2 = new SwadlError(10, "Error message");

    assertThat(error1.hashCode()).isEqualTo(error2.hashCode());
  }

  @Test
  void testHashCode_withDifferentValues() {
    // Test that two SwadlError objects with different values have different hash codes
    SwadlError error1 = new SwadlError(10, "Error message 1");
    SwadlError error2 = new SwadlError(20, "Error message 2");

    // Note: Different values might have same hash code, but it's unlikely
    assertThat(error1.hashCode()).isNotEqualTo(error2.hashCode());
  }

  @Test
  void testToString_consistentResults() {
    // Test that calling toString multiple times returns the same result
    SwadlError error = new SwadlError(25, "Consistent error");

    String result1 = error.toString();
    String result2 = error.toString();
    String result3 = error.toString();

    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);
    assertThat(result1).isEqualTo("Line 25: Consistent error");
  }

  @Test
  void testToString_boundaryCase_lineNumberMinusOne() {
    // Test the boundary case where lineNumber is exactly -1 (just below 0)
    SwadlError error = new SwadlError(-1, "Boundary error");

    String result = error.toString();

    assertThat(result).isEqualTo("Boundary error");
  }

  @Test
  void testToString_boundaryCase_lineNumberZero() {
    // Test the boundary case where lineNumber is exactly 0 (at the threshold)
    SwadlError error = new SwadlError(0, "Zero line error");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 0: Zero line error");
  }

  @Test
  void testToString_withMessageContainingFormatSpecifiers() {
    // Test that toString handles messages with format specifiers
    SwadlError error = new SwadlError(30, "Error: %s %d %%");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 30: Error: %s %d %%");
  }

  @Test
  void testMultipleInstances_independent() {
    // Test that multiple instances maintain independent state
    SwadlError error1 = new SwadlError(1, "First error");
    SwadlError error2 = new SwadlError(2, "Second error");
    SwadlError error3 = new SwadlError(-1, "Third error");

    assertThat(error1.toString()).isEqualTo("Line 1: First error");
    assertThat(error2.toString()).isEqualTo("Line 2: Second error");
    assertThat(error3.toString()).isEqualTo("Third error");
  }
}
