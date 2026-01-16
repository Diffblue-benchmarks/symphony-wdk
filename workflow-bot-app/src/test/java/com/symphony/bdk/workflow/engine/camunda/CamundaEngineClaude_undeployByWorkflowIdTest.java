package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentQuery;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CamundaEngineClaude_undeployByWorkflowIdTest {

  @Test
  void undeployByWorkflowId_withSingleDeployment_shouldUndeployIt() {
    // Given: A single deployment with the specified workflow name
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-1");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName("test-workflow")).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.singletonList(deployment));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called
    engine.undeployByWorkflowId("test-workflow");

    // Then: Should delete the deployment and log it
    verify(repositoryService).deleteDeployment("deployment-1", true);
    verify(auditTrailLogger).undeployed(deployment);
  }

  @Test
  void undeployByWorkflowId_withMultipleDeployments_shouldUndeployAll() {
    // Given: Multiple deployments with the same workflow name
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("deployment-1");
    when(deployment1.getName()).thenReturn("test-workflow");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("deployment-2");
    when(deployment2.getName()).thenReturn("test-workflow");

    Deployment deployment3 = mock(Deployment.class);
    when(deployment3.getId()).thenReturn("deployment-3");
    when(deployment3.getName()).thenReturn("test-workflow");

    List<Deployment> deployments = Arrays.asList(deployment1, deployment2, deployment3);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName("test-workflow")).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(deployments);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called
    engine.undeployByWorkflowId("test-workflow");

    // Then: Should delete all deployments and log each one
    verify(repositoryService).deleteDeployment("deployment-1", true);
    verify(repositoryService).deleteDeployment("deployment-2", true);
    verify(repositoryService).deleteDeployment("deployment-3", true);
    verify(auditTrailLogger).undeployed(deployment1);
    verify(auditTrailLogger).undeployed(deployment2);
    verify(auditTrailLogger).undeployed(deployment3);
  }

  @Test
  void undeployByWorkflowId_withNoDeployments_shouldNotAttemptUndeploy() {
    // Given: No deployments found for the specified workflow name
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName("non-existent-workflow")).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.emptyList());

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called with a non-existent workflow name
    engine.undeployByWorkflowId("non-existent-workflow");

    // Then: Should not attempt to delete any deployment or log
    verify(repositoryService, never()).deleteDeployment(anyString(), anyBoolean());
    verify(auditTrailLogger, never()).undeployed(org.mockito.ArgumentMatchers.any(Deployment.class));
  }

  @Test
  void undeployByWorkflowId_withEmptyWorkflowName_shouldQueryWithEmptyString() {
    // Given: A deployment with an empty workflow name
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName("")).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.emptyList());

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called with an empty string
    engine.undeployByWorkflowId("");

    // Then: Should query with empty string
    verify(repositoryService).createDeploymentQuery();
    verify(deploymentQuery).deploymentName("");
    verify(deploymentQuery).list();
  }

  @Test
  void undeployByWorkflowId_shouldPassTrueToCascadeParameter() {
    // Given: A deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-1");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName("test-workflow")).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.singletonList(deployment));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called
    engine.undeployByWorkflowId("test-workflow");

    // Then: Should delete deployment with cascade=true
    verify(repositoryService).deleteDeployment("deployment-1", true);
  }

  @Test
  void undeployByWorkflowId_calledMultipleTimes_shouldQueryAndUndeployEachTime() {
    // Given: A deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-1");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName("test-workflow")).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.singletonList(deployment));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called multiple times
    engine.undeployByWorkflowId("test-workflow");
    engine.undeployByWorkflowId("test-workflow");

    // Then: Should query and undeploy each time
    verify(repositoryService, times(2)).createDeploymentQuery();
    verify(deploymentQuery, times(2)).deploymentName("test-workflow");
    verify(deploymentQuery, times(2)).list();
    verify(repositoryService, times(2)).deleteDeployment("deployment-1", true);
    verify(auditTrailLogger, times(2)).undeployed(deployment);
  }

  @Test
  void undeployByWorkflowId_withDifferentWorkflowNames_shouldUndeployEachSeparately() {
    // Given: Deployments for different workflows
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
    when(deploymentQuery1.deploymentName("workflow-1")).thenReturn(deploymentQuery1);
    when(deploymentQuery1.list()).thenReturn(Collections.singletonList(deployment1));
    when(deploymentQuery2.deploymentName("workflow-2")).thenReturn(deploymentQuery2);
    when(deploymentQuery2.list()).thenReturn(Collections.singletonList(deployment2));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called for different workflows
    engine.undeployByWorkflowId("workflow-1");
    engine.undeployByWorkflowId("workflow-2");

    // Then: Should undeploy each workflow separately
    verify(repositoryService).deleteDeployment("deployment-1", true);
    verify(repositoryService).deleteDeployment("deployment-2", true);
    verify(auditTrailLogger).undeployed(deployment1);
    verify(auditTrailLogger).undeployed(deployment2);
  }

  @Test
  void undeployByWorkflowId_withSpecialCharactersInName_shouldHandleCorrectly() {
    // Given: A workflow with special characters in its name
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-1");
    when(deployment.getName()).thenReturn("test-workflow-v1.2.3");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName("test-workflow-v1.2.3")).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.singletonList(deployment));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called with special characters
    engine.undeployByWorkflowId("test-workflow-v1.2.3");

    // Then: Should handle the workflow name correctly
    verify(repositoryService).deleteDeployment("deployment-1", true);
    verify(auditTrailLogger).undeployed(deployment);
  }

  @Test
  void undeployByWorkflowId_withLongWorkflowName_shouldHandleCorrectly() {
    // Given: A workflow with a very long name
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    String longName = "very-long-workflow-name-with-many-characters-that-goes-on-and-on-and-on";
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-1");
    when(deployment.getName()).thenReturn(longName);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName(longName)).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.singletonList(deployment));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployByWorkflowId is called with a long name
    engine.undeployByWorkflowId(longName);

    // Then: Should handle the long workflow name correctly
    verify(repositoryService).deleteDeployment("deployment-1", true);
    verify(auditTrailLogger).undeployed(deployment);
  }
}
