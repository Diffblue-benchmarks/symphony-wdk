package com.symphony.bdk.workflow.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.SecretView;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper.SecretMetadata;
import com.symphony.bdk.workflow.expiration.WorkflowExpirationService;
import com.symphony.bdk.workflow.logs.LogsStreamingService;
import com.symphony.bdk.workflow.management.WorkflowManagementService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class WorkflowsMgtApiControllerDiffblueTest {
  @Mock
  private SecretKeeper secretKeeper;

  @InjectMocks
  private WorkflowsMgtApiController workflowsMgtApiController;

  @Mock
  private WorkflowManagementService workflowManagementService;

  @Mock
  private WorkflowExpirationService workflowExpirationService;

  @Mock
  private LogsStreamingService logsStreamingService;

  /**
   * Test {@link WorkflowsMgtApiController#saveAndDeploySwadl(String, SwadlView)}.
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#saveAndDeploySwadl(String, SwadlView)}
   */
  @Test
  @DisplayName("Test saveAndDeploySwadl(String, SwadlView)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.saveAndDeploySwadl(String, SwadlView)"})
  void testSaveAndDeploySwadl() {
    // Arrange
    doNothing().when(workflowManagementService).deploy(Mockito.<SwadlView>any());

    // Act
    ResponseEntity<Void> actualSaveAndDeploySwadlResult = workflowsMgtApiController.saveAndDeploySwadl("ABC123", null);

    // Assert
    verify(workflowManagementService).deploy(isNull());
    HttpStatusCode statusCode = actualSaveAndDeploySwadlResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSaveAndDeploySwadlResult.getBody());
    assertEquals(204, actualSaveAndDeploySwadlResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualSaveAndDeploySwadlResult.hasBody());
    assertTrue(actualSaveAndDeploySwadlResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#updateSwadl(String, SwadlView)}.
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#updateSwadl(String, SwadlView)}
   */
  @Test
  @DisplayName("Test updateSwadl(String, SwadlView)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.updateSwadl(String, SwadlView)"})
  void testUpdateSwadl() {
    // Arrange
    doNothing().when(workflowManagementService).update(Mockito.<SwadlView>any());

    // Act
    ResponseEntity<Void> actualUpdateSwadlResult = workflowsMgtApiController.updateSwadl("ABC123", null);

    // Assert
    verify(workflowManagementService).update(isNull());
    HttpStatusCode statusCode = actualUpdateSwadlResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualUpdateSwadlResult.getBody());
    assertEquals(204, actualUpdateSwadlResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualUpdateSwadlResult.hasBody());
    assertTrue(actualUpdateSwadlResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}.
   * <ul>
   *   <li>Then calls {@link WorkflowManagementService#get(String, Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  @DisplayName("Test getVersionedWorkflow(String, String, Long, Boolean); then calls get(String, Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"})
  void testGetVersionedWorkflow_thenCallsGet() {
    // Arrange
    VersionedWorkflowView buildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    Optional<VersionedWorkflowView> ofResult = Optional.of(buildResult);
    when(workflowManagementService.get(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = workflowsMgtApiController
        .getVersionedWorkflow("ABC123", "42", 1L, false);

    // Assert
    verify(workflowManagementService).get(eq("42"), eq(1L));
    List<VersionedWorkflowView> body = actualVersionedWorkflow.getBody();
    assertEquals(1, body.size());
    VersionedWorkflowView getResult = body.get(0);
    assertEquals("42", getResult.getDeploymentId());
    assertEquals("42", getResult.getId());
    assertEquals("42", getResult.getWorkflowId());
    assertEquals("Swadl", getResult.getSwadl());
    assertEquals("The characteristics of someone or something", getResult.getDescription());
    assertEquals(1L, getResult.getCreatedBy().longValue());
    assertEquals(1L, getResult.getVersion().longValue());
    assertTrue(getResult.getActive());
    assertTrue(getResult.getPublished());
  }

  /**
   * Test {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}.
   * <ul>
   *   <li>Then calls {@link WorkflowManagementService#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  @DisplayName("Test getVersionedWorkflow(String, String, Long, Boolean); then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"})
  void testGetVersionedWorkflow_thenCallsGet2() {
    // Arrange
    VersionedWorkflowView buildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    Optional<VersionedWorkflowView> ofResult = Optional.of(buildResult);
    when(workflowManagementService.get(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = workflowsMgtApiController
        .getVersionedWorkflow("ABC123", "42", null, false);

    // Assert
    verify(workflowManagementService).get(eq("42"));
    List<VersionedWorkflowView> body = actualVersionedWorkflow.getBody();
    assertEquals(1, body.size());
    VersionedWorkflowView getResult = body.get(0);
    assertEquals("42", getResult.getDeploymentId());
    assertEquals("42", getResult.getId());
    assertEquals("42", getResult.getWorkflowId());
    assertEquals("Swadl", getResult.getSwadl());
    assertEquals("The characteristics of someone or something", getResult.getDescription());
    assertEquals(1L, getResult.getCreatedBy().longValue());
    assertEquals(1L, getResult.getVersion().longValue());
    assertTrue(getResult.getActive());
    assertTrue(getResult.getPublished());
  }

  /**
   * Test {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then StatusCode return {@link HttpStatus}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  @DisplayName("Test getVersionedWorkflow(String, String, Long, Boolean); when 'true'; then StatusCode return HttpStatus")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"})
  void testGetVersionedWorkflow_whenTrue_thenStatusCodeReturnHttpStatus() {
    // Arrange
    when(workflowManagementService.getAllVersions(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = workflowsMgtApiController
        .getVersionedWorkflow("ABC123", "42", 1L, true);

    // Assert
    verify(workflowManagementService).getAllVersions(eq("42"));
    HttpStatusCode statusCode = actualVersionedWorkflow.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualVersionedWorkflow.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualVersionedWorkflow.getBody().isEmpty());
    assertTrue(actualVersionedWorkflow.hasBody());
    assertTrue(actualVersionedWorkflow.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then StatusCode return {@link HttpStatus}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  @DisplayName("Test getVersionedWorkflow(String, String, Long, Boolean); when 'true'; then StatusCode return HttpStatus")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"})
  void testGetVersionedWorkflow_whenTrue_thenStatusCodeReturnHttpStatus2() {
    // Arrange
    when(workflowManagementService.getAllVersions(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = workflowsMgtApiController
        .getVersionedWorkflow("ABC123", "42", null, true);

    // Assert
    verify(workflowManagementService).getAllVersions(eq("42"));
    HttpStatusCode statusCode = actualVersionedWorkflow.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualVersionedWorkflow.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualVersionedWorkflow.getBody().isEmpty());
    assertTrue(actualVersionedWorkflow.hasBody());
    assertTrue(actualVersionedWorkflow.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}.
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}
   */
  @Test
  @DisplayName("Test deleteWorkflowByIdAndVersion(String, String, Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.deleteWorkflowByIdAndVersion(String, String, Long)"})
  void testDeleteWorkflowByIdAndVersion() {
    // Arrange
    doNothing().when(workflowManagementService).delete(Mockito.<String>any(), Mockito.<Long>any());

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult = workflowsMgtApiController
        .deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

    // Assert
    verify(workflowManagementService).delete(eq("42"), eq(1L));
    HttpStatusCode statusCode = actualDeleteWorkflowByIdAndVersionResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteWorkflowByIdAndVersionResult.getBody());
    assertEquals(204, actualDeleteWorkflowByIdAndVersionResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteWorkflowByIdAndVersionResult.hasBody());
    assertTrue(actualDeleteWorkflowByIdAndVersionResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long, Instant)}.
   * <ul>
   *   <li>Then calls {@link WorkflowExpirationService#scheduleWorkflowExpiration(String, Instant)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long, Instant)}
   */
  @Test
  @DisplayName("Test setVersionAndExpirationTime(String, String, Long, Instant); then calls scheduleWorkflowExpiration(String, Instant)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ResponseEntity WorkflowsMgtApiController.setVersionAndExpirationTime(String, String, Long, Instant)"})
  void testSetVersionAndExpirationTime_thenCallsScheduleWorkflowExpiration() {
    // Arrange
    doNothing().when(workflowManagementService).setActiveVersion(Mockito.<String>any(), Mockito.<Long>any());
    doNothing().when(workflowExpirationService)
        .scheduleWorkflowExpiration(Mockito.<String>any(), Mockito.<Instant>any());

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult = workflowsMgtApiController
        .setVersionAndExpirationTime("ABC123", "42", 1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(workflowExpirationService).scheduleWorkflowExpiration(eq("42"), isA(Instant.class));
    verify(workflowManagementService).setActiveVersion(eq("42"), eq(1L));
    HttpStatusCode statusCode = actualSetVersionAndExpirationTimeResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSetVersionAndExpirationTimeResult.getBody());
    assertEquals(204, actualSetVersionAndExpirationTimeResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualSetVersionAndExpirationTimeResult.hasBody());
    assertTrue(actualSetVersionAndExpirationTimeResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long, Instant)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long, Instant)}
   */
  @Test
  @DisplayName("Test setVersionAndExpirationTime(String, String, Long, Instant); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ResponseEntity WorkflowsMgtApiController.setVersionAndExpirationTime(String, String, Long, Instant)"})
  void testSetVersionAndExpirationTime_whenNull() {
    // Arrange and Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult = workflowsMgtApiController
        .setVersionAndExpirationTime("ABC123", "42", null, null);

    // Assert
    HttpStatusCode statusCode = actualSetVersionAndExpirationTimeResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSetVersionAndExpirationTimeResult.getBody());
    assertEquals(204, actualSetVersionAndExpirationTimeResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualSetVersionAndExpirationTimeResult.hasBody());
    assertTrue(actualSetVersionAndExpirationTimeResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#streamingLogs(String)}.
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#streamingLogs(String)}
   */
  @Test
  @DisplayName("Test streamingLogs(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"SseEmitter WorkflowsMgtApiController.streamingLogs(String)"})
  void testStreamingLogs() {
    // Arrange
    doNothing().when(logsStreamingService).subscribe(Mockito.<SseEmitter>any());

    // Act
    SseEmitter actualStreamingLogsResult = workflowsMgtApiController.streamingLogs("ABC123");

    // Assert
    verify(logsStreamingService).subscribe(isA(SseEmitter.class));
    assertNull(actualStreamingLogsResult.getTimeout());
  }

  /**
   * Test {@link WorkflowsMgtApiController#uploadSecret(SecretView)}.
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#uploadSecret(SecretView)}
   */
  @Test
  @DisplayName("Test uploadSecret(SecretView); then StatusCode return HttpStatus")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.uploadSecret(SecretView)"})
  void testUploadSecret_thenStatusCodeReturnHttpStatus() {
    // Arrange
    doNothing().when(secretKeeper).save(Mockito.<String>any(), Mockito.<byte[]>any());

    // Act
    ResponseEntity<Void> actualUploadSecretResult = workflowsMgtApiController
        .uploadSecret(new SecretView("Key", "A\u0000A\u0000".toCharArray()));

    // Assert
    verify(secretKeeper).save(eq("Key"), isA(byte[].class));
    HttpStatusCode statusCode = actualUploadSecretResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualUploadSecretResult.getBody());
    assertEquals(204, actualUploadSecretResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualUploadSecretResult.hasBody());
    assertTrue(actualUploadSecretResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#deleteSecret(String)}.
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#deleteSecret(String)}
   */
  @Test
  @DisplayName("Test deleteSecret(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.deleteSecret(String)"})
  void testDeleteSecret() {
    // Arrange
    doNothing().when(secretKeeper).remove(Mockito.<String>any());

    // Act
    ResponseEntity<Void> actualDeleteSecretResult = workflowsMgtApiController
        .deleteSecret("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");

    // Assert
    verify(secretKeeper).remove(eq("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
    HttpStatusCode statusCode = actualDeleteSecretResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteSecretResult.getBody());
    assertEquals(204, actualDeleteSecretResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteSecretResult.hasBody());
    assertTrue(actualDeleteSecretResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#getSecretMetadata()}.
   * <p>
   * Method under test: {@link WorkflowsMgtApiController#getSecretMetadata()}
   */
  @Test
  @DisplayName("Test getSecretMetadata()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.getSecretMetadata()"})
  void testGetSecretMetadata() {
    // Arrange
    when(secretKeeper.getSecretsMetadata()).thenReturn(new ArrayList<>());

    // Act
    ResponseEntity<List<SecretMetadata>> actualSecretMetadata = workflowsMgtApiController.getSecretMetadata();

    // Assert
    verify(secretKeeper).getSecretsMetadata();
    HttpStatusCode statusCode = actualSecretMetadata.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualSecretMetadata.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualSecretMetadata.getBody().isEmpty());
    assertTrue(actualSecretMetadata.hasBody());
    assertTrue(actualSecretMetadata.getHeaders().isEmpty());
  }
}
