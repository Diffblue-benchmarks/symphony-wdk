package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
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

import java.time.Duration;
import java.time.Instant;
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
class WorkflowsApiControllerClaude_listWorkflowInstancesTest {

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
  void listWorkflowInstances_withEmptyList_shouldReturnEmptyList() {
    // Given: MonitoringService returns an empty list
    String workflowId = "workflow-1";
    String status = "COMPLETED";
    Long version = 1L;
    when(monitoringService.listWorkflowInstances(workflowId, status, version))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, version);

    // Then: Should return OK with empty list
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEmpty();
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, status, version);
  }

  @Test
  void listWorkflowInstances_withSingleInstance_shouldReturnSingleInstance() {
    // Given: MonitoringService returns a single workflow instance
    String workflowId = "workflow-1";
    String status = "COMPLETED";
    Long version = 1L;
    WorkflowInstView instance = WorkflowInstView.builder()
        .id("workflow-1")
        .instanceId("instance-1")
        .version(1L)
        .status(StatusEnum.COMPLETED)
        .startDate(Instant.now())
        .endDate(Instant.now())
        .duration(Duration.ofMinutes(5))
        .build();
    when(monitoringService.listWorkflowInstances(workflowId, status, version))
        .thenReturn(Collections.singletonList(instance));

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, version);

    // Then: Should return OK with single instance
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getId()).isEqualTo("workflow-1");
    assertThat(response.getBody().get(0).getInstanceId()).isEqualTo("instance-1");
    assertThat(response.getBody().get(0).getStatus()).isEqualTo(StatusEnum.COMPLETED);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, status, version);
  }

  @Test
  void listWorkflowInstances_withMultipleInstances_shouldReturnAllInstances() {
    // Given: MonitoringService returns multiple workflow instances
    String workflowId = "workflow-1";
    String status = null;
    Long version = null;
    WorkflowInstView instance1 = WorkflowInstView.builder()
        .id("workflow-1")
        .instanceId("instance-1")
        .status(StatusEnum.COMPLETED)
        .build();
    WorkflowInstView instance2 = WorkflowInstView.builder()
        .id("workflow-1")
        .instanceId("instance-2")
        .status(StatusEnum.PENDING)
        .build();
    WorkflowInstView instance3 = WorkflowInstView.builder()
        .id("workflow-1")
        .instanceId("instance-3")
        .status(StatusEnum.FAILED)
        .build();
    List<WorkflowInstView> instances = Arrays.asList(instance1, instance2, instance3);
    when(monitoringService.listWorkflowInstances(workflowId, status, version))
        .thenReturn(instances);

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, version);

    // Then: Should return OK with all instances
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(3);
    assertThat(response.getBody().get(0).getInstanceId()).isEqualTo("instance-1");
    assertThat(response.getBody().get(1).getInstanceId()).isEqualTo("instance-2");
    assertThat(response.getBody().get(2).getInstanceId()).isEqualTo("instance-3");
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, status, version);
  }

  // ==================== WorkflowId Parameter Tests ====================

  @Test
  void listWorkflowInstances_withValidWorkflowId_shouldPassToMonitoringService() {
    // Given: Valid workflow ID
    String workflowId = "my-workflow-123";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should pass the workflow ID to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, null);
  }

  @Test
  void listWorkflowInstances_withWorkflowIdContainingSpecialCharacters_shouldWork() {
    // Given: Workflow ID with special characters
    String workflowId = "workflow-with-dashes_and_underscores.and.dots";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should work correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, null);
  }

  @Test
  void listWorkflowInstances_withNullWorkflowId_shouldPassNullToMonitoringService() {
    // Given: Null workflow ID
    String workflowId = null;
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should pass null to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(null, null, null);
  }

  @Test
  void listWorkflowInstances_withEmptyWorkflowId_shouldPassEmptyStringToMonitoringService() {
    // Given: Empty workflow ID
    String workflowId = "";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should pass empty string to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances("", null, null);
  }

  // ==================== Token Parameter Tests ====================

  @Test
  void listWorkflowInstances_withNullToken_shouldStillCallMonitoringService() {
    // Given: Token is null
    String workflowId = "workflow-1";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances with null token
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, null, null, null);

    // Then: Should still call monitoring service (authorization handled by @Authorized)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, null);
  }

  @Test
  void listWorkflowInstances_withEmptyToken_shouldStillCallMonitoringService() {
    // Given: Token is empty
    String workflowId = "workflow-1";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances with empty token
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "", null, null);

    // Then: Should still call monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, null);
  }

  @Test
  void listWorkflowInstances_withValidToken_shouldCallMonitoringService() {
    // Given: Valid token
    String workflowId = "workflow-1";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances with valid token
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "valid-token-12345", null, null);

    // Then: Should call monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, null);
  }

  // ==================== Status Parameter Tests ====================

  @Test
  void listWorkflowInstances_withNullStatus_shouldPassNullToMonitoringService() {
    // Given: Status is null
    String workflowId = "workflow-1";
    String status = null;
    when(monitoringService.listWorkflowInstances(workflowId, status, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, null);

    // Then: Should pass null status to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, null);
  }

  @Test
  void listWorkflowInstances_withCompletedStatus_shouldPassToMonitoringService() {
    // Given: Status is COMPLETED
    String workflowId = "workflow-1";
    String status = "COMPLETED";
    when(monitoringService.listWorkflowInstances(workflowId, status, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, null);

    // Then: Should pass COMPLETED status to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, "COMPLETED", null);
  }

  @Test
  void listWorkflowInstances_withPendingStatus_shouldPassToMonitoringService() {
    // Given: Status is PENDING
    String workflowId = "workflow-1";
    String status = "PENDING";
    when(monitoringService.listWorkflowInstances(workflowId, status, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, null);

    // Then: Should pass PENDING status to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, "PENDING", null);
  }

  @Test
  void listWorkflowInstances_withFailedStatus_shouldPassToMonitoringService() {
    // Given: Status is FAILED
    String workflowId = "workflow-1";
    String status = "FAILED";
    when(monitoringService.listWorkflowInstances(workflowId, status, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, null);

    // Then: Should pass FAILED status to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, "FAILED", null);
  }

  @Test
  void listWorkflowInstances_withEmptyStatus_shouldPassEmptyStringToMonitoringService() {
    // Given: Status is empty string
    String workflowId = "workflow-1";
    String status = "";
    when(monitoringService.listWorkflowInstances(workflowId, status, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, null);

    // Then: Should pass empty string to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, "", null);
  }

  @Test
  void listWorkflowInstances_withArbitraryStatus_shouldPassToMonitoringService() {
    // Given: Status is an arbitrary string (validation happens in MonitoringService)
    String workflowId = "workflow-1";
    String status = "SOME_STATUS";
    when(monitoringService.listWorkflowInstances(workflowId, status, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, null);

    // Then: Should pass the status to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, "SOME_STATUS", null);
  }

  // ==================== Version Parameter Tests ====================

  @Test
  void listWorkflowInstances_withNullVersion_shouldPassNullToMonitoringService() {
    // Given: Version is null
    String workflowId = "workflow-1";
    Long version = null;
    when(monitoringService.listWorkflowInstances(workflowId, null, version))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, version);

    // Then: Should pass null version to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, null);
  }

  @Test
  void listWorkflowInstances_withVersion1_shouldPassToMonitoringService() {
    // Given: Version is 1
    String workflowId = "workflow-1";
    Long version = 1L;
    when(monitoringService.listWorkflowInstances(workflowId, null, version))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, version);

    // Then: Should pass version 1 to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, 1L);
  }

  @Test
  void listWorkflowInstances_withLargeVersion_shouldPassToMonitoringService() {
    // Given: Version is a large number
    String workflowId = "workflow-1";
    Long version = 999999L;
    when(monitoringService.listWorkflowInstances(workflowId, null, version))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, version);

    // Then: Should pass large version to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, 999999L);
  }

  @Test
  void listWorkflowInstances_withVersionZero_shouldPassToMonitoringService() {
    // Given: Version is 0
    String workflowId = "workflow-1";
    Long version = 0L;
    when(monitoringService.listWorkflowInstances(workflowId, null, version))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, version);

    // Then: Should pass version 0 to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, 0L);
  }

  // ==================== Combined Parameters Tests ====================

  @Test
  void listWorkflowInstances_withAllParametersProvided_shouldPassAllToMonitoringService() {
    // Given: All parameters are provided
    String workflowId = "workflow-1";
    String status = "COMPLETED";
    Long version = 5L;
    when(monitoringService.listWorkflowInstances(workflowId, status, version))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, version);

    // Then: Should pass all parameters to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances("workflow-1", "COMPLETED", 5L);
  }

  @Test
  void listWorkflowInstances_withAllParametersNull_shouldPassAllNullsToMonitoringService() {
    // Given: All parameters are null
    String workflowId = null;
    String status = null;
    Long version = null;
    when(monitoringService.listWorkflowInstances(workflowId, status, version))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, null, status, version);

    // Then: Should pass all nulls to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances(null, null, null);
  }

  @Test
  void listWorkflowInstances_withMixedParameters_shouldPassCorrectlyToMonitoringService() {
    // Given: Mixed parameters (some null, some not)
    String workflowId = "workflow-123";
    String status = null;
    Long version = 3L;
    when(monitoringService.listWorkflowInstances(workflowId, status, version))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", status, version);

    // Then: Should pass correct parameters to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstances("workflow-123", null, 3L);
  }

  // ==================== WorkflowInstView Field Tests ====================

  @Test
  void listWorkflowInstances_withInstanceHavingAllFields_shouldReturnInstance() {
    // Given: Instance with all fields populated
    String workflowId = "workflow-1";
    Instant now = Instant.now();
    Instant later = now.plusSeconds(300);
    WorkflowInstView instance = WorkflowInstView.builder()
        .id("workflow-1")
        .version(1L)
        .instanceId("instance-123")
        .status(StatusEnum.COMPLETED)
        .startDate(now)
        .endDate(later)
        .duration(Duration.ofSeconds(300))
        .build();
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.singletonList(instance));

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should return instance with all fields
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    WorkflowInstView result = response.getBody().get(0);
    assertThat(result.getId()).isEqualTo("workflow-1");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getInstanceId()).isEqualTo("instance-123");
    assertThat(result.getStatus()).isEqualTo(StatusEnum.COMPLETED);
    assertThat(result.getStartDate()).isEqualTo(now);
    assertThat(result.getEndDate()).isEqualTo(later);
    assertThat(result.getDuration()).isEqualTo(Duration.ofSeconds(300));
  }

  @Test
  void listWorkflowInstances_withInstanceHavingNullFields_shouldReturnInstance() {
    // Given: Instance with null fields
    String workflowId = "workflow-1";
    WorkflowInstView instance = WorkflowInstView.builder()
        .id(null)
        .version(null)
        .instanceId(null)
        .status(null)
        .startDate(null)
        .endDate(null)
        .duration(null)
        .build();
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.singletonList(instance));

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should return instance with null fields
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    WorkflowInstView result = response.getBody().get(0);
    assertThat(result.getId()).isNull();
    assertThat(result.getVersion()).isNull();
    assertThat(result.getInstanceId()).isNull();
    assertThat(result.getStatus()).isNull();
    assertThat(result.getStartDate()).isNull();
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void listWorkflowInstances_withPendingStatusInstance_shouldReturnPendingInstance() {
    // Given: Instance with PENDING status
    String workflowId = "workflow-1";
    WorkflowInstView instance = WorkflowInstView.builder()
        .instanceId("instance-1")
        .status(StatusEnum.PENDING)
        .build();
    when(monitoringService.listWorkflowInstances(workflowId, "PENDING", null))
        .thenReturn(Collections.singletonList(instance));

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", "PENDING", null);

    // Then: Should return pending instance
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getStatus()).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void listWorkflowInstances_withFailedStatusInstance_shouldReturnFailedInstance() {
    // Given: Instance with FAILED status
    String workflowId = "workflow-1";
    WorkflowInstView instance = WorkflowInstView.builder()
        .instanceId("instance-1")
        .status(StatusEnum.FAILED)
        .build();
    when(monitoringService.listWorkflowInstances(workflowId, "FAILED", null))
        .thenReturn(Collections.singletonList(instance));

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", "FAILED", null);

    // Then: Should return failed instance
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getStatus()).isEqualTo(StatusEnum.FAILED);
  }

  // ==================== Large Dataset Tests ====================

  @Test
  void listWorkflowInstances_withLargeNumberOfInstances_shouldReturnAllInstances() {
    // Given: MonitoringService returns a large number of instances
    String workflowId = "workflow-1";
    List<WorkflowInstView> instances = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      instances.add(WorkflowInstView.builder()
          .id(workflowId)
          .instanceId("instance-" + i)
          .version((long) i)
          .status(i % 2 == 0 ? StatusEnum.COMPLETED : StatusEnum.PENDING)
          .build());
    }
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(instances);

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should return all 100 instances
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(100);
    assertThat(response.getBody().get(0).getInstanceId()).isEqualTo("instance-0");
    assertThat(response.getBody().get(99).getInstanceId()).isEqualTo("instance-99");
  }

  // ==================== Service Interaction Tests ====================

  @Test
  void listWorkflowInstances_shouldCallMonitoringServiceOnce() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should call monitoringService exactly once
    verify(monitoringService, times(1)).listWorkflowInstances(workflowId, null, null);
  }

  @Test
  void listWorkflowInstances_shouldNotInteractWithWorkflowEngine() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should not interact with workflowEngine at all
    verifyNoInteractions(workflowEngine);
  }

  // ==================== Response Entity Tests ====================

  @Test
  void listWorkflowInstances_shouldReturnHttpStatusOK() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should return HTTP 200 OK
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void listWorkflowInstances_shouldReturnNonNullBody() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Body should not be null
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  void listWorkflowInstances_shouldReturnSameListAsMonitoringService() {
    // Given: MonitoringService returns specific instances
    String workflowId = "workflow-1";
    List<WorkflowInstView> expectedInstances = Arrays.asList(
        WorkflowInstView.builder().instanceId("inst-1").build(),
        WorkflowInstView.builder().instanceId("inst-2").build()
    );
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(expectedInstances);

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Body should be the same list returned by monitoringService
    assertThat(response.getBody()).isSameAs(expectedInstances);
  }

  // ==================== Edge Cases ====================

  @Test
  void listWorkflowInstances_withInstancesOfDifferentStatuses_shouldReturnAllStatuses() {
    // Given: Instances with different statuses
    String workflowId = "workflow-1";
    List<WorkflowInstView> instances = Arrays.asList(
        WorkflowInstView.builder().instanceId("inst-1").status(StatusEnum.PENDING).build(),
        WorkflowInstView.builder().instanceId("inst-2").status(StatusEnum.COMPLETED).build(),
        WorkflowInstView.builder().instanceId("inst-3").status(StatusEnum.FAILED).build()
    );
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(instances);

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should return instances with all different statuses
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(3);
    assertThat(response.getBody().get(0).getStatus()).isEqualTo(StatusEnum.PENDING);
    assertThat(response.getBody().get(1).getStatus()).isEqualTo(StatusEnum.COMPLETED);
    assertThat(response.getBody().get(2).getStatus()).isEqualTo(StatusEnum.FAILED);
  }

  @Test
  void listWorkflowInstances_withVeryLongDuration_shouldReturnCorrectly() {
    // Given: Instance with very long duration
    String workflowId = "workflow-1";
    Duration longDuration = Duration.ofDays(365);
    WorkflowInstView instance = WorkflowInstView.builder()
        .instanceId("instance-1")
        .duration(longDuration)
        .build();
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.singletonList(instance));

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should return instance with long duration
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getDuration()).isEqualTo(Duration.ofDays(365));
  }

  @Test
  void listWorkflowInstances_withZeroDuration_shouldReturnCorrectly() {
    // Given: Instance with zero duration
    String workflowId = "workflow-1";
    WorkflowInstView instance = WorkflowInstView.builder()
        .instanceId("instance-1")
        .duration(Duration.ZERO)
        .build();
    when(monitoringService.listWorkflowInstances(workflowId, null, null))
        .thenReturn(Collections.singletonList(instance));

    // When: Calling listWorkflowInstances
    ResponseEntity<List<WorkflowInstView>> response =
        controller.listWorkflowInstances(workflowId, "test-token", null, null);

    // Then: Should return instance with zero duration
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getDuration()).isEqualTo(Duration.ZERO);
  }
}
