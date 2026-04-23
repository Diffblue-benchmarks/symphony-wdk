package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowDeployerTest {

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @Mock
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  @Mock
  private CamundaTranslatedWorkflowContext context;

  @Mock
  private WorkflowDirectedGraph workflowDirectedGraph;

  @TempDir
  Path tempDir;

  private WorkflowDeployer deployer;

  private static final String PUBLISH_YAML =
      "id: test-workflow\n"
      + "activities:\n"
      + "  - execute-script:\n"
      + "      id: script1\n"
      + "      on:\n"
      + "        message-received:\n"
      + "          content: /test\n"
      + "      script: |\n"
      + "        messageService.send(\"123\", \"hello\")\n";

  private static final String DRAFT_YAML =
      "id: test-workflow\n"
      + "properties:\n"
      + "  publish: false\n"
      + "activities:\n"
      + "  - execute-script:\n"
      + "      id: script1\n"
      + "      on:\n"
      + "        message-received:\n"
      + "          content: /test\n"
      + "      script: |\n"
      + "        messageService.send(\"123\", \"hello\")\n";

  @BeforeEach
  void setUp() {
    deployer = new WorkflowDeployer(workflowEngine, workflowDirectedGraphService);
  }

  // ---- addAllWorkflowsFromFolder ----

  @Test
  void shouldThrowWhenPathIsNotDirectory() {
    // given
    Path nonExistentPath = tempDir.resolve("not-a-dir.yaml");

    // when / then
    assertThatThrownBy(() -> deployer.addAllWorkflowsFromFolder(nonExistentPath))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Could not find workflows folder");
  }

  @Test
  void shouldAddYamlFilesFromFolder() throws Exception {
    // given
    Files.writeString(tempDir.resolve("workflow.yaml"), PUBLISH_YAML);
    when(workflowEngine.translate(any())).thenReturn(context);
    when(context.getWorkflowDirectedGraph()).thenReturn(workflowDirectedGraph);

    // when
    deployer.addAllWorkflowsFromFolder(tempDir);

    // then
    verify(workflowEngine, times(1)).translate(any());
  }

  @Test
  void shouldSkipNonYamlFilesFromFolder() throws Exception {
    // given
    Files.writeString(tempDir.resolve("file.txt"), "not a yaml file");

    // when
    deployer.addAllWorkflowsFromFolder(tempDir);

    // then
    verify(workflowEngine, never()).translate(any());
  }

  @Test
  void shouldContinueProcessingWhenOneFileFailsInFolder() throws Exception {
    // given - a yaml file that will fail parsing (no activities)
    Files.writeString(tempDir.resolve("bad.yaml"), "id: invalid-only\n");

    // when - should not throw even if file processing fails
    deployer.addAllWorkflowsFromFolder(tempDir);

    // then - no exception propagated
    verify(workflowEngine, never()).deploy(any(CamundaTranslatedWorkflowContext.class));
  }

  // ---- addWorkflow ----

  @Test
  void shouldSkipEmptyWorkflowFile() throws Exception {
    // given
    Path emptyFile = Files.createFile(tempDir.resolve("empty.yaml"));

    // when
    deployer.addWorkflow(emptyFile);

    // then
    verify(workflowEngine, never()).translate(any());
  }

  @Test
  void shouldDeployWorkflowWhenToPublish() throws Exception {
    // given
    Path yamlFile = Files.writeString(tempDir.resolve("workflow.yaml"), PUBLISH_YAML);
    when(workflowEngine.translate(any())).thenReturn(context);
    when(context.getWorkflowDirectedGraph()).thenReturn(workflowDirectedGraph);

    // when
    deployer.addWorkflow(yamlFile);

    // then
    verify(workflowEngine).deploy(context);
    verify(workflowDirectedGraphService).putDirectedGraph(workflowDirectedGraph);
  }

  @Test
  void shouldNotDeployWhenWorkflowIsDraftAndNoPreviousDeployment() throws Exception {
    // given
    Path yamlFile = Files.writeString(tempDir.resolve("workflow.yaml"), DRAFT_YAML);
    when(workflowEngine.translate(any())).thenReturn(context);

    // when
    deployer.addWorkflow(yamlFile);

    // then
    verify(workflowEngine, never()).deploy(any(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine, never()).undeployByWorkflowId(any());
  }

  @Test
  void shouldUndeployOldVersionWhenWorkflowChangesToDraft() throws Exception {
    // given - add as published first
    Path yamlFile = Files.writeString(tempDir.resolve("workflow.yaml"), PUBLISH_YAML);
    when(workflowEngine.translate(any())).thenReturn(context);
    when(context.getWorkflowDirectedGraph()).thenReturn(workflowDirectedGraph);
    deployer.addWorkflow(yamlFile);

    // now update to draft
    Files.writeString(yamlFile, DRAFT_YAML);

    // when
    deployer.addWorkflow(yamlFile);

    // then
    verify(workflowEngine).undeployByWorkflowId("test-workflow");
  }

  // ---- handleFileEvent ----

  @Test
  void shouldAddWorkflowOnCreateEvent() throws Exception {
    // given
    Path yamlFile = Files.writeString(tempDir.resolve("workflow.yaml"), PUBLISH_YAML);
    WatchEvent<Path> event = mockWatchEvent(StandardWatchEventKinds.ENTRY_CREATE);
    when(workflowEngine.translate(any())).thenReturn(context);
    when(context.getWorkflowDirectedGraph()).thenReturn(workflowDirectedGraph);

    // when
    deployer.handleFileEvent(yamlFile, event);

    // then
    verify(workflowEngine).translate(any());
  }

  @Test
  void shouldAddWorkflowOnModifyEvent() throws Exception {
    // given
    Path yamlFile = Files.writeString(tempDir.resolve("workflow.yaml"), PUBLISH_YAML);
    WatchEvent<Path> event = mockWatchEvent(StandardWatchEventKinds.ENTRY_MODIFY);
    when(workflowEngine.translate(any())).thenReturn(context);
    when(context.getWorkflowDirectedGraph()).thenReturn(workflowDirectedGraph);

    // when
    deployer.handleFileEvent(yamlFile, event);

    // then
    verify(workflowEngine).translate(any());
  }

  @Test
  void shouldUndeployWorkflowOnDeleteEvent() throws Exception {
    // given - add the workflow so it appears in deployedWorkflows
    Path yamlFile = Files.writeString(tempDir.resolve("workflow.yaml"), PUBLISH_YAML);
    when(workflowEngine.translate(any())).thenReturn(context);
    when(context.getWorkflowDirectedGraph()).thenReturn(workflowDirectedGraph);
    deployer.addWorkflow(yamlFile);

    WatchEvent<Path> event = mockWatchEvent(StandardWatchEventKinds.ENTRY_DELETE);

    // when
    deployer.handleFileEvent(yamlFile, event);

    // then
    verify(workflowEngine).undeployByWorkflowId("test-workflow");
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldSkipNonYamlFileInHandleFileEvent() throws Exception {
    // given
    Path nonYaml = tempDir.resolve("file.txt");
    WatchEvent<Path> event = mock(WatchEvent.class);

    // when
    deployer.handleFileEvent(nonYaml, event);

    // then
    verify(workflowEngine, never()).translate(any());
  }

  @Test
  void shouldHandleUnknownEventKindGracefully() throws Exception {
    // given
    Path yamlFile = Files.writeString(tempDir.resolve("workflow.yaml"), PUBLISH_YAML);
    @SuppressWarnings("unchecked")
    WatchEvent<Path> event = mock(WatchEvent.class);
    @SuppressWarnings("unchecked")
    WatchEvent.Kind<Path> unknownKind = mock(WatchEvent.Kind.class);
    when(event.kind()).thenReturn(unknownKind);

    // when - unknown kind should not throw
    deployer.handleFileEvent(yamlFile, event);

    // then
    verify(workflowEngine, never()).translate(any());
  }

  // ---- isYaml (via handleFileEvent) ----

  @Test
  void shouldRecognizeYmlExtension() throws Exception {
    // given
    Path ymlFile = Files.writeString(tempDir.resolve("workflow.yml"), PUBLISH_YAML);
    WatchEvent<Path> event = mockWatchEvent(StandardWatchEventKinds.ENTRY_CREATE);
    when(workflowEngine.translate(any())).thenReturn(context);
    when(context.getWorkflowDirectedGraph()).thenReturn(workflowDirectedGraph);

    // when
    deployer.handleFileEvent(ymlFile, event);

    // then
    verify(workflowEngine).translate(any());
  }

  @SuppressWarnings("unchecked")
  private WatchEvent<Path> mockWatchEvent(WatchEvent.Kind<Path> kind) {
    WatchEvent<Path> event = mock(WatchEvent.class);
    when(event.kind()).thenReturn(kind);
    return event;
  }
}
