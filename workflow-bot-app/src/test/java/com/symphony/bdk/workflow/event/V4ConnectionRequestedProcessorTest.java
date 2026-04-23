package com.symphony.bdk.workflow.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

class V4ConnectionRequestedProcessorTest {

  @Test
  void shouldCreateProcessorWithConnectionRequestedEventName() {
    RuntimeService runtimeService = mock(RuntimeService.class);

    V4ConnectionRequestedProcessor underTest = new V4ConnectionRequestedProcessor(runtimeService);

    assertThat(underTest.eventName).isEqualTo(WorkflowEventType.CONNECTION_REQUESTED.getEventName());
  }
}
