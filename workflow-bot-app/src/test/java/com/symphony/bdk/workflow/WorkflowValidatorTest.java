package com.symphony.bdk.workflow;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityCompletedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityFailedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

class WorkflowValidatorTest {

  @Test
  void shouldThrowExceptionWhenEventWithTimeoutHasTimeout() {
    var activity = new TestActivity();
    activity.setId("activity-1");
    var event = new EventWithTimeout();
    event.setTimeout("10s");
    var workflowId = "workflow-1";

    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Workflow's starting activity activity-1 must not have timeout");
  }

  @Test
  void shouldThrowExceptionWhenActivityHasActivityCompletedEvent() {
    var activity = new TestActivity();
    activity.setId("activity-1");
    var eventWithTimeout = new EventWithTimeout();
    var activityCompletedEvent = new ActivityCompletedEvent();
    eventWithTimeout.setActivityCompleted(activityCompletedEvent);
    activity.setOn(eventWithTimeout);
    var event = new Event();
    var workflowId = "workflow-1";

    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Workflow's starting activity activity-1 must not be dependent on other activities");
  }

  @Test
  void shouldThrowExceptionWhenActivityHasActivityFailedEvent() {
    var activity = new TestActivity();
    activity.setId("activity-2");
    var eventWithTimeout = new EventWithTimeout();
    var activityFailedEvent = new ActivityFailedEvent();
    eventWithTimeout.setActivityFailed(activityFailedEvent);
    activity.setOn(eventWithTimeout);
    var event = new Event();
    var workflowId = "workflow-2";

    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Workflow's starting activity activity-2 must not be dependent on other activities");
  }

  @Test
  void shouldThrowExceptionWhenActivityHasActivityExpiredEvent() {
    var activity = new TestActivity();
    activity.setId("activity-3");
    var eventWithTimeout = new EventWithTimeout();
    var activityExpiredEvent = new ActivityExpiredEvent();
    eventWithTimeout.setActivityExpired(activityExpiredEvent);
    activity.setOn(eventWithTimeout);
    var event = new Event();
    var workflowId = "workflow-3";

    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("Workflow's starting activity activity-3 must not be dependent on other activities");
  }

  @Test
  void shouldNotThrowExceptionWhenFirstActivityIsValid() {
    var activity = new TestActivity();
    activity.setId("activity-4");
    var event = new Event();
    var workflowId = "workflow-4";

    assertThatCode(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldNotThrowExceptionWhenEventWithTimeoutHasEmptyTimeout() {
    var activity = new TestActivity();
    activity.setId("activity-5");
    var event = new EventWithTimeout();
    event.setTimeout("");
    var workflowId = "workflow-5";

    assertThatCode(() -> WorkflowValidator.validateFirstActivity(activity, event, workflowId))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenActivityNotFoundInWorkflow() {
    var currentNodeId = "non-existent-activity";
    var activityId = "activity-1";
    var workflow = new Workflow();
    workflow.setId("workflow-1");
    var activity1 = new Activity();
    var baseActivity1 = new TestActivity();
    baseActivity1.setId("existing-activity");
    activity1.setImplementation(baseActivity1);
    workflow.setActivities(List.of(activity1));

    assertThatThrownBy(() -> WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Invalid activity in the workflow workflow-1")
        .hasMessageContaining("No activity found with id non-existent-activity")
        .hasMessageContaining("referenced in activity-1");
  }

  @Test
  void shouldNotThrowExceptionWhenActivityFoundInWorkflow() {
    var currentNodeId = "existing-activity";
    var activityId = "activity-1";
    var workflow = new Workflow();
    workflow.setId("workflow-1");
    var activity1 = new Activity();
    var baseActivity1 = new TestActivity();
    baseActivity1.setId("existing-activity");
    activity1.setImplementation(baseActivity1);
    workflow.setActivities(List.of(activity1));

    assertThatCode(() -> WorkflowValidator.validateActivityCompletedNodeId(currentNodeId, activityId, workflow))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenNodeNotSeenBeforeInGraph() {
    var currentNodeId = "unseen-node";
    var activityId = "activity-1";
    var workflowId = "workflow-1";
    var graph = new WorkflowDirectedGraph(workflowId);

    assertThatThrownBy(() -> WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Invalid activity in the workflow workflow-1")
        .hasMessageContaining("No activity found with id unseen-node")
        .hasMessageContaining("referenced in activity-1");
  }

  @Test
  void shouldNotThrowExceptionWhenNodeSeenBeforeInGraph() {
    var currentNodeId = "seen-node";
    var activityId = "activity-1";
    var workflowId = "workflow-1";
    var graph = new WorkflowDirectedGraph(workflowId);
    graph.addParent(currentNodeId, "parent-node");

    assertThatCode(() -> WorkflowValidator.validateExistingNodeId(currentNodeId, activityId, workflowId, graph))
        .doesNotThrowAnyException();
  }

  static class TestActivity extends BaseActivity {
  }
}
