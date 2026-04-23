package com.symphony.bdk.workflow.swadl.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoStartingEventExceptionTest {

  @Test
  void shouldContainWorkflowIdInMessage() {
    NoStartingEventException exception = new NoStartingEventException("my-workflow");

    assertThat(exception.getMessage())
        .isEqualTo("Workflow with id \"my-workflow\" does not have any starting event.");
  }
}
