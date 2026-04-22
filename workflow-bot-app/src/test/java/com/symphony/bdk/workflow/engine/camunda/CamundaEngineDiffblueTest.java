package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.SessionApi;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.engine.ExecutionParameters;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.exception.UnauthorizedException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.swadl.exception.UniqueIdViolationException;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.DeploymentQueryImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.repository.Resource;
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
class CamundaEngineDiffblueTest {
  @Mock private AuditTrailLogAction auditTrailLogAction;

  @Mock private CamundaBpmnBuilder camundaBpmnBuilder;

  @InjectMocks private CamundaEngine camundaEngine;

  @Mock private List<RealTimeEventProcessor<?>> list;

  @Mock private RepositoryService repositoryService;

  /**
   * Test {@link CamundaEngine#deploy(Workflow)} with {@code Workflow}.
   *
   * <p>Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName("Test deploy(Workflow) with 'Workflow'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CamundaEngine.deploy(Workflow)"})
  void testDeployWithWorkflow() throws JsonProcessingException, ModelValidationException {
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

    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(bpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(deploymentEntity);

    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    doNothing().when(auditTrailLogger).deployed(Mockito.<Deployment>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, new ArrayList<>(), auditTrailLogger);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
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
   *
   * <ul>
   *   <li>Given {@link Activity} (default constructor) Implementation is {@link Debug} (default
   *       constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName(
      "Test deploy(Workflow) with 'Workflow'; given Activity (default constructor) Implementation is Debug (default constructor); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CamundaEngine.deploy(Workflow)"})
  void testDeployWithWorkflow_givenActivityImplementationIsDebug_thenReturnNull()
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
    when(camundaBpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(camundaBpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(new DeploymentEntity());
    doNothing().when(auditTrailLogAction).deployed(Mockito.<Deployment>any());

    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    ArrayList<Activity> activities = new ArrayList<>();
    activities.add(activity);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(activities);
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act
    String actualDeployResult = camundaEngine.deploy(workflow2);

    // Assert
    verify(camundaBpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(camundaBpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(auditTrailLogAction).deployed(isA(Deployment.class));
    assertNull(actualDeployResult);
  }

  /**
   * Test {@link CamundaEngine#deploy(Workflow)} with {@code Workflow}.
   *
   * <ul>
   *   <li>Then calls {@link DeploymentEntity#getDeployedArtifacts()}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName("Test deploy(Workflow) with 'Workflow'; then calls getDeployedArtifacts()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CamundaEngine.deploy(Workflow)"})
  void testDeployWithWorkflow_thenCallsGetDeployedArtifacts()
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

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
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
   * Test {@link CamundaEngine#deploy(Workflow)} with {@code Workflow}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName("Test deploy(Workflow) with 'Workflow'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CamundaEngine.deploy(Workflow)"})
  void testDeployWithWorkflow_thenThrowIllegalArgumentException()
      throws JsonProcessingException, ModelValidationException {
    // Arrange
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenThrow(new ModelValidationException());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> camundaEngine.deploy(workflow));
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
  }

  /**
   * Test {@link CamundaEngine#deploy(Workflow)} with {@code Workflow}.
   *
   * <ul>
   *   <li>Then throw {@link ModelValidationException}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName("Test deploy(Workflow) with 'Workflow'; then throw ModelValidationException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CamundaEngine.deploy(Workflow)"})
  void testDeployWithWorkflow_thenThrowModelValidationException()
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
    when(camundaBpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(camundaTranslatedWorkflowContext);
    when(camundaBpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(new DeploymentEntity());
    doThrow(new ModelValidationException())
        .when(auditTrailLogAction)
        .deployed(Mockito.<Deployment>any());

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaEngine.deploy(workflow2));
    verify(camundaBpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(camundaBpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(auditTrailLogAction).deployed(isA(Deployment.class));
  }

  /**
   * Test {@link CamundaEngine#translate(Workflow)}.
   *
   * <p>Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CamundaTranslatedWorkflowContext CamundaEngine.translate(Workflow)"})
  void testTranslate() throws JsonProcessingException, ModelValidationException {
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

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
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
   *
   * <p>Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CamundaTranslatedWorkflowContext CamundaEngine.translate(Workflow)"})
  void testTranslate2() throws JsonProcessingException, ModelValidationException {
    // Arrange
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    when(bpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenThrow(new ModelValidationException());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

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
   * Test {@link CamundaEngine#translate(Workflow)}.
   *
   * <p>Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CamundaTranslatedWorkflowContext CamundaEngine.translate(Workflow)"})
  void testTranslate3() throws JsonProcessingException, ModelValidationException {
    // Arrange
    when(camundaBpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenThrow(new IllegalArgumentException());

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
    verify(camundaBpmnBuilder).translateWorkflow(isA(Workflow.class));
  }

  /**
   * Test {@link CamundaEngine#translate(Workflow)}.
   *
   * <ul>
   *   <li>Given {@link Activity} (default constructor) Implementation is {@link Debug} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName(
      "Test translate(Workflow); given Activity (default constructor) Implementation is Debug (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CamundaTranslatedWorkflowContext CamundaEngine.translate(Workflow)"})
  void testTranslate_givenActivityImplementationIsDebug()
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

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    ArrayList<Activity> activities = new ArrayList<>();
    activities.add(activity);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(activities);
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act
    CamundaTranslatedWorkflowContext actualTranslateResult = camundaEngine.translate(workflow2);

    // Assert
    verify(bpmnBuilder).translateWorkflow(isA(Workflow.class));
    assertSame(camundaTranslatedWorkflowContext, actualTranslateResult);
  }

  /**
   * Test {@link CamundaEngine#execute(String, ExecutionParameters)}.
   *
   * <ul>
   *   <li>Then throw {@link ModelValidationException}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#execute(String, ExecutionParameters)}
   */
  @Test
  @DisplayName("Test execute(String, ExecutionParameters); then throw ModelValidationException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.execute(String, ExecutionParameters)"})
  void testExecute_thenThrowModelValidationException() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ModelValidationException());

    // Act and Assert
    assertThrows(
        ModelValidationException.class,
        () -> camundaEngine.execute("42", new ExecutionParameters(new HashMap<>(), "ABC123")));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link CamundaEngine#undeployByWorkflowId(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ModelValidationException}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#undeployByWorkflowId(String)}
   */
  @Test
  @DisplayName("Test undeployByWorkflowId(String); then throw ModelValidationException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.undeployByWorkflowId(String)"})
  void testUndeployByWorkflowId_thenThrowModelValidationException() {
    // Arrange
    when(repositoryService.createDeploymentQuery()).thenThrow(new ModelValidationException());

    // Act and Assert
    assertThrows(
        ModelValidationException.class, () -> camundaEngine.undeployByWorkflowId("Workflow Name"));
    verify(repositoryService).createDeploymentQuery();
  }

  /**
   * Test {@link CamundaEngine#undeployByDeploymentId(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ModelValidationException}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#undeployByDeploymentId(String)}
   */
  @Test
  @DisplayName("Test undeployByDeploymentId(String); then throw ModelValidationException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.undeployByDeploymentId(String)"})
  void testUndeployByDeploymentId_thenThrowModelValidationException() {
    // Arrange
    when(repositoryService.createDeploymentQuery()).thenThrow(new ModelValidationException());

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaEngine.undeployByDeploymentId("42"));
    verify(repositoryService).createDeploymentQuery();
  }

  /**
   * Test {@link CamundaEngine#undeployAll()}.
   *
   * <p>Method under test: {@link CamundaEngine#undeployAll()}
   */
  @Test
  @DisplayName("Test undeployAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.undeployAll()"})
  void testUndeployAll() {
    // Arrange
    when(repositoryService.createDeploymentQuery()).thenThrow(new ModelValidationException());

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaEngine.undeployAll());
    verify(repositoryService).createDeploymentQuery();
  }

  /**
   * Test {@link CamundaEngine#undeployAll()}.
   *
   * <p>Method under test: {@link CamundaEngine#undeployAll()}
   */
  @Test
  @DisplayName("Test undeployAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.undeployAll()"})
  void testUndeployAll2() {
    // Arrange
    ArrayList<Deployment> deploymentList = new ArrayList<>();
    deploymentList.add(new DeploymentEntity());

    DeploymentQueryImpl deploymentQueryImpl = mock(DeploymentQueryImpl.class);
    when(deploymentQueryImpl.list()).thenReturn(deploymentList);
    doThrow(new ModelValidationException())
        .when(repositoryService)
        .deleteDeployment(Mockito.<String>any(), anyBoolean());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaEngine.undeployAll());
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment(null, true);
    verify(deploymentQueryImpl).list();
  }

  /**
   * Test {@link CamundaEngine#undeployAll()}.
   *
   * <ul>
   *   <li>Given {@link AuditTrailLogAction} {@link AuditTrailLogAction#undeployed(Deployment)} does
   *       nothing.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#undeployAll()}
   */
  @Test
  @DisplayName("Test undeployAll(); given AuditTrailLogAction undeployed(Deployment) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.undeployAll()"})
  void testUndeployAll_givenAuditTrailLogActionUndeployedDoesNothing() {
    // Arrange
    ArrayList<Deployment> deploymentList = new ArrayList<>();
    deploymentList.add(new DeploymentEntity());

    DeploymentQueryImpl deploymentQueryImpl = mock(DeploymentQueryImpl.class);
    when(deploymentQueryImpl.list()).thenReturn(deploymentList);
    doNothing().when(repositoryService).deleteDeployment(Mockito.<String>any(), anyBoolean());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);
    doNothing().when(auditTrailLogAction).undeployed(Mockito.<Deployment>any());

    // Act
    camundaEngine.undeployAll();

    // Assert
    verify(auditTrailLogAction).undeployed(isA(Deployment.class));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment(null, true);
    verify(deploymentQueryImpl).list();
  }

  /**
   * Test {@link CamundaEngine#undeployAll()}.
   *
   * <ul>
   *   <li>Given {@link AuditTrailLogAction} {@link AuditTrailLogAction#undeployed(Deployment)}
   *       throw {@link ModelValidationException#ModelValidationException()}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#undeployAll()}
   */
  @Test
  @DisplayName(
      "Test undeployAll(); given AuditTrailLogAction undeployed(Deployment) throw ModelValidationException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.undeployAll()"})
  void testUndeployAll_givenAuditTrailLogActionUndeployedThrowModelValidationException() {
    // Arrange
    ArrayList<Deployment> deploymentList = new ArrayList<>();
    deploymentList.add(new DeploymentEntity());

    DeploymentQueryImpl deploymentQueryImpl = mock(DeploymentQueryImpl.class);
    when(deploymentQueryImpl.list()).thenReturn(deploymentList);
    doNothing().when(repositoryService).deleteDeployment(Mockito.<String>any(), anyBoolean());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);
    doThrow(new ModelValidationException())
        .when(auditTrailLogAction)
        .undeployed(Mockito.<Deployment>any());

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaEngine.undeployAll());
    verify(auditTrailLogAction).undeployed(isA(Deployment.class));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment(null, true);
    verify(deploymentQueryImpl).list();
  }

  /**
   * Test {@link CamundaEngine#undeployAll()}.
   *
   * <ul>
   *   <li>Given {@link DeploymentQueryImpl} {@link DeploymentQueryImpl#list()} return {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link DeploymentQueryImpl#list()}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#undeployAll()}
   */
  @Test
  @DisplayName(
      "Test undeployAll(); given DeploymentQueryImpl list() return ArrayList(); then calls list()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.undeployAll()"})
  void testUndeployAll_givenDeploymentQueryImplListReturnArrayList_thenCallsList() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = mock(DeploymentQueryImpl.class);
    when(deploymentQueryImpl.list()).thenReturn(new ArrayList<>());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);

    // Act
    camundaEngine.undeployAll();

    // Assert
    verify(repositoryService).createDeploymentQuery();
    verify(deploymentQueryImpl).list();
  }

  /**
   * Test {@link CamundaEngine#onEvent(RealTimeEvent)}.
   *
   * <ul>
   *   <li>Given {@link ModelValidationException#ModelValidationException()}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#onEvent(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onEvent(RealTimeEvent); given ModelValidationException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.onEvent(RealTimeEvent)"})
  void testOnEvent_givenModelValidationException() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository,
            sessionService2,
            new DefaultObjectConverter(converters, optionalBiConverters));

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    RealTimeEvent<Object> event = mock(RealTimeEvent.class);
    when(event.getSource()).thenThrow(new ModelValidationException());

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(event).getSource();
  }

  /**
   * Test {@link CamundaEngine#onEvent(RealTimeEvent)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link RealTimeEvent} {@link RealTimeEvent#getSource()} return {@code null}.
   *   <li>Then calls {@link RealTimeEvent#getSource()}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#onEvent(RealTimeEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(RealTimeEvent); given 'null'; when RealTimeEvent getSource() return 'null'; then calls getSource()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.onEvent(RealTimeEvent)"})
  void testOnEvent_givenNull_whenRealTimeEventGetSourceReturnNull_thenCallsGetSource() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository,
            sessionService2,
            new DefaultObjectConverter(converters, optionalBiConverters));

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    RealTimeEvent<Object> event = mock(RealTimeEvent.class);
    when(event.getSource()).thenReturn(null);

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(event).getSource();
  }

  /**
   * Test {@link CamundaEngine#onEvent(RealTimeEvent)}.
   *
   * <ul>
   *   <li>Given {@code Source}.
   *   <li>When {@link RealTimeEvent} {@link RealTimeEvent#getSource()} return {@code Source}.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#onEvent(RealTimeEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(RealTimeEvent); given 'Source'; when RealTimeEvent getSource() return 'Source'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngine.onEvent(RealTimeEvent)"})
  void testOnEvent_givenSource_whenRealTimeEventGetSourceReturnSource() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository,
            sessionService2,
            new DefaultObjectConverter(converters, optionalBiConverters));

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine camundaEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    RealTimeEvent<Object> event = mock(RealTimeEvent.class);
    when(event.getSource()).thenReturn("Source");

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(event, atLeast(1)).getSource();
  }

  /**
   * Test {@link CamundaEngine#CamundaEngine(RepositoryService, CamundaBpmnBuilder, List, AuditTrailLogAction)}.
   *
   * <p>Method under test: constructor</p>
   */
  @Test
  @DisplayName("Test CamundaEngine constructor with processors; builds processor registry")
  void testConstructor_withProcessors() {
    // Arrange
    @SuppressWarnings("unchecked")
    RealTimeEventProcessor<RequestReceivedEvent> processor = mock(RealTimeEventProcessor.class);
    when(processor.sourceType()).thenReturn(RequestReceivedEvent.class);

    List<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    processors.add(processor);

    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();

    // Act
    CamundaEngine engine =
        new CamundaEngine(repositoryService, camundaBpmnBuilder, processors, auditTrailLogAction);

    // Assert
    assertNotNull(engine);
    verify(processor).sourceType();
  }

  /**
   * Test {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}.
   *
   * <p>Method under test: {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}</p>
   */
  @Test
  @DisplayName("Test deploy(CamundaTranslatedWorkflowContext); returns deployment id")
  void testDeployWithContext() {
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

    CamundaTranslatedWorkflowContext context =
        new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph, instance);

    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("deploy-1");
    when(deploymentEntity.getName()).thenReturn("workflow-1");
    when(camundaBpmnBuilder.deployWorkflow(any(CamundaTranslatedWorkflowContext.class)))
        .thenReturn(deploymentEntity);
    doNothing().when(auditTrailLogAction).deployed(any(Deployment.class));

    // Act
    String result = camundaEngine.deploy(context);

    // Assert
    verify(camundaBpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(auditTrailLogAction).deployed(isA(Deployment.class));
    assertEquals("deploy-1", result);
  }

  /**
   * Test {@link CamundaEngine#execute(String, ExecutionParameters)}.
   *
   * <ul>
   *   <li>Then throw {@link NotFoundException} when no matching workflow is found.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#execute(String, ExecutionParameters)}</p>
   */
  @Test
  @DisplayName("Test execute(String, ExecutionParameters); then throw NotFoundException when no workflow")
  void testExecute_thenThrowNotFoundException() {
    // Arrange
    ProcessDefinitionQuery query = mock(ProcessDefinitionQuery.class);
    when(query.active()).thenReturn(query);
    when(query.list()).thenReturn(new ArrayList<>());
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(query);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () -> camundaEngine.execute("nonexistent-workflow", new ExecutionParameters(new HashMap<>(), "")));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link CamundaEngine#execute(String, ExecutionParameters)}.
   *
   * <ul>
   *   <li>Then throw {@link UnauthorizedException} when token does not match.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#execute(String, ExecutionParameters)}</p>
   */
  @Test
  @DisplayName("Test execute(String, ExecutionParameters); then throw UnauthorizedException on token mismatch")
  void testExecute_thenThrowUnauthorizedException() {
    // Arrange
    ProcessDefinition processDefinition = mock(ProcessDefinition.class);
    when(processDefinition.getName()).thenReturn("myWorkflow");
    when(processDefinition.getDeploymentId()).thenReturn("dep-1");

    ProcessDefinitionQuery query = mock(ProcessDefinitionQuery.class);
    when(query.active()).thenReturn(query);
    when(query.list()).thenReturn(Collections.singletonList(processDefinition));
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(query);

    Resource tokenResource = mock(Resource.class);
    when(tokenResource.getName()).thenReturn(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY);
    when(tokenResource.getBytes()).thenReturn("secret-token".getBytes(StandardCharsets.UTF_8));
    when(repositoryService.getDeploymentResources("dep-1"))
        .thenReturn(Collections.singletonList(tokenResource));

    // Act and Assert
    assertThrows(
        UnauthorizedException.class,
        () -> camundaEngine.execute("myWorkflow", new ExecutionParameters(new HashMap<>(), "wrong-token")));
    verify(repositoryService).createProcessDefinitionQuery();
    verify(repositoryService).getDeploymentResources("dep-1");
  }

  /**
   * Test {@link CamundaEngine#execute(String, ExecutionParameters)}.
   *
   * <ul>
   *   <li>Given no token required; dispatches event to processor.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#execute(String, ExecutionParameters)}</p>
   */
  @Test
  @DisplayName("Test execute(String, ExecutionParameters); dispatches event when no token required")
  @SuppressWarnings("unchecked")
  void testExecute_noTokenRequired_dispatchesEvent() throws Exception {
    // Arrange
    RealTimeEventProcessor<RequestReceivedEvent> processor = mock(RealTimeEventProcessor.class);
    when(processor.sourceType()).thenReturn(RequestReceivedEvent.class);
    doNothing().when(processor).process(any());

    List<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    processors.add(processor);

    CamundaEngine engineWithProcessor =
        new CamundaEngine(repositoryService, camundaBpmnBuilder, processors, auditTrailLogAction);

    ProcessDefinition processDefinition = mock(ProcessDefinition.class);
    when(processDefinition.getName()).thenReturn("myWorkflow");
    when(processDefinition.getDeploymentId()).thenReturn("dep-1");

    ProcessDefinitionQuery query = mock(ProcessDefinitionQuery.class);
    when(query.active()).thenReturn(query);
    when(query.list()).thenReturn(Collections.singletonList(processDefinition));
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(query);
    when(repositoryService.getDeploymentResources("dep-1")).thenReturn(new ArrayList<>());

    // Act
    engineWithProcessor.execute("myWorkflow", new ExecutionParameters(new HashMap<>(), ""));

    // Assert
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processor).process(any(RealTimeEvent.class));
  }

  /**
   * Test {@link CamundaEngine#undeployByWorkflowId(String)}.
   *
   * <ul>
   *   <li>Then stops all matching deployments.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#undeployByWorkflowId(String)}</p>
   */
  @Test
  @DisplayName("Test undeployByWorkflowId(String); stops all matching deployments")
  void testUndeployByWorkflowId_success() {
    // Arrange
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("dep-1");
    when(deploymentEntity.getName()).thenReturn("myWorkflow");

    ArrayList<Deployment> deployments = new ArrayList<>();
    deployments.add(deploymentEntity);

    DeploymentQueryImpl deploymentQuery = mock(DeploymentQueryImpl.class);
    when(deploymentQuery.deploymentName(anyString())).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(deployments);
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    doNothing().when(repositoryService).deleteDeployment(anyString(), anyBoolean());
    doNothing().when(auditTrailLogAction).undeployed(any(Deployment.class));

    // Act
    camundaEngine.undeployByWorkflowId("myWorkflow");

    // Assert
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment("dep-1", true);
    verify(auditTrailLogAction).undeployed(isA(Deployment.class));
  }

  /**
   * Test {@link CamundaEngine#undeployByDeploymentId(String)}.
   *
   * <ul>
   *   <li>Then stops the deployment with the given id.
   * </ul>
   *
   * <p>Method under test: {@link CamundaEngine#undeployByDeploymentId(String)}</p>
   */
  @Test
  @DisplayName("Test undeployByDeploymentId(String); stops the deployment with given id")
  void testUndeployByDeploymentId_success() {
    // Arrange
    DeploymentEntity deploymentEntity = mock(DeploymentEntity.class);
    when(deploymentEntity.getId()).thenReturn("dep-1");
    when(deploymentEntity.getName()).thenReturn("myWorkflow");

    DeploymentQueryImpl deploymentQuery = mock(DeploymentQueryImpl.class);
    when(deploymentQuery.deploymentId(anyString())).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deploymentEntity);
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    doNothing().when(repositoryService).deleteDeployment(anyString(), anyBoolean());
    doNothing().when(auditTrailLogAction).undeployed(any(Deployment.class));

    // Act
    camundaEngine.undeployByDeploymentId("dep-1");

    // Assert
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment("dep-1", true);
    verify(auditTrailLogAction).undeployed(isA(Deployment.class));
  }

  /**
   * Test {@link CamundaEngine#translate(Workflow)} with duplicate activity IDs.
   *
   * <ul>
   *   <li>Then throw {@link UniqueIdViolationException}.
   * </ul>
   *
   * <p>Method under test: checkUniquenessOfActivitiesId via {@link CamundaEngine#translate(Workflow)}</p>
   */
  @Test
  @DisplayName("Test translate(Workflow); given duplicate activity IDs; then throw UniqueIdViolationException")
  void testTranslate_withDuplicateActivityIds_throwsUniqueIdViolationException() {
    // Arrange
    Debug debug1 = new Debug();
    debug1.setId("duplicate-id");
    Activity activity1 = new Activity();
    activity1.setImplementation(debug1);

    Debug debug2 = new Debug();
    debug2.setId("duplicate-id");
    Activity activity2 = new Activity();
    activity2.setImplementation(debug2);

    ArrayList<Activity> activities = new ArrayList<>();
    activities.add(activity1);
    activities.add(activity2);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(activities);
    workflow.setId("test-workflow");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act and Assert
    assertThrows(UniqueIdViolationException.class, () -> camundaEngine.translate(workflow));
  }
}
