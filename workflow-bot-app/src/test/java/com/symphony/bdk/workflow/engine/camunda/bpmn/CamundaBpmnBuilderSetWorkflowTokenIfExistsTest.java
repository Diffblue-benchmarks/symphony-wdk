package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaBpmnBuilderSetWorkflowTokenIfExistsTest {

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private WorkflowNodeBpmnBuilderRegistry builderFactory;

  @Mock
  private SessionService sessionService;

  @Mock
  private WorkflowDirectedGraphService directedGraphService;

  @Mock
  private DeploymentBuilder deploymentBuilder;

  private CamundaBpmnBuilder camundaBpmnBuilder;

  @BeforeEach
  void setUp() {
    camundaBpmnBuilder = new CamundaBpmnBuilder(repositoryService, builderFactory, sessionService,
        directedGraphService);
  }

  @Test
  void shouldAddTokenWhenActivityHasRequestReceivedEventWithToken() throws Exception {
    // Given
    String expectedToken = "test-token-123";
    Workflow workflow = createWorkflowWithToken(expectedToken);

    // When
    DeploymentBuilder result = invokeSetWorkflowTokenIfExists(deploymentBuilder, workflow);

    // Then
    verify(deploymentBuilder).addString(eq(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY), eq(expectedToken));
    assertThat(result).isEqualTo(deploymentBuilder);
  }

  @Test
  void shouldNotAddTokenWhenActivityHasNoRequestReceivedEvent() throws Exception {
    // Given
    Workflow workflow = createWorkflowWithoutRequestReceived();

    // When
    DeploymentBuilder result = invokeSetWorkflowTokenIfExists(deploymentBuilder, workflow);

    // Then
    verify(deploymentBuilder, never()).addString(eq(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY),
        org.mockito.ArgumentMatchers.any());
    assertThat(result).isEqualTo(deploymentBuilder);
  }

  @Test
  void shouldNotAddTokenWhenRequestReceivedEventHasNullToken() throws Exception {
    // Given
    Workflow workflow = createWorkflowWithNullToken();

    // When
    DeploymentBuilder result = invokeSetWorkflowTokenIfExists(deploymentBuilder, workflow);

    // Then
    verify(deploymentBuilder, never()).addString(eq(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY),
        org.mockito.ArgumentMatchers.any());
    assertThat(result).isEqualTo(deploymentBuilder);
  }

  @Test
  void shouldHandleEmptyActivitiesList() throws Exception {
    // Given
    Workflow workflow = new Workflow();
    workflow.setActivities(Collections.emptyList());

    // When
    DeploymentBuilder result = invokeSetWorkflowTokenIfExists(deploymentBuilder, workflow);

    // Then
    verify(deploymentBuilder, never()).addString(eq(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY),
        org.mockito.ArgumentMatchers.any());
    assertThat(result).isEqualTo(deploymentBuilder);
  }

  @Test
  void shouldAddFirstTokenWhenMultipleActivitiesHaveTokens() throws Exception {
    // Given
    String firstToken = "first-token";
    String secondToken = "second-token";
    Workflow workflow = createWorkflowWithMultipleTokens(firstToken, secondToken);

    // When
    DeploymentBuilder result = invokeSetWorkflowTokenIfExists(deploymentBuilder, workflow);

    // Then
    verify(deploymentBuilder).addString(eq(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY), eq(firstToken));
    assertThat(result).isEqualTo(deploymentBuilder);
  }

  @Test
  void shouldAddTokenFromSecondActivityWhenFirstHasNoToken() throws Exception {
    // Given
    String expectedToken = "second-activity-token";
    Workflow workflow = createWorkflowWithTokenInSecondActivity(expectedToken);

    // When
    DeploymentBuilder result = invokeSetWorkflowTokenIfExists(deploymentBuilder, workflow);

    // Then
    verify(deploymentBuilder).addString(eq(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY), eq(expectedToken));
    assertThat(result).isEqualTo(deploymentBuilder);
  }

  @Test
  void shouldNotAddTokenWhenRequestReceivedEventIsNull() throws Exception {
    // Given
    Workflow workflow = createWorkflowWithNullRequestReceived();

    // When
    DeploymentBuilder result = invokeSetWorkflowTokenIfExists(deploymentBuilder, workflow);

    // Then
    verify(deploymentBuilder, never()).addString(eq(CamundaBpmnBuilder.DEPLOYMENT_RESOURCE_TOKEN_KEY),
        org.mockito.ArgumentMatchers.any());
    assertThat(result).isEqualTo(deploymentBuilder);
  }

  private Workflow createWorkflowWithToken(String token) {
    Workflow workflow = new Workflow();
    Activity activity = new Activity();
    BaseActivity baseActivity = mock(BaseActivity.class);

    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setToken(token);

    Event event = new Event();
    event.setRequestReceived(requestReceivedEvent);

    List<Event> events = Collections.singletonList(event);
    RelationalEvents relationalEvents = new RelationalEvents(events, false);

    when(baseActivity.getEvents()).thenReturn(relationalEvents);
    activity.setImplementation(baseActivity);

    workflow.setActivities(Collections.singletonList(activity));
    return workflow;
  }

  private Workflow createWorkflowWithoutRequestReceived() {
    Workflow workflow = new Workflow();
    Activity activity = new Activity();
    BaseActivity baseActivity = mock(BaseActivity.class);

    Event event = new Event();
    // No requestReceived set

    List<Event> events = Collections.singletonList(event);
    RelationalEvents relationalEvents = new RelationalEvents(events, false);

    when(baseActivity.getEvents()).thenReturn(relationalEvents);
    activity.setImplementation(baseActivity);

    workflow.setActivities(Collections.singletonList(activity));
    return workflow;
  }

  private Workflow createWorkflowWithNullToken() {
    Workflow workflow = new Workflow();
    Activity activity = new Activity();
    BaseActivity baseActivity = mock(BaseActivity.class);

    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setToken(null);

    Event event = new Event();
    event.setRequestReceived(requestReceivedEvent);

    List<Event> events = Collections.singletonList(event);
    RelationalEvents relationalEvents = new RelationalEvents(events, false);

    when(baseActivity.getEvents()).thenReturn(relationalEvents);
    activity.setImplementation(baseActivity);

    workflow.setActivities(Collections.singletonList(activity));
    return workflow;
  }

  private Workflow createWorkflowWithMultipleTokens(String firstToken, String secondToken) {
    Workflow workflow = new Workflow();

    Activity activity1 = new Activity();
    BaseActivity baseActivity1 = mock(BaseActivity.class);

    RequestReceivedEvent requestReceivedEvent1 = new RequestReceivedEvent();
    requestReceivedEvent1.setToken(firstToken);

    Event event1 = new Event();
    event1.setRequestReceived(requestReceivedEvent1);

    List<Event> events1 = Collections.singletonList(event1);
    RelationalEvents relationalEvents1 = new RelationalEvents(events1, false);

    when(baseActivity1.getEvents()).thenReturn(relationalEvents1);
    activity1.setImplementation(baseActivity1);

    Activity activity2 = new Activity();
    BaseActivity baseActivity2 = mock(BaseActivity.class);

    RequestReceivedEvent requestReceivedEvent2 = new RequestReceivedEvent();
    requestReceivedEvent2.setToken(secondToken);

    Event event2 = new Event();
    event2.setRequestReceived(requestReceivedEvent2);

    List<Event> events2 = Collections.singletonList(event2);
    RelationalEvents relationalEvents2 = new RelationalEvents(events2, false);

    when(baseActivity2.getEvents()).thenReturn(relationalEvents2);
    activity2.setImplementation(baseActivity2);

    workflow.setActivities(Arrays.asList(activity1, activity2));
    return workflow;
  }

  private Workflow createWorkflowWithTokenInSecondActivity(String token) {
    Workflow workflow = new Workflow();

    // First activity without token
    Activity activity1 = new Activity();
    BaseActivity baseActivity1 = mock(BaseActivity.class);
    Event event1 = new Event();
    List<Event> events1 = Collections.singletonList(event1);
    RelationalEvents relationalEvents1 = new RelationalEvents(events1, false);
    when(baseActivity1.getEvents()).thenReturn(relationalEvents1);
    activity1.setImplementation(baseActivity1);

    // Second activity with token
    Activity activity2 = new Activity();
    BaseActivity baseActivity2 = mock(BaseActivity.class);

    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setToken(token);

    Event event2 = new Event();
    event2.setRequestReceived(requestReceivedEvent);

    List<Event> events2 = Collections.singletonList(event2);
    RelationalEvents relationalEvents2 = new RelationalEvents(events2, false);

    when(baseActivity2.getEvents()).thenReturn(relationalEvents2);
    activity2.setImplementation(baseActivity2);

    workflow.setActivities(Arrays.asList(activity1, activity2));
    return workflow;
  }

  private Workflow createWorkflowWithNullRequestReceived() {
    Workflow workflow = new Workflow();
    Activity activity = new Activity();
    BaseActivity baseActivity = mock(BaseActivity.class);

    Event event = new Event();
    event.setRequestReceived(null);

    List<Event> events = Collections.singletonList(event);
    RelationalEvents relationalEvents = new RelationalEvents(events, false);

    when(baseActivity.getEvents()).thenReturn(relationalEvents);
    activity.setImplementation(baseActivity);

    workflow.setActivities(Collections.singletonList(activity));
    return workflow;
  }

  private DeploymentBuilder invokeSetWorkflowTokenIfExists(DeploymentBuilder deploymentBuilder, Workflow workflow)
      throws Exception {
    Method setWorkflowTokenIfExistsMethod = CamundaBpmnBuilder.class.getDeclaredMethod("setWorkflowTokenIfExists",
        DeploymentBuilder.class, Workflow.class);
    setWorkflowTokenIfExistsMethod.setAccessible(true);
    return (DeploymentBuilder) setWorkflowTokenIfExistsMethod.invoke(camundaBpmnBuilder, deploymentBuilder, workflow);
  }
}
