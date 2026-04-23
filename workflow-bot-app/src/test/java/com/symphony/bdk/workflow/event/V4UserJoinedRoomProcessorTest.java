package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class V4UserJoinedRoomProcessorTest {

  @Test
  void shouldInitializeWithUserJoinedRoomEventName() {
    RuntimeService runtimeService = mock(RuntimeService.class);

    V4UserJoinedRoomProcessor processor = new V4UserJoinedRoomProcessor(runtimeService);

    assertThat(processor.eventName).isEqualTo(WorkflowEventType.USER_JOINED_ROOM.getEventName());
    assertThat(processor.runtimeService).isEqualTo(runtimeService);
  }
}
