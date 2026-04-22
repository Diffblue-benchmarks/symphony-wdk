package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.swadl.v1.Workflow;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
public class WorkflowDeployerTest {

  @Mock
  WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;
  @Mock
  WorkflowDirectedGraphService directedGraphService;

  @InjectMocks
  WorkflowDeployer workflowDeployer;

  @Test
  void testAddAllWorkflowsFromFolderException() {
    String file = "src/test/resources/basic/publish/basic-workflow.swadl.yaml";
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> workflowDeployer.addAllWorkflowsFromFolder(Path.of(file)))
        .satisfies(
            e -> assertThat(e.getMessage()).isEqualTo("Could not find workflows folder to monitor with path: " + file));
  }

  private static class WatchEvent implements java.nio.file.WatchEvent<Path> {

    private final java.nio.file.WatchEvent.Kind<Path> kind;

    public WatchEvent(WatchEvent.Kind<Path> kind) {
      this.kind = kind;
    }

    @Override
    public Kind<Path> kind() {
      return this.kind;
    }

    @Override
    public int count() {
      return 0;
    }

    @Override
    public Path context() {
      return null;
    }
  }
}
