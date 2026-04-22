package com.symphony.bdk.workflow.engine.handler.audit;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricJobLogEvent;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricVariableUpdateEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.Deployment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = {AuditTrailLogAction.class})
@ExtendWith(SpringExtension.class)
class AuditTrailLogActionDiffblueTest {
  @Autowired private AuditTrailLogAction auditTrailLogAction;

  /**
   * Test {@link AuditTrailLogAction#execute(DelegateExecution, String)} with {@code execution},
   * {@code activityType}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then calls {@link ExecutionEntity#getCurrentActivityId()}.
   * </ul>
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(DelegateExecution, String)}
   */
  @Test
  @DisplayName(
      "Test execute(DelegateExecution, String) with 'execution', 'activityType'; given '42'; then calls getCurrentActivityId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(DelegateExecution, String)"})
  void testExecuteWithExecutionActivityType_given42_thenCallsGetCurrentActivityId() {
    // Arrange
    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getCurrentActivityName()).thenReturn("Current Activity Name");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessDefinition()).thenReturn(new ProcessDefinitionEntity());

    // Act
    auditTrailLogAction.execute(execution, "Activity Type");

    // Assert
    verify(execution).getCurrentActivityId();
    verify(execution).getCurrentActivityName();
    verify(execution).getProcessDefinition();
    verify(execution).getProcessDefinitionId();
  }

  /**
   * Test {@link AuditTrailLogAction#execute(HistoryEvent)} with a {@link HistoricJobLogEvent}.
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName("Test execute(HistoryEvent) with HistoricJobLogEvent; then logs job event")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(HistoryEvent)"})
  void testExecuteWithHistoryEvent_givenHistoricJobLogEvent_thenLogsJobEvent() {
    // Arrange
    HistoricJobLogEvent event = mock(HistoricJobLogEvent.class);
    when(event.getJobId()).thenReturn("job-1");
    when(event.getJobDefinitionType()).thenReturn("ServiceTask");
    when(event.getProcessInstanceId()).thenReturn("proc-1");
    when(event.getProcessDefinitionKey()).thenReturn("key-1");
    when(event.getActivityId()).thenReturn("activity-1");

    // Act
    auditTrailLogAction.execute(event);

    // Assert
    verify(event).getJobId();
    verify(event).getProcessDefinitionKey();
  }

  /**
   * Test {@link AuditTrailLogAction#execute(HistoryEvent)} with a
   * {@link HistoricProcessInstanceEventEntity} when duration is null.
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName(
      "Test execute(HistoryEvent) with HistoricProcessInstanceEventEntity; given null duration; then logs process event without duration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(HistoryEvent)"})
  void testExecuteWithHistoryEvent_givenHistoricProcessInstanceEvent_andNullDuration() {
    // Arrange
    HistoricProcessInstanceEventEntity event = mock(HistoricProcessInstanceEventEntity.class);
    when(event.getDurationInMillis()).thenReturn(null);
    when(event.getEventType()).thenReturn("start");
    when(event.getProcessInstanceId()).thenReturn("proc-1");
    when(event.getProcessDefinitionKey()).thenReturn("key-1");

    // Act
    auditTrailLogAction.execute(event);

    // Assert
    verify(event, atLeast(1)).getDurationInMillis();
    verify(event).getEventType();
  }

  /**
   * Test {@link AuditTrailLogAction#execute(HistoryEvent)} with a
   * {@link HistoricProcessInstanceEventEntity} when duration is non-null.
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName(
      "Test execute(HistoryEvent) with HistoricProcessInstanceEventEntity; given non-null duration; then logs process event with duration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(HistoryEvent)"})
  void testExecuteWithHistoryEvent_givenHistoricProcessInstanceEvent_andNonNullDuration() {
    // Arrange
    HistoricProcessInstanceEventEntity event = mock(HistoricProcessInstanceEventEntity.class);
    when(event.getDurationInMillis()).thenReturn(1000L);
    when(event.getEventType()).thenReturn("end");
    when(event.getProcessInstanceId()).thenReturn("proc-1");
    when(event.getProcessDefinitionKey()).thenReturn("key-1");

    // Act
    auditTrailLogAction.execute(event);

    // Assert
    verify(event, atLeast(1)).getDurationInMillis();
    verify(event).getEventType();
  }

  /**
   * Test {@link AuditTrailLogAction#execute(HistoryEvent)} with a
   * {@link HistoricActivityInstanceEventEntity} when duration is null.
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName(
      "Test execute(HistoryEvent) with HistoricActivityInstanceEventEntity; given null duration; then logs activity event without duration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(HistoryEvent)"})
  void testExecuteWithHistoryEvent_givenHistoricActivityInstanceEvent_andNullDuration() {
    // Arrange
    HistoricActivityInstanceEventEntity event = mock(HistoricActivityInstanceEventEntity.class);
    when(event.getDurationInMillis()).thenReturn(null);
    when(event.getEventType()).thenReturn("start");
    when(event.getProcessInstanceId()).thenReturn("proc-1");
    when(event.getProcessDefinitionKey()).thenReturn("key-1");
    when(event.getActivityId()).thenReturn("act-1");
    when(event.getActivityName()).thenReturn("Activity Name");

    // Act
    auditTrailLogAction.execute(event);

    // Assert
    verify(event).getDurationInMillis();
    verify(event).getActivityId();
  }

  /**
   * Test {@link AuditTrailLogAction#execute(HistoryEvent)} with a
   * {@link HistoricActivityInstanceEventEntity} when duration is non-null.
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName(
      "Test execute(HistoryEvent) with HistoricActivityInstanceEventEntity; given non-null duration; then logs activity event with duration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(HistoryEvent)"})
  void testExecuteWithHistoryEvent_givenHistoricActivityInstanceEvent_andNonNullDuration() {
    // Arrange
    HistoricActivityInstanceEventEntity event = mock(HistoricActivityInstanceEventEntity.class);
    when(event.getDurationInMillis()).thenReturn(500L);
    when(event.getEventType()).thenReturn("end");
    when(event.getProcessInstanceId()).thenReturn("proc-1");
    when(event.getProcessDefinitionKey()).thenReturn("key-1");
    when(event.getActivityId()).thenReturn("act-1");
    when(event.getActivityName()).thenReturn("Activity Name");

    // Act
    auditTrailLogAction.execute(event);

    // Assert
    verify(event, atLeast(1)).getDurationInMillis();
    verify(event).getActivityId();
  }

  /**
   * Test {@link AuditTrailLogAction#execute(HistoryEvent)} with a
   * {@link HistoricVariableUpdateEventEntity} when variable is INITIATOR and longValue is set.
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName(
      "Test execute(HistoryEvent) with HistoricVariableUpdateEventEntity; given INITIATOR variable with longValue; then logs initiator")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(HistoryEvent)"})
  void testExecuteWithHistoryEvent_givenHistoricVariableUpdateEvent_andInitiatorVariable() {
    // Arrange
    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn(ActivityExecutorContext.INITIATOR);
    when(event.getLongValue()).thenReturn(42L);
    when(event.getProcessInstanceId()).thenReturn("proc-1");
    when(event.getProcessDefinitionKey()).thenReturn("key-1");

    // Act
    auditTrailLogAction.execute(event);

    // Assert
    verify(event).getVariableName();
    verify(event, atLeast(1)).getLongValue();
  }

  /**
   * Test {@link AuditTrailLogAction#execute(HistoryEvent)} with a
   * {@link HistoricVariableUpdateEventEntity} when variable is not INITIATOR.
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName(
      "Test execute(HistoryEvent) with HistoricVariableUpdateEventEntity; given non-INITIATOR variable; then does not log")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(HistoryEvent)"})
  void testExecuteWithHistoryEvent_givenHistoricVariableUpdateEvent_andNonInitiatorVariable() {
    // Arrange
    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("someOtherVariable");

    // Act
    auditTrailLogAction.execute(event);

    // Assert
    verify(event).getVariableName();
  }

  /**
   * Test {@link AuditTrailLogAction#execute(HistoryEvent)} with an unknown {@link HistoryEvent}
   * type; then trace-logs the event.
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(HistoryEvent)}
   */
  @Test
  @DisplayName(
      "Test execute(HistoryEvent) with unknown HistoryEvent type; then trace-logs the event")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.execute(HistoryEvent)"})
  void testExecuteWithHistoryEvent_givenUnknownHistoryEvent_thenTraceLogs() {
    // Arrange
    HistoryEvent event = mock(HistoryEvent.class);

    // Act
    auditTrailLogAction.execute(event);

    // Assert - no exception thrown, trace logging occurred
  }

  /**
   * Test {@link AuditTrailLogAction#deployed(Deployment)} with a {@link DeploymentEntity} that
   * has a {@link ProcessDefinitionEntity} in its deployed artifacts.
   *
   * <p>Method under test: {@link AuditTrailLogAction#deployed(Deployment)}
   */
  @Test
  @DisplayName(
      "Test deployed(Deployment); given DeploymentEntity with ProcessDefinitionEntity artifact; then logs with process key")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.deployed(Deployment)"})
  @SuppressWarnings({"unchecked", "rawtypes"})
  void testDeployed_givenDeploymentEntityWithProcessDefinitionArtifact() {
    // Arrange
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);
    when(processDefinition.getKey()).thenReturn("my-process-key");

    List artifacts = Collections.singletonList(processDefinition);
    Map<Class<?>, List> artifactsMap = new HashMap<>();
    artifactsMap.put(ProcessDefinitionEntity.class, artifacts);

    DeploymentEntity deployment = mock(DeploymentEntity.class);
    when(deployment.getId()).thenReturn("deploy-1");
    when(deployment.getName()).thenReturn("Deploy Name");
    when(deployment.getDeployedArtifacts()).thenReturn(artifactsMap);

    // Act
    auditTrailLogAction.deployed(deployment);

    // Assert
    verify(deployment).getId();
    verify(deployment).getName();
    verify(deployment).getDeployedArtifacts();
  }

  /**
   * Test {@link AuditTrailLogAction#deployed(Deployment)} with a {@link DeploymentEntity} that
   * has no deployed artifacts for {@link ProcessDefinitionEntity}.
   *
   * <p>Method under test: {@link AuditTrailLogAction#deployed(Deployment)}
   */
  @Test
  @DisplayName(
      "Test deployed(Deployment); given DeploymentEntity with empty artifacts map; then logs with empty process key")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.deployed(Deployment)"})
  @SuppressWarnings("rawtypes")
  void testDeployed_givenDeploymentEntityWithEmptyArtifacts() {
    // Arrange
    DeploymentEntity deployment = mock(DeploymentEntity.class);
    when(deployment.getId()).thenReturn("deploy-2");
    when(deployment.getName()).thenReturn("Deploy Name 2");
    when(deployment.getDeployedArtifacts()).thenReturn(new HashMap<Class<?>, List>());

    // Act
    auditTrailLogAction.deployed(deployment);

    // Assert
    verify(deployment).getId();
    verify(deployment).getDeployedArtifacts();
  }

  /**
   * Test {@link AuditTrailLogAction#deployed(Deployment)} with a non-{@link DeploymentEntity}
   * deployment.
   *
   * <p>Method under test: {@link AuditTrailLogAction#deployed(Deployment)}
   */
  @Test
  @DisplayName(
      "Test deployed(Deployment); given non-DeploymentEntity deployment; then logs with empty process key")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.deployed(Deployment)"})
  void testDeployed_givenNonDeploymentEntity() {
    // Arrange
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deploy-3");
    when(deployment.getName()).thenReturn("Deploy Name 3");

    // Act
    auditTrailLogAction.deployed(deployment);

    // Assert
    verify(deployment).getId();
    verify(deployment).getName();
  }

  /**
   * Test {@link AuditTrailLogAction#undeployed(Deployment)}.
   *
   * <p>Method under test: {@link AuditTrailLogAction#undeployed(Deployment)}
   */
  @Test
  @DisplayName("Test undeployed(Deployment); then logs undeploy event")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AuditTrailLogAction.undeployed(Deployment)"})
  void testUndeployed_thenLogsUndeployEvent() {
    // Arrange
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deploy-4");
    when(deployment.getName()).thenReturn("Deploy Name 4");

    // Act
    auditTrailLogAction.undeployed(deployment);

    // Assert
    verify(deployment).getId();
    verify(deployment).getName();
  }
}
