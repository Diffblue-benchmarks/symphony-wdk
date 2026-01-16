package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentQuery;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CamundaEngineClaude_undeployByDeploymentIdTest {

  @Test
  void undeployByDeploymentId_withValidDeploymentId_shouldUndeployIt() {
    // Given: A valid deployment ID that exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-123");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId("deployment-123")).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deployment);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByDeploymentId is called
    engine.undeployByDeploymentId("deployment-123");

    // Then: Should delete the deployment and log it
    verify(repositoryService).deleteDeployment("deployment-123", true);
    verify(auditTrailLogger).undeployed(deployment);
  }

  @Test
  void undeployByDeploymentId_withNonExistentDeploymentId_shouldThrowNullPointerException() {
    // Given: A deployment ID that doesn't exist (singleResult returns null)
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId("non-existent-id")).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(null);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When/Then: Should throw NullPointerException when trying to call stop on null
    assertThatThrownBy(() -> engine.undeployByDeploymentId("non-existent-id"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void undeployByDeploymentId_shouldPassTrueToCascadeParameter() {
    // Given: A deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-456");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId("deployment-456")).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deployment);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByDeploymentId is called
    engine.undeployByDeploymentId("deployment-456");

    // Then: Should delete deployment with cascade=true
    verify(repositoryService).deleteDeployment("deployment-456", true);
  }

  @Test
  void undeployByDeploymentId_calledMultipleTimes_shouldQueryAndUndeployEachTime() {
    // Given: A deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-789");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId("deployment-789")).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deployment);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByDeploymentId is called multiple times with the same ID
    engine.undeployByDeploymentId("deployment-789");
    engine.undeployByDeploymentId("deployment-789");

    // Then: Should query and undeploy each time
    verify(repositoryService, times(2)).createDeploymentQuery();
    verify(deploymentQuery, times(2)).deploymentId("deployment-789");
    verify(deploymentQuery, times(2)).singleResult();
    verify(repositoryService, times(2)).deleteDeployment("deployment-789", true);
    verify(auditTrailLogger, times(2)).undeployed(deployment);
  }

  @Test
  void undeployByDeploymentId_withDifferentDeploymentIds_shouldUndeployEachSeparately() {
    // Given: Multiple different deployments
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery1 = mock(DeploymentQuery.class);
    DeploymentQuery deploymentQuery2 = mock(DeploymentQuery.class);

    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("deployment-1");
    when(deployment1.getName()).thenReturn("workflow-1");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("deployment-2");
    when(deployment2.getName()).thenReturn("workflow-2");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery1, deploymentQuery2);
    when(deploymentQuery1.deploymentId("deployment-1")).thenReturn(deploymentQuery1);
    when(deploymentQuery1.singleResult()).thenReturn(deployment1);
    when(deploymentQuery2.deploymentId("deployment-2")).thenReturn(deploymentQuery2);
    when(deploymentQuery2.singleResult()).thenReturn(deployment2);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByDeploymentId is called for different deployment IDs
    engine.undeployByDeploymentId("deployment-1");
    engine.undeployByDeploymentId("deployment-2");

    // Then: Should undeploy each deployment separately
    verify(repositoryService).deleteDeployment("deployment-1", true);
    verify(repositoryService).deleteDeployment("deployment-2", true);
    verify(auditTrailLogger).undeployed(deployment1);
    verify(auditTrailLogger).undeployed(deployment2);
  }

  @Test
  void undeployByDeploymentId_withEmptyDeploymentId_shouldQueryWithEmptyString() {
    // Given: An empty deployment ID is provided
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId("")).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(null);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When/Then: Should query with empty string and throw NPE when result is null
    assertThatThrownBy(() -> engine.undeployByDeploymentId(""))
        .isInstanceOf(NullPointerException.class);

    verify(repositoryService).createDeploymentQuery();
    verify(deploymentQuery).deploymentId("");
    verify(deploymentQuery).singleResult();
  }

  @Test
  void undeployByDeploymentId_withSpecialCharactersInId_shouldHandleCorrectly() {
    // Given: A deployment ID with special characters
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    String specialId = "deployment-id-with-special-chars_@#$%";
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn(specialId);
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId(specialId)).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deployment);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByDeploymentId is called with special characters
    engine.undeployByDeploymentId(specialId);

    // Then: Should handle the deployment ID correctly
    verify(repositoryService).deleteDeployment(specialId, true);
    verify(auditTrailLogger).undeployed(deployment);
  }

  @Test
  void undeployByDeploymentId_withLongDeploymentId_shouldHandleCorrectly() {
    // Given: A very long deployment ID
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    String longId = "very-long-deployment-id-with-many-characters-that-goes-on-and-on-and-on-and-on";
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn(longId);
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId(longId)).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deployment);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByDeploymentId is called with a long ID
    engine.undeployByDeploymentId(longId);

    // Then: Should handle the long deployment ID correctly
    verify(repositoryService).deleteDeployment(longId, true);
    verify(auditTrailLogger).undeployed(deployment);
  }

  @Test
  void undeployByDeploymentId_shouldQueryByDeploymentIdNotByName() {
    // Given: A deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-id-999");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId("deployment-id-999")).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deployment);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByDeploymentId is called
    engine.undeployByDeploymentId("deployment-id-999");

    // Then: Should query by deploymentId, not deploymentName
    verify(deploymentQuery).deploymentId("deployment-id-999");
    verify(deploymentQuery, never()).deploymentName(org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void undeployByDeploymentId_shouldUseSingleResultNotList() {
    // Given: A deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-single");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId("deployment-single")).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deployment);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByDeploymentId is called
    engine.undeployByDeploymentId("deployment-single");

    // Then: Should use singleResult(), not list()
    verify(deploymentQuery).singleResult();
    verify(deploymentQuery, never()).list();
  }
}
