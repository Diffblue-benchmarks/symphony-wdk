package com.symphony.bdk.workflow.event;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestReceivedEventProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Mock
  private SignalEventReceivedBuilder signalEventReceivedBuilder;

  private RequestReceivedEventProcessor processor;

  @BeforeEach
  void setUp() {
    processor = new RequestReceivedEventProcessor(runtimeService);
  }

  @Test
  void shouldInitializeProcessorWithCorrectEventName() {
    // When
    RequestReceivedEventProcessor newProcessor = new RequestReceivedEventProcessor(runtimeService);

    // Then
    assertThat(newProcessor).isNotNull();
  }

  @Test
  void shouldProcessEventSourceWithArguments() throws Exception {
    // Given
    String workflowId = "workflow123";
    Map<String, Object> arguments = new HashMap<>();
    arguments.put("key1", "value1");
    arguments.put("key2", "value2");

    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId(workflowId);
    eventSource.setArguments(arguments);

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>();
    eventHolder.setArgs(new HashMap<>());

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(any(String.class))).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any(Map.class))).thenReturn(signalEventReceivedBuilder);

    // When
    processor.processEventSource(eventSource, variables);

    // Then
    verify(runtimeService).createSignalEvent(eq("request-received_" + workflowId));
    verify(signalEventReceivedBuilder).setVariables(eq(variables));
    verify(signalEventReceivedBuilder).send();

    assertThat(eventHolder.getArgs()).containsEntry(RealTimeEventProcessor.EVENT_NAME_KEY, "request-received_" + workflowId);
    assertThat(eventHolder.getArgs()).containsEntry("key1", "value1");
    assertThat(eventHolder.getArgs()).containsEntry("key2", "value2");
  }

  @Test
  void shouldProcessEventSourceWithNullArguments() throws Exception {
    // Given
    String workflowId = "workflow456";

    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId(workflowId);
    eventSource.setArguments(null);

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>();
    eventHolder.setArgs(new HashMap<>());

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(any(String.class))).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any(Map.class))).thenReturn(signalEventReceivedBuilder);

    // When
    processor.processEventSource(eventSource, variables);

    // Then
    verify(runtimeService).createSignalEvent(eq("request-received_" + workflowId));
    verify(signalEventReceivedBuilder).setVariables(eq(variables));
    verify(signalEventReceivedBuilder).send();

    assertThat(eventHolder.getArgs()).containsEntry(RealTimeEventProcessor.EVENT_NAME_KEY, "request-received_" + workflowId);
  }

  @Test
  void shouldProcessEventSourceWithEmptyArguments() throws Exception {
    // Given
    String workflowId = "workflow789";

    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId(workflowId);
    eventSource.setArguments(new HashMap<>());

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>();
    eventHolder.setArgs(new HashMap<>());

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(any(String.class))).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any(Map.class))).thenReturn(signalEventReceivedBuilder);

    // When
    processor.processEventSource(eventSource, variables);

    // Then
    verify(runtimeService).createSignalEvent(eq("request-received_" + workflowId));
    verify(signalEventReceivedBuilder).setVariables(eq(variables));
    verify(signalEventReceivedBuilder).send();

    assertThat(eventHolder.getArgs()).containsEntry(RealTimeEventProcessor.EVENT_NAME_KEY, "request-received_" + workflowId);
  }

  @Test
  void shouldProcessEventSourceAndMergeArgsCorrectly() throws Exception {
    // Given
    String workflowId = "testWorkflow";
    Map<String, Object> existingArgs = new HashMap<>();
    existingArgs.put("existingKey", "existingValue");

    Map<String, Object> eventArguments = new HashMap<>();
    eventArguments.put("newKey", "newValue");

    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId(workflowId);
    eventSource.setArguments(eventArguments);

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>();
    eventHolder.setArgs(existingArgs);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(any(String.class))).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any(Map.class))).thenReturn(signalEventReceivedBuilder);

    // When
    processor.processEventSource(eventSource, variables);

    // Then
    ArgumentCaptor<Map<String, Object>> argsCaptor = ArgumentCaptor.forClass(Map.class);
    assertThat(eventHolder.getArgs()).containsEntry("newKey", "newValue");
    assertThat(eventHolder.getArgs()).containsEntry(RealTimeEventProcessor.EVENT_NAME_KEY, "request-received_" + workflowId);
  }
}
