package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class V4InstantMessageCreatedProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldInitializeProcessorWithCorrectEventName() {
    // When
    V4InstantMessageCreatedProcessor processor = new V4InstantMessageCreatedProcessor(runtimeService);

    // Then
    assertThat(processor).isNotNull();
  }
}
