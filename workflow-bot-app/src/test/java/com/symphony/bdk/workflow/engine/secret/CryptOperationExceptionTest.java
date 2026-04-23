package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CryptOperationExceptionTest {

  @Test
  void shouldCreateExceptionWithMessageAndCause() {
    Throwable cause = new RuntimeException("root cause");

    CryptOperationException exception = new CryptOperationException("error message", cause);

    assertThat(exception.getMessage()).isEqualTo("error message");
    assertThat(exception.getCause()).isEqualTo(cause);
  }
}
