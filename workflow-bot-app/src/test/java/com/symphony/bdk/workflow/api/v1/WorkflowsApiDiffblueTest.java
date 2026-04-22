package com.symphony.bdk.workflow.api.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.controller.WorkflowsApiController;
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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ContextConfiguration(classes = {WorkflowsApiController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class WorkflowsApiDiffblueTest {
  @MockBean private MonitoringService monitoringService;

  @MockBean private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @Autowired private WorkflowsApiController workflowsApiController;

  /**
   * Test {@link WorkflowsApi#executeWorkflowById(String, String, WorkflowExecutionRequest)}.
   *
   * <p>Method under test: {@link WorkflowsApi#executeWorkflowById(String, String,
   * WorkflowExecutionRequest)}
   */
  @Test
  @DisplayName("Test executeWorkflowById(String, String, WorkflowExecutionRequest) via interface")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApi.executeWorkflowById(String, String, WorkflowExecutionRequest)"
  })
  void testExecuteWorkflowById() {
    // Arrange
    doNothing()
        .when(workflowEngine)
        .execute(Mockito.<String>any(), Mockito.<ExecutionParameters>any());

    WorkflowExecutionRequest arguments = new WorkflowExecutionRequest();
    arguments.setArgs(new HashMap<>());

    WorkflowsApi api = workflowsApiController;

    // Act
    ResponseEntity<Object> actualResult =
        api.executeWorkflowById("ABC123", "42", arguments);

    // Assert
    verify(workflowEngine).execute(eq("42"), isA(ExecutionParameters.class));
    HttpStatusCode statusCode = actualResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualResult.getBody());
    assertEquals(204, actualResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
  }

  /**
   * Test {@link WorkflowsApi#listAllWorkflows(String)}.
   *
   * <p>Method under test: {@link WorkflowsApi#listAllWorkflows(String)}
   */
  @Test
  @DisplayName("Test listAllWorkflows(String) via interface")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsApi.listAllWorkflows(String)"})
  void testListAllWorkflows() {
    // Arrange
    when(monitoringService.listAllWorkflows()).thenReturn(new ArrayList<>());

    WorkflowsApi api = workflowsApiController;

    // Act
    ResponseEntity<List<WorkflowView>> actualResult = api.listAllWorkflows("ABC123");

    // Assert
    verify(monitoringService).listAllWorkflows();
    HttpStatusCode statusCode = actualResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualResult.getBody().isEmpty());
  }

  /**
   * Test {@link WorkflowsApi#listWorkflowInstances(String, String, String, Long)}.
   *
   * <p>Method under test: {@link WorkflowsApi#listWorkflowInstances(String, String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, String, Long) via interface")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApi.listWorkflowInstances(String, String, String, Long)"
  })
  void testListWorkflowInstances() {
    // Arrange
    when(monitoringService.listWorkflowInstances(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    WorkflowsApi api = workflowsApiController;

    // Act
    ResponseEntity<List<WorkflowInstView>> actualResult =
        api.listWorkflowInstances("wfId", "ABC123", "Completed", 1L);

    // Assert
    verify(monitoringService).listWorkflowInstances("wfId", "Completed", 1L);
    HttpStatusCode statusCode = actualResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualResult.getBody().isEmpty());
  }

  /**
   * Test {@link WorkflowsApi#getInstanceState(String, String, String, Instant, Instant, Instant,
   * Instant)}.
   *
   * <p>Method under test: {@link WorkflowsApi#getInstanceState(String, String, String, Instant,
   * Instant, Instant, Instant)}
   */
  @Test
  @DisplayName(
      "Test getInstanceState(String, String, String, Instant, Instant, Instant, Instant) via interface")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApi.getInstanceState(String, String, String, Instant, Instant, Instant, Instant)"
  })
  void testGetInstanceState() {
    // Arrange
    WorkflowNodesStateView stateView = new WorkflowNodesStateView();
    stateView.setError(new HashMap<>());
    stateView.setGlobalVariables(new VariableView());
    stateView.setNodes(new ArrayList<>());
    when(monitoringService.listWorkflowInstanceNodes(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<WorkflowInstLifeCycleFilter>any()))
        .thenReturn(stateView);

    Instant epoch = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    WorkflowsApi api = workflowsApiController;

    // Act
    ResponseEntity<WorkflowNodesStateView> actualResult =
        api.getInstanceState("wfId", "instId", "ABC123", epoch, epoch, epoch, epoch);

    // Assert
    verify(monitoringService)
        .listWorkflowInstanceNodes(eq("wfId"), eq("instId"), isA(WorkflowInstLifeCycleFilter.class));
    HttpStatusCode statusCode = actualResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualResult.hasBody());
  }

  /**
   * Test {@link WorkflowsApi#getWorkflowGraphNodes(String, String, Long)}.
   *
   * <p>Method under test: {@link WorkflowsApi#getWorkflowGraphNodes(String, String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowGraphNodes(String, String, Long) via interface")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsApi.getWorkflowGraphNodes(String, String, Long)"})
  void testGetWorkflowGraphNodes() {
    // Arrange
    WorkflowNodesView nodesView = WorkflowNodesView.builder().build();
    when(monitoringService.getWorkflowDefinition(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(nodesView);

    WorkflowsApi api = workflowsApiController;

    // Act
    ResponseEntity<WorkflowNodesView> actualResult =
        api.getWorkflowGraphNodes("wfId", "ABC123", 1L);

    // Assert
    verify(monitoringService).getWorkflowDefinition("wfId", 1L);
    HttpStatusCode statusCode = actualResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualResult.hasBody());
  }

  /**
   * Test {@link WorkflowsApi#listWorkflowGlobalVariables(String, String, String, Instant,
   * Instant)}.
   *
   * <p>Method under test: {@link WorkflowsApi#listWorkflowGlobalVariables(String, String, String,
   * Instant, Instant)}
   */
  @Test
  @DisplayName(
      "Test listWorkflowGlobalVariables(String, String, String, Instant, Instant) via interface")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApi.listWorkflowGlobalVariables(String, String, String, Instant, Instant)"
  })
  void testListWorkflowGlobalVariables() {
    // Arrange
    when(monitoringService.listWorkflowInstanceGlobalVars(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<Instant>any(),
            Mockito.<Instant>any()))
        .thenReturn(new ArrayList<>());

    Instant epoch = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    WorkflowsApi api = workflowsApiController;

    // Act
    ResponseEntity<List<VariableView>> actualResult =
        api.listWorkflowGlobalVariables("wfId", "instId", "ABC123", epoch, epoch);

    // Assert
    verify(monitoringService).listWorkflowInstanceGlobalVars("wfId", "instId", epoch, epoch);
    HttpStatusCode statusCode = actualResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualResult.getBody().isEmpty());
  }
}
