package com.symphony.bdk.workflow.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
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
import com.symphony.bdk.workflow.swadl.v1.Activity;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;

class WorkflowManagementServiceDiffblueTest {
  /**
   * Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  void testDeploy() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    when(versionRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowManagementService.deploy(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndPublishedFalse(eq("42"));
  }

  /**
   * Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  void testDeploy2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.deploy(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndPublishedFalse(eq("42"));
  }

  /**
   * Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  void testDeploy3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    when(versionRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("42");
    doNothing().when(workflow).setActivities(Mockito.<List<Activity>>any());
    doNothing().when(workflow).setId(Mockito.<String>any());
    doNothing().when(workflow).setProperties(Mockito.<Properties>any());
    doNothing().when(workflow).setVariables(Mockito.<Map<String, Object>>any());
    doNothing().when(workflow).setVersion(Mockito.<Long>any());
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowManagementService.deploy(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndPublishedFalse(eq("42"));
    verify(workflow).getId();
    verify(workflow).setActivities(isA(List.class));
    verify(workflow).setId(eq("42"));
    verify(workflow).setProperties(isA(Properties.class));
    verify(workflow).setVariables(isA(Map.class));
    verify(workflow).setVersion(eq(1L));
  }

  /**
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  void testUpdate() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> workflowManagementService.update(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
  }

  /**
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  void testUpdate2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.update(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
  }

  /**
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  void testUpdate3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any())).thenReturn(emptyResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.update(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
  }

  /**
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  void testUpdate4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getPublished()).thenReturn(true);
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
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("42");
    doNothing().when(workflow).setActivities(Mockito.<List<Activity>>any());
    doNothing().when(workflow).setId(Mockito.<String>any());
    doNothing().when(workflow).setProperties(Mockito.<Properties>any());
    doNothing().when(workflow).setVariables(Mockito.<Map<String, Object>>any());
    doNothing().when(workflow).setVersion(Mockito.<Long>any());
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> workflowManagementService.update(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
    verify(versionedWorkflow).getPublished();
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
    verify(workflow).getId();
    verify(workflow).setActivities(isA(List.class));
    verify(workflow).setId(eq("42"));
    verify(workflow).setProperties(isA(Properties.class));
    verify(workflow).setVariables(isA(Map.class));
    verify(workflow).setVersion(eq(1L));
  }

  /**
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  void testUpdate5() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("42");
    when(deploymentEntity.getName()).thenReturn("Name");
    Mockito.<Map<Class<?>, List>>when(deploymentEntity.getDeployedArtifacts()).thenReturn(new HashMap<>());
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any())).thenReturn(deploymentEntity);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getPublished()).thenReturn(false);
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
    when(versionRepository.saveAndFlush(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow4);
    when(versionRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(ofResult2);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow2 = mock(Workflow.class);
    when(workflow2.isToPublish()).thenReturn(true);
    when(workflow2.getVersion()).thenReturn(1L);
    when(workflow2.getActivities()).thenReturn(new ArrayList<>());
    when(workflow2.getId()).thenReturn("42");
    doNothing().when(workflow2).setActivities(Mockito.<List<Activity>>any());
    doNothing().when(workflow2).setId(Mockito.<String>any());
    doNothing().when(workflow2).setProperties(Mockito.<Properties>any());
    doNothing().when(workflow2).setVariables(Mockito.<Map<String, Object>>any());
    doNothing().when(workflow2).setVersion(Mockito.<Long>any());
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow2);
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act
    workflowManagementService.update(swadlView);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
    verify(versionedWorkflow).getPublished();
    verify(versionedWorkflow, atLeast(1)).setActive(eq(true));
    verify(versionedWorkflow).setCreatedBy(eq(1L));
    verify(versionedWorkflow, atLeast(1)).setDeploymentId(eq("42"));
    verify(versionedWorkflow, atLeast(1)).setDescription(eq("The characteristics of someone or something"));
    verify(versionedWorkflow).setEtag(eq(1L));
    verify(versionedWorkflow).setId(eq("42"));
    verify(versionedWorkflow, atLeast(1)).setPublished(eq(true));
    verify(versionedWorkflow, atLeast(1)).setSwadl(eq("Swadl"));
    verify(versionedWorkflow, atLeast(1)).setVersion(eq(1L));
    verify(versionedWorkflow).setWorkflowId(eq("42"));
    verify(workflow2).getActivities();
    verify(workflow2, atLeast(1)).getId();
    verify(workflow2).getVersion();
    verify(workflow2, atLeast(1)).isToPublish();
    verify(workflow2).setActivities(isA(List.class));
    verify(workflow2).setId(eq("42"));
    verify(workflow2).setProperties(isA(Properties.class));
    verify(workflow2).setVariables(isA(Map.class));
    verify(workflow2).setVersion(eq(1L));
    verify(deploymentEntity).getDeployedArtifacts();
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity, atLeast(1)).getName();
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  void testUpdate6() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("42");
    when(deploymentEntity.getName()).thenReturn("Name");
    Mockito.<Map<Class<?>, List>>when(deploymentEntity.getDeployedArtifacts()).thenReturn(new HashMap<>());
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any())).thenReturn(deploymentEntity);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getPublished()).thenReturn(false);
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
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(ofResult2);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow2 = mock(Workflow.class);
    when(workflow2.isToPublish()).thenReturn(true);
    when(workflow2.getVersion()).thenReturn(1L);
    when(workflow2.getActivities()).thenReturn(new ArrayList<>());
    when(workflow2.getId()).thenReturn("42");
    doNothing().when(workflow2).setActivities(Mockito.<List<Activity>>any());
    doNothing().when(workflow2).setId(Mockito.<String>any());
    doNothing().when(workflow2).setProperties(Mockito.<Properties>any());
    doNothing().when(workflow2).setVariables(Mockito.<Map<String, Object>>any());
    doNothing().when(workflow2).setVersion(Mockito.<Long>any());
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow2);
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.update(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
    verify(versionedWorkflow).getPublished();
    verify(versionedWorkflow).setActive(eq(true));
    verify(versionedWorkflow).setCreatedBy(eq(1L));
    verify(versionedWorkflow).setDeploymentId(eq("42"));
    verify(versionedWorkflow, atLeast(1)).setDescription(eq("The characteristics of someone or something"));
    verify(versionedWorkflow).setEtag(eq(1L));
    verify(versionedWorkflow).setId(eq("42"));
    verify(versionedWorkflow, atLeast(1)).setPublished(eq(true));
    verify(versionedWorkflow, atLeast(1)).setSwadl(eq("Swadl"));
    verify(versionedWorkflow, atLeast(1)).setVersion(eq(1L));
    verify(versionedWorkflow).setWorkflowId(eq("42"));
    verify(workflow2).getActivities();
    verify(workflow2, atLeast(1)).getId();
    verify(workflow2).getVersion();
    verify(workflow2, atLeast(1)).isToPublish();
    verify(workflow2).setActivities(isA(List.class));
    verify(workflow2).setId(eq("42"));
    verify(workflow2).setProperties(isA(Properties.class));
    verify(workflow2).setVariables(isA(Map.class));
    verify(workflow2).setVersion(eq(1L));
    verify(deploymentEntity).getDeployedArtifacts();
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity, atLeast(1)).getName();
    verify(versionRepository).saveAndFlush(isA(VersionedWorkflow.class));
  }

  /**
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  void testUpdate7() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("42");
    when(deploymentEntity.getName()).thenReturn("Name");
    Mockito.<Map<Class<?>, List>>when(deploymentEntity.getDeployedArtifacts()).thenReturn(new HashMap<>());
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any())).thenReturn(deploymentEntity);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getPublished()).thenReturn(false);
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
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(emptyResult);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow2 = mock(Workflow.class);
    when(workflow2.isToPublish()).thenReturn(true);
    when(workflow2.getVersion()).thenReturn(1L);
    when(workflow2.getActivities()).thenReturn(new ArrayList<>());
    when(workflow2.getId()).thenReturn("42");
    doNothing().when(workflow2).setActivities(Mockito.<List<Activity>>any());
    doNothing().when(workflow2).setId(Mockito.<String>any());
    doNothing().when(workflow2).setProperties(Mockito.<Properties>any());
    doNothing().when(workflow2).setVariables(Mockito.<Map<String, Object>>any());
    doNothing().when(workflow2).setVersion(Mockito.<Long>any());
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow2);
    WorkflowManagementService workflowManagementService = new WorkflowManagementService(workflowEngine,
        versionRepository, objectConverter);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act
    workflowManagementService.update(swadlView);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    verify(versionRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
    verify(versionedWorkflow).getPublished();
    verify(versionedWorkflow, atLeast(1)).setActive(eq(true));
    verify(versionedWorkflow).setCreatedBy(eq(1L));
    verify(versionedWorkflow, atLeast(1)).setDeploymentId(eq("42"));
    verify(versionedWorkflow, atLeast(1)).setDescription(eq("The characteristics of someone or something"));
    verify(versionedWorkflow).setEtag(eq(1L));
    verify(versionedWorkflow).setId(eq("42"));
    verify(versionedWorkflow, atLeast(1)).setPublished(eq(true));
    verify(versionedWorkflow, atLeast(1)).setSwadl(eq("Swadl"));
    verify(versionedWorkflow, atLeast(1)).setVersion(eq(1L));
    verify(versionedWorkflow).setWorkflowId(eq("42"));
    verify(workflow2).getActivities();
    verify(workflow2, atLeast(1)).getId();
    verify(workflow2).getVersion();
    verify(workflow2, atLeast(1)).isToPublish();
    verify(workflow2).setActivities(isA(List.class));
    verify(workflow2).setId(eq("42"));
    verify(workflow2).setProperties(isA(Properties.class));
    verify(workflow2).setVariables(isA(Map.class));
    verify(workflow2).setVersion(eq(1L));
    verify(deploymentEntity).getDeployedArtifacts();
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity, atLeast(1)).getName();
    verify(versionRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  void testGet() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(emptyResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act
    Optional<VersionedWorkflowView> actualGetResult = (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).get("42");

    // Assert
    verify(versionRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    assertFalse(actualGetResult.isPresent());
    assertSame(emptyResult, actualGetResult);
  }

  /**
   * Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  void testGet2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    when(versionRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(ofResult);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
        .thenReturn(buildResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    // Act
    Optional<VersionedWorkflowView> actualGetResult = (new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter)).get("42");

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndActiveTrue(eq("42"));
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
   * Method under test: {@link WorkflowManagementService#get(String, Long)}
   */
  @Test
  void testGet3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act
    Optional<VersionedWorkflowView> actualGetResult = (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).get("42", 1L);

    // Assert
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    assertFalse(actualGetResult.isPresent());
    assertSame(emptyResult, actualGetResult);
  }

  /**
   * Method under test: {@link WorkflowManagementService#get(String, Long)}
   */
  @Test
  void testGet4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(ofResult);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
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
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
        .thenReturn(buildResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    // Act
    Optional<VersionedWorkflowView> actualGetResult = (new WorkflowManagementService(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()), versionRepository,
        objectConverter)).get("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
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
   * Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  void testGetAllVersions() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act
    List<VersionedWorkflowView> actualAllVersions = (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).getAllVersions("42");

    // Assert
    verify(versionRepository).findByWorkflowId(eq("42"));
    assertTrue(actualAllVersions.isEmpty());
  }

  /**
   * Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  void testGetAllVersions2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    when(versionRepository.findByWorkflowId(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).getAllVersions("42"));
    verify(versionRepository).findByWorkflowId(eq("42"));
  }

  /**
   * Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  void testDelete() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CamundaEngine workflowEngine = mock(CamundaEngine.class);
    doNothing().when(workflowEngine).undeployByWorkflowId(Mockito.<String>any());
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    doNothing().when(versionRepository).deleteByWorkflowId(Mockito.<String>any());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act
    (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).delete("42");

    // Assert
    verify(workflowEngine).undeployByWorkflowId(eq("42"));
    verify(versionRepository).deleteByWorkflowId(eq("42"));
  }

  /**
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  void testDelete2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    doThrow(new NotFoundException("An error occurred")).when(versionRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(ofResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).delete("42", 1L));
    verify(versionRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }

  /**
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  void testDelete3() {
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

    // Act
    (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).delete("42", 1L);

    // Assert
    verify(workflowEngine).undeployByDeploymentId(eq("42"));
    verify(versionRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }

  /**
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  void testDelete4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getDeploymentId()).thenThrow(new NotFoundException("An error occurred"));
    when(versionedWorkflow.getActive()).thenReturn(true);
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

    // Act and Assert
    assertThrows(NotFoundException.class, () -> (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).delete("42", 1L));
    verify(versionRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflow).getActive();
    verify(versionedWorkflow).getDeploymentId();
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
  }

  /**
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  void testDelete5() {
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

    // Act
    (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).delete("42", 1L);

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
  }

  /**
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  void testDelete6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    new NotFoundException("An error occurred");
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine = mock(WorkflowEngine.class);
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act
    (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).delete("42", 1L);

    // Assert
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }

  /**
   * Method under test:
   * {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  void testSetActiveVersion() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getSwadl()).thenThrow(new NotFoundException("An error occurred"));
    when(versionedWorkflow.getPublished()).thenReturn(true);
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
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(ofResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).setActiveVersion("42", 1L));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflow).getPublished();
    verify(versionedWorkflow).getSwadl();
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
  }

  /**
   * Method under test:
   * {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  void testSetActiveVersion2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getPublished()).thenReturn(false);
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
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any())).thenReturn(ofResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).setActiveVersion("42", 1L));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflow).getPublished();
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
  }

  /**
   * Method under test:
   * {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  void testSetActiveVersion3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    new NotFoundException("An error occurred");
    VersionedWorkflowRepository versionRepository = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> (new WorkflowManagementService(workflowEngine, versionRepository,
        new DefaultObjectConverter(converters, optionalBiConverters))).setActiveVersion("42", 1L));
    verify(versionRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }
}
