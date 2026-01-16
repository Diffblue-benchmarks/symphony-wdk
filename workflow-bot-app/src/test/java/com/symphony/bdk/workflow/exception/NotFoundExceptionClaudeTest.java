package com.symphony.bdk.workflow.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotFoundExceptionClaudeTest {

  @Test
  void testConstructor_withMessage() {
    // Test that constructor properly initializes exception with message
    String message = "Resource not found";

    NotFoundException exception = new NotFoundException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withNullMessage() {
    // Test that constructor handles null message
    NotFoundException exception = new NotFoundException(null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isNull();
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyMessage() {
    // Test that constructor handles empty string message
    String message = "";

    NotFoundException exception = new NotFoundException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_isRuntimeException() {
    // Test that NotFoundException is a RuntimeException
    String message = "Entity not found";

    NotFoundException exception = new NotFoundException(message);

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    String message1 = "First resource not found";
    String message2 = "Second resource not found";

    NotFoundException exception1 = new NotFoundException(message1);
    NotFoundException exception2 = new NotFoundException(message2);

    assertThat(exception1).isNotNull();
    assertThat(exception2).isNotNull();
    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getMessage()).isEqualTo(message1);
    assertThat(exception2.getMessage()).isEqualTo(message2);
  }

  @Test
  void testConstructor_canBeThrown() {
    // Test that the exception can actually be thrown and caught
    String message = "Workflow not found";

    try {
      throw new NotFoundException(message);
    } catch (NotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(message);
      assertThat(e.getCause()).isNull();
    }
  }

  @Test
  void testConstructor_canBeCaughtAsRuntimeException() {
    // Test that the exception can be caught as RuntimeException
    String message = "Operation not found";

    try {
      throw new NotFoundException(message);
    } catch (RuntimeException e) {
      assertThat(e).isInstanceOf(NotFoundException.class);
      assertThat(e.getMessage()).isEqualTo(message);
    }
  }

  @Test
  void testConstructor_withLongMessage() {
    // Test that constructor handles long message strings
    String message = "Resource not found: This is a very long message that describes "
        + "in detail what went wrong during the operation, including various context "
        + "information that may be useful for debugging purposes.";

    NotFoundException exception = new NotFoundException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
  }

  @Test
  void testConstructor_withSpecialCharacters() {
    // Test that constructor handles special characters in message
    String message = "Not found: workflow-id-123 @#$%^&*() \n\t\r";

    NotFoundException exception = new NotFoundException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
  }
}
