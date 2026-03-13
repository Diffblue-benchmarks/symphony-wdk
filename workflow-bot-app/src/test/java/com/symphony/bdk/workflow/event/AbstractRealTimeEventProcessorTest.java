package com.symphony.bdk.workflow.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4User;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

class AbstractRealTimeEventProcessorTest {

  private TestRealTimeEventProcessor processor;
  private RuntimeService runtimeService;
  private SignalEventReceivedBuilder signalEventReceivedBuilder;

  @BeforeEach
  void setUp() {
    runtimeService = mock(RuntimeService.class);
    signalEventReceivedBuilder = mock(SignalEventReceivedBuilder.class);
    processor = new TestRealTimeEventProcessor(runtimeService, "testEvent");

    when(runtimeService.createSignalEvent(any())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(anyMap())).thenReturn(signalEventReceivedBuilder);
  }

  @Test
  void shouldProcessEventWithoutInitiator() throws Exception {
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    when(event.getSource()).thenReturn("testSource");
    when(event.getInitiator()).thenReturn(null);

    processor.process(event);

    ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
    verify(runtimeService).createSignalEvent(eq("testEvent"));
    verify(signalEventReceivedBuilder).setVariables(captor.capture());
    verify(signalEventReceivedBuilder).send();

    Map<String, Object> variables = captor.getValue();
    assertThat(variables).containsKey(ActivityExecutorContext.EVENT);
    assertThat(variables).doesNotContainKey(ActivityExecutorContext.INITIATOR);
  }

  @Test
  void shouldProcessEventWithInitiatorButNullUser() throws Exception {
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    V4Initiator initiator = mock(V4Initiator.class);
    when(event.getSource()).thenReturn("testSource");
    when(event.getInitiator()).thenReturn(initiator);
    when(initiator.getUser()).thenReturn(null);

    processor.process(event);

    ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
    verify(signalEventReceivedBuilder).setVariables(captor.capture());

    Map<String, Object> variables = captor.getValue();
    assertThat(variables).containsKey(ActivityExecutorContext.EVENT);
    assertThat(variables).doesNotContainKey(ActivityExecutorContext.INITIATOR);
  }

  @Test
  void shouldProcessEventWithUserButNullUserId() throws Exception {
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    V4Initiator initiator = mock(V4Initiator.class);
    V4User user = mock(V4User.class);
    when(event.getSource()).thenReturn("testSource");
    when(event.getInitiator()).thenReturn(initiator);
    when(initiator.getUser()).thenReturn(user);
    when(user.getUserId()).thenReturn(null);

    processor.process(event);

    ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
    verify(signalEventReceivedBuilder).setVariables(captor.capture());

    Map<String, Object> variables = captor.getValue();
    assertThat(variables).containsKey(ActivityExecutorContext.EVENT);
    assertThat(variables).doesNotContainKey(ActivityExecutorContext.INITIATOR);
  }

  @Test
  void shouldProcessEventWithCompleteInitiator() throws Exception {
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    V4Initiator initiator = mock(V4Initiator.class);
    V4User user = mock(V4User.class);
    Long userId = 123456L;

    when(event.getSource()).thenReturn("testSource");
    when(event.getInitiator()).thenReturn(initiator);
    when(initiator.getUser()).thenReturn(user);
    when(user.getUserId()).thenReturn(userId);

    processor.process(event);

    ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
    verify(runtimeService).createSignalEvent(eq("testEvent"));
    verify(signalEventReceivedBuilder).setVariables(captor.capture());
    verify(signalEventReceivedBuilder).send();

    Map<String, Object> variables = captor.getValue();
    assertThat(variables).containsKey(ActivityExecutorContext.EVENT);
    assertThat(variables).containsKey(ActivityExecutorContext.INITIATOR);
    assertThat(variables.get(ActivityExecutorContext.INITIATOR)).isEqualTo(userId);

    EventHolder<?> eventHolder = (EventHolder<?>) variables.get(ActivityExecutorContext.EVENT);
    assertThat(eventHolder.getInitiator()).isEqualTo(initiator);
    assertThat(eventHolder.getSource()).isEqualTo("testSource");
    assertThat(eventHolder.getArgs()).containsEntry("eventName", "testEvent");
  }

  @Test
  void shouldCreateEventHolderWithEventNameInArgs() throws Exception {
    RealTimeEvent<String> event = mock(RealTimeEvent.class);
    when(event.getSource()).thenReturn("testSource");
    when(event.getInitiator()).thenReturn(null);

    processor.process(event);

    ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
    verify(signalEventReceivedBuilder).setVariables(captor.capture());

    Map<String, Object> variables = captor.getValue();
    EventHolder<?> eventHolder = (EventHolder<?>) variables.get(ActivityExecutorContext.EVENT);
    assertThat(eventHolder).isNotNull();
    assertThat(eventHolder.getArgs()).containsEntry("eventName", "testEvent");
  }

  // Concrete test implementation of the abstract class
  private static class TestRealTimeEventProcessor extends AbstractRealTimeEventProcessor<String> {
    public TestRealTimeEventProcessor(RuntimeService runtimeService, String eventName) {
      super(runtimeService, eventName);
    }
  }
}
