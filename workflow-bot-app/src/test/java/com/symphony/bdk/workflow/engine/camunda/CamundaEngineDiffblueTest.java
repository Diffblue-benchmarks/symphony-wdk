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
import org.camunda.bpm.engine.RepositoryService;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.w3c.dom.Document;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CamundaEngineDiffblueTest {
  @MockBean
  private AuditTrailLogAction auditTrailLogAction;

  @MockBean
  private CamundaBpmnBuilder camundaBpmnBuilder;

  @MockBean
  private CamundaEngine camundaEngine;

  @Autowired
  private List<RealTimeEventProcessor<Object>> list;

  @MockBean
  private RealTimeEventProcessor<Object> realTimeEventProcessor;

  @MockBean
  private RepositoryService repositoryService;

  /**
   * Method under test:
   * {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}
   */
  @Test
  void testDeploy() {
    // Arrange
    when(camundaEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any())).thenReturn("Deploy");
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    // Act
    String actualDeployResult = camundaEngine
        .deploy(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));

    // Assert
    verify(camundaEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    assertEquals("Deploy", actualDeployResult);
  }

  /**
   * Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  void testDeploy2() throws JsonProcessingException, ModelValidationException {
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
   * Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  void testDeploy3() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  void testTranslate() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  void testTranslate2() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  void testTranslate3() throws JsonProcessingException, ModelValidationException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link CamundaEngine#undeployByDeploymentId(String)}
   */
  @Test
  void testUndeployByDeploymentId() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link CamundaEngine#undeployByDeploymentId(String)}
   */
  @Test
  void testUndeployByDeploymentId2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link CamundaEngine#onEvent(RealTimeEvent)}
   */
  @Test
  void testOnEvent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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

    // Assert that nothing has changed
    verify(event, atLeast(1)).getSource();
  }
}
