package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BpmnBuilderHelperClaude_hasAllConditionalChildrenTest {

  // Test 1: Empty children list - should return true (vacuous truth - all zero children are conditional)
  @Test
  void hasAllConditionalChildren_withEmptyChildren_shouldReturnTrue() {
    // Given: Context with no children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return true (vacuous truth)
    assertThat(result).isTrue();
  }

  // Test 2: Single conditional child with matching parent - should return true
  @Test
  void hasAllConditionalChildren_withSingleConditionalChild_shouldReturnTrue() {
    // Given: Context with one conditional child
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");

    graph.registerToDictionary("child1", child1);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 3: Single non-conditional child - should return false
  @Test
  void hasAllConditionalChildren_withSingleNonConditionalChild_shouldReturnFalse() {
    // Given: Context with one non-conditional child
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY);

    graph.registerToDictionary("child1", child1);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return false
    assertThat(result).isFalse();
  }

  // Test 4: Multiple conditional children with matching parent - should return true
  @Test
  void hasAllConditionalChildren_withAllConditionalChildren_shouldReturnTrue() {
    // Given: Context with multiple conditional children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition2");
    WorkflowNode child3 = new WorkflowNode()
        .id("child3")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition3");

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);
    graph.registerToDictionary("child3", child3);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2", "child3")
    );

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 5: Mix of conditional and non-conditional children - should return false
  @Test
  void hasAllConditionalChildren_withMixedChildren_shouldReturnFalse() {
    // Given: Context with mix of conditional and non-conditional children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY); // No condition

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return false (not all children are conditional)
    assertThat(result).isFalse();
  }

  // Test 6: Child has condition but for different parent - should return false
  @Test
  void hasAllConditionalChildren_withConditionForDifferentParent_shouldReturnFalse() {
    // Given: Context with child that has condition for different parent
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent2", "condition1"); // Different parent

    graph.registerToDictionary("child1", child1);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking if all children are conditional for parent1
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return false (condition is for different parent)
    assertThat(result).isFalse();
  }

  // Test 7: Children with conditions for multiple parents including the target - should return true
  @Test
  void hasAllConditionalChildren_withConditionsForMultipleParents_shouldReturnTrue() {
    // Given: Context with children that have conditions for multiple parents
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1")
        .addIfCondition("parent2", "condition2");
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition3")
        .addIfCondition("parent3", "condition4");

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking if all children are conditional for parent1
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return true (both have condition for parent1)
    assertThat(result).isTrue();
  }

  // Test 8: Some children have condition for target parent, others don't - should return false
  @Test
  void hasAllConditionalChildren_withSomeChildrenHavingConditionForTargetParent_shouldReturnFalse() {
    // Given: Context where some children have condition for target parent
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent2", "condition2"); // Different parent

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking if all children are conditional for parent1
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return false (child2 doesn't have condition for parent1)
    assertThat(result).isFalse();
  }

  // Test 9: Null parentId with children having conditions - should return false
  @Test
  void hasAllConditionalChildren_withNullParentId_shouldReturnFalse() {
    // Given: Context with conditional children but null parentId
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");

    graph.registerToDictionary("child1", child1);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking if all children are conditional with null parentId
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, null);

    // Then: Should return false (no condition for null parent)
    assertThat(result).isFalse();
  }

  // Test 10: Empty string parentId with children having conditions - should return false
  @Test
  void hasAllConditionalChildren_withEmptyStringParentId_shouldReturnFalse() {
    // Given: Context with conditional children but empty string parentId
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");

    graph.registerToDictionary("child1", child1);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking if all children are conditional with empty parentId
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "");

    // Then: Should return false (no condition for empty parent)
    assertThat(result).isFalse();
  }

  // Test 11: Large number of conditional children - should return true
  @Test
  void hasAllConditionalChildren_withManyConditionalChildren_shouldReturnTrue() {
    // Given: Context with many conditional children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    for (int i = 1; i <= 20; i++) {
      WorkflowNode child = new WorkflowNode()
          .id("child" + i)
          .elementType(WorkflowNodeType.ACTIVITY)
          .addIfCondition("parent1", "condition" + i);
      graph.registerToDictionary("child" + i, child);
    }

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    for (int i = 1; i <= 20; i++) {
      nodeChildren.addChild("child" + i);
    }

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 12: Large number of children with one non-conditional - should return false
  @Test
  void hasAllConditionalChildren_withManyChildrenAndOneNonConditional_shouldReturnFalse() {
    // Given: Context with many conditional children and one non-conditional
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    for (int i = 1; i <= 19; i++) {
      WorkflowNode child = new WorkflowNode()
          .id("child" + i)
          .elementType(WorkflowNodeType.ACTIVITY)
          .addIfCondition("parent1", "condition" + i);
      graph.registerToDictionary("child" + i, child);
    }

    // Add one non-conditional child
    WorkflowNode nonConditionalChild = new WorkflowNode()
        .id("child20")
        .elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("child20", nonConditionalChild);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    for (int i = 1; i <= 20; i++) {
      nodeChildren.addChild("child" + i);
    }

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return false (one child is not conditional)
    assertThat(result).isFalse();
  }

  // Test 13: Different node types all conditional - should return true
  @Test
  void hasAllConditionalChildren_withDifferentNodeTypesAllConditional_shouldReturnTrue() {
    // Given: Context with different node types, all conditional
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode activity = new WorkflowNode()
        .id("activity1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");
    WorkflowNode timer = new WorkflowNode()
        .id("timer1")
        .elementType(WorkflowNodeType.TIMER_FIRED_EVENT)
        .addIfCondition("parent1", "condition2");
    WorkflowNode signal = new WorkflowNode()
        .id("signal1")
        .elementType(WorkflowNodeType.SIGNAL_EVENT)
        .addIfCondition("parent1", "condition3");

    graph.registerToDictionary("activity1", activity);
    graph.registerToDictionary("timer1", timer);
    graph.registerToDictionary("signal1", signal);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("activity1", "timer1", "signal1")
    );

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 14: Child with empty condition map - should return false
  @Test
  void hasAllConditionalChildren_withChildHavingEmptyConditionMap_shouldReturnFalse() {
    // Given: Context with child that has empty condition map
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY);
    // No conditions added - empty map

    graph.registerToDictionary("child1", child1);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking if all children are conditional
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return false (empty condition map means not conditional)
    assertThat(result).isFalse();
  }

  // Test 15: All children conditional for one parent but checking different parent - should return false
  @Test
  void hasAllConditionalChildren_withAllConditionalForDifferentParent_shouldReturnFalse() {
    // Given: Context where all children are conditional for parent2 but we check parent1
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent2", "condition1");
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent2", "condition2");

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking if all children are conditional for parent1 (but they're conditional for parent2)
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, "parent1");

    // Then: Should return false (no conditions for parent1)
    assertThat(result).isFalse();
  }
}
