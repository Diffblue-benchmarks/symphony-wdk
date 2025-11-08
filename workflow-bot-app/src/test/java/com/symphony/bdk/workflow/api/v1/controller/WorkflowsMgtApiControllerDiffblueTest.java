package com.symphony.bdk.workflow.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.api.v1.dto.SecretView;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.engine.secret.DefaultSecretKeeper;
import com.symphony.bdk.workflow.engine.secret.SecretCryptVault;
import com.symphony.bdk.workflow.engine.secret.SecretRepository;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.expiration.WorkflowExpirationPlanner;
import com.symphony.bdk.workflow.expiration.WorkflowExpirationService;
import com.symphony.bdk.workflow.logs.LogsStreamingService;
import com.symphony.bdk.workflow.management.WorkflowManagementService;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

class WorkflowsMgtApiControllerDiffblueTest {
  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#saveAndDeploySwadl(String, SwadlView)}
   */
  @Test
  void testSaveAndDeploySwadl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    doNothing().when(workflowManagementService).deploy(Mockito.<SwadlView>any());
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<Void> actualSaveAndDeploySwadlResult = (new WorkflowsMgtApiController(workflowManagementService,
        workflowExpirationService, logsStreamingService, new DefaultSecretKeeper(repository, new SecretCryptVault())))
        .saveAndDeploySwadl("ABC123", null);

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
   * Method under test:
   * {@link WorkflowsMgtApiController#updateSwadl(String, SwadlView)}
   */
  @Test
  void testUpdateSwadl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    doNothing().when(workflowManagementService).update(Mockito.<SwadlView>any());
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<Void> actualUpdateSwadlResult = (new WorkflowsMgtApiController(workflowManagementService,
        workflowExpirationService, logsStreamingService, new DefaultSecretKeeper(repository, new SecretCryptVault())))
        .updateSwadl("ABC123", null);

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
   * Method under test:
   * {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  void testGetVersionedWorkflow() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).getVersionedWorkflow("ABC123", "42", 1L, true);

    // Assert
    verify(versionRepository).findByWorkflowId(eq("42"));
    HttpStatusCode statusCode = actualVersionedWorkflow.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualVersionedWorkflow.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualVersionedWorkflow.getBody().isEmpty());
    assertTrue(actualVersionedWorkflow.hasBody());
    assertTrue(actualVersionedWorkflow.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  void testGetVersionedWorkflow2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    when(workflowManagementService.getAllVersions(Mockito.<String>any())).thenReturn(new ArrayList<>());
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).getVersionedWorkflow("ABC123", "42", 1L, true);

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
   * Method under test:
   * {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  void testGetVersionedWorkflow3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    when(workflowManagementService.getAllVersions(Mockito.<String>any())).thenReturn(new ArrayList<>());
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).getVersionedWorkflow("ABC123", "42", null, true);

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
   * Method under test:
   * {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  void testGetVersionedWorkflow4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
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
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).getVersionedWorkflow("ABC123", "42", 1L, false);

    // Assert
    verify(workflowManagementService).get(eq("42"), eq(1L));
    HttpStatusCode statusCode = actualVersionedWorkflow.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
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
    assertEquals(200, actualVersionedWorkflow.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(getResult.getActive());
    assertTrue(getResult.getPublished());
    assertTrue(actualVersionedWorkflow.hasBody());
    assertTrue(actualVersionedWorkflow.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String, Long, Boolean)}
   */
  @Test
  void testGetVersionedWorkflow5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
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
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).getVersionedWorkflow("ABC123", "42", null, false);

    // Assert
    verify(workflowManagementService).get(eq("42"));
    HttpStatusCode statusCode = actualVersionedWorkflow.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
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
    assertEquals(200, actualVersionedWorkflow.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(getResult.getActive());
    assertTrue(getResult.getPublished());
    assertTrue(actualVersionedWorkflow.hasBody());
    assertTrue(actualVersionedWorkflow.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}
   */
  @Test
  void testDeleteWorkflowByIdAndVersion() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine = mock(WorkflowEngine.class);
    doNothing().when(workflowEngine).undeployByDeploymentId(Mockito.<String>any());

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    doNothing().when(versionRepository).deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(ofResult);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

    // Assert
    verify(workflowEngine).undeployByDeploymentId(eq("42"));
    verify(versionRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    HttpStatusCode statusCode = actualDeleteWorkflowByIdAndVersionResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteWorkflowByIdAndVersionResult.getBody());
    assertEquals(204, actualDeleteWorkflowByIdAndVersionResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteWorkflowByIdAndVersionResult.hasBody());
    assertTrue(actualDeleteWorkflowByIdAndVersionResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}
   */
  @Test
  void testDeleteWorkflowByIdAndVersion2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getActive()).thenReturn(false);
    doNothing().when(versionedWorkflow).setActive(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setCreatedBy(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setDeploymentId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setDescription(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setEtag(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setPublished(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setSwadl(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setVersion(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setWorkflowId(Mockito.<String>any());
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    doNothing().when(versionRepository).deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(ofResult);
    WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine = mock(WorkflowEngine.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

    // Assert
    verify(versionRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflow).getActive();
    verify(versionedWorkflow).setActive(eq(true));
    verify(versionedWorkflow).setCreatedBy(eq(1L));
    verify(versionedWorkflow).setDeploymentId(eq("42"));
    verify(versionedWorkflow).setDescription(eq("The characteristics of someone or something"));
    verify(versionedWorkflow).setEtag(eq(1L));
    verify(versionedWorkflow).setId(eq("42"));
    verify(versionedWorkflow).setPublished(eq(true));
    verify(versionedWorkflow).setSwadl(eq("Swadl"));
    verify(versionedWorkflow).setVersion(eq(1L));
    verify(versionedWorkflow).setWorkflowId(eq("42"));
    HttpStatusCode statusCode = actualDeleteWorkflowByIdAndVersionResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteWorkflowByIdAndVersionResult.getBody());
    assertEquals(204, actualDeleteWorkflowByIdAndVersionResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteWorkflowByIdAndVersionResult.hasBody());
    assertTrue(actualDeleteWorkflowByIdAndVersionResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}
   */
  @Test
  void testDeleteWorkflowByIdAndVersion3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine = mock(WorkflowEngine.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

    // Assert
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    HttpStatusCode statusCode = actualDeleteWorkflowByIdAndVersionResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteWorkflowByIdAndVersionResult.getBody());
    assertEquals(204, actualDeleteWorkflowByIdAndVersionResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteWorkflowByIdAndVersionResult.hasBody());
    assertTrue(actualDeleteWorkflowByIdAndVersionResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}
   */
  @Test
  void testDeleteWorkflowByIdAndVersion4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    doNothing().when(workflowManagementService).delete(Mockito.<String>any(), Mockito.<Long>any());
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault()))).deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

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
   * Method under test:
   * {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long, Instant)}
   */
  @Test
  void testSetVersionAndExpirationTime() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, logsStreamingService,
        new DefaultSecretKeeper(repository, new SecretCryptVault())))
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
   * Method under test:
   * {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long, Instant)}
   */
  @Test
  void testSetVersionAndExpirationTime2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowExpirationJobRepository expirationJobRepository = mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any())).thenReturn(new ArrayList<>());

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow);
    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any())).thenReturn(versionedWorkflowList);
    WorkflowExpirationPlanner workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    doNothing().when(workflowExpirationPlanner).planExpiration(Mockito.<WorkflowExpirationJob>any());
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(expirationJobRepository,
        versioningRepository, workflowExpirationPlanner);

    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    WorkflowsMgtApiController workflowsMgtApiController = new WorkflowsMgtApiController(workflowManagementService,
        workflowExpirationService, logsStreamingService, new DefaultSecretKeeper(repository, new SecretCryptVault()));

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult = workflowsMgtApiController
        .setVersionAndExpirationTime("ABC123", "42", null,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(workflowExpirationPlanner).planExpiration(isA(WorkflowExpirationJob.class));
    verify(versioningRepository).findByWorkflowId(eq("42"));
    verify(expirationJobRepository).saveAll(isA(Iterable.class));
    HttpStatusCode statusCode = actualSetVersionAndExpirationTimeResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSetVersionAndExpirationTimeResult.getBody());
    assertEquals(204, actualSetVersionAndExpirationTimeResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualSetVersionAndExpirationTimeResult.hasBody());
    assertTrue(actualSetVersionAndExpirationTimeResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long, Instant)}
   */
  @Test
  void testSetVersionAndExpirationTime3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowExpirationJobRepository expirationJobRepository = mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any())).thenReturn(new ArrayList<>());

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(false);
    versionedWorkflow2.setCreatedBy(0L);
    versionedWorkflow2.setDeploymentId("Deployment Id");
    versionedWorkflow2.setDescription("Description");
    versionedWorkflow2.setEtag(0L);
    versionedWorkflow2.setId("Id");
    versionedWorkflow2.setPublished(false);
    versionedWorkflow2.setSwadl("com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow");
    versionedWorkflow2.setVersion(0L);
    versionedWorkflow2.setWorkflowId("Workflow Id");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow2);
    versionedWorkflowList.add(versionedWorkflow);
    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any())).thenReturn(versionedWorkflowList);
    WorkflowExpirationPlanner workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    doNothing().when(workflowExpirationPlanner).planExpiration(Mockito.<WorkflowExpirationJob>any());
    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(expirationJobRepository,
        versioningRepository, workflowExpirationPlanner);

    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    WorkflowsMgtApiController workflowsMgtApiController = new WorkflowsMgtApiController(workflowManagementService,
        workflowExpirationService, logsStreamingService, new DefaultSecretKeeper(repository, new SecretCryptVault()));

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult = workflowsMgtApiController
        .setVersionAndExpirationTime("ABC123", "42", null,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(workflowExpirationPlanner, atLeast(1)).planExpiration(Mockito.<WorkflowExpirationJob>any());
    verify(versioningRepository).findByWorkflowId(eq("42"));
    verify(expirationJobRepository).saveAll(isA(Iterable.class));
    HttpStatusCode statusCode = actualSetVersionAndExpirationTimeResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSetVersionAndExpirationTimeResult.getBody());
    assertEquals(204, actualSetVersionAndExpirationTimeResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualSetVersionAndExpirationTimeResult.hasBody());
    assertTrue(actualSetVersionAndExpirationTimeResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long, Instant)}
   */
  @Test
  void testSetVersionAndExpirationTime4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowExpirationService workflowExpirationService = mock(WorkflowExpirationService.class);
    doNothing().when(workflowExpirationService)
        .scheduleWorkflowExpiration(Mockito.<String>any(), Mockito.<Instant>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    WorkflowsMgtApiController workflowsMgtApiController = new WorkflowsMgtApiController(workflowManagementService,
        workflowExpirationService, logsStreamingService, new DefaultSecretKeeper(repository, new SecretCryptVault()));

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult = workflowsMgtApiController
        .setVersionAndExpirationTime("ABC123", "42", null,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(workflowExpirationService).scheduleWorkflowExpiration(eq("42"), isA(Instant.class));
    HttpStatusCode statusCode = actualSetVersionAndExpirationTimeResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSetVersionAndExpirationTimeResult.getBody());
    assertEquals(204, actualSetVersionAndExpirationTimeResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualSetVersionAndExpirationTimeResult.hasBody());
    assertTrue(actualSetVersionAndExpirationTimeResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link WorkflowsMgtApiController#streamingLogs(String)}
   */
  @Test
  void testStreamingLogs() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);

    // Act and Assert
    assertNull(
        (new WorkflowsMgtApiController(workflowManagementService, workflowExpirationService, logsStreamingService,
            new DefaultSecretKeeper(repository, new SecretCryptVault()))).streamingLogs("ABC123").getTimeout());
  }

  /**
   * Method under test: {@link WorkflowsMgtApiController#streamingLogs(String)}
   */
  @Test
  void testStreamingLogs2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    LogsStreamingService logsStreamingService = mock(LogsStreamingService.class);
    doNothing().when(logsStreamingService).subscribe(Mockito.<SseEmitter>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    SecretRepository repository = mock(SecretRepository.class);

    // Act
    SseEmitter actualStreamingLogsResult = (new WorkflowsMgtApiController(workflowManagementService,
        workflowExpirationService, logsStreamingService, new DefaultSecretKeeper(repository, new SecretCryptVault())))
        .streamingLogs("ABC123");

    // Assert
    verify(logsStreamingService).subscribe(isA(SseEmitter.class));
    assertNull(actualStreamingLogsResult.getTimeout());
  }

  /**
   * Method under test: {@link WorkflowsMgtApiController#uploadSecret(SecretView)}
   */
  @Test
  void testUploadSecret() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SecretKeeper secretKeeper = mock(SecretKeeper.class);
    doNothing().when(secretKeeper).save(Mockito.<String>any(), Mockito.<byte[]>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    WorkflowsMgtApiController workflowsMgtApiController = new WorkflowsMgtApiController(workflowManagementService,
        workflowExpirationService, new LogsStreamingService(), secretKeeper);

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
   * Method under test: {@link WorkflowsMgtApiController#deleteSecret(String)}
   */
  @Test
  void testDeleteSecret() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SecretRepository repository = mock(SecretRepository.class);
    doNothing().when(repository).deleteByRef(Mockito.<String>any());
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    // Act
    ResponseEntity<Void> actualDeleteSecretResult = (new WorkflowsMgtApiController(workflowManagementService,
        workflowExpirationService, new LogsStreamingService(), secretKeeper))
        .deleteSecret("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");

    // Assert
    verify(repository).deleteByRef(eq("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY"));
    HttpStatusCode statusCode = actualDeleteSecretResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteSecretResult.getBody());
    assertEquals(204, actualDeleteSecretResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteSecretResult.hasBody());
    assertTrue(actualDeleteSecretResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link WorkflowsMgtApiController#getSecretMetadata()}
   */
  @Test
  void testGetSecretMetadata() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    SecretRepository repository = mock(SecretRepository.class);
    when(repository.findAll()).thenReturn(new ArrayList<>());
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, new DefaultObjectConverter(converters, optionalBiConverters));

    WorkflowExpirationService workflowExpirationService = new WorkflowExpirationService(
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class),
        mock(WorkflowExpirationPlanner.class));

    // Act
    ResponseEntity<List<SecretKeeper.SecretMetadata>> actualSecretMetadata = (new WorkflowsMgtApiController(
        workflowManagementService, workflowExpirationService, new LogsStreamingService(), secretKeeper))
        .getSecretMetadata();

    // Assert
    verify(repository).findAll();
    HttpStatusCode statusCode = actualSecretMetadata.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualSecretMetadata.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualSecretMetadata.getBody().isEmpty());
    assertTrue(actualSecretMetadata.hasBody());
    assertTrue(actualSecretMetadata.getHeaders().isEmpty());
  }
}
