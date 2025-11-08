package com.symphony.bdk.workflow.engine.handler.audit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
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

@ContextConfiguration(classes = {AuditTrailLogAction.class})
@ExtendWith(SpringExtension.class)
class AuditTrailLogActionDiffblueTest {
  @Autowired
  private AuditTrailLogAction auditTrailLogAction;

  /**
   * Test {@link AuditTrailLogAction#execute(DelegateExecution, String)} with {@code execution}, {@code activityType}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then calls {@link ExecutionEntity#getCurrentActivityId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AuditTrailLogAction#execute(DelegateExecution, String)}
   */
  @Test
  @DisplayName("Test execute(DelegateExecution, String) with 'execution', 'activityType'; given '42'; then calls getCurrentActivityId()")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link AuditTrailLogAction#deployed(Deployment)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then calls {@link DeploymentEntity#getDeployedArtifacts()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AuditTrailLogAction#deployed(Deployment)}
   */
  @Test
  @DisplayName("Test deployed(Deployment); given '42'; then calls getDeployedArtifacts()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AuditTrailLogAction.deployed(Deployment)"})
  void testDeployed_given42_thenCallsGetDeployedArtifacts() {
    // Arrange
    DeploymentEntity deployment = mock(DeploymentEntity.class);
    when(deployment.getId()).thenReturn("42");
    when(deployment.getName()).thenReturn("Name");
    org.mockito.Mockito.<Map<Class<?>, List>>when(deployment.getDeployedArtifacts()).thenReturn(new HashMap<>());

    // Act
    auditTrailLogAction.deployed(deployment);

    // Assert
    verify(deployment).getDeployedArtifacts();
    verify(deployment).getId();
    verify(deployment).getName();
  }
}
