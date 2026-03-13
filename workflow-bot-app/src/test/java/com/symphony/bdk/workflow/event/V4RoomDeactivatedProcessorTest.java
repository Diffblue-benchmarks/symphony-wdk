package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class V4RoomDeactivatedProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldCreateProcessorWithRuntimeService() {
    // Arrange & Act
    V4RoomDeactivatedProcessor processor = new V4RoomDeactivatedProcessor(runtimeService);

    // Assert
    assertThat(processor).isNotNull();
    assertThat(processor.runtimeService).isEqualTo(runtimeService);
    assertThat(processor.eventName).isEqualTo("room-deactivated");
  }
}
