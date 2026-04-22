package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class WorkflowDirectedGraphTest {

  @Test
  @DisplayName("Test new WorkflowDirectedGraph(String, Long); sets workflowId and version")
  void testConstructorWithVersion() {
    // Arrange and Act
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 42L);

    // Assert
    assertEquals("wf1", graph.getWorkflowId());
    assertEquals(42L, graph.getVersion().longValue());
    assertTrue(graph.getDictionary().isEmpty());
    assertTrue(graph.getParents().isEmpty());
    assertTrue(graph.getStartEvents().isEmpty());
    assertTrue(graph.getVariables().isEmpty());
  }

  @Test
  @DisplayName("Test new WorkflowDirectedGraph(String); sets workflowId and version is null")
  void testConstructorWithoutVersion() {
    // Arrange and Act
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");

    // Assert
    assertEquals("wf1", graph.getWorkflowId());
    assertNull(graph.getVersion());
    assertTrue(graph.getDictionary().isEmpty());
    assertTrue(graph.getParents().isEmpty());
    assertTrue(graph.getStartEvents().isEmpty());
    assertTrue(graph.getVariables().isEmpty());
  }

  @Test
  @DisplayName("Test addParent(String, String); adds parent to map")
  void testAddParent() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");

    // Act
    graph.addParent("child", "parent");

    // Assert
    assertTrue(graph.getParents().containsKey("child"));
    assertTrue(graph.getParents().get("child").contains("parent"));
  }

  @Test
  @DisplayName("Test addStartEvent(String); adds event to list")
  void testAddStartEvent() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");

    // Act
    graph.addStartEvent("startEvent");

    // Assert
    List<String> startEvents = graph.getStartEvents();
    assertEquals(1, startEvents.size());
    assertEquals("startEvent", startEvents.get(0));
  }

  @Test
  @DisplayName("Test registerToDictionary(String, WorkflowNode); registers node")
  void testRegisterToDictionary() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");
    WorkflowNode node = new WorkflowNode();

    // Act
    graph.registerToDictionary("nodeId", node);

    // Assert
    assertTrue(graph.getDictionary().containsKey("nodeId"));
    assertEquals(node, graph.getDictionary().get("nodeId"));
  }

  @Test
  @DisplayName("Test isRegistered(String); returns true when registered")
  void testIsRegistered_whenRegistered() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");
    graph.registerToDictionary("nodeId", new WorkflowNode());

    // Act and Assert
    assertTrue(graph.isRegistered("nodeId"));
  }

  @Test
  @DisplayName("Test isRegistered(String); returns false when not registered")
  void testIsRegistered_whenNotRegistered() {
    // Arrange and Act and Assert
    assertFalse(new WorkflowDirectedGraph("wf1").isRegistered("nodeId"));
  }

  @Test
  @DisplayName("Test getChildren(String); creates and returns NodeChildren")
  void testGetChildren() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");

    // Act
    NodeChildren children = graph.getChildren("nodeId");

    // Assert
    assertNotNull(children);
    assertTrue(children.isEmpty());
  }

  @Test
  @DisplayName("Test readWorkflowNode(String); returns node when registered")
  void testReadWorkflowNode_whenRegistered() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");
    WorkflowNode node = new WorkflowNode();
    graph.registerToDictionary("nodeId", node);

    // Act and Assert
    assertEquals(node, graph.readWorkflowNode("nodeId"));
  }

  @Test
  @DisplayName("Test readWorkflowNode(String); returns null when not registered")
  void testReadWorkflowNode_whenNotRegistered() {
    // Arrange and Act and Assert
    assertNull(new WorkflowDirectedGraph("wf1").readWorkflowNode("nodeId"));
  }

  @Test
  @DisplayName("Test readChildren(String); returns null when no children added")
  void testReadChildren_whenNone() {
    // Arrange and Act and Assert
    assertNull(new WorkflowDirectedGraph("wf1").readChildren("nodeId"));
  }

  @Test
  @DisplayName("Test readChildren(String); returns NodeChildren when children added via getChildren")
  void testReadChildren_afterGetChildren() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");
    graph.getChildren("nodeId").addChild("child1");

    // Act
    NodeChildren children = graph.readChildren("nodeId");

    // Assert
    assertNotNull(children);
    assertFalse(children.isEmpty());
  }

  @Test
  @DisplayName("Test hasSeenBefore(String); returns false when no parent added")
  void testHasSeenBefore_false() {
    // Arrange and Act and Assert
    assertFalse(new WorkflowDirectedGraph("wf1").hasSeenBefore("nodeId"));
  }

  @Test
  @DisplayName("Test hasSeenBefore(String); returns true when parent added")
  void testHasSeenBefore_true() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");
    graph.addParent("nodeId", "parent");

    // Act and Assert
    assertTrue(graph.hasSeenBefore("nodeId"));
  }

  @Test
  @DisplayName("Test getParents(String); returns empty list when no parent added")
  void testGetParents_empty() {
    // Arrange and Act
    List<String> parents = new WorkflowDirectedGraph("wf1").getParents("nodeId");

    // Assert
    assertNotNull(parents);
    assertTrue(parents.isEmpty());
  }

  @Test
  @DisplayName("Test getParents(String); returns parent list when parent added")
  void testGetParents_withParent() {
    // Arrange
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1");
    graph.addParent("nodeId", "parent1");

    // Act
    List<String> parents = graph.getParents("nodeId");

    // Assert
    assertEquals(1, parents.size());
    assertTrue(parents.contains("parent1"));
  }
}
