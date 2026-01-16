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

class BpmnBuilderHelperClaude_hasConditionalStringTest {

  // Test 1: Empty children list - should return false (no children to check)
  @Test
  void hasConditionalString_withEmptyChildren_shouldReturnFalse() {
    // Given: Context with no children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return false (no children)
    assertThat(result).isFalse();
  }

  // Test 2: Single conditional child with matching parent - should return true
  @Test
  void hasConditionalString_withSingleConditionalChild_shouldReturnTrue() {
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

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 3: Single non-conditional child - should return false
  @Test
  void hasConditionalString_withSingleNonConditionalChild_shouldReturnFalse() {
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

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return false
    assertThat(result).isFalse();
  }

  // Test 4: Multiple non-conditional children - should return false
  @Test
  void hasConditionalString_withAllNonConditionalChildren_shouldReturnFalse() {
    // Given: Context with multiple non-conditional children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode child3 = new WorkflowNode()
        .id("child3")
        .elementType(WorkflowNodeType.ACTIVITY);

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);
    graph.registerToDictionary("child3", child3);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2", "child3")
    );

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return false (no conditional children)
    assertThat(result).isFalse();
  }

  // Test 5: Multiple conditional children - should return true
  @Test
  void hasConditionalString_withAllConditionalChildren_shouldReturnTrue() {
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

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 6: Mix of conditional and non-conditional children - should return true
  @Test
  void hasConditionalString_withMixedChildren_shouldReturnTrue() {
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

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (at least one child is conditional)
    assertThat(result).isTrue();
  }

  // Test 7: Child has condition but for different parent - should return false
  @Test
  void hasConditionalString_withConditionForDifferentParent_shouldReturnFalse() {
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

    // When: Checking if has any conditional child for parent1
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return false (condition is for different parent)
    assertThat(result).isFalse();
  }

  // Test 8: Children with conditions for different parents, one matches - should return true
  @Test
  void hasConditionalString_withOneChildMatchingParent_shouldReturnTrue() {
    // Given: Context with children that have conditions for different parents
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
        .addIfCondition("parent1", "condition2"); // Matches target parent

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking if has any conditional child for parent1
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (child2 has condition for parent1)
    assertThat(result).isTrue();
  }

  // Test 9: Child with conditions for multiple parents including target - should return true
  @Test
  void hasConditionalString_withConditionsForMultipleParents_shouldReturnTrue() {
    // Given: Context with child that has conditions for multiple parents
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1")
        .addIfCondition("parent2", "condition2");

    graph.registerToDictionary("child1", child1);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.singletonList("child1")
    );

    // When: Checking if has any conditional child for parent1
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (child has condition for parent1)
    assertThat(result).isTrue();
  }

  // Test 10: Null parentId with children having conditions - should return false
  @Test
  void hasConditionalString_withNullParentId_shouldReturnFalse() {
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

    // When: Checking if has any conditional child with null parentId
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, null);

    // Then: Should return false (no condition for null parent)
    assertThat(result).isFalse();
  }

  // Test 11: Empty string parentId with children having conditions - should return false
  @Test
  void hasConditionalString_withEmptyStringParentId_shouldReturnFalse() {
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

    // When: Checking if has any conditional child with empty parentId
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "");

    // Then: Should return false (no condition for empty parent)
    assertThat(result).isFalse();
  }

  // Test 12: First child is conditional - should return true (short-circuit evaluation)
  @Test
  void hasConditionalString_withFirstChildConditional_shouldReturnTrue() {
    // Given: Context with first child conditional
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode child3 = new WorkflowNode()
        .id("child3")
        .elementType(WorkflowNodeType.ACTIVITY);

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);
    graph.registerToDictionary("child3", child3);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2", "child3")
    );

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (first child is conditional)
    assertThat(result).isTrue();
  }

  // Test 13: Last child is conditional - should return true
  @Test
  void hasConditionalString_withLastChildConditional_shouldReturnTrue() {
    // Given: Context with last child conditional
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode child3 = new WorkflowNode()
        .id("child3")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);
    graph.registerToDictionary("child3", child3);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2", "child3")
    );

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (last child is conditional)
    assertThat(result).isTrue();
  }

  // Test 14: Middle child is conditional - should return true
  @Test
  void hasConditionalString_withMiddleChildConditional_shouldReturnTrue() {
    // Given: Context with middle child conditional
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode child1 = new WorkflowNode()
        .id("child1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode child2 = new WorkflowNode()
        .id("child2")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");
    WorkflowNode child3 = new WorkflowNode()
        .id("child3")
        .elementType(WorkflowNodeType.ACTIVITY);

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);
    graph.registerToDictionary("child3", child3);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2", "child3")
    );

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (middle child is conditional)
    assertThat(result).isTrue();
  }

  // Test 15: Large number of children with none conditional - should return false
  @Test
  void hasConditionalString_withManyNonConditionalChildren_shouldReturnFalse() {
    // Given: Context with many non-conditional children
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    for (int i = 1; i <= 20; i++) {
      WorkflowNode child = new WorkflowNode()
          .id("child" + i)
          .elementType(WorkflowNodeType.ACTIVITY);
      graph.registerToDictionary("child" + i, child);
    }

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    for (int i = 1; i <= 20; i++) {
      nodeChildren.addChild("child" + i);
    }

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return false (no conditional children)
    assertThat(result).isFalse();
  }

  // Test 16: Large number of children with one conditional - should return true
  @Test
  void hasConditionalString_withManyChildrenAndOneConditional_shouldReturnTrue() {
    // Given: Context with many children and one conditional
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    for (int i = 1; i <= 19; i++) {
      WorkflowNode child = new WorkflowNode()
          .id("child" + i)
          .elementType(WorkflowNodeType.ACTIVITY);
      graph.registerToDictionary("child" + i, child);
    }

    // Add one conditional child
    WorkflowNode conditionalChild = new WorkflowNode()
        .id("child20")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent1", "condition1");
    graph.registerToDictionary("child20", conditionalChild);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    for (int i = 1; i <= 20; i++) {
      nodeChildren.addChild("child" + i);
    }

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (one child is conditional)
    assertThat(result).isTrue();
  }

  // Test 17: Different node types, one conditional - should return true
  @Test
  void hasConditionalString_withDifferentNodeTypesOneConditional_shouldReturnTrue() {
    // Given: Context with different node types, one conditional
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    WorkflowNode activity = new WorkflowNode()
        .id("activity1")
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode timer = new WorkflowNode()
        .id("timer1")
        .elementType(WorkflowNodeType.TIMER_FIRED_EVENT)
        .addIfCondition("parent1", "condition1");
    WorkflowNode signal = new WorkflowNode()
        .id("signal1")
        .elementType(WorkflowNodeType.SIGNAL_EVENT);

    graph.registerToDictionary("activity1", activity);
    graph.registerToDictionary("timer1", timer);
    graph.registerToDictionary("signal1", signal);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("activity1", "timer1", "signal1")
    );

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (timer is conditional)
    assertThat(result).isTrue();
  }

  // Test 18: All children have conditions for different parents - should return false
  @Test
  void hasConditionalString_withAllChildrenConditionalForDifferentParents_shouldReturnFalse() {
    // Given: Context where all children are conditional for other parents
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
        .addIfCondition("parent3", "condition2");

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2")
    );

    // When: Checking if has any conditional child for parent1
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return false (no conditions for parent1)
    assertThat(result).isFalse();
  }

  // Test 19: Child with empty condition map - should return false
  @Test
  void hasConditionalString_withChildHavingEmptyConditionMap_shouldReturnFalse() {
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

    // When: Checking if has any conditional child
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return false (empty condition map means not conditional)
    assertThat(result).isFalse();
  }

  // Test 20: Multiple children with some conditional for target, some for different parents - should return true
  @Test
  void hasConditionalString_withSomeConditionalForTargetSomeForOthers_shouldReturnTrue() {
    // Given: Context with mixed conditions for different parents
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
        .addIfCondition("parent1", "condition2"); // Matches target
    WorkflowNode child3 = new WorkflowNode()
        .id("child3")
        .elementType(WorkflowNodeType.ACTIVITY)
        .addIfCondition("parent3", "condition3");

    graph.registerToDictionary("child1", child1);
    graph.registerToDictionary("child2", child2);
    graph.registerToDictionary("child3", child3);

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList("child1", "child2", "child3")
    );

    // When: Checking if has any conditional child for parent1
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, "parent1");

    // Then: Should return true (child2 has condition for parent1)
    assertThat(result).isTrue();
  }
}
