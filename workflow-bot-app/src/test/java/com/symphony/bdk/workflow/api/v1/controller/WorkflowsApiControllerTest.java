package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.VariableView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowExecutionRequest;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.engine.ExecutionParameters;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.monitoring.service.MonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkflowsApiControllerTest {

  private MonitoringService monitoringService;
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;
  private WorkflowsApiController controller;

  @BeforeEach
  void setUp() {
    monitoringService = mock(MonitoringService.class);
    workflowEngine = mock(WorkflowEngine.class);
    controller = new WorkflowsApiController(monitoringService, workflowEngine);
  }

  @Test
  void shouldExecuteWorkflowByIdWithArguments() {
    String token = "test-token";
    String workflowId = "workflow-123";
    Map<String, Object> args = new HashMap<>();
    args.put("key1", "value1");
    WorkflowExecutionRequest request = new WorkflowExecutionRequest();
    request.setArgs(args);

    ResponseEntity<Object> response = controller.executeWorkflowById(token, workflowId, request);

    verify(workflowEngine).execute(eq(workflowId), any(ExecutionParameters.class));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldExecuteWorkflowByIdWithEmptyArguments() {
    String token = "test-token";
    String workflowId = "workflow-456";
    WorkflowExecutionRequest request = new WorkflowExecutionRequest();
    request.setArgs(new HashMap<>());

    ResponseEntity<Object> response = controller.executeWorkflowById(token, workflowId, request);

    verify(workflowEngine).execute(eq(workflowId), any(ExecutionParameters.class));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldListAllWorkflows() {
    String token = "monitoring-token";
    List<WorkflowView> expectedWorkflows = List.of(mock(WorkflowView.class), mock(WorkflowView.class));
    when(monitoringService.listAllWorkflows()).thenReturn(expectedWorkflows);

    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows(token);

    verify(monitoringService).listAllWorkflows();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expectedWorkflows);
  }

  @Test
  void shouldListWorkflowInstances() {
    String workflowId = "workflow-789";
    String token = "monitoring-token";
    String status = "ACTIVE";
    Long version = 1L;
    List<WorkflowInstView> expectedInstances = List.of(mock(WorkflowInstView.class));
    when(monitoringService.listWorkflowInstances(workflowId, status, version))
        .thenReturn(expectedInstances);

    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, token, status, version);

    verify(monitoringService).listWorkflowInstances(workflowId, status, version);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expectedInstances);
  }

  @Test
  void shouldGetInstanceStateWithLifeCycleFilter() {
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    String token = "monitoring-token";
    Instant startedBefore = Instant.parse("2026-03-13T10:00:00Z");
    Instant startedAfter = Instant.parse("2026-03-13T09:00:00Z");
    Instant finishedBefore = Instant.parse("2026-03-13T11:00:00Z");
    Instant finishedAfter = Instant.parse("2026-03-13T10:30:00Z");
    WorkflowNodesStateView expectedState = mock(WorkflowNodesStateView.class);
    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId),
        any(WorkflowInstLifeCycleFilter.class))).thenReturn(expectedState);

    ResponseEntity<WorkflowNodesStateView> response = controller.getInstanceState(
        workflowId, instanceId, token, startedBefore, startedAfter, finishedBefore, finishedAfter);

    verify(monitoringService).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId),
        any(WorkflowInstLifeCycleFilter.class));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expectedState);
  }

  @Test
  void shouldGetWorkflowGraphNodes() {
    String workflowId = "workflow-999";
    String token = "monitoring-token";
    Long version = 2L;
    WorkflowNodesView expectedNodes = mock(WorkflowNodesView.class);
    when(monitoringService.getWorkflowDefinition(workflowId, version)).thenReturn(expectedNodes);

    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, token, version);

    verify(monitoringService).getWorkflowDefinition(workflowId, version);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expectedNodes);
  }

  @Test
  void shouldListWorkflowGlobalVariablesWithTimestamps() {
    String workflowId = "workflow-111";
    String instanceId = "instance-222";
    String token = "monitoring-token";
    Instant updatedBefore = Instant.parse("2026-03-13T12:00:00Z");
    Instant updatedAfter = Instant.parse("2026-03-13T08:00:00Z");
    List<VariableView> expectedVariables = List.of(mock(VariableView.class), mock(VariableView.class));
    when(monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, updatedBefore, updatedAfter)).thenReturn(expectedVariables);

    ResponseEntity<List<VariableView>> response = controller.listWorkflowGlobalVariables(
        workflowId, instanceId, token, updatedBefore, updatedAfter);

    verify(monitoringService).listWorkflowInstanceGlobalVars(
        workflowId, instanceId, updatedBefore, updatedAfter);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expectedVariables);
  }
}
