package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CryptOperationExceptionClaudeTest {

  @Test
  void testConstructor_withMessageAndCause() {
    // Test that constructor properly initializes exception with message and cause
    String message = "Encryption failed";
    Throwable cause = new IllegalArgumentException("Invalid key");

    CryptOperationException exception = new CryptOperationException(message, cause);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isEqualTo(cause);
  }

  @Test
  void testConstructor_withNullMessage() {
    // Test that constructor handles null message
    Throwable cause = new IllegalArgumentException("Invalid key");

    CryptOperationException exception = new CryptOperationException(null, cause);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isNull();
    assertThat(exception.getCause()).isEqualTo(cause);
  }

  @Test
  void testConstructor_withNullCause() {
    // Test that constructor handles null cause
    String message = "Encryption failed";

    CryptOperationException exception = new CryptOperationException(message, null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withBothNull() {
    // Test that constructor handles both null message and cause
    CryptOperationException exception = new CryptOperationException(null, null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isNull();
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_isRuntimeException() {
    // Test that CryptOperationException is a RuntimeException
    String message = "Decryption failed";
    Throwable cause = new RuntimeException("Key error");

    CryptOperationException exception = new CryptOperationException(message, cause);

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void testConstructor_withNestedCause() {
    // Test that constructor properly handles nested exceptions
    Throwable rootCause = new IllegalStateException("Root cause");
    Throwable intermediateCause = new RuntimeException("Intermediate", rootCause);
    String message = "Operation failed";

    CryptOperationException exception = new CryptOperationException(message, intermediateCause);

    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isEqualTo(intermediateCause);
    assertThat(exception.getCause().getCause()).isEqualTo(rootCause);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    String message1 = "First error";
    String message2 = "Second error";
    Throwable cause1 = new IllegalArgumentException("Cause 1");
    Throwable cause2 = new IllegalStateException("Cause 2");

    CryptOperationException exception1 = new CryptOperationException(message1, cause1);
    CryptOperationException exception2 = new CryptOperationException(message2, cause2);

    assertThat(exception1).isNotNull();
    assertThat(exception2).isNotNull();
    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getMessage()).isEqualTo(message1);
    assertThat(exception2.getMessage()).isEqualTo(message2);
    assertThat(exception1.getCause()).isEqualTo(cause1);
    assertThat(exception2.getCause()).isEqualTo(cause2);
  }

  @Test
  void testConstructor_withEmptyMessage() {
    // Test that constructor handles empty string message
    String message = "";
    Throwable cause = new RuntimeException("Some error");

    CryptOperationException exception = new CryptOperationException(message, cause);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isEqualTo(cause);
  }

  @Test
  void testConstructor_canBeThrown() {
    // Test that the exception can actually be thrown and caught
    String message = "Cryptographic operation failed";
    Throwable cause = new IllegalArgumentException("Bad parameter");

    try {
      throw new CryptOperationException(message, cause);
    } catch (CryptOperationException e) {
      assertThat(e.getMessage()).isEqualTo(message);
      assertThat(e.getCause()).isEqualTo(cause);
    }
  }

  @Test
  void testConstructor_canBeCaughtAsRuntimeException() {
    // Test that the exception can be caught as RuntimeException
    String message = "Operation failed";
    Throwable cause = new RuntimeException("Root cause");

    try {
      throw new CryptOperationException(message, cause);
    } catch (RuntimeException e) {
      assertThat(e).isInstanceOf(CryptOperationException.class);
      assertThat(e.getMessage()).isEqualTo(message);
      assertThat(e.getCause()).isEqualTo(cause);
    }
  }
}
