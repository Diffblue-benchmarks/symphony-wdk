package com.symphony.bdk.workflow.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DuplicateExceptionClaudeTest {

  @Test
  void testConstructor_withMessage() {
    // Test that constructor properly initializes exception with message
    String message = "Duplicate workflow found";

    DuplicateException exception = new DuplicateException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withNullMessage() {
    // Test that constructor handles null message
    DuplicateException exception = new DuplicateException(null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isNull();
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyMessage() {
    // Test that constructor handles empty string message
    String message = "";

    DuplicateException exception = new DuplicateException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_isRuntimeException() {
    // Test that DuplicateException is a RuntimeException
    String message = "Duplicate entity detected";

    DuplicateException exception = new DuplicateException(message);

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    String message1 = "First duplicate";
    String message2 = "Second duplicate";

    DuplicateException exception1 = new DuplicateException(message1);
    DuplicateException exception2 = new DuplicateException(message2);

    assertThat(exception1).isNotNull();
    assertThat(exception2).isNotNull();
    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getMessage()).isEqualTo(message1);
    assertThat(exception2.getMessage()).isEqualTo(message2);
  }

  @Test
  void testConstructor_canBeThrown() {
    // Test that the exception can actually be thrown and caught
    String message = "Duplicate workflow ID found";

    try {
      throw new DuplicateException(message);
    } catch (DuplicateException e) {
      assertThat(e.getMessage()).isEqualTo(message);
      assertThat(e.getCause()).isNull();
    }
  }

  @Test
  void testConstructor_canBeCaughtAsRuntimeException() {
    // Test that the exception can be caught as RuntimeException
    String message = "Duplicate operation detected";

    try {
      throw new DuplicateException(message);
    } catch (RuntimeException e) {
      assertThat(e).isInstanceOf(DuplicateException.class);
      assertThat(e.getMessage()).isEqualTo(message);
    }
  }

  @Test
  void testConstructor_withLongMessage() {
    // Test that constructor handles long message strings
    String message = "Duplicate entity found: This is a very long message that describes "
        + "in detail what went wrong during the operation, including various context "
        + "information that may be useful for debugging purposes.";

    DuplicateException exception = new DuplicateException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
  }

  @Test
  void testConstructor_withSpecialCharacters() {
    // Test that constructor handles special characters in message
    String message = "Duplicate: workflow-id-123 @#$%^&*() \n\t\r";

    DuplicateException exception = new DuplicateException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
  }
}
