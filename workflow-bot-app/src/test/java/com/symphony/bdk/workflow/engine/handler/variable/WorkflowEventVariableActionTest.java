package com.symphony.bdk.workflow.engine.handler.variable;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.history.event.HistoricVariableUpdateEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowEventVariableActionTest {

  @Mock
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  @Mock
  private RuntimeService runtimeService;

  private WorkflowEventVariableAction workflowEventVariableAction;

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @BeforeEach
  void setUp() {
    workflowEventVariableAction = new WorkflowEventVariableAction(workflowDirectedGraphService, runtimeService);
  }

  @Test
  void shouldNotProcessWhenEventIsNotHistoricVariableUpdateEventEntity() {
    HistoryEvent historyEvent = mock(HistoryEvent.class);

    workflowEventVariableAction.execute(historyEvent);

    verify(workflowDirectedGraphService, never()).getDirectedGraph(any());
    verify(runtimeService, never()).setVariable(any(), any(), any());
  }

  @Test
  void shouldNotProcessWhenVariableNameDoesNotMatchEvent() {
    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("someOtherVariable");

    workflowEventVariableAction.execute(event);

    verify(workflowDirectedGraphService, never()).getDirectedGraph(any());
    verify(runtimeService, never()).setVariable(any(), any(), any());
  }

  @Test
  void shouldNotProcessWhenByteValueIsNull() {
    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("event");
    when(event.getByteValue()).thenReturn(null);

    workflowEventVariableAction.execute(event);

    verify(workflowDirectedGraphService, never()).getDirectedGraph(any());
    verify(runtimeService, never()).setVariable(any(), any(), any());
  }

  @Test
  void shouldSetVariableWhenEventNameAndDirectedGraphArePresent() throws Exception {
    Map<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "myEvent");
    EventHolder<?> eventHolder = new EventHolder<>(null, null, args);
    byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(eventHolder);

    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("event");
    when(event.getByteValue()).thenReturn(bytes);
    when(event.getProcessDefinitionKey()).thenReturn("processKey");
    when(event.getExecutionId()).thenReturn("execId");

    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("processKey");
    WorkflowNode node = new WorkflowNode().eventId("myEventId");
    graph.registerToDictionary("myEvent", node);

    when(workflowDirectedGraphService.getDirectedGraph("processKey")).thenReturn(graph);

    workflowEventVariableAction.execute(event);

    verify(runtimeService).setVariable(eq("execId"), eq("myEventId"), any(EventHolder.class));
  }

  @Test
  void shouldNotSetVariableWhenEventNameIsNull() throws Exception {
    Map<String, Object> args = new HashMap<>();
    EventHolder<?> eventHolder = new EventHolder<>(null, null, args);
    byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(eventHolder);

    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("event");
    when(event.getByteValue()).thenReturn(bytes);
    when(event.getProcessDefinitionKey()).thenReturn("processKey");

    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("processKey");
    when(workflowDirectedGraphService.getDirectedGraph("processKey")).thenReturn(graph);

    workflowEventVariableAction.execute(event);

    verify(runtimeService, never()).setVariable(any(), any(), any());
  }

  @Test
  void shouldNotSetVariableWhenDirectedGraphIsNull() throws Exception {
    Map<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "myEvent");
    EventHolder<?> eventHolder = new EventHolder<>(null, null, args);
    byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(eventHolder);

    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("event");
    when(event.getByteValue()).thenReturn(bytes);
    when(event.getProcessDefinitionKey()).thenReturn("processKey");

    when(workflowDirectedGraphService.getDirectedGraph("processKey")).thenReturn(null);

    workflowEventVariableAction.execute(event);

    verify(runtimeService, never()).setVariable(any(), any(), any());
  }

  @Test
  void shouldNotSetVariableWhenEventIdIsBlank() throws Exception {
    Map<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "myEvent");
    EventHolder<?> eventHolder = new EventHolder<>(null, null, args);
    byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(eventHolder);

    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("event");
    when(event.getByteValue()).thenReturn(bytes);
    when(event.getProcessDefinitionKey()).thenReturn("processKey");

    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("processKey");
    WorkflowNode node = new WorkflowNode().eventId("");
    graph.registerToDictionary("myEvent", node);

    when(workflowDirectedGraphService.getDirectedGraph("processKey")).thenReturn(graph);

    workflowEventVariableAction.execute(event);

    verify(runtimeService, never()).setVariable(any(), any(), any());
  }

  @Test
  void shouldHandleInvalidJsonGracefully() {
    byte[] invalidJson = "not-valid-json".getBytes(StandardCharsets.UTF_8);

    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("event");
    when(event.getByteValue()).thenReturn(invalidJson);

    workflowEventVariableAction.execute(event);

    verify(runtimeService, never()).setVariable(any(), any(), any());
  }

  @Test
  void shouldRemoveEventNameFromArgsBeforeStoringVariable() throws Exception {
    Map<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "myEvent");
    args.put("otherArg", "otherValue");
    EventHolder<?> eventHolder = new EventHolder<>(null, null, args);
    byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(eventHolder);

    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("event");
    when(event.getByteValue()).thenReturn(bytes);
    when(event.getProcessDefinitionKey()).thenReturn("processKey");
    when(event.getExecutionId()).thenReturn("execId");

    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("processKey");
    WorkflowNode node = new WorkflowNode().eventId("myEventId");
    graph.registerToDictionary("myEvent", node);

    when(workflowDirectedGraphService.getDirectedGraph("processKey")).thenReturn(graph);

    workflowEventVariableAction.execute(event);

    verify(runtimeService).setVariable(eq("execId"), eq("myEventId"), any(EventHolder.class));
  }
}
