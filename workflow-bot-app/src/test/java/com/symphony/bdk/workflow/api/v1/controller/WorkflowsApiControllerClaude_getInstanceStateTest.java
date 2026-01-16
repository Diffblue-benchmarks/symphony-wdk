package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.NodeStateView;
import com.symphony.bdk.workflow.api.v1.dto.VariableView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.monitoring.service.MonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowsApiControllerClaude_getInstanceStateTest {

  @Mock
  private MonitoringService monitoringService;

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  private WorkflowsApiController controller;

  @BeforeEach
  void setUp() {
    controller = new WorkflowsApiController(monitoringService, workflowEngine);
  }

  // ==================== Basic Functionality Tests ====================

  @Test
  void getInstanceState_withMinimalParameters_shouldReturnState() {
    // Given: Only required parameters provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(Collections.emptyList());

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with only required parameters
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return OK with state
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isSameAs(expectedState);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_withAllParameters_shouldReturnState() {
    // Given: All parameters provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant startedBefore = Instant.now();
    Instant startedAfter = startedBefore.minus(1, ChronoUnit.HOURS);
    Instant finishedBefore = startedBefore.plus(2, ChronoUnit.HOURS);
    Instant finishedAfter = startedBefore.plus(1, ChronoUnit.HOURS);

    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(Collections.emptyList());

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with all parameters
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token",
            startedBefore, startedAfter, finishedBefore, finishedAfter);

    // Then: Should return OK with state
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isSameAs(expectedState);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_withNodes_shouldReturnNodesInState() {
    // Given: State with multiple nodes
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    NodeStateView node1 = NodeStateView.builder()
        .nodeId("node-1")
        .type("activity")
        .build();
    NodeStateView node2 = NodeStateView.builder()
        .nodeId("node-2")
        .type("gateway")
        .build();

    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(Arrays.asList(node1, node2));

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return state with nodes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getNodes()).hasSize(2);
    assertThat(response.getBody().getNodes().get(0).getNodeId()).isEqualTo("node-1");
    assertThat(response.getBody().getNodes().get(1).getNodeId()).isEqualTo("node-2");
  }

  // ==================== WorkflowId Parameter Tests ====================

  @Test
  void getInstanceState_withValidWorkflowId_shouldPassToMonitoringService() {
    // Given: Valid workflow ID
    String workflowId = "my-workflow-123";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should pass workflow ID to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_withWorkflowIdContainingSpecialCharacters_shouldWork() {
    // Given: Workflow ID with special characters
    String workflowId = "workflow-with-dashes_and_underscores.and.dots";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should work correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_withNullWorkflowId_shouldPassNullToMonitoringService() {
    // Given: Null workflow ID
    String workflowId = null;
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should pass null to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq((String) null), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  // ==================== InstanceId Parameter Tests ====================

  @Test
  void getInstanceState_withValidInstanceId_shouldPassToMonitoringService() {
    // Given: Valid instance ID
    String workflowId = "workflow-1";
    String instanceId = "my-instance-456";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should pass instance ID to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_withInstanceIdContainingSpecialCharacters_shouldWork() {
    // Given: Instance ID with special characters
    String workflowId = "workflow-1";
    String instanceId = "instance-with-dashes_and_underscores.and.dots";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should work correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_withNullInstanceId_shouldPassNullToMonitoringService() {
    // Given: Null instance ID
    String workflowId = "workflow-1";
    String instanceId = null;
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should pass null to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq((String) null), any(WorkflowInstLifeCycleFilter.class));
  }

  // ==================== Token Parameter Tests ====================

  @Test
  void getInstanceState_withNullToken_shouldStillCallMonitoringService() {
    // Given: Token is null
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with null token
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, null, null, null, null, null);

    // Then: Should still call monitoring service (authorization handled by @Authorized)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_withEmptyToken_shouldStillCallMonitoringService() {
    // Given: Token is empty
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with empty token
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "", null, null, null, null);

    // Then: Should still call monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_withValidToken_shouldCallMonitoringService() {
    // Given: Valid token
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with valid token
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "valid-token-12345", null, null, null, null);

    // Then: Should call monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  // ==================== Instant Parameters Tests (startedBefore, startedAfter, finishedBefore, finishedAfter) ====================

  @Test
  void getInstanceState_withAllNullInstants_shouldCreateFilterWithNullValues() {
    // Given: All Instant parameters are null
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with all null instants
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should create filter with all null values
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isNull();
    assertThat(capturedFilter.getStartedAfter()).isNull();
    assertThat(capturedFilter.getFinishedBefore()).isNull();
    assertThat(capturedFilter.getFinishedAfter()).isNull();
  }

  @Test
  void getInstanceState_withOnlyStartedBefore_shouldCreateFilterWithStartedBefore() {
    // Given: Only startedBefore is provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant startedBefore = Instant.parse("2024-01-15T10:00:00Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with only startedBefore
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", startedBefore, null, null, null);

    // Then: Should create filter with startedBefore and nulls for others
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isEqualTo(startedBefore);
    assertThat(capturedFilter.getStartedAfter()).isNull();
    assertThat(capturedFilter.getFinishedBefore()).isNull();
    assertThat(capturedFilter.getFinishedAfter()).isNull();
  }

  @Test
  void getInstanceState_withOnlyStartedAfter_shouldCreateFilterWithStartedAfter() {
    // Given: Only startedAfter is provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant startedAfter = Instant.parse("2024-01-15T09:00:00Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with only startedAfter
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, startedAfter, null, null);

    // Then: Should create filter with startedAfter and nulls for others
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isNull();
    assertThat(capturedFilter.getStartedAfter()).isEqualTo(startedAfter);
    assertThat(capturedFilter.getFinishedBefore()).isNull();
    assertThat(capturedFilter.getFinishedAfter()).isNull();
  }

  @Test
  void getInstanceState_withOnlyFinishedBefore_shouldCreateFilterWithFinishedBefore() {
    // Given: Only finishedBefore is provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant finishedBefore = Instant.parse("2024-01-15T12:00:00Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with only finishedBefore
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, finishedBefore, null);

    // Then: Should create filter with finishedBefore and nulls for others
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isNull();
    assertThat(capturedFilter.getStartedAfter()).isNull();
    assertThat(capturedFilter.getFinishedBefore()).isEqualTo(finishedBefore);
    assertThat(capturedFilter.getFinishedAfter()).isNull();
  }

  @Test
  void getInstanceState_withOnlyFinishedAfter_shouldCreateFilterWithFinishedAfter() {
    // Given: Only finishedAfter is provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant finishedAfter = Instant.parse("2024-01-15T11:00:00Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with only finishedAfter
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, finishedAfter);

    // Then: Should create filter with finishedAfter and nulls for others
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isNull();
    assertThat(capturedFilter.getStartedAfter()).isNull();
    assertThat(capturedFilter.getFinishedBefore()).isNull();
    assertThat(capturedFilter.getFinishedAfter()).isEqualTo(finishedAfter);
  }

  @Test
  void getInstanceState_withAllInstantsProvided_shouldCreateFilterWithAllValues() {
    // Given: All Instant parameters are provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant startedBefore = Instant.parse("2024-01-15T10:00:00Z");
    Instant startedAfter = Instant.parse("2024-01-15T09:00:00Z");
    Instant finishedBefore = Instant.parse("2024-01-15T12:00:00Z");
    Instant finishedAfter = Instant.parse("2024-01-15T11:00:00Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with all instants
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token",
            startedBefore, startedAfter, finishedBefore, finishedAfter);

    // Then: Should create filter with all values
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isEqualTo(startedBefore);
    assertThat(capturedFilter.getStartedAfter()).isEqualTo(startedAfter);
    assertThat(capturedFilter.getFinishedBefore()).isEqualTo(finishedBefore);
    assertThat(capturedFilter.getFinishedAfter()).isEqualTo(finishedAfter);
  }

  @Test
  void getInstanceState_withBothStartedInstants_shouldCreateFilterCorrectly() {
    // Given: Both started instants provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant startedBefore = Instant.parse("2024-01-15T10:00:00Z");
    Instant startedAfter = Instant.parse("2024-01-15T09:00:00Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with both started instants
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token",
            startedBefore, startedAfter, null, null);

    // Then: Should create filter with both started values
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isEqualTo(startedBefore);
    assertThat(capturedFilter.getStartedAfter()).isEqualTo(startedAfter);
    assertThat(capturedFilter.getFinishedBefore()).isNull();
    assertThat(capturedFilter.getFinishedAfter()).isNull();
  }

  @Test
  void getInstanceState_withBothFinishedInstants_shouldCreateFilterCorrectly() {
    // Given: Both finished instants provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant finishedBefore = Instant.parse("2024-01-15T12:00:00Z");
    Instant finishedAfter = Instant.parse("2024-01-15T11:00:00Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with both finished instants
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token",
            null, null, finishedBefore, finishedAfter);

    // Then: Should create filter with both finished values
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isNull();
    assertThat(capturedFilter.getStartedAfter()).isNull();
    assertThat(capturedFilter.getFinishedBefore()).isEqualTo(finishedBefore);
    assertThat(capturedFilter.getFinishedAfter()).isEqualTo(finishedAfter);
  }

  // ==================== WorkflowNodesStateView Content Tests ====================

  @Test
  void getInstanceState_withEmptyNodes_shouldReturnEmptyNodesList() {
    // Given: State with empty nodes list
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(Collections.emptyList());

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return state with empty nodes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getNodes()).isEmpty();
  }

  @Test
  void getInstanceState_withMultipleNodes_shouldReturnAllNodes() {
    // Given: State with multiple nodes
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    NodeStateView node1 = NodeStateView.builder()
        .workflowId(workflowId)
        .instanceId(instanceId)
        .nodeId("node-1")
        .type("activity")
        .group("group-1")
        .startDate(Instant.parse("2024-01-15T10:00:00Z"))
        .endDate(Instant.parse("2024-01-15T10:05:00Z"))
        .duration(Duration.ofMinutes(5))
        .build();

    NodeStateView node2 = NodeStateView.builder()
        .workflowId(workflowId)
        .instanceId(instanceId)
        .nodeId("node-2")
        .type("gateway")
        .group("group-2")
        .startDate(Instant.parse("2024-01-15T10:05:00Z"))
        .endDate(Instant.parse("2024-01-15T10:06:00Z"))
        .duration(Duration.ofMinutes(1))
        .build();

    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(Arrays.asList(node1, node2));

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return all nodes with their properties
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getNodes()).hasSize(2);

    NodeStateView returnedNode1 = response.getBody().getNodes().get(0);
    assertThat(returnedNode1.getNodeId()).isEqualTo("node-1");
    assertThat(returnedNode1.getType()).isEqualTo("activity");
    assertThat(returnedNode1.getGroup()).isEqualTo("group-1");
    assertThat(returnedNode1.getDuration()).isEqualTo(Duration.ofMinutes(5));

    NodeStateView returnedNode2 = response.getBody().getNodes().get(1);
    assertThat(returnedNode2.getNodeId()).isEqualTo("node-2");
    assertThat(returnedNode2.getType()).isEqualTo("gateway");
    assertThat(returnedNode2.getGroup()).isEqualTo("group-2");
    assertThat(returnedNode2.getDuration()).isEqualTo(Duration.ofMinutes(1));
  }

  @Test
  void getInstanceState_withGlobalVariables_shouldReturnGlobalVariables() {
    // Given: State with global variables
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView globalVars = new VariableView();

    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setGlobalVariables(globalVars);

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return state with global variables
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getGlobalVariables()).isNotNull();
    assertThat(response.getBody().getGlobalVariables()).isSameAs(globalVars);
  }

  @Test
  void getInstanceState_withError_shouldReturnError() {
    // Given: State with error
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    Map<String, Object> error = new HashMap<>();
    error.put("code", "ERROR_CODE");
    error.put("message", "Error message");

    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setError(error);

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return state with error
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getError()).isNotNull();
    assertThat(response.getBody().getError()).containsEntry("code", "ERROR_CODE");
    assertThat(response.getBody().getError()).containsEntry("message", "Error message");
  }

  @Test
  void getInstanceState_withNodeHavingOutputs_shouldReturnNodeWithOutputs() {
    // Given: Node with outputs
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("result", "success");
    outputs.put("value", 42);

    NodeStateView node = NodeStateView.builder()
        .nodeId("node-1")
        .type("activity")
        .outputs(outputs)
        .build();

    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(Collections.singletonList(node));

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return node with outputs
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getNodes()).hasSize(1);
    assertThat(response.getBody().getNodes().get(0).getOutputs()).isNotNull();
    assertThat(response.getBody().getNodes().get(0).getOutputs()).containsEntry("result", "success");
    assertThat(response.getBody().getNodes().get(0).getOutputs()).containsEntry("value", 42);
  }

  @Test
  void getInstanceState_withNodeHavingNullFields_shouldReturnNodeWithNullFields() {
    // Given: Node with null fields
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    NodeStateView node = NodeStateView.builder()
        .workflowId(null)
        .instanceId(null)
        .nodeId(null)
        .type(null)
        .group(null)
        .startDate(null)
        .endDate(null)
        .duration(null)
        .outputs(null)
        .build();

    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(Collections.singletonList(node));

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return node with null fields
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getNodes()).hasSize(1);
    NodeStateView returnedNode = response.getBody().getNodes().get(0);
    assertThat(returnedNode.getWorkflowId()).isNull();
    assertThat(returnedNode.getInstanceId()).isNull();
    assertThat(returnedNode.getNodeId()).isNull();
    assertThat(returnedNode.getType()).isNull();
    assertThat(returnedNode.getGroup()).isNull();
    assertThat(returnedNode.getStartDate()).isNull();
    assertThat(returnedNode.getEndDate()).isNull();
    assertThat(returnedNode.getDuration()).isNull();
    assertThat(returnedNode.getOutputs()).isNull();
  }

  // ==================== Large Dataset Tests ====================

  @Test
  void getInstanceState_withLargeNumberOfNodes_shouldReturnAllNodes() {
    // Given: State with many nodes
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    List<NodeStateView> nodes = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      nodes.add(NodeStateView.builder()
          .nodeId("node-" + i)
          .type("activity")
          .build());
    }

    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(nodes);

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return all 100 nodes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getNodes()).hasSize(100);
    assertThat(response.getBody().getNodes().get(0).getNodeId()).isEqualTo("node-0");
    assertThat(response.getBody().getNodes().get(99).getNodeId()).isEqualTo("node-99");
  }

  // ==================== Service Interaction Tests ====================

  @Test
  void getInstanceState_shouldCallMonitoringServiceOnce() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should call monitoringService exactly once
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class));
  }

  @Test
  void getInstanceState_shouldNotInteractWithWorkflowEngine() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should not interact with workflowEngine at all
    verifyNoInteractions(workflowEngine);
  }

  // ==================== Response Entity Tests ====================

  @Test
  void getInstanceState_shouldReturnHttpStatusOK() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Should return HTTP 200 OK
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getInstanceState_shouldReturnNonNullBody() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Body should not be null
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  void getInstanceState_shouldReturnSameStateAsMonitoringService() {
    // Given: MonitoringService returns specific state
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();
    expectedState.setNodes(Collections.emptyList());

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", null, null, null, null);

    // Then: Body should be the same state returned by monitoringService
    assertThat(response.getBody()).isSameAs(expectedState);
  }

  // ==================== Edge Cases ====================

  @Test
  void getInstanceState_withInstantAtEpoch_shouldHandleCorrectly() {
    // Given: Instant at epoch (1970-01-01T00:00:00Z)
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant epoch = Instant.EPOCH;
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with epoch instant
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", epoch, null, null, null);

    // Then: Should handle epoch correctly
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());
    assertThat(filterCaptor.getValue().getStartedBefore()).isEqualTo(Instant.EPOCH);
  }

  @Test
  void getInstanceState_withVeryFarFutureInstant_shouldHandleCorrectly() {
    // Given: Very far future instant
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant farFuture = Instant.parse("2999-12-31T23:59:59Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with far future instant
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", farFuture, null, null, null);

    // Then: Should handle far future correctly
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());
    assertThat(filterCaptor.getValue().getStartedBefore()).isEqualTo(farFuture);
  }

  @Test
  void getInstanceState_withSameStartedBeforeAndAfter_shouldCreateFilterCorrectly() {
    // Given: Same instant for startedBefore and startedAfter
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant sameInstant = Instant.parse("2024-01-15T10:00:00Z");
    WorkflowNodesStateView expectedState = new WorkflowNodesStateView();

    when(monitoringService.listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), any(WorkflowInstLifeCycleFilter.class)))
        .thenReturn(expectedState);

    // When: Calling getInstanceState with same instant for both
    ResponseEntity<WorkflowNodesStateView> response =
        controller.getInstanceState(workflowId, instanceId, "test-token", sameInstant, sameInstant, null, null);

    // Then: Should create filter with same instant for both
    ArgumentCaptor<WorkflowInstLifeCycleFilter> filterCaptor = ArgumentCaptor.forClass(WorkflowInstLifeCycleFilter.class);
    verify(monitoringService, times(1)).listWorkflowInstanceNodes(eq(workflowId), eq(instanceId), filterCaptor.capture());

    WorkflowInstLifeCycleFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStartedBefore()).isEqualTo(sameInstant);
    assertThat(capturedFilter.getStartedAfter()).isEqualTo(sameInstant);
  }
}
