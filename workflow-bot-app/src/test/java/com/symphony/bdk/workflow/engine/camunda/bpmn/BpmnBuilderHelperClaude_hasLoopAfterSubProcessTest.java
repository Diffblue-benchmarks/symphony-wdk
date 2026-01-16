package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class BpmnBuilderHelperClaude_hasLoopAfterSubProcessTest {

  // Test 1: All three conditions met - should return true
  @Test
  void hasLoopAfterSubProcess_withAllConditionsMet_shouldReturnTrue() {
    // Given: ACTIVITY_COMPLETED_EVENT, event subprocess exists, and at least one child already built
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking if has loop after subprocess
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Then: Should return true (all conditions met)
    assertThat(result).isTrue();
  }

  // Test 2: Wrong node type (ACTIVITY) - should return false
  @Test
  void hasLoopAfterSubProcess_withActivityNodeType_shouldReturnFalse() {
    // Given: ACTIVITY type instead of ACTIVITY_COMPLETED_EVENT
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking with ACTIVITY type
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY);

    // Then: Should return false (wrong node type)
    assertThat(result).isFalse();
  }

  // Test 3: No event subprocess - should return false
  @Test
  void hasLoopAfterSubProcess_withoutEventSubProcess_shouldReturnFalse() {
    // Given: ACTIVITY_COMPLETED_EVENT but no event subprocess
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking without event subprocess
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Then: Should return false (no event subprocess)
    assertThat(result).isFalse();
  }

  // Test 4: No children already built - should return false
  @Test
  void hasLoopAfterSubProcess_withNoChildrenAlreadyBuilt_shouldReturnFalse() {
    // Given: ACTIVITY_COMPLETED_EVENT and event subprocess but no children already built
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking with no children already built
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Then: Should return false (no children already built)
    assertThat(result).isFalse();
  }

  // Test 5: Empty children list - should return false
  @Test
  void hasLoopAfterSubProcess_withEmptyChildren_shouldReturnFalse() {
    // Given: ACTIVITY_COMPLETED_EVENT and event subprocess but empty children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Checking with empty children
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Then: Should return false (no children to check)
    assertThat(result).isFalse();
  }

  // Test 6: Wrong node type (SIGNAL_EVENT) - should return false
  @Test
  void hasLoopAfterSubProcess_withSignalEventNodeType_shouldReturnFalse() {
    // Given: SIGNAL_EVENT type instead of ACTIVITY_COMPLETED_EVENT
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking with SIGNAL_EVENT type
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.SIGNAL_EVENT);

    // Then: Should return false (wrong node type)
    assertThat(result).isFalse();
  }

  // Test 7: Wrong node type (FORM_REPLIED_EVENT) - should return false
  @Test
  void hasLoopAfterSubProcess_withFormRepliedEventNodeType_shouldReturnFalse() {
    // Given: FORM_REPLIED_EVENT type instead of ACTIVITY_COMPLETED_EVENT
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking with FORM_REPLIED_EVENT type
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.FORM_REPLIED_EVENT);

    // Then: Should return false (wrong node type)
    assertThat(result).isFalse();
  }

  // Test 8: Multiple children, only one already built - should return true
  @Test
  void hasLoopAfterSubProcess_withOneOfMultipleChildrenBuilt_shouldReturnTrue() {
    // Given: ACTIVITY_COMPLETED_EVENT, event subprocess, multiple children but only one built
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add only one node builder (child2 is already built)
    context.addNodeBuilder("child2", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2", "child3")
    );

    // When: Checking with one of multiple children built
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Then: Should return true (at least one child is already built)
    assertThat(result).isTrue();
  }

  // Test 9: All children already built - should return true
  @Test
  void hasLoopAfterSubProcess_withAllChildrenBuilt_shouldReturnTrue() {
    // Given: ACTIVITY_COMPLETED_EVENT, event subprocess, and all children already built
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add all node builders
    context.addNodeBuilder("child1", processBuilder.startEvent());
    context.addNodeBuilder("child2", processBuilder.startEvent());
    context.addNodeBuilder("child3", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2", "child3")
    );

    // When: Checking with all children built
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Then: Should return true (all children already built)
    assertThat(result).isTrue();
  }

  // Test 10: Wrong node type (ACTIVITY_FAILED_EVENT) - should return false
  @Test
  void hasLoopAfterSubProcess_withActivityFailedEventNodeType_shouldReturnFalse() {
    // Given: ACTIVITY_FAILED_EVENT type instead of ACTIVITY_COMPLETED_EVENT
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking with ACTIVITY_FAILED_EVENT type
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_FAILED_EVENT);

    // Then: Should return false (wrong node type)
    assertThat(result).isFalse();
  }

  // Test 11: Wrong node type (ACTIVITY_EXPIRED_EVENT) - should return false
  @Test
  void hasLoopAfterSubProcess_withActivityExpiredEventNodeType_shouldReturnFalse() {
    // Given: ACTIVITY_EXPIRED_EVENT type instead of ACTIVITY_COMPLETED_EVENT
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking with ACTIVITY_EXPIRED_EVENT type
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);

    // Then: Should return false (wrong node type)
    assertThat(result).isFalse();
  }

  // Test 12: Wrong node type (TIMER_FIRED_EVENT) - should return false
  @Test
  void hasLoopAfterSubProcess_withTimerFiredEventNodeType_shouldReturnFalse() {
    // Given: TIMER_FIRED_EVENT type instead of ACTIVITY_COMPLETED_EVENT
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking with TIMER_FIRED_EVENT type
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.TIMER_FIRED_EVENT);

    // Then: Should return false (wrong node type)
    assertThat(result).isFalse();
  }

  // Test 13: Wrong node type (JOIN_ACTIVITY) - should return false
  @Test
  void hasLoopAfterSubProcess_withJoinActivityNodeType_shouldReturnFalse() {
    // Given: JOIN_ACTIVITY type instead of ACTIVITY_COMPLETED_EVENT
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking with JOIN_ACTIVITY type
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.JOIN_ACTIVITY);

    // Then: Should return false (wrong node type)
    assertThat(result).isFalse();
  }

  // Test 14: Single child already built - should return true
  @Test
  void hasLoopAfterSubProcess_withSingleChildBuilt_shouldReturnTrue() {
    // Given: ACTIVITY_COMPLETED_EVENT, event subprocess, and single child already built
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add a node builder to simulate an already built node
    context.addNodeBuilder("child1", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking with single child built
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Then: Should return true (single child is already built)
    assertThat(result).isTrue();
  }

  // Test 15: ACTIVITY_COMPLETED_EVENT and event subprocess but no children in list - should return false
  @Test
  void hasLoopAfterSubProcess_withNoChildrenInList_shouldReturnFalse() {
    // Given: ACTIVITY_COMPLETED_EVENT and event subprocess but children list is empty
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("process1").done();
    ProcessBuilder processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Add an event subprocess to the context
    EventSubProcessBuilder eventSubProcessBuilder = processBuilder.eventSubProcess();
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Add some node builders but don't add them to children list
    context.addNodeBuilder("someNode", processBuilder.startEvent());

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.emptyList()
    );

    // When: Checking with empty children list
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Then: Should return false (no children in list)
    assertThat(result).isFalse();
  }
}
