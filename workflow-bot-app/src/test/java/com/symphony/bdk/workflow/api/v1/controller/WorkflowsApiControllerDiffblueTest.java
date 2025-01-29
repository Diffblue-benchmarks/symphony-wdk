package com.symphony.bdk.workflow.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import com.symphony.bdk.workflow.exception.UnauthorizedException;
import com.symphony.bdk.workflow.monitoring.service.MonitoringService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
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

@ContextConfiguration(classes = {WorkflowsApiController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WorkflowsApiControllerDiffblueTest {
  @MockBean
  private MonitoringService monitoringService;

  @MockBean
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @Autowired
  private WorkflowsApiController workflowsApiController;

  /**
   * Test
   * {@link WorkflowsApiController#executeWorkflowById(String, String, WorkflowExecutionRequest)}.
   * <p>
   * Method under test:
   * {@link WorkflowsApiController#executeWorkflowById(String, String, WorkflowExecutionRequest)}
   */
  @Test
  @DisplayName("Test executeWorkflowById(String, String, WorkflowExecutionRequest)")
  void testExecuteWorkflowById() throws UnauthorizedException {
    // Arrange
    doNothing().when(workflowEngine).execute(Mockito.<String>any(), Mockito.<ExecutionParameters>any());

    WorkflowExecutionRequest arguments = new WorkflowExecutionRequest();
    arguments.setArgs(new HashMap<>());

    // Act
    ResponseEntity<Object> actualExecuteWorkflowByIdResult = workflowsApiController.executeWorkflowById("ABC123", "42",
        arguments);

    // Assert
    verify(workflowEngine).execute(eq("42"), isA(ExecutionParameters.class));
    HttpStatusCode statusCode = actualExecuteWorkflowByIdResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualExecuteWorkflowByIdResult.getBody());
    assertEquals(204, actualExecuteWorkflowByIdResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualExecuteWorkflowByIdResult.hasBody());
    assertTrue(actualExecuteWorkflowByIdResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#listAllWorkflows(String)}.
   * <p>
   * Method under test: {@link WorkflowsApiController#listAllWorkflows(String)}
   */
  @Test
  @DisplayName("Test listAllWorkflows(String)")
  void testListAllWorkflows() {
    // Arrange
    when(monitoringService.listAllWorkflows()).thenReturn(new ArrayList<>());

    // Act
    ResponseEntity<List<WorkflowView>> actualListAllWorkflowsResult = workflowsApiController.listAllWorkflows("ABC123");

    // Assert
    verify(monitoringService).listAllWorkflows();
    HttpStatusCode statusCode = actualListAllWorkflowsResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListAllWorkflowsResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListAllWorkflowsResult.getBody().isEmpty());
    assertTrue(actualListAllWorkflowsResult.hasBody());
    assertTrue(actualListAllWorkflowsResult.getHeaders().isEmpty());
  }

  /**
   * Test
   * {@link WorkflowsApiController#listWorkflowInstances(String, String, String, Long)}.
   * <p>
   * Method under test:
   * {@link WorkflowsApiController#listWorkflowInstances(String, String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, String, Long)")
  void testListWorkflowInstances() {
    // Arrange
    when(monitoringService.listWorkflowInstances(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    ResponseEntity<List<WorkflowInstView>> actualListWorkflowInstancesResult = workflowsApiController
        .listWorkflowInstances("42", "ABC123", "Status", 1L);

    // Assert
    verify(monitoringService).listWorkflowInstances(eq("42"), eq("Status"), eq(1L));
    HttpStatusCode statusCode = actualListWorkflowInstancesResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListWorkflowInstancesResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListWorkflowInstancesResult.getBody().isEmpty());
    assertTrue(actualListWorkflowInstancesResult.hasBody());
    assertTrue(actualListWorkflowInstancesResult.getHeaders().isEmpty());
  }

  /**
   * Test
   * {@link WorkflowsApiController#getInstanceState(String, String, String, Instant, Instant, Instant, Instant)}.
   * <p>
   * Method under test:
   * {@link WorkflowsApiController#getInstanceState(String, String, String, Instant, Instant, Instant, Instant)}
   */
  @Test
  @DisplayName("Test getInstanceState(String, String, String, Instant, Instant, Instant, Instant)")
  void testGetInstanceState() {
    // Arrange
    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(new ArrayList<>());
    when(monitoringService.listWorkflowInstanceNodes(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<WorkflowInstLifeCycleFilter>any())).thenReturn(workflowNodesStateView);
    Instant startedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant startedAfter = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant finishedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    ResponseEntity<WorkflowNodesStateView> actualInstanceState = workflowsApiController.getInstanceState("42", "42",
        "ABC123", startedBefore, startedAfter, finishedBefore,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(monitoringService).listWorkflowInstanceNodes(eq("42"), eq("42"), isA(WorkflowInstLifeCycleFilter.class));
    HttpStatusCode statusCode = actualInstanceState.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualInstanceState.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualInstanceState.hasBody());
    assertTrue(actualInstanceState.getHeaders().isEmpty());
    assertSame(workflowNodesStateView, actualInstanceState.getBody());
  }

  /**
   * Test
   * {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}.
   * <p>
   * Method under test:
   * {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowGraphNodes(String, String, Long)")
  void testGetWorkflowGraphNodes() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    when(monitoringService.getWorkflowDefinition(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(buildResult);

    // Act
    ResponseEntity<WorkflowNodesView> actualWorkflowGraphNodes = workflowsApiController.getWorkflowGraphNodes("42",
        "ABC123", 1L);

    // Assert
    verify(monitoringService).getWorkflowDefinition(eq("42"), eq(1L));
    HttpStatusCode statusCode = actualWorkflowGraphNodes.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    WorkflowNodesView body = actualWorkflowGraphNodes.getBody();
    assertEquals("42", body.getWorkflowId());
    assertEquals(1L, body.getVersion().longValue());
    assertEquals(200, actualWorkflowGraphNodes.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(body.getFlowNodes().isEmpty());
    assertTrue(body.getVariables().isEmpty());
    assertTrue(actualWorkflowGraphNodes.hasBody());
    assertTrue(actualWorkflowGraphNodes.getHeaders().isEmpty());
  }

  /**
   * Test
   * {@link WorkflowsApiController#listWorkflowGlobalVariables(String, String, String, Instant, Instant)}.
   * <p>
   * Method under test:
   * {@link WorkflowsApiController#listWorkflowGlobalVariables(String, String, String, Instant, Instant)}
   */
  @Test
  @DisplayName("Test listWorkflowGlobalVariables(String, String, String, Instant, Instant)")
  void testListWorkflowGlobalVariables() {
    // Arrange
    when(monitoringService.listWorkflowInstanceGlobalVars(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<Instant>any(), Mockito.<Instant>any())).thenReturn(new ArrayList<>());
    Instant updatedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    ResponseEntity<List<VariableView>> actualListWorkflowGlobalVariablesResult = workflowsApiController
        .listWorkflowGlobalVariables("42", "42", "ABC123", updatedBefore,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(monitoringService).listWorkflowInstanceGlobalVars(eq("42"), eq("42"), isA(Instant.class),
        isA(Instant.class));
    HttpStatusCode statusCode = actualListWorkflowGlobalVariablesResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListWorkflowGlobalVariablesResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListWorkflowGlobalVariablesResult.getBody().isEmpty());
    assertTrue(actualListWorkflowGlobalVariablesResult.hasBody());
    assertTrue(actualListWorkflowGlobalVariablesResult.getHeaders().isEmpty());
  }
}
