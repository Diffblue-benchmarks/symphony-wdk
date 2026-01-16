package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BpmnBuilderHelperClaude_hasActivitiesOnlyTest {

  // Test 1: Empty children list - should return true (no events)
  @Test
  void hasActivitiesOnly_withEmptyChildren_shouldReturnTrue() {
    // Given: Context with no children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return true (no signal or form replied events)
    assertThat(result).isTrue();
  }

  // Test 2: Children with only ACTIVITY nodes - should return true
  @Test
  void hasActivitiesOnly_withOnlyActivityNodes_shouldReturnTrue() {
    // Given: Context with only activity children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode activityNode1 = new WorkflowNode()
        .id("activity1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode activityNode2 = new WorkflowNode()
        .id("activity2")
        .elementType(WorkflowNodeType.ACTIVITY);

    graph.registerToDictionary("activity1", activityNode1);
    graph.registerToDictionary("activity2", activityNode2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("activity1", "activity2")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return true (no signal or form replied events)
    assertThat(result).isTrue();
  }

  // Test 3: Children with SIGNAL_EVENT - should return false
  @Test
  void hasActivitiesOnly_withSignalEvent_shouldReturnFalse() {
    // Given: Context with a signal event child
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode signalEventNode = new WorkflowNode()
        .id("signal1")
        .elementType(WorkflowNodeType.SIGNAL_EVENT);

    graph.registerToDictionary("signal1", signalEventNode);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("signal1")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has signal event)
    assertThat(result).isFalse();
  }

  // Test 4: Children with FORM_REPLIED_EVENT - should return false
  @Test
  void hasActivitiesOnly_withFormRepliedEvent_shouldReturnFalse() {
    // Given: Context with a form replied event child
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode formRepliedEventNode = new WorkflowNode()
        .id("form1")
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    graph.registerToDictionary("form1", formRepliedEventNode);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("form1")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has form replied event)
    assertThat(result).isFalse();
  }

  // Test 5: Mixed children with both activities and signal event - should return false
  @Test
  void hasActivitiesOnly_withActivitiesAndSignalEvent_shouldReturnFalse() {
    // Given: Context with activities and a signal event
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode activityNode = new WorkflowNode()
        .id("activity1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode signalEventNode = new WorkflowNode()
        .id("signal1")
        .elementType(WorkflowNodeType.SIGNAL_EVENT);

    graph.registerToDictionary("activity1", activityNode);
    graph.registerToDictionary("signal1", signalEventNode);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("activity1", "signal1")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has signal event)
    assertThat(result).isFalse();
  }

  // Test 6: Mixed children with both activities and form replied event - should return false
  @Test
  void hasActivitiesOnly_withActivitiesAndFormRepliedEvent_shouldReturnFalse() {
    // Given: Context with activities and a form replied event
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode activityNode = new WorkflowNode()
        .id("activity1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode formRepliedEventNode = new WorkflowNode()
        .id("form1")
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    graph.registerToDictionary("activity1", activityNode);
    graph.registerToDictionary("form1", formRepliedEventNode);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("activity1", "form1")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has form replied event)
    assertThat(result).isFalse();
  }

  // Test 7: Children with both signal and form replied events - should return false
  @Test
  void hasActivitiesOnly_withBothSignalAndFormRepliedEvents_shouldReturnFalse() {
    // Given: Context with both signal and form replied events
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode signalEventNode = new WorkflowNode()
        .id("signal1")
        .elementType(WorkflowNodeType.SIGNAL_EVENT);
    WorkflowNode formRepliedEventNode = new WorkflowNode()
        .id("form1")
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    graph.registerToDictionary("signal1", signalEventNode);
    graph.registerToDictionary("form1", formRepliedEventNode);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("signal1", "form1")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has both event types)
    assertThat(result).isFalse();
  }

  // Test 8: Children with other event types (not signal or form replied) - should return true
  @Test
  void hasActivitiesOnly_withOtherEventTypes_shouldReturnTrue() {
    // Given: Context with timer, activity completed, activity failed, and activity expired events
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode timerNode = new WorkflowNode()
        .id("timer1")
        .elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
    WorkflowNode activityCompletedNode = new WorkflowNode()
        .id("completed1")
        .elementType(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    WorkflowNode activityFailedNode = new WorkflowNode()
        .id("failed1")
        .elementType(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    WorkflowNode activityExpiredNode = new WorkflowNode()
        .id("expired1")
        .elementType(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);

    graph.registerToDictionary("timer1", timerNode);
    graph.registerToDictionary("completed1", activityCompletedNode);
    graph.registerToDictionary("failed1", activityFailedNode);
    graph.registerToDictionary("expired1", activityExpiredNode);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("timer1", "completed1", "failed1", "expired1")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return true (no signal or form replied events)
    assertThat(result).isTrue();
  }

  // Test 9: Single activity child - should return true
  @Test
  void hasActivitiesOnly_withSingleActivityChild_shouldReturnTrue() {
    // Given: Context with a single activity child
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode activityNode = new WorkflowNode()
        .id("activity1")
        .elementType(WorkflowNodeType.ACTIVITY);

    graph.registerToDictionary("activity1", activityNode);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("activity1")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 10: Multiple activities and other event types (not signal/form replied) - should return true
  @Test
  void hasActivitiesOnly_withActivitiesAndOtherEventTypes_shouldReturnTrue() {
    // Given: Context with activities, timer events, and completed events
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode activity1 = new WorkflowNode()
        .id("activity1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode activity2 = new WorkflowNode()
        .id("activity2")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode timerNode = new WorkflowNode()
        .id("timer1")
        .elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
    WorkflowNode joinNode = new WorkflowNode()
        .id("join1")
        .elementType(WorkflowNodeType.JOIN_ACTIVITY);

    graph.registerToDictionary("activity1", activity1);
    graph.registerToDictionary("activity2", activity2);
    graph.registerToDictionary("timer1", timerNode);
    graph.registerToDictionary("join1", joinNode);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("activity1", "activity2", "timer1", "join1")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return true (no signal or form replied events)
    assertThat(result).isTrue();
  }

  // Test 11: Multiple signal events - should return false
  @Test
  void hasActivitiesOnly_withMultipleSignalEvents_shouldReturnFalse() {
    // Given: Context with multiple signal events
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode signal1 = new WorkflowNode()
        .id("signal1")
        .elementType(WorkflowNodeType.SIGNAL_EVENT);
    WorkflowNode signal2 = new WorkflowNode()
        .id("signal2")
        .elementType(WorkflowNodeType.SIGNAL_EVENT);

    graph.registerToDictionary("signal1", signal1);
    graph.registerToDictionary("signal2", signal2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("signal1", "signal2")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has signal events)
    assertThat(result).isFalse();
  }

  // Test 12: Multiple form replied events - should return false
  @Test
  void hasActivitiesOnly_withMultipleFormRepliedEvents_shouldReturnFalse() {
    // Given: Context with multiple form replied events
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode form1 = new WorkflowNode()
        .id("form1")
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);
    WorkflowNode form2 = new WorkflowNode()
        .id("form2")
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    graph.registerToDictionary("form1", form1);
    graph.registerToDictionary("form2", form2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("form1", "form2")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has form replied events)
    assertThat(result).isFalse();
  }

  // Test 13: Large list of activities - should return true
  @Test
  void hasActivitiesOnly_withManyActivities_shouldReturnTrue() {
    // Given: Context with many activities
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    List<String> childIds = Arrays.asList("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10");
    for (String id : childIds) {
      WorkflowNode activityNode = new WorkflowNode()
          .id(id)
          .elementType(WorkflowNodeType.ACTIVITY);
      graph.registerToDictionary(id, activityNode);
    }

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(childIds);

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 14: Large list with one signal event at the end - should return false
  @Test
  void hasActivitiesOnly_withManyActivitiesAndOneSignalEvent_shouldReturnFalse() {
    // Given: Context with many activities and one signal event
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    for (int i = 1; i <= 10; i++) {
      WorkflowNode activityNode = new WorkflowNode()
          .id("a" + i)
          .elementType(WorkflowNodeType.ACTIVITY);
      graph.registerToDictionary("a" + i, activityNode);
    }

    WorkflowNode signalNode = new WorkflowNode()
        .id("signal1")
        .elementType(WorkflowNodeType.SIGNAL_EVENT);
    graph.registerToDictionary("signal1", signalNode);

    List<String> childIds = Arrays.asList("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "signal1");
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(childIds);

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has signal event)
    assertThat(result).isFalse();
  }

  // Test 15: Complex mix with signal event in the middle - should return false
  @Test
  void hasActivitiesOnly_withSignalEventInMiddle_shouldReturnFalse() {
    // Given: Context with activities, signal event in middle, and other events
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode activity1 = new WorkflowNode()
        .id("activity1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode timerNode = new WorkflowNode()
        .id("timer1")
        .elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
    WorkflowNode signalNode = new WorkflowNode()
        .id("signal1")
        .elementType(WorkflowNodeType.SIGNAL_EVENT);
    WorkflowNode activity2 = new WorkflowNode()
        .id("activity2")
        .elementType(WorkflowNodeType.ACTIVITY);

    graph.registerToDictionary("activity1", activity1);
    graph.registerToDictionary("timer1", timerNode);
    graph.registerToDictionary("signal1", signalNode);
    graph.registerToDictionary("activity2", activity2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("activity1", "timer1", "signal1", "activity2")
    );

    // When: Checking if has activities only
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Then: Should return false (has signal event)
    assertThat(result).isFalse();
  }
}
