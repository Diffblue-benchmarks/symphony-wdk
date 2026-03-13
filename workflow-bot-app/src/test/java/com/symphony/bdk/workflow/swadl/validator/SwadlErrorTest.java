package com.symphony.bdk.workflow.swadl.validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwadlErrorTest {

  @Test
  void shouldFormatWithLineNumberWhenLineNumberIsNonNegative() {
    SwadlError error = new SwadlError(10, "Validation error");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 10: Validation error");
  }

  @Test
  void shouldFormatWithLineNumberWhenLineNumberIsZero() {
    SwadlError error = new SwadlError(0, "Error at line zero");

    String result = error.toString();

    assertThat(result).isEqualTo("Line 0: Error at line zero");
  }

  @Test
  void shouldReturnMessageOnlyWhenLineNumberIsNegative() {
    SwadlError error = new SwadlError(-1, "General error");

    String result = error.toString();

    assertThat(result).isEqualTo("General error");
  }
}
