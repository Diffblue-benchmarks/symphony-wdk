package com.symphony.bdk.workflow.engine.handler.variable;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowEventVariableAction.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WorkflowEventVariableActionDiffblueTest {
  @MockBean
  private RuntimeService runtimeService;

  @MockBean
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  @Autowired
  private WorkflowEventVariableAction workflowEventVariableAction;

  /**
   * Method under test: {@link WorkflowEventVariableAction#execute(HistoryEvent)}
   */
  @Test
  void testExecute() {
    // Arrange
    HistoryEvent historyEvent = mock(HistoryEvent.class);
    doNothing().when(historyEvent).setCaseDefinitionId(Mockito.<String>any());
    doNothing().when(historyEvent).setCaseDefinitionKey(Mockito.<String>any());
    doNothing().when(historyEvent).setCaseDefinitionName(Mockito.<String>any());
    doNothing().when(historyEvent).setCaseExecutionId(Mockito.<String>any());
    doNothing().when(historyEvent).setCaseInstanceId(Mockito.<String>any());
    doNothing().when(historyEvent).setEventType(Mockito.<String>any());
    doNothing().when(historyEvent).setExecutionId(Mockito.<String>any());
    doNothing().when(historyEvent).setId(Mockito.<String>any());
    doNothing().when(historyEvent).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(historyEvent).setProcessDefinitionKey(Mockito.<String>any());
    doNothing().when(historyEvent).setProcessDefinitionName(Mockito.<String>any());
    doNothing().when(historyEvent).setProcessDefinitionVersion(Mockito.<Integer>any());
    doNothing().when(historyEvent).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(historyEvent).setRemovalTime(Mockito.<Date>any());
    doNothing().when(historyEvent).setRootProcessInstanceId(Mockito.<String>any());
    doNothing().when(historyEvent).setSequenceCounter(anyLong());
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
    workflowEventVariableAction.execute(historyEvent);

    // Assert that nothing has changed
    verify(historyEvent).setCaseDefinitionId(eq("42"));
    verify(historyEvent).setCaseDefinitionKey(eq("Case Definition Key"));
    verify(historyEvent).setCaseDefinitionName(eq("Case Definition Name"));
    verify(historyEvent).setCaseExecutionId(eq("42"));
    verify(historyEvent).setCaseInstanceId(eq("42"));
    verify(historyEvent).setEventType(eq("Event Type"));
    verify(historyEvent).setExecutionId(eq("42"));
    verify(historyEvent).setId(eq("42"));
    verify(historyEvent).setProcessDefinitionId(eq("42"));
    verify(historyEvent).setProcessDefinitionKey(eq("Process Definition Key"));
    verify(historyEvent).setProcessDefinitionName(eq("Process Definition Name"));
    verify(historyEvent).setProcessDefinitionVersion(eq(1));
    verify(historyEvent).setProcessInstanceId(eq("42"));
    verify(historyEvent).setRemovalTime(isA(Date.class));
    verify(historyEvent).setRootProcessInstanceId(eq("42"));
    verify(historyEvent).setSequenceCounter(eq(3L));
  }
}
