package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class V4RoomMemberDemotedFromOwnerProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldCreateProcessorWithRuntimeService() {
    // Arrange & Act
    V4RoomMemberDemotedFromOwnerProcessor processor = new V4RoomMemberDemotedFromOwnerProcessor(runtimeService);

    // Assert
    assertThat(processor).isNotNull();
    assertThat(processor.runtimeService).isEqualTo(runtimeService);
    assertThat(processor.eventName).isEqualTo("room-member-demoted-from-owner-event");
  }
}
