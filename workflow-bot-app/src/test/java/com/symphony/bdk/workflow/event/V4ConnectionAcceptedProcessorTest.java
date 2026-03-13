package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class V4ConnectionAcceptedProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldInitializeProcessorWithRuntimeServiceAndEventName() throws Exception {
    V4ConnectionAcceptedProcessor processor = new V4ConnectionAcceptedProcessor(runtimeService);

    assertThat(processor).isNotNull();

    Field runtimeServiceField = AbstractRealTimeEventProcessor.class.getDeclaredField("runtimeService");
    runtimeServiceField.setAccessible(true);
    RuntimeService actualRuntimeService = (RuntimeService) runtimeServiceField.get(processor);
    assertThat(actualRuntimeService).isEqualTo(runtimeService);

    Field eventNameField = AbstractRealTimeEventProcessor.class.getDeclaredField("eventName");
    eventNameField.setAccessible(true);
    String actualEventName = (String) eventNameField.get(processor);
    assertThat(actualEventName).isEqualTo("connection-accepted");
  }
}
