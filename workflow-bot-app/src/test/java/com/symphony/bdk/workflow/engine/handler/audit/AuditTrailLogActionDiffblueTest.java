package com.symphony.bdk.workflow.engine.handler.audit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessApplicationDeploymentImpl;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.camunda.bpm.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuditTrailLogAction.class})
@ExtendWith(SpringExtension.class)
class AuditTrailLogActionDiffblueTest {
  @Autowired
  private AuditTrailLogAction auditTrailLogAction;

  /**
   * Method under test:
   * {@link AuditTrailLogAction#execute(DelegateExecution, String)}
   */
  @Test
  void testExecute() {
    // Arrange
    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getCurrentActivityName()).thenReturn("Current Activity Name");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessDefinition()).thenReturn(new ProcessDefinitionEntity());
    doNothing().when(execution).setProcessDefinition(Mockito.<ProcessDefinitionImpl>any());
    execution.setProcessDefinition(mock(ProcessDefinitionImpl.class));

    // Act
    auditTrailLogAction.execute(execution, "Activity Type");

    // Assert
    verify(execution).getCurrentActivityId();
    verify(execution).getCurrentActivityName();
    verify(execution).getProcessDefinition();
    verify(execution).getProcessDefinitionId();
    verify(execution).setProcessDefinition(isA(ProcessDefinitionImpl.class));
  }

  /**
   * Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  void testExecute2() {
    // Arrange
    HistoricActivityInstanceEventEntity historyEvent = mock(HistoricActivityInstanceEventEntity.class);
    when(historyEvent.getDurationInMillis()).thenReturn(1L);
    when(historyEvent.getActivityId()).thenReturn("42");
    when(historyEvent.getActivityName()).thenReturn("Activity Name");
    when(historyEvent.getEventType()).thenReturn("Event Type");
    when(historyEvent.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(historyEvent.getProcessInstanceId()).thenReturn("42");
    doNothing().when(historyEvent).setRootProcessInstanceId(Mockito.<String>any());
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
    auditTrailLogAction.execute(historyEvent);

    // Assert
    verify(historyEvent).getActivityId();
    verify(historyEvent).getActivityName();
    verify(historyEvent).setRootProcessInstanceId(eq("42"));
    verify(historyEvent, atLeast(1)).getDurationInMillis();
    verify(historyEvent).getEventType();
    verify(historyEvent).getProcessDefinitionKey();
    verify(historyEvent).getProcessInstanceId();
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
    verify(historyEvent).setSequenceCounter(eq(3L));
  }

  /**
   * Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  void testExecute3() {
    // Arrange
    HistoricActivityInstanceEventEntity historyEvent = mock(HistoricActivityInstanceEventEntity.class);
    when(historyEvent.getDurationInMillis()).thenReturn(null);
    when(historyEvent.getActivityId()).thenReturn("42");
    when(historyEvent.getActivityName()).thenReturn("Activity Name");
    when(historyEvent.getEventType()).thenReturn("Event Type");
    when(historyEvent.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(historyEvent.getProcessInstanceId()).thenReturn("42");
    doNothing().when(historyEvent).setRootProcessInstanceId(Mockito.<String>any());
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
    auditTrailLogAction.execute(historyEvent);

    // Assert
    verify(historyEvent).getActivityId();
    verify(historyEvent).getActivityName();
    verify(historyEvent).setRootProcessInstanceId(eq("42"));
    verify(historyEvent).getDurationInMillis();
    verify(historyEvent).getEventType();
    verify(historyEvent).getProcessDefinitionKey();
    verify(historyEvent).getProcessInstanceId();
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
    verify(historyEvent).setSequenceCounter(eq(3L));
  }

  /**
   * Method under test: {@link AuditTrailLogAction#deployed(Deployment)}
   */
  @Test
  void testDeployed() {
    // Arrange
    DeploymentEntity deployment = mock(DeploymentEntity.class);
    when(deployment.getId()).thenReturn("42");
    when(deployment.getName()).thenReturn("Name");
    Mockito.<Map<Class<?>, List>>when(deployment.getDeployedArtifacts()).thenReturn(new HashMap<>());

    // Act
    auditTrailLogAction.deployed(deployment);

    // Assert
    verify(deployment).getDeployedArtifacts();
    verify(deployment).getId();
    verify(deployment).getName();
  }

  /**
   * Method under test: {@link AuditTrailLogAction#undeployed(Deployment)}
   */
  @Test
  void testUndeployed() {
    // Arrange
    ProcessApplicationDeploymentImpl deployment = mock(ProcessApplicationDeploymentImpl.class);
    when(deployment.getId()).thenReturn("42");
    when(deployment.getName()).thenReturn("Name");

    // Act
    auditTrailLogAction.undeployed(deployment);

    // Assert
    verify(deployment).getId();
    verify(deployment).getName();
  }
}
