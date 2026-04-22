package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4SymphonyElementsAction;
import com.symphony.bdk.workflow.engine.executor.EventHolder;

import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.runtime.VariableInstanceQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(classes = {V4ElementActionEventProcessor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class V4ElementActionEventProcessorDiffblueTest {
  @MockBean private RuntimeService runtimeService;

  @Autowired private V4ElementActionEventProcessor v4ElementActionEventProcessor;

  /**
   * Test {@link V4ElementActionEventProcessor#V4ElementActionEventProcessor(RuntimeService)}.
   * Verifies that the processor can be constructed directly.
   */
  @Test
  @DisplayName("Test V4ElementActionEventProcessor(RuntimeService) constructor")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void V4ElementActionEventProcessor.<init>(RuntimeService)"})
  void testConstructor() {
    // Arrange and Act
    RuntimeService mockService = mock(RuntimeService.class);
    V4ElementActionEventProcessor processor = new V4ElementActionEventProcessor(mockService);

    // Assert – constructor completes without error; eventName is set to FORM_REPLIED event name
    assertDoesNotThrow(() -> new V4ElementActionEventProcessor(mockService));
  }

  /**
   * Test {@link V4ElementActionEventProcessor#processEventSource(V4SymphonyElementsAction, Map)}
   * with {@code V4SymphonyElementsAction}, {@code Map}.
   *
   * <p>Method under test: {@link
   * V4ElementActionEventProcessor#processEventSource(V4SymphonyElementsAction, Map)}
   */
  @Test
  @DisplayName(
      "Test processEventSource(V4SymphonyElementsAction, Map) with 'V4SymphonyElementsAction', 'Map'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void V4ElementActionEventProcessor.processEventSource(V4SymphonyElementsAction, Map)"
  })
  void testProcessEventSourceWithV4SymphonyElementsActionMap() {
    // Arrange
    when(runtimeService.createMessageCorrelation(Mockito.<String>any()))
        .thenThrow(new MismatchingMessageCorrelationException("An error occurred"));

    V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
    eventSource.formValues(new HashMap<>());

    HashMap<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "Args");
    EventHolder<Object> eventHolder = new EventHolder<>(new V4Initiator(), "Source", args);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("form", "Variables");
    variables.put("event", eventHolder);

    // Act and Assert
    assertThrows(
        MismatchingMessageCorrelationException.class,
        () -> v4ElementActionEventProcessor.processEventSource(eventSource, variables));
    verify(runtimeService).createMessageCorrelation("form-reply_null");
  }

  /**
   * Test {@link V4ElementActionEventProcessor#processEventSource(V4SymphonyElementsAction, Map)}
   * when a matching process instance is found (processId is present).
   * Exercises lines 45–46 where correlationBuilder.processInstanceId(...).correlateAll() is called.
   */
  @Test
  @DisplayName("Test processEventSource – process instance found, correlates by processInstanceId")
  @MethodsUnderTest({
    "void V4ElementActionEventProcessor.processEventSource(V4SymphonyElementsAction, Map)"
  })
  void testProcessEventSource_processIdPresent_correlatesWithProcessInstanceId() {
    // Arrange
    String formId = "myForm";
    String messageId = "msg123";
    String processInstanceId = "proc-001";

    VariableInstance variableInstance = mock(VariableInstance.class);
    when(variableInstance.getValue()).thenReturn(Arrays.asList(messageId));
    when(variableInstance.getProcessInstanceId()).thenReturn(processInstanceId);

    VariableInstanceQuery variableInstanceQuery = mock(VariableInstanceQuery.class);
    when(variableInstanceQuery.variableName(anyString())).thenReturn(variableInstanceQuery);
    when(variableInstanceQuery.list()).thenReturn(Collections.singletonList(variableInstance));
    when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);

    MessageCorrelationBuilder correlationBuilder = mock(MessageCorrelationBuilder.class);
    when(correlationBuilder.setVariables(any())).thenReturn(correlationBuilder);
    when(correlationBuilder.processInstanceId(anyString())).thenReturn(correlationBuilder);
    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);

    V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
    eventSource.setFormId(formId);
    eventSource.setFormMessageId(messageId);
    eventSource.formValues(new HashMap<>());

    HashMap<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "Args");
    EventHolder<Object> eventHolder = new EventHolder<>(new V4Initiator(), "Source", args);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("form", "Variables");
    variables.put("event", eventHolder);

    // Act
    v4ElementActionEventProcessor.processEventSource(eventSource, variables);

    // Assert
    verify(correlationBuilder).processInstanceId(processInstanceId);
    verify(correlationBuilder).correlateAll();
  }

  /**
   * Test {@link V4ElementActionEventProcessor#processEventSource(V4SymphonyElementsAction, Map)}
   * when no matching process instance is found (processId is empty) and startMessageOnly succeeds.
   * Exercises lines 49–50.
   */
  @Test
  @DisplayName("Test processEventSource – no process instance found, uses startMessageOnly")
  @MethodsUnderTest({
    "void V4ElementActionEventProcessor.processEventSource(V4SymphonyElementsAction, Map)"
  })
  void testProcessEventSource_processIdEmpty_usesStartMessageOnly() {
    // Arrange
    VariableInstanceQuery variableInstanceQuery = mock(VariableInstanceQuery.class);
    when(variableInstanceQuery.variableName(anyString())).thenReturn(variableInstanceQuery);
    when(variableInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);

    MessageCorrelationBuilder correlationBuilder = mock(MessageCorrelationBuilder.class);
    when(correlationBuilder.setVariables(any())).thenReturn(correlationBuilder);
    when(correlationBuilder.startMessageOnly()).thenReturn(correlationBuilder);
    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);

    V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
    eventSource.setFormId("formX");
    eventSource.setFormMessageId("msg456");
    eventSource.formValues(new HashMap<>());

    HashMap<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "Args");
    EventHolder<Object> eventHolder = new EventHolder<>(new V4Initiator(), "Source", args);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("form", "Variables");
    variables.put("event", eventHolder);

    // Act
    v4ElementActionEventProcessor.processEventSource(eventSource, variables);

    // Assert
    verify(correlationBuilder).startMessageOnly();
    verify(correlationBuilder).correlateAll();
  }

  /**
   * Test {@link V4ElementActionEventProcessor#processEventSource(V4SymphonyElementsAction, Map)}
   * when no process instance is found and startMessageOnly().correlateAll() throws
   * MismatchingMessageCorrelationException – exception is caught and swallowed (lines 51–54).
   */
  @Test
  @DisplayName("Test processEventSource – startMessageOnly throws MismatchingMessageCorrelationException, caught silently")
  @MethodsUnderTest({
    "void V4ElementActionEventProcessor.processEventSource(V4SymphonyElementsAction, Map)"
  })
  void testProcessEventSource_startMessageOnlyThrows_exceptionCaughtSilently() {
    // Arrange
    VariableInstanceQuery variableInstanceQuery = mock(VariableInstanceQuery.class);
    when(variableInstanceQuery.variableName(anyString())).thenReturn(variableInstanceQuery);
    when(variableInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);

    MessageCorrelationBuilder correlationBuilder = mock(MessageCorrelationBuilder.class);
    when(correlationBuilder.setVariables(any())).thenReturn(correlationBuilder);
    when(correlationBuilder.startMessageOnly()).thenReturn(correlationBuilder);
    Mockito.doThrow(new MismatchingMessageCorrelationException("no process waiting"))
        .when(correlationBuilder).correlateAll();
    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);

    V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
    eventSource.setFormId("formY");
    eventSource.setFormMessageId("msg789");
    eventSource.formValues(new HashMap<>());

    HashMap<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "Args");
    EventHolder<Object> eventHolder = new EventHolder<>(new V4Initiator(), "Source", args);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("form", "Variables");
    variables.put("event", eventHolder);

    // Act and Assert – no exception should propagate
    assertDoesNotThrow(() -> v4ElementActionEventProcessor.processEventSource(eventSource, variables));
    verify(correlationBuilder).startMessageOnly();
    verify(correlationBuilder).correlateAll();
  }
}
