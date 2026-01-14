package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.Gateway;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NodeChildren.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class WorkflowDirectedGraphDiffblueTest {
  @Autowired private NodeChildren nodeChildren;

  /**
   * Test {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return Version is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)}
   */
  @Test
  @DisplayName("Test new WorkflowDirectedGraph(String); when '42'; then return Version is 'null'")
  @Tag("MaintainedByDiffblue")
  void testNewWorkflowDirectedGraph_when42_thenReturnVersionIsNull() {
    // Arrange and Act
    WorkflowDirectedGraph actualWorkflowDirectedGraph = new WorkflowDirectedGraph("42");

    // Assert
    assertEquals("42", actualWorkflowDirectedGraph.getWorkflowId());
    assertNull(actualWorkflowDirectedGraph.getVersion());
    assertTrue(actualWorkflowDirectedGraph.getStartEvents().isEmpty());
    assertTrue(actualWorkflowDirectedGraph.getDictionary().isEmpty());
    assertTrue(actualWorkflowDirectedGraph.getParents().isEmpty());
    assertTrue(actualWorkflowDirectedGraph.getVariables().isEmpty());
  }

  /**
   * Test {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String, Long)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return Version longValue is one.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String, Long)}
   */
  @Test
  @DisplayName(
      "Test new WorkflowDirectedGraph(String, Long); when one; then return Version longValue is one")
  @Tag("MaintainedByDiffblue")
  void testNewWorkflowDirectedGraph_whenOne_thenReturnVersionLongValueIsOne() {
    // Arrange and Act
    WorkflowDirectedGraph actualWorkflowDirectedGraph = new WorkflowDirectedGraph("42", 1L);

    // Assert
    assertEquals("42", actualWorkflowDirectedGraph.getWorkflowId());
    assertEquals(1L, actualWorkflowDirectedGraph.getVersion().longValue());
    assertTrue(actualWorkflowDirectedGraph.getStartEvents().isEmpty());
    assertTrue(actualWorkflowDirectedGraph.getDictionary().isEmpty());
    assertTrue(actualWorkflowDirectedGraph.getParents().isEmpty());
    assertTrue(actualWorkflowDirectedGraph.getVariables().isEmpty());
  }

  /**
   * Test {@link WorkflowDirectedGraph#addParent(String, String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with workflowId is
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#addParent(String, String)}
   */
  @Test
  @DisplayName(
      "Test addParent(String, String); given WorkflowDirectedGraph(String) with workflowId is '42'")
  @Tag("MaintainedByDiffblue")
  void testAddParent_givenWorkflowDirectedGraphWithWorkflowIdIs42() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");

    // Act
    workflowDirectedGraph.addParent("42", "Parent");

    // Assert
    Map<String, Set<String>> parents = workflowDirectedGraph.getParents();
    assertEquals(1, parents.size());
    Set<String> getResult = parents.get("42");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("Parent"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#addParent(String, String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with workflowId is
   *       {@code 42} addParent {@code 42} and {@code Parent}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#addParent(String, String)}
   */
  @Test
  @DisplayName(
      "Test addParent(String, String); given WorkflowDirectedGraph(String) with workflowId is '42' addParent '42' and 'Parent'")
  @Tag("MaintainedByDiffblue")
  void testAddParent_givenWorkflowDirectedGraphWithWorkflowIdIs42AddParent42AndParent() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    workflowDirectedGraph.addParent("42", "Parent");

    // Act
    workflowDirectedGraph.addParent("42", "Parent");

    // Assert that nothing has changed
    Map<String, Set<String>> parents = workflowDirectedGraph.getParents();
    assertEquals(1, parents.size());
    Set<String> getResult = parents.get("42");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("Parent"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#addStartEvent(String)}.
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#addStartEvent(String)}
   */
  @Test
  @DisplayName("Test addStartEvent(String)")
  @Tag("MaintainedByDiffblue")
  void testAddStartEvent() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");

    // Act
    workflowDirectedGraph.addStartEvent("Start Event");

    // Assert
    List<String> startEvents = workflowDirectedGraph.getStartEvents();
    assertEquals(1, startEvents.size());
    assertEquals("Start Event", startEvents.get(0));
  }

  /**
   * Test NodeChildren {@link NodeChildren#addChild(String)}.
   *
   * <p>Method under test: {@link NodeChildren#addChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren addChild(String)")
  @Tag("MaintainedByDiffblue")
  void testNodeChildrenAddChild() {
    // Arrange and Act
    NodeChildren actualAddChildResult = nodeChildren.addChild("Child");

    // Assert
    assertEquals("Child", nodeChildren.getUniqueChild());
    assertFalse(nodeChildren.isEmpty());
    assertTrue(nodeChildren.isChildUnique());
    assertSame(nodeChildren, actualAddChildResult);
  }

  /**
   * Test NodeChildren {@link NodeChildren#getUniqueChild()}.
   *
   * <ul>
   *   <li>Given {@link NodeChildren}.
   * </ul>
   *
   * <p>Method under test: {@link NodeChildren#getUniqueChild()}
   */
  @Test
  @DisplayName("Test NodeChildren getUniqueChild(); given NodeChildren")
  @Tag("MaintainedByDiffblue")
  void testNodeChildrenGetUniqueChild_givenNodeChildren() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> nodeChildren.getUniqueChild());
  }

  /**
   * Test NodeChildren getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link NodeChildren#NodeChildren(List)}
   *   <li>{@link NodeChildren#gateway(Gateway)}
   * </ul>
   */
  @Test
  @DisplayName("Test NodeChildren getters and setters")
  @Tag("MaintainedByDiffblue")
  void testNodeChildrenGettersAndSetters() {
    // Arrange
    ArrayList<String> children = new ArrayList<>();

    // Act
    NodeChildren actualNodeChildren = new NodeChildren(children);
    NodeChildren actualGatewayResult = actualNodeChildren.gateway(Gateway.EXCLUSIVE);

    // Assert
    assertEquals(Gateway.EXCLUSIVE, actualNodeChildren.getGateway());
    List<String> children2 = actualNodeChildren.getChildren();
    assertTrue(children2.isEmpty());
    assertSame(actualNodeChildren, actualGatewayResult);
    assertSame(children, children2);
  }

  /**
   * Test NodeChildren {@link NodeChildren#isChildUnique()}.
   *
   * <ul>
   *   <li>Given {@link NodeChildren#NodeChildren()} addChild {@code Child}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link NodeChildren#isChildUnique()}
   */
  @Test
  @DisplayName(
      "Test NodeChildren isChildUnique(); given NodeChildren() addChild 'Child'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testNodeChildrenIsChildUnique_givenNodeChildrenAddChildChild_thenReturnTrue() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();
    nodeChildren.addChild("Child");

    // Act and Assert
    assertTrue(nodeChildren.isChildUnique());
  }

  /**
   * Test NodeChildren {@link NodeChildren#isEmpty()}.
   *
   * <ul>
   *   <li>Given {@link NodeChildren#NodeChildren()} addChild {@code Child}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link NodeChildren#isEmpty()}
   */
  @Test
  @DisplayName(
      "Test NodeChildren isEmpty(); given NodeChildren() addChild 'Child'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testNodeChildrenIsEmpty_givenNodeChildrenAddChildChild_thenReturnFalse() {
    // Arrange
    NodeChildren nodeChildren = new NodeChildren();
    nodeChildren.addChild("Child");

    // Act and Assert
    assertFalse(nodeChildren.isEmpty());
  }

  /**
   * Test NodeChildren {@link NodeChildren#removeChild(String)}.
   *
   * <p>Method under test: {@link NodeChildren#removeChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren removeChild(String)")
  @Tag("MaintainedByDiffblue")
  void testNodeChildrenRemoveChild() {
    // Arrange and Act
    NodeChildren actualRemoveChildResult = nodeChildren.removeChild("Child");

    // Assert
    assertSame(nodeChildren, actualRemoveChildResult);
  }

  /**
   * Test {@link WorkflowDirectedGraph#registerToDictionary(String, WorkflowNode)}.
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#registerToDictionary(String, WorkflowNode)}
   */
  @Test
  @DisplayName("Test registerToDictionary(String, WorkflowNode)")
  @Tag("MaintainedByDiffblue")
  void testRegisterToDictionary() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    WorkflowNode node = new WorkflowNode();

    // Act
    workflowDirectedGraph.registerToDictionary("42", node);

    // Assert
    Map<String, WorkflowNode> dictionary = workflowDirectedGraph.getDictionary();
    assertEquals(1, dictionary.size());
    assertSame(node, dictionary.get("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#isRegistered(String)}.
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#isRegistered(String)}
   */
  @Test
  @DisplayName("Test isRegistered(String)")
  @Tag("MaintainedByDiffblue")
  void testIsRegistered() {
    // Arrange, Act and Assert
    assertFalse(new WorkflowDirectedGraph("42").isRegistered("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#getChildren(String)}.
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#getChildren(String)}
   */
  @Test
  @DisplayName("Test getChildren(String)")
  @Tag("MaintainedByDiffblue")
  void testGetChildren() {
    // Arrange and Act
    NodeChildren actualChildren = new WorkflowDirectedGraph("42").getChildren("42");

    // Assert
    assertNull(actualChildren.getGateway());
    assertFalse(actualChildren.isChildUnique());
    assertTrue(actualChildren.isEmpty());
    assertTrue(actualChildren.getChildren().isEmpty());
  }

  /**
   * Test {@link WorkflowDirectedGraph#readWorkflowNode(String)}.
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#readWorkflowNode(String)}
   */
  @Test
  @DisplayName("Test readWorkflowNode(String)")
  @Tag("MaintainedByDiffblue")
  void testReadWorkflowNode() {
    // Arrange, Act and Assert
    assertNull(new WorkflowDirectedGraph("42").readWorkflowNode("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#readChildren(String)}.
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#readChildren(String)}
   */
  @Test
  @DisplayName("Test readChildren(String)")
  @Tag("MaintainedByDiffblue")
  void testReadChildren() {
    // Arrange, Act and Assert
    assertNull(new WorkflowDirectedGraph("42").readChildren("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#hasSeenBefore(String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with workflowId is
   *       {@code 42}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#hasSeenBefore(String)}
   */
  @Test
  @DisplayName(
      "Test hasSeenBefore(String); given WorkflowDirectedGraph(String) with workflowId is '42'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testHasSeenBefore_givenWorkflowDirectedGraphWithWorkflowIdIs42_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new WorkflowDirectedGraph("42").hasSeenBefore("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#hasSeenBefore(String)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#hasSeenBefore(String)}
   */
  @Test
  @DisplayName("Test hasSeenBefore(String); then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testHasSeenBefore_thenReturnTrue() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    workflowDirectedGraph.addParent("42", "Parent");

    // Act and Assert
    assertTrue(workflowDirectedGraph.hasSeenBefore("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#getParents(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#getParents(String)}
   */
  @Test
  @DisplayName("Test getParents(String) with 'String'; then return Empty")
  @Tag("MaintainedByDiffblue")
  void testGetParentsWithString_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new WorkflowDirectedGraph("42").getParents("42").isEmpty());
  }

  /**
   * Test {@link WorkflowDirectedGraph#getParents(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraph#getParents(String)}
   */
  @Test
  @DisplayName("Test getParents(String) with 'String'; then return size is one")
  @Tag("MaintainedByDiffblue")
  void testGetParentsWithString_thenReturnSizeIsOne() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    workflowDirectedGraph.addParent("42", "Parent");

    // Act
    List<String> actualParents = workflowDirectedGraph.getParents("42");

    // Assert
    assertEquals(1, actualParents.size());
    assertEquals("Parent", actualParents.get(0));
  }
}
