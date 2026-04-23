package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4MessageSent;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RealTimeEventProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldReturnCorrectSourceType() {
    V4MessageSentEventProcessor processor = new V4MessageSentEventProcessor(runtimeService);

    Class<?> sourceType = processor.sourceType();

    assertThat(sourceType).isEqualTo(V4MessageSent.class);
  }
}
