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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkflowsMgtApiControllerTest {

  private WorkflowManagementService workflowManagementService;
  private WorkflowExpirationService workflowExpirationService;
  private LogsStreamingService logsStreamingService;
  private SecretKeeper secretKeeper;
  private WorkflowsMgtApiController controller;

  @BeforeEach
  void setUp() {
    workflowManagementService = mock(WorkflowManagementService.class);
    workflowExpirationService = mock(WorkflowExpirationService.class);
    logsStreamingService = mock(LogsStreamingService.class);
    secretKeeper = mock(SecretKeeper.class);
    controller = new WorkflowsMgtApiController(
        workflowManagementService,
        workflowExpirationService,
        logsStreamingService,
        secretKeeper
    );
  }

  @Test
  void saveAndDeploySwadlShouldDeployWorkflowAndReturnNoContent() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow")
        .description("Test workflow")
        .build();

    ResponseEntity<Void> response = controller.saveAndDeploySwadl("token", swadlView);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(workflowManagementService).deploy(swadlView);
  }

  @Test
  void updateSwadlShouldUpdateWorkflowAndReturnNoContent() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow")
        .description("Updated workflow")
        .build();

    ResponseEntity<Void> response = controller.updateSwadl("token", swadlView);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(workflowManagementService).update(swadlView);
  }

  @Test
  void getVersionedWorkflowShouldReturnLatestVersionWhenNoVersionAndNotAllVersions() {
    String workflowId = "testWorkflow";
    VersionedWorkflowView workflowView = VersionedWorkflowView.builder()
        .workflowId(workflowId)
        .version(1L)
        .build();

    when(workflowManagementService.get(workflowId)).thenReturn(Optional.of(workflowView));

    ResponseEntity<List<VersionedWorkflowView>> response = controller.getVersionedWorkflow(
        "token",
        workflowId,
        null,
        false
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertEquals(workflowView, response.getBody().get(0));
    verify(workflowManagementService).get(workflowId);
  }

  @Test
  void getVersionedWorkflowShouldReturnEmptyListWhenLatestVersionNotFound() {
    String workflowId = "testWorkflow";

    when(workflowManagementService.get(workflowId)).thenReturn(Optional.empty());

    ResponseEntity<List<VersionedWorkflowView>> response = controller.getVersionedWorkflow(
        "token",
        workflowId,
        null,
        false
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(0, response.getBody().size());
    verify(workflowManagementService).get(workflowId);
  }

  @Test
  void getVersionedWorkflowShouldReturnAllVersionsWhenAllVersionsIsTrue() {
    String workflowId = "testWorkflow";
    VersionedWorkflowView workflow1 = VersionedWorkflowView.builder()
        .workflowId(workflowId)
        .version(1L)
        .build();
    VersionedWorkflowView workflow2 = VersionedWorkflowView.builder()
        .workflowId(workflowId)
        .version(2L)
        .build();
    List<VersionedWorkflowView> allVersions = List.of(workflow1, workflow2);

    when(workflowManagementService.getAllVersions(workflowId)).thenReturn(allVersions);

    ResponseEntity<List<VersionedWorkflowView>> response = controller.getVersionedWorkflow(
        "token",
        workflowId,
        null,
        true
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    verify(workflowManagementService).getAllVersions(workflowId);
  }

  @Test
  void getVersionedWorkflowShouldReturnSpecificVersionWhenVersionProvided() {
    String workflowId = "testWorkflow";
    Long version = 2L;
    VersionedWorkflowView workflowView = VersionedWorkflowView.builder()
        .workflowId(workflowId)
        .version(version)
        .build();

    when(workflowManagementService.get(workflowId, version)).thenReturn(Optional.of(workflowView));

    ResponseEntity<List<VersionedWorkflowView>> response = controller.getVersionedWorkflow(
        "token",
        workflowId,
        version,
        false
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertEquals(workflowView, response.getBody().get(0));
    verify(workflowManagementService).get(workflowId, version);
  }

  @Test
  void deleteWorkflowByIdAndVersionShouldDeleteSpecificVersionWhenVersionProvided() {
    String workflowId = "testWorkflow";
    Long version = 2L;

    ResponseEntity<Void> response = controller.deleteWorkflowByIdAndVersion("token", workflowId, version);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(workflowManagementService).delete(workflowId, version);
  }

  @Test
  void deleteWorkflowByIdAndVersionShouldDeleteAllVersionsWhenVersionIsNull() {
    String workflowId = "testWorkflow";

    ResponseEntity<Void> response = controller.deleteWorkflowByIdAndVersion("token", workflowId, null);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(workflowManagementService).delete(workflowId);
  }

  @Test
  void setVersionAndExpirationTimeShouldSetActiveVersionWhenVersionProvided() {
    String workflowId = "testWorkflow";
    Long version = 2L;

    ResponseEntity<Void> response = controller.setVersionAndExpirationTime(
        "token",
        workflowId,
        version,
        null
    );

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(workflowManagementService).setActiveVersion(workflowId, version);
    verify(workflowExpirationService, never()).scheduleWorkflowExpiration(anyString(), any());
  }

  @Test
  void setVersionAndExpirationTimeShouldScheduleExpirationWhenExpirationDateProvided() {
    String workflowId = "testWorkflow";
    Instant expirationDate = Instant.now().plusSeconds(3600);

    ResponseEntity<Void> response = controller.setVersionAndExpirationTime(
        "token",
        workflowId,
        null,
        expirationDate
    );

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(workflowExpirationService).scheduleWorkflowExpiration(workflowId, expirationDate);
    verify(workflowManagementService, never()).setActiveVersion(anyString(), any());
  }

  @Test
  void setVersionAndExpirationTimeShouldSetBothWhenBothProvided() {
    String workflowId = "testWorkflow";
    Long version = 2L;
    Instant expirationDate = Instant.now().plusSeconds(3600);

    ResponseEntity<Void> response = controller.setVersionAndExpirationTime(
        "token",
        workflowId,
        version,
        expirationDate
    );

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(workflowManagementService).setActiveVersion(workflowId, version);
    verify(workflowExpirationService).scheduleWorkflowExpiration(workflowId, expirationDate);
  }

  @Test
  void streamingLogsShouldCreateEmitterAndSubscribeToLogsService() {
    SseEmitter result = controller.streamingLogs("token");

    assertNotNull(result);
    verify(logsStreamingService).subscribe(result);
  }

  @Test
  void uploadSecretShouldSaveSecretAndReturnNoContent() {
    SecretView secretView = new SecretView("testKey", "testSecret".toCharArray());

    ResponseEntity<Void> response = controller.uploadSecret(secretView);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(secretKeeper).save(eq("testKey"), any(byte[].class));
  }

  @Test
  void deleteSecretShouldRemoveSecretAndReturnNoContent() {
    String secretKey = "testKey";

    ResponseEntity<Void> response = controller.deleteSecret(secretKey);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(secretKeeper).remove(secretKey);
  }

  @Test
  void getSecretMetadataShouldReturnSecretsMetadata() {
    List<SecretKeeper.SecretMetadata> metadata = List.of();

    when(secretKeeper.getSecretsMetadata()).thenReturn(metadata);

    ResponseEntity<List<SecretKeeper.SecretMetadata>> response = controller.getSecretMetadata();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(metadata, response.getBody());
    verify(secretKeeper).getSecretsMetadata();
  }
}
