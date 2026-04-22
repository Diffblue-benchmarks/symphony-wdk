package com.symphony.bdk.workflow.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.symphony.bdk.workflow.api.v1.WorkflowsMgtApi;
import com.symphony.bdk.workflow.api.v1.dto.SecretView;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper.SecretMetadata;
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
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.camunda.bpm.application.impl.EmbeddedProcessApplication;
import org.camunda.bpm.application.impl.EmbeddedProcessApplicationReferenceImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.application.DefaultProcessApplicationRegistration;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessApplicationDeploymentImpl;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.camunda.bpm.model.xml.impl.ModelBuilderImpl;
import org.camunda.bpm.model.xml.impl.ModelImpl;
import org.camunda.bpm.model.xml.impl.instance.DomDocumentImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class WorkflowsMgtApiControllerDiffblueTest {
  /**
   * Test {@link WorkflowsMgtApiController#saveAndDeploySwadl(String, SwadlView)}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.
   *   <li>Then calls {@link ObjectConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#saveAndDeploySwadl(String, SwadlView)}
   */
  @Test
  @DisplayName(
      "Test saveAndDeploySwadl(String, SwadlView); given Properties (default constructor) Publish is 'true'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.saveAndDeploySwadl(String, SwadlView)"
  })
  void testSaveAndDeploySwadl_givenPropertiesPublishIsTrue_thenCallsConvert()
      throws JsonProcessingException, ModelValidationException {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl instance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));

    CamundaTranslatedWorkflowContext camundaTranslatedWorkflowContext =
        new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph, instance);

    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("42");
    when(deploymentEntity.getName()).thenReturn("Name");
    Mockito.<Map<Class<?>, List>>when(deploymentEntity.getDeployedArtifacts())
        .thenReturn(new HashMap<>());

    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(deploymentEntity);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

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
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setCreatedBy(1L);
    versionedWorkflow2.setDeploymentId("42");
    versionedWorkflow2.setDescription("The characteristics of someone or something");
    versionedWorkflow2.setEtag(1L);
    versionedWorkflow2.setId("42");
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("Swadl");
    versionedWorkflow2.setVersion(1L);
    versionedWorkflow2.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow2);

    VersionedWorkflow versionedWorkflow3 = new VersionedWorkflow();
    versionedWorkflow3.setActive(true);
    versionedWorkflow3.setCreatedBy(1L);
    versionedWorkflow3.setDeploymentId("42");
    versionedWorkflow3.setDescription("The characteristics of someone or something");
    versionedWorkflow3.setEtag(1L);
    versionedWorkflow3.setId("42");
    versionedWorkflow3.setPublished(true);
    versionedWorkflow3.setSwadl("Swadl");
    versionedWorkflow3.setVersion(1L);
    versionedWorkflow3.setWorkflowId("42");

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow3);
    when(versionRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow);
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenReturn(emptyResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualSaveAndDeploySwadlResult =
        workflowsMgtApiController.saveAndDeploySwadl(
            "ABC123",
            SwadlView.builder()
                .createdBy(1L)
                .description("The characteristics of someone or something")
                .swadl("Swadl")
                .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndPublishedFalse("42");
    verify(deploymentEntity).getDeployedArtifacts();
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity, atLeast(1)).getName();
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
    HttpStatusCode statusCode = actualSaveAndDeploySwadlResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSaveAndDeploySwadlResult.getBody());
    assertEquals(204, actualSaveAndDeploySwadlResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualSaveAndDeploySwadlResult.hasBody());
    assertTrue(actualSaveAndDeploySwadlResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#saveAndDeploySwadl(String, SwadlView)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowManagementService#deploy(SwadlView)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#saveAndDeploySwadl(String, SwadlView)}
   */
  @Test
  @DisplayName("Test saveAndDeploySwadl(String, SwadlView); then calls deploy(SwadlView)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.saveAndDeploySwadl(String, SwadlView)"
  })
  void testSaveAndDeploySwadl_thenCallsDeploy() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    doNothing().when(workflowManagementService).deploy(Mockito.<SwadlView>any());
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualSaveAndDeploySwadlResult =
        workflowsMgtApiController.saveAndDeploySwadl(
            "ABC123",
            SwadlView.builder()
                .createdBy(1L)
                .description("The characteristics of someone or something")
                .swadl("Swadl")
                .build());

    // Assert
    verify(workflowManagementService).deploy(isA(SwadlView.class));
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
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#updateSwadl(String, SwadlView)}
   */
  @Test
  @DisplayName("Test updateSwadl(String, SwadlView)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.updateSwadl(String, SwadlView)"})
  void testUpdateSwadl() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    doNothing().when(workflowManagementService).update(Mockito.<SwadlView>any());
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualUpdateSwadlResult =
        workflowsMgtApiController.updateSwadl(
            "ABC123",
            SwadlView.builder()
                .createdBy(1L)
                .description("The characteristics of someone or something")
                .swadl("Swadl")
                .build());

    // Assert
    verify(workflowManagementService).update(isA(SwadlView.class));
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
   *
   * <ul>
   *   <li>Then calls {@link VersionedWorkflowRepository#findByWorkflowId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String,
   * Long, Boolean)}
   */
  @Test
  @DisplayName(
      "Test getVersionedWorkflow(String, String, Long, Boolean); then calls findByWorkflowId(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"
  })
  void testGetVersionedWorkflow_thenCallsFindByWorkflowId() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow =
        workflowsMgtApiController.getVersionedWorkflow("ABC123", "42", 1L, true);

    // Assert
    verify(versionRepository).findByWorkflowId("42");
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
   *
   * <ul>
   *   <li>Then calls {@link WorkflowManagementService#get(String, Long)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String,
   * Long, Boolean)}
   */
  @Test
  @DisplayName(
      "Test getVersionedWorkflow(String, String, Long, Boolean); then calls get(String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"
  })
  void testGetVersionedWorkflow_thenCallsGet() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    VersionedWorkflowView versionedWorkflowView =
        VersionedWorkflowView.builder()
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
    Optional<VersionedWorkflowView> ofResult = Optional.of(versionedWorkflowView);
    when(workflowManagementService.get(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow =
        workflowsMgtApiController.getVersionedWorkflow("ABC123", "42", 1L, false);

    // Assert
    verify(workflowManagementService).get("42", 1L);
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
   *
   * <ul>
   *   <li>Then calls {@link WorkflowManagementService#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String,
   * Long, Boolean)}
   */
  @Test
  @DisplayName("Test getVersionedWorkflow(String, String, Long, Boolean); then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"
  })
  void testGetVersionedWorkflow_thenCallsGet2() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    VersionedWorkflowView versionedWorkflowView =
        VersionedWorkflowView.builder()
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
    Optional<VersionedWorkflowView> ofResult = Optional.of(versionedWorkflowView);
    when(workflowManagementService.get(Mockito.<String>any())).thenReturn(ofResult);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow =
        workflowsMgtApiController.getVersionedWorkflow("ABC123", "42", null, false);

    // Assert
    verify(workflowManagementService).get("42");
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
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then calls {@link WorkflowManagementService#getAllVersions(String)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String,
   * Long, Boolean)}
   */
  @Test
  @DisplayName(
      "Test getVersionedWorkflow(String, String, Long, Boolean); when 'true'; then calls getAllVersions(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"
  })
  void testGetVersionedWorkflow_whenTrue_thenCallsGetAllVersions() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    when(workflowManagementService.getAllVersions(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow =
        workflowsMgtApiController.getVersionedWorkflow("ABC123", "42", 1L, true);

    // Assert
    verify(workflowManagementService).getAllVersions("42");
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
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then calls {@link WorkflowManagementService#getAllVersions(String)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#getVersionedWorkflow(String, String,
   * Long, Boolean)}
   */
  @Test
  @DisplayName(
      "Test getVersionedWorkflow(String, String, Long, Boolean); when 'true'; then calls getAllVersions(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.getVersionedWorkflow(String, String, Long, Boolean)"
  })
  void testGetVersionedWorkflow_whenTrue_thenCallsGetAllVersions2() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    when(workflowManagementService.getAllVersions(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<List<VersionedWorkflowView>> actualVersionedWorkflow =
        workflowsMgtApiController.getVersionedWorkflow("ABC123", "42", null, true);

    // Assert
    verify(workflowManagementService).getAllVersions("42");
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
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String,
   * String, Long)}
   */
  @Test
  @DisplayName("Test deleteWorkflowByIdAndVersion(String, String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.deleteWorkflowByIdAndVersion(String, String, Long)"
  })
  void testDeleteWorkflowByIdAndVersion() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine = mock(WorkflowEngine.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult =
        workflowsMgtApiController.deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

    // Assert
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    HttpStatusCode statusCode = actualDeleteWorkflowByIdAndVersionResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteWorkflowByIdAndVersionResult.getBody());
    assertEquals(204, actualDeleteWorkflowByIdAndVersionResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteWorkflowByIdAndVersionResult.hasBody());
    assertTrue(actualDeleteWorkflowByIdAndVersionResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String,
   * String, Long)}
   */
  @Test
  @DisplayName(
      "Test deleteWorkflowByIdAndVersion(String, String, Long); given VersionedWorkflow (default constructor) Active is 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.deleteWorkflowByIdAndVersion(String, String, Long)"
  })
  void testDeleteWorkflowByIdAndVersion_givenVersionedWorkflowActiveIsFalse() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(false);
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
    doNothing()
        .when(versionRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine = mock(WorkflowEngine.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult =
        workflowsMgtApiController.deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

    // Assert
    verify(versionRepository).deleteByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    HttpStatusCode statusCode = actualDeleteWorkflowByIdAndVersionResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteWorkflowByIdAndVersionResult.getBody());
    assertEquals(204, actualDeleteWorkflowByIdAndVersionResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteWorkflowByIdAndVersionResult.hasBody());
    assertTrue(actualDeleteWorkflowByIdAndVersionResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowManagementService#delete(String, Long)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String,
   * String, Long)}
   */
  @Test
  @DisplayName(
      "Test deleteWorkflowByIdAndVersion(String, String, Long); then calls delete(String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.deleteWorkflowByIdAndVersion(String, String, Long)"
  })
  void testDeleteWorkflowByIdAndVersion_thenCallsDelete() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    doNothing().when(workflowManagementService).delete(Mockito.<String>any(), Mockito.<Long>any());
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult =
        workflowsMgtApiController.deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

    // Assert
    verify(workflowManagementService).delete("42", 1L);
    HttpStatusCode statusCode = actualDeleteWorkflowByIdAndVersionResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteWorkflowByIdAndVersionResult.getBody());
    assertEquals(204, actualDeleteWorkflowByIdAndVersionResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteWorkflowByIdAndVersionResult.hasBody());
    assertTrue(actualDeleteWorkflowByIdAndVersionResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowEngine#undeployByDeploymentId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#deleteWorkflowByIdAndVersion(String,
   * String, Long)}
   */
  @Test
  @DisplayName(
      "Test deleteWorkflowByIdAndVersion(String, String, Long); then calls undeployByDeploymentId(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.deleteWorkflowByIdAndVersion(String, String, Long)"
  })
  void testDeleteWorkflowByIdAndVersion_thenCallsUndeployByDeploymentId() {
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
    doNothing()
        .when(versionRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualDeleteWorkflowByIdAndVersionResult =
        workflowsMgtApiController.deleteWorkflowByIdAndVersion("ABC123", "42", 1L);

    // Assert
    verify(workflowEngine).undeployByDeploymentId("42");
    verify(versionRepository).deleteByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    HttpStatusCode statusCode = actualDeleteWorkflowByIdAndVersionResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteWorkflowByIdAndVersionResult.getBody());
    assertEquals(204, actualDeleteWorkflowByIdAndVersionResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteWorkflowByIdAndVersionResult.hasBody());
    assertTrue(actualDeleteWorkflowByIdAndVersionResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long,
   * Instant)}.
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String,
   * String, Long, Instant)}
   */
  @Test
  @DisplayName("Test setVersionAndExpirationTime(String, String, Long, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.setVersionAndExpirationTime(String, String, Long, Instant)"
  })
  void testSetVersionAndExpirationTime() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult =
        workflowsMgtApiController.setVersionAndExpirationTime("ABC123", "42", null, null);

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
   * Test {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long,
   * Instant)}.
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String,
   * String, Long, Instant)}
   */
  @Test
  @DisplayName("Test setVersionAndExpirationTime(String, String, Long, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.setVersionAndExpirationTime(String, String, Long, Instant)"
  })
  void testSetVersionAndExpirationTime2() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    doNothing()
        .when(workflowManagementService)
        .setActiveVersion(Mockito.<String>any(), Mockito.<Long>any());

    WorkflowExpirationJobRepository expirationJobRepository =
        mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any()))
        .thenReturn(new ArrayList<>());

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(2L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(2L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Deployed workflow {} {}");
    versionedWorkflow.setVersion(2L);
    versionedWorkflow.setWorkflowId("42");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow);

    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(versionedWorkflowList);

    WorkflowExpirationPlanner workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    doNothing()
        .when(workflowExpirationPlanner)
        .planExpiration(Mockito.<WorkflowExpirationJob>any());

    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            expirationJobRepository, versioningRepository, workflowExpirationPlanner);
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult =
        workflowsMgtApiController.setVersionAndExpirationTime(
            "ABC123",
            "42",
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(workflowExpirationPlanner).planExpiration(isA(WorkflowExpirationJob.class));
    verify(workflowManagementService).setActiveVersion("42", 1L);
    verify(versioningRepository).findByWorkflowId("42");
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
   * Test {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long,
   * Instant)}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String,
   * String, Long, Instant)}
   */
  @Test
  @DisplayName(
      "Test setVersionAndExpirationTime(String, String, Long, Instant); given VersionedWorkflow (default constructor) Active is 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.setVersionAndExpirationTime(String, String, Long, Instant)"
  })
  void testSetVersionAndExpirationTime_givenVersionedWorkflowActiveIsFalse()
      throws JsonProcessingException, ModelValidationException {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl instance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));

    CamundaTranslatedWorkflowContext camundaTranslatedWorkflowContext =
        new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph, instance);

    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    DeploymentEntity deployment = new DeploymentEntity();
    EmbeddedProcessApplicationReferenceImpl reference =
        new EmbeddedProcessApplicationReferenceImpl(new EmbeddedProcessApplication());
    DefaultProcessApplicationRegistration registration =
        new DefaultProcessApplicationRegistration(
            reference, new HashSet<>(), "Process Enginen Name");

    ProcessApplicationDeploymentImpl processApplicationDeploymentImpl =
        new ProcessApplicationDeploymentImpl(deployment, registration);
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(processApplicationDeploymentImpl);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

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

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setCreatedBy(1L);
    versionedWorkflow2.setDeploymentId("42");
    versionedWorkflow2.setDescription("The characteristics of someone or something");
    versionedWorkflow2.setEtag(1L);
    versionedWorkflow2.setId("42");
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("Swadl");
    versionedWorkflow2.setVersion(1L);
    versionedWorkflow2.setWorkflowId("42");

    VersionedWorkflow versionedWorkflow3 = new VersionedWorkflow();
    versionedWorkflow3.setActive(true);
    versionedWorkflow3.setCreatedBy(1L);
    versionedWorkflow3.setDeploymentId("42");
    versionedWorkflow3.setDescription("The characteristics of someone or something");
    versionedWorkflow3.setEtag(1L);
    versionedWorkflow3.setId("42");
    versionedWorkflow3.setPublished(true);
    versionedWorkflow3.setSwadl("Swadl");
    versionedWorkflow3.setVersion(1L);
    versionedWorkflow3.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow3);

    VersionedWorkflow versionedWorkflow4 = new VersionedWorkflow();
    versionedWorkflow4.setActive(true);
    versionedWorkflow4.setCreatedBy(1L);
    versionedWorkflow4.setDeploymentId("42");
    versionedWorkflow4.setDescription("The characteristics of someone or something");
    versionedWorkflow4.setEtag(1L);
    versionedWorkflow4.setId("42");
    versionedWorkflow4.setPublished(true);
    versionedWorkflow4.setSwadl("Swadl");
    versionedWorkflow4.setVersion(1L);
    versionedWorkflow4.setWorkflowId("42");

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow4);
    when(versionRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult2);
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    WorkflowExpirationJobRepository expirationJobRepository =
        mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any()))
        .thenReturn(new ArrayList<>());

    VersionedWorkflow versionedWorkflow5 = new VersionedWorkflow();
    versionedWorkflow5.setActive(true);
    versionedWorkflow5.setCreatedBy(2L);
    versionedWorkflow5.setDeploymentId("42");
    versionedWorkflow5.setDescription("The characteristics of someone or something");
    versionedWorkflow5.setEtag(2L);
    versionedWorkflow5.setId("42");
    versionedWorkflow5.setPublished(true);
    versionedWorkflow5.setSwadl("Deployed workflow {} {}");
    versionedWorkflow5.setVersion(2L);
    versionedWorkflow5.setWorkflowId("42");

    VersionedWorkflow versionedWorkflow6 = new VersionedWorkflow();
    versionedWorkflow6.setActive(false);
    versionedWorkflow6.setCreatedBy(1L);
    versionedWorkflow6.setDeploymentId("Deployed workflow {} {}");
    versionedWorkflow6.setDescription("Deployed workflow {} {}");
    versionedWorkflow6.setEtag(1L);
    versionedWorkflow6.setId("Deployed workflow {} {}");
    versionedWorkflow6.setPublished(false);
    versionedWorkflow6.setSwadl("event={}, deployment={}, deployment_name={}, process_key={}");
    versionedWorkflow6.setVersion(1L);
    versionedWorkflow6.setWorkflowId("Deployed workflow {} {}");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow6);
    versionedWorkflowList.add(versionedWorkflow5);

    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(versionedWorkflowList);

    WorkflowExpirationPlanner workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    doNothing()
        .when(workflowExpirationPlanner)
        .planExpiration(Mockito.<WorkflowExpirationJob>any());

    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            expirationJobRepository, versioningRepository, workflowExpirationPlanner);
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult =
        workflowsMgtApiController.setVersionAndExpirationTime(
            "ABC123",
            "42",
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(workflowExpirationPlanner, atLeast(1))
        .planExpiration(Mockito.<WorkflowExpirationJob>any());
    verify(versioningRepository).findByWorkflowId("42");
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
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
   * Test {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long,
   * Instant)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowEngine#deploy(Workflow)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String,
   * String, Long, Instant)}
   */
  @Test
  @DisplayName(
      "Test setVersionAndExpirationTime(String, String, Long, Instant); then calls deploy(Workflow)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.setVersionAndExpirationTime(String, String, Long, Instant)"
  })
  void testSetVersionAndExpirationTime_thenCallsDeploy() {
    // Arrange
    WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine = mock(WorkflowEngine.class);
    when(workflowEngine.deploy(Mockito.<Workflow>any())).thenReturn("Deploy");

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

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setCreatedBy(1L);
    versionedWorkflow2.setDeploymentId("42");
    versionedWorkflow2.setDescription("The characteristics of someone or something");
    versionedWorkflow2.setEtag(1L);
    versionedWorkflow2.setId("42");
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("Swadl");
    versionedWorkflow2.setVersion(1L);
    versionedWorkflow2.setWorkflowId("42");

    VersionedWorkflow versionedWorkflow3 = new VersionedWorkflow();
    versionedWorkflow3.setActive(true);
    versionedWorkflow3.setCreatedBy(1L);
    versionedWorkflow3.setDeploymentId("42");
    versionedWorkflow3.setDescription("The characteristics of someone or something");
    versionedWorkflow3.setEtag(1L);
    versionedWorkflow3.setId("42");
    versionedWorkflow3.setPublished(true);
    versionedWorkflow3.setSwadl("Swadl");
    versionedWorkflow3.setVersion(1L);
    versionedWorkflow3.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow3);

    VersionedWorkflow versionedWorkflow4 = new VersionedWorkflow();
    versionedWorkflow4.setActive(true);
    versionedWorkflow4.setCreatedBy(1L);
    versionedWorkflow4.setDeploymentId("42");
    versionedWorkflow4.setDescription("The characteristics of someone or something");
    versionedWorkflow4.setEtag(1L);
    versionedWorkflow4.setId("42");
    versionedWorkflow4.setPublished(true);
    versionedWorkflow4.setSwadl("Swadl");
    versionedWorkflow4.setVersion(1L);
    versionedWorkflow4.setWorkflowId("42");

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow4);
    when(versionRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult2);
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenReturn(workflow);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    WorkflowExpirationJobRepository expirationJobRepository =
        mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any()))
        .thenReturn(new ArrayList<>());

    VersionedWorkflow versionedWorkflow5 = new VersionedWorkflow();
    versionedWorkflow5.setActive(true);
    versionedWorkflow5.setCreatedBy(2L);
    versionedWorkflow5.setDeploymentId("42");
    versionedWorkflow5.setDescription("The characteristics of someone or something");
    versionedWorkflow5.setEtag(2L);
    versionedWorkflow5.setId("42");
    versionedWorkflow5.setPublished(true);
    versionedWorkflow5.setSwadl("Deployed workflow {} {}");
    versionedWorkflow5.setVersion(2L);
    versionedWorkflow5.setWorkflowId("42");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow5);

    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(versionedWorkflowList);

    WorkflowExpirationPlanner workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    doNothing()
        .when(workflowExpirationPlanner)
        .planExpiration(Mockito.<WorkflowExpirationJob>any());

    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            expirationJobRepository, versioningRepository, workflowExpirationPlanner);
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult =
        workflowsMgtApiController.setVersionAndExpirationTime(
            "ABC123",
            "42",
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
    verify(workflowExpirationPlanner).planExpiration(isA(WorkflowExpirationJob.class));
    verify(versioningRepository).findByWorkflowId("42");
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
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
   * Test {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long,
   * Instant)}.
   *
   * <ul>
   *   <li>Then calls {@link CamundaBpmnBuilder#deployWorkflow(CamundaTranslatedWorkflowContext)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String,
   * String, Long, Instant)}
   */
  @Test
  @DisplayName(
      "Test setVersionAndExpirationTime(String, String, Long, Instant); then calls deployWorkflow(CamundaTranslatedWorkflowContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.setVersionAndExpirationTime(String, String, Long, Instant)"
  })
  void testSetVersionAndExpirationTime_thenCallsDeployWorkflow()
      throws JsonProcessingException, ModelValidationException {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl instance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));

    CamundaTranslatedWorkflowContext camundaTranslatedWorkflowContext =
        new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph, instance);

    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    DeploymentEntity deployment = new DeploymentEntity();
    EmbeddedProcessApplicationReferenceImpl reference =
        new EmbeddedProcessApplicationReferenceImpl(new EmbeddedProcessApplication());
    DefaultProcessApplicationRegistration registration =
        new DefaultProcessApplicationRegistration(
            reference, new HashSet<>(), "Process Enginen Name");

    ProcessApplicationDeploymentImpl processApplicationDeploymentImpl =
        new ProcessApplicationDeploymentImpl(deployment, registration);
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(processApplicationDeploymentImpl);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

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

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setCreatedBy(1L);
    versionedWorkflow2.setDeploymentId("42");
    versionedWorkflow2.setDescription("The characteristics of someone or something");
    versionedWorkflow2.setEtag(1L);
    versionedWorkflow2.setId("42");
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("Swadl");
    versionedWorkflow2.setVersion(1L);
    versionedWorkflow2.setWorkflowId("42");

    VersionedWorkflow versionedWorkflow3 = new VersionedWorkflow();
    versionedWorkflow3.setActive(true);
    versionedWorkflow3.setCreatedBy(1L);
    versionedWorkflow3.setDeploymentId("42");
    versionedWorkflow3.setDescription("The characteristics of someone or something");
    versionedWorkflow3.setEtag(1L);
    versionedWorkflow3.setId("42");
    versionedWorkflow3.setPublished(true);
    versionedWorkflow3.setSwadl("Swadl");
    versionedWorkflow3.setVersion(1L);
    versionedWorkflow3.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow3);

    VersionedWorkflow versionedWorkflow4 = new VersionedWorkflow();
    versionedWorkflow4.setActive(true);
    versionedWorkflow4.setCreatedBy(1L);
    versionedWorkflow4.setDeploymentId("42");
    versionedWorkflow4.setDescription("The characteristics of someone or something");
    versionedWorkflow4.setEtag(1L);
    versionedWorkflow4.setId("42");
    versionedWorkflow4.setPublished(true);
    versionedWorkflow4.setSwadl("Swadl");
    versionedWorkflow4.setVersion(1L);
    versionedWorkflow4.setWorkflowId("42");

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow4);
    when(versionRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult2);
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    WorkflowExpirationJobRepository expirationJobRepository =
        mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any()))
        .thenReturn(new ArrayList<>());

    VersionedWorkflow versionedWorkflow5 = new VersionedWorkflow();
    versionedWorkflow5.setActive(true);
    versionedWorkflow5.setCreatedBy(2L);
    versionedWorkflow5.setDeploymentId("42");
    versionedWorkflow5.setDescription("The characteristics of someone or something");
    versionedWorkflow5.setEtag(2L);
    versionedWorkflow5.setId("42");
    versionedWorkflow5.setPublished(true);
    versionedWorkflow5.setSwadl("Deployed workflow {} {}");
    versionedWorkflow5.setVersion(2L);
    versionedWorkflow5.setWorkflowId("42");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow5);

    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(versionedWorkflowList);

    WorkflowExpirationPlanner workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    doNothing()
        .when(workflowExpirationPlanner)
        .planExpiration(Mockito.<WorkflowExpirationJob>any());

    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            expirationJobRepository, versioningRepository, workflowExpirationPlanner);
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult =
        workflowsMgtApiController.setVersionAndExpirationTime(
            "ABC123",
            "42",
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(workflowExpirationPlanner).planExpiration(isA(WorkflowExpirationJob.class));
    verify(versioningRepository).findByWorkflowId("42");
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
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
   * Test {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String, String, Long,
   * Instant)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowExpirationService#scheduleWorkflowExpiration(String, Instant)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#setVersionAndExpirationTime(String,
   * String, Long, Instant)}
   */
  @Test
  @DisplayName(
      "Test setVersionAndExpirationTime(String, String, Long, Instant); then calls scheduleWorkflowExpiration(String, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsMgtApiController.setVersionAndExpirationTime(String, String, Long, Instant)"
  })
  void testSetVersionAndExpirationTime_thenCallsScheduleWorkflowExpiration() {
    // Arrange
    WorkflowManagementService workflowManagementService = mock(WorkflowManagementService.class);
    doNothing()
        .when(workflowManagementService)
        .setActiveVersion(Mockito.<String>any(), Mockito.<Long>any());

    WorkflowExpirationService workflowExpirationService = mock(WorkflowExpirationService.class);
    doNothing()
        .when(workflowExpirationService)
        .scheduleWorkflowExpiration(Mockito.<String>any(), Mockito.<Instant>any());
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    ResponseEntity<Void> actualSetVersionAndExpirationTimeResult =
        workflowsMgtApiController.setVersionAndExpirationTime(
            "ABC123",
            "42",
            1L,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(workflowExpirationService).scheduleWorkflowExpiration(eq("42"), isA(Instant.class));
    verify(workflowManagementService).setActiveVersion("42", 1L);
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
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#streamingLogs(String)}
   */
  @Test
  @DisplayName("Test streamingLogs(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SseEmitter WorkflowsMgtApiController.streamingLogs(String)"})
  void testStreamingLogs() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    LogsStreamingService logsStreamingService = new LogsStreamingService();
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act and Assert
    assertNull(workflowsMgtApiController.streamingLogs("ABC123").getTimeout());
  }

  /**
   * Test {@link WorkflowsMgtApiController#streamingLogs(String)}.
   *
   * <ul>
   *   <li>Then calls {@link LogsStreamingService#subscribe(SseEmitter)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#streamingLogs(String)}
   */
  @Test
  @DisplayName("Test streamingLogs(String); then calls subscribe(SseEmitter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SseEmitter WorkflowsMgtApiController.streamingLogs(String)"})
  void testStreamingLogs_thenCallsSubscribe() {
    // Arrange
    LogsStreamingService logsStreamingService = mock(LogsStreamingService.class);
    doNothing().when(logsStreamingService).subscribe(Mockito.<SseEmitter>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            logsStreamingService,
            secretKeeper);

    // Act
    SseEmitter actualStreamingLogsResult = workflowsMgtApiController.streamingLogs("ABC123");

    // Assert
    verify(logsStreamingService).subscribe(isA(SseEmitter.class));
    assertNull(actualStreamingLogsResult.getTimeout());
  }

  /**
   * Test {@link WorkflowsMgtApiController#uploadSecret(SecretView)}.
   *
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#uploadSecret(SecretView)}
   */
  @Test
  @DisplayName("Test uploadSecret(SecretView); then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.uploadSecret(SecretView)"})
  void testUploadSecret_thenStatusCodeReturnHttpStatus() {
    // Arrange
    SecretKeeper secretKeeper = mock(SecretKeeper.class);
    doNothing().when(secretKeeper).save(Mockito.<String>any(), Mockito.<byte[]>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            new LogsStreamingService(),
            secretKeeper);
    SecretView secret = new SecretView("Key", "A\u0000A\u0000".toCharArray());

    // Act
    ResponseEntity<Void> actualUploadSecretResult = workflowsMgtApiController.uploadSecret(secret);

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
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#deleteSecret(String)}
   */
  @Test
  @DisplayName("Test deleteSecret(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.deleteSecret(String)"})
  void testDeleteSecret() {
    // Arrange
    SecretRepository repository = mock(SecretRepository.class);
    doNothing().when(repository).deleteByRef(Mockito.<String>any());
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            new LogsStreamingService(),
            secretKeeper);

    // Act
    ResponseEntity<Void> actualDeleteSecretResult =
        workflowsMgtApiController.deleteSecret("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");

    // Assert
    verify(repository).deleteByRef("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
    HttpStatusCode statusCode = actualDeleteSecretResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualDeleteSecretResult.getBody());
    assertEquals(204, actualDeleteSecretResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualDeleteSecretResult.hasBody());
    assertTrue(actualDeleteSecretResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsMgtApi#getSecretMetadata()} via interface reference.
   *
   * <p>Method under test: {@link WorkflowsMgtApi#getSecretMetadata()}
   */
  @Test
  @DisplayName("Test getSecretMetadata() via WorkflowsMgtApi interface")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApi.getSecretMetadata()"})
  void testGetSecretMetadata_viaInterface() {
    // Arrange
    SecretRepository repository = mock(SecretRepository.class);
    when(repository.findAll()).thenReturn(new ArrayList<>());
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));

    WorkflowsMgtApi workflowsMgtApi =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            new LogsStreamingService(),
            secretKeeper);

    // Act
    ResponseEntity<List<SecretMetadata>> actualSecretMetadata =
        workflowsMgtApi.getSecretMetadata();

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

  /**
   * Test {@link WorkflowsMgtApiController#getSecretMetadata()}.
   *
   * <p>Method under test: {@link WorkflowsMgtApiController#getSecretMetadata()}
   */
  @Test
  @DisplayName("Test getSecretMetadata()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsMgtApiController.getSecretMetadata()"})
  void testGetSecretMetadata() {
    // Arrange
    SecretRepository repository = mock(SecretRepository.class);
    when(repository.findAll()).thenReturn(new ArrayList<>());
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(new RepositoryServiceImpl(), null, null, null);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            mock(WorkflowExpirationPlanner.class));

    WorkflowsMgtApiController workflowsMgtApiController =
        new WorkflowsMgtApiController(
            workflowManagementService,
            workflowExpirationService,
            new LogsStreamingService(),
            secretKeeper);

    // Act
    ResponseEntity<List<SecretMetadata>> actualSecretMetadata =
        workflowsMgtApiController.getSecretMetadata();

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
