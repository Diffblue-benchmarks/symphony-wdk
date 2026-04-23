package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class V4RoomCreatedProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldInitialiseWithRoomCreatedEventName() {
    V4RoomCreatedProcessor processor = new V4RoomCreatedProcessor(runtimeService);

    assertThat(processor.eventName).isEqualTo(WorkflowEventType.ROOM_CREATED.getEventName());
    assertThat(processor.runtimeService).isEqualTo(runtimeService);
  }
}
