package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.SecretView;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.expiration.WorkflowExpirationService;
import com.symphony.bdk.workflow.logs.LogsStreamingService;
import com.symphony.bdk.workflow.management.WorkflowManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowsMgtApiControllerClaudeTest {

  @Mock
  private WorkflowManagementService workflowManagementService;

  @Mock
  private WorkflowExpirationService workflowExpirationService;

  @Mock
  private LogsStreamingService logsStreamingService;

  @Mock
  private SecretKeeper secretKeeper;

  private WorkflowsMgtApiController controller;

  @BeforeEach
  void setUp() {
    controller = new WorkflowsMgtApiController(
        workflowManagementService,
        workflowExpirationService,
        logsStreamingService,
        secretKeeper
    );
  }

  // ==================== saveAndDeploySwadl Tests ====================

  @Test
  void saveAndDeploySwadl_withValidSwadl_shouldCallDeployAndReturnNoContent() {
    // Given: A valid SwadlView
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: test-workflow")
        .description("Test workflow")
        .createdBy(100L)
        .build();

    // When: Calling saveAndDeploySwadl
    ResponseEntity<Void> response = controller.saveAndDeploySwadl("test-token", swadlView);

    // Then: Should call deploy and return HTTP 204 No Content
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();
    verify(workflowManagementService, times(1)).deploy(swadlView);
    verifyNoInteractions(workflowExpirationService, logsStreamingService, secretKeeper);
  }

  @Test
  void saveAndDeploySwadl_withMinimalSwadl_shouldCallDeploy() {
    // Given: A minimal SwadlView with only SWADL content
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: minimal-workflow")
        .build();

    // When: Calling saveAndDeploySwadl
    ResponseEntity<Void> response = controller.saveAndDeploySwadl("test-token", swadlView);

    // Then: Should call deploy successfully
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).deploy(swadlView);
  }

  @Test
  void saveAndDeploySwadl_withNullToken_shouldStillCallDeploy() {
    // Given: A SwadlView with null token (authorization handled by aspect)
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: test-workflow")
        .build();

    // When: Calling saveAndDeploySwadl with null token
    ResponseEntity<Void> response = controller.saveAndDeploySwadl(null, swadlView);

    // Then: Should still work (authorization handled by @Authorized annotation)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).deploy(swadlView);
  }

  @Test
  void saveAndDeploySwadl_shouldPassExactSwadlViewToService() {
    // Given: A specific SwadlView instance
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: specific-workflow\nactivities:\n  - send-message")
        .description("Complex workflow")
        .createdBy(999L)
        .build();

    // When: Calling saveAndDeploySwadl
    controller.saveAndDeploySwadl("test-token", swadlView);

    // Then: Should pass the exact same instance to the service
    ArgumentCaptor<SwadlView> captor = ArgumentCaptor.forClass(SwadlView.class);
    verify(workflowManagementService).deploy(captor.capture());
    assertThat(captor.getValue()).isSameAs(swadlView);
  }

  // ==================== updateSwadl Tests ====================

  @Test
  void updateSwadl_withValidSwadl_shouldCallUpdateAndReturnNoContent() {
    // Given: A valid SwadlView for update
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: updated-workflow")
        .description("Updated workflow")
        .createdBy(200L)
        .build();

    // When: Calling updateSwadl
    ResponseEntity<Void> response = controller.updateSwadl("test-token", swadlView);

    // Then: Should call update and return HTTP 204 No Content
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();
    verify(workflowManagementService, times(1)).update(swadlView);
    verifyNoInteractions(workflowExpirationService, logsStreamingService, secretKeeper);
  }

  @Test
  void updateSwadl_withMinimalSwadl_shouldCallUpdate() {
    // Given: A minimal SwadlView for update
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: minimal-update")
        .build();

    // When: Calling updateSwadl
    ResponseEntity<Void> response = controller.updateSwadl("test-token", swadlView);

    // Then: Should call update successfully
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).update(swadlView);
  }

  @Test
  void updateSwadl_withNullToken_shouldStillCallUpdate() {
    // Given: A SwadlView with null token
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: test-workflow")
        .build();

    // When: Calling updateSwadl with null token
    ResponseEntity<Void> response = controller.updateSwadl(null, swadlView);

    // Then: Should still work
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).update(swadlView);
  }

  @Test
  void updateSwadl_shouldPassExactSwadlViewToService() {
    // Given: A specific SwadlView instance
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: precise-workflow")
        .description("Precise update")
        .build();

    // When: Calling updateSwadl
    controller.updateSwadl("test-token", swadlView);

    // Then: Should pass the exact same instance to the service
    ArgumentCaptor<SwadlView> captor = ArgumentCaptor.forClass(SwadlView.class);
    verify(workflowManagementService).update(captor.capture());
    assertThat(captor.getValue()).isSameAs(swadlView);
  }

  // ==================== getVersionedWorkflow Tests ====================

  @Test
  void getVersionedWorkflow_withNoVersionAndAllVersionsFalse_shouldGetActiveVersion() {
    // Given: version is null and allVersions is false
    VersionedWorkflowView workflow = VersionedWorkflowView.builder()
        .workflowId("workflow-1")
        .version(1L)
        .active(true)
        .build();
    when(workflowManagementService.get("workflow-1")).thenReturn(Optional.of(workflow));

    // When: Calling getVersionedWorkflow with no version and allVersions=false
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "workflow-1", null, false);

    // Then: Should return active version
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0)).isSameAs(workflow);
    verify(workflowManagementService, times(1)).get("workflow-1");
    verify(workflowManagementService, never()).get(any(String.class), any(Long.class));
    verify(workflowManagementService, never()).getAllVersions(any(String.class));
  }

  @Test
  void getVersionedWorkflow_withNoVersionAndAllVersionsFalse_whenWorkflowNotFound_shouldReturnEmptyList() {
    // Given: version is null, allVersions is false, and workflow not found
    when(workflowManagementService.get("non-existent")).thenReturn(Optional.empty());

    // When: Calling getVersionedWorkflow
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "non-existent", null, false);

    // Then: Should return empty list
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEmpty();
    verify(workflowManagementService, times(1)).get("non-existent");
  }

  @Test
  void getVersionedWorkflow_withAllVersionsTrue_shouldGetAllVersions() {
    // Given: allVersions is true
    VersionedWorkflowView workflow1 = VersionedWorkflowView.builder()
        .workflowId("workflow-1")
        .version(1L)
        .active(false)
        .build();
    VersionedWorkflowView workflow2 = VersionedWorkflowView.builder()
        .workflowId("workflow-1")
        .version(2L)
        .active(true)
        .build();
    List<VersionedWorkflowView> allVersions = Arrays.asList(workflow1, workflow2);
    when(workflowManagementService.getAllVersions("workflow-1")).thenReturn(allVersions);

    // When: Calling getVersionedWorkflow with allVersions=true
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "workflow-1", null, true);

    // Then: Should return all versions
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(2);
    assertThat(response.getBody()).containsExactly(workflow1, workflow2);
    verify(workflowManagementService, times(1)).getAllVersions("workflow-1");
    verify(workflowManagementService, never()).get(any(String.class));
    verify(workflowManagementService, never()).get(any(String.class), any(Long.class));
  }

  @Test
  void getVersionedWorkflow_withAllVersionsTrue_whenNoVersionsFound_shouldReturnEmptyList() {
    // Given: allVersions is true but no versions exist
    when(workflowManagementService.getAllVersions("workflow-1")).thenReturn(Collections.emptyList());

    // When: Calling getVersionedWorkflow with allVersions=true
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "workflow-1", null, true);

    // Then: Should return empty list
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEmpty();
    verify(workflowManagementService, times(1)).getAllVersions("workflow-1");
  }

  @Test
  void getVersionedWorkflow_withSpecificVersion_shouldGetThatVersion() {
    // Given: a specific version is requested
    VersionedWorkflowView workflow = VersionedWorkflowView.builder()
        .workflowId("workflow-1")
        .version(5L)
        .active(false)
        .build();
    when(workflowManagementService.get("workflow-1", 5L)).thenReturn(Optional.of(workflow));

    // When: Calling getVersionedWorkflow with version=5
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "workflow-1", 5L, false);

    // Then: Should return that specific version
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0)).isSameAs(workflow);
    assertThat(response.getBody().get(0).getVersion()).isEqualTo(5L);
    verify(workflowManagementService, times(1)).get("workflow-1", 5L);
    verify(workflowManagementService, never()).get(any(String.class));
    verify(workflowManagementService, never()).getAllVersions(any(String.class));
  }

  @Test
  void getVersionedWorkflow_withSpecificVersion_whenVersionNotFound_shouldReturnEmptyList() {
    // Given: a specific version that doesn't exist
    when(workflowManagementService.get("workflow-1", 99L)).thenReturn(Optional.empty());

    // When: Calling getVersionedWorkflow with non-existent version
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "workflow-1", 99L, false);

    // Then: Should return empty list
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEmpty();
    verify(workflowManagementService, times(1)).get("workflow-1", 99L);
  }

  @Test
  void getVersionedWorkflow_withSpecificVersionAndAllVersionsTrue_shouldGetAllVersions() {
    // Given: both version and allVersions are set (allVersions takes precedence)
    VersionedWorkflowView workflow1 = VersionedWorkflowView.builder()
        .workflowId("workflow-1")
        .version(1L)
        .build();
    VersionedWorkflowView workflow2 = VersionedWorkflowView.builder()
        .workflowId("workflow-1")
        .version(2L)
        .build();
    List<VersionedWorkflowView> allVersions = Arrays.asList(workflow1, workflow2);
    when(workflowManagementService.getAllVersions("workflow-1")).thenReturn(allVersions);

    // When: Calling with both version and allVersions=true
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "workflow-1", 5L, true);

    // Then: Should return all versions (allVersions takes precedence)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(2);
    verify(workflowManagementService, times(1)).getAllVersions("workflow-1");
    verify(workflowManagementService, never()).get(any(String.class), any(Long.class));
  }

  @Test
  void getVersionedWorkflow_withVersion1_shouldGetVersion1() {
    // Given: version 1 is requested
    VersionedWorkflowView workflow = VersionedWorkflowView.builder()
        .workflowId("workflow-1")
        .version(1L)
        .build();
    when(workflowManagementService.get("workflow-1", 1L)).thenReturn(Optional.of(workflow));

    // When: Calling getVersionedWorkflow with version=1
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "workflow-1", 1L, false);

    // Then: Should return version 1
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getVersion()).isEqualTo(1L);
    verify(workflowManagementService, times(1)).get("workflow-1", 1L);
  }

  @Test
  void getVersionedWorkflow_withLargeVersionNumber_shouldHandleCorrectly() {
    // Given: a large version number
    VersionedWorkflowView workflow = VersionedWorkflowView.builder()
        .workflowId("workflow-1")
        .version(Long.MAX_VALUE)
        .build();
    when(workflowManagementService.get("workflow-1", Long.MAX_VALUE)).thenReturn(Optional.of(workflow));

    // When: Calling getVersionedWorkflow with max version
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "workflow-1", Long.MAX_VALUE, false);

    // Then: Should handle large version correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getVersion()).isEqualTo(Long.MAX_VALUE);
  }

  @Test
  void getVersionedWorkflow_withManyVersions_shouldReturnAllVersions() {
    // Given: many versions exist
    List<VersionedWorkflowView> manyVersions = Arrays.asList(
        VersionedWorkflowView.builder().workflowId("wf").version(1L).build(),
        VersionedWorkflowView.builder().workflowId("wf").version(2L).build(),
        VersionedWorkflowView.builder().workflowId("wf").version(3L).build(),
        VersionedWorkflowView.builder().workflowId("wf").version(4L).build(),
        VersionedWorkflowView.builder().workflowId("wf").version(5L).build()
    );
    when(workflowManagementService.getAllVersions("wf")).thenReturn(manyVersions);

    // When: Calling getVersionedWorkflow with allVersions=true
    ResponseEntity<List<VersionedWorkflowView>> response =
        controller.getVersionedWorkflow("test-token", "wf", null, true);

    // Then: Should return all 5 versions
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(5);
  }

  // ==================== deleteWorkflowByIdAndVersion Tests ====================

  @Test
  void deleteWorkflowByIdAndVersion_withSpecificVersion_shouldDeleteThatVersion() {
    // Given: a specific version to delete
    String workflowId = "workflow-1";
    Long version = 3L;

    // When: Calling deleteWorkflowByIdAndVersion with a specific version
    ResponseEntity<Void> response =
        controller.deleteWorkflowByIdAndVersion("test-token", workflowId, version);

    // Then: Should delete that specific version and return No Content
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();
    verify(workflowManagementService, times(1)).delete(workflowId, version);
    verify(workflowManagementService, never()).delete(any(String.class));
    verifyNoInteractions(workflowExpirationService, logsStreamingService, secretKeeper);
  }

  @Test
  void deleteWorkflowByIdAndVersion_withNullVersion_shouldDeleteAllVersions() {
    // Given: version is null (delete all versions)
    String workflowId = "workflow-1";

    // When: Calling deleteWorkflowByIdAndVersion with null version
    ResponseEntity<Void> response =
        controller.deleteWorkflowByIdAndVersion("test-token", workflowId, null);

    // Then: Should delete all versions of the workflow
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();
    verify(workflowManagementService, times(1)).delete(workflowId);
    verify(workflowManagementService, never()).delete(any(String.class), any(Long.class));
  }

  @Test
  void deleteWorkflowByIdAndVersion_withVersion1_shouldDeleteVersion1() {
    // Given: version 1 to delete
    String workflowId = "workflow-1";

    // When: Calling deleteWorkflowByIdAndVersion with version 1
    ResponseEntity<Void> response =
        controller.deleteWorkflowByIdAndVersion("test-token", workflowId, 1L);

    // Then: Should delete version 1
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).delete(workflowId, 1L);
    verify(workflowManagementService, never()).delete(any(String.class));
  }

  @Test
  void deleteWorkflowByIdAndVersion_withLargeVersion_shouldHandleCorrectly() {
    // Given: a large version number
    String workflowId = "workflow-1";
    Long largeVersion = Long.MAX_VALUE;

    // When: Calling deleteWorkflowByIdAndVersion with large version
    ResponseEntity<Void> response =
        controller.deleteWorkflowByIdAndVersion("test-token", workflowId, largeVersion);

    // Then: Should handle correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).delete(workflowId, largeVersion);
  }

  @Test
  void deleteWorkflowByIdAndVersion_withSpecialCharactersInWorkflowId_shouldPassThrough() {
    // Given: workflow ID with special characters
    String workflowId = "workflow-with-dashes_and_underscores.and.dots";

    // When: Calling deleteWorkflowByIdAndVersion
    ResponseEntity<Void> response =
        controller.deleteWorkflowByIdAndVersion("test-token", workflowId, null);

    // Then: Should pass the ID through unchanged
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).delete(workflowId);
  }

  // ==================== setVersionAndExpirationTime Tests ====================

  @Test
  void setVersionAndExpirationTime_withBothVersionAndExpirationDate_shouldSetBoth() {
    // Given: both version and expiration date are provided
    String workflowId = "workflow-1";
    Long version = 2L;
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    // When: Calling setVersionAndExpirationTime with both parameters
    ResponseEntity<Void> response =
        controller.setVersionAndExpirationTime("test-token", workflowId, version, expirationDate);

    // Then: Should set both version and expiration
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();
    verify(workflowManagementService, times(1)).setActiveVersion(workflowId, version);
    verify(workflowExpirationService, times(1)).scheduleWorkflowExpiration(workflowId, expirationDate);
    verifyNoInteractions(logsStreamingService, secretKeeper);
  }

  @Test
  void setVersionAndExpirationTime_withVersionOnly_shouldSetVersionOnly() {
    // Given: only version is provided
    String workflowId = "workflow-1";
    Long version = 3L;

    // When: Calling setVersionAndExpirationTime with version only
    ResponseEntity<Void> response =
        controller.setVersionAndExpirationTime("test-token", workflowId, version, null);

    // Then: Should only set version
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).setActiveVersion(workflowId, version);
    verifyNoInteractions(workflowExpirationService);
  }

  @Test
  void setVersionAndExpirationTime_withExpirationDateOnly_shouldSetExpirationOnly() {
    // Given: only expiration date is provided
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-06-30T12:00:00Z");

    // When: Calling setVersionAndExpirationTime with expiration only
    ResponseEntity<Void> response =
        controller.setVersionAndExpirationTime("test-token", workflowId, null, expirationDate);

    // Then: Should only set expiration
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowExpirationService, times(1)).scheduleWorkflowExpiration(workflowId, expirationDate);
    verifyNoInteractions(workflowManagementService);
  }

  @Test
  void setVersionAndExpirationTime_withBothNull_shouldDoNothing() {
    // Given: both version and expiration date are null
    String workflowId = "workflow-1";

    // When: Calling setVersionAndExpirationTime with both null
    ResponseEntity<Void> response =
        controller.setVersionAndExpirationTime("test-token", workflowId, null, null);

    // Then: Should do nothing and still return No Content
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verifyNoInteractions(workflowManagementService, workflowExpirationService);
  }

  @Test
  void setVersionAndExpirationTime_withVersion1_shouldSetVersion1() {
    // Given: version 1
    String workflowId = "workflow-1";

    // When: Calling setVersionAndExpirationTime with version 1
    ResponseEntity<Void> response =
        controller.setVersionAndExpirationTime("test-token", workflowId, 1L, null);

    // Then: Should set version to 1
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).setActiveVersion(workflowId, 1L);
  }

  @Test
  void setVersionAndExpirationTime_withLargeVersion_shouldHandleCorrectly() {
    // Given: large version number
    String workflowId = "workflow-1";
    Long largeVersion = Long.MAX_VALUE;

    // When: Calling with large version
    ResponseEntity<Void> response =
        controller.setVersionAndExpirationTime("test-token", workflowId, largeVersion, null);

    // Then: Should handle correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowManagementService, times(1)).setActiveVersion(workflowId, largeVersion);
  }

  @Test
  void setVersionAndExpirationTime_withPastExpirationDate_shouldStillSchedule() {
    // Given: an expiration date in the past (business logic should handle this)
    String workflowId = "workflow-1";
    Instant pastDate = Instant.parse("2020-01-01T00:00:00Z");

    // When: Calling with past date
    ResponseEntity<Void> response =
        controller.setVersionAndExpirationTime("test-token", workflowId, null, pastDate);

    // Then: Should still pass to service (service handles business logic)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowExpirationService, times(1)).scheduleWorkflowExpiration(workflowId, pastDate);
  }

  @Test
  void setVersionAndExpirationTime_withFutureExpirationDate_shouldSchedule() {
    // Given: a future expiration date
    String workflowId = "workflow-1";
    Instant futureDate = Instant.parse("2030-01-01T00:00:00Z");

    // When: Calling with future date
    ResponseEntity<Void> response =
        controller.setVersionAndExpirationTime("test-token", workflowId, null, futureDate);

    // Then: Should schedule expiration
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(workflowExpirationService, times(1)).scheduleWorkflowExpiration(workflowId, futureDate);
  }

  @Test
  void setVersionAndExpirationTime_shouldInvokeServicesInCorrectOrder() {
    // Given: both version and expiration date
    String workflowId = "workflow-1";
    Long version = 5L;
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    // When: Calling setVersionAndExpirationTime
    controller.setVersionAndExpirationTime("test-token", workflowId, version, expirationDate);

    // Then: Should invoke services (order is important - version first, then expiration)
    verify(workflowManagementService, times(1)).setActiveVersion(workflowId, version);
    verify(workflowExpirationService, times(1)).scheduleWorkflowExpiration(workflowId, expirationDate);
  }

  // ==================== streamingLogs Tests ====================

  @Test
  void streamingLogs_shouldCreateEmitterAndSubscribe() {
    // Given: logsStreamingService is ready
    // (no setup needed, service is mocked)

    // When: Calling streamingLogs
    SseEmitter emitter = controller.streamingLogs("test-token");

    // Then: Should return an SseEmitter and subscribe it to the service
    assertThat(emitter).isNotNull();
    verify(logsStreamingService, times(1)).subscribe(emitter);
    verifyNoInteractions(workflowManagementService, workflowExpirationService, secretKeeper);
  }

  @Test
  void streamingLogs_shouldReturnNewEmitterEachTime() {
    // Given: logsStreamingService is ready

    // When: Calling streamingLogs twice
    SseEmitter emitter1 = controller.streamingLogs("test-token");
    SseEmitter emitter2 = controller.streamingLogs("test-token");

    // Then: Should return different emitter instances
    assertThat(emitter1).isNotNull();
    assertThat(emitter2).isNotNull();
    assertThat(emitter1).isNotSameAs(emitter2);
    verify(logsStreamingService, times(2)).subscribe(any(SseEmitter.class));
  }

  @Test
  void streamingLogs_withNullToken_shouldStillCreateEmitter() {
    // Given: null token (authorization handled by aspect)

    // When: Calling streamingLogs with null token
    SseEmitter emitter = controller.streamingLogs(null);

    // Then: Should still work
    assertThat(emitter).isNotNull();
    verify(logsStreamingService, times(1)).subscribe(emitter);
  }

  @Test
  void streamingLogs_shouldSubscribeExactEmitterToService() {
    // Given: ready to create emitter

    // When: Calling streamingLogs
    SseEmitter emitter = controller.streamingLogs("test-token");

    // Then: Should subscribe the exact emitter instance created
    ArgumentCaptor<SseEmitter> captor = ArgumentCaptor.forClass(SseEmitter.class);
    verify(logsStreamingService).subscribe(captor.capture());
    assertThat(captor.getValue()).isSameAs(emitter);
  }

  // ==================== uploadSecret Tests ====================

  @Test
  void uploadSecret_withValidSecret_shouldSaveAndReturnNoContent() {
    // Given: a valid secret
    char[] secretBytes = "my-secret-value".toCharArray();
    SecretView secretView = new SecretView("key1", secretBytes);

    // When: Calling uploadSecret
    ResponseEntity<Void> response = controller.uploadSecret(secretView);

    // Then: Should save secret and return No Content
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();

    ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<byte[]> bytesCaptor = ArgumentCaptor.forClass(byte[].class);
    verify(secretKeeper, times(1)).save(keyCaptor.capture(), bytesCaptor.capture());

    assertThat(keyCaptor.getValue()).isEqualTo("key1");
    String savedSecret = new String(bytesCaptor.getValue(), StandardCharsets.UTF_8);
    assertThat(savedSecret).isEqualTo("my-secret-value");
    verifyNoInteractions(workflowManagementService, workflowExpirationService, logsStreamingService);
  }

  @Test
  void uploadSecret_withShortKey_shouldSave() {
    // Given: a secret with short key
    char[] secretBytes = "value".toCharArray();
    SecretView secretView = new SecretView("k", secretBytes);

    // When: Calling uploadSecret
    ResponseEntity<Void> response = controller.uploadSecret(secretView);

    // Then: Should save successfully
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(secretKeeper, times(1)).save(eq("k"), any(byte[].class));
  }

  @Test
  void uploadSecret_withMaxLengthKey_shouldSave() {
    // Given: a secret with 15 character key (max allowed by validation)
    char[] secretBytes = "value".toCharArray();
    SecretView secretView = new SecretView("fifteencharskey", secretBytes);

    // When: Calling uploadSecret
    ResponseEntity<Void> response = controller.uploadSecret(secretView);

    // Then: Should save successfully
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(secretKeeper, times(1)).save(eq("fifteencharskey"), any(byte[].class));
  }

  @Test
  void uploadSecret_withSpecialCharactersInValue_shouldEncodeCorrectly() {
    // Given: a secret with special characters
    char[] secretBytes = "special!@#$%^&*()_+-={}[]|:;<>,.?/~`".toCharArray();
    SecretView secretView = new SecretView("key1", secretBytes);

    // When: Calling uploadSecret
    ResponseEntity<Void> response = controller.uploadSecret(secretView);

    // Then: Should encode and save correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    ArgumentCaptor<byte[]> captor = ArgumentCaptor.forClass(byte[].class);
    verify(secretKeeper).save(eq("key1"), captor.capture());
    String saved = new String(captor.getValue(), StandardCharsets.UTF_8);
    assertThat(saved).isEqualTo("special!@#$%^&*()_+-={}[]|:;<>,.?/~`");
  }

  @Test
  void uploadSecret_withUnicodeCharacters_shouldEncodeAsUTF8() {
    // Given: a secret with unicode characters
    char[] secretBytes = "Hello 世界 🌍".toCharArray();
    SecretView secretView = new SecretView("key1", secretBytes);

    // When: Calling uploadSecret
    ResponseEntity<Void> response = controller.uploadSecret(secretView);

    // Then: Should encode as UTF-8 correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    ArgumentCaptor<byte[]> captor = ArgumentCaptor.forClass(byte[].class);
    verify(secretKeeper).save(eq("key1"), captor.capture());
    String saved = new String(captor.getValue(), StandardCharsets.UTF_8);
    assertThat(saved).isEqualTo("Hello 世界 🌍");
  }

  @Test
  void uploadSecret_withEmptySecretValue_shouldSaveEmptyBytes() {
    // Given: a secret with empty value
    char[] secretBytes = "".toCharArray();
    SecretView secretView = new SecretView("key1", secretBytes);

    // When: Calling uploadSecret
    ResponseEntity<Void> response = controller.uploadSecret(secretView);

    // Then: Should save empty bytes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    ArgumentCaptor<byte[]> captor = ArgumentCaptor.forClass(byte[].class);
    verify(secretKeeper).save(eq("key1"), captor.capture());
    assertThat(captor.getValue()).isEmpty();
  }

  @Test
  void uploadSecret_shouldConvertCharArrayToStringThenToBytes() {
    // Given: a secret
    char[] secretBytes = "test-value".toCharArray();
    SecretView secretView = new SecretView("key1", secretBytes);

    // When: Calling uploadSecret
    controller.uploadSecret(secretView);

    // Then: Should convert char[] to String to byte[] with UTF-8 encoding
    ArgumentCaptor<byte[]> captor = ArgumentCaptor.forClass(byte[].class);
    verify(secretKeeper).save(eq("key1"), captor.capture());
    byte[] expectedBytes = "test-value".getBytes(StandardCharsets.UTF_8);
    assertThat(captor.getValue()).isEqualTo(expectedBytes);
  }

  // ==================== deleteSecret Tests ====================

  @Test
  void deleteSecret_withValidKey_shouldRemoveAndReturnNoContent() {
    // Given: a valid secret key
    String secretKey = "key1";

    // When: Calling deleteSecret
    ResponseEntity<Void> response = controller.deleteSecret(secretKey);

    // Then: Should remove secret and return No Content
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    assertThat(response.getBody()).isNull();
    verify(secretKeeper, times(1)).remove(secretKey);
    verifyNoInteractions(workflowManagementService, workflowExpirationService, logsStreamingService);
  }

  @Test
  void deleteSecret_withShortKey_shouldRemove() {
    // Given: a short key
    String secretKey = "k";

    // When: Calling deleteSecret
    ResponseEntity<Void> response = controller.deleteSecret(secretKey);

    // Then: Should remove successfully
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(secretKeeper, times(1)).remove(secretKey);
  }

  @Test
  void deleteSecret_withMaxLengthKey_shouldRemove() {
    // Given: a 15 character key
    String secretKey = "fifteencharskey";

    // When: Calling deleteSecret
    ResponseEntity<Void> response = controller.deleteSecret(secretKey);

    // Then: Should remove successfully
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    verify(secretKeeper, times(1)).remove(secretKey);
  }

  @Test
  void deleteSecret_shouldPassExactKeyToService() {
    // Given: a specific key
    String secretKey = "specific-key-123";

    // When: Calling deleteSecret
    controller.deleteSecret(secretKey);

    // Then: Should pass exact key to service
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(secretKeeper).remove(captor.capture());
    assertThat(captor.getValue()).isEqualTo(secretKey);
  }

  // ==================== getSecretMetadata Tests ====================

  @Test
  void getSecretMetadata_withSecrets_shouldReturnMetadataList() {
    // Given: secrets exist
    SecretKeeper.SecretMetadata metadata1 = new SecretKeeper.SecretMetadata(
        "key1",
        Instant.parse("2024-01-01T10:00:00Z")
    );
    SecretKeeper.SecretMetadata metadata2 = new SecretKeeper.SecretMetadata(
        "key2",
        Instant.parse("2024-01-02T11:00:00Z")
    );
    List<SecretKeeper.SecretMetadata> metadataList = Arrays.asList(metadata1, metadata2);
    when(secretKeeper.getSecretsMetadata()).thenReturn(metadataList);

    // When: Calling getSecretMetadata
    ResponseEntity<List<SecretKeeper.SecretMetadata>> response = controller.getSecretMetadata();

    // Then: Should return OK with metadata list
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(2);
    assertThat(response.getBody().get(0).getSecretKey()).isEqualTo("key1");
    assertThat(response.getBody().get(1).getSecretKey()).isEqualTo("key2");
    verify(secretKeeper, times(1)).getSecretsMetadata();
    verifyNoInteractions(workflowManagementService, workflowExpirationService, logsStreamingService);
  }

  @Test
  void getSecretMetadata_withNoSecrets_shouldReturnEmptyList() {
    // Given: no secrets exist
    when(secretKeeper.getSecretsMetadata()).thenReturn(Collections.emptyList());

    // When: Calling getSecretMetadata
    ResponseEntity<List<SecretKeeper.SecretMetadata>> response = controller.getSecretMetadata();

    // Then: Should return OK with empty list
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEmpty();
    verify(secretKeeper, times(1)).getSecretsMetadata();
  }

  @Test
  void getSecretMetadata_withSingleSecret_shouldReturnSingleMetadata() {
    // Given: one secret exists
    SecretKeeper.SecretMetadata metadata = new SecretKeeper.SecretMetadata(
        "only-key",
        Instant.parse("2024-06-15T14:30:00Z")
    );
    when(secretKeeper.getSecretsMetadata()).thenReturn(Collections.singletonList(metadata));

    // When: Calling getSecretMetadata
    ResponseEntity<List<SecretKeeper.SecretMetadata>> response = controller.getSecretMetadata();

    // Then: Should return OK with single metadata
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getSecretKey()).isEqualTo("only-key");
    assertThat(response.getBody().get(0).getCreatedAt()).isEqualTo(Instant.parse("2024-06-15T14:30:00Z"));
  }

  @Test
  void getSecretMetadata_withManySecrets_shouldReturnAllMetadata() {
    // Given: many secrets exist
    List<SecretKeeper.SecretMetadata> manyMetadata = Arrays.asList(
        new SecretKeeper.SecretMetadata("key1", Instant.now()),
        new SecretKeeper.SecretMetadata("key2", Instant.now()),
        new SecretKeeper.SecretMetadata("key3", Instant.now()),
        new SecretKeeper.SecretMetadata("key4", Instant.now()),
        new SecretKeeper.SecretMetadata("key5", Instant.now())
    );
    when(secretKeeper.getSecretsMetadata()).thenReturn(manyMetadata);

    // When: Calling getSecretMetadata
    ResponseEntity<List<SecretKeeper.SecretMetadata>> response = controller.getSecretMetadata();

    // Then: Should return all metadata
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(5);
  }

  @Test
  void getSecretMetadata_shouldReturnSameListAsSecretKeeper() {
    // Given: specific metadata list
    List<SecretKeeper.SecretMetadata> expectedList = Arrays.asList(
        new SecretKeeper.SecretMetadata("k1", Instant.now()),
        new SecretKeeper.SecretMetadata("k2", Instant.now())
    );
    when(secretKeeper.getSecretsMetadata()).thenReturn(expectedList);

    // When: Calling getSecretMetadata
    ResponseEntity<List<SecretKeeper.SecretMetadata>> response = controller.getSecretMetadata();

    // Then: Should return the same list instance
    assertThat(response.getBody()).isSameAs(expectedList);
  }

  @Test
  void getSecretMetadata_shouldCallSecretKeeperOnce() {
    // Given: secrets exist
    when(secretKeeper.getSecretsMetadata()).thenReturn(Collections.emptyList());

    // When: Calling getSecretMetadata
    controller.getSecretMetadata();

    // Then: Should call secretKeeper exactly once
    verify(secretKeeper, times(1)).getSecretsMetadata();
  }
}
