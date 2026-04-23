package com.symphony.bdk.workflow.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnauthorizedExceptionTest {

  @Test
  void shouldContainMessageWhenCreated() {
    UnauthorizedException exception = new UnauthorizedException("access denied");

    assertThat(exception.getMessage()).isEqualTo("access denied");
  }
}
