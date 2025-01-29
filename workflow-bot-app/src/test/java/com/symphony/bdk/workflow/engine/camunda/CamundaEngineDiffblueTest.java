package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.SessionApi;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.camunda.bpm.application.impl.EmbeddedProcessApplication;
import org.camunda.bpm.application.impl.EmbeddedProcessApplicationReferenceImpl;
import org.camunda.bpm.engine.impl.DeploymentQueryImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.application.DefaultProcessApplicationRegistration;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessApplicationDeploymentImpl;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.camunda.bpm.model.xml.impl.ModelBuilderImpl;
import org.camunda.bpm.model.xml.impl.ModelImpl;
import org.camunda.bpm.model.xml.impl.instance.DomDocumentImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;

class CamundaEngineDiffblueTest {
  /**
   * Test {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)} with
   * {@code CamundaTranslatedWorkflowContext}.
   * <ul>
   *   <li>Then calls {@link AuditTrailLogAction#deployed(Deployment)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}
   */
  @Test
  @DisplayName("Test deploy(CamundaTranslatedWorkflowContext) with 'CamundaTranslatedWorkflowContext'; then calls deployed(Deployment)")
  void testDeployWithCamundaTranslatedWorkflowContext_thenCallsDeployed() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("42");
    when(deploymentEntity.getName()).thenReturn("Name");
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any())).thenReturn(deploymentEntity);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    doNothing().when(auditTrailLogger).deployed(Mockito.<Deployment>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, new ArrayList<>(),
        auditTrailLogger);
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    // Act
    String actualDeployResult = camundaEngine
        .deploy(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));

    // Assert
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(auditTrailLogger).deployed(isA(Deployment.class));
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity).getName();
    assertEquals("42", actualDeployResult);
  }

  /**
   * Test {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)} with
   * {@code CamundaTranslatedWorkflowContext}.
   * <ul>
   *   <li>Then calls {@link DeploymentEntity#getDeployedArtifacts()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}
   */
  @Test
  @DisplayName("Test deploy(CamundaTranslatedWorkflowContext) with 'CamundaTranslatedWorkflowContext'; then calls getDeployedArtifacts()")
  void testDeployWithCamundaTranslatedWorkflowContext_thenCallsGetDeployedArtifacts() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("42");
    when(deploymentEntity.getName()).thenReturn("Name");
    Mockito.<Map<Class<?>, List>>when(deploymentEntity.getDeployedArtifacts()).thenReturn(new HashMap<>());
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any())).thenReturn(deploymentEntity);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    // Act
    String actualDeployResult = camundaEngine
        .deploy(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));

    // Assert
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(deploymentEntity).getDeployedArtifacts();
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity, atLeast(1)).getName();
    assertEquals("42", actualDeployResult);
  }

  /**
   * Test {@link CamundaEngine#deploy(Workflow)} with {@code Workflow}.
   * <ul>
   *   <li>Then calls {@link AuditTrailLogAction#deployed(Deployment)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName("Test deploy(Workflow) with 'Workflow'; then calls deployed(Deployment)")
  void testDeployWithWorkflow_thenCallsDeployed() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("42");
    when(deploymentEntity.getName()).thenReturn("Name");
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any())).thenReturn(deploymentEntity);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    doNothing().when(auditTrailLogger).deployed(Mockito.<Deployment>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, new ArrayList<>(),
        auditTrailLogger);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act
    String actualDeployResult = camundaEngine.deploy(workflow2);

    // Assert
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(auditTrailLogger).deployed(isA(Deployment.class));
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity).getName();
    assertEquals("42", actualDeployResult);
  }

  /**
   * Test {@link CamundaEngine#deploy(Workflow)} with {@code Workflow}.
   * <ul>
   *   <li>Then calls {@link DeploymentEntity#getDeployedArtifacts()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName("Test deploy(Workflow) with 'Workflow'; then calls getDeployedArtifacts()")
  void testDeployWithWorkflow_thenCallsGetDeployedArtifacts() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act
    String actualDeployResult = camundaEngine.deploy(workflow2);

    // Assert
    verify(bpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(deploymentEntity).getDeployedArtifacts();
    verify(deploymentEntity, atLeast(1)).getId();
    verify(deploymentEntity, atLeast(1)).getName();
    assertEquals("42", actualDeployResult);
  }

  /**
   * Test {@link CamundaEngine#translate(Workflow)}.
   * <p>
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow)")
  void testTranslate() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    CamundaTranslatedWorkflowContext camundaTranslatedWorkflowContext = new CamundaTranslatedWorkflowContext(workflow,
        workflowDirectedGraph,
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class))));

    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any())).thenReturn(camundaTranslatedWorkflowContext);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act
    CamundaTranslatedWorkflowContext actualTranslateResult = camundaEngine.translate(workflow2);

    // Assert
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    assertSame(camundaTranslatedWorkflowContext, actualTranslateResult);
  }

  /**
   * Test {@link CamundaEngine#translate(Workflow)}.
   * <ul>
   *   <li>Given {@link Activity} (default constructor) Implementation is
   * {@link Debug} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow); given Activity (default constructor) Implementation is Debug (default constructor)")
  void testTranslate_givenActivityImplementationIsDebug() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    CamundaTranslatedWorkflowContext camundaTranslatedWorkflowContext = new CamundaTranslatedWorkflowContext(workflow,
        workflowDirectedGraph,
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class))));

    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any())).thenReturn(camundaTranslatedWorkflowContext);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    ArrayList<Activity> activities = new ArrayList<>();
    activities.add(activity);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(activities);
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act
    CamundaTranslatedWorkflowContext actualTranslateResult = camundaEngine.translate(workflow2);

    // Assert
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    assertSame(camundaTranslatedWorkflowContext, actualTranslateResult);
  }

  /**
   * Test {@link CamundaEngine#translate(Workflow)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow); then throw IllegalArgumentException")
  void testTranslate_thenThrowIllegalArgumentException() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenThrow(new ModelValidationException("An error occurred"));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> camundaEngine.translate(workflow));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
  }

  /**
   * Test {@link CamundaEngine#undeployByDeploymentId(String)}.
   * <ul>
   *   <li>Then calls {@link RepositoryServiceImpl#createDeploymentQuery()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#undeployByDeploymentId(String)}
   */
  @Test
  @DisplayName("Test undeployByDeploymentId(String); then calls createDeploymentQuery()")
  void testUndeployByDeploymentId_thenCallsCreateDeploymentQuery() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    DeploymentEntity deployment = new DeploymentEntity();
    EmbeddedProcessApplicationReferenceImpl reference = new EmbeddedProcessApplicationReferenceImpl(
        new EmbeddedProcessApplication());
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(new ProcessApplicationDeploymentImpl(
        deployment, new DefaultProcessApplicationRegistration(reference, new HashSet<>(), "Process Enginen Name")));
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl(commandExecutor);
    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    doNothing().when(repositoryService).deleteDeployment(Mockito.<String>any(), anyBoolean());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService = new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional
        .of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, sessionService2,
            new DefaultObjectConverter(converters, optionalBiConverters)));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    // Act
    (new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()))
        .undeployByDeploymentId("42");

    // Assert
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment(isNull(), eq(true));
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link CamundaEngine#undeployByDeploymentId(String)}.
   * <ul>
   *   <li>Then calls {@link AuditTrailLogAction#undeployed(Deployment)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#undeployByDeploymentId(String)}
   */
  @Test
  @DisplayName("Test undeployByDeploymentId(String); then calls undeployed(Deployment)")
  void testUndeployByDeploymentId_thenCallsUndeployed() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    DeploymentEntity deployment = new DeploymentEntity();
    EmbeddedProcessApplicationReferenceImpl reference = new EmbeddedProcessApplicationReferenceImpl(
        new EmbeddedProcessApplication());
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(new ProcessApplicationDeploymentImpl(
        deployment, new DefaultProcessApplicationRegistration(reference, new HashSet<>(), "Process Enginen Name")));
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl(commandExecutor);
    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    doNothing().when(repositoryService).deleteDeployment(Mockito.<String>any(), anyBoolean());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    doNothing().when(auditTrailLogger).undeployed(Mockito.<Deployment>any());
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService = new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional
        .of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, sessionService2,
            new DefaultObjectConverter(converters, optionalBiConverters)));

    // Act
    (new CamundaEngine(repositoryService, bpmnBuilder, new ArrayList<>(), auditTrailLogger))
        .undeployByDeploymentId("42");

    // Assert
    verify(auditTrailLogger).undeployed(isA(Deployment.class));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment(isNull(), eq(true));
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link CamundaEngine#onEvent(RealTimeEvent)}.
   * <ul>
   *   <li>Given {@code Source}.</li>
   *   <li>Then calls {@link RealTimeEvent#getSource()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#onEvent(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onEvent(RealTimeEvent); given 'Source'; then calls getSource()")
  void testOnEvent_givenSource_thenCallsGetSource() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService = new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional
        .of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, sessionService2,
            new DefaultObjectConverter(converters, optionalBiConverters)));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());
    RealTimeEvent<Object> event = mock(RealTimeEvent.class);
    when(event.getSource()).thenReturn("Source");

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(event, atLeast(1)).getSource();
  }
}
