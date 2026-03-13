package com.symphony.bdk.workflow.engine;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.Gateway;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkflowDirectGraphBuilderTest {

  @Test
  void shouldRegisterTimeoutEventWhenNotAlreadyRegistered() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String parentId = "parent-activity";
    String timeoutEventId = "timeout-event-id";
    String timeoutValue = "PT5M";

    // Register parent node so it exists
    directGraph.registerToDictionary(parentId, new WorkflowNode().id(parentId).eventId(parentId));

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "registerTimeoutEvent",
        WorkflowDirectedGraph.class,
        String.class,
        String.class,
        String.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, directGraph, timeoutEventId, parentId, timeoutValue);

    // Assert
    assertThat(directGraph.isRegistered(timeoutEventId)).isTrue();

    WorkflowNode registeredNode = directGraph.readWorkflowNode(timeoutEventId);
    assertThat(registeredNode).isNotNull();
    assertThat(registeredNode.getId()).isEqualTo(timeoutEventId);
    assertThat(registeredNode.getEventId()).isEqualTo(timeoutEventId);
    assertThat(registeredNode.getElementType()).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(registeredNode.getWrappedType()).isEqualTo(ActivityExpiredEvent.class);
    assertThat(registeredNode.getEvent()).isNotNull();

    assertThat(directGraph.getParents(timeoutEventId)).contains(parentId);
    assertThat(directGraph.getChildren(parentId).getChildren()).contains(timeoutEventId);
    assertThat(directGraph.getChildren(parentId).getGateway()).isEqualTo(Gateway.EVENT_BASED);
  }

  @Test
  void shouldNotRegisterTimeoutEventWhenAlreadyRegistered() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String parentId = "parent-activity";
    String timeoutEventId = "timeout-event-id";
    String timeoutValue = "PT5M";

    // Register parent node
    directGraph.registerToDictionary(parentId, new WorkflowNode().id(parentId).eventId(parentId));

    // Pre-register the timeout event
    WorkflowNode existingNode = new WorkflowNode()
        .id(timeoutEventId)
        .eventId(timeoutEventId)
        .elementType(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    directGraph.registerToDictionary(timeoutEventId, existingNode);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "registerTimeoutEvent",
        WorkflowDirectedGraph.class,
        String.class,
        String.class,
        String.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, directGraph, timeoutEventId, parentId, timeoutValue);

    // Assert
    assertThat(directGraph.isRegistered(timeoutEventId)).isTrue();

    WorkflowNode registeredNode = directGraph.readWorkflowNode(timeoutEventId);
    assertThat(registeredNode).isNotNull();
    // The existing node should remain unchanged
    assertThat(registeredNode).isSameAs(existingNode);
    assertThat(registeredNode.getEvent()).isNull();

    // Parent relationship should not be added
    assertThat(directGraph.getParents(timeoutEventId)).isEmpty();
    assertThat(directGraph.getChildren(parentId).getChildren()).isEmpty();
  }

  @Test
  void shouldSetCorrectTimeoutValueInEventWhenRegistering() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String parentId = "parent-activity";
    String timeoutEventId = "timeout-event-id";
    String timeoutValue = "PT10M";

    // Register parent node
    directGraph.registerToDictionary(parentId, new WorkflowNode().id(parentId).eventId(parentId));

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "registerTimeoutEvent",
        WorkflowDirectedGraph.class,
        String.class,
        String.class,
        String.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, directGraph, timeoutEventId, parentId, timeoutValue);

    // Assert
    WorkflowNode registeredNode = directGraph.readWorkflowNode(timeoutEventId);
    assertThat(registeredNode.getEvent()).isInstanceOf(EventWithTimeout.class);
    EventWithTimeout event = (EventWithTimeout) registeredNode.getEvent();
    assertThat(event.getTimeout()).isEqualTo(timeoutValue);
    assertThat(event.getActivityExpired()).isNotNull();
  }

  @Test
  void shouldHandleExpiredActivityWhenParentIsNotExclusiveFormReply() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String expiredActivityId = "expired-activity";
    String parentActivityId = "parent-activity";
    String grandParentId = "grand-parent";
    String currentActivityId = "current-activity";

    // Register expired activity node
    directGraph.registerToDictionary(expiredActivityId,
        new WorkflowNode().id(expiredActivityId).eventId(expiredActivityId));

    // Register parent activity node with non-exclusive form reply event
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(false);
    EventWithTimeout parentEvent = new EventWithTimeout();
    parentEvent.setFormReplied(formRepliedEvent);
    parentEvent.setTimeout("PT5M");

    WorkflowNode parentNode = new WorkflowNode()
        .id(parentActivityId)
        .eventId(parentActivityId)
        .event(parentEvent)
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);
    directGraph.registerToDictionary(parentActivityId, parentNode);

    // Register grand parent node
    directGraph.registerToDictionary(grandParentId,
        new WorkflowNode().id(grandParentId).eventId(grandParentId));

    // Register current activity node
    directGraph.registerToDictionary(currentActivityId,
        new WorkflowNode().id(currentActivityId).eventId(currentActivityId));

    // Set up parent relationships
    directGraph.addParent(expiredActivityId, parentActivityId);
    directGraph.addParent(parentActivityId, grandParentId);

    // Create event with activity expired
    Event event = new Event();
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId(expiredActivityId);
    event.setActivityExpired(activityExpiredEvent);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeExpiredActivity",
        Event.class,
        String.class,
        WorkflowDirectedGraph.class
    );
    method.setAccessible(true);

    // Act
    String result = (String) method.invoke(builder, event, currentActivityId, directGraph);

    // Assert
    assertThat(result).isEqualTo(parentActivityId);
    WorkflowNode currentNode = directGraph.readWorkflowNode(currentActivityId);
    assertThat(currentNode.getElementType()).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(currentNode.getWrappedType()).isEqualTo(ActivityExpiredEvent.class);
  }

  @Test
  void shouldThrowNoStartingEventExceptionWhenFirstActivityHasNoEvents() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String activityId = "first-activity";

    // Create activity with no events
    com.symphony.bdk.workflow.swadl.v1.Activity activity = new com.symphony.bdk.workflow.swadl.v1.Activity();
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity baseActivity = new com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity() {};
    baseActivity.setId(activityId);

    // Use reflection to set private implementation field
    java.lang.reflect.Field implField = com.symphony.bdk.workflow.swadl.v1.Activity.class.getDeclaredField("implementation");
    implField.setAccessible(true);
    implField.set(activity, baseActivity);

    List<com.symphony.bdk.workflow.swadl.v1.Activity> activities = Collections.singletonList(activity);

    // Register activity node
    directGraph.registerToDictionary(activityId, new WorkflowNode().id(activityId).eventId(activityId));

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeStandaloneActivities",
        List.class,
        WorkflowDirectedGraph.class,
        int.class
    );
    method.setAccessible(true);

    // Act & Assert
    try {
      method.invoke(builder, activities, directGraph, 0);
      assertThat(false).as("Expected NoStartingEventException to be thrown").isTrue();
    } catch (Exception e) {
      assertThat(e.getCause()).isInstanceOf(com.symphony.bdk.workflow.swadl.exception.NoStartingEventException.class);
    }
  }

  @Test
  void shouldLinkActivityToPreviousActivityWhenActivityHasNoEvents() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String firstActivityId = "first-activity";
    String secondActivityId = "second-activity";

    // Create first activity with events
    com.symphony.bdk.workflow.swadl.v1.Activity firstActivity = new com.symphony.bdk.workflow.swadl.v1.Activity();
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity firstBaseActivity = new com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity() {};
    firstBaseActivity.setId(firstActivityId);
    firstBaseActivity.setOn(new EventWithTimeout());

    // Use reflection to set private implementation field
    java.lang.reflect.Field implField = com.symphony.bdk.workflow.swadl.v1.Activity.class.getDeclaredField("implementation");
    implField.setAccessible(true);
    implField.set(firstActivity, firstBaseActivity);

    // Create second activity with no events
    com.symphony.bdk.workflow.swadl.v1.Activity secondActivity = new com.symphony.bdk.workflow.swadl.v1.Activity();
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity secondBaseActivity = new com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity() {};
    secondBaseActivity.setId(secondActivityId);
    implField.set(secondActivity, secondBaseActivity);

    List<com.symphony.bdk.workflow.swadl.v1.Activity> activities = List.of(firstActivity, secondActivity);

    // Register activity nodes
    directGraph.registerToDictionary(firstActivityId, new WorkflowNode().id(firstActivityId).eventId(firstActivityId));
    directGraph.registerToDictionary(secondActivityId, new WorkflowNode().id(secondActivityId).eventId(secondActivityId));

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeStandaloneActivities",
        List.class,
        WorkflowDirectedGraph.class,
        int.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, activities, directGraph, 1);

    // Assert
    assertThat(directGraph.getChildren(firstActivityId).getChildren()).contains(secondActivityId);
    assertThat(directGraph.getParents(secondActivityId)).contains(firstActivityId);
  }

  @Test
  void shouldAddIfConditionWhenActivityHasNoEventsAndIfCondition() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String firstActivityId = "first-activity";
    String secondActivityId = "second-activity";
    String ifCondition = "outputs.var1 == true";

    // Create first activity with events
    com.symphony.bdk.workflow.swadl.v1.Activity firstActivity = new com.symphony.bdk.workflow.swadl.v1.Activity();
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity firstBaseActivity = new com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity() {};
    firstBaseActivity.setId(firstActivityId);
    firstBaseActivity.setOn(new EventWithTimeout());

    // Use reflection to set private implementation field
    java.lang.reflect.Field implField = com.symphony.bdk.workflow.swadl.v1.Activity.class.getDeclaredField("implementation");
    implField.setAccessible(true);
    implField.set(firstActivity, firstBaseActivity);

    // Create second activity with no events and an if condition
    com.symphony.bdk.workflow.swadl.v1.Activity secondActivity = new com.symphony.bdk.workflow.swadl.v1.Activity();
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity secondBaseActivity = new com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity() {};
    secondBaseActivity.setId(secondActivityId);
    secondBaseActivity.setIfCondition(ifCondition);
    implField.set(secondActivity, secondBaseActivity);

    List<com.symphony.bdk.workflow.swadl.v1.Activity> activities = List.of(firstActivity, secondActivity);

    // Register activity nodes
    directGraph.registerToDictionary(firstActivityId, new WorkflowNode().id(firstActivityId).eventId(firstActivityId));
    directGraph.registerToDictionary(secondActivityId, new WorkflowNode().id(secondActivityId).eventId(secondActivityId));

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeStandaloneActivities",
        List.class,
        WorkflowDirectedGraph.class,
        int.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, activities, directGraph, 1);

    // Assert
    assertThat(directGraph.getChildren(firstActivityId).getChildren()).contains(secondActivityId);
    assertThat(directGraph.getParents(secondActivityId)).contains(firstActivityId);
    WorkflowNode secondNode = directGraph.readWorkflowNode(secondActivityId);
    assertThat(secondNode.getIfConditions()).containsEntry(firstActivityId, ifCondition);
  }

  @Test
  void shouldThrowInvalidActivityExceptionWhenActivityHasNoEventsAndElseCondition() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String firstActivityId = "first-activity";
    String secondActivityId = "second-activity";

    // Create first activity with events
    com.symphony.bdk.workflow.swadl.v1.Activity firstActivity = new com.symphony.bdk.workflow.swadl.v1.Activity();
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity firstBaseActivity = new com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity() {};
    firstBaseActivity.setId(firstActivityId);
    firstBaseActivity.setOn(new EventWithTimeout());

    // Use reflection to set private implementation field
    java.lang.reflect.Field implField = com.symphony.bdk.workflow.swadl.v1.Activity.class.getDeclaredField("implementation");
    implField.setAccessible(true);
    implField.set(firstActivity, firstBaseActivity);

    // Create second activity with no events and an else condition
    com.symphony.bdk.workflow.swadl.v1.Activity secondActivity = new com.symphony.bdk.workflow.swadl.v1.Activity();
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity secondBaseActivity = new com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity() {};
    secondBaseActivity.setId(secondActivityId);
    secondBaseActivity.setElseCondition(new Object());
    implField.set(secondActivity, secondBaseActivity);

    List<com.symphony.bdk.workflow.swadl.v1.Activity> activities = List.of(firstActivity, secondActivity);

    // Register activity nodes
    directGraph.registerToDictionary(firstActivityId, new WorkflowNode().id(firstActivityId).eventId(firstActivityId));
    directGraph.registerToDictionary(secondActivityId, new WorkflowNode().id(secondActivityId).eventId(secondActivityId));

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeStandaloneActivities",
        List.class,
        WorkflowDirectedGraph.class,
        int.class
    );
    method.setAccessible(true);

    // Act & Assert
    try {
      method.invoke(builder, activities, directGraph, 1);
      assertThat(false).as("Expected InvalidActivityException to be thrown").isTrue();
    } catch (Exception e) {
      assertThat(e.getCause()).isInstanceOf(com.symphony.bdk.workflow.swadl.exception.InvalidActivityException.class);
    }
  }

  @Test
  void shouldHandleExpiredActivityWhenParentIsExclusiveFormReply() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String expiredActivityId = "expired-activity";
    String parentActivityId = "parent-activity";
    String grandParentId = "grand-parent";
    String currentActivityId = "current-activity";

    // Register expired activity node
    directGraph.registerToDictionary(expiredActivityId,
        new WorkflowNode().id(expiredActivityId).eventId(expiredActivityId));

    // Register parent activity node with exclusive form reply event
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    EventWithTimeout parentEvent = new EventWithTimeout();
    parentEvent.setFormReplied(formRepliedEvent);
    parentEvent.setTimeout("PT10M");

    WorkflowNode parentNode = new WorkflowNode()
        .id(parentActivityId)
        .eventId(parentActivityId)
        .event(parentEvent)
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);
    directGraph.registerToDictionary(parentActivityId, parentNode);

    // Register grand parent node
    directGraph.registerToDictionary(grandParentId,
        new WorkflowNode().id(grandParentId).eventId(grandParentId));

    // Register current activity node
    directGraph.registerToDictionary(currentActivityId,
        new WorkflowNode().id(currentActivityId).eventId(currentActivityId));

    // Set up parent relationships
    directGraph.addParent(expiredActivityId, parentActivityId);
    directGraph.addParent(parentActivityId, grandParentId);

    // Create event with activity expired
    Event event = new Event();
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId(expiredActivityId);
    event.setActivityExpired(activityExpiredEvent);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeExpiredActivity",
        Event.class,
        String.class,
        WorkflowDirectedGraph.class
    );
    method.setAccessible(true);

    // Act
    String result = (String) method.invoke(builder, event, currentActivityId, directGraph);

    // Assert
    String expectedTimeoutEventId = parentActivityId + "_timeout";
    assertThat(result).isEqualTo(expectedTimeoutEventId);

    // Verify the timeout event was registered
    assertThat(directGraph.isRegistered(expectedTimeoutEventId)).isTrue();
    WorkflowNode timeoutNode = directGraph.readWorkflowNode(expectedTimeoutEventId);
    assertThat(timeoutNode.getElementType()).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(timeoutNode.getWrappedType()).isEqualTo(ActivityExpiredEvent.class);

    // Verify timeout value was set correctly
    assertThat(timeoutNode.getEvent()).isInstanceOf(EventWithTimeout.class);
    EventWithTimeout timeoutEvent = (EventWithTimeout) timeoutNode.getEvent();
    assertThat(timeoutEvent.getTimeout()).isEqualTo("PT10M");
  }

  @Test
  void shouldHandleExpiredActivityWithNullTimeoutWhenParentIsExclusiveFormReply() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String expiredActivityId = "expired-activity";
    String parentActivityId = "parent-activity";
    String grandParentId = "grand-parent";
    String currentActivityId = "current-activity";

    // Register expired activity node
    directGraph.registerToDictionary(expiredActivityId,
        new WorkflowNode().id(expiredActivityId).eventId(expiredActivityId));

    // Register parent activity node with exclusive form reply event but null timeout
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    EventWithTimeout parentEvent = new EventWithTimeout();
    parentEvent.setFormReplied(formRepliedEvent);
    parentEvent.setTimeout(null);

    WorkflowNode parentNode = new WorkflowNode()
        .id(parentActivityId)
        .eventId(parentActivityId)
        .event(parentEvent)
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);
    directGraph.registerToDictionary(parentActivityId, parentNode);

    // Register grand parent node
    directGraph.registerToDictionary(grandParentId,
        new WorkflowNode().id(grandParentId).eventId(grandParentId));

    // Register current activity node
    directGraph.registerToDictionary(currentActivityId,
        new WorkflowNode().id(currentActivityId).eventId(currentActivityId));

    // Set up parent relationships
    directGraph.addParent(expiredActivityId, parentActivityId);
    directGraph.addParent(parentActivityId, grandParentId);

    // Create event with activity expired
    Event event = new Event();
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId(expiredActivityId);
    event.setActivityExpired(activityExpiredEvent);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeExpiredActivity",
        Event.class,
        String.class,
        WorkflowDirectedGraph.class
    );
    method.setAccessible(true);

    // Act
    String result = (String) method.invoke(builder, event, currentActivityId, directGraph);

    // Assert
    String expectedTimeoutEventId = parentActivityId + "_timeout";
    assertThat(result).isEqualTo(expectedTimeoutEventId);

    // Verify the timeout event was registered with default timeout
    assertThat(directGraph.isRegistered(expectedTimeoutEventId)).isTrue();
    WorkflowNode timeoutNode = directGraph.readWorkflowNode(expectedTimeoutEventId);

    // Verify timeout value defaults to PT24H
    assertThat(timeoutNode.getEvent()).isInstanceOf(EventWithTimeout.class);
    EventWithTimeout timeoutEvent = (EventWithTimeout) timeoutNode.getEvent();
    assertThat(timeoutEvent.getTimeout()).isEqualTo("PT24H");
  }

  @Test
  void shouldSetDefaultTimeoutForNonExclusiveFormReplyEventWithEmptyTimeout() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "form-reply_form-123";
    String activityId = "target-activity";
    String signalEventId = "signal-event-id";

    // Register and set up parent relationship for form-123 (required by validateExistingNodeId)
    directGraph.registerToDictionary("form-123",
        new WorkflowNode().id("form-123").eventId("form-123"));
    directGraph.addParent("form-123", "some-parent");

    // Create EventWithTimeout with FormRepliedEvent and empty timeout
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setFormId("form-123");
    formRepliedEvent.setExclusive(false);
    EventWithTimeout event = new EventWithTimeout();
    event.setFormReplied(formRepliedEvent);
    event.setTimeout("");

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeNoExclusiveFormReplyEvent",
        String.class,
        Event.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        String.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, eventNodeId, event, directGraph, signalEvent, activityId);

    // Assert
    assertThat(event.getTimeout()).isEqualTo("PT24H");
    assertThat(directGraph.isRegistered(signalEventId)).isTrue();
    WorkflowNode registeredNode = directGraph.readWorkflowNode(signalEventId);
    assertThat(registeredNode.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void shouldNotSetTimeoutForNonExclusiveFormReplyEventWithExistingTimeout() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "form-reply_form-456";
    String activityId = "target-activity";
    String signalEventId = "signal-event-id";

    // Register and set up parent relationship for form-456 (required by validateExistingNodeId)
    directGraph.registerToDictionary("form-456",
        new WorkflowNode().id("form-456").eventId("form-456"));
    directGraph.addParent("form-456", "some-parent");

    // Create EventWithTimeout with FormRepliedEvent and existing timeout
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setFormId("form-456");
    formRepliedEvent.setExclusive(false);
    EventWithTimeout event = new EventWithTimeout();
    event.setFormReplied(formRepliedEvent);
    event.setTimeout("PT10M");

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeNoExclusiveFormReplyEvent",
        String.class,
        Event.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        String.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, eventNodeId, event, directGraph, signalEvent, activityId);

    // Assert
    assertThat(event.getTimeout()).isEqualTo("PT10M");
    assertThat(directGraph.isRegistered(signalEventId)).isTrue();
    WorkflowNode registeredNode = directGraph.readWorkflowNode(signalEventId);
    assertThat(registeredNode.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void shouldHandleNonExclusiveFormReplyEventWithNullTimeout() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "form-reply_form-789";
    String activityId = "target-activity";
    String signalEventId = "signal-event-id";

    // Register and set up parent relationship for form-789 (required by validateExistingNodeId)
    directGraph.registerToDictionary("form-789",
        new WorkflowNode().id("form-789").eventId("form-789"));
    directGraph.addParent("form-789", "some-parent");

    // Create EventWithTimeout with FormRepliedEvent and null timeout
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setFormId("form-789");
    formRepliedEvent.setExclusive(false);
    EventWithTimeout event = new EventWithTimeout();
    event.setFormReplied(formRepliedEvent);
    event.setTimeout(null);

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeNoExclusiveFormReplyEvent",
        String.class,
        Event.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        String.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, eventNodeId, event, directGraph, signalEvent, activityId);

    // Assert
    assertThat(event.getTimeout()).isEqualTo("PT24H");
    assertThat(directGraph.isRegistered(signalEventId)).isTrue();
    WorkflowNode registeredNode = directGraph.readWorkflowNode(signalEventId);
    assertThat(registeredNode.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void shouldHandleNonExclusiveFormReplyEventWithNonEventWithTimeoutEvent() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "form-reply_form-999";
    String activityId = "target-activity";
    String signalEventId = "signal-event-id";

    // Register and set up parent relationship for form-999 (required by validateExistingNodeId)
    directGraph.registerToDictionary("form-999",
        new WorkflowNode().id("form-999").eventId("form-999"));
    directGraph.addParent("form-999", "some-parent");

    // Create a regular Event (not EventWithTimeout)
    Event event = new Event();
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setFormId("form-999");
    formRepliedEvent.setExclusive(false);
    event.setFormReplied(formRepliedEvent);

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeNoExclusiveFormReplyEvent",
        String.class,
        Event.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        String.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, eventNodeId, event, directGraph, signalEvent, activityId);

    // Assert
    assertThat(directGraph.isRegistered(signalEventId)).isTrue();
    WorkflowNode registeredNode = directGraph.readWorkflowNode(signalEventId);
    assertThat(registeredNode.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void shouldComputeActivityTimeoutForFormRepliedEventWithDefaultTimeout() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "form-reply_form-123";
    String signalEventId = "signal-event-123";
    String activityId = "activity-123";
    String parentId = "parent-activity";

    // Register required nodes
    directGraph.registerToDictionary("form-123",
        new WorkflowNode().id("form-123").eventId("form-123"));
    directGraph.addParent("form-123", "some-parent");
    directGraph.registerToDictionary(parentId,
        new WorkflowNode().id(parentId).eventId(parentId));
    directGraph.registerToDictionary(signalEventId,
        new WorkflowNode().id(signalEventId).eventId(eventNodeId));
    directGraph.addParent(signalEventId, parentId);

    // Create EventWithTimeout without timeout
    EventWithTimeout event = new EventWithTimeout();
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setFormId("form-123");
    formRepliedEvent.setExclusive(true);
    event.setFormReplied(formRepliedEvent);

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create BaseActivity mock with empty timeout
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity activity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    EventWithTimeout onEvent = new EventWithTimeout();
    onEvent.setTimeout("");
    when(activity.getOn()).thenReturn(onEvent);
    when(activity.getId()).thenReturn(activityId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeActivityTimeout",
        int.class,
        String.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        boolean.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 1, eventNodeId, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.FORM_REPLIED, false,
        directGraph, signalEvent, activity);

    // Assert
    assertThat(signalEvent.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    String timeoutEventId = signalEventId + "_timeout";
    assertThat(directGraph.isRegistered(timeoutEventId)).isTrue();
  }

  @Test
  void shouldComputeActivityTimeoutForFormRepliedEventWhenParallel() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "form-reply_form-456";
    String signalEventId = "signal-event-456";
    String activityId = "activity-456";
    String parentId = "parent-activity";

    // Register required nodes
    directGraph.registerToDictionary("form-456",
        new WorkflowNode().id("form-456").eventId("form-456"));
    directGraph.addParent("form-456", "some-parent");
    directGraph.registerToDictionary(parentId,
        new WorkflowNode().id(parentId).eventId(parentId));
    directGraph.registerToDictionary(signalEventId,
        new WorkflowNode().id(signalEventId).eventId(eventNodeId));
    directGraph.addParent(signalEventId, parentId);

    // Create EventWithTimeout without timeout
    EventWithTimeout event = new EventWithTimeout();
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setFormId("form-456");
    formRepliedEvent.setExclusive(true);
    event.setFormReplied(formRepliedEvent);

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create BaseActivity mock with empty timeout
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity activity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    EventWithTimeout onEvent = new EventWithTimeout();
    onEvent.setTimeout("");
    when(activity.getOn()).thenReturn(onEvent);
    when(activity.getId()).thenReturn(activityId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeActivityTimeout",
        int.class,
        String.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        boolean.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 1, eventNodeId, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.FORM_REPLIED, true,
        directGraph, signalEvent, activity);

    // Assert
    assertThat(signalEvent.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    String timeoutEventId = signalEventId + "_timeout";
    assertThat(directGraph.isRegistered(timeoutEventId)).isFalse();
  }

  @Test
  void shouldComputeActivityTimeoutForFormRepliedEventAtIndexZero() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "form-reply_form-789";
    String signalEventId = "signal-event-789";
    String activityId = "activity-789";

    // Create EventWithTimeout without timeout
    EventWithTimeout event = new EventWithTimeout();
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setFormId("form-789");
    formRepliedEvent.setExclusive(true);
    event.setFormReplied(formRepliedEvent);

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create BaseActivity mock with empty timeout
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity activity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    EventWithTimeout onEvent = new EventWithTimeout();
    onEvent.setTimeout("");
    when(activity.getOn()).thenReturn(onEvent);
    when(activity.getId()).thenReturn(activityId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeActivityTimeout",
        int.class,
        String.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        boolean.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 0, eventNodeId, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.FORM_REPLIED, false,
        directGraph, signalEvent, activity);

    // Assert
    assertThat(signalEvent.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void shouldComputeActivityTimeoutWithEventTimeout() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "message-received_hello";
    String signalEventId = "signal-event-msg";
    String activityId = "activity-msg";
    String parentId = "parent-activity";

    // Register required nodes
    directGraph.registerToDictionary(parentId,
        new WorkflowNode().id(parentId).eventId(parentId));
    directGraph.registerToDictionary(signalEventId,
        new WorkflowNode().id(signalEventId).eventId(eventNodeId));
    directGraph.addParent(signalEventId, parentId);

    // Create EventWithTimeout with timeout
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT15M");
    com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent messageEvent =
        new com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent();
    messageEvent.setContent("hello");
    event.setMessageReceived(messageEvent);

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create BaseActivity mock with no timeout
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity activity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    EventWithTimeout onEvent = new EventWithTimeout();
    onEvent.setTimeout(null);
    when(activity.getOn()).thenReturn(onEvent);
    when(activity.getId()).thenReturn(activityId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeActivityTimeout",
        int.class,
        String.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        boolean.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 1, eventNodeId, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.MESSAGE_RECEIVED, false,
        directGraph, signalEvent, activity);

    // Assert
    assertThat(signalEvent.getElementType()).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    String timeoutEventId = signalEventId + "_timeout";
    assertThat(directGraph.isRegistered(timeoutEventId)).isTrue();
    WorkflowNode timeoutNode = directGraph.readWorkflowNode(timeoutEventId);
    EventWithTimeout timeoutEvent = (EventWithTimeout) timeoutNode.getEvent();
    assertThat(timeoutEvent.getTimeout()).isEqualTo("PT15M");
  }

  @Test
  void shouldComputeActivityTimeoutWithActivityTimeout() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "message-received_world";
    String signalEventId = "signal-event-msg2";
    String activityId = "activity-msg2";
    String parentId = "parent-activity";

    // Register required nodes
    directGraph.registerToDictionary(parentId,
        new WorkflowNode().id(parentId).eventId(parentId));
    directGraph.registerToDictionary(signalEventId,
        new WorkflowNode().id(signalEventId).eventId(eventNodeId));
    directGraph.addParent(signalEventId, parentId);

    // Create Event without timeout (not EventWithTimeout)
    Event event = new Event();
    com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent messageEvent =
        new com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent();
    messageEvent.setContent("world");
    event.setMessageReceived(messageEvent);

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create BaseActivity mock with timeout
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity activity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    EventWithTimeout onEvent = new EventWithTimeout();
    onEvent.setTimeout("PT20M");
    when(activity.getOn()).thenReturn(onEvent);
    when(activity.getId()).thenReturn(activityId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeActivityTimeout",
        int.class,
        String.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        boolean.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 1, eventNodeId, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.MESSAGE_RECEIVED, false,
        directGraph, signalEvent, activity);

    // Assert
    assertThat(signalEvent.getElementType()).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    String timeoutEventId = signalEventId + "_timeout";
    assertThat(directGraph.isRegistered(timeoutEventId)).isTrue();
    WorkflowNode timeoutNode = directGraph.readWorkflowNode(timeoutEventId);
    EventWithTimeout timeoutEvent = (EventWithTimeout) timeoutNode.getEvent();
    assertThat(timeoutEvent.getTimeout()).isEqualTo("PT20M");
  }

  @Test
  void shouldComputeActivityTimeoutWithBothTimeoutsPreferActivity() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String eventNodeId = "message-received_both";
    String signalEventId = "signal-event-both";
    String activityId = "activity-both";
    String parentId = "parent-activity";

    // Register required nodes
    directGraph.registerToDictionary(parentId,
        new WorkflowNode().id(parentId).eventId(parentId));
    directGraph.registerToDictionary(signalEventId,
        new WorkflowNode().id(signalEventId).eventId(eventNodeId));
    directGraph.addParent(signalEventId, parentId);

    // Create EventWithTimeout with timeout
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT5M");
    com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent messageEvent =
        new com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent();
    messageEvent.setContent("both");
    event.setMessageReceived(messageEvent);

    // Create signal event node
    WorkflowNode signalEvent = new WorkflowNode()
        .id(signalEventId)
        .eventId(eventNodeId);

    // Create BaseActivity mock with timeout
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity activity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    EventWithTimeout onEvent = new EventWithTimeout();
    onEvent.setTimeout("PT30M");
    when(activity.getOn()).thenReturn(onEvent);
    when(activity.getId()).thenReturn(activityId);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeActivityTimeout",
        int.class,
        String.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        boolean.class,
        WorkflowDirectedGraph.class,
        WorkflowNode.class,
        com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 1, eventNodeId, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.MESSAGE_RECEIVED, false,
        directGraph, signalEvent, activity);

    // Assert
    assertThat(signalEvent.getElementType()).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    String timeoutEventId = signalEventId + "_timeout";
    assertThat(directGraph.isRegistered(timeoutEventId)).isTrue();
    WorkflowNode timeoutNode = directGraph.readWorkflowNode(timeoutEventId);
    EventWithTimeout timeoutEvent = (EventWithTimeout) timeoutNode.getEvent();
    assertThat(timeoutEvent.getTimeout()).isEqualTo("PT30M");
  }

  @Test
  void shouldHandleNonExclusiveFormRepliedEventInComputeSignal() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String formId = "test-form";
    String activityId = "test-activity";
    String eventNodeId = "form-reply_" + formId;

    // Register activity node
    directGraph.registerToDictionary(activityId,
        new WorkflowNode().id(activityId).eventId(activityId));

    // Register form node (required by validateExistingNodeId)
    directGraph.registerToDictionary(formId,
        new WorkflowNode().id(formId).eventId(formId));
    directGraph.addParent(formId, "some-parent");

    // Create FormRepliedEvent with non-exclusive
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setFormId(formId);
    formRepliedEvent.setExclusive(false);
    Event event = new Event();
    event.setFormReplied(formRepliedEvent);

    // Create Activity with BaseActivity
    com.symphony.bdk.workflow.swadl.v1.Activity activity = mock(com.symphony.bdk.workflow.swadl.v1.Activity.class);
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity baseActivity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    when(activity.getActivity()).thenReturn(baseActivity);
    when(baseActivity.getId()).thenReturn(activityId);

    List<com.symphony.bdk.workflow.swadl.v1.Activity> activities = Collections.singletonList(activity);

    // Create Triple
    Triple<String, String, Class<?>> triple = Triple.of(null, eventNodeId, FormRepliedEvent.class);

    // Create RelationalEvents
    com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents onEvents =
        new com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents(
            Collections.singletonList(event), false);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeSignal",
        int.class,
        List.class,
        Triple.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents.class,
        WorkflowDirectedGraph.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 0, activities, triple, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.FORM_REPLIED, onEvents, directGraph);

    // Assert
    assertThat(directGraph.isRegistered(eventNodeId)).isTrue();
    WorkflowNode registeredNode = directGraph.readWorkflowNode(eventNodeId);
    assertThat(registeredNode.getElementType()).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void shouldHandleTimeFiredEventInComputeSignal() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String activityId = "test-activity";
    String eventNodeId = "timerFired_date_2024-01-01";

    // Register activity node
    directGraph.registerToDictionary(activityId,
        new WorkflowNode().id(activityId).eventId(activityId));

    // Create TimerFiredEvent
    com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent timerFiredEvent =
        new com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent();
    timerFiredEvent.setAt("2024-01-01");
    Event event = new Event();
    event.setTimerFired(timerFiredEvent);

    // Create Activity with BaseActivity
    com.symphony.bdk.workflow.swadl.v1.Activity activity = mock(com.symphony.bdk.workflow.swadl.v1.Activity.class);
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity baseActivity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    when(activity.getActivity()).thenReturn(baseActivity);
    when(baseActivity.getId()).thenReturn(activityId);

    List<com.symphony.bdk.workflow.swadl.v1.Activity> activities = Collections.singletonList(activity);

    // Create Triple
    Triple<String, String, Class<?>> triple = Triple.of(null, eventNodeId,
        com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent.class);

    // Create RelationalEvents
    com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents onEvents =
        new com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents(
            Collections.singletonList(event), false);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeSignal",
        int.class,
        List.class,
        Triple.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents.class,
        WorkflowDirectedGraph.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 0, activities, triple, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.TIME_FIRED, onEvents, directGraph);

    // Assert
    assertThat(directGraph.isRegistered(eventNodeId)).isTrue();
    WorkflowNode registeredNode = directGraph.readWorkflowNode(eventNodeId);
    assertThat(registeredNode.getElementType()).isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
    assertThat(registeredNode.getEvent()).isEqualTo(event);
  }

  @Test
  void shouldHandleDefaultCaseInComputeSignal() throws Exception {
    // Arrange
    WorkflowDirectedGraph directGraph = new WorkflowDirectedGraph("test-workflow", 1L);
    String activityId = "test-activity";
    String eventNodeId = "message-received_test-message";
    String parentId = "parent-activity";

    // Register parent node
    directGraph.registerToDictionary(parentId,
        new WorkflowNode().id(parentId).eventId(parentId));

    // Register activity node
    directGraph.registerToDictionary(activityId,
        new WorkflowNode().id(activityId).eventId(activityId));

    // Set up parent relationship for eventNodeId
    directGraph.addParent(eventNodeId, parentId);

    // Create MessageReceivedEvent
    com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent messageReceivedEvent =
        new com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent();
    messageReceivedEvent.setContent("test-message");
    Event event = new Event();
    event.setMessageReceived(messageReceivedEvent);

    // Create Activity with BaseActivity
    com.symphony.bdk.workflow.swadl.v1.Activity activity = mock(com.symphony.bdk.workflow.swadl.v1.Activity.class);
    com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity baseActivity =
        mock(com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity.class);
    when(activity.getActivity()).thenReturn(baseActivity);
    when(baseActivity.getId()).thenReturn(activityId);

    // Mock getOn() to return an EventWithTimeout with no timeout
    EventWithTimeout onEvent = new EventWithTimeout();
    onEvent.setTimeout(null);
    when(baseActivity.getOn()).thenReturn(onEvent);

    List<com.symphony.bdk.workflow.swadl.v1.Activity> activities = Collections.singletonList(activity);

    // Create Triple
    Triple<String, String, Class<?>> triple = Triple.of(null, eventNodeId,
        com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent.class);

    // Create RelationalEvents
    com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents onEvents =
        new com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents(
            Collections.singletonList(event), false);

    // Create builder instance with mocked dependencies
    Workflow workflow = mock(Workflow.class);
    when(workflow.getId()).thenReturn("test-workflow");
    SessionService sessionService = mock(SessionService.class);
    UserV2 user = mock(UserV2.class);
    when(sessionService.getSession()).thenReturn(user);
    when(user.getDisplayName()).thenReturn("test-user");

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(workflow, sessionService);

    // Use reflection to access private method
    Method method = WorkflowDirectGraphBuilder.class.getDeclaredMethod(
        "computeSignal",
        int.class,
        List.class,
        Triple.class,
        Event.class,
        com.symphony.bdk.workflow.event.WorkflowEventType.class,
        com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents.class,
        WorkflowDirectedGraph.class
    );
    method.setAccessible(true);

    // Act
    method.invoke(builder, 0, activities, triple, event,
        com.symphony.bdk.workflow.event.WorkflowEventType.MESSAGE_RECEIVED, onEvents, directGraph);

    // Assert
    assertThat(directGraph.isRegistered(eventNodeId)).isTrue();
    WorkflowNode registeredNode = directGraph.readWorkflowNode(eventNodeId);
    assertThat(registeredNode.getElementType()).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    assertThat(registeredNode.getEvent()).isEqualTo(event);
  }
}
