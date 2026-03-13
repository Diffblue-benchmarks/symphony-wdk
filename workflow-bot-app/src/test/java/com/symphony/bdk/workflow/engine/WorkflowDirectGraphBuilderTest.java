package com.symphony.bdk.workflow.engine;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.Gateway;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

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
}
