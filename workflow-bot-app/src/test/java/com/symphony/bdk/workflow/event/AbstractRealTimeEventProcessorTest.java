package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4User;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractRealTimeEventProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  private static final String EVENT_NAME = "testEvent";

  private AbstractRealTimeEventProcessor<String> createProcessor() {
    return new AbstractRealTimeEventProcessor<String>(runtimeService, EVENT_NAME) {};
  }

  @Test
  void shouldProcessEventWhenInitiatorIsNull() throws Exception {
    // given
    AbstractRealTimeEventProcessor<String> processor = createProcessor();
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    SignalEventReceivedBuilder builder = mock(SignalEventReceivedBuilder.class);

    when(event.getInitiator()).thenReturn(null);
    when(event.getSource()).thenReturn("testSource");
    when(runtimeService.createSignalEvent(EVENT_NAME)).thenReturn(builder);
    when(builder.setVariables(any())).thenReturn(builder);

    // when
    processor.process(event);

    // then
    verify(runtimeService).createSignalEvent(EVENT_NAME);
    verify(builder).send();
  }

  @Test
  void shouldProcessEventWithInitiatorUserIdAndAddToVariables() throws Exception {
    // given
    AbstractRealTimeEventProcessor<String> processor = createProcessor();
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    SignalEventReceivedBuilder builder = mock(SignalEventReceivedBuilder.class);

    V4User user = new V4User();
    user.setUserId(12345L);
    V4Initiator initiator = new V4Initiator();
    initiator.setUser(user);

    when(event.getInitiator()).thenReturn(initiator);
    when(event.getSource()).thenReturn("testSource");
    when(runtimeService.createSignalEvent(EVENT_NAME)).thenReturn(builder);
    when(builder.setVariables(any())).thenReturn(builder);

    // when
    processor.process(event);

    // then
    ArgumentCaptor<Map> variablesCaptor = ArgumentCaptor.forClass(Map.class);
    verify(builder).setVariables(variablesCaptor.capture());
    Map<String, Object> variables = variablesCaptor.getValue();
    assertThat(variables).containsKey(ActivityExecutorContext.INITIATOR);
    assertThat(variables.get(ActivityExecutorContext.INITIATOR)).isEqualTo(12345L);
    verify(builder).send();
  }

  @Test
  void shouldProcessEventSourceAndSetEventNameInArgs() throws Exception {
    // given
    AbstractRealTimeEventProcessor<String> processor = createProcessor();
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    SignalEventReceivedBuilder builder = mock(SignalEventReceivedBuilder.class);

    when(event.getInitiator()).thenReturn(null);
    when(event.getSource()).thenReturn("testSource");
    when(runtimeService.createSignalEvent(EVENT_NAME)).thenReturn(builder);
    when(builder.setVariables(any())).thenReturn(builder);

    // when
    processor.process(event);

    // then
    ArgumentCaptor<Map> variablesCaptor = ArgumentCaptor.forClass(Map.class);
    verify(builder).setVariables(variablesCaptor.capture());
    Map<String, Object> variables = variablesCaptor.getValue();
    assertThat(variables).containsKey(ActivityExecutorContext.EVENT);
    EventHolder<?> eventHolder = (EventHolder<?>) variables.get(ActivityExecutorContext.EVENT);
    assertThat(eventHolder.getArgs()).containsEntry(RealTimeEventProcessor.EVENT_NAME_KEY, EVENT_NAME);
  }

  @Test
  void shouldProcessEventWhenInitiatorUserIsNull() throws Exception {
    // given
    AbstractRealTimeEventProcessor<String> processor = createProcessor();
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    SignalEventReceivedBuilder builder = mock(SignalEventReceivedBuilder.class);

    V4Initiator initiator = new V4Initiator();
    initiator.setUser(null);

    when(event.getInitiator()).thenReturn(initiator);
    when(event.getSource()).thenReturn("testSource");
    when(runtimeService.createSignalEvent(EVENT_NAME)).thenReturn(builder);
    when(builder.setVariables(any())).thenReturn(builder);

    // when
    processor.process(event);

    // then
    ArgumentCaptor<Map> variablesCaptor = ArgumentCaptor.forClass(Map.class);
    verify(builder).setVariables(variablesCaptor.capture());
    Map<String, Object> variables = variablesCaptor.getValue();
    assertThat(variables).doesNotContainKey(ActivityExecutorContext.INITIATOR);
    verify(builder).send();
  }
}
