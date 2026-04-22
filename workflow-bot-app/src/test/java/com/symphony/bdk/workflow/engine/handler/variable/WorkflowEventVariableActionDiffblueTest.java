package com.symphony.bdk.workflow.engine.handler.variable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.history.event.HistoricVariableUpdateEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
class WorkflowEventVariableActionDiffblueTest {

  @InjectMocks
  private WorkflowEventVariableAction workflowEventVariableAction;

  @Mock
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  @Mock
  private RuntimeService runtimeService;

  private static final String EVENT_HOLDER_JSON =
      "{\"initiator\":{\"user\":{\"userId\":123,\"firstName\":\"firstName\",\"lastName\":\"lastName\","
          + "\"displayName\":\"displayName\",\"email\":\"email@email.com\",\"username\":\"username\"}},"
          + "\"source\":{\"@eventImpl\":\"com.symphony.bdk.gen.api.model.V4MessageSent\","
          + "\"message\":{\"messageId\":\"MSG_ID\",\"parentMessageId\":null,\"timestamp\":1672825563000,"
          + "\"message\":\"<p>/start</p>\",\"sharedMessage\":null,\"data\":\"{}\",\"attachments\":null,"
          + "\"user\":{\"userId\":123,\"firstName\":\"firstName\",\"lastName\":\"lastName\","
          + "\"displayName\":\"displayName\",\"email\":\"email@email.com\",\"username\":\"username\"},"
          + "\"stream\":{\"streamId\":\"STREAM_ID\",\"streamType\":\"IM\",\"roomName\":null,\"members\":null,"
          + "\"external\":null,\"crossPod\":null},\"externalRecipients\":false,\"diagnostic\":null,"
          + "\"userAgent\":\"AGENT_USER\",\"originalFormat\":\"com.symphony.messageml.v2\","
          + "\"disclaimer\":null,\"sid\":\"SID\",\"replacing\":null,\"replacedBy\":null,"
          + "\"initialTimestamp\":0,\"initialMessageId\":null,\"silent\":null}},"
          + "\"args\":{\"eventName\":\"EVENT_NAME\"}}";

  /**
   * Test {@link WorkflowEventVariableAction#WorkflowEventVariableAction(WorkflowDirectedGraphService, RuntimeService)}.
   *
   * <p>Method under test:
   * {@link WorkflowEventVariableAction#WorkflowEventVariableAction(WorkflowDirectedGraphService, RuntimeService)}
   */
  @Test
  @DisplayName("Test new WorkflowEventVariableAction(WorkflowDirectedGraphService, RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
      "void WorkflowEventVariableAction.<init>(WorkflowDirectedGraphService, RuntimeService)"})
  void testNewWorkflowEventVariableAction() {
    // Arrange and Act
    WorkflowEventVariableAction action =
        new WorkflowEventVariableAction(workflowDirectedGraphService, runtimeService);

    // Assert - constructor completes without exception; no further interactions
    verify(workflowDirectedGraphService, never()).getDirectedGraph(anyString());
  }

  /**
   * Test {@link WorkflowEventVariableAction#execute(HistoryEvent)}.
   *
   * <ul>
   *   <li>Given {@link HistoryEvent} is not {@link HistoricVariableUpdateEventEntity}.
   *   <li>Then no graph service interaction.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowEventVariableAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName("Test execute(HistoryEvent); given HistoryEvent is not HistoricVariableUpdateEventEntity")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowEventVariableAction.execute(HistoryEvent)"})
  void testExecute_givenHistoryEventIsNotVariableUpdate_thenNoInteraction() {
    // Arrange
    HistoryEvent historyEvent = new HistoryEvent();

    // Act
    workflowEventVariableAction.execute(historyEvent);

    // Assert
    verify(workflowDirectedGraphService, never()).getDirectedGraph(anyString());
    verify(runtimeService, never()).setVariable(anyString(), anyString(), any());
  }

  /**
   * Test {@link WorkflowEventVariableAction#execute(HistoryEvent)}.
   *
   * <ul>
   *   <li>Given variable name is not EVENT.
   *   <li>Then no graph service interaction.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowEventVariableAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName("Test execute(HistoryEvent); given variable name is not EVENT; then no interaction")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowEventVariableAction.execute(HistoryEvent)"})
  void testExecute_givenVariableNameIsNotEvent_thenNoInteraction() {
    // Arrange
    HistoricVariableUpdateEventEntity event = new HistoricVariableUpdateEventEntity();
    event.setVariableName("otherVariable");
    event.setByteValue(EVENT_HOLDER_JSON.getBytes(StandardCharsets.UTF_8));

    // Act
    workflowEventVariableAction.execute(event);

    // Assert
    verify(workflowDirectedGraphService, never()).getDirectedGraph(anyString());
    verify(runtimeService, never()).setVariable(anyString(), anyString(), any());
  }

  /**
   * Test {@link WorkflowEventVariableAction#execute(HistoryEvent)}.
   *
   * <ul>
   *   <li>Given byte value is null.
   *   <li>Then no graph service interaction.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowEventVariableAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName("Test execute(HistoryEvent); given byte value is null; then no interaction")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowEventVariableAction.execute(HistoryEvent)"})
  void testExecute_givenByteValueIsNull_thenNoInteraction() {
    // Arrange
    HistoricVariableUpdateEventEntity event = new HistoricVariableUpdateEventEntity();
    event.setVariableName(ActivityExecutorContext.EVENT);
    event.setByteValue(null);

    // Act
    workflowEventVariableAction.execute(event);

    // Assert
    verify(workflowDirectedGraphService, never()).getDirectedGraph(anyString());
    verify(runtimeService, never()).setVariable(anyString(), anyString(), any());
  }

  /**
   * Test {@link WorkflowEventVariableAction#execute(HistoryEvent)}.
   *
   * <ul>
   *   <li>Given directed graph is null.
   *   <li>Then runtime service never called.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowEventVariableAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName("Test execute(HistoryEvent); given directed graph is null; then runtime service not called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowEventVariableAction.execute(HistoryEvent)"})
  void testExecute_givenDirectedGraphIsNull_thenRuntimeServiceNotCalled() {
    // Arrange
    HistoricVariableUpdateEventEntity event = new HistoricVariableUpdateEventEntity();
    event.setVariableName(ActivityExecutorContext.EVENT);
    event.setByteValue(EVENT_HOLDER_JSON.getBytes(StandardCharsets.UTF_8));
    event.setProcessDefinitionKey("PROC_DEF_KEY");

    when(workflowDirectedGraphService.getDirectedGraph(anyString())).thenReturn(null);

    // Act
    workflowEventVariableAction.execute(event);

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph(eq("PROC_DEF_KEY"));
    verify(runtimeService, never()).setVariable(anyString(), anyString(), any());
  }

  /**
   * Test {@link WorkflowEventVariableAction#execute(HistoryEvent)}.
   *
   * <ul>
   *   <li>Given event name is not in the directed graph (readWorkflowNode returns node with blank eventId).
   *   <li>Then runtime service never called.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowEventVariableAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName("Test execute(HistoryEvent); given eventId is blank; then runtime service not called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowEventVariableAction.execute(HistoryEvent)"})
  void testExecute_givenEventIdIsBlank_thenRuntimeServiceNotCalled() {
    // Arrange
    final String processDefKey = "PROC_DEF_KEY";
    final String eventName = "EVENT_NAME";

    HistoricVariableUpdateEventEntity event = new HistoricVariableUpdateEventEntity();
    event.setVariableName(ActivityExecutorContext.EVENT);
    event.setByteValue(EVENT_HOLDER_JSON.getBytes(StandardCharsets.UTF_8));
    event.setProcessDefinitionKey(processDefKey);

    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.setId(eventName);
    // eventId is not set so it will be null/blank

    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph(processDefKey);
    workflowDirectedGraph.registerToDictionary(eventName, workflowNode);
    when(workflowDirectedGraphService.getDirectedGraph(anyString())).thenReturn(workflowDirectedGraph);

    // Act
    workflowEventVariableAction.execute(event);

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph(eq(processDefKey));
    verify(runtimeService, never()).setVariable(anyString(), anyString(), any());
  }

  /**
   * Test {@link WorkflowEventVariableAction#execute(HistoryEvent)}.
   *
   * <ul>
   *   <li>Given valid event holder with matching node in directed graph.
   *   <li>Then runtime service stores variable with eventId.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowEventVariableAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName("Test execute(HistoryEvent); given valid event holder; then runtime service stores variable")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowEventVariableAction.execute(HistoryEvent)"})
  void testExecute_givenValidEventHolder_thenRuntimeServiceStoresVariable() {
    // Arrange
    final String eventName = "EVENT_NAME";
    final String eventId = "EVENT_ID";
    final String executionId = "EXEC_ID";
    final String processDefKey = "PROC_DEF_KEY";

    HistoricVariableUpdateEventEntity event = new HistoricVariableUpdateEventEntity();
    event.setVariableName(ActivityExecutorContext.EVENT);
    event.setByteValue(EVENT_HOLDER_JSON.getBytes(StandardCharsets.UTF_8));
    event.setProcessDefinitionKey(processDefKey);
    event.setExecutionId(executionId);

    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.setId(eventName);
    workflowNode.eventId(eventId);

    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph(processDefKey);
    workflowDirectedGraph.registerToDictionary(eventName, workflowNode);
    when(workflowDirectedGraphService.getDirectedGraph(anyString())).thenReturn(workflowDirectedGraph);
    doNothing().when(runtimeService).setVariable(anyString(), anyString(), any());

    // Act
    workflowEventVariableAction.execute(event);

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph(eq(processDefKey));
    verify(runtimeService).setVariable(eq(executionId), eq(eventId), any(EventHolder.class));
  }

  /**
   * Test {@link WorkflowEventVariableAction#execute(HistoryEvent)}.
   *
   * <ul>
   *   <li>Given invalid JSON byte value.
   *   <li>Then JsonProcessingException is caught and logged; runtime service never called.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowEventVariableAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName("Test execute(HistoryEvent); given invalid JSON; then exception is caught")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowEventVariableAction.execute(HistoryEvent)"})
  void testExecute_givenInvalidJson_thenExceptionIsCaught() {
    // Arrange
    HistoricVariableUpdateEventEntity event = new HistoricVariableUpdateEventEntity();
    event.setVariableName(ActivityExecutorContext.EVENT);
    event.setByteValue("not-valid-json".getBytes(StandardCharsets.UTF_8));

    // Act
    workflowEventVariableAction.execute(event);

    // Assert
    verify(workflowDirectedGraphService, never()).getDirectedGraph(anyString());
    verify(runtimeService, never()).setVariable(anyString(), anyString(), any());
  }
}
