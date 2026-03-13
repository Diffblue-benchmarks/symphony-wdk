package com.symphony.bdk.workflow.engine.secret;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CryptOperationExceptionTest {

  @Test
  void shouldCreateExceptionWithMessageAndCause() {
    String message = "Encryption operation failed";
    Throwable cause = new RuntimeException("Underlying error");

    CryptOperationException exception = new CryptOperationException(message, cause);

    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isEqualTo(cause);
  }

  @Test
  void shouldBeRuntimeException() {
    CryptOperationException exception = new CryptOperationException("message", new Exception());

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void shouldHandleNullMessage() {
    Throwable cause = new Exception("cause");

    CryptOperationException exception = new CryptOperationException(null, cause);

    assertThat(exception.getMessage()).isNull();
    assertThat(exception.getCause()).isEqualTo(cause);
  }

  @Test
  void shouldHandleNullCause() {
    String message = "Error message";

    CryptOperationException exception = new CryptOperationException(message, null);

    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isNull();
  }
}
