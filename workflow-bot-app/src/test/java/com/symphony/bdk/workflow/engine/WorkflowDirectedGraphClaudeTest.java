package com.symphony.bdk.workflow.engine;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class WorkflowDirectedGraphClaudeTest {

  // Tests for constructor WorkflowDirectedGraph(String workflowId, Long version)

  @Test
  void constructor_withVersionShouldCreateNonNullInstance() {
    // Given: A workflow ID and version
    String workflowId = "test-workflow";
    Long version = 1L;

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId, version);

    // Then: The instance should not be null
    assertThat(graph).isNotNull();
  }

  @Test
  void constructor_withVersionShouldSetWorkflowId() {
    // Given: A workflow ID and version
    String workflowId = "test-workflow";
    Long version = 1L;

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId, version);

    // Then: The workflow ID should be set correctly
    assertThat(graph.getWorkflowId()).isEqualTo(workflowId);
  }

  @Test
  void constructor_withVersionShouldSetVersion() {
    // Given: A workflow ID and version
    String workflowId = "test-workflow";
    Long version = 1L;

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId, version);

    // Then: The version should be set correctly
    assertThat(graph.getVersion()).isEqualTo(version);
  }

  @Test
  void constructor_withVersionShouldInitializeEmptyCollections() {
    // Given: A workflow ID and version
    String workflowId = "test-workflow";
    Long version = 1L;

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId, version);

    // Then: All collections should be initialized and empty
    assertThat(graph.getDictionary()).isNotNull().isEmpty();
    assertThat(graph.getParents()).isNotNull().isEmpty();
    assertThat(graph.getStartEvents()).isNotNull().isEmpty();
    assertThat(graph.getVariables()).isNotNull().isEmpty();
  }

  @Test
  void constructor_withVersionShouldAcceptNullVersion() {
    // Given: A workflow ID and null version
    String workflowId = "test-workflow";
    Long version = null;

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId, version);

    // Then: The instance should be created with null version
    assertThat(graph.getWorkflowId()).isEqualTo(workflowId);
    assertThat(graph.getVersion()).isNull();
  }

  // Tests for constructor WorkflowDirectedGraph(String workflowId)

  @Test
  void constructor_withoutVersionShouldCreateNonNullInstance() {
    // Given: A workflow ID
    String workflowId = "test-workflow";

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId);

    // Then: The instance should not be null
    assertThat(graph).isNotNull();
  }

  @Test
  void constructor_withoutVersionShouldSetWorkflowId() {
    // Given: A workflow ID
    String workflowId = "test-workflow";

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId);

    // Then: The workflow ID should be set correctly
    assertThat(graph.getWorkflowId()).isEqualTo(workflowId);
  }

  @Test
  void constructor_withoutVersionShouldSetVersionToNull() {
    // Given: A workflow ID
    String workflowId = "test-workflow";

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId);

    // Then: The version should be null
    assertThat(graph.getVersion()).isNull();
  }

  @Test
  void constructor_withoutVersionShouldInitializeEmptyCollections() {
    // Given: A workflow ID
    String workflowId = "test-workflow";

    // When: Creating a new WorkflowDirectedGraph instance
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph(workflowId);

    // Then: All collections should be initialized and empty
    assertThat(graph.getDictionary()).isNotNull().isEmpty();
    assertThat(graph.getParents()).isNotNull().isEmpty();
    assertThat(graph.getStartEvents()).isNotNull().isEmpty();
    assertThat(graph.getVariables()).isNotNull().isEmpty();
  }

  // Tests for addParent(String id, String parent)

  @Test
  void addParent_shouldAddParentToNewId() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    String parentId = "parent1";

    // When: Adding a parent to a node
    graph.addParent(nodeId, parentId);

    // Then: The parent should be added to the node
    assertThat(graph.getParents(nodeId)).containsExactly(parentId);
  }

  @Test
  void addParent_shouldAddMultipleParentsToSameId() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    String parent1 = "parent1";
    String parent2 = "parent2";

    // When: Adding multiple parents to the same node
    graph.addParent(nodeId, parent1);
    graph.addParent(nodeId, parent2);

    // Then: Both parents should be present
    assertThat(graph.getParents(nodeId)).containsExactlyInAnyOrder(parent1, parent2);
  }

  @Test
  void addParent_shouldNotAddDuplicateParent() {
    // Given: A workflow directed graph with a parent already added
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    String parentId = "parent1";
    graph.addParent(nodeId, parentId);

    // When: Adding the same parent again
    graph.addParent(nodeId, parentId);

    // Then: The parent should only appear once
    assertThat(graph.getParents(nodeId)).containsExactly(parentId);
  }

  @Test
  void addParent_shouldCreateSeparateParentSetsForDifferentNodes() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");

    // When: Adding parents to different nodes
    graph.addParent("node1", "parent1");
    graph.addParent("node2", "parent2");

    // Then: Each node should have its own parent set
    assertThat(graph.getParents("node1")).containsExactly("parent1");
    assertThat(graph.getParents("node2")).containsExactly("parent2");
  }

  // Tests for addStartEvent(String startEvent)

  @Test
  void addStartEvent_shouldAddEventToEmptyList() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String startEvent = "event1";

    // When: Adding a start event
    graph.addStartEvent(startEvent);

    // Then: The start event should be in the list
    assertThat(graph.getStartEvents()).containsExactly(startEvent);
  }

  @Test
  void addStartEvent_shouldAddMultipleEvents() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String event1 = "event1";
    String event2 = "event2";

    // When: Adding multiple start events
    graph.addStartEvent(event1);
    graph.addStartEvent(event2);

    // Then: Both events should be in the list in order
    assertThat(graph.getStartEvents()).containsExactly(event1, event2);
  }

  @Test
  void addStartEvent_shouldAllowDuplicateEvents() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String event = "event1";

    // When: Adding the same event twice
    graph.addStartEvent(event);
    graph.addStartEvent(event);

    // Then: The event should appear twice
    assertThat(graph.getStartEvents()).containsExactly(event, event);
  }

  // Tests for registerToDictionary(String id, WorkflowNode node)

  @Test
  void registerToDictionary_shouldAddNodeToDictionary() {
    // Given: A workflow directed graph and a workflow node
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    WorkflowNode node = new WorkflowNode().id(nodeId);

    // When: Registering the node to the dictionary
    graph.registerToDictionary(nodeId, node);

    // Then: The node should be retrievable from the dictionary
    assertThat(graph.getDictionary()).containsKey(nodeId);
    assertThat(graph.getDictionary().get(nodeId)).isEqualTo(node);
  }

  @Test
  void registerToDictionary_shouldReplaceExistingNode() {
    // Given: A workflow directed graph with a node already registered
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    WorkflowNode node1 = new WorkflowNode().id("first");
    WorkflowNode node2 = new WorkflowNode().id("second");
    graph.registerToDictionary(nodeId, node1);

    // When: Registering a new node with the same ID
    graph.registerToDictionary(nodeId, node2);

    // Then: The new node should replace the old one
    assertThat(graph.getDictionary().get(nodeId)).isEqualTo(node2);
    assertThat(graph.getDictionary().get(nodeId).getId()).isEqualTo("second");
  }

  @Test
  void registerToDictionary_shouldHandleMultipleNodes() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    WorkflowNode node1 = new WorkflowNode().id("node1");
    WorkflowNode node2 = new WorkflowNode().id("node2");

    // When: Registering multiple nodes
    graph.registerToDictionary("node1", node1);
    graph.registerToDictionary("node2", node2);

    // Then: Both nodes should be in the dictionary
    assertThat(graph.getDictionary()).hasSize(2);
    assertThat(graph.getDictionary().get("node1")).isEqualTo(node1);
    assertThat(graph.getDictionary().get("node2")).isEqualTo(node2);
  }

  // Tests for isRegistered(String id)

  @Test
  void isRegistered_shouldReturnFalseForUnregisteredId() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");

    // When: Checking if an ID is registered
    boolean result = graph.isRegistered("node1");

    // Then: It should return false
    assertThat(result).isFalse();
  }

  @Test
  void isRegistered_shouldReturnTrueForRegisteredId() {
    // Given: A workflow directed graph with a registered node
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    WorkflowNode node = new WorkflowNode().id(nodeId);
    graph.registerToDictionary(nodeId, node);

    // When: Checking if the ID is registered
    boolean result = graph.isRegistered(nodeId);

    // Then: It should return true
    assertThat(result).isTrue();
  }

  @Test
  void isRegistered_shouldReturnFalseAfterRegistrationOfDifferentId() {
    // Given: A workflow directed graph with a registered node
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    graph.registerToDictionary("node1", new WorkflowNode().id("node1"));

    // When: Checking for a different ID
    boolean result = graph.isRegistered("node2");

    // Then: It should return false
    assertThat(result).isFalse();
  }

  // Tests for getChildren(String id)

  @Test
  void getChildren_shouldCreateNewNodeChildrenForNewId() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";

    // When: Getting children for a new ID
    WorkflowDirectedGraph.NodeChildren children = graph.getChildren(nodeId);

    // Then: A new NodeChildren instance should be created and returned
    assertThat(children).isNotNull();
    assertThat(children.isEmpty()).isTrue();
  }

  @Test
  void getChildren_shouldReturnSameInstanceOnMultipleCalls() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";

    // When: Getting children multiple times
    WorkflowDirectedGraph.NodeChildren children1 = graph.getChildren(nodeId);
    WorkflowDirectedGraph.NodeChildren children2 = graph.getChildren(nodeId);

    // Then: The same instance should be returned
    assertThat(children1).isSameAs(children2);
  }

  @Test
  void getChildren_shouldAllowModificationOfReturnedChildren() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";

    // When: Getting children and modifying them
    WorkflowDirectedGraph.NodeChildren children = graph.getChildren(nodeId);
    children.addChild("child1");

    // Then: The modification should persist
    WorkflowDirectedGraph.NodeChildren retrievedChildren = graph.getChildren(nodeId);
    assertThat(retrievedChildren.getChildren()).containsExactly("child1");
  }

  @Test
  void getChildren_shouldCreateSeparateChildrenForDifferentIds() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");

    // When: Getting children for different IDs
    WorkflowDirectedGraph.NodeChildren children1 = graph.getChildren("node1");
    WorkflowDirectedGraph.NodeChildren children2 = graph.getChildren("node2");
    children1.addChild("child1");
    children2.addChild("child2");

    // Then: Each ID should have its own children
    assertThat(graph.getChildren("node1").getChildren()).containsExactly("child1");
    assertThat(graph.getChildren("node2").getChildren()).containsExactly("child2");
  }

  // Tests for readWorkflowNode(String id)

  @Test
  void readWorkflowNode_shouldReturnNullForUnregisteredId() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");

    // When: Reading a node that doesn't exist
    WorkflowNode node = graph.readWorkflowNode("node1");

    // Then: It should return null
    assertThat(node).isNull();
  }

  @Test
  void readWorkflowNode_shouldReturnRegisteredNode() {
    // Given: A workflow directed graph with a registered node
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    WorkflowNode expectedNode = new WorkflowNode().id(nodeId);
    graph.registerToDictionary(nodeId, expectedNode);

    // When: Reading the node
    WorkflowNode node = graph.readWorkflowNode(nodeId);

    // Then: It should return the registered node
    assertThat(node).isEqualTo(expectedNode);
  }

  @Test
  void readWorkflowNode_shouldReturnNullForDifferentId() {
    // Given: A workflow directed graph with a registered node
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    graph.registerToDictionary("node1", new WorkflowNode().id("node1"));

    // When: Reading a different node ID
    WorkflowNode node = graph.readWorkflowNode("node2");

    // Then: It should return null
    assertThat(node).isNull();
  }

  // Tests for readChildren(String id)

  @Test
  void readChildren_shouldReturnNullForIdWithoutChildren() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");

    // When: Reading children that don't exist
    WorkflowDirectedGraph.NodeChildren children = graph.readChildren("node1");

    // Then: It should return null
    assertThat(children).isNull();
  }

  @Test
  void readChildren_shouldReturnChildrenAfterGetChildren() {
    // Given: A workflow directed graph with children created via getChildren
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    WorkflowDirectedGraph.NodeChildren expected = graph.getChildren(nodeId);
    expected.addChild("child1");

    // When: Reading children
    WorkflowDirectedGraph.NodeChildren children = graph.readChildren(nodeId);

    // Then: It should return the same children instance
    assertThat(children).isSameAs(expected);
    assertThat(children.getChildren()).containsExactly("child1");
  }

  @Test
  void readChildren_shouldReturnNullForDifferentId() {
    // Given: A workflow directed graph with children for one ID
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    graph.getChildren("node1").addChild("child1");

    // When: Reading children for a different ID
    WorkflowDirectedGraph.NodeChildren children = graph.readChildren("node2");

    // Then: It should return null
    assertThat(children).isNull();
  }

  // Tests for hasSeenBefore(String id)

  @Test
  void hasSeenBefore_shouldReturnFalseForIdWithoutParents() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");

    // When: Checking if an ID has been seen before
    boolean result = graph.hasSeenBefore("node1");

    // Then: It should return false
    assertThat(result).isFalse();
  }

  @Test
  void hasSeenBefore_shouldReturnTrueForIdWithParents() {
    // Given: A workflow directed graph with a parent added
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    graph.addParent(nodeId, "parent1");

    // When: Checking if the ID has been seen before
    boolean result = graph.hasSeenBefore(nodeId);

    // Then: It should return true
    assertThat(result).isTrue();
  }

  @Test
  void hasSeenBefore_shouldReturnFalseForDifferentId() {
    // Given: A workflow directed graph with a parent added to one ID
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    graph.addParent("node1", "parent1");

    // When: Checking a different ID
    boolean result = graph.hasSeenBefore("node2");

    // Then: It should return false
    assertThat(result).isFalse();
  }

  // Tests for getParents(String id)

  @Test
  void getParents_shouldReturnEmptyListForIdWithoutParents() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");

    // When: Getting parents for an ID without parents
    List<String> parents = graph.getParents("node1");

    // Then: It should return an empty list
    assertThat(parents).isNotNull().isEmpty();
  }

  @Test
  void getParents_shouldReturnListOfParents() {
    // Given: A workflow directed graph with parents added
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    graph.addParent(nodeId, "parent1");
    graph.addParent(nodeId, "parent2");

    // When: Getting parents
    List<String> parents = graph.getParents(nodeId);

    // Then: It should return all parents
    assertThat(parents).containsExactlyInAnyOrder("parent1", "parent2");
  }

  @Test
  void getParents_shouldReturnNewListInstance() {
    // Given: A workflow directed graph with parents added
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    graph.addParent(nodeId, "parent1");

    // When: Getting parents twice
    List<String> parents1 = graph.getParents(nodeId);
    List<String> parents2 = graph.getParents(nodeId);

    // Then: Different list instances should be returned
    assertThat(parents1).isNotSameAs(parents2);
    assertThat(parents1).isEqualTo(parents2);
  }

  @Test
  void getParents_shouldReturnImmutableCopy() {
    // Given: A workflow directed graph with parents added
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    String nodeId = "node1";
    graph.addParent(nodeId, "parent1");

    // When: Getting parents and modifying the returned list
    List<String> parents = graph.getParents(nodeId);
    parents.add("parent2");

    // Then: The modification should not affect the internal state
    assertThat(graph.getParents(nodeId)).containsExactly("parent1");
  }

  @Test
  void getParents_shouldReturnEmptyListForDifferentId() {
    // Given: A workflow directed graph with parents for one ID
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("test-workflow");
    graph.addParent("node1", "parent1");

    // When: Getting parents for a different ID
    List<String> parents = graph.getParents("node2");

    // Then: It should return an empty list
    assertThat(parents).isEmpty();
  }

  // Integration tests for complex scenarios

  @Test
  void integration_shouldHandleComplexWorkflowGraph() {
    // Given: A workflow directed graph representing a complex workflow
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("complex-workflow", 2L);

    // When: Building a complex graph structure
    graph.addStartEvent("start1");
    graph.addStartEvent("start2");

    WorkflowNode node1 = new WorkflowNode().id("node1");
    WorkflowNode node2 = new WorkflowNode().id("node2");
    WorkflowNode node3 = new WorkflowNode().id("node3");

    graph.registerToDictionary("node1", node1);
    graph.registerToDictionary("node2", node2);
    graph.registerToDictionary("node3", node3);

    graph.addParent("node2", "node1");
    graph.addParent("node3", "node1");
    graph.addParent("node3", "node2");

    graph.getChildren("node1").addChild("node2").addChild("node3");
    graph.getChildren("node2").addChild("node3");

    // Then: All relationships should be correctly maintained
    assertThat(graph.getWorkflowId()).isEqualTo("complex-workflow");
    assertThat(graph.getVersion()).isEqualTo(2L);
    assertThat(graph.getStartEvents()).containsExactly("start1", "start2");
    assertThat(graph.isRegistered("node1")).isTrue();
    assertThat(graph.isRegistered("node2")).isTrue();
    assertThat(graph.isRegistered("node3")).isTrue();
    assertThat(graph.getParents("node2")).containsExactly("node1");
    assertThat(graph.getParents("node3")).containsExactlyInAnyOrder("node1", "node2");
    assertThat(graph.getChildren("node1").getChildren()).containsExactly("node2", "node3");
    assertThat(graph.getChildren("node2").getChildren()).containsExactly("node3");
    assertThat(graph.hasSeenBefore("node2")).isTrue();
    assertThat(graph.hasSeenBefore("node3")).isTrue();
  }

  @Test
  void integration_shouldSupportGatewayConfiguration() {
    // Given: A workflow directed graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("gateway-workflow");

    // When: Setting up children with different gateways
    graph.getChildren("node1")
        .gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE)
        .addChild("node2")
        .addChild("node3");

    graph.getChildren("node2")
        .gateway(WorkflowDirectedGraph.Gateway.PARALLEL)
        .addChild("node4")
        .addChild("node5");

    // Then: Gateway types should be preserved
    assertThat(graph.getChildren("node1").getGateway())
        .isEqualTo(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    assertThat(graph.getChildren("node2").getGateway())
        .isEqualTo(WorkflowDirectedGraph.Gateway.PARALLEL);
  }

  // Tests for Gateway enum

  @Test
  void gateway_valuesShouldReturnAllEnumConstants() {
    // When: Calling values() on Gateway enum
    WorkflowDirectedGraph.Gateway[] values = WorkflowDirectedGraph.Gateway.values();

    // Then: All three enum constants should be returned
    assertThat(values).hasSize(3);
    assertThat(values).containsExactly(
        WorkflowDirectedGraph.Gateway.EXCLUSIVE,
        WorkflowDirectedGraph.Gateway.EVENT_BASED,
        WorkflowDirectedGraph.Gateway.PARALLEL
    );
  }

  @Test
  void gateway_valuesShouldReturnArrayInDeclarationOrder() {
    // When: Calling values() on Gateway enum
    WorkflowDirectedGraph.Gateway[] values = WorkflowDirectedGraph.Gateway.values();

    // Then: The array should be in declaration order
    assertThat(values[0]).isEqualTo(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    assertThat(values[1]).isEqualTo(WorkflowDirectedGraph.Gateway.EVENT_BASED);
    assertThat(values[2]).isEqualTo(WorkflowDirectedGraph.Gateway.PARALLEL);
  }

  @Test
  void gateway_valuesShouldReturnNewArrayOnEachCall() {
    // When: Calling values() twice
    WorkflowDirectedGraph.Gateway[] values1 = WorkflowDirectedGraph.Gateway.values();
    WorkflowDirectedGraph.Gateway[] values2 = WorkflowDirectedGraph.Gateway.values();

    // Then: Different array instances should be returned
    assertThat(values1).isNotSameAs(values2);
    assertThat(values1).isEqualTo(values2);
  }

  @Test
  void gateway_valuesShouldReturnDefensiveCopy() {
    // Given: Getting the values array
    WorkflowDirectedGraph.Gateway[] values = WorkflowDirectedGraph.Gateway.values();

    // When: Modifying the returned array
    WorkflowDirectedGraph.Gateway original = values[0];
    values[0] = null;

    // Then: The modification should not affect subsequent calls
    WorkflowDirectedGraph.Gateway[] newValues = WorkflowDirectedGraph.Gateway.values();
    assertThat(newValues[0]).isEqualTo(original);
  }

  @Test
  void gateway_valueOfShouldReturnExclusiveForExclusiveString() {
    // When: Calling valueOf with "EXCLUSIVE"
    WorkflowDirectedGraph.Gateway gateway = WorkflowDirectedGraph.Gateway.valueOf("EXCLUSIVE");

    // Then: It should return the EXCLUSIVE enum constant
    assertThat(gateway).isEqualTo(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
  }

  @Test
  void gateway_valueOfShouldReturnEventBasedForEventBasedString() {
    // When: Calling valueOf with "EVENT_BASED"
    WorkflowDirectedGraph.Gateway gateway = WorkflowDirectedGraph.Gateway.valueOf("EVENT_BASED");

    // Then: It should return the EVENT_BASED enum constant
    assertThat(gateway).isEqualTo(WorkflowDirectedGraph.Gateway.EVENT_BASED);
  }

  @Test
  void gateway_valueOfShouldReturnParallelForParallelString() {
    // When: Calling valueOf with "PARALLEL"
    WorkflowDirectedGraph.Gateway gateway = WorkflowDirectedGraph.Gateway.valueOf("PARALLEL");

    // Then: It should return the PARALLEL enum constant
    assertThat(gateway).isEqualTo(WorkflowDirectedGraph.Gateway.PARALLEL);
  }

  @Test
  void gateway_valueOfShouldBeCaseSensitive() {
    // When/Then: Calling valueOf with lowercase should throw exception
    assertThatCode(() -> WorkflowDirectedGraph.Gateway.valueOf("exclusive"))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatCode(() -> WorkflowDirectedGraph.Gateway.valueOf("event_based"))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatCode(() -> WorkflowDirectedGraph.Gateway.valueOf("parallel"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void gateway_valueOfShouldThrowExceptionForInvalidName() {
    // When/Then: Calling valueOf with an invalid name should throw IllegalArgumentException
    assertThatCode(() -> WorkflowDirectedGraph.Gateway.valueOf("INVALID"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void gateway_valueOfShouldThrowExceptionForNull() {
    // When/Then: Calling valueOf with null should throw NullPointerException
    assertThatCode(() -> WorkflowDirectedGraph.Gateway.valueOf(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void gateway_valueOfShouldThrowExceptionForEmptyString() {
    // When/Then: Calling valueOf with empty string should throw IllegalArgumentException
    assertThatCode(() -> WorkflowDirectedGraph.Gateway.valueOf(""))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void gateway_enumConstantsShouldHaveCorrectNames() {
    // When: Getting the enum constants
    WorkflowDirectedGraph.Gateway exclusive = WorkflowDirectedGraph.Gateway.EXCLUSIVE;
    WorkflowDirectedGraph.Gateway eventBased = WorkflowDirectedGraph.Gateway.EVENT_BASED;
    WorkflowDirectedGraph.Gateway parallel = WorkflowDirectedGraph.Gateway.PARALLEL;

    // Then: Their names should match their declaration
    assertThat(exclusive.name()).isEqualTo("EXCLUSIVE");
    assertThat(eventBased.name()).isEqualTo("EVENT_BASED");
    assertThat(parallel.name()).isEqualTo("PARALLEL");
  }

  @Test
  void gateway_enumConstantsShouldHaveCorrectOrdinals() {
    // When: Getting the enum constants
    WorkflowDirectedGraph.Gateway exclusive = WorkflowDirectedGraph.Gateway.EXCLUSIVE;
    WorkflowDirectedGraph.Gateway eventBased = WorkflowDirectedGraph.Gateway.EVENT_BASED;
    WorkflowDirectedGraph.Gateway parallel = WorkflowDirectedGraph.Gateway.PARALLEL;

    // Then: Their ordinals should match their declaration order
    assertThat(exclusive.ordinal()).isEqualTo(0);
    assertThat(eventBased.ordinal()).isEqualTo(1);
    assertThat(parallel.ordinal()).isEqualTo(2);
  }

  @Test
  void gateway_valueOfAndValuesShouldBeConsistent() {
    // Given: All enum constants from values()
    WorkflowDirectedGraph.Gateway[] values = WorkflowDirectedGraph.Gateway.values();

    // When/Then: valueOf should return the same instance for each name
    for (WorkflowDirectedGraph.Gateway gateway : values) {
      assertThat(WorkflowDirectedGraph.Gateway.valueOf(gateway.name())).isSameAs(gateway);
    }
  }

  // Tests for NodeChildren class

  // Tests for NodeChildren() no-arg constructor
  @Test
  void nodeChildren_noArgConstructorShouldCreateInstance() {
    // When: Creating a NodeChildren instance with no-arg constructor
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // Then: The instance should be created
    assertThat(nodeChildren).isNotNull();
  }

  @Test
  void nodeChildren_noArgConstructorShouldInitializeEmptyChildren() {
    // When: Creating a NodeChildren instance with no-arg constructor
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // Then: The children list should be empty
    assertThat(nodeChildren.getChildren()).isNotNull().isEmpty();
  }

  @Test
  void nodeChildren_noArgConstructorShouldSetGatewayToNull() {
    // When: Creating a NodeChildren instance with no-arg constructor
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // Then: The gateway should be null
    assertThat(nodeChildren.getGateway()).isNull();
  }

  // Tests for NodeChildren(List<String> children) constructor
  @Test
  void nodeChildren_listConstructorShouldCreateInstance() {
    // Given: A list of children
    List<String> children = List.of("child1", "child2");

    // When: Creating a NodeChildren instance with the list
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(children);

    // Then: The instance should be created
    assertThat(nodeChildren).isNotNull();
  }

  @Test
  void nodeChildren_listConstructorShouldSetChildren() {
    // Given: A list of children
    List<String> children = List.of("child1", "child2");

    // When: Creating a NodeChildren instance with the list
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(children);

    // Then: The children should be set
    assertThat(nodeChildren.getChildren()).containsExactly("child1", "child2");
  }

  @Test
  void nodeChildren_listConstructorShouldSetGatewayToNull() {
    // Given: A list of children
    List<String> children = List.of("child1");

    // When: Creating a NodeChildren instance with the list
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(children);

    // Then: The gateway should be null
    assertThat(nodeChildren.getGateway()).isNull();
  }

  @Test
  void nodeChildren_listConstructorShouldAcceptEmptyList() {
    // Given: An empty list
    List<String> children = List.of();

    // When: Creating a NodeChildren instance with the empty list
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(children);

    // Then: The children should be empty
    assertThat(nodeChildren.getChildren()).isEmpty();
  }

  // Tests for addChild(String child)
  @Test
  void nodeChildren_addChildShouldAddChildToEmptyList() {
    // Given: A NodeChildren instance with no children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Adding a child
    WorkflowDirectedGraph.NodeChildren result = nodeChildren.addChild("child1");

    // Then: The child should be added
    assertThat(nodeChildren.getChildren()).containsExactly("child1");
    assertThat(result).isSameAs(nodeChildren); // Check fluent interface
  }

  @Test
  void nodeChildren_addChildShouldAddMultipleChildren() {
    // Given: A NodeChildren instance
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Adding multiple children
    nodeChildren.addChild("child1").addChild("child2").addChild("child3");

    // Then: All children should be added in order
    assertThat(nodeChildren.getChildren()).containsExactly("child1", "child2", "child3");
  }

  @Test
  void nodeChildren_addChildShouldAllowDuplicates() {
    // Given: A NodeChildren instance with a child
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    // When: Adding the same child again
    nodeChildren.addChild("child1");

    // Then: The child should be added twice
    assertThat(nodeChildren.getChildren()).containsExactly("child1", "child1");
  }

  @Test
  void nodeChildren_addChildShouldReturnThis() {
    // Given: A NodeChildren instance
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Adding a child
    WorkflowDirectedGraph.NodeChildren result = nodeChildren.addChild("child1");

    // Then: The same instance should be returned (fluent interface)
    assertThat(result).isSameAs(nodeChildren);
  }

  // Tests for removeChild(String child)
  @Test
  void nodeChildren_removeChildShouldRemoveExistingChild() {
    // Given: A NodeChildren instance with children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1").addChild("child2");

    // When: Removing a child
    WorkflowDirectedGraph.NodeChildren result = nodeChildren.removeChild("child1");

    // Then: The child should be removed
    assertThat(nodeChildren.getChildren()).containsExactly("child2");
    assertThat(result).isSameAs(nodeChildren); // Check fluent interface
  }

  @Test
  void nodeChildren_removeChildShouldHandleNonExistentChild() {
    // Given: A NodeChildren instance with children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    // When: Removing a child that doesn't exist
    nodeChildren.removeChild("child2");

    // Then: The existing children should remain unchanged
    assertThat(nodeChildren.getChildren()).containsExactly("child1");
  }

  @Test
  void nodeChildren_removeChildShouldRemoveFirstOccurrenceOnly() {
    // Given: A NodeChildren instance with duplicate children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1").addChild("child1").addChild("child1");

    // When: Removing a child
    nodeChildren.removeChild("child1");

    // Then: Only the first occurrence should be removed
    assertThat(nodeChildren.getChildren()).containsExactly("child1", "child1");
  }

  @Test
  void nodeChildren_removeChildShouldReturnThis() {
    // Given: A NodeChildren instance with children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    // When: Removing a child
    WorkflowDirectedGraph.NodeChildren result = nodeChildren.removeChild("child1");

    // Then: The same instance should be returned (fluent interface)
    assertThat(result).isSameAs(nodeChildren);
  }

  @Test
  void nodeChildren_removeChildShouldLeaveListEmptyWhenLastChildRemoved() {
    // Given: A NodeChildren instance with one child
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    // When: Removing the last child
    nodeChildren.removeChild("child1");

    // Then: The children list should be empty
    assertThat(nodeChildren.getChildren()).isEmpty();
  }

  // Tests for gateway(Gateway gateway)
  @Test
  void nodeChildren_gatewayShouldSetGateway() {
    // Given: A NodeChildren instance
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Setting a gateway
    WorkflowDirectedGraph.NodeChildren result = nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);

    // Then: The gateway should be set
    assertThat(nodeChildren.getGateway()).isEqualTo(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    assertThat(result).isSameAs(nodeChildren); // Check fluent interface
  }

  @Test
  void nodeChildren_gatewayShouldSetParallelGateway() {
    // Given: A NodeChildren instance
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Setting a parallel gateway
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.PARALLEL);

    // Then: The gateway should be set to PARALLEL
    assertThat(nodeChildren.getGateway()).isEqualTo(WorkflowDirectedGraph.Gateway.PARALLEL);
  }

  @Test
  void nodeChildren_gatewayShouldSetEventBasedGateway() {
    // Given: A NodeChildren instance
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Setting an event-based gateway
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EVENT_BASED);

    // Then: The gateway should be set to EVENT_BASED
    assertThat(nodeChildren.getGateway()).isEqualTo(WorkflowDirectedGraph.Gateway.EVENT_BASED);
  }

  @Test
  void nodeChildren_gatewayShouldReplaceExistingGateway() {
    // Given: A NodeChildren instance with a gateway already set
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);

    // When: Setting a different gateway
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.PARALLEL);

    // Then: The gateway should be replaced
    assertThat(nodeChildren.getGateway()).isEqualTo(WorkflowDirectedGraph.Gateway.PARALLEL);
  }

  @Test
  void nodeChildren_gatewayShouldReturnThis() {
    // Given: A NodeChildren instance
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Setting a gateway
    WorkflowDirectedGraph.NodeChildren result = nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);

    // Then: The same instance should be returned (fluent interface)
    assertThat(result).isSameAs(nodeChildren);
  }

  @Test
  void nodeChildren_gatewayShouldAcceptNullGateway() {
    // Given: A NodeChildren instance with a gateway set
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);

    // When: Setting gateway to null
    nodeChildren.gateway(null);

    // Then: The gateway should be null
    assertThat(nodeChildren.getGateway()).isNull();
  }

  // Tests for isEmpty()
  @Test
  void nodeChildren_isEmptyShouldReturnTrueForEmptyChildren() {
    // Given: A NodeChildren instance with no children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Checking if empty
    boolean result = nodeChildren.isEmpty();

    // Then: It should return true
    assertThat(result).isTrue();
  }

  @Test
  void nodeChildren_isEmptyShouldReturnFalseForNonEmptyChildren() {
    // Given: A NodeChildren instance with children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    // When: Checking if empty
    boolean result = nodeChildren.isEmpty();

    // Then: It should return false
    assertThat(result).isFalse();
  }

  @Test
  void nodeChildren_isEmptyShouldReturnTrueAfterRemovingAllChildren() {
    // Given: A NodeChildren instance with one child
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    // When: Removing the child and checking if empty
    nodeChildren.removeChild("child1");
    boolean result = nodeChildren.isEmpty();

    // Then: It should return true
    assertThat(result).isTrue();
  }

  // Tests for isChildUnique()
  @Test
  void nodeChildren_isChildUniqueShouldReturnFalseForEmptyChildren() {
    // Given: A NodeChildren instance with no children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Checking if child is unique
    boolean result = nodeChildren.isChildUnique();

    // Then: It should return false
    assertThat(result).isFalse();
  }

  @Test
  void nodeChildren_isChildUniqueShouldReturnTrueForOneChild() {
    // Given: A NodeChildren instance with one child
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    // When: Checking if child is unique
    boolean result = nodeChildren.isChildUnique();

    // Then: It should return true
    assertThat(result).isTrue();
  }

  @Test
  void nodeChildren_isChildUniqueShouldReturnFalseForMultipleChildren() {
    // Given: A NodeChildren instance with multiple children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1").addChild("child2");

    // When: Checking if child is unique
    boolean result = nodeChildren.isChildUnique();

    // Then: It should return false
    assertThat(result).isFalse();
  }

  @Test
  void nodeChildren_isChildUniqueShouldReturnTrueAfterRemovingToOneChild() {
    // Given: A NodeChildren instance with multiple children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1").addChild("child2");

    // When: Removing children until one remains
    nodeChildren.removeChild("child2");
    boolean result = nodeChildren.isChildUnique();

    // Then: It should return true
    assertThat(result).isTrue();
  }

  // Tests for getUniqueChild()
  @Test
  void nodeChildren_getUniqueChildShouldReturnChildWhenUnique() {
    // Given: A NodeChildren instance with one child
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    // When: Getting the unique child
    String result = nodeChildren.getUniqueChild();

    // Then: The child should be returned
    assertThat(result).isEqualTo("child1");
  }

  @Test
  void nodeChildren_getUniqueChildShouldThrowExceptionWhenEmpty() {
    // Given: A NodeChildren instance with no children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When/Then: Getting unique child should throw IllegalStateException
    assertThatCode(() -> nodeChildren.getUniqueChild())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No children or not unique child");
  }

  @Test
  void nodeChildren_getUniqueChildShouldThrowExceptionWhenMultipleChildren() {
    // Given: A NodeChildren instance with multiple children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1").addChild("child2");

    // When/Then: Getting unique child should throw IllegalStateException
    assertThatCode(() -> nodeChildren.getUniqueChild())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No children or not unique child");
  }

  @Test
  void nodeChildren_getUniqueChildShouldWorkAfterRemovingToOneChild() {
    // Given: A NodeChildren instance with multiple children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1").addChild("child2");

    // When: Removing children until one remains and getting unique child
    nodeChildren.removeChild("child2");
    String result = nodeChildren.getUniqueChild();

    // Then: The remaining child should be returned
    assertThat(result).isEqualTo("child1");
  }

  // Integration tests for NodeChildren fluent interface
  @Test
  void nodeChildren_shouldSupportFluentInterfaceChaining() {
    // Given: A NodeChildren instance
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When: Chaining multiple operations
    nodeChildren
        .gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE)
        .addChild("child1")
        .addChild("child2")
        .addChild("child3")
        .removeChild("child2");

    // Then: All operations should be applied
    assertThat(nodeChildren.getGateway()).isEqualTo(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    assertThat(nodeChildren.getChildren()).containsExactly("child1", "child3");
    assertThat(nodeChildren.isEmpty()).isFalse();
    assertThat(nodeChildren.isChildUnique()).isFalse();
  }

  @Test
  void nodeChildren_allArgsConstructorShouldWorkCorrectly() {
    // Given: A gateway and a list of children
    WorkflowDirectedGraph.Gateway gateway = WorkflowDirectedGraph.Gateway.PARALLEL;
    List<String> children = new ArrayList<>(List.of("child1", "child2"));

    // When: Creating a NodeChildren instance with all-args constructor
    WorkflowDirectedGraph.NodeChildren nodeChildren =
        new WorkflowDirectedGraph.NodeChildren(gateway, children);

    // Then: Both gateway and children should be set
    assertThat(nodeChildren.getGateway()).isEqualTo(gateway);
    assertThat(nodeChildren.getChildren()).containsExactly("child1", "child2");
  }
}
