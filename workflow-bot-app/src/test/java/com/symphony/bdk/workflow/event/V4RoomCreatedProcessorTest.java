package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class V4RoomCreatedProcessorTest {

  @Test
  void shouldCreateProcessorWithRuntimeService() {
    RuntimeService runtimeService = mock(RuntimeService.class);

    V4RoomCreatedProcessor processor = new V4RoomCreatedProcessor(runtimeService);

    assertThat(processor).isNotNull();
    assertThat(processor).isInstanceOf(AbstractRealTimeEventProcessor.class);
  }
}
