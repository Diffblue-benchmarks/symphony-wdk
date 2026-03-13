package com.symphony.bdk.workflow.engine.handler;

import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.engine.handler.variable.WorkflowEventVariableAction;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HistoricEventHandlerTest {

  private HistoricEventActionExecutor historicEventActionExecutor;
  private AuditTrailLogAction auditTrailLogAction;
  private WorkflowEventVariableAction workflowEventVariableAction;
  private HistoricEventHandler handler;

  @BeforeEach
  void setUp() {
    historicEventActionExecutor = mock(HistoricEventActionExecutor.class);
    auditTrailLogAction = mock(AuditTrailLogAction.class);
    workflowEventVariableAction = mock(WorkflowEventVariableAction.class);

    handler = new HistoricEventHandler(historicEventActionExecutor,
        auditTrailLogAction, workflowEventVariableAction);
  }

  @Test
  void shouldInitializeWithAllDependencies() {
    assertThat(handler).isNotNull();
    assertThat(handler.historicEventActionExecutor).isSameAs(historicEventActionExecutor);
    assertThat(handler.auditTrailLogAction).isSameAs(auditTrailLogAction);
    assertThat(handler.workflowEventVariableAction).isSameAs(workflowEventVariableAction);
  }

  @Test
  void shouldExecuteActionsWhenHandlingEvent() {
    HistoryEvent historyEvent = mock(HistoryEvent.class);

    handler.handleEvent(historyEvent);

    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, historyEvent);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, historyEvent);
  }

  @Test
  void shouldHandleMultipleEvents() {
    HistoryEvent event1 = mock(HistoryEvent.class);
    HistoryEvent event2 = mock(HistoryEvent.class);
    HistoryEvent event3 = mock(HistoryEvent.class);
    List<HistoryEvent> events = Arrays.asList(event1, event2, event3);

    handler.handleEvents(events);

    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, event1);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, event1);
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, event2);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, event2);
    verify(historicEventActionExecutor).executeAction(auditTrailLogAction, event3);
    verify(historicEventActionExecutor).executeAction(workflowEventVariableAction, event3);
  }
}
