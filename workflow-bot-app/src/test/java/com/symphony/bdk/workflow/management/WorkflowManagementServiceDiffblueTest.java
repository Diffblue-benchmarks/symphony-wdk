package com.symphony.bdk.workflow.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.session.SessionService;
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
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WorkflowManagementServiceDiffblueTest {
  @Mock private ObjectConverter objectConverter;

  @Mock private VersionedWorkflowRepository versionedWorkflowRepository;

  @Mock private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @InjectMocks private WorkflowManagementService workflowManagementService;

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testDeploy() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowManagementService.deploy(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndPublishedFalse("42");
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testDeploy2() {
    // Arrange
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class)))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowManagementService.deploy(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testDeploy3() {
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
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(workflowEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn("Deploy");

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
    when(versionedWorkflowRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenReturn(emptyResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowManagementService.deploy(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionedWorkflowRepository).findByWorkflowIdAndPublishedFalse("42");
    verify(versionedWorkflowRepository).saveAndFlush(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testDeploy4() {
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
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(workflowEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn("Deploy");

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
    when(versionedWorkflowRepository.save(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(emptyResult);
    Optional<VersionedWorkflow> emptyResult2 = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenReturn(emptyResult2);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    // Act
    workflowManagementService.deploy(
        SwadlView.builder()
            .createdBy(1L)
            .description("The characteristics of someone or something")
            .swadl("Swadl")
            .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionedWorkflowRepository).findByWorkflowIdAndPublishedFalse("42");
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <ul>
   *   <li>Given {@link DeploymentEntity} {@link DeploymentEntity#getId()} return {@code 42}.
   *   <li>Then calls {@link CamundaBpmnBuilder#deployWorkflow(CamundaTranslatedWorkflowContext)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test deploy(SwadlView); given DeploymentEntity getId() return '42'; then calls deployWorkflow(CamundaTranslatedWorkflowContext)")
  @Tag("MaintainedByDiffblue")
  void testDeploy_givenDeploymentEntityGetIdReturn42_thenCallsDeployWorkflow()
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

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    workflowManagementService.deploy(
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
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code false}.
   *   <li>Then calls {@link CamundaBpmnBuilder#translateWorkflow(Workflow)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test deploy(SwadlView); given Properties (default constructor) Publish is 'false'; then calls translateWorkflow(Workflow)")
  @Tag("MaintainedByDiffblue")
  void testDeploy_givenPropertiesPublishIsFalse_thenCallsTranslateWorkflow()
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

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenReturn(emptyResult);

    Properties properties2 = new Properties();
    properties2.setPublish(false);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    workflowManagementService.deploy(
        SwadlView.builder()
            .createdBy(1L)
            .description("The characteristics of someone or something")
            .swadl("Swadl")
            .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndPublishedFalse("42");
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <ul>
   *   <li>Then calls {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView); then calls deploy(CamundaTranslatedWorkflowContext)")
  @Tag("MaintainedByDiffblue")
  void testDeploy_thenCallsDeploy() {
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

    CamundaEngine workflowEngine = mock(CamundaEngine.class);
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(workflowEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn("Deploy");

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

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    workflowManagementService.deploy(
        SwadlView.builder()
            .createdBy(1L)
            .description("The characteristics of someone or something")
            .swadl("Swadl")
            .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndPublishedFalse("42");
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  void testDeploy_thenThrowIllegalArgumentException() {
    // Arrange
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
    when(versionRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            workflowManagementService.deploy(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndPublishedFalse("42");
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testUpdate() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowManagementService.update(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc("42");
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testUpdate2() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(emptyResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowManagementService.update(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc("42");
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testUpdate3() {
    // Arrange
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class)))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowManagementService.update(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testUpdate4() {
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
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(workflowEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn("Deploy");

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(false);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowManagementService.update(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc("42");
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testUpdate5() {
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
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(workflowEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn("Deploy");

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(false);
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
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow2);
    when(versionedWorkflowRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult2);
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowManagementService.update(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc("42");
    verify(versionedWorkflowRepository).saveAndFlush(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("MaintainedByDiffblue")
  void testUpdate6() {
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
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(workflowEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn("Deploy");

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(false);
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
    when(versionedWorkflowRepository.save(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow2);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(emptyResult);
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    // Act
    workflowManagementService.update(
        SwadlView.builder()
            .createdBy(1L)
            .description("The characteristics of someone or something")
            .swadl("Swadl")
            .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc("42");
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <ul>
   *   <li>Given {@link DeploymentEntity} {@link DeploymentEntity#getId()} return {@code 42}.
   *   <li>Then calls {@link CamundaBpmnBuilder#deployWorkflow(CamundaTranslatedWorkflowContext)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test update(SwadlView); given DeploymentEntity getId() return '42'; then calls deployWorkflow(CamundaTranslatedWorkflowContext)")
  @Tag("MaintainedByDiffblue")
  void testUpdate_givenDeploymentEntityGetIdReturn42_thenCallsDeployWorkflow()
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
    versionedWorkflow.setPublished(false);
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
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    workflowManagementService.update(
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
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc("42");
    verify(deploymentEntity).getDeployedArtifacts();
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity, atLeast(1)).getName();
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code false}.
   *   <li>Then calls {@link CamundaBpmnBuilder#translateWorkflow(Workflow)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test update(SwadlView); given Properties (default constructor) Publish is 'false'; then calls translateWorkflow(Workflow)")
  @Tag("MaintainedByDiffblue")
  void testUpdate_givenPropertiesPublishIsFalse_thenCallsTranslateWorkflow()
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
    versionedWorkflow.setPublished(false);
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

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(false);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    workflowManagementService.update(
        SwadlView.builder()
            .createdBy(1L)
            .description("The characteristics of someone or something")
            .swadl("Swadl")
            .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc("42");
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <ul>
   *   <li>Then calls {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView); then calls deploy(CamundaTranslatedWorkflowContext)")
  @Tag("MaintainedByDiffblue")
  void testUpdate_thenCallsDeploy() {
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

    CamundaEngine workflowEngine = mock(CamundaEngine.class);
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(workflowEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn("Deploy");

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(false);
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
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow2);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    workflowManagementService.update(
        SwadlView.builder()
            .createdBy(1L)
            .description("The characteristics of someone or something")
            .swadl("Swadl")
            .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc("42");
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView); then throw UnsupportedOperationException")
  @Tag("MaintainedByDiffblue")
  void testUpdate_thenThrowUnsupportedOperationException() {
    // Arrange
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
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    DefaultObjectConverter objectConverter = mock(DefaultObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(Workflow.class))).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            workflowManagementService.update(
                SwadlView.builder()
                    .createdBy(1L)
                    .description("The characteristics of someone or something")
                    .swadl("Swadl")
                    .build()));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc("42");
  }

  /**
   * Test {@link WorkflowManagementService#get(String)} with {@code id}.
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName("Test get(String) with 'id'")
  @Tag("MaintainedByDiffblue")
  void testGetWithId() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.get("42"));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
  }

  /**
   * Test {@link WorkflowManagementService#get(String)} with {@code id}.
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName("Test get(String) with 'id'")
  @Tag("MaintainedByDiffblue")
  void testGetWithId2() {
    // Arrange
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
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult);
    when(objectConverter.convert(Mockito.<Object>any(), eq(VersionedWorkflowView.class)))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.get("42"));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
  }

  /**
   * Test {@link WorkflowManagementService#get(String, Long)} with {@code id}, {@code version}.
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String, Long)}
   */
  @Test
  @DisplayName("Test get(String, Long) with 'id', 'version'")
  @Tag("MaintainedByDiffblue")
  void testGetWithIdVersion() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.get("42", 1L));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#get(String, Long)} with {@code id}, {@code version}.
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String, Long)}
   */
  @Test
  @DisplayName("Test get(String, Long) with 'id', 'version'")
  @Tag("MaintainedByDiffblue")
  void testGetWithIdVersion2() {
    // Arrange
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
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    when(objectConverter.convert(Mockito.<Object>any(), eq(VersionedWorkflowView.class)))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.get("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#get(String, Long)} with {@code id}, {@code version}.
   *
   * <ul>
   *   <li>Then return {@link Optional#get()} DeploymentId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String, Long)}
   */
  @Test
  @DisplayName(
      "Test get(String, Long) with 'id', 'version'; then return get() DeploymentId is '42'")
  @Tag("MaintainedByDiffblue")
  void testGetWithIdVersion_thenReturnGetDeploymentIdIs42() {
    // Arrange
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
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(VersionedWorkflowView.class)))
        .thenReturn(
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
                .build());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    Optional<VersionedWorkflowView> actualGetResult = workflowManagementService.get("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    VersionedWorkflowView getResult = actualGetResult.get();
    assertEquals("42", getResult.getDeploymentId());
    assertEquals("42", getResult.getId());
    assertEquals("42", getResult.getWorkflowId());
    assertEquals("Swadl", getResult.getSwadl());
    assertEquals("The characteristics of someone or something", getResult.getDescription());
    assertEquals(1L, getResult.getCreatedBy().longValue());
    assertEquals(1L, getResult.getVersion().longValue());
    assertTrue(getResult.getActive());
    assertTrue(getResult.getPublished());
    assertTrue(actualGetResult.isPresent());
  }

  /**
   * Test {@link WorkflowManagementService#get(String, Long)} with {@code id}, {@code version}.
   *
   * <ul>
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String, Long)}
   */
  @Test
  @DisplayName("Test get(String, Long) with 'id', 'version'; then return not Present")
  @Tag("MaintainedByDiffblue")
  void testGetWithIdVersion_thenReturnNotPresent() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(
            workflowEngine, versionRepository, mock(ObjectConverter.class));

    // Act
    Optional<VersionedWorkflowView> actualGetResult = workflowManagementService.get("42", 1L);

    // Assert
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    assertFalse(actualGetResult.isPresent());
  }

  /**
   * Test {@link WorkflowManagementService#get(String)} with {@code id}.
   *
   * <ul>
   *   <li>Then return {@link Optional#get()} DeploymentId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName("Test get(String) with 'id'; then return get() DeploymentId is '42'")
  @Tag("MaintainedByDiffblue")
  void testGetWithId_thenReturnGetDeploymentIdIs42() {
    // Arrange
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
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult);

    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), eq(VersionedWorkflowView.class)))
        .thenReturn(
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
                .build());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    Optional<VersionedWorkflowView> actualGetResult = workflowManagementService.get("42");

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    VersionedWorkflowView getResult = actualGetResult.get();
    assertEquals("42", getResult.getDeploymentId());
    assertEquals("42", getResult.getId());
    assertEquals("42", getResult.getWorkflowId());
    assertEquals("Swadl", getResult.getSwadl());
    assertEquals("The characteristics of someone or something", getResult.getDescription());
    assertEquals(1L, getResult.getCreatedBy().longValue());
    assertEquals(1L, getResult.getVersion().longValue());
    assertTrue(getResult.getActive());
    assertTrue(getResult.getPublished());
    assertTrue(actualGetResult.isPresent());
  }

  /**
   * Test {@link WorkflowManagementService#get(String)} with {@code id}.
   *
   * <ul>
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName("Test get(String) with 'id'; then return not Present")
  @Tag("MaintainedByDiffblue")
  void testGetWithId_thenReturnNotPresent() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(emptyResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(
            workflowEngine, versionRepository, mock(ObjectConverter.class));

    // Act
    Optional<VersionedWorkflowView> actualGetResult = workflowManagementService.get("42");

    // Assert
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    assertFalse(actualGetResult.isPresent());
  }

  /**
   * Test {@link WorkflowManagementService#getAllVersions(String)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  @DisplayName("Test getAllVersions(String)")
  @Tag("MaintainedByDiffblue")
  void testGetAllVersions() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowId(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.getAllVersions("42"));
    verify(versionRepository).findByWorkflowId("42");
  }

  /**
   * Test {@link WorkflowManagementService#getAllVersions(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convertCollection(List, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  @DisplayName("Test getAllVersions(String); then calls convertCollection(List, Class)")
  @Tag("MaintainedByDiffblue")
  void testGetAllVersions_thenCallsConvertCollection() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.getAllVersions("42"));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowId("42");
  }

  /**
   * Test {@link WorkflowManagementService#getAllVersions(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  @DisplayName("Test getAllVersions(String); then return Empty")
  @Tag("MaintainedByDiffblue")
  void testGetAllVersions_thenReturnEmpty() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    List<VersionedWorkflowView> actualAllVersions = workflowManagementService.getAllVersions("42");

    // Assert
    verify(versionRepository).findByWorkflowId("42");
    assertTrue(actualAllVersions.isEmpty());
  }

  /**
   * Test {@link WorkflowManagementService#delete(String)} with {@code id}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'id'")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithId() {
    // Arrange
    doThrow(new NotFoundException("An error occurred"))
        .when(versionedWorkflowRepository)
        .deleteByWorkflowId(Mockito.<String>any());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42"));
    verify(versionedWorkflowRepository).deleteByWorkflowId("42");
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithIdVersion() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42", 1L));
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithIdVersion2() {
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

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithIdVersion3() {
    // Arrange
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
    doThrow(new NotFoundException("An error occurred"))
        .when(versionedWorkflowRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42", 1L));
    verify(versionedWorkflowRepository).deleteByWorkflowIdAndVersion("42", 1L);
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithIdVersion4() {
    // Arrange
    doThrow(new NotFoundException("An error occurred"))
        .when(workflowEngine)
        .undeployByDeploymentId(Mockito.<String>any());

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
    doNothing()
        .when(versionedWorkflowRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42", 1L));
    verify(workflowEngine).undeployByDeploymentId("42");
    verify(versionedWorkflowRepository).deleteByWorkflowIdAndVersion("42", 1L);
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName(
      "Test delete(String, Long) with 'id', 'version'; given VersionedWorkflow (default constructor) Active is 'false'")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithIdVersion_givenVersionedWorkflowActiveIsFalse() {
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

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
    verify(versionRepository).deleteByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   *
   * <ul>
   *   <li>Given {@link WorkflowEngine} {@link WorkflowEngine#undeployByDeploymentId(String)} does
   *       nothing.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName(
      "Test delete(String, Long) with 'id', 'version'; given WorkflowEngine undeployByDeploymentId(String) does nothing")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithIdVersion_givenWorkflowEngineUndeployByDeploymentIdDoesNothing() {
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

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
    verify(workflowEngine).undeployByDeploymentId("42");
    verify(versionRepository).deleteByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#delete(String)} with {@code id}.
   *
   * <ul>
   *   <li>Then calls {@link CamundaEngine#undeployByWorkflowId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'id'; then calls undeployByWorkflowId(String)")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithId_thenCallsUndeployByWorkflowId() {
    // Arrange
    CamundaEngine workflowEngine = mock(CamundaEngine.class);
    doNothing().when(workflowEngine).undeployByWorkflowId(Mockito.<String>any());

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    doNothing().when(versionRepository).deleteByWorkflowId(Mockito.<String>any());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act
    workflowManagementService.delete("42");

    // Assert
    verify(workflowEngine).undeployByWorkflowId("42");
    verify(versionRepository).deleteByWorkflowId("42");
  }

  /**
   * Test {@link WorkflowManagementService#delete(String)} with {@code id}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowEngine#undeployByWorkflowId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'id'; then calls undeployByWorkflowId(String)")
  @Tag("MaintainedByDiffblue")
  void testDeleteWithId_thenCallsUndeployByWorkflowId2() {
    // Arrange
    doThrow(new NotFoundException("An error occurred"))
        .when(workflowEngine)
        .undeployByWorkflowId(Mockito.<String>any());
    doNothing().when(versionedWorkflowRepository).deleteByWorkflowId(Mockito.<String>any());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42"));
    verify(workflowEngine).undeployByWorkflowId("42");
    verify(versionedWorkflowRepository).deleteByWorkflowId("42");
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion2() throws JsonProcessingException, ModelValidationException {
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

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
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

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion3() throws JsonProcessingException, ModelValidationException {
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

    // Act
    workflowManagementService.setActiveVersion("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion4() throws JsonProcessingException, ModelValidationException {
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
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow2);

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenThrow(new NotFoundException("An error occurred"));
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

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion5() {
    // Arrange
    CamundaEngine workflowEngine = mock(CamundaEngine.class);
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

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(emptyResult);
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

    // Act
    workflowManagementService.setActiveVersion("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion6() {
    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(
            mock(CamundaEngine.class), versionRepository, mock(ObjectConverter.class));

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion7() {
    // Arrange
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
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowEngine#deploy(Workflow)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long); then calls deploy(Workflow)")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion_thenCallsDeploy() {
    // Arrange
    when(workflowEngine.deploy(Mockito.<Workflow>any()))
        .thenThrow(new NotFoundException("An error occurred"));

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
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenReturn(workflow);

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion_thenThrowIllegalArgumentException() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(false);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long); then throw UnsupportedOperationException")
  @Tag("MaintainedByDiffblue")
  void testSetActiveVersion_thenThrowUnsupportedOperationException() {
    // Arrange
    CamundaEngine workflowEngine = mock(CamundaEngine.class);
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
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow2);

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenThrow(new UnsupportedOperationException());
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

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionRepository).findByWorkflowIdAndVersion("42", 1L);
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
  }
}
