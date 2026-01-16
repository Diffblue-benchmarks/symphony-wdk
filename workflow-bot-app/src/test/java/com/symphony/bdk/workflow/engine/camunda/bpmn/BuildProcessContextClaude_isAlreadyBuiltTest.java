package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuildProcessContextClaude_isAlreadyBuiltTest {

  private BuildProcessContext context;
  private ProcessBuilder processBuilder;

  @BeforeEach
  void setUp() {
    // Create a real BpmnModelInstance and ProcessBuilder for testing
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("testProcess").done();
    processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();

    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    context = new BuildProcessContext(graph, processBuilder);
  }

  // Test 1: isAlreadyBuilt should return false when no builder exists for the node
  @Test
  void isAlreadyBuilt_whenNodeNotAdded_shouldReturnFalse() {
    // When: Checking if a node is already built without adding it
    boolean result = context.isAlreadyBuilt("node1");

    // Then: Should return false
    assertThat(result).isFalse();
  }

  // Test 2: isAlreadyBuilt should return true after adding a node builder
  @Test
  void isAlreadyBuilt_afterAddingNodeBuilder_shouldReturnTrue() {
    // Given: A builder is added for a node
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addNodeBuilder("node1", builder);

    // When: Checking if the node is already built
    boolean result = context.isAlreadyBuilt("node1");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 3: isAlreadyBuilt should return true even when builder value is null
  @Test
  void isAlreadyBuilt_whenBuilderIsNull_shouldReturnTrue() {
    // Given: A null builder is added for a node (key exists but value is null)
    context.addNodeBuilder("node1", null);

    // When: Checking if the node is already built
    boolean result = context.isAlreadyBuilt("node1");

    // Then: Should return true (because the key exists in the map)
    assertThat(result).isTrue();
  }

  // Test 4: isAlreadyBuilt should return false for different node IDs
  @Test
  void isAlreadyBuilt_forDifferentNodeIds_shouldReturnFalse() {
    // Given: A builder is added for node1
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addNodeBuilder("node1", builder);

    // When: Checking if different node IDs are already built
    boolean resultNode2 = context.isAlreadyBuilt("node2");
    boolean resultNode3 = context.isAlreadyBuilt("node3");

    // Then: Should return false for all non-added nodes
    assertThat(resultNode2).isFalse();
    assertThat(resultNode3).isFalse();
  }

  // Test 5: isAlreadyBuilt should handle empty string node ID
  @Test
  void isAlreadyBuilt_withEmptyString_shouldReturnFalseInitially() {
    // When: Checking if empty string node is already built
    boolean result = context.isAlreadyBuilt("");

    // Then: Should return false initially
    assertThat(result).isFalse();
  }

  // Test 6: isAlreadyBuilt should return true for empty string after getNodeBuilder creates it
  @Test
  void isAlreadyBuilt_afterGetNodeBuilderCreatesEmptyString_shouldReturnTrue() {
    // Given: getNodeBuilder is called with empty string (which creates and caches it)
    context.getNodeBuilder("");

    // When: Checking if empty string node is already built
    boolean result = context.isAlreadyBuilt("");

    // Then: Should return true (because getNodeBuilder added it to the map)
    assertThat(result).isTrue();
  }

  // Test 7: isAlreadyBuilt should work with the special "last" key
  @Test
  void isAlreadyBuilt_withLastKey_shouldReturnFalseInitially() {
    // When: Checking if "last" key is already built
    boolean result = context.isAlreadyBuilt("last");

    // Then: Should return false initially
    assertThat(result).isFalse();
  }

  // Test 8: isAlreadyBuilt should return true for "last" key after addLastNodeBuilder
  @Test
  void isAlreadyBuilt_afterAddingLastNodeBuilder_shouldReturnTrue() {
    // Given: A last node builder is added
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addLastNodeBuilder(builder);

    // When: Checking if "last" key is already built
    boolean result = context.isAlreadyBuilt("last");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 9: isAlreadyBuilt should handle multiple nodes independently
  @Test
  void isAlreadyBuilt_withMultipleNodes_shouldTrackEachIndependently() {
    // Given: Multiple builders are added
    AbstractFlowNodeBuilder<?, ?> builder1 = processBuilder.startEvent("event1");
    AbstractFlowNodeBuilder<?, ?> builder2 = processBuilder.startEvent("event2");
    context.addNodeBuilder("node1", builder1);
    context.addNodeBuilder("node2", builder2);

    // When: Checking which nodes are already built
    boolean resultNode1 = context.isAlreadyBuilt("node1");
    boolean resultNode2 = context.isAlreadyBuilt("node2");
    boolean resultNode3 = context.isAlreadyBuilt("node3");

    // Then: Should return true for added nodes, false for non-added
    assertThat(resultNode1).isTrue();
    assertThat(resultNode2).isTrue();
    assertThat(resultNode3).isFalse();
  }

  // Test 10: isAlreadyBuilt should be case-sensitive
  @Test
  void isAlreadyBuilt_shouldBeCaseSensitive() {
    // Given: A builder is added for "Node1"
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addNodeBuilder("Node1", builder);

    // When: Checking with different cases
    boolean resultUpperCase = context.isAlreadyBuilt("Node1");
    boolean resultLowerCase = context.isAlreadyBuilt("node1");
    boolean resultAllUpper = context.isAlreadyBuilt("NODE1");

    // Then: Should only return true for exact match
    assertThat(resultUpperCase).isTrue();
    assertThat(resultLowerCase).isFalse();
    assertThat(resultAllUpper).isFalse();
  }

  // Test 11: isAlreadyBuilt should handle special characters in node ID
  @Test
  void isAlreadyBuilt_withSpecialCharacters_shouldWork() {
    // Given: Builders with special character node IDs
    AbstractFlowNodeBuilder<?, ?> builder1 = processBuilder.startEvent();
    AbstractFlowNodeBuilder<?, ?> builder2 = processBuilder.startEvent();
    AbstractFlowNodeBuilder<?, ?> builder3 = processBuilder.startEvent();
    context.addNodeBuilder("node-1", builder1);
    context.addNodeBuilder("node_2", builder2);
    context.addNodeBuilder("node.3", builder3);

    // When: Checking if they are already built
    boolean result1 = context.isAlreadyBuilt("node-1");
    boolean result2 = context.isAlreadyBuilt("node_2");
    boolean result3 = context.isAlreadyBuilt("node.3");

    // Then: Should return true for all
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
    assertThat(result3).isTrue();
  }

  // Test 12: isAlreadyBuilt should handle node IDs with spaces
  @Test
  void isAlreadyBuilt_withSpaces_shouldWork() {
    // Given: A builder with a node ID containing spaces
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addNodeBuilder("node with spaces", builder);

    // When: Checking if it is already built
    boolean result = context.isAlreadyBuilt("node with spaces");

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 13: isAlreadyBuilt should consistently return true after multiple calls
  @Test
  void isAlreadyBuilt_calledMultipleTimes_shouldReturnConsistently() {
    // Given: A builder is added for a node
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addNodeBuilder("node1", builder);

    // When: Checking multiple times
    boolean result1 = context.isAlreadyBuilt("node1");
    boolean result2 = context.isAlreadyBuilt("node1");
    boolean result3 = context.isAlreadyBuilt("node1");

    // Then: Should return true consistently
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
    assertThat(result3).isTrue();
  }

  // Test 14: isAlreadyBuilt should work with numeric string node IDs
  @Test
  void isAlreadyBuilt_withNumericStrings_shouldWork() {
    // Given: Builders with numeric string node IDs
    AbstractFlowNodeBuilder<?, ?> builder1 = processBuilder.startEvent();
    AbstractFlowNodeBuilder<?, ?> builder2 = processBuilder.startEvent();
    context.addNodeBuilder("123", builder1);
    context.addNodeBuilder("456", builder2);

    // When: Checking if they are already built
    boolean result123 = context.isAlreadyBuilt("123");
    boolean result456 = context.isAlreadyBuilt("456");
    boolean result789 = context.isAlreadyBuilt("789");

    // Then: Should return true for added, false for non-added
    assertThat(result123).isTrue();
    assertThat(result456).isTrue();
    assertThat(result789).isFalse();
  }

  // Test 15: isAlreadyBuilt should handle long node IDs
  @Test
  void isAlreadyBuilt_withLongNodeId_shouldWork() {
    // Given: A builder with a very long node ID
    String longNodeId = "this_is_a_very_long_node_id_with_many_characters_to_test_that_the_method_handles_long_strings_properly";
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addNodeBuilder(longNodeId, builder);

    // When: Checking if it is already built
    boolean result = context.isAlreadyBuilt(longNodeId);

    // Then: Should return true
    assertThat(result).isTrue();
  }

  // Test 16: isAlreadyBuilt should work independently across different context instances
  @Test
  void isAlreadyBuilt_multipleContexts_shouldBeIndependent() {
    // Given: Two different contexts
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    BpmnModelInstance modelInstance2 = Bpmn.createExecutableProcess("testProcess2").done();
    ProcessBuilder processBuilder2 = modelInstance2.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);

    // When: Adding a builder to context1 only
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addNodeBuilder("node1", builder);

    // Then: context1 should have it, context2 should not
    assertThat(context.isAlreadyBuilt("node1")).isTrue();
    assertThat(context2.isAlreadyBuilt("node1")).isFalse();
  }

  // Test 17: isAlreadyBuilt should return false for null node ID
  @Test
  void isAlreadyBuilt_withNullNodeId_shouldReturnFalse() {
    // When: Checking with null node ID
    boolean result = context.isAlreadyBuilt(null);

    // Then: Should return false (map.containsKey(null) when null key was never added)
    assertThat(result).isFalse();
  }

  // Test 18: isAlreadyBuilt should return true if null key was explicitly added
  @Test
  void isAlreadyBuilt_afterAddingNullKey_shouldReturnTrue() {
    // Given: A builder is added with null key
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addNodeBuilder(null, builder);

    // When: Checking if null key is already built
    boolean result = context.isAlreadyBuilt(null);

    // Then: Should return true (because null key exists in the map)
    assertThat(result).isTrue();
  }

  // Test 19: isAlreadyBuilt should handle rapid sequential additions
  @Test
  void isAlreadyBuilt_withSequentialAdditions_shouldWork() {
    // Given: Multiple sequential additions
    for (int i = 0; i < 10; i++) {
      AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent("event" + i);
      context.addNodeBuilder("node" + i, builder);
    }

    // When: Checking all nodes
    boolean allPresent = true;
    for (int i = 0; i < 10; i++) {
      if (!context.isAlreadyBuilt("node" + i)) {
        allPresent = false;
        break;
      }
    }

    // Then: All should be present
    assertThat(allPresent).isTrue();
    assertThat(context.isAlreadyBuilt("node10")).isFalse(); // node10 was never added
  }

  // Test 20: isAlreadyBuilt should differentiate between similar node IDs
  @Test
  void isAlreadyBuilt_withSimilarNodeIds_shouldDifferentiate() {
    // Given: Builders with similar but different node IDs
    AbstractFlowNodeBuilder<?, ?> builder1 = processBuilder.startEvent();
    AbstractFlowNodeBuilder<?, ?> builder2 = processBuilder.startEvent();
    context.addNodeBuilder("node", builder1);
    context.addNodeBuilder("node1", builder2);

    // When: Checking if they are already built
    boolean resultNode = context.isAlreadyBuilt("node");
    boolean resultNode1 = context.isAlreadyBuilt("node1");
    boolean resultNode2 = context.isAlreadyBuilt("node2");

    // Then: Should differentiate correctly
    assertThat(resultNode).isTrue();
    assertThat(resultNode1).isTrue();
    assertThat(resultNode2).isFalse();
  }
}
