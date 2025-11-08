package com.symphony.bdk.workflow.engine.handler;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HistoricEventActionExecutor.class})
@ExtendWith(SpringExtension.class)
class HistoricEventActionExecutorDiffblueTest {
  @Autowired
  private HistoricEventActionExecutor historicEventActionExecutor;

  /**
   * Test {@link HistoricEventActionExecutor#executeAction(HistoricEventAction, HistoryEvent)}.
   * <p>
   * Method under test: {@link HistoricEventActionExecutor#executeAction(HistoricEventAction, HistoryEvent)}
   */
  @Test
  @DisplayName("Test executeAction(HistoricEventAction, HistoryEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HistoricEventActionExecutor.executeAction(HistoricEventAction, HistoryEvent)"})
  void testExecuteAction() {
    // Arrange
    HistoricEventAction historicEventAction = mock(HistoricEventAction.class);
    doNothing().when(historicEventAction).execute(Mockito.<HistoryEvent>any());

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
    historicEventActionExecutor.executeAction(historicEventAction, historyEvent);

    // Assert
    verify(historicEventAction).execute(isA(HistoryEvent.class));
  }
}
