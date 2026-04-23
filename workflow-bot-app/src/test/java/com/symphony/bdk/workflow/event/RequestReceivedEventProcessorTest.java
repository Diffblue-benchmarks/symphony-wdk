package com.symphony.bdk.workflow.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class RequestReceivedEventProcessorTest {

  @Test
  void shouldCreateProcessorWithRequestReceivedEventName() {
    RuntimeService runtimeService = mock(RuntimeService.class);

    RequestReceivedEventProcessor underTest = new RequestReceivedEventProcessor(runtimeService);

    assertThat(underTest.eventName).isEqualTo(WorkflowEventType.REQUEST_RECEIVED.getEventName());
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldProcessEventSourceWithArguments() throws Exception {
    RuntimeService runtimeService = mock(RuntimeService.class);
    SignalEventReceivedBuilder signalBuilder = mock(SignalEventReceivedBuilder.class);
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalBuilder);
    when(signalBuilder.setVariables(anyMap())).thenReturn(signalBuilder);

    RequestReceivedEventProcessor underTest = new RequestReceivedEventProcessor(runtimeService);

    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow1");
    Map<String, Object> arguments = new HashMap<>();
    arguments.put("param1", "value1");
    eventSource.setArguments(arguments);

    EventHolder eventHolder = new EventHolder<>(null, null, new HashMap<>());
    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    underTest.processEventSource(eventSource, variables);

    String expectedSignalName = WorkflowEventType.REQUEST_RECEIVED.getEventName() + "workflow1";
    verify(runtimeService).createSignalEvent(expectedSignalName);
    verify(signalBuilder).setVariables(variables);
    verify(signalBuilder).send();
    assertThat(eventHolder.getArgs()).containsEntry(RealTimeEventProcessor.EVENT_NAME_KEY, expectedSignalName);
    assertThat(eventHolder.getArgs()).containsEntry("param1", "value1");
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldProcessEventSourceWithNullArguments() throws Exception {
    RuntimeService runtimeService = mock(RuntimeService.class);
    SignalEventReceivedBuilder signalBuilder = mock(SignalEventReceivedBuilder.class);
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalBuilder);
    when(signalBuilder.setVariables(anyMap())).thenReturn(signalBuilder);

    RequestReceivedEventProcessor underTest = new RequestReceivedEventProcessor(runtimeService);

    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflowAbc");
    eventSource.setArguments(null);

    EventHolder eventHolder = new EventHolder<>(null, null, new HashMap<>());
    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    underTest.processEventSource(eventSource, variables);

    String expectedSignalName = WorkflowEventType.REQUEST_RECEIVED.getEventName() + "workflowAbc";
    verify(runtimeService).createSignalEvent(expectedSignalName);
    verify(signalBuilder).setVariables(variables);
    verify(signalBuilder).send();
    assertThat(eventHolder.getArgs()).containsEntry(RealTimeEventProcessor.EVENT_NAME_KEY, expectedSignalName);
  }
}
