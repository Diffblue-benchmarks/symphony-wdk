package com.symphony.bdk.workflow.engine.handler;

import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.engine.handler.variable.WorkflowEventVariableAction;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class HistoricEventHandlerClaudeTest {

  @Mock
  private HistoricEventActionExecutor historicEventActionExecutor;

  @Mock
  private AuditTrailLogAction auditTrailLogAction;

  @Mock
  private WorkflowEventVariableAction workflowEventVariableAction;

  @Mock
  private HistoryEvent historyEvent;

  @Mock
  private HistoryEvent historyEvent2;

  @Mock
  private HistoryEvent historyEvent3;

  private HistoricEventHandler handler;

  @BeforeEach
  void setUp() {
    handler = new HistoricEventHandler(historicEventActionExecutor, auditTrailLogAction,
        workflowEventVariableAction);
  }

  @Test
  void testConstructor_initializesAllFields() {
    // Create a new instance to test the constructor
    HistoricEventHandler newHandler = new HistoricEventHandler(historicEventActionExecutor,
        auditTrailLogAction, workflowEventVariableAction);

    // Verify the handler is created successfully
    assertThat(newHandler).isNotNull();
  }

  @Test
  void testHandleEvent_callsBothActionsInOrder() {
    // When
    handler.handleEvent(historyEvent);

    // Then - Verify both actions are executed
    verify(historicEventActionExecutor, times(1)).executeAction(auditTrailLogAction, historyEvent);
    verify(historicEventActionExecutor, times(1)).executeAction(workflowEventVariableAction,
        historyEvent);

    // Verify they are called in the correct order (audit first, then variable)
    InOrder inOrder = inOrder(historicEventActionExecutor);
    inOrder.verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent);
    inOrder.verify(historicEventActionExecutor).executeAction(workflowEventVariableAction,
        historyEvent);
  }

  @Test
  void testHandleEvent_withNullEvent() {
    // When
    handler.handleEvent(null);

    // Then - Both actions should be called with null
    verify(historicEventActionExecutor, times(1)).executeAction(auditTrailLogAction, null);
    verify(historicEventActionExecutor, times(1)).executeAction(workflowEventVariableAction, null);
  }

  @Test
  void testHandleEvents_withEmptyList() {
    // Given
    List<HistoryEvent> emptyList = Collections.emptyList();

    // When
    handler.handleEvents(emptyList);

    // Then - No interactions should occur
    verifyNoInteractions(historicEventActionExecutor);
  }

  @Test
  void testHandleEvents_withSingleEvent() {
    // Given
    List<HistoryEvent> singleEvent = Collections.singletonList(historyEvent);

    // When
    handler.handleEvents(singleEvent);

    // Then - Both actions should be called once
    verify(historicEventActionExecutor, times(1)).executeAction(auditTrailLogAction, historyEvent);
    verify(historicEventActionExecutor, times(1)).executeAction(workflowEventVariableAction,
        historyEvent);
  }

  @Test
  void testHandleEvents_withMultipleEvents() {
    // Given
    List<HistoryEvent> multipleEvents = Arrays.asList(historyEvent, historyEvent2, historyEvent3);

    // When
    handler.handleEvents(multipleEvents);

    // Then - Verify each event is processed with both actions
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent);
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent2);
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent3);

    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, historyEvent);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, historyEvent2);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, historyEvent3);
  }

  @Test
  void testHandleEvents_withNullList() {
    // When / Then - Should throw NullPointerException
    assertThatThrownBy(() -> handler.handleEvents(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void testHandleEvents_withListContainingNulls() {
    // Given - List with null elements
    List<HistoryEvent> listWithNulls = Arrays.asList(historyEvent, null, historyEvent2);

    // When
    handler.handleEvents(listWithNulls);

    // Then - All events should be processed, including null
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, null);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, null);

    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent);
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent2);

    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, historyEvent);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, historyEvent2);
  }

  @Test
  void testHandleEvent_multipleCalls() {
    // Test that multiple sequential calls work correctly
    handler.handleEvent(historyEvent);
    handler.handleEvent(historyEvent2);

    // Then - Both actions should be called for each event
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent);
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent2);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, historyEvent);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, historyEvent2);
  }
}
