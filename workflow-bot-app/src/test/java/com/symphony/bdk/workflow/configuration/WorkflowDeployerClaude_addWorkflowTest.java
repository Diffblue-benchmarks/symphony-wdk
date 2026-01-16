package com.symphony.bdk.workflow.configuration;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowDeployerClaude_addWorkflowTest {

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @Mock
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  private WorkflowDeployer workflowDeployer;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    workflowDeployer = new WorkflowDeployer(workflowEngine, workflowDirectedGraphService);
  }

  // ==================== addWorkflow Tests ====================

  @Test
  void addWorkflow_withEmptyFile_shouldReturnEarlyWithoutProcessing() throws IOException, ProcessingException {
    // Given: An empty workflow file
    Path emptyFile = tempDir.resolve("empty-workflow.yaml");
    Files.writeString(emptyFile, "");

    // When: Adding the empty workflow
    workflowDeployer.addWorkflow(emptyFile);

    // Then: Should not interact with engine or graph service
    verify(workflowEngine, never()).translate(any(Workflow.class));
    verify(workflowEngine, never()).deploy(any(CamundaTranslatedWorkflowContext.class));
    verify(workflowDirectedGraphService, never()).putDirectedGraph(any(WorkflowDirectedGraph.class));
  }

  @Test
  void addWorkflow_withPublishableWorkflow_shouldDeployAndStoreGraph() throws IOException, ProcessingException {
    // Given: A valid workflow file that should be published
    Path workflowFile = tempDir.resolve("publishable-workflow.yaml");
    String workflowContent = "id: test-workflow\n" +
        "properties:\n" +
        "  publish: true\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      content: Test message\n";
    Files.writeString(workflowFile, workflowContent);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    when(context.getWorkflowDirectedGraph()).thenReturn(graph);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(context);

    // When: Adding the workflow
    workflowDeployer.addWorkflow(workflowFile);

    // Then: Should translate, deploy, and store the directed graph
    verify(workflowEngine, times(1)).translate(any(Workflow.class));
    verify(workflowEngine, times(1)).deploy(context);
    verify(workflowDirectedGraphService, times(1)).putDirectedGraph(graph);
  }

  @Test
  void addWorkflow_withDraftWorkflow_shouldNotDeploy() throws IOException, ProcessingException {
    // Given: A valid workflow file marked as draft (publish: false)
    Path workflowFile = tempDir.resolve("draft-workflow.yaml");
    String workflowContent = "id: draft-workflow\n" +
        "properties:\n" +
        "  publish: false\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      content: Draft message\n";
    Files.writeString(workflowFile, workflowContent);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(context);

    // When: Adding the draft workflow
    workflowDeployer.addWorkflow(workflowFile);

    // Then: Should translate but not deploy or store graph
    verify(workflowEngine, times(1)).translate(any(Workflow.class));
    verify(workflowEngine, never()).deploy(any(CamundaTranslatedWorkflowContext.class));
    verify(workflowDirectedGraphService, never()).putDirectedGraph(any(WorkflowDirectedGraph.class));
  }

  @Test
  void addWorkflow_withWorkflowMissingPublishProperty_shouldDeployByDefault() throws IOException, ProcessingException {
    // Given: A valid workflow file without publish property (defaults to true)
    Path workflowFile = tempDir.resolve("default-publish-workflow.yaml");
    String workflowContent = "id: default-workflow\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      content: Default publish message\n";
    Files.writeString(workflowFile, workflowContent);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    when(context.getWorkflowDirectedGraph()).thenReturn(graph);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(context);

    // When: Adding the workflow
    workflowDeployer.addWorkflow(workflowFile);

    // Then: Should deploy (default behavior when publish is not specified)
    verify(workflowEngine, times(1)).translate(any(Workflow.class));
    verify(workflowEngine, times(1)).deploy(context);
    verify(workflowDirectedGraphService, times(1)).putDirectedGraph(graph);
  }

  @Test
  void addWorkflow_calledMultipleTimes_shouldUpdateDeployedWorkflowsMap() throws IOException, ProcessingException {
    // Given: A valid workflow file
    Path workflowFile = tempDir.resolve("workflow-v1.yaml");
    String workflowContent = "id: versioned-workflow\n" +
        "properties:\n" +
        "  publish: true\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      content: Version 1\n";
    Files.writeString(workflowFile, workflowContent);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    when(context.getWorkflowDirectedGraph()).thenReturn(graph);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(context);

    // When: Adding the workflow twice
    workflowDeployer.addWorkflow(workflowFile);
    workflowDeployer.addWorkflow(workflowFile);

    // Then: Should deploy twice (no check for duplicates in the method)
    verify(workflowEngine, times(2)).translate(any(Workflow.class));
    verify(workflowEngine, times(2)).deploy(context);
    verify(workflowDirectedGraphService, times(2)).putDirectedGraph(graph);
  }

  @Test
  void addWorkflow_withPublishedThenDraft_shouldUndeployPreviousVersion() throws IOException, ProcessingException {
    // Given: A workflow is first published, then updated to draft
    Path workflowFile = tempDir.resolve("changeable-workflow.yaml");

    // First: Publish the workflow
    String publishedContent = "id: changeable-workflow\n" +
        "properties:\n" +
        "  publish: true\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      content: Published version\n";
    Files.writeString(workflowFile, publishedContent);

    CamundaTranslatedWorkflowContext publishedContext = mock(CamundaTranslatedWorkflowContext.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    when(publishedContext.getWorkflowDirectedGraph()).thenReturn(graph);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(publishedContext);

    workflowDeployer.addWorkflow(workflowFile);

    // Then: Change to draft
    String draftContent = "id: changeable-workflow\n" +
        "properties:\n" +
        "  publish: false\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      content: Draft version\n";
    Files.writeString(workflowFile, draftContent);

    CamundaTranslatedWorkflowContext draftContext = mock(CamundaTranslatedWorkflowContext.class);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(draftContext);

    // When: Adding the draft version
    workflowDeployer.addWorkflow(workflowFile);

    // Then: Should undeploy the previous published version
    verify(workflowEngine, times(1)).undeployByWorkflowId("changeable-workflow");
    verify(workflowEngine, times(1)).deploy(publishedContext);
    verify(workflowEngine, never()).deploy(draftContext);
  }

  @Test
  void addWorkflow_withValidYamlFile_shouldNotThrowException() throws IOException, ProcessingException {
    // Given: A valid workflow file
    Path workflowFile = tempDir.resolve("valid-workflow.yaml");
    String workflowContent = "id: valid-workflow\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      content: Valid content\n";
    Files.writeString(workflowFile, workflowContent);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    when(context.getWorkflowDirectedGraph()).thenReturn(graph);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(context);

    // When/Then: Adding the workflow should not throw any exception
    assertThatCode(() -> workflowDeployer.addWorkflow(workflowFile))
        .doesNotThrowAnyException();
  }

  @Test
  void addWorkflow_withMultipleActivities_shouldDeploySuccessfully() throws IOException, ProcessingException {
    // Given: A workflow file with multiple activities
    Path workflowFile = tempDir.resolve("multi-activity-workflow.yaml");
    String workflowContent = "id: multi-activity\n" +
        "properties:\n" +
        "  publish: true\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: msg1\n" +
        "      content: Message 1\n" +
        "  - send-message:\n" +
        "      id: msg2\n" +
        "      content: Message 2\n" +
        "  - send-message:\n" +
        "      id: msg3\n" +
        "      content: Message 3\n";
    Files.writeString(workflowFile, workflowContent);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    when(context.getWorkflowDirectedGraph()).thenReturn(graph);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(context);

    // When: Adding the workflow
    workflowDeployer.addWorkflow(workflowFile);

    // Then: Should deploy successfully
    verify(workflowEngine, times(1)).translate(any(Workflow.class));
    verify(workflowEngine, times(1)).deploy(context);
    verify(workflowDirectedGraphService, times(1)).putDirectedGraph(graph);
  }

  @Test
  void addWorkflow_withComplexWorkflow_shouldTranslateAndDeploy() throws IOException, ProcessingException {
    // Given: A complex workflow with variables and properties
    Path workflowFile = tempDir.resolve("complex-workflow.yaml");
    String workflowContent = "id: complex-workflow\n" +
        "properties:\n" +
        "  publish: true\n" +
        "  description: A complex workflow for testing\n" +
        "variables:\n" +
        "  counter: 0\n" +
        "  message: Hello World\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      content: ${message}\n";
    Files.writeString(workflowFile, workflowContent);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    when(context.getWorkflowDirectedGraph()).thenReturn(graph);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(context);

    // When: Adding the complex workflow
    workflowDeployer.addWorkflow(workflowFile);

    // Then: Should translate and deploy successfully
    verify(workflowEngine, times(1)).translate(any(Workflow.class));
    verify(workflowEngine, times(1)).deploy(context);
    verify(workflowDirectedGraphService, times(1)).putDirectedGraph(graph);
  }

  @Test
  void addWorkflow_withDifferentWorkflowIds_shouldTrackSeparately() throws IOException, ProcessingException {
    // Given: Two different workflow files with different IDs
    Path workflow1 = tempDir.resolve("workflow1.yaml");
    String content1 = "id: workflow-one\n" +
        "properties:\n" +
        "  publish: true\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: msg1\n" +
        "      content: Workflow 1\n";
    Files.writeString(workflow1, content1);

    Path workflow2 = tempDir.resolve("workflow2.yaml");
    String content2 = "id: workflow-two\n" +
        "properties:\n" +
        "  publish: true\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: msg2\n" +
        "      content: Workflow 2\n";
    Files.writeString(workflow2, content2);

    CamundaTranslatedWorkflowContext context1 = mock(CamundaTranslatedWorkflowContext.class);
    CamundaTranslatedWorkflowContext context2 = mock(CamundaTranslatedWorkflowContext.class);
    WorkflowDirectedGraph graph1 = mock(WorkflowDirectedGraph.class);
    WorkflowDirectedGraph graph2 = mock(WorkflowDirectedGraph.class);
    when(context1.getWorkflowDirectedGraph()).thenReturn(graph1);
    when(context2.getWorkflowDirectedGraph()).thenReturn(graph2);
    when(workflowEngine.translate(any(Workflow.class))).thenReturn(context1, context2);

    // When: Adding both workflows
    workflowDeployer.addWorkflow(workflow1);
    workflowDeployer.addWorkflow(workflow2);

    // Then: Should deploy both workflows
    verify(workflowEngine, times(2)).translate(any(Workflow.class));
    verify(workflowEngine, times(1)).deploy(context1);
    verify(workflowEngine, times(1)).deploy(context2);
    verify(workflowDirectedGraphService, times(1)).putDirectedGraph(graph1);
    verify(workflowDirectedGraphService, times(1)).putDirectedGraph(graph2);
  }
}
