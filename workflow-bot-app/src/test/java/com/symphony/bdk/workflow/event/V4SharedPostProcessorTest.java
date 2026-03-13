package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class V4SharedPostProcessorTest {

  @Test
  void constructorShouldInitializeProcessorWithRuntimeService() {
    RuntimeService mockRuntimeService = mock(RuntimeService.class);

    V4SharedPostProcessor processor = new V4SharedPostProcessor(mockRuntimeService);

    assertNotNull(processor);
  }
}
