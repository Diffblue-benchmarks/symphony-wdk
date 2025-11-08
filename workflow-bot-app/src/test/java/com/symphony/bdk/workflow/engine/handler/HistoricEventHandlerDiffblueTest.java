package com.symphony.bdk.workflow.engine.handler;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.engine.handler.variable.WorkflowEventVariableAction;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HistoricEventHandler.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class HistoricEventHandlerDiffblueTest {
  @MockBean
  private AuditTrailLogAction auditTrailLogAction;

  @MockBean
  private HistoricEventActionExecutor historicEventActionExecutor;

  @Autowired
  private HistoricEventHandler historicEventHandler;

  @MockBean
  private WorkflowEventVariableAction workflowEventVariableAction;

  /**
   * Method under test: {@link HistoricEventHandler#handleEvent(HistoryEvent)}
   */
  @Test
  void testHandleEvent() {
    // Arrange
    doNothing().when(historicEventActionExecutor)
        .executeAction(Mockito.<HistoricEventAction>any(), Mockito.<HistoryEvent>any());

    HistoryEvent historyEvent = new HistoryEvent();
    historyEvent.setCaseDefinitionId("42");
    historyEvent.setCaseDefinitionKey("Case Definition Key");
    historyEvent.setCaseDefinitionName("Case Definition Name");
    historyEvent.setCaseExecutionId("42");
    historyEvent.setCaseInstanceId("42");
    historyEvent.setEventType("Event Type");
    historyEvent.setExecutionId("42");
    historyEvent.setId("42");
    historyEvent.setProcessDefinitionId("42");
    historyEvent.setProcessDefinitionKey("Process Definition Key");
    historyEvent.setProcessDefinitionName("Process Definition Name");
    historyEvent.setProcessDefinitionVersion(1);
    historyEvent.setProcessInstanceId("42");
    historyEvent.setRemovalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historyEvent.setRootProcessInstanceId("42");
    historyEvent.setSequenceCounter(3L);

    // Act
    historicEventHandler.handleEvent(historyEvent);

    // Assert that nothing has changed
    verify(historicEventActionExecutor, atLeast(1)).executeAction(Mockito.<HistoricEventAction>any(),
        isA(HistoryEvent.class));
  }

  /**
   * Method under test: {@link HistoricEventHandler#handleEvents(List)}
   */
  @Test
  void testHandleEvents() {
    // Arrange
    doNothing().when(historicEventActionExecutor)
        .executeAction(Mockito.<HistoricEventAction>any(), Mockito.<HistoryEvent>any());

    HistoryEvent historyEvent = new HistoryEvent();
    historyEvent.setCaseDefinitionId("42");
    historyEvent.setCaseDefinitionKey("Case Definition Key");
    historyEvent.setCaseDefinitionName("Case Definition Name");
    historyEvent.setCaseExecutionId("42");
    historyEvent.setCaseInstanceId("42");
    historyEvent.setEventType("Event Type");
    historyEvent.setExecutionId("42");
    historyEvent.setId("42");
    historyEvent.setProcessDefinitionId("42");
    historyEvent.setProcessDefinitionKey("Process Definition Key");
    historyEvent.setProcessDefinitionName("Process Definition Name");
    historyEvent.setProcessDefinitionVersion(1);
    historyEvent.setProcessInstanceId("42");
    historyEvent.setRemovalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historyEvent.setRootProcessInstanceId("42");
    historyEvent.setSequenceCounter(3L);

    ArrayList<HistoryEvent> historyEvents = new ArrayList<>();
    historyEvents.add(historyEvent);

    // Act
    historicEventHandler.handleEvents(historyEvents);

    // Assert that nothing has changed
    verify(historicEventActionExecutor, atLeast(1)).executeAction(Mockito.<HistoricEventAction>any(),
        isA(HistoryEvent.class));
  }

  /**
   * Method under test: {@link HistoricEventHandler#handleEvents(List)}
   */
  @Test
  void testHandleEvents2() {
    // Arrange
    doNothing().when(historicEventActionExecutor)
        .executeAction(Mockito.<HistoricEventAction>any(), Mockito.<HistoryEvent>any());

    HistoryEvent historyEvent = new HistoryEvent();
    historyEvent.setCaseDefinitionId("42");
    historyEvent.setCaseDefinitionKey("Case Definition Key");
    historyEvent.setCaseDefinitionName("Case Definition Name");
    historyEvent.setCaseExecutionId("42");
    historyEvent.setCaseInstanceId("42");
    historyEvent.setEventType("Event Type");
    historyEvent.setExecutionId("42");
    historyEvent.setId("42");
    historyEvent.setProcessDefinitionId("42");
    historyEvent.setProcessDefinitionKey("Process Definition Key");
    historyEvent.setProcessDefinitionName("Process Definition Name");
    historyEvent.setProcessDefinitionVersion(1);
    historyEvent.setProcessInstanceId("42");
    historyEvent.setRemovalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historyEvent.setRootProcessInstanceId("42");
    historyEvent.setSequenceCounter(3L);

    HistoryEvent historyEvent2 = new HistoryEvent();
    historyEvent2.setCaseDefinitionId("Case Definition Id");
    historyEvent2.setCaseDefinitionKey("42");
    historyEvent2.setCaseDefinitionName("42");
    historyEvent2.setCaseExecutionId("Case Execution Id");
    historyEvent2.setCaseInstanceId("Case Instance Id");
    historyEvent2.setEventType("42");
    historyEvent2.setExecutionId("Execution Id");
    historyEvent2.setId("Id");
    historyEvent2.setProcessDefinitionId("Process Definition Id");
    historyEvent2.setProcessDefinitionKey("42");
    historyEvent2.setProcessDefinitionName("42");
    historyEvent2.setProcessDefinitionVersion(0);
    historyEvent2.setProcessInstanceId("Process Instance Id");
    historyEvent2.setRemovalTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historyEvent2.setRootProcessInstanceId("Root Process Instance Id");
    historyEvent2.setSequenceCounter(1L);

    ArrayList<HistoryEvent> historyEvents = new ArrayList<>();
    historyEvents.add(historyEvent2);
    historyEvents.add(historyEvent);

    // Act
    historicEventHandler.handleEvents(historyEvents);

    // Assert that nothing has changed
    verify(historicEventActionExecutor, atLeast(1)).executeAction(Mockito.<HistoricEventAction>any(),
        Mockito.<HistoryEvent>any());
  }
}
