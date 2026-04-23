package com.symphony.bdk.workflow;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityCompletedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityFailedEvent;

import org.junit.jupiter.api.Test;

import java.util.Collections;

class WorkflowValidatorTest {

  // ---- validateFirstActivity ----

  @Test
  void shouldThrowWhenFirstActivityEventIsEventWithTimeoutAndTimeoutIsSet() {
    SendMessage activity = new SendMessage();
    activity.setId("act1");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT10S");

    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, "wf1"))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("must not have timeout");
  }

  @Test
  void shouldNotThrowWhenFirstActivityEventIsEventWithTimeoutButTimeoutIsEmpty() {
    SendMessage activity = new SendMessage();
    activity.setId("act1");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("");

    assertThatCode(() -> WorkflowValidator.validateFirstActivity(activity, event, "wf1"))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldNotThrowWhenFirstActivityEventIsNotEventWithTimeout() {
    SendMessage activity = new SendMessage();
    activity.setId("act1");
    Event event = new Event();

    assertThatCode(() -> WorkflowValidator.validateFirstActivity(activity, event, "wf1"))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowWhenFirstActivityHasActivityCompletedOnEvent() {
    SendMessage activity = new SendMessage();
    activity.setId("act1");
    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(new ActivityCompletedEvent());
    activity.setOn(on);
    Event event = new Event();

    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, "wf1"))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("must not be dependent on other activities");
  }

  @Test
  void shouldThrowWhenFirstActivityHasActivityFailedOnEvent() {
    SendMessage activity = new SendMessage();
    activity.setId("act1");
    EventWithTimeout on = new EventWithTimeout();
    on.setActivityFailed(new ActivityFailedEvent());
    activity.setOn(on);
    Event event = new Event();

    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, "wf1"))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("must not be dependent on other activities");
  }

  @Test
  void shouldThrowWhenFirstActivityHasActivityExpiredOnEvent() {
    SendMessage activity = new SendMessage();
    activity.setId("act1");
    EventWithTimeout on = new EventWithTimeout();
    on.setActivityExpired(new ActivityExpiredEvent());
    activity.setOn(on);
    Event event = new Event();

    assertThatThrownBy(() -> WorkflowValidator.validateFirstActivity(activity, event, "wf1"))
        .isInstanceOf(InvalidActivityException.class)
        .hasMessageContaining("must not be dependent on other activities");
  }

  @Test
  void shouldNotThrowWhenFirstActivityOnIsNull() {
    SendMessage activity = new SendMessage();
    activity.setId("act1");
    activity.setOn(null);
    Event event = new Event();

    assertThatCode(() -> WorkflowValidator.validateFirstActivity(activity, event, "wf1"))
        .doesNotThrowAnyException();
  }

  // ---- validateActivityCompletedNodeId ----

  @Test
  void shouldThrowWhenActivityIdNotFoundInWorkflow() {
    Workflow workflow = new Workflow();
    workflow.setId("wf1");

    SendMessage impl = new SendMessage();
    impl.setId("act1");
    Activity activity = new Activity();
    activity.setImplementation(impl);
    workflow.setActivities(Collections.singletonList(activity));

    assertThatThrownBy(() -> WorkflowValidator.validateActivityCompletedNodeId("unknownId", "refActivity", workflow))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("No activity found with id unknownId");
  }

  @Test
  void shouldNotThrowWhenActivityIdExistsInWorkflow() {
    Workflow workflow = new Workflow();
    workflow.setId("wf1");

    SendMessage impl = new SendMessage();
    impl.setId("act1");
    Activity activity = new Activity();
    activity.setImplementation(impl);
    workflow.setActivities(Collections.singletonList(activity));

    assertThatCode(() -> WorkflowValidator.validateActivityCompletedNodeId("act1", "refActivity", workflow))
        .doesNotThrowAnyException();
  }

  // ---- validateExistingNodeId ----

  @Test
  void shouldThrowWhenNodeIdNotInGraph() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");

    assertThatThrownBy(() -> WorkflowValidator.validateExistingNodeId("unknownNode", "refActivity", "wf1", graph))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("No activity found with id unknownNode");
  }

  @Test
  void shouldNotThrowWhenNodeIdIsInGraph() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");
    graph.addParent("knownNode", "parentNode");

    assertThatCode(() -> WorkflowValidator.validateExistingNodeId("knownNode", "refActivity", "wf1", graph))
        .doesNotThrowAnyException();
  }
}
