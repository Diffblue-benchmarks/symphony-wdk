package com.symphony.bdk.workflow.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DuplicateExceptionTest {

  @Test
  void shouldCreateExceptionWithMessage() {
    String message = "Duplicate entity found";

    DuplicateException exception = new DuplicateException(message);

    assertThat(exception.getMessage()).isEqualTo(message);
  }

  @Test
  void shouldBeInstanceOfRuntimeException() {
    DuplicateException exception = new DuplicateException("Test message");

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void shouldHandleNullMessage() {
    DuplicateException exception = new DuplicateException(null);

    assertThat(exception.getMessage()).isNull();
  }
}
