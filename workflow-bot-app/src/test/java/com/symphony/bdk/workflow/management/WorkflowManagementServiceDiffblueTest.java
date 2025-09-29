package com.symphony.bdk.workflow.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
  void testDeploy() {
    // Arrange
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
  void testDeploy2() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

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
    verify(versionedWorkflowRepository).findByWorkflowIdAndPublishedFalse("42");
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
   *   <li>Then calls {@link DefaultObjectConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test deploy(SwadlView); given DeploymentEntity getId() return '42'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
  void testDeploy_givenDeploymentEntityGetIdReturn42_thenCallsConvert()
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
   *   <li>Then calls {@link WorkflowEngine#translate(Workflow)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test deploy(SwadlView); given Properties (default constructor) Publish is 'false'; then calls translate(Workflow)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
  void testDeploy_givenPropertiesPublishIsFalse_thenCallsTranslate() {
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
    when(versionedWorkflowRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenReturn(emptyResult);

    Properties properties2 = new Properties();
    properties2.setPublish(false);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

    // Act
    workflowManagementService.deploy(
        SwadlView.builder()
            .createdBy(1L)
            .description("The characteristics of someone or something")
            .swadl("Swadl")
            .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndPublishedFalse("42");
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflowRepository} {@link
   *       VersionedWorkflowRepository#saveAndFlush(Object)} return {@link VersionedWorkflow}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test deploy(SwadlView); given VersionedWorkflowRepository saveAndFlush(Object) return VersionedWorkflow (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
  void testDeploy_givenVersionedWorkflowRepositorySaveAndFlushReturnVersionedWorkflow() {
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
    when(versionedWorkflowRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow3);
    when(versionedWorkflowRepository.save(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow);
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
    verify(versionedWorkflowRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
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
    when(versionedWorkflowRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

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
    verify(versionedWorkflowRepository).findByWorkflowIdAndPublishedFalse("42");
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate() {
    // Arrange
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate2() {
    // Arrange
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

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
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc("42");
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate3() {
    // Arrange
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenThrow(new NotFoundException("An error occurred"));

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
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

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
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc("42");
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate7() {
    // Arrange
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(emptyResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

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
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc("42");
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <ul>
   *   <li>Given {@link DeploymentEntity} {@link DeploymentEntity#getId()} return {@code 42}.
   *   <li>Then calls {@link DefaultObjectConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test update(SwadlView); given DeploymentEntity getId() return '42'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate_givenDeploymentEntityGetIdReturn42_thenCallsConvert()
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
   *   <li>Then calls {@link VersionedWorkflowRepository#save(Object)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test update(SwadlView); given Properties (default constructor) Publish is 'false'; then calls save(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate_givenPropertiesPublishIsFalse_thenCallsSave() {
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
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties2 = new Properties();
    properties2.setPublish(false);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

    // Act
    workflowManagementService.update(
        SwadlView.builder()
            .createdBy(1L)
            .description("The characteristics of someone or something")
            .swadl("Swadl")
            .build());

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc("42");
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflowRepository} {@link
   *       VersionedWorkflowRepository#saveAndFlush(Object)} return {@link VersionedWorkflow}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName(
      "Test update(SwadlView); given VersionedWorkflowRepository saveAndFlush(Object) return VersionedWorkflow (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate_givenVersionedWorkflowRepositorySaveAndFlushReturnVersionedWorkflow() {
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
    when(versionedWorkflowRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow4);
    when(versionedWorkflowRepository.save(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow2);
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow2);

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
    verify(versionedWorkflowRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
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
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

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
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc("42");
  }

  /**
   * Test {@link WorkflowManagementService#get(String)} with {@code id}.
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName("Test get(String) with 'id'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String)"})
  void testGetWithId() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.get("42"));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
  }

  /**
   * Test {@link WorkflowManagementService#get(String)} with {@code id}.
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName("Test get(String) with 'id'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String)"})
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String, Long)"})
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String, Long)"})
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String, Long)"})
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
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
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

    // Act
    Optional<VersionedWorkflowView> actualGetResult = workflowManagementService.get("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
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
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code true}.
   *   <li>Then return {@link Optional#get()} DeploymentId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName(
      "Test get(String) with 'id'; given VersionedWorkflow (default constructor) Active is 'true'; then return get() DeploymentId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String)"})
  void testGetWithId_givenVersionedWorkflowActiveIsTrue_thenReturnGetDeploymentIdIs42() {
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
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

    // Act
    Optional<VersionedWorkflowView> actualGetResult = workflowManagementService.get("42");

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
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
   * Test {@link WorkflowManagementService#getAllVersions(String)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  @DisplayName("Test getAllVersions(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List WorkflowManagementService.getAllVersions(String)"})
  void testGetAllVersions() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowId(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.getAllVersions("42"));
    verify(versionedWorkflowRepository).findByWorkflowId("42");
  }

  /**
   * Test {@link WorkflowManagementService#getAllVersions(String)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  @DisplayName("Test getAllVersions(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List WorkflowManagementService.getAllVersions(String)"})
  void testGetAllVersions2() {
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List WorkflowManagementService.getAllVersions(String)"})
  void testGetAllVersions_thenReturnEmpty() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<VersionedWorkflowView> actualAllVersions = workflowManagementService.getAllVersions("42");

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowId("42");
    assertTrue(actualAllVersions.isEmpty());
  }

  /**
   * Test {@link WorkflowManagementService#delete(String)} with {@code id}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'id'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String)"})
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
   * Test {@link WorkflowManagementService#delete(String)} with {@code id}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'id'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String)"})
  void testDeleteWithId2() {
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
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42", 1L));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion2() {
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion3() {
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
   * <p>Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion4() {
    // Arrange
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
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
    doNothing()
        .when(versionedWorkflowRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
    verify(versionedWorkflowRepository).deleteByWorkflowIdAndVersion("42", 1L);
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion_givenWorkflowEngineUndeployByDeploymentIdDoesNothing() {
    // Arrange
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
    doNothing()
        .when(versionedWorkflowRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
    verify(workflowEngine).undeployByDeploymentId("42");
    verify(versionedWorkflowRepository).deleteByWorkflowIdAndVersion("42", 1L);
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#delete(String)} with {@code id}.
   *
   * <ul>
   *   <li>Given {@link WorkflowEngine} {@link WorkflowEngine#undeployByWorkflowId(String)} does
   *       nothing.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  @DisplayName(
      "Test delete(String) with 'id'; given WorkflowEngine undeployByWorkflowId(String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.delete(String)"})
  void testDeleteWithId_givenWorkflowEngineUndeployByWorkflowIdDoesNothing() {
    // Arrange
    doNothing().when(workflowEngine).undeployByWorkflowId(Mockito.<String>any());
    doNothing().when(versionedWorkflowRepository).deleteByWorkflowId(Mockito.<String>any());

    // Act
    workflowManagementService.delete("42");

    // Assert
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion2() {
    // Arrange
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
    when(versionedWorkflowRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow4);
    when(versionedWorkflowRepository.save(Mockito.<VersionedWorkflow>any()))
        .thenReturn(versionedWorkflow2);
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult2);
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

    // Act
    workflowManagementService.setActiveVersion("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
    verify(versionedWorkflowRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   *
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion3() {
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
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
   * <p>Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion4() {
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

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion5() {
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
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow2);

    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.saveAndFlush(Mockito.<VersionedWorkflow>any()))
        .thenThrow(new NotFoundException("An error occurred"));
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

    WorkflowManagementService workflowManagementService =
        new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion6() {
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
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
}
