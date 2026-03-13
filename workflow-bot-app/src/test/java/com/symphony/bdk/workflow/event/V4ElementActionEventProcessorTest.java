package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4SymphonyElementsAction;
import com.symphony.bdk.workflow.engine.camunda.variable.FormVariableListener;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.engine.executor.message.SendMessageExecutor;

import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.runtime.VariableInstanceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class V4ElementActionEventProcessorTest {

    @Mock
    private RuntimeService runtimeService;

    @Mock
    private MessageCorrelationBuilder correlationBuilder;

    @Mock
    private VariableInstanceQuery variableInstanceQuery;

    private V4ElementActionEventProcessor processor;

    @BeforeEach
    void setUp() {
        // Arrange
        processor = new V4ElementActionEventProcessor(runtimeService);
    }

    @Test
    void constructorShouldInitializeWithRuntimeServiceAndEventName() {
        // Arrange & Act
        V4ElementActionEventProcessor newProcessor = new V4ElementActionEventProcessor(runtimeService);

        // Assert
        assertNotNull(newProcessor);
    }

    @Test
    void processEventSourceWithProcessIdPresentShouldCorrelateWithProcessInstance() {
        // Arrange
        String formId = "testFormId";
        String messageId = "testMessageId";
        Map<String, Object> formValues = new HashMap<>();
        formValues.put("field1", "value1");

        V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
        eventSource.setFormId(formId);
        eventSource.setFormMessageId(messageId);
        eventSource.setFormValues(formValues);

        Map<String, Object> variables = new HashMap<>();
        EventHolder<?> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
        variables.put(ActivityExecutorContext.EVENT, eventHolder);

        String processInstanceId = "processInstance123";
        VariableInstance variableInstance = mock(VariableInstance.class);
        when(variableInstance.getProcessInstanceId()).thenReturn(processInstanceId);
        when(variableInstance.getValue()).thenReturn(Arrays.asList(messageId));

        when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);
        when(correlationBuilder.setVariables(anyMap())).thenReturn(correlationBuilder);
        when(correlationBuilder.processInstanceId(anyString())).thenReturn(correlationBuilder);
        when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.variableName(anyString())).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.list()).thenReturn(Arrays.asList(variableInstance));

        // Act
        processor.processEventSource(eventSource, variables);

        // Assert
        ArgumentCaptor<String> messageNameCaptor = ArgumentCaptor.forClass(String.class);
        verify(runtimeService).createMessageCorrelation(messageNameCaptor.capture());
        assertEquals("form-reply_" + formId, messageNameCaptor.getValue());

        ArgumentCaptor<Map<String, Object>> variablesCaptor = ArgumentCaptor.forClass(Map.class);
        verify(correlationBuilder).setVariables(variablesCaptor.capture());
        Map<String, Object> capturedVariables = variablesCaptor.getValue();
        assertTrue(capturedVariables.containsKey(FormVariableListener.FORM_VARIABLES));
        assertEquals(singletonMap(formId, formValues), capturedVariables.get(FormVariableListener.FORM_VARIABLES));

        verify(correlationBuilder).processInstanceId(processInstanceId);
        verify(correlationBuilder).correlateAll();
    }

    @Test
    void processEventSourceWithNoProcessIdShouldStartMessageOnly() {
        // Arrange
        String formId = "testFormId";
        String messageId = "testMessageId";
        Map<String, Object> formValues = new HashMap<>();
        formValues.put("field1", "value1");

        V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
        eventSource.setFormId(formId);
        eventSource.setFormMessageId(messageId);
        eventSource.setFormValues(formValues);

        Map<String, Object> variables = new HashMap<>();
        EventHolder<?> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
        variables.put(ActivityExecutorContext.EVENT, eventHolder);

        when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);
        when(correlationBuilder.setVariables(anyMap())).thenReturn(correlationBuilder);
        when(correlationBuilder.startMessageOnly()).thenReturn(correlationBuilder);
        when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.variableName(anyString())).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.list()).thenReturn(Arrays.asList());

        // Act
        processor.processEventSource(eventSource, variables);

        // Assert
        verify(correlationBuilder).startMessageOnly();
        verify(correlationBuilder).correlateAll();
    }

    @Test
    void processEventSourceWithNoProcessIdAndCorrelationExceptionShouldHandleGracefully() {
        // Arrange
        String formId = "testFormId";
        String messageId = "testMessageId";
        Map<String, Object> formValues = new HashMap<>();
        formValues.put("field1", "value1");

        V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
        eventSource.setFormId(formId);
        eventSource.setFormMessageId(messageId);
        eventSource.setFormValues(formValues);

        Map<String, Object> variables = new HashMap<>();
        EventHolder<?> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
        variables.put(ActivityExecutorContext.EVENT, eventHolder);

        when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);
        when(correlationBuilder.setVariables(anyMap())).thenReturn(correlationBuilder);
        when(correlationBuilder.startMessageOnly()).thenReturn(correlationBuilder);
        doThrow(new MismatchingMessageCorrelationException("No matching process")).when(correlationBuilder).correlateAll();
        when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.variableName(anyString())).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.list()).thenReturn(Arrays.asList());

        // Act
        processor.processEventSource(eventSource, variables);

        // Assert
        verify(correlationBuilder).startMessageOnly();
        verify(correlationBuilder).correlateAll();
    }

    @Test
    void processEventSourceShouldSetFormVariablesCorrectly() {
        // Arrange
        String formId = "formId123";
        String messageId = "messageId456";
        Map<String, Object> formValues = new HashMap<>();
        formValues.put("field1", "value1");
        formValues.put("field2", "value2");

        V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
        eventSource.setFormId(formId);
        eventSource.setFormMessageId(messageId);
        eventSource.setFormValues(formValues);

        Map<String, Object> variables = new HashMap<>();
        EventHolder<?> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
        variables.put(ActivityExecutorContext.EVENT, eventHolder);

        when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);
        when(correlationBuilder.setVariables(anyMap())).thenReturn(correlationBuilder);
        when(correlationBuilder.startMessageOnly()).thenReturn(correlationBuilder);
        when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.variableName(anyString())).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.list()).thenReturn(Arrays.asList());

        // Act
        processor.processEventSource(eventSource, variables);

        // Assert
        ArgumentCaptor<Map<String, Object>> variablesCaptor = ArgumentCaptor.forClass(Map.class);
        verify(correlationBuilder).setVariables(variablesCaptor.capture());
        Map<String, Object> capturedVariables = variablesCaptor.getValue();

        assertTrue(capturedVariables.containsKey(FormVariableListener.FORM_VARIABLES));
        Map<String, Object> formVariables = (Map<String, Object>) capturedVariables.get(FormVariableListener.FORM_VARIABLES);
        assertEquals(singletonMap(formId, formValues), formVariables);
    }

    @Test
    void processEventSourceShouldSetEventNameInEventHolderArgs() {
        // Arrange
        String formId = "testFormId";
        String messageId = "testMessageId";

        V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
        eventSource.setFormId(formId);
        eventSource.setFormMessageId(messageId);
        eventSource.setFormValues(new HashMap<>());

        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> eventArgs = new HashMap<>();
        EventHolder<?> eventHolder = new EventHolder<>(null, eventSource, eventArgs);
        variables.put(ActivityExecutorContext.EVENT, eventHolder);

        when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);
        when(correlationBuilder.setVariables(anyMap())).thenReturn(correlationBuilder);
        when(correlationBuilder.startMessageOnly()).thenReturn(correlationBuilder);
        when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.variableName(anyString())).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.list()).thenReturn(Arrays.asList());

        // Act
        processor.processEventSource(eventSource, variables);

        // Assert
        assertTrue(eventArgs.containsKey("eventName"));
        assertEquals("form-reply_" + formId, eventArgs.get("eventName"));
    }

    @Test
    void getProcessToExecuteShouldReturnProcessIdWhenMessageIdMatches() {
        // Arrange
        String formId = "formId123";
        String messageId = "messageId456";
        String expectedVariableName = String.format("%s.%s.%s", formId, ActivityExecutorContext.OUTPUTS,
            SendMessageExecutor.OUTPUT_MESSAGE_IDS_KEY);

        String processInstanceId = "processInstance789";
        VariableInstance variableInstance = mock(VariableInstance.class);
        when(variableInstance.getProcessInstanceId()).thenReturn(processInstanceId);
        List<String> messageIds = Arrays.asList(messageId, "otherMessageId");
        when(variableInstance.getValue()).thenReturn(messageIds);

        when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.variableName(expectedVariableName)).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.list()).thenReturn(Arrays.asList(variableInstance));

        when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);
        when(correlationBuilder.setVariables(anyMap())).thenReturn(correlationBuilder);
        when(correlationBuilder.processInstanceId(anyString())).thenReturn(correlationBuilder);

        V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
        eventSource.setFormId(formId);
        eventSource.setFormMessageId(messageId);
        eventSource.setFormValues(new HashMap<>());

        Map<String, Object> variables = new HashMap<>();
        EventHolder<?> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
        variables.put(ActivityExecutorContext.EVENT, eventHolder);

        // Act
        processor.processEventSource(eventSource, variables);

        // Assert
        verify(variableInstanceQuery).variableName(expectedVariableName);
        verify(correlationBuilder).processInstanceId(processInstanceId);
    }

    @Test
    void getProcessToExecuteShouldReturnEmptyWhenNoMatchingMessageId() {
        // Arrange
        String formId = "formId123";
        String messageId = "messageId456";
        String expectedVariableName = String.format("%s.%s.%s", formId, ActivityExecutorContext.OUTPUTS,
            SendMessageExecutor.OUTPUT_MESSAGE_IDS_KEY);

        VariableInstance variableInstance = mock(VariableInstance.class);
        List<String> messageIds = Arrays.asList("otherMessageId1", "otherMessageId2");
        when(variableInstance.getValue()).thenReturn(messageIds);

        when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.variableName(expectedVariableName)).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.list()).thenReturn(Arrays.asList(variableInstance));

        when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);
        when(correlationBuilder.setVariables(anyMap())).thenReturn(correlationBuilder);
        when(correlationBuilder.startMessageOnly()).thenReturn(correlationBuilder);

        V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
        eventSource.setFormId(formId);
        eventSource.setFormMessageId(messageId);
        eventSource.setFormValues(new HashMap<>());

        Map<String, Object> variables = new HashMap<>();
        EventHolder<?> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
        variables.put(ActivityExecutorContext.EVENT, eventHolder);

        // Act
        processor.processEventSource(eventSource, variables);

        // Assert
        verify(variableInstanceQuery).variableName(expectedVariableName);
        verify(correlationBuilder).startMessageOnly();
    }

    @Test
    void getProcessToExecuteShouldReturnEmptyWhenNoVariableInstances() {
        // Arrange
        String formId = "formId123";
        String messageId = "messageId456";
        String expectedVariableName = String.format("%s.%s.%s", formId, ActivityExecutorContext.OUTPUTS,
            SendMessageExecutor.OUTPUT_MESSAGE_IDS_KEY);

        when(runtimeService.createVariableInstanceQuery()).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.variableName(expectedVariableName)).thenReturn(variableInstanceQuery);
        when(variableInstanceQuery.list()).thenReturn(Arrays.asList());

        when(runtimeService.createMessageCorrelation(anyString())).thenReturn(correlationBuilder);
        when(correlationBuilder.setVariables(anyMap())).thenReturn(correlationBuilder);
        when(correlationBuilder.startMessageOnly()).thenReturn(correlationBuilder);

        V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
        eventSource.setFormId(formId);
        eventSource.setFormMessageId(messageId);
        eventSource.setFormValues(new HashMap<>());

        Map<String, Object> variables = new HashMap<>();
        EventHolder<?> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
        variables.put(ActivityExecutorContext.EVENT, eventHolder);

        // Act
        processor.processEventSource(eventSource, variables);

        // Assert
        verify(variableInstanceQuery).variableName(expectedVariableName);
        verify(correlationBuilder).startMessageOnly();
    }
}
