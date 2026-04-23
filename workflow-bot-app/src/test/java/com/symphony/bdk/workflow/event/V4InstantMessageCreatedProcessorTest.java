package com.symphony.bdk.workflow.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

class V4InstantMessageCreatedProcessorTest {

  @Test
  void shouldCreateProcessorWithImCreatedEventName() {
    RuntimeService runtimeService = mock(RuntimeService.class);

    V4InstantMessageCreatedProcessor underTest = new V4InstantMessageCreatedProcessor(runtimeService);

    assertThat(underTest.eventName).isEqualTo(WorkflowEventType.IM_CREATED.getEventName());
  }
}
