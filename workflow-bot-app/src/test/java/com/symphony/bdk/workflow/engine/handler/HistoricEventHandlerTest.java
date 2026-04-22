package com.symphony.bdk.workflow.engine.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.engine.handler.variable.WorkflowEventVariableAction;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class HistoricEventHandlerTest {

  private HistoricEventActionExecutor historicEventActionExecutor;
  private AuditTrailLogAction auditTrailLogAction;
  private WorkflowEventVariableAction workflowEventVariableAction;
  private HistoricEventHandler historicEventHandler;

  @BeforeEach
  void setUp() {
    historicEventActionExecutor = mock(HistoricEventActionExecutor.class);
    auditTrailLogAction = mock(AuditTrailLogAction.class);
    workflowEventVariableAction = mock(WorkflowEventVariableAction.class);
    historicEventHandler =
        new HistoricEventHandler(historicEventActionExecutor, auditTrailLogAction, workflowEventVariableAction);
  }

  @Test
  @DisplayName("Constructor sets all fields correctly")
  void testConstructor() {
    // Assert
    assertEquals(historicEventActionExecutor, historicEventHandler.historicEventActionExecutor);
    assertEquals(auditTrailLogAction, historicEventHandler.auditTrailLogAction);
    assertEquals(workflowEventVariableAction, historicEventHandler.workflowEventVariableAction);
  }

  @Test
  @DisplayName("handleEvent executes both audit and variable actions")
  void testHandleEvent() {
    // Arrange
    doNothing().when(historicEventActionExecutor)
        .executeAction(any(HistoricEventAction.class), any(HistoryEvent.class));
    HistoryEvent historyEvent = new HistoryEvent();

    // Act
    historicEventHandler.handleEvent(historyEvent);

    // Assert
    verify(historicEventActionExecutor, times(1)).executeAction(auditTrailLogAction, historyEvent);
    verify(historicEventActionExecutor, times(1)).executeAction(workflowEventVariableAction, historyEvent);
  }

  @Test
  @DisplayName("handleEvents iterates over list and handles each event")
  void testHandleEvents() {
    // Arrange
    doNothing().when(historicEventActionExecutor)
        .executeAction(any(HistoricEventAction.class), any(HistoryEvent.class));
    HistoryEvent event1 = new HistoryEvent();
    HistoryEvent event2 = new HistoryEvent();
    List<HistoryEvent> events = Arrays.asList(event1, event2);

    // Act
    historicEventHandler.handleEvents(events);

    // Assert
    verify(historicEventActionExecutor, times(1)).executeAction(auditTrailLogAction, event1);
    verify(historicEventActionExecutor, times(1)).executeAction(workflowEventVariableAction, event1);
    verify(historicEventActionExecutor, times(1)).executeAction(auditTrailLogAction, event2);
    verify(historicEventActionExecutor, times(1)).executeAction(workflowEventVariableAction, event2);
  }
}
