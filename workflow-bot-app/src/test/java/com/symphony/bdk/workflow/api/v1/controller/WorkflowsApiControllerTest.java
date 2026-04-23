package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.VariableView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowExecutionRequest;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.monitoring.service.MonitoringService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowsApiControllerTest {

  @Mock
  private MonitoringService monitoringService;

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @InjectMocks
  private WorkflowsApiController controller;

  @Test
  void shouldReturnNoContentWhenExecuteWorkflowById() {
    WorkflowExecutionRequest request = new WorkflowExecutionRequest();
    request.setArgs(Map.of("key", "value"));

    ResponseEntity<Object> response = controller.executeWorkflowById("token", "workflow-id", request);

    verify(workflowEngine).execute(eq("workflow-id"), any());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldReturnWorkflowsWhenListAllWorkflows() {
    List<WorkflowView> workflows = Collections.singletonList(WorkflowView.builder().id("wf-id").build());
    when(monitoringService.listAllWorkflows()).thenReturn(workflows);

    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("token");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(workflows);
  }

  @Test
  void shouldReturnInstancesWhenListWorkflowInstances() {
    List<WorkflowInstView> instances = Collections.singletonList(WorkflowInstView.builder().id("wf-id").build());
    when(monitoringService.listWorkflowInstances("wf-id", "Completed", 1L)).thenReturn(instances);

    ResponseEntity<List<WorkflowInstView>> response = controller.listWorkflowInstances("wf-id", "token", "Completed", 1L);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(instances);
  }

  @Test
  void shouldReturnInstanceStateWhenGetInstanceState() {
    WorkflowNodesStateView stateView = new WorkflowNodesStateView();
    when(monitoringService.listWorkflowInstanceNodes(eq("wf-id"), eq("inst-id"), any())).thenReturn(stateView);

    Instant now = Instant.now();
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState("wf-id", "inst-id", "token", now, now, now, now);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(stateView);
  }

  @Test
  void shouldReturnNodesWhenGetWorkflowGraphNodes() {
    WorkflowNodesView nodesView = WorkflowNodesView.builder().workflowId("wf-id").build();
    when(monitoringService.getWorkflowDefinition("wf-id", 2L)).thenReturn(nodesView);

    ResponseEntity<WorkflowNodesView> response = controller.getWorkflowGraphNodes("wf-id", "token", 2L);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(nodesView);
  }

  @Test
  void shouldReturnVariablesWhenListWorkflowGlobalVariables() {
    List<VariableView> variables = Collections.singletonList(new VariableView());
    Instant before = Instant.now();
    Instant after = before.minusSeconds(3600);
    when(monitoringService.listWorkflowInstanceGlobalVars("wf-id", "inst-id", before, after)).thenReturn(variables);

    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables("wf-id", "inst-id", "token", before, after);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(variables);
  }
}
