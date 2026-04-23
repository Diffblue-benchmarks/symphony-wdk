package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class V4RoomReactivatedProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldCreateProcessorWithRoomReactivatedEventName() {
    // when
    V4RoomReactivatedProcessor processor = new V4RoomReactivatedProcessor(runtimeService);

    // then
    assertThat(processor.eventName).isEqualTo(WorkflowEventType.ROOM_REACTIVATED.getEventName());
    assertThat(processor.runtimeService).isEqualTo(runtimeService);
  }
}
