package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.SecretView;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.expiration.WorkflowExpirationService;
import com.symphony.bdk.workflow.logs.LogsStreamingService;
import com.symphony.bdk.workflow.management.WorkflowManagementService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowsMgtApiControllerTest {

  @Mock
  private WorkflowManagementService workflowManagementService;

  @Mock
  private WorkflowExpirationService workflowExpirationService;

  @Mock
  private LogsStreamingService logsStreamingService;

  @Mock
  private SecretKeeper secretKeeper;

  @InjectMocks
  private WorkflowsMgtApiController controller;

  @Test
  void shouldReturnNoContentWhenSaveAndDeploySwadl() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").build();

    ResponseEntity<Void> response = controller.saveAndDeploySwadl("token", swadlView);

    verify(workflowManagementService).deploy(swadlView);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldReturnNoContentWhenUpdateSwadl() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").build();

    ResponseEntity<Void> response = controller.updateSwadl("token", swadlView);

    verify(workflowManagementService).update(swadlView);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldReturnActiveWorkflowWhenGetVersionedWorkflowWithNoVersionAndNotAllVersions() {
    VersionedWorkflowView view = VersionedWorkflowView.builder().workflowId("wf1").build();
    when(workflowManagementService.get("wf1")).thenReturn(Optional.of(view));

    ResponseEntity<List<VersionedWorkflowView>> response = controller.getVersionedWorkflow("token", "wf1", null, false);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).containsExactly(view);
  }

  @Test
  void shouldReturnAllVersionsWhenGetVersionedWorkflowWithAllVersionsTrue() {
    VersionedWorkflowView v1 = VersionedWorkflowView.builder().workflowId("wf1").version(1L).build();
    VersionedWorkflowView v2 = VersionedWorkflowView.builder().workflowId("wf1").version(2L).build();
    when(workflowManagementService.getAllVersions("wf1")).thenReturn(List.of(v1, v2));

    ResponseEntity<List<VersionedWorkflowView>> response = controller.getVersionedWorkflow("token", "wf1", null, true);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).containsExactly(v1, v2);
  }

  @Test
  void shouldReturnSpecificVersionWhenGetVersionedWorkflowWithVersionProvided() {
    VersionedWorkflowView view = VersionedWorkflowView.builder().workflowId("wf1").version(2L).build();
    when(workflowManagementService.get("wf1", 2L)).thenReturn(Optional.of(view));

    ResponseEntity<List<VersionedWorkflowView>> response = controller.getVersionedWorkflow("token", "wf1", 2L, false);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).containsExactly(view);
  }

  @Test
  void shouldDeleteWorkflowByIdWhenVersionIsNull() {
    ResponseEntity<Void> response = controller.deleteWorkflowByIdAndVersion("token", "wf1", null);

    verify(workflowManagementService).delete("wf1");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldDeleteWorkflowByIdAndVersionWhenVersionIsProvided() {
    ResponseEntity<Void> response = controller.deleteWorkflowByIdAndVersion("token", "wf1", 3L);

    verify(workflowManagementService).delete("wf1", 3L);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldSetActiveVersionWhenVersionIsProvided() {
    ResponseEntity<Void> response = controller.setVersionAndExpirationTime("token", "wf1", 2L, null);

    verify(workflowManagementService).setActiveVersion("wf1", 2L);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldScheduleExpirationWhenExpirationDateIsProvided() {
    Instant expiration = Instant.now().plusSeconds(3600);

    ResponseEntity<Void> response = controller.setVersionAndExpirationTime("token", "wf1", null, expiration);

    verify(workflowExpirationService).scheduleWorkflowExpiration("wf1", expiration);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldSetVersionAndScheduleExpirationWhenBothProvided() {
    Instant expiration = Instant.now().plusSeconds(3600);

    ResponseEntity<Void> response = controller.setVersionAndExpirationTime("token", "wf1", 1L, expiration);

    verify(workflowManagementService).setActiveVersion("wf1", 1L);
    verify(workflowExpirationService).scheduleWorkflowExpiration("wf1", expiration);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldReturnSseEmitterWhenStreamingLogs() {
    SseEmitter emitter = controller.streamingLogs("token");

    verify(logsStreamingService).subscribe(any(SseEmitter.class));
    assertThat(emitter).isNotNull();
  }

  @Test
  void shouldReturnNoContentWhenUploadSecret() {
    SecretView secret = new SecretView("myKey", "mySecret".toCharArray());

    ResponseEntity<Void> response = controller.uploadSecret(secret);

    verify(secretKeeper).save(eq("myKey"), any(byte[].class));
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldReturnNoContentWhenDeleteSecret() {
    ResponseEntity<Void> response = controller.deleteSecret("myKey");

    verify(secretKeeper).remove("myKey");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void shouldReturnSecretsMetadataWhenGetSecretMetadata() {
    SecretKeeper.SecretMetadata metadata = new SecretKeeper.SecretMetadata("myKey", Instant.now());
    when(secretKeeper.getSecretsMetadata()).thenReturn(List.of(metadata));

    ResponseEntity<List<SecretKeeper.SecretMetadata>> response = controller.getSecretMetadata();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).containsExactly(metadata);
  }
}
