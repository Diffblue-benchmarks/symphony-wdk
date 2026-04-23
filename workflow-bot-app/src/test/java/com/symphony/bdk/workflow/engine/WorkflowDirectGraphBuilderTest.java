package com.symphony.bdk.workflow.engine;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;
import com.symphony.bdk.workflow.swadl.exception.NoStartingEventException;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityCompletedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityFailedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowDirectGraphBuilderTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private SessionService sessionService;

  @BeforeEach
  void setUp() {
    when(sessionService.getSession().getDisplayName()).thenReturn("TestBot");
  }

  // --- helpers ---

  private Activity activityWithMessageReceived(String id, String content) {
    MessageReceivedEvent msgEvent = new MessageReceivedEvent();
    msgEvent.setContent(content);
    EventWithTimeout on = new EventWithTimeout();
    on.setMessageReceived(msgEvent);
    Debug debug = new Debug();
    debug.setId(id);
    debug.setOn(on);
    Activity activity = new Activity();
    activity.setImplementation(debug);
    return activity;
  }

  private Activity standaloneActivity(String id) {
    Debug debug = new Debug();
    debug.setId(id);
    Activity activity = new Activity();
    activity.setImplementation(debug);
    return activity;
  }

  private Workflow workflow(String id, Activity... activities) {
    Workflow wf = new Workflow();
    wf.setId(id);
    wf.setActivities(Arrays.asList(activities));
    return wf;
  }

  // --- constructor ---

  @Test
  void shouldConstructWorkflowDirectGraphBuilder() {
    Workflow wf = workflow("wf1", activityWithMessageReceived("act1", "hello"));

    WorkflowDirectGraphBuilder builder = new WorkflowDirectGraphBuilder(wf, sessionService);

    assertThat(builder).isNotNull();
  }

  // --- build() ---

  @Test
  void shouldBuildGraphWithSingleActivityAndMessageReceivedEvent() {
    Workflow wf = workflow("wf1", activityWithMessageReceived("act1", "hello"));

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.getWorkflowId()).isEqualTo("wf1");
    assertThat(graph.getDictionary()).containsKey("act1");
    assertThat(graph.getStartEvents()).isNotEmpty();
  }

  @Test
  void shouldBuildGraphAndPropagateWorkflowVariables() {
    Activity activity = activityWithMessageReceived("act1", "hello");
    Workflow wf = workflow("wf-vars", activity);
    wf.setVariables(Map.of("key1", "value1", "key2", 42));

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.getVariables()).containsEntry("key1", "value1").containsEntry("key2", 42);
  }

  @Test
  void shouldBuildGraphWithMessageReceivedEventIdSetAsEventId() {
    MessageReceivedEvent msgEvent = new MessageReceivedEvent();
    msgEvent.setContent("hello");
    msgEvent.setId("customEventId");
    EventWithTimeout on = new EventWithTimeout();
    on.setMessageReceived(msgEvent);
    Debug debug = new Debug();
    debug.setId("act1");
    debug.setOn(on);
    Activity activity = new Activity();
    activity.setImplementation(debug);
    Workflow wf = workflow("wf1", activity);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("message-received_hello").getEventId()).isEqualTo("customEventId");
  }

  // --- computeStandaloneActivities ---

  @Test
  void shouldThrowNoStartingEventExceptionWhenFirstActivityHasNoEvents() {
    Workflow wf = workflow("wf1", standaloneActivity("act1"));

    assertThatThrownBy(() -> new WorkflowDirectGraphBuilder(wf, sessionService).build())
        .isInstanceOf(NoStartingEventException.class);
  }

  @Test
  void shouldBuildGraphLinkingStandaloneSecondActivityToPrevious() {
    Activity first = activityWithMessageReceived("act1", "hello");
    Activity second = standaloneActivity("act2");
    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.getDictionary()).containsKey("act1").containsKey("act2");
    assertThat(graph.getParents("act2")).contains("act1");
  }

  @Test
  void shouldBuildGraphWithIfConditionOnStandaloneSecondActivity() {
    Activity first = activityWithMessageReceived("act1", "hello");
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setIfCondition("someCondition");
    Activity second = new Activity();
    second.setImplementation(debug2);
    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("act2").getIfConditions()).isNotEmpty();
  }

  @Test
  void shouldThrowInvalidActivityExceptionWhenElseConditionWithoutPrecedingIf() {
    Activity first = activityWithMessageReceived("act1", "hello");
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setElseCondition("someElse");
    Activity second = new Activity();
    second.setImplementation(debug2);
    Workflow wf = workflow("wf1", first, second);

    assertThatThrownBy(() -> new WorkflowDirectGraphBuilder(wf, sessionService).build())
        .isInstanceOf(InvalidActivityException.class);
  }

  // --- computeParallelJoinGateway ---

  @Test
  void shouldAddJoinGatewayForParallelAllOfEvents() {
    Activity first = activityWithMessageReceived("act1", "hello");

    MessageReceivedEvent msg1 = new MessageReceivedEvent();
    msg1.setContent("msg1");
    EventWithTimeout e1 = new EventWithTimeout();
    e1.setMessageReceived(msg1);

    MessageReceivedEvent msg2 = new MessageReceivedEvent();
    msg2.setContent("msg2");
    EventWithTimeout e2 = new EventWithTimeout();
    e2.setMessageReceived(msg2);

    EventWithTimeout onAllOf = new EventWithTimeout();
    onAllOf.setAllOf(Arrays.asList(e1, e2));
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(onAllOf);
    Activity second = new Activity();
    second.setImplementation(debug2);

    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.getDictionary()).containsKey("act2_join_gateway");
    assertThat(graph.getParents("act2")).contains("act2_join_gateway");
  }

  // --- computeEvents: activityCompleted ---

  @Test
  void shouldBuildGraphWithActivityCompletedEvent() {
    Activity first = activityWithMessageReceived("act1", "hello");
    ActivityCompletedEvent completed = new ActivityCompletedEvent();
    completed.setActivityId("act1");
    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(completed);
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(on);
    Activity second = new Activity();
    second.setImplementation(debug2);
    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("act1").getElementType())
        .isEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    assertThat(graph.getParents("act2")).contains("act1");
  }

  @Test
  void shouldBuildGraphWithActivityCompletedEventAndIfConditionOnEvent() {
    Activity first = activityWithMessageReceived("act1", "hello");
    ActivityCompletedEvent completed = new ActivityCompletedEvent();
    completed.setActivityId("act1");
    completed.setIfCondition("eventCondition");
    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(completed);
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(on);
    Activity second = new Activity();
    second.setImplementation(debug2);
    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("act2").getIfConditions()).containsKey("act1");
  }

  @Test
  void shouldBuildGraphWithActivityCompletedEventAndIfConditionOnActivity() {
    Activity first = activityWithMessageReceived("act1", "hello");
    ActivityCompletedEvent completed = new ActivityCompletedEvent();
    completed.setActivityId("act1");
    EventWithTimeout on = new EventWithTimeout();
    on.setActivityCompleted(completed);
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(on);
    debug2.setIfCondition("activityCondition");
    Activity second = new Activity();
    second.setImplementation(debug2);
    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("act2").getIfConditions()).containsKey("act1");
  }

  // --- computeEvents: activityFailed ---

  @Test
  void shouldBuildGraphWithActivityFailedEvent() {
    Activity first = activityWithMessageReceived("act1", "hello");
    ActivityFailedEvent failed = new ActivityFailedEvent();
    failed.setActivityId("act1");
    EventWithTimeout on = new EventWithTimeout();
    on.setActivityFailed(failed);
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(on);
    Activity second = new Activity();
    second.setImplementation(debug2);
    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("act2").getElementType())
        .isEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    assertThat(graph.getParents("act2")).contains("act1");
  }

  // --- computeSignal: TIME_FIRED ---

  @Test
  void shouldBuildGraphWithTimerFiredEvent() {
    TimerFiredEvent timerEvent = new TimerFiredEvent();
    timerEvent.setAt("2024-01-01T10:00:00Z");
    EventWithTimeout on = new EventWithTimeout();
    on.setTimerFired(timerEvent);
    Debug debug = new Debug();
    debug.setId("act1");
    debug.setOn(on);
    Activity activity = new Activity();
    activity.setImplementation(debug);
    Workflow wf = workflow("wf1", activity);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("timerFired_date_2024-01-01T10:00:00Z").getElementType())
        .isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
  }

  // --- computeSignal: FORM_REPLIED exclusive (with default timeout) ---

  @Test
  void shouldBuildGraphWithExclusiveFormReplyAndRegisterTimeoutEvent() {
    Activity first = activityWithMessageReceived("act1", "hello");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setFormId("act1");
    formReplied.setExclusive(true);
    EventWithTimeout on = new EventWithTimeout();
    on.setFormReplied(formReplied);
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(on);
    Activity second = new Activity();
    second.setImplementation(debug2);

    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("form-reply_act1").getElementType())
        .isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(graph.getDictionary()).containsKey("form-reply_act1_timeout");
  }

  @Test
  void shouldNotRegisterTimeoutEventTwiceWhenAlreadyRegistered() {
    Activity first = activityWithMessageReceived("act1", "hello");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setFormId("act1");
    formReplied.setExclusive(true);
    EventWithTimeout on = new EventWithTimeout();
    on.setFormReplied(formReplied);
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(on);
    Activity second = new Activity();
    second.setImplementation(debug2);

    // Third activity expired for act2, which triggers registerTimeoutEvent again for already-registered id
    ActivityExpiredEvent expired = new ActivityExpiredEvent();
    expired.setActivityId("act2");
    EventWithTimeout on3 = new EventWithTimeout();
    on3.setActivityExpired(expired);
    Debug debug3 = new Debug();
    debug3.setId("act3");
    debug3.setOn(on3);
    Activity third = new Activity();
    third.setImplementation(debug3);

    Workflow wf = workflow("wf1", first, second, third);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.getDictionary()).containsKey("form-reply_act1_timeout");
    assertThat(graph.getParents("act3")).contains("form-reply_act1_timeout");
  }

  // --- computeNoExclusiveFormReplyEvent ---

  @Test
  void shouldBuildGraphWithNonExclusiveFormReplyAndSetDefaultTimeout() {
    Activity first = activityWithMessageReceived("act1", "hello");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setFormId("act1");
    formReplied.setExclusive(false);
    EventWithTimeout on = new EventWithTimeout();
    on.setFormReplied(formReplied);
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(on);
    Activity second = new Activity();
    second.setImplementation(debug2);

    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("form-reply_act1").getElementType())
        .isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(((EventWithTimeout) graph.readWorkflowNode("form-reply_act1").getEvent()).getTimeout())
        .isEqualTo("PT24H");
  }

  // --- computeExpiredActivity: non-exclusive form reply path ---

  @Test
  void shouldBuildGraphWithActivityExpiredEventForNonExclusiveFormReply() {
    Activity first = activityWithMessageReceived("act1", "hello");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setFormId("act1");
    formReplied.setExclusive(false);
    EventWithTimeout on2 = new EventWithTimeout();
    on2.setFormReplied(formReplied);
    Debug debug2 = new Debug();
    debug2.setId("act2");
    debug2.setOn(on2);
    Activity second = new Activity();
    second.setImplementation(debug2);

    ActivityExpiredEvent expired = new ActivityExpiredEvent();
    expired.setActivityId("act2");
    EventWithTimeout on3 = new EventWithTimeout();
    on3.setActivityExpired(expired);
    Debug debug3 = new Debug();
    debug3.setId("act3");
    debug3.setOn(on3);
    Activity third = new Activity();
    third.setImplementation(debug3);

    Workflow wf = workflow("wf1", first, second, third);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("act3").getElementType())
        .isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(graph.readWorkflowNode("act3").getWrappedType())
        .isEqualTo(ActivityExpiredEvent.class);
    assertThat(graph.getParents("act3")).contains("form-reply_act1");
  }

  // --- computeEvents: if condition on event node ---

  @Test
  void shouldBuildGraphWithIfConditionOnActivityEventNode() {
    MessageReceivedEvent msgEvent = new MessageReceivedEvent();
    msgEvent.setContent("hello");
    EventWithTimeout on = new EventWithTimeout();
    on.setMessageReceived(msgEvent);
    Debug debug = new Debug();
    debug.setId("act1");
    debug.setOn(on);
    debug.setIfCondition("someCondition");
    Activity activity = new Activity();
    activity.setImplementation(debug);
    Workflow wf = workflow("wf1", activity);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readWorkflowNode("act1").getIfConditions()).isNotEmpty();
  }

  // --- computeActivity: subsequent activity event-based gateway ---

  @Test
  void shouldBuildGraphWithEventBasedGatewayForSubsequentMessageReceivedActivity() {
    Activity first = activityWithMessageReceived("act1", "hello");
    Activity second = activityWithMessageReceived("act2", "world");
    Workflow wf = workflow("wf1", first, second);

    WorkflowDirectedGraph graph = new WorkflowDirectGraphBuilder(wf, sessionService).build();

    assertThat(graph.readChildren("act1").getGateway())
        .isEqualTo(WorkflowDirectedGraph.Gateway.EVENT_BASED);
  }
}
