package com.symphony.bdk.workflow;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityCompletedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityFailedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

class WorkflowValidatorClaudeTest {

  // ==================== validateFirstActivity Tests ====================

  @Test
  void validateFirstActivity_withValidActivity_shouldNotThrowException() {
    // Given: A valid first activity with no timeout and no activity dependencies
    BaseActivity activity = createActivity("firstActivity");
    Event event = new Event();
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateFirstActivity(activity, event, workflowId);
  }

  @Test
  void validateFirstActivity_withEventWithTimeoutButNoTimeoutValue_shouldNotThrowException() {
    // Given: An EventWithTimeout without an actual timeout value
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout(null);
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateFirstActivity(activity, event, workflowId);
  }

  @Test
  void validateFirstActivity_withEventWithTimeoutButEmptyTimeout_shouldNotThrowException() {
    // Given: An EventWithTimeout with an empty string timeout
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("");
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateFirstActivity(activity, event, workflowId);
  }

  @Test
  void validateFirstActivity_withEventWithTimeoutHavingValue_shouldThrowInvalidActivityException() {
    // Given: An EventWithTimeout with a timeout value
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("5m");
    String workflowId = "testWorkflow";

    // When/Then: Should throw InvalidActivityException
    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Invalid activity in the workflow testWorkflow")
        .hasMessageContaining("Workflow's starting activity firstActivity must not have timeout");
  }

  @Test
  void validateFirstActivity_withActivityCompletedInOn_shouldThrowInvalidActivityException() {
    // Given: An activity with activityCompleted in its "on" section
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout on = new EventWithTimeout();
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("previousActivity");
    on.setActivityCompleted(activityCompletedEvent);
    activity.setOn(on);

    Event event = new Event();
    String workflowId = "testWorkflow";

    // When/Then: Should throw InvalidActivityException
    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Invalid activity in the workflow testWorkflow")
        .hasMessageContaining("Workflow's starting activity firstActivity must not be dependent on other activities");
  }

  @Test
  void validateFirstActivity_withActivityFailedInOn_shouldThrowInvalidActivityException() {
    // Given: An activity with activityFailed in its "on" section
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout on = new EventWithTimeout();
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("previousActivity");
    on.setActivityFailed(activityFailedEvent);
    activity.setOn(on);

    Event event = new Event();
    String workflowId = "testWorkflow";

    // When/Then: Should throw InvalidActivityException
    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Invalid activity in the workflow testWorkflow")
        .hasMessageContaining("Workflow's starting activity firstActivity must not be dependent on other activities");
  }

  @Test
  void validateFirstActivity_withActivityExpiredInOn_shouldThrowInvalidActivityException() {
    // Given: An activity with activityExpired in its "on" section
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout on = new EventWithTimeout();
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("previousActivity");
    on.setActivityExpired(activityExpiredEvent);
    activity.setOn(on);

    Event event = new Event();
    String workflowId = "testWorkflow";

    // When/Then: Should throw InvalidActivityException
    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Invalid activity in the workflow testWorkflow")
        .hasMessageContaining("Workflow's starting activity firstActivity must not be dependent on other activities");
  }

  @Test
  void validateFirstActivity_withMultipleActivityDependenciesInOn_shouldThrowInvalidActivityException() {
    // Given: An activity with multiple activity dependencies
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout on = new EventWithTimeout();
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("activity1");
    on.setActivityCompleted(activityCompletedEvent);
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("activity2");
    on.setActivityFailed(activityFailedEvent);
    activity.setOn(on);

    Event event = new Event();
    String workflowId = "testWorkflow";

    // When/Then: Should throw InvalidActivityException
    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Workflow's starting activity firstActivity must not be dependent on other activities");
  }

  @Test
  void validateFirstActivity_withActivityCompletedInOneOfList_shouldNotThrowException() {
    // Given: An activity with activityCompleted in oneOf list (which is acceptable)
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout on = new EventWithTimeout();
    EventWithTimeout oneOfEvent = new EventWithTimeout();
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("someActivity");
    oneOfEvent.setActivityCompleted(activityCompletedEvent);
    on.setOneOf(Arrays.asList(oneOfEvent));
    activity.setOn(on);

    Event event = oneOfEvent;
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown because the event comes from oneOf list
    WorkflowValidator.validateFirstActivity(activity, event, workflowId);
  }

  @Test
  void validateFirstActivity_withNullOn_shouldNotThrowException() {
    // Given: An activity with no "on" section
    BaseActivity activity = createActivity("firstActivity");
    activity.setOn(null);
    Event event = new Event();
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateFirstActivity(activity, event, workflowId);
  }

  @Test
  void validateFirstActivity_withBothTimeoutAndActivityDependency_shouldThrowInvalidActivityException() {
    // Given: An activity with both timeout and activity dependency (should fail on timeout first)
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("10m");

    EventWithTimeout on = new EventWithTimeout();
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("previousActivity");
    on.setActivityCompleted(activityCompletedEvent);
    activity.setOn(on);

    String workflowId = "testWorkflow";

    // When/Then: Should throw InvalidActivityException for timeout
    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Workflow's starting activity firstActivity must not have timeout");
  }

  // ==================== validateActivityCompletedNodeId Tests ====================

  @Test
  void validateActivityCompletedNodeId_withExistingActivity_shouldNotThrowException() {
    // Given: A workflow with an activity that exists
    Workflow workflow = createWorkflow("testWorkflow");
    Activity activity1 = createActivityWrapper("activity1");
    Activity activity2 = createActivityWrapper("activity2");
    workflow.setActivities(Arrays.asList(activity1, activity2));

    String currentNodeId = "activity1";
    String activityId = "referringActivity";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow);
  }

  @Test
  void validateActivityCompletedNodeId_withNonExistingActivity_shouldThrowNotFoundException() {
    // Given: A workflow without the referenced activity
    Workflow workflow = createWorkflow("testWorkflow");
    Activity activity1 = createActivityWrapper("activity1");
    Activity activity2 = createActivityWrapper("activity2");
    workflow.setActivities(Arrays.asList(activity1, activity2));

    String currentNodeId = "nonExistentActivity";
    String activityId = "referringActivity";

    // When/Then: Should throw NotFoundException
    assertThatThrownBy(() ->
        WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Invalid activity in the workflow testWorkflow")
        .hasMessageContaining("No activity found with id nonExistentActivity")
        .hasMessageContaining("referenced in referringActivity");
  }

  @Test
  void validateActivityCompletedNodeId_withEmptyActivitiesList_shouldThrowNotFoundException() {
    // Given: A workflow with no activities
    Workflow workflow = createWorkflow("testWorkflow");
    workflow.setActivities(Arrays.asList());

    String currentNodeId = "activity1";
    String activityId = "referringActivity";

    // When/Then: Should throw NotFoundException
    assertThatThrownBy(() ->
        WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("No activity found with id activity1");
  }

  @Test
  void validateActivityCompletedNodeId_withMultipleActivitiesAndExistingId_shouldNotThrowException() {
    // Given: A workflow with multiple activities including the target
    Workflow workflow = createWorkflow("testWorkflow");
    Activity activity1 = createActivityWrapper("activity1");
    Activity activity2 = createActivityWrapper("targetActivity");
    Activity activity3 = createActivityWrapper("activity3");
    workflow.setActivities(Arrays.asList(activity1, activity2, activity3));

    String currentNodeId = "targetActivity";
    String activityId = "referringActivity";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow);
  }

  @Test
  void validateActivityCompletedNodeId_withSpecialCharactersInIds_shouldWorkCorrectly() {
    // Given: A workflow with activities having special characters in IDs
    Workflow workflow = createWorkflow("test-workflow_123");
    Activity activity1 = createActivityWrapper("activity-1_test");
    Activity activity2 = createActivityWrapper("activity.2.test");
    workflow.setActivities(Arrays.asList(activity1, activity2));

    String currentNodeId = "activity-1_test";
    String activityId = "referring-activity_test";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow);
  }

  // ==================== validateExistingNodeId Tests ====================

  @Test
  void validateExistingNodeId_withExistingNode_shouldNotThrowException() {
    // Given: A graph with a registered node
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("testWorkflow");
    graph.addParent("existingNode", "parentNode");

    String currentNodeId = "existingNode";
    String activityId = "referringActivity";
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph);
  }

  @Test
  void validateExistingNodeId_withNonExistingNode_shouldThrowNotFoundException() {
    // Given: A graph without the referenced node
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("testWorkflow");
    graph.addParent("someNode", "parentNode");

    String currentNodeId = "nonExistentNode";
    String activityId = "referringActivity";
    String workflowId = "testWorkflow";

    // When/Then: Should throw NotFoundException
    assertThatThrownBy(() ->
        WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Invalid activity in the workflow testWorkflow")
        .hasMessageContaining("No activity found with id nonExistentNode")
        .hasMessageContaining("referenced in referringActivity");
  }

  @Test
  void validateExistingNodeId_withEmptyGraph_shouldThrowNotFoundException() {
    // Given: An empty graph with no nodes
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("testWorkflow");

    String currentNodeId = "someNode";
    String activityId = "referringActivity";
    String workflowId = "testWorkflow";

    // When/Then: Should throw NotFoundException
    assertThatThrownBy(() ->
        WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("No activity found with id someNode");
  }

  @Test
  void validateExistingNodeId_withMultipleNodesAndExistingId_shouldNotThrowException() {
    // Given: A graph with multiple nodes including the target
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("testWorkflow");
    graph.addParent("node1", "parent1");
    graph.addParent("targetNode", "parent2");
    graph.addParent("node3", "parent3");

    String currentNodeId = "targetNode";
    String activityId = "referringActivity";
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph);
  }

  @Test
  void validateExistingNodeId_withGraphHavingVersionAndExistingNode_shouldNotThrowException() {
    // Given: A graph with version and a registered node
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("testWorkflow", 1L);
    graph.addParent("existingNode", "parentNode");

    String currentNodeId = "existingNode";
    String activityId = "referringActivity";
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph);
  }

  @Test
  void validateExistingNodeId_withSpecialCharactersInIds_shouldWorkCorrectly() {
    // Given: A graph with nodes having special characters
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow_123");
    graph.addParent("node-1_test", "parent-node");
    graph.addParent("node.2.test", "parent.node");

    String currentNodeId = "node-1_test";
    String activityId = "referring-activity_test";
    String workflowId = "test-workflow_123";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph);
  }

  // ==================== Edge Cases and Integration Tests ====================

  @Test
  void validateFirstActivity_withMessageReceivedEvent_shouldNotThrowException() {
    // Given: A first activity triggered by a messageReceived event (common case)
    BaseActivity activity = createActivity("firstActivity");
    EventWithTimeout on = new EventWithTimeout();
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    on.setMessageReceived(messageReceivedEvent);
    activity.setOn(on);

    Event event = on;
    String workflowId = "testWorkflow";

    // When/Then: No exception should be thrown
    WorkflowValidator.validateFirstActivity(activity, event, workflowId);
  }

  @Test
  void validateActivityCompletedNodeId_withCaseSensitiveIds_shouldRespectCase() {
    // Given: A workflow with case-sensitive activity IDs
    Workflow workflow = createWorkflow("testWorkflow");
    Activity activity1 = createActivityWrapper("MyActivity");
    Activity activity2 = createActivityWrapper("myActivity");
    workflow.setActivities(Arrays.asList(activity1, activity2));

    String currentNodeId = "myactivity"; // Different case
    String activityId = "referringActivity";

    // When/Then: Should throw NotFoundException because case doesn't match
    assertThatThrownBy(() ->
        WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void validateExistingNodeId_errorMessageFormat_shouldContainAllRelevantInformation() {
    // Given: A scenario that will produce an error
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("myWorkflow");
    String currentNodeId = "missingNode";
    String activityId = "callingActivity";
    String workflowId = "myWorkflow";

    // When: Validating a non-existent node
    Throwable thrown = catchThrowable(() ->
        WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph));

    // Then: Error message should contain workflow ID, missing node ID, and referencing activity
    assertThat(thrown)
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("myWorkflow")
        .hasMessageContaining("missingNode")
        .hasMessageContaining("callingActivity");
  }

  @Test
  void validateActivityCompletedNodeId_errorMessageFormat_shouldContainAllRelevantInformation() {
    // Given: A scenario that will produce an error
    Workflow workflow = createWorkflow("myWorkflow");
    workflow.setActivities(Arrays.asList());
    String currentNodeId = "missingNode";
    String activityId = "callingActivity";

    // When: Validating a non-existent activity
    Throwable thrown = catchThrowable(() ->
        WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow));

    // Then: Error message should contain workflow ID, missing node ID, and referencing activity
    assertThat(thrown)
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("myWorkflow")
        .hasMessageContaining("missingNode")
        .hasMessageContaining("callingActivity");
  }

  @Test
  void validateFirstActivity_errorMessageForTimeout_shouldContainActivityIdAndWorkflowId() {
    // Given: A first activity with a timeout
    BaseActivity activity = createActivity("startActivity");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("1h");
    String workflowId = "myWorkflow";

    // When: Validating the activity
    Throwable thrown = catchThrowable(() ->
        WorkflowValidator.validateFirstActivity(activity, event, workflowId));

    // Then: Error message should contain workflow ID and activity ID
    assertThat(thrown)
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("myWorkflow")
        .hasMessageContaining("startActivity")
        .hasMessageContaining("must not have timeout");
  }

  @Test
  void validateFirstActivity_errorMessageForDependency_shouldContainActivityIdAndWorkflowId() {
    // Given: A first activity with an activity dependency
    BaseActivity activity = createActivity("startActivity");
    EventWithTimeout on = new EventWithTimeout();
    ActivityCompletedEvent completedEvent = new ActivityCompletedEvent();
    completedEvent.setActivityId("previousActivity");
    on.setActivityCompleted(completedEvent);
    activity.setOn(on);

    Event event = new Event();
    String workflowId = "myWorkflow";

    // When: Validating the activity
    Throwable thrown = catchThrowable(() ->
        WorkflowValidator.validateFirstActivity(activity, event, workflowId));

    // Then: Error message should contain workflow ID and activity ID
    assertThat(thrown)
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("myWorkflow")
        .hasMessageContaining("startActivity")
        .hasMessageContaining("must not be dependent on other activities");
  }

  // ==================== Helper Methods ====================

  private BaseActivity createActivity(String id) {
    SendMessage activity = new SendMessage();
    activity.setId(id);
    return activity;
  }

  private Activity createActivityWrapper(String id) {
    Activity wrapper = new Activity();
    BaseActivity baseActivity = createActivity(id);
    wrapper.setImplementation(baseActivity);
    return wrapper;
  }

  private Workflow createWorkflow(String id) {
    Workflow workflow = new Workflow();
    workflow.setId(id);
    return workflow;
  }
}
