package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4User;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for RequestReceivedEventProcessor.
 */
@ExtendWith(MockitoExtension.class)
class RequestReceivedEventProcessorClaudeTest {

  @Mock
  private RuntimeService runtimeService;

  @Mock
  private SignalEventReceivedBuilder signalEventReceivedBuilder;

  @Captor
  private ArgumentCaptor<String> signalNameCaptor;

  @Captor
  private ArgumentCaptor<Map<String, Object>> variablesCaptor;

  private RequestReceivedEventProcessor processor;

  @BeforeEach
  void setUp() {
    processor = new RequestReceivedEventProcessor(runtimeService);
  }

  @Test
  void constructor_withRuntimeService_shouldInitializeCorrectly() {
    // Given: A RuntimeService
    RuntimeService service = runtimeService;

    // When: Creating a new processor
    RequestReceivedEventProcessor newProcessor = new RequestReceivedEventProcessor(service);

    // Then: The processor should be created successfully
    assertThat(newProcessor).isNotNull();
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw any exception
    assertThatCode(() -> new RequestReceivedEventProcessor(runtimeService))
        .doesNotThrowAnyException();
  }

  @Test
  void processEventSource_withWorkflowId_shouldCreateSignalWithCorrectName() throws Exception {
    // Given: A RequestReceivedEvent with a workflowId
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("testWorkflowId");

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: Signal should be created with correct name (request-received_ + workflowId)
    verify(runtimeService).createSignalEvent(signalNameCaptor.capture());
    assertThat(signalNameCaptor.getValue()).isEqualTo("request-received_testWorkflowId");
  }

  @Test
  void processEventSource_withArguments_shouldIncludeArgumentsInEventHolder() throws Exception {
    // Given: A RequestReceivedEvent with arguments
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow1");
    Map<String, Object> arguments = new HashMap<>();
    arguments.put("key1", "value1");
    arguments.put("key2", 42);
    eventSource.setArguments(arguments);

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: EventHolder's args should contain the original arguments plus eventName
    Map<String, Object> args = eventHolder.getArgs();
    assertThat(args).containsEntry("key1", "value1");
    assertThat(args).containsEntry("key2", 42);
    assertThat(args).containsEntry("eventName", "request-received_workflow1");
  }

  @Test
  void processEventSource_withNullArguments_shouldCreateEmptyArgsMap() throws Exception {
    // Given: A RequestReceivedEvent with null arguments
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow2");
    eventSource.setArguments(null);

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: EventHolder's args should only contain eventName
    Map<String, Object> args = eventHolder.getArgs();
    assertThat(args).hasSize(1);
    assertThat(args).containsEntry("eventName", "request-received_workflow2");
  }

  @Test
  void processEventSource_shouldSetVariablesOnSignalEventBuilder() throws Exception {
    // Given: A RequestReceivedEvent
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow3");

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));
    variables.put("extraVar", "extraValue");

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: setVariables should be called with the variables map
    verify(signalEventReceivedBuilder).setVariables(variablesCaptor.capture());
    Map<String, Object> capturedVariables = variablesCaptor.getValue();
    assertThat(capturedVariables).containsKey(ActivityExecutorContext.EVENT);
    assertThat(capturedVariables).containsEntry("extraVar", "extraValue");
  }

  @Test
  void processEventSource_shouldCallSendOnSignalEventBuilder() throws Exception {
    // Given: A RequestReceivedEvent
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow4");

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: send should be called on the builder
    verify(signalEventReceivedBuilder).send();
  }

  @Test
  void processEventSource_withEmptyWorkflowId_shouldCreateSignalWithEventNameOnly() throws Exception {
    // Given: A RequestReceivedEvent with empty workflowId
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("");

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: Signal should be created with "request-received_" only
    verify(runtimeService).createSignalEvent(signalNameCaptor.capture());
    assertThat(signalNameCaptor.getValue()).isEqualTo("request-received_");
  }

  @Test
  void processEventSource_withComplexArguments_shouldPreserveAllArgumentTypes() throws Exception {
    // Given: A RequestReceivedEvent with various argument types
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow5");

    Map<String, Object> arguments = new HashMap<>();
    arguments.put("stringVal", "test");
    arguments.put("intVal", 123);
    arguments.put("boolVal", true);
    arguments.put("doubleVal", 45.67);
    Map<String, String> nestedMap = new HashMap<>();
    nestedMap.put("nested", "value");
    arguments.put("mapVal", nestedMap);
    eventSource.setArguments(arguments);

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: All argument types should be preserved in the args map
    Map<String, Object> args = eventHolder.getArgs();
    assertThat(args).containsEntry("stringVal", "test");
    assertThat(args).containsEntry("intVal", 123);
    assertThat(args).containsEntry("boolVal", true);
    assertThat(args).containsEntry("doubleVal", 45.67);
    assertThat(args).containsEntry("mapVal", nestedMap);
    assertThat(args).containsKey("eventName");
  }

  @Test
  void processEventSource_withToken_shouldNotAffectProcessing() throws Exception {
    // Given: A RequestReceivedEvent with a token
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow6");
    eventSource.setToken("secretToken123");

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: Processing should complete successfully
    verify(runtimeService).createSignalEvent("request-received_workflow6");
    verify(signalEventReceivedBuilder).send();
  }

  @Test
  void processEventSource_multipleCalls_shouldCreateMultipleSignals() throws Exception {
    // Given: Multiple RequestReceivedEvents
    RequestReceivedEvent event1 = new RequestReceivedEvent();
    event1.setWorkflowId("workflow7");
    RequestReceivedEvent event2 = new RequestReceivedEvent();
    event2.setWorkflowId("workflow8");

    Map<String, Object> variables1 = new HashMap<>();
    variables1.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, event1, new HashMap<>()));
    Map<String, Object> variables2 = new HashMap<>();
    variables2.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, event2, new HashMap<>()));

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called multiple times
    processor.processEventSource(event1, variables1);
    processor.processEventSource(event2, variables2);

    // Then: Both signals should be created and sent
    verify(runtimeService).createSignalEvent("request-received_workflow7");
    verify(runtimeService).createSignalEvent("request-received_workflow8");
  }

  @Test
  void processEventSource_withSpecialCharactersInWorkflowId_shouldHandleCorrectly() throws Exception {
    // Given: A RequestReceivedEvent with special characters in workflowId
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow-with-dashes_and_underscores.123");

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: Signal name should include the special characters
    verify(runtimeService).createSignalEvent(signalNameCaptor.capture());
    assertThat(signalNameCaptor.getValue()).isEqualTo("request-received_workflow-with-dashes_and_underscores.123");
  }

  @Test
  void processEventSource_withExistingEventHolderArgs_shouldOverrideWithNewArgs() throws Exception {
    // Given: An EventHolder with existing args
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow9");
    Map<String, Object> newArguments = new HashMap<>();
    newArguments.put("newKey", "newValue");
    eventSource.setArguments(newArguments);

    Map<String, Object> existingArgs = new HashMap<>();
    existingArgs.put("oldKey", "oldValue");
    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>(null, eventSource, existingArgs);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: EventHolder args should be replaced with new arguments from the event
    Map<String, Object> args = eventHolder.getArgs();
    assertThat(args).containsEntry("newKey", "newValue");
    assertThat(args).containsEntry("eventName", "request-received_workflow9");
    assertThat(args).doesNotContainKey("oldKey");
  }

  @Test
  void processEventSource_shouldNotModifyOriginalArguments() throws Exception {
    // Given: A RequestReceivedEvent with arguments
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow10");
    Map<String, Object> originalArguments = new HashMap<>();
    originalArguments.put("originalKey", "originalValue");
    eventSource.setArguments(originalArguments);

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: Original arguments should remain unchanged
    assertThat(originalArguments).hasSize(1);
    assertThat(originalArguments).containsEntry("originalKey", "originalValue");
    assertThat(originalArguments).doesNotContainKey("eventName");
  }

  @Test
  void processEventSource_withEmptyArguments_shouldOnlyAddEventName() throws Exception {
    // Given: A RequestReceivedEvent with empty arguments map
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow11");
    eventSource.setArguments(new HashMap<>());

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: Args should only contain eventName
    Map<String, Object> args = eventHolder.getArgs();
    assertThat(args).hasSize(1);
    assertThat(args).containsEntry("eventName", "request-received_workflow11");
  }

  @Test
  void processEventSource_shouldNotThrowException() {
    // Given: A valid RequestReceivedEvent
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow12");

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When/Then: Should not throw any exception
    assertThatCode(() -> processor.processEventSource(eventSource, variables))
        .doesNotThrowAnyException();
  }

  @Test
  void processEventSource_withInitiatorInEventHolder_shouldPreserveInitiator() throws Exception {
    // Given: An EventHolder with an initiator
    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setWorkflowId("workflow13");

    V4Initiator initiator = new V4Initiator();
    V4User user = new V4User();
    user.setUserId(12345L);
    initiator.setUser(user);

    EventHolder<RequestReceivedEvent> eventHolder = new EventHolder<>(initiator, eventSource, new HashMap<>());
    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // When: processEventSource is called
    processor.processEventSource(eventSource, variables);

    // Then: EventHolder should still have the initiator
    assertThat(eventHolder.getInitiator()).isEqualTo(initiator);
  }
}
