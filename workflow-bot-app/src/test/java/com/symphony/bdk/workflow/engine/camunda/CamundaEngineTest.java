package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.datafeed.EventPayload;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.engine.ExecutionParameters;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentQuery;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.repository.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaEngineTest {

  // Test helper classes for EventPayload testing
  static class TestEventPayload implements EventPayload {
    private Long eventTimestamp;

    @Override
    public Long getEventTimestamp() {
      return eventTimestamp;
    }

    @Override
    public void setEventTimestamp(Long eventTimestamp) {
      this.eventTimestamp = eventTimestamp;
    }
  }

  static class TestEventPayloadSubclass extends TestEventPayload {
  }

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private CamundaBpmnBuilder bpmnBuilder;

  @Mock
  private RealTimeEventProcessor<RequestReceivedEvent> requestReceivedEventProcessor;

  @Mock
  private RealTimeEventProcessor<TestEventPayload> testEventPayloadProcessor;

  @Mock
  private AuditTrailLogAction auditTrailLogger;

  @Mock
  private ProcessDefinitionQuery processDefinitionQuery;

  @Mock
  private ProcessDefinition processDefinition;

  @Mock
  private Resource resource;

  @Mock
  private DeploymentQuery deploymentQuery;

  @Mock
  private Deployment deployment1;

  @Mock
  private Deployment deployment2;

  private CamundaEngine camundaEngine;

  @BeforeEach
  void setUp() {
    List<RealTimeEventProcessor<?>> processors = List.of(requestReceivedEventProcessor);
    when(requestReceivedEventProcessor.sourceType()).thenReturn((Class) RequestReceivedEvent.class);
    camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors, auditTrailLogger);
  }

  @Test
  void shouldCreateRealTimeEventWithCorrectParametersWhenExecuteCalled() throws Exception {
    // Arrange
    String workflowId = "test-workflow";
    String token = "test-token";
    Map<String, Object> arguments = new HashMap<>();
    arguments.put("key1", "value1");
    arguments.put("key2", 123);

    ExecutionParameters parameters = new ExecutionParameters(arguments, token);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(processDefinition));
    when(processDefinition.getName()).thenReturn(workflowId);
    when(processDefinition.getDeploymentId()).thenReturn("deployment-id");
    when(repositoryService.getDeploymentResources("deployment-id")).thenReturn(List.of(resource));
    when(resource.getName()).thenReturn(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY);
    when(resource.getBytes()).thenReturn(token.getBytes(StandardCharsets.UTF_8));

    ArgumentCaptor<RealTimeEvent<RequestReceivedEvent>> eventCaptor = ArgumentCaptor.forClass(RealTimeEvent.class);

    // Act
    camundaEngine.execute(workflowId, parameters);

    // Assert
    verify(requestReceivedEventProcessor).process(eventCaptor.capture());
    RealTimeEvent<RequestReceivedEvent> capturedEvent = eventCaptor.getValue();

    assertThat(capturedEvent).isNotNull();
    assertThat(capturedEvent.getInitiator()).isNull();

    RequestReceivedEvent requestEvent = capturedEvent.getSource();
    assertThat(requestEvent).isNotNull();
    assertThat(requestEvent.getArguments()).isEqualTo(arguments);
    assertThat(requestEvent.getToken()).isEqualTo(token);
    assertThat(requestEvent.getWorkflowId()).isEqualTo(workflowId);
  }

  @Test
  void shouldCreateRealTimeEventWithEmptyArgumentsWhenNoArgumentsProvided() throws Exception {
    // Arrange
    String workflowId = "test-workflow-2";
    String token = "token-2";
    Map<String, Object> emptyArguments = new HashMap<>();

    ExecutionParameters parameters = new ExecutionParameters(emptyArguments, token);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(processDefinition));
    when(processDefinition.getName()).thenReturn(workflowId);
    when(processDefinition.getDeploymentId()).thenReturn("deployment-id-2");
    when(repositoryService.getDeploymentResources("deployment-id-2")).thenReturn(List.of(resource));
    when(resource.getName()).thenReturn(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY);
    when(resource.getBytes()).thenReturn(token.getBytes(StandardCharsets.UTF_8));

    ArgumentCaptor<RealTimeEvent<RequestReceivedEvent>> eventCaptor = ArgumentCaptor.forClass(RealTimeEvent.class);

    // Act
    camundaEngine.execute(workflowId, parameters);

    // Assert
    verify(requestReceivedEventProcessor).process(eventCaptor.capture());
    RealTimeEvent<RequestReceivedEvent> capturedEvent = eventCaptor.getValue();

    RequestReceivedEvent requestEvent = capturedEvent.getSource();
    assertThat(requestEvent.getArguments()).isEmpty();
    assertThat(requestEvent.getToken()).isEqualTo(token);
    assertThat(requestEvent.getWorkflowId()).isEqualTo(workflowId);
  }

  @Test
  void shouldCreateRealTimeEventWithNullTokenWhenTokenIsNull() throws Exception {
    // Arrange
    String workflowId = "test-workflow-3";
    Map<String, Object> arguments = Map.of("arg", "value");

    ExecutionParameters parameters = new ExecutionParameters(arguments, null);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(processDefinition));
    when(processDefinition.getName()).thenReturn(workflowId);
    when(processDefinition.getDeploymentId()).thenReturn("deployment-id-3");
    when(repositoryService.getDeploymentResources("deployment-id-3")).thenReturn(List.of());

    ArgumentCaptor<RealTimeEvent<RequestReceivedEvent>> eventCaptor = ArgumentCaptor.forClass(RealTimeEvent.class);

    // Act
    camundaEngine.execute(workflowId, parameters);

    // Assert
    verify(requestReceivedEventProcessor).process(eventCaptor.capture());
    RealTimeEvent<RequestReceivedEvent> capturedEvent = eventCaptor.getValue();

    RequestReceivedEvent requestEvent = capturedEvent.getSource();
    assertThat(requestEvent.getArguments()).isEqualTo(arguments);
    assertThat(requestEvent.getToken()).isNull();
    assertThat(requestEvent.getWorkflowId()).isEqualTo(workflowId);
  }

  @Test
  void shouldProcessEventUsingSourceClassWhenEventSourceIsNotEventPayload() throws Exception {
    // Arrange
    RequestReceivedEvent requestEvent = new RequestReceivedEvent();
    requestEvent.setWorkflowId("test-workflow");
    RealTimeEvent<RequestReceivedEvent> event = new RealTimeEvent<>(null, requestEvent);

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(requestReceivedEventProcessor).process(event);
  }

  @Test
  void shouldProcessEventUsingSuperclassWhenEventSourceIsEventPayload() throws Exception {
    // Arrange
    TestEventPayloadSubclass eventSource = new TestEventPayloadSubclass();
    RealTimeEvent<TestEventPayloadSubclass> event = new RealTimeEvent<>(null, eventSource);

    when(testEventPayloadProcessor.sourceType()).thenReturn((Class) TestEventPayload.class);

    List<RealTimeEventProcessor<?>> processors = List.of(requestReceivedEventProcessor, testEventPayloadProcessor);
    camundaEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors, auditTrailLogger);

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(testEventPayloadProcessor).process(any());
  }

  @Test
  void shouldCatchExceptionAndLogErrorWhenProcessorThrowsException() throws Exception {
    // Arrange
    RequestReceivedEvent requestEvent = new RequestReceivedEvent();
    requestEvent.setWorkflowId("test-workflow");
    RealTimeEvent<RequestReceivedEvent> event = new RealTimeEvent<>(null, requestEvent);

    doThrow(new RuntimeException("Test exception")).when(requestReceivedEventProcessor).process(any());

    // Act
    camundaEngine.onEvent(event);

    // Assert
    verify(requestReceivedEventProcessor).process(event);
  }

  @Test
  void shouldUndeployAllDeploymentsWhenUndeployAllCalled() {
    // Arrange
    String deploymentId1 = "deployment-1";
    String deploymentId2 = "deployment-2";
    String deploymentName1 = "workflow-1";
    String deploymentName2 = "workflow-2";

    when(deployment1.getId()).thenReturn(deploymentId1);
    when(deployment1.getName()).thenReturn(deploymentName1);
    when(deployment2.getId()).thenReturn(deploymentId2);
    when(deployment2.getName()).thenReturn(deploymentName2);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(List.of(deployment1, deployment2));

    // Act
    camundaEngine.undeployAll();

    // Assert
    verify(repositoryService).deleteDeployment(deploymentId1, true);
    verify(repositoryService).deleteDeployment(deploymentId2, true);
    verify(auditTrailLogger).undeployed(deployment1);
    verify(auditTrailLogger).undeployed(deployment2);
  }

  @Test
  void shouldUndeployDeploymentWhenUndeployByDeploymentIdCalled() {
    // Arrange
    String deploymentId = "test-deployment-id";
    String deploymentName = "test-workflow";

    when(deployment1.getId()).thenReturn(deploymentId);
    when(deployment1.getName()).thenReturn(deploymentName);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentId(deploymentId)).thenReturn(deploymentQuery);
    when(deploymentQuery.singleResult()).thenReturn(deployment1);

    // Act
    camundaEngine.undeployByDeploymentId(deploymentId);

    // Assert
    verify(repositoryService).deleteDeployment(deploymentId, true);
    verify(auditTrailLogger).undeployed(deployment1);
  }

  @Test
  void shouldUndeployMultipleDeploymentsWhenUndeployByWorkflowIdCalled() {
    // Arrange
    String workflowName = "test-workflow";
    String deploymentId1 = "deployment-1";
    String deploymentId2 = "deployment-2";

    when(deployment1.getId()).thenReturn(deploymentId1);
    when(deployment1.getName()).thenReturn(workflowName);
    when(deployment2.getId()).thenReturn(deploymentId2);
    when(deployment2.getName()).thenReturn(workflowName);

    when(repositoryService.createDeploymentQuery()).thenReturn(deploymentQuery);
    when(deploymentQuery.deploymentName(workflowName)).thenReturn(deploymentQuery);
    when(deploymentQuery.list()).thenReturn(List.of(deployment1, deployment2));

    // Act
    camundaEngine.undeployByWorkflowId(workflowName);

    // Assert
    verify(repositoryService).deleteDeployment(deploymentId1, true);
    verify(repositoryService).deleteDeployment(deploymentId2, true);
    verify(auditTrailLogger).undeployed(deployment1);
    verify(auditTrailLogger).undeployed(deployment2);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenWorkflowNotFound() {
    // Arrange
    String workflowId = "non-existent-workflow";
    ExecutionParameters parameters = new ExecutionParameters(new HashMap<>(), null);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of());

    // Act & Assert
    org.junit.jupiter.api.Assertions.assertThrows(
        com.symphony.bdk.workflow.exception.NotFoundException.class,
        () -> camundaEngine.execute(workflowId, parameters),
        "No workflow found with id " + workflowId
    );
  }

  @Test
  void shouldThrowNotFoundExceptionWhenWorkflowIdDoesNotMatch() {
    // Arrange
    String requestedWorkflowId = "requested-workflow";
    String existingWorkflowId = "existing-workflow";
    ExecutionParameters parameters = new ExecutionParameters(new HashMap<>(), null);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(processDefinition));
    when(processDefinition.getName()).thenReturn(existingWorkflowId);

    // Act & Assert
    org.junit.jupiter.api.Assertions.assertThrows(
        com.symphony.bdk.workflow.exception.NotFoundException.class,
        () -> camundaEngine.execute(requestedWorkflowId, parameters),
        "No workflow found with id " + requestedWorkflowId
    );
  }

  @Test
  void shouldThrowUnauthorizedExceptionWhenTokenMismatch() {
    // Arrange
    String workflowId = "test-workflow";
    String deployedToken = "deployed-token";
    String requestToken = "different-token";
    ExecutionParameters parameters = new ExecutionParameters(new HashMap<>(), requestToken);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(processDefinition));
    when(processDefinition.getName()).thenReturn(workflowId);
    when(processDefinition.getDeploymentId()).thenReturn("deployment-id");
    when(repositoryService.getDeploymentResources("deployment-id")).thenReturn(List.of(resource));
    when(resource.getName()).thenReturn(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY);
    when(resource.getBytes()).thenReturn(deployedToken.getBytes(StandardCharsets.UTF_8));

    // Act & Assert
    org.junit.jupiter.api.Assertions.assertThrows(
        com.symphony.bdk.workflow.exception.UnauthorizedException.class,
        () -> camundaEngine.execute(workflowId, parameters),
        "Request is not authorised"
    );
  }

  @Test
  void shouldThrowRuntimeExceptionWhenEventProcessingFails() throws Exception {
    // Arrange
    String workflowId = "test-workflow";
    String token = "test-token";
    ExecutionParameters parameters = new ExecutionParameters(new HashMap<>(), token);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(processDefinition));
    when(processDefinition.getName()).thenReturn(workflowId);
    when(processDefinition.getDeploymentId()).thenReturn("deployment-id");
    when(repositoryService.getDeploymentResources("deployment-id")).thenReturn(List.of(resource));
    when(resource.getName()).thenReturn(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY);
    when(resource.getBytes()).thenReturn(token.getBytes(StandardCharsets.UTF_8));

    doThrow(new IllegalArgumentException("Processing error")).when(requestReceivedEventProcessor).process(any());

    // Act & Assert
    org.junit.jupiter.api.Assertions.assertThrows(
        RuntimeException.class,
        () -> camundaEngine.execute(workflowId, parameters)
    );
  }

  @Test
  void shouldExecuteSuccessfullyWhenNoTokenResourceExists() throws Exception {
    // Arrange
    String workflowId = "test-workflow";
    ExecutionParameters parameters = new ExecutionParameters(Map.of("key", "value"), "any-token");

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(processDefinition));
    when(processDefinition.getName()).thenReturn(workflowId);
    when(processDefinition.getDeploymentId()).thenReturn("deployment-id");
    when(repositoryService.getDeploymentResources("deployment-id")).thenReturn(List.of());

    ArgumentCaptor<RealTimeEvent<RequestReceivedEvent>> eventCaptor = ArgumentCaptor.forClass(RealTimeEvent.class);

    // Act
    camundaEngine.execute(workflowId, parameters);

    // Assert
    verify(requestReceivedEventProcessor).process(eventCaptor.capture());
    RealTimeEvent<RequestReceivedEvent> capturedEvent = eventCaptor.getValue();
    assertThat(capturedEvent.getSource().getWorkflowId()).isEqualTo(workflowId);
  }

  @Test
  void shouldExecuteSuccessfullyWhenTokenResourceHasDifferentName() throws Exception {
    // Arrange
    String workflowId = "test-workflow";
    ExecutionParameters parameters = new ExecutionParameters(Map.of("key", "value"), "any-token");

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(processDefinition));
    when(processDefinition.getName()).thenReturn(workflowId);
    when(processDefinition.getDeploymentId()).thenReturn("deployment-id");
    when(repositoryService.getDeploymentResources("deployment-id")).thenReturn(List.of(resource));
    when(resource.getName()).thenReturn("some-other-resource.xml");

    ArgumentCaptor<RealTimeEvent<RequestReceivedEvent>> eventCaptor = ArgumentCaptor.forClass(RealTimeEvent.class);

    // Act
    camundaEngine.execute(workflowId, parameters);

    // Assert
    verify(requestReceivedEventProcessor).process(eventCaptor.capture());
    RealTimeEvent<RequestReceivedEvent> capturedEvent = eventCaptor.getValue();
    assertThat(capturedEvent.getSource().getWorkflowId()).isEqualTo(workflowId);
  }
}
