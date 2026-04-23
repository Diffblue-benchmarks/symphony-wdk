package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class V4RoomUpdatedProcessorTest {

  @Test
  void shouldInitializeWithRuntimeServiceAndRoomUpdatedEventName() {
    RuntimeService runtimeService = mock(RuntimeService.class);

    V4RoomUpdatedProcessor processor = new V4RoomUpdatedProcessor(runtimeService);

    assertThat(processor.runtimeService).isEqualTo(runtimeService);
    assertThat(processor.eventName).isEqualTo(WorkflowEventType.ROOM_UPDATED.getEventName());
  }
}
