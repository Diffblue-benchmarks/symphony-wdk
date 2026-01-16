package com.symphony.bdk.workflow.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnauthorizedExceptionClaudeTest {

  @Test
  void testConstructor_withMessage() {
    // Test that constructor properly initializes exception with message
    String message = "Unauthorized access";

    UnauthorizedException exception = new UnauthorizedException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withNullMessage() {
    // Test that constructor handles null message
    UnauthorizedException exception = new UnauthorizedException(null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isNull();
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyMessage() {
    // Test that constructor handles empty string message
    String message = "";

    UnauthorizedException exception = new UnauthorizedException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_isRuntimeException() {
    // Test that UnauthorizedException is a RuntimeException
    String message = "Access denied";

    UnauthorizedException exception = new UnauthorizedException(message);

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    String message1 = "First unauthorized access";
    String message2 = "Second unauthorized access";

    UnauthorizedException exception1 = new UnauthorizedException(message1);
    UnauthorizedException exception2 = new UnauthorizedException(message2);

    assertThat(exception1).isNotNull();
    assertThat(exception2).isNotNull();
    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getMessage()).isEqualTo(message1);
    assertThat(exception2.getMessage()).isEqualTo(message2);
  }

  @Test
  void testConstructor_canBeThrown() {
    // Test that the exception can actually be thrown and caught
    String message = "User not authorized";

    try {
      throw new UnauthorizedException(message);
    } catch (UnauthorizedException e) {
      assertThat(e.getMessage()).isEqualTo(message);
      assertThat(e.getCause()).isNull();
    }
  }

  @Test
  void testConstructor_canBeCaughtAsRuntimeException() {
    // Test that the exception can be caught as RuntimeException
    String message = "Insufficient permissions";

    try {
      throw new UnauthorizedException(message);
    } catch (RuntimeException e) {
      assertThat(e).isInstanceOf(UnauthorizedException.class);
      assertThat(e.getMessage()).isEqualTo(message);
    }
  }

  @Test
  void testConstructor_withLongMessage() {
    // Test that constructor handles long message strings
    String message = "Unauthorized access: This is a very long message that describes "
        + "in detail what went wrong during the operation, including various context "
        + "information that may be useful for debugging purposes.";

    UnauthorizedException exception = new UnauthorizedException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
  }

  @Test
  void testConstructor_withSpecialCharacters() {
    // Test that constructor handles special characters in message
    String message = "Unauthorized: user-id-123 @#$%^&*() \n\t\r";

    UnauthorizedException exception = new UnauthorizedException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
  }
}
