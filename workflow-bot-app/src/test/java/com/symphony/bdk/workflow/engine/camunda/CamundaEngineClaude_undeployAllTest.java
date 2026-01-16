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

class CamundaEngineClaude_undeployAllTest {

  @Test
  void undeployAll_withSingleDeployment_shouldUndeployIt() {
    // Given: A single deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-1");
    when(deployment.getName()).thenReturn("workflow-1");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.singletonList(deployment));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called
    engine.undeployAll();

    // Then: Should delete the deployment and log it
    verify(repositoryService).deleteDeployment("deployment-1", true);
    verify(auditTrailLogger).undeployed(deployment);
  }

  @Test
  void undeployAll_withMultipleDeployments_shouldUndeployAll() {
    // Given: Multiple deployments exist
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("deployment-1");
    when(deployment1.getName()).thenReturn("workflow-1");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("deployment-2");
    when(deployment2.getName()).thenReturn("workflow-2");

    Deployment deployment3 = mock(Deployment.class);
    when(deployment3.getId()).thenReturn("deployment-3");
    when(deployment3.getName()).thenReturn("workflow-3");

    List<Deployment> deployments = Arrays.asList(deployment1, deployment2, deployment3);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(deployments);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called
    engine.undeployAll();

    // Then: Should delete all deployments and log each one
    verify(repositoryService).deleteDeployment("deployment-1", true);
    verify(repositoryService).deleteDeployment("deployment-2", true);
    verify(repositoryService).deleteDeployment("deployment-3", true);
    verify(auditTrailLogger).undeployed(deployment1);
    verify(auditTrailLogger).undeployed(deployment2);
    verify(auditTrailLogger).undeployed(deployment3);
  }

  @Test
  void undeployAll_withNoDeployments_shouldNotAttemptUndeploy() {
    // Given: No deployments exist
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.emptyList());

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called with no deployments
    engine.undeployAll();

    // Then: Should not attempt to delete any deployment or log
    verify(repositoryService, never()).deleteDeployment(anyString(), anyBoolean());
    verify(auditTrailLogger, never()).undeployed(org.mockito.ArgumentMatchers.any(Deployment.class));
  }

  @Test
  void undeployAll_shouldPassTrueToCascadeParameter() {
    // Given: A deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-cascade");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.singletonList(deployment));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called
    engine.undeployAll();

    // Then: Should delete deployment with cascade=true
    verify(repositoryService).deleteDeployment("deployment-cascade", true);
  }

  @Test
  void undeployAll_calledMultipleTimes_shouldQueryAndUndeployEachTime() {
    // Given: A deployment exists
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-repeat");
    when(deployment.getName()).thenReturn("test-workflow");

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(Collections.singletonList(deployment));

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called multiple times
    engine.undeployAll();
    engine.undeployAll();

    // Then: Should query and undeploy each time
    verify(repositoryService, times(2)).createDeploymentQuery();
    verify(deploymentQuery, times(2)).list();
    verify(repositoryService, times(2)).deleteDeployment("deployment-repeat", true);
    verify(auditTrailLogger, times(2)).undeployed(deployment);
  }

  @Test
  void undeployAll_shouldNotFilterByDeploymentId() {
    // Given: Multiple deployments with different IDs exist
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("id-1");
    when(deployment1.getName()).thenReturn("workflow-1");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("id-2");
    when(deployment2.getName()).thenReturn("workflow-2");

    List<Deployment> deployments = Arrays.asList(deployment1, deployment2);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(deployments);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called
    engine.undeployAll();

    // Then: Should not filter by deploymentId
    verify(deploymentQuery, never()).deploymentId(anyString());
    verify(repositoryService).deleteDeployment("id-1", true);
    verify(repositoryService).deleteDeployment("id-2", true);
  }

  @Test
  void undeployAll_shouldNotFilterByDeploymentName() {
    // Given: Multiple deployments with different names exist
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("id-1");
    when(deployment1.getName()).thenReturn("workflow-alpha");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("id-2");
    when(deployment2.getName()).thenReturn("workflow-beta");

    List<Deployment> deployments = Arrays.asList(deployment1, deployment2);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(deployments);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called
    engine.undeployAll();

    // Then: Should not filter by deploymentName
    verify(deploymentQuery, never()).deploymentName(anyString());
    verify(repositoryService).deleteDeployment("id-1", true);
    verify(repositoryService).deleteDeployment("id-2", true);
  }

  @Test
  void undeployAll_withManyDeployments_shouldUndeployAllInOrder() {
    // Given: Many deployments exist
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("deploy-1");
    when(deployment1.getName()).thenReturn("workflow-1");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("deploy-2");
    when(deployment2.getName()).thenReturn("workflow-2");

    Deployment deployment3 = mock(Deployment.class);
    when(deployment3.getId()).thenReturn("deploy-3");
    when(deployment3.getName()).thenReturn("workflow-3");

    Deployment deployment4 = mock(Deployment.class);
    when(deployment4.getId()).thenReturn("deploy-4");
    when(deployment4.getName()).thenReturn("workflow-4");

    Deployment deployment5 = mock(Deployment.class);
    when(deployment5.getId()).thenReturn("deploy-5");
    when(deployment5.getName()).thenReturn("workflow-5");

    List<Deployment> deployments = Arrays.asList(deployment1, deployment2, deployment3, deployment4, deployment5);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(deployments);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called
    engine.undeployAll();

    // Then: Should undeploy all deployments
    verify(repositoryService).deleteDeployment("deploy-1", true);
    verify(repositoryService).deleteDeployment("deploy-2", true);
    verify(repositoryService).deleteDeployment("deploy-3", true);
    verify(repositoryService).deleteDeployment("deploy-4", true);
    verify(repositoryService).deleteDeployment("deploy-5", true);
    verify(auditTrailLogger, times(5)).undeployed(org.mockito.ArgumentMatchers.any(Deployment.class));
  }

  @Test
  void undeployAll_shouldUseListNotSingleResult() {
    // Given: Multiple deployments exist
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("deploy-1");
    when(deployment1.getName()).thenReturn("workflow-1");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("deploy-2");
    when(deployment2.getName()).thenReturn("workflow-2");

    List<Deployment> deployments = Arrays.asList(deployment1, deployment2);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(deployments);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called
    engine.undeployAll();

    // Then: Should use list(), not singleResult()
    verify(deploymentQuery).list();
    verify(deploymentQuery, never()).singleResult();
  }

  @Test
  void undeployAll_withDeploymentsHavingSameWorkflowName_shouldUndeployAllInstances() {
    // Given: Multiple deployments with the same workflow name (different versions)
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    DeploymentQuery deploymentQuery = mock(DeploymentQuery.class);

    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("deployment-v1");
    when(deployment1.getName()).thenReturn("same-workflow");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("deployment-v2");
    when(deployment2.getName()).thenReturn("same-workflow");

    Deployment deployment3 = mock(Deployment.class);
    when(deployment3.getId()).thenReturn("deployment-v3");
    when(deployment3.getName()).thenReturn("same-workflow");

    List<Deployment> deployments = Arrays.asList(deployment1, deployment2, deployment3);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(deployments);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: undeployAll is called
    engine.undeployAll();

    // Then: Should undeploy all instances even with the same workflow name
    verify(repositoryService).deleteDeployment("deployment-v1", true);
    verify(repositoryService).deleteDeployment("deployment-v2", true);
    verify(repositoryService).deleteDeployment("deployment-v3", true);
    verify(auditTrailLogger).undeployed(deployment1);
    verify(auditTrailLogger).undeployed(deployment2);
    verify(auditTrailLogger).undeployed(deployment3);
  }
}
