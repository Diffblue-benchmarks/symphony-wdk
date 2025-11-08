package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.camunda.bpm.application.impl.EmbeddedProcessApplication;
import org.camunda.bpm.application.impl.EmbeddedProcessApplicationReferenceImpl;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.DeploymentQueryImpl;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Document;

@ExtendWith(MockitoExtension.class)
class CamundaEngineDiffblueTest {
  @Mock
  private AuditTrailLogAction auditTrailLogAction;

  @Mock
  private CamundaBpmnBuilder camundaBpmnBuilder;

  @InjectMocks
  private CamundaEngine camundaEngine;

  @Mock
  private List<RealTimeEventProcessor<?>> list;

  @Mock
  private RepositoryService repositoryService;

  /**
   * Test {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)} with {@code CamundaTranslatedWorkflowContext}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}
   */
  @Test
  @DisplayName("Test deploy(CamundaTranslatedWorkflowContext) with 'CamundaTranslatedWorkflowContext'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CamundaEngine.deploy(CamundaTranslatedWorkflowContext)"})
  void testDeployWithCamundaTranslatedWorkflowContext_thenReturnNull() {
    // Arrange
    when(camundaBpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(new DeploymentEntity());
    doNothing().when(auditTrailLogAction).deployed(Mockito.<Deployment>any());
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    // Act
    String actualDeployResult = camundaEngine
        .deploy(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));

    // Assert
    verify(camundaBpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(auditTrailLogAction).deployed(isA(Deployment.class));
    assertNull(actualDeployResult);
  }

  /**
   * Test {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)} with {@code CamundaTranslatedWorkflowContext}.
   * <ul>
   *   <li>Then throw {@link ModelValidationException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#deploy(CamundaTranslatedWorkflowContext)}
   */
  @Test
  @DisplayName("Test deploy(CamundaTranslatedWorkflowContext) with 'CamundaTranslatedWorkflowContext'; then throw ModelValidationException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CamundaEngine.deploy(CamundaTranslatedWorkflowContext)"})
  void testDeployWithCamundaTranslatedWorkflowContext_thenThrowModelValidationException() {
    // Arrange
    when(camundaBpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(new DeploymentEntity());
    doThrow(new ModelValidationException("An error occurred")).when(auditTrailLogAction)
        .deployed(Mockito.<Deployment>any());
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    // Act and Assert
    assertThrows(ModelValidationException.class,
        () -> camundaEngine.deploy(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class))))));
    verify(camundaBpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(auditTrailLogAction).deployed(isA(Deployment.class));
  }

  /**
   * Test {@link CamundaEngine#deploy(Workflow)} with {@code Workflow}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName("Test deploy(Workflow) with 'Workflow'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CamundaEngine.deploy(Workflow)"})
  void testDeployWithWorkflow_thenReturnNull() throws JsonProcessingException, ModelValidationException {
    // Arrange
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    when(camundaBpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));
    when(camundaBpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(new DeploymentEntity());
    doNothing().when(auditTrailLogAction).deployed(Mockito.<Deployment>any());

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
    verify(camundaBpmnBuilder).deployWorkflow(isA(CamundaTranslatedWorkflowContext.class));
    verify(camundaBpmnBuilder).translateWorkflow(isA(Workflow.class));
    verify(auditTrailLogAction).deployed(isA(Deployment.class));
    assertNull(actualDeployResult);
  }

  /**
   * Test {@link CamundaEngine#deploy(Workflow)} with {@code Workflow}.
   * <ul>
   *   <li>Then throw {@link ModelValidationException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#deploy(Workflow)}
   */
  @Test
  @DisplayName("Test deploy(Workflow) with 'Workflow'; then throw ModelValidationException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CamundaEngine.deploy(Workflow)"})
  void testDeployWithWorkflow_thenThrowModelValidationException()
      throws JsonProcessingException, ModelValidationException {
    // Arrange
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    when(camundaBpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenReturn(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));
    when(camundaBpmnBuilder.deployWorkflow(Mockito.<CamundaTranslatedWorkflowContext>any()))
        .thenReturn(new DeploymentEntity());
    doThrow(new ModelValidationException("An error occurred")).when(auditTrailLogAction)
        .deployed(Mockito.<Deployment>any());

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
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
   * <p>
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CamundaTranslatedWorkflowContext CamundaEngine.translate(Workflow)"})
  void testTranslate() throws JsonProcessingException, ModelValidationException {
    // Arrange
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    CamundaTranslatedWorkflowContext camundaTranslatedWorkflowContext = new CamundaTranslatedWorkflowContext(workflow,
        workflowDirectedGraph,
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class))));

    when(camundaBpmnBuilder.translateWorkflow(Mockito.<Workflow>any())).thenReturn(camundaTranslatedWorkflowContext);

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
    verify(camundaBpmnBuilder).translateWorkflow(isA(Workflow.class));
    assertSame(camundaTranslatedWorkflowContext, actualTranslateResult);
  }

  /**
   * Test {@link CamundaEngine#translate(Workflow)}.
   * <p>
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CamundaTranslatedWorkflowContext CamundaEngine.translate(Workflow)"})
  void testTranslate2() throws JsonProcessingException, ModelValidationException {
    // Arrange
    when(camundaBpmnBuilder.translateWorkflow(Mockito.<Workflow>any()))
        .thenThrow(new ModelValidationException("An error occurred"));

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
   * <p>
   * Method under test: {@link CamundaEngine#translate(Workflow)}
   */
  @Test
  @DisplayName("Test translate(Workflow)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CamundaTranslatedWorkflowContext CamundaEngine.translate(Workflow)"})
  void testTranslate3() throws JsonProcessingException, ModelValidationException {
    // Arrange
    when(camundaBpmnBuilder.translateWorkflow(Mockito.<Workflow>any())).thenThrow(new IllegalArgumentException("foo"));

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
   * Test {@link CamundaEngine#undeployByDeploymentId(String)}.
   * <ul>
   *   <li>Then calls {@link AuditTrailLogAction#undeployed(Deployment)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#undeployByDeploymentId(String)}
   */
  @Test
  @DisplayName("Test undeployByDeploymentId(String); then calls undeployed(Deployment)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngine.undeployByDeploymentId(String)"})
  void testUndeployByDeploymentId_thenCallsUndeployed() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    DeploymentEntity deployment = new DeploymentEntity();
    EmbeddedProcessApplicationReferenceImpl reference = new EmbeddedProcessApplicationReferenceImpl(
        new EmbeddedProcessApplication());
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(new ProcessApplicationDeploymentImpl(
        deployment, new DefaultProcessApplicationRegistration(reference, new HashSet<>(), "Process Enginen Name")));
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl(commandExecutor);
    doNothing().when(repositoryService).deleteDeployment(Mockito.<String>any(), anyBoolean());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);
    doNothing().when(auditTrailLogAction).undeployed(Mockito.<Deployment>any());

    // Act
    camundaEngine.undeployByDeploymentId("42");

    // Assert
    verify(auditTrailLogAction).undeployed(isA(Deployment.class));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment(isNull(), eq(true));
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link CamundaEngine#undeployByDeploymentId(String)}.
   * <ul>
   *   <li>Then throw {@link ModelValidationException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#undeployByDeploymentId(String)}
   */
  @Test
  @DisplayName("Test undeployByDeploymentId(String); then throw ModelValidationException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngine.undeployByDeploymentId(String)"})
  void testUndeployByDeploymentId_thenThrowModelValidationException() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    DeploymentEntity deployment = new DeploymentEntity();
    EmbeddedProcessApplicationReferenceImpl reference = new EmbeddedProcessApplicationReferenceImpl(
        new EmbeddedProcessApplication());
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(new ProcessApplicationDeploymentImpl(
        deployment, new DefaultProcessApplicationRegistration(reference, new HashSet<>(), "Process Enginen Name")));
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl(commandExecutor);
    doNothing().when(repositoryService).deleteDeployment(Mockito.<String>any(), anyBoolean());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);
    doThrow(new ModelValidationException("An error occurred")).when(auditTrailLogAction)
        .undeployed(Mockito.<Deployment>any());

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaEngine.undeployByDeploymentId("42"));
    verify(auditTrailLogAction).undeployed(isA(Deployment.class));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment(isNull(), eq(true));
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link CamundaEngine#undeployAll()}.
   * <ul>
   *   <li>Given {@link AuditTrailLogAction} {@link AuditTrailLogAction#undeployed(Deployment)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#undeployAll()}
   */
  @Test
  @DisplayName("Test undeployAll(); given AuditTrailLogAction undeployed(Deployment) does nothing")
  @Tag("MaintainedByDiffblue")
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
    verify(repositoryService).deleteDeployment(isNull(), eq(true));
    verify(deploymentQueryImpl).list();
  }

  /**
   * Test {@link CamundaEngine#undeployAll()}.
   * <ul>
   *   <li>Then calls {@link RepositoryService#createDeploymentQuery()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#undeployAll()}
   */
  @Test
  @DisplayName("Test undeployAll(); then calls createDeploymentQuery()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngine.undeployAll()"})
  void testUndeployAll_thenCallsCreateDeploymentQuery() {
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
   * Test {@link CamundaEngine#undeployAll()}.
   * <ul>
   *   <li>Then throw {@link ModelValidationException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#undeployAll()}
   */
  @Test
  @DisplayName("Test undeployAll(); then throw ModelValidationException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngine.undeployAll()"})
  void testUndeployAll_thenThrowModelValidationException() {
    // Arrange
    ArrayList<Deployment> deploymentList = new ArrayList<>();
    deploymentList.add(new DeploymentEntity());
    DeploymentQueryImpl deploymentQueryImpl = mock(DeploymentQueryImpl.class);
    when(deploymentQueryImpl.list()).thenReturn(deploymentList);
    doNothing().when(repositoryService).deleteDeployment(Mockito.<String>any(), anyBoolean());
    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQueryImpl);
    doThrow(new ModelValidationException("An error occurred")).when(auditTrailLogAction)
        .undeployed(Mockito.<Deployment>any());

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaEngine.undeployAll());
    verify(auditTrailLogAction).undeployed(isA(Deployment.class));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).deleteDeployment(isNull(), eq(true));
    verify(deploymentQueryImpl).list();
  }

  /**
   * Test {@link CamundaEngine#onEvent(RealTimeEvent)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link RealTimeEvent} {@link RealTimeEvent#getSource()} return {@code null}.</li>
   *   <li>Then calls {@link RealTimeEvent#getSource()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#onEvent(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onEvent(RealTimeEvent); given 'null'; when RealTimeEvent getSource() return 'null'; then calls getSource()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngine.onEvent(RealTimeEvent)"})
  void testOnEvent_givenNull_whenRealTimeEventGetSourceReturnNull_thenCallsGetSource() {
    // Arrange
    RealTimeEvent<Object> event = mock(RealTimeEvent.class);
    when(event.getSource()).thenReturn(null);

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(event).getSource();
  }

  /**
   * Test {@link CamundaEngine#onEvent(RealTimeEvent)}.
   * <ul>
   *   <li>Given {@code Source}.</li>
   *   <li>When {@link RealTimeEvent} {@link RealTimeEvent#getSource()} return {@code Source}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngine#onEvent(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onEvent(RealTimeEvent); given 'Source'; when RealTimeEvent getSource() return 'Source'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngine.onEvent(RealTimeEvent)"})
  void testOnEvent_givenSource_whenRealTimeEventGetSourceReturnSource() {
    // Arrange
    RealTimeEvent<Object> event = mock(RealTimeEvent.class);
    when(event.getSource()).thenReturn("Source");

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(event, atLeast(1)).getSource();
  }
}
