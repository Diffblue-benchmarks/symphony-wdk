package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class V4RoomMemberPromotedToOwnerProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldCreateProcessorWithCorrectEventName() {
    V4RoomMemberPromotedToOwnerProcessor processor = new V4RoomMemberPromotedToOwnerProcessor(runtimeService);

    assertThat(processor.eventName).isEqualTo(WorkflowEventType.ROOM_MEMBER_PROMOTED_TO_OWNER.getEventName());
    assertThat(processor.runtimeService).isEqualTo(runtimeService);
  }
}
