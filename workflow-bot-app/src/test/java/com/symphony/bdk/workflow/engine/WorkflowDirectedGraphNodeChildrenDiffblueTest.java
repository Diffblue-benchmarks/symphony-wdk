package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.Gateway;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class WorkflowDirectedGraphNodeChildrenDiffblueTest {

  /**
   * Test {@link NodeChildren#NodeChildren(List)}.
   *
   * <p>Method under test: {@link NodeChildren#NodeChildren(List)}
   */
  @Test
  @DisplayName("Test NodeChildren(List); then children list is same instance")
  void testNodeChildrenConstructorWithList() {
    // Arrange
    ArrayList<String> children = new ArrayList<>();
    children.add("Child1");

    // Act
    NodeChildren nodeChildren = new NodeChildren(children);

    // Assert
    assertSame(children, nodeChildren.getChildren());
    assertEquals(1, nodeChildren.getChildren().size());
    assertFalse(nodeChildren.isEmpty());
    assertTrue(nodeChildren.isChildUnique());
  }

  /**
   * Test {@link NodeChildren#NodeChildren(List)} with empty list.
   *
   * <p>Method under test: {@link NodeChildren#NodeChildren(List)}
   */
  @Test
  @DisplayName("Test NodeChildren(List); given empty list; then isEmpty returns true")
  void testNodeChildrenConstructorWithEmptyList() {
    // Arrange
    ArrayList<String> children = new ArrayList<>();

    // Act
    NodeChildren nodeChildren = new NodeChildren(children);

    // Assert
    assertTrue(nodeChildren.isEmpty());
    assertFalse(nodeChildren.isChildUnique());
    assertSame(children, nodeChildren.getChildren());
  }

  /**
   * Test {@link NodeChildren#addChild(String)}.
   *
   * <p>Method under test: {@link NodeChildren#addChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren addChild(String); then child is added and returned")
  void testNodeChildrenAddChild() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();

    // Act
    NodeChildren result = nodeChildren.addChild("ChildA");

    // Assert
    assertSame(nodeChildren, result);
    assertFalse(nodeChildren.isEmpty());
    assertTrue(nodeChildren.isChildUnique());
    assertEquals("ChildA", nodeChildren.getUniqueChild());
  }

  /**
   * Test {@link NodeChildren#addChild(String)} adding multiple children.
   *
   * <p>Method under test: {@link NodeChildren#addChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren addChild(String); adding two children; then size is two")
  void testNodeChildrenAddChildMultiple() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();

    // Act
    nodeChildren.addChild("ChildA");
    nodeChildren.addChild("ChildB");

    // Assert
    assertFalse(nodeChildren.isEmpty());
    assertFalse(nodeChildren.isChildUnique());
    assertEquals(2, nodeChildren.getChildren().size());
  }

  /**
   * Test {@link NodeChildren#removeChild(String)}.
   *
   * <p>Method under test: {@link NodeChildren#removeChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren removeChild(String); then child removed and fluent return")
  void testNodeChildrenRemoveChild() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();
    nodeChildren.addChild("ChildA");
    nodeChildren.addChild("ChildB");

    // Act
    NodeChildren result = nodeChildren.removeChild("ChildA");

    // Assert
    assertSame(nodeChildren, result);
    assertTrue(nodeChildren.isChildUnique());
    assertEquals("ChildB", nodeChildren.getUniqueChild());
  }

  /**
   * Test {@link NodeChildren#removeChild(String)} when child not present.
   *
   * <p>Method under test: {@link NodeChildren#removeChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren removeChild(String); child not present; then still returns self")
  void testNodeChildrenRemoveChildNotPresent() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();

    // Act
    NodeChildren result = nodeChildren.removeChild("NonExistent");

    // Assert
    assertSame(nodeChildren, result);
    assertTrue(nodeChildren.isEmpty());
  }

  /**
   * Test {@link NodeChildren#gateway(Gateway)}.
   *
   * <p>Method under test: {@link NodeChildren#gateway(Gateway)}
   */
  @Test
  @DisplayName("Test NodeChildren gateway(Gateway); then gateway set and self returned")
  void testNodeChildrenGateway() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();

    // Act
    NodeChildren result = nodeChildren.gateway(Gateway.PARALLEL);

    // Assert
    assertSame(nodeChildren, result);
    assertEquals(Gateway.PARALLEL, nodeChildren.getGateway());
  }

  /**
   * Test {@link NodeChildren#gateway(Gateway)} with all gateway types.
   *
   * <p>Method under test: {@link NodeChildren#gateway(Gateway)}
   */
  @Test
  @DisplayName("Test NodeChildren gateway(Gateway); with EVENT_BASED; then gateway is EVENT_BASED")
  void testNodeChildrenGatewayEventBased() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();

    // Act
    NodeChildren result = nodeChildren.gateway(Gateway.EVENT_BASED);

    // Assert
    assertSame(nodeChildren, result);
    assertEquals(Gateway.EVENT_BASED, nodeChildren.getGateway());
  }

  /**
   * Test {@link NodeChildren#isEmpty()}.
   *
   * <p>Method under test: {@link NodeChildren#isEmpty()}
   */
  @Test
  @DisplayName("Test NodeChildren isEmpty(); given empty NodeChildren; then return true")
  void testNodeChildrenIsEmpty_thenReturnTrue() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();

    // Act and Assert
    assertTrue(nodeChildren.isEmpty());
  }

  /**
   * Test {@link NodeChildren#isEmpty()}.
   *
   * <p>Method under test: {@link NodeChildren#isEmpty()}
   */
  @Test
  @DisplayName("Test NodeChildren isEmpty(); given NodeChildren with child; then return false")
  void testNodeChildrenIsEmpty_thenReturnFalse() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();
    nodeChildren.addChild("Child");

    // Act and Assert
    assertFalse(nodeChildren.isEmpty());
  }

  /**
   * Test {@link NodeChildren#isChildUnique()}.
   *
   * <p>Method under test: {@link NodeChildren#isChildUnique()}
   */
  @Test
  @DisplayName("Test NodeChildren isChildUnique(); given empty; then return false")
  void testNodeChildrenIsChildUnique_whenEmpty_thenReturnFalse() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();

    // Act and Assert
    assertFalse(nodeChildren.isChildUnique());
  }

  /**
   * Test {@link NodeChildren#isChildUnique()}.
   *
   * <p>Method under test: {@link NodeChildren#isChildUnique()}
   */
  @Test
  @DisplayName("Test NodeChildren isChildUnique(); given one child; then return true")
  void testNodeChildrenIsChildUnique_whenOneChild_thenReturnTrue() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();
    nodeChildren.addChild("OnlyChild");

    // Act and Assert
    assertTrue(nodeChildren.isChildUnique());
  }

  /**
   * Test {@link NodeChildren#getUniqueChild()} happy path.
   *
   * <p>Method under test: {@link NodeChildren#getUniqueChild()}
   */
  @Test
  @DisplayName("Test NodeChildren getUniqueChild(); given one child; then return that child")
  void testNodeChildrenGetUniqueChild_givenOneChild_thenReturnChild() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();
    nodeChildren.addChild("UniqueChild");

    // Act
    String result = nodeChildren.getUniqueChild();

    // Assert
    assertEquals("UniqueChild", result);
  }

  /**
   * Test {@link NodeChildren#getUniqueChild()} when no children.
   *
   * <p>Method under test: {@link NodeChildren#getUniqueChild()}
   */
  @Test
  @DisplayName("Test NodeChildren getUniqueChild(); given no children; then throw IllegalStateException")
  void testNodeChildrenGetUniqueChild_givenNoChildren_thenThrowIllegalStateException() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> nodeChildren.getUniqueChild());
  }

  /**
   * Test {@link NodeChildren#getUniqueChild()} when multiple children.
   *
   * <p>Method under test: {@link NodeChildren#getUniqueChild()}
   */
  @Test
  @DisplayName("Test NodeChildren getUniqueChild(); given multiple children; then throw IllegalStateException")
  void testNodeChildrenGetUniqueChild_givenMultipleChildren_thenThrowIllegalStateException() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();
    nodeChildren.addChild("Child1");
    nodeChildren.addChild("Child2");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> nodeChildren.getUniqueChild());
  }
}
