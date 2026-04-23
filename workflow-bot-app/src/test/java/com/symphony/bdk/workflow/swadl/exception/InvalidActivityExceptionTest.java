package com.symphony.bdk.workflow.swadl.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidActivityExceptionTest {

  @Test
  void shouldFormatMessageWhenConstructed() {
    InvalidActivityException exception = new InvalidActivityException("myWorkflow", "bad activity");

    assertThat(exception.getMessage())
        .isEqualTo("Invalid activity in the workflow myWorkflow: bad activity");
  }
}
