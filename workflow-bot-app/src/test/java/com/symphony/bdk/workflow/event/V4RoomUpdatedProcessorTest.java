package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class V4RoomUpdatedProcessorTest {

  @Test
  void shouldCreateProcessorWithRuntimeService() {
    RuntimeService runtimeService = mock(RuntimeService.class);

    V4RoomUpdatedProcessor processor = new V4RoomUpdatedProcessor(runtimeService);

    assertThat(processor).isNotNull();
    assertThat(processor).isInstanceOf(AbstractRealTimeEventProcessor.class);
  }
}
