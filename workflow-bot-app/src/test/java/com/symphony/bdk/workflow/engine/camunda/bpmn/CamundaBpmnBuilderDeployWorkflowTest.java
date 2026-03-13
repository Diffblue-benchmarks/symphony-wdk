package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaBpmnBuilderDeployWorkflowTest {

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private WorkflowNodeBpmnBuilderRegistry builderFactory;

  @Mock
  private SessionService sessionService;

  @Mock
  private WorkflowDirectedGraphService directedGraphService;

  @Mock
  private DeploymentBuilder deploymentBuilder;

  @Mock
  private Deployment deployment;

  private CamundaBpmnBuilder camundaBpmnBuilder;

  @BeforeEach
  void setUp() {
    camundaBpmnBuilder = new CamundaBpmnBuilder(repositoryService, builderFactory, sessionService,
        directedGraphService);
  }

  @Test
  void shouldDeployWorkflowWithAllComponents() {
    // Given
    String workflowId = "test-workflow";
    Workflow workflow = new Workflow();
    workflow.setId(workflowId);
    workflow.setActivities(java.util.Collections.emptyList());

    BpmnModelInstance bpmnModelInstance = mock(BpmnModelInstance.class);
    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);

    CamundaTranslatedWorkflowContext context = new CamundaTranslatedWorkflowContext(workflow, directedGraph,
        bpmnModelInstance);

    when(repositoryService.createDeployment()).thenReturn(deploymentBuilder);
    when(deploymentBuilder.name(workflowId)).thenReturn(deploymentBuilder);
    when(deploymentBuilder.addModelInstance(workflowId + ".bpmn", bpmnModelInstance)).thenReturn(deploymentBuilder);
    when(deploymentBuilder.deploy()).thenReturn(deployment);

    // When
    Deployment result = camundaBpmnBuilder.deployWorkflow(context);

    // Then
    verify(repositoryService).createDeployment();
    verify(deploymentBuilder).name(eq(workflowId));
    verify(deploymentBuilder).addModelInstance(eq(workflowId + ".bpmn"), eq(bpmnModelInstance));
    verify(directedGraphService).putDirectedGraph(eq(directedGraph));
    verify(deploymentBuilder).deploy();
    assertThat(result).isEqualTo(deployment);
  }

  @Test
  void shouldDeployWorkflowWithDifferentWorkflowId() {
    // Given
    String workflowId = "another-workflow-id";
    Workflow workflow = new Workflow();
    workflow.setId(workflowId);
    workflow.setActivities(java.util.Collections.emptyList());

    BpmnModelInstance bpmnModelInstance = mock(BpmnModelInstance.class);
    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);

    CamundaTranslatedWorkflowContext context = new CamundaTranslatedWorkflowContext(workflow, directedGraph,
        bpmnModelInstance);

    when(repositoryService.createDeployment()).thenReturn(deploymentBuilder);
    when(deploymentBuilder.name(workflowId)).thenReturn(deploymentBuilder);
    when(deploymentBuilder.addModelInstance(workflowId + ".bpmn", bpmnModelInstance)).thenReturn(deploymentBuilder);
    when(deploymentBuilder.deploy()).thenReturn(deployment);

    // When
    Deployment result = camundaBpmnBuilder.deployWorkflow(context);

    // Then
    verify(repositoryService).createDeployment();
    verify(deploymentBuilder).name(eq(workflowId));
    verify(deploymentBuilder).addModelInstance(eq(workflowId + ".bpmn"), eq(bpmnModelInstance));
    verify(directedGraphService).putDirectedGraph(eq(directedGraph));
    verify(deploymentBuilder).deploy();
    assertThat(result).isEqualTo(deployment);
  }

  @Test
  void shouldPutDirectedGraphBeforeDeployment() {
    // Given
    String workflowId = "workflow-with-graph";
    Workflow workflow = new Workflow();
    workflow.setId(workflowId);
    workflow.setActivities(java.util.Collections.emptyList());

    BpmnModelInstance bpmnModelInstance = mock(BpmnModelInstance.class);
    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);

    CamundaTranslatedWorkflowContext context = new CamundaTranslatedWorkflowContext(workflow, directedGraph,
        bpmnModelInstance);

    when(repositoryService.createDeployment()).thenReturn(deploymentBuilder);
    when(deploymentBuilder.name(any(String.class))).thenReturn(deploymentBuilder);
    when(deploymentBuilder.addModelInstance(any(String.class), any(BpmnModelInstance.class))).thenReturn(deploymentBuilder);
    when(deploymentBuilder.deploy()).thenReturn(deployment);

    // When
    camundaBpmnBuilder.deployWorkflow(context);

    // Then
    verify(directedGraphService).putDirectedGraph(eq(directedGraph));
  }

  @Test
  void shouldReturnDeploymentFromDeployMethod() {
    // Given
    Workflow workflow = new Workflow();
    workflow.setId("test-id");
    workflow.setActivities(java.util.Collections.emptyList());

    BpmnModelInstance bpmnModelInstance = mock(BpmnModelInstance.class);
    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);

    CamundaTranslatedWorkflowContext context = new CamundaTranslatedWorkflowContext(workflow, directedGraph,
        bpmnModelInstance);

    when(repositoryService.createDeployment()).thenReturn(deploymentBuilder);
    when(deploymentBuilder.name(any(String.class))).thenReturn(deploymentBuilder);
    when(deploymentBuilder.addModelInstance(any(String.class), any(BpmnModelInstance.class))).thenReturn(deploymentBuilder);
    when(deploymentBuilder.deploy()).thenReturn(deployment);

    // When
    Deployment result = camundaBpmnBuilder.deployWorkflow(context);

    // Then
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(deployment);
  }

  @Test
  void shouldCreateDeploymentWithCorrectBpmnFileName() {
    // Given
    String workflowId = "my-workflow";
    String expectedFileName = workflowId + ".bpmn";
    Workflow workflow = new Workflow();
    workflow.setId(workflowId);
    workflow.setActivities(java.util.Collections.emptyList());

    BpmnModelInstance bpmnModelInstance = mock(BpmnModelInstance.class);
    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);

    CamundaTranslatedWorkflowContext context = new CamundaTranslatedWorkflowContext(workflow, directedGraph,
        bpmnModelInstance);

    when(repositoryService.createDeployment()).thenReturn(deploymentBuilder);
    when(deploymentBuilder.name(workflowId)).thenReturn(deploymentBuilder);
    when(deploymentBuilder.addModelInstance(expectedFileName, bpmnModelInstance)).thenReturn(deploymentBuilder);
    when(deploymentBuilder.deploy()).thenReturn(deployment);

    // When
    camundaBpmnBuilder.deployWorkflow(context);

    // Then
    verify(deploymentBuilder).addModelInstance(eq(expectedFileName), eq(bpmnModelInstance));
  }
}
