package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.monitoring.service.MonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowsApiControllerClaude_listAllWorkflowsTest {

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
  void listAllWorkflows_withEmptyList_shouldReturnEmptyList() {
    // Given: MonitoringService returns an empty list
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.emptyList());

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with empty list
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEmpty();
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  @Test
  void listAllWorkflows_withSingleWorkflow_shouldReturnSingleWorkflow() {
    // Given: MonitoringService returns a single workflow
    WorkflowView workflow = WorkflowView.builder()
        .id("workflow-1")
        .version(1L)
        .createdBy(100L)
        .build();
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.singletonList(workflow));

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with single workflow
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getId()).isEqualTo("workflow-1");
    assertThat(response.getBody().get(0).getVersion()).isEqualTo(1L);
    assertThat(response.getBody().get(0).getCreatedBy()).isEqualTo(100L);
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  @Test
  void listAllWorkflows_withMultipleWorkflows_shouldReturnAllWorkflows() {
    // Given: MonitoringService returns multiple workflows
    WorkflowView workflow1 = WorkflowView.builder()
        .id("workflow-1")
        .version(1L)
        .createdBy(100L)
        .build();
    WorkflowView workflow2 = WorkflowView.builder()
        .id("workflow-2")
        .version(2L)
        .createdBy(200L)
        .build();
    WorkflowView workflow3 = WorkflowView.builder()
        .id("workflow-3")
        .version(3L)
        .createdBy(300L)
        .build();
    List<WorkflowView> workflows = Arrays.asList(workflow1, workflow2, workflow3);
    when(monitoringService.listAllWorkflows()).thenReturn(workflows);

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with all workflows
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(3);
    assertThat(response.getBody().get(0).getId()).isEqualTo("workflow-1");
    assertThat(response.getBody().get(1).getId()).isEqualTo("workflow-2");
    assertThat(response.getBody().get(2).getId()).isEqualTo("workflow-3");
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  // ==================== Token Parameter Tests ====================

  @Test
  void listAllWorkflows_withNullToken_shouldStillCallMonitoringService() {
    // Given: MonitoringService returns workflows and token is null
    WorkflowView workflow = WorkflowView.builder()
        .id("workflow-1")
        .version(1L)
        .build();
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.singletonList(workflow));

    // When: Calling listAllWorkflows with null token
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows(null);

    // Then: Should still return OK (authorization is handled by @Authorized annotation/aspect)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  @Test
  void listAllWorkflows_withEmptyToken_shouldStillCallMonitoringService() {
    // Given: MonitoringService returns workflows and token is empty
    WorkflowView workflow = WorkflowView.builder()
        .id("workflow-1")
        .version(1L)
        .build();
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.singletonList(workflow));

    // When: Calling listAllWorkflows with empty token
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("");

    // Then: Should still return OK (authorization is handled by @Authorized annotation/aspect)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  @Test
  void listAllWorkflows_withValidToken_shouldCallMonitoringService() {
    // Given: MonitoringService returns workflows and token is valid
    WorkflowView workflow = WorkflowView.builder()
        .id("workflow-1")
        .version(1L)
        .build();
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.singletonList(workflow));

    // When: Calling listAllWorkflows with a valid token
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("valid-token-12345");

    // Then: Should return OK
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  // ==================== WorkflowView Field Tests ====================

  @Test
  void listAllWorkflows_withWorkflowHavingNullVersion_shouldReturnWorkflowWithNullVersion() {
    // Given: MonitoringService returns a workflow with null version
    WorkflowView workflow = WorkflowView.builder()
        .id("workflow-1")
        .version(null)
        .createdBy(100L)
        .build();
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.singletonList(workflow));

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with workflow having null version
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getId()).isEqualTo("workflow-1");
    assertThat(response.getBody().get(0).getVersion()).isNull();
    assertThat(response.getBody().get(0).getCreatedBy()).isEqualTo(100L);
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  @Test
  void listAllWorkflows_withWorkflowHavingNullCreatedBy_shouldReturnWorkflowWithNullCreatedBy() {
    // Given: MonitoringService returns a workflow with null createdBy
    WorkflowView workflow = WorkflowView.builder()
        .id("workflow-1")
        .version(1L)
        .createdBy(null)
        .build();
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.singletonList(workflow));

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with workflow having null createdBy
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getId()).isEqualTo("workflow-1");
    assertThat(response.getBody().get(0).getVersion()).isEqualTo(1L);
    assertThat(response.getBody().get(0).getCreatedBy()).isNull();
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  @Test
  void listAllWorkflows_withWorkflowHavingAllNullFields_shouldReturnWorkflow() {
    // Given: MonitoringService returns a workflow with null id, version, and createdBy
    WorkflowView workflow = WorkflowView.builder()
        .id(null)
        .version(null)
        .createdBy(null)
        .build();
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.singletonList(workflow));

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with workflow having all null fields
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getId()).isNull();
    assertThat(response.getBody().get(0).getVersion()).isNull();
    assertThat(response.getBody().get(0).getCreatedBy()).isNull();
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  // ==================== Large Dataset Tests ====================

  @Test
  void listAllWorkflows_withLargeNumberOfWorkflows_shouldReturnAllWorkflows() {
    // Given: MonitoringService returns a large number of workflows
    List<WorkflowView> workflows = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      workflows.add(WorkflowView.builder()
          .id("workflow-" + i)
          .version((long) i)
          .createdBy((long) (i * 10))
          .build());
    }
    when(monitoringService.listAllWorkflows()).thenReturn(workflows);

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with all 100 workflows
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(100);
    assertThat(response.getBody().get(0).getId()).isEqualTo("workflow-0");
    assertThat(response.getBody().get(99).getId()).isEqualTo("workflow-99");
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  // ==================== Service Interaction Tests ====================

  @Test
  void listAllWorkflows_shouldCallMonitoringServiceOnce() {
    // Given: MonitoringService returns workflows
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.emptyList());

    // When: Calling listAllWorkflows
    controller.listAllWorkflows("test-token");

    // Then: Should call monitoringService exactly once
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  @Test
  void listAllWorkflows_shouldNotInteractWithWorkflowEngine() {
    // Given: MonitoringService returns workflows
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.emptyList());

    // When: Calling listAllWorkflows
    controller.listAllWorkflows("test-token");

    // Then: Should not interact with workflowEngine at all
    verifyNoInteractions(workflowEngine);
  }

  // ==================== Response Entity Tests ====================

  @Test
  void listAllWorkflows_shouldReturnHttpStatusOK() {
    // Given: MonitoringService returns workflows
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.emptyList());

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return HTTP 200 OK
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void listAllWorkflows_shouldReturnNonNullBody() {
    // Given: MonitoringService returns workflows
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.emptyList());

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Body should not be null
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  void listAllWorkflows_shouldReturnSameListAsMonitoringService() {
    // Given: MonitoringService returns specific workflows
    List<WorkflowView> expectedWorkflows = Arrays.asList(
        WorkflowView.builder().id("wf-1").version(1L).build(),
        WorkflowView.builder().id("wf-2").version(2L).build()
    );
    when(monitoringService.listAllWorkflows()).thenReturn(expectedWorkflows);

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Body should be the same list returned by monitoringService
    assertThat(response.getBody()).isSameAs(expectedWorkflows);
  }

  // ==================== Edge Cases ====================

  @Test
  void listAllWorkflows_withWorkflowsHavingSpecialCharactersInId_shouldReturnWorkflows() {
    // Given: MonitoringService returns workflows with special characters in IDs
    WorkflowView workflow1 = WorkflowView.builder()
        .id("workflow-with-dashes")
        .version(1L)
        .build();
    WorkflowView workflow2 = WorkflowView.builder()
        .id("workflow_with_underscores")
        .version(2L)
        .build();
    WorkflowView workflow3 = WorkflowView.builder()
        .id("workflow.with.dots")
        .version(3L)
        .build();
    List<WorkflowView> workflows = Arrays.asList(workflow1, workflow2, workflow3);
    when(monitoringService.listAllWorkflows()).thenReturn(workflows);

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with all workflows
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(3);
    assertThat(response.getBody().get(0).getId()).isEqualTo("workflow-with-dashes");
    assertThat(response.getBody().get(1).getId()).isEqualTo("workflow_with_underscores");
    assertThat(response.getBody().get(2).getId()).isEqualTo("workflow.with.dots");
    verify(monitoringService, times(1)).listAllWorkflows();
  }

  @Test
  void listAllWorkflows_withWorkflowsHavingLargeVersionNumbers_shouldReturnWorkflows() {
    // Given: MonitoringService returns workflows with large version numbers
    WorkflowView workflow = WorkflowView.builder()
        .id("workflow-1")
        .version(Long.MAX_VALUE)
        .createdBy(Long.MAX_VALUE)
        .build();
    when(monitoringService.listAllWorkflows()).thenReturn(Collections.singletonList(workflow));

    // When: Calling listAllWorkflows
    ResponseEntity<List<WorkflowView>> response = controller.listAllWorkflows("test-token");

    // Then: Should return OK with workflow having large version
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getVersion()).isEqualTo(Long.MAX_VALUE);
    assertThat(response.getBody().get(0).getCreatedBy()).isEqualTo(Long.MAX_VALUE);
    verify(monitoringService, times(1)).listAllWorkflows();
  }
}
