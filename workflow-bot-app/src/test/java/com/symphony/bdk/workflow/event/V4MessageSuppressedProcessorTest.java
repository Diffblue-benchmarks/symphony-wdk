package com.symphony.bdk.workflow.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class V4MessageSuppressedProcessorTest {

  @Test
  void constructor_shouldInitializeProcessorWithRuntimeService() {
    // Arrange
    RuntimeService runtimeService = mock(RuntimeService.class);

    // Act
    V4MessageSuppressedProcessor processor = new V4MessageSuppressedProcessor(runtimeService);

    // Assert
    assertThat(processor).isNotNull();
  }
}
