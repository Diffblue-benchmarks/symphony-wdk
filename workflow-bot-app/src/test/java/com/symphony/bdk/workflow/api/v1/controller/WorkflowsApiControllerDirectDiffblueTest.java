package com.symphony.bdk.workflow.api.v1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.api.v1.dto.VariableView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowExecutionRequest;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.engine.ExecutionParameters;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.monitoring.service.MonitoringService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class WorkflowsApiControllerDirectDiffblueTest {

  @Mock
  private MonitoringService monitoringService;

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @InjectMocks
  private WorkflowsApiController workflowsApiController;

  /**
   * Test {@link WorkflowsApiController#executeWorkflowById(String, String, WorkflowExecutionRequest)}.
   */
  @Test
  @DisplayName("Test executeWorkflowById returns 204 No Content")
  void testExecuteWorkflowById_returnsNoContent() {
    // Arrange
    doNothing().when(workflowEngine).execute(anyString(), any(ExecutionParameters.class));
    WorkflowExecutionRequest request = new WorkflowExecutionRequest();
    request.setArgs(new HashMap<>());

    // Act
    ResponseEntity<Object> response = workflowsApiController.executeWorkflowById("token", "workflowId", request);

    // Assert
    verify(workflowEngine).execute(eq("workflowId"), any(ExecutionParameters.class));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();
  }

  /**
   * Test {@link WorkflowsApiController#listAllWorkflows(String)}.
   */
  @Test
  @DisplayName("Test listAllWorkflows returns 200 OK with list")
  void testListAllWorkflows_returnsOk() {
    // Arrange
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.emptyList());

    // Act
    ResponseEntity<List<WorkflowView>> response = workflowsApiController.listAllWorkflows("token");

    // Assert
    verify(monitoringService).listAllWorkflows();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEmpty();
  }

  /**
   * Test {@link WorkflowsApiController#listWorkflowInstances(String, String, String, Long)}.
   */
  @Test
  @DisplayName("Test listWorkflowInstances returns 200 OK")
  void testListWorkflowInstances_returnsOk() {
    // Arrange
    when(monitoringService.listWorkflowInstances(anyString(), any(), any())).thenReturn(Collections.emptyList());

    // Act
    ResponseEntity<List<WorkflowInstView>> response =
        workflowsApiController.listWorkflowInstances("wfId", "token", null, null);

    // Assert
    verify(monitoringService).listWorkflowInstances(eq("wfId"), eq(null), eq(null));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEmpty();
  }

  /**
   * Test {@link WorkflowsApiController#getInstanceState(String, String, String, Instant, Instant, Instant, Instant)}.
   */
  @Test
  @DisplayName("Test getInstanceState returns 200 OK with state view")
  void testGetInstanceState_returnsOk() {
    // Arrange
    WorkflowNodesStateView stateView = new WorkflowNodesStateView();
    when(monitoringService.listWorkflowInstanceNodes(anyString(), anyString(), any())).thenReturn(stateView);
    Instant now = Instant.now();

    // Act
    ResponseEntity<WorkflowNodesStateView> response = workflowsApiController.getInstanceState(
        "wfId", "instanceId", "token", now, now, now, now);

    // Assert
    verify(monitoringService).listWorkflowInstanceNodes(eq("wfId"), eq("instanceId"), any());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isSameAs(stateView);
  }

  /**
   * Test {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}.
   */
  @Test
  @DisplayName("Test getWorkflowGraphNodes returns 200 OK")
  void testGetWorkflowGraphNodes_returnsOk() {
    // Arrange
    WorkflowNodesView nodesView = WorkflowNodesView.builder().workflowId("wfId").version(1L).build();
    when(monitoringService.getWorkflowDefinition(anyString(), anyLong())).thenReturn(nodesView);

    // Act
    ResponseEntity<WorkflowNodesView> response =
        workflowsApiController.getWorkflowGraphNodes("wfId", "token", 1L);

    // Assert
    verify(monitoringService).getWorkflowDefinition(eq("wfId"), eq(1L));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isSameAs(nodesView);
  }

  /**
   * Test {@link WorkflowsApiController#listWorkflowGlobalVariables(String, String, String, Instant, Instant)}.
   */
  @Test
  @DisplayName("Test listWorkflowGlobalVariables returns 200 OK")
  void testListWorkflowGlobalVariables_returnsOk() {
    // Arrange
    when(monitoringService.listWorkflowInstanceGlobalVars(anyString(), anyString(), any(), any()))
        .thenReturn(Collections.emptyList());
    Instant now = Instant.now();

    // Act
    ResponseEntity<List<VariableView>> response = workflowsApiController.listWorkflowGlobalVariables(
        "wfId", "instanceId", "token", now, now);

    // Assert
    verify(monitoringService).listWorkflowInstanceGlobalVars(eq("wfId"), eq("instanceId"), eq(now), eq(now));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEmpty();
  }
}
