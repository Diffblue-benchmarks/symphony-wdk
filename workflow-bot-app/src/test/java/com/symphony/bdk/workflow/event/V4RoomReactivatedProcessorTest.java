package com.symphony.bdk.workflow.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

class V4RoomReactivatedProcessorTest {

  @Test
  void shouldCreateProcessorWhenConstructorCalled() {
    // Arrange
    RuntimeService runtimeService = mock(RuntimeService.class);

    // Act
    V4RoomReactivatedProcessor processor = new V4RoomReactivatedProcessor(runtimeService);

    // Assert
    assertThat(processor).isNotNull();
  }
}
