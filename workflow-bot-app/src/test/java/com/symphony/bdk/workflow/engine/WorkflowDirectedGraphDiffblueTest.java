package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowDirectedGraph.NodeChildren.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WorkflowDirectedGraphDiffblueTest {
  @Autowired
  private WorkflowDirectedGraph.NodeChildren nodeChildren;

  /**
   * Method under test: {@link WorkflowDirectedGraph#addStartEvent(String)}
   */
  @Test
  void testAddStartEvent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link WorkflowDirectedGraph.NodeChildren#addChild(String)}
   */
  @Test
  void testNodeChildrenAddChild() {
    // Arrange and Act
    WorkflowDirectedGraph.NodeChildren actualAddChildResult = nodeChildren.addChild("Child");

    // Assert
    assertEquals("Child", nodeChildren.getUniqueChild());
    assertFalse(nodeChildren.isEmpty());
    assertTrue(nodeChildren.isChildUnique());
    assertSame(nodeChildren, actualAddChildResult);
  }

  /**
   * Method under test:
   * {@link WorkflowDirectedGraph.NodeChildren#getUniqueChild()}
   */
  @Test
  void testNodeChildrenGetUniqueChild() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> nodeChildren.getUniqueChild());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDirectedGraph.NodeChildren#NodeChildren()}
   *   <li>
   * {@link WorkflowDirectedGraph.NodeChildren#gateway(WorkflowDirectedGraph.Gateway)}
   *   <li>{@link WorkflowDirectedGraph.NodeChildren#getChildren()}
   *   <li>{@link WorkflowDirectedGraph.NodeChildren#getGateway()}
   * </ul>
   */
  @Test
  void testNodeChildrenGettersAndSetters() {
    // Arrange and Act
    WorkflowDirectedGraph.NodeChildren actualNodeChildren = new WorkflowDirectedGraph.NodeChildren();
    WorkflowDirectedGraph.NodeChildren actualGatewayResult = actualNodeChildren
        .gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    List<String> actualChildren = actualNodeChildren.getChildren();

    // Assert
    assertEquals(WorkflowDirectedGraph.Gateway.EXCLUSIVE, actualNodeChildren.getGateway());
    assertTrue(actualChildren.isEmpty());
    assertSame(actualNodeChildren, actualGatewayResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link WorkflowDirectedGraph.NodeChildren#NodeChildren(WorkflowDirectedGraph.Gateway, List)}
   *   <li>
   * {@link WorkflowDirectedGraph.NodeChildren#gateway(WorkflowDirectedGraph.Gateway)}
   *   <li>{@link WorkflowDirectedGraph.NodeChildren#getChildren()}
   *   <li>{@link WorkflowDirectedGraph.NodeChildren#getGateway()}
   * </ul>
   */
  @Test
  void testNodeChildrenGettersAndSetters2() {
    // Arrange
    ArrayList<String> children = new ArrayList<>();

    // Act
    WorkflowDirectedGraph.NodeChildren actualNodeChildren = new WorkflowDirectedGraph.NodeChildren(
        WorkflowDirectedGraph.Gateway.EXCLUSIVE, children);
    WorkflowDirectedGraph.NodeChildren actualGatewayResult = actualNodeChildren
        .gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    List<String> actualChildren = actualNodeChildren.getChildren();

    // Assert
    assertEquals(WorkflowDirectedGraph.Gateway.EXCLUSIVE, actualNodeChildren.getGateway());
    assertTrue(actualChildren.isEmpty());
    assertSame(actualNodeChildren, actualGatewayResult);
    assertSame(children, actualChildren);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDirectedGraph.NodeChildren#NodeChildren(List)}
   *   <li>
   * {@link WorkflowDirectedGraph.NodeChildren#gateway(WorkflowDirectedGraph.Gateway)}
   *   <li>{@link WorkflowDirectedGraph.NodeChildren#getChildren()}
   *   <li>{@link WorkflowDirectedGraph.NodeChildren#getGateway()}
   * </ul>
   */
  @Test
  void testNodeChildrenGettersAndSetters3() {
    // Arrange
    ArrayList<String> children = new ArrayList<>();

    // Act
    WorkflowDirectedGraph.NodeChildren actualNodeChildren = new WorkflowDirectedGraph.NodeChildren(children);
    WorkflowDirectedGraph.NodeChildren actualGatewayResult = actualNodeChildren
        .gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    List<String> actualChildren = actualNodeChildren.getChildren();

    // Assert
    assertEquals(WorkflowDirectedGraph.Gateway.EXCLUSIVE, actualNodeChildren.getGateway());
    assertTrue(actualChildren.isEmpty());
    assertSame(actualNodeChildren, actualGatewayResult);
    assertSame(children, actualChildren);
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph.NodeChildren#isChildUnique()}
   */
  @Test
  void testNodeChildrenIsChildUnique() {
    // Arrange, Act and Assert
    assertFalse(nodeChildren.isChildUnique());
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph.NodeChildren#isEmpty()}
   */
  @Test
  void testNodeChildrenIsEmpty() {
    // Arrange, Act and Assert
    assertTrue(nodeChildren.isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowDirectedGraph.NodeChildren#removeChild(String)}
   */
  @Test
  void testNodeChildrenRemoveChild() {
    // Arrange, Act and Assert
    assertSame(nodeChildren, nodeChildren.removeChild("Child"));
  }

  /**
   * Method under test:
   * {@link WorkflowDirectedGraph#registerToDictionary(String, WorkflowNode)}
   */
  @Test
  void testRegisterToDictionary() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    WorkflowNode node = new WorkflowNode();

    // Act
    workflowDirectedGraph.registerToDictionary("42", node);

    // Assert
    Map<String, WorkflowNode> dictionary = workflowDirectedGraph.getDictionary();
    assertEquals(1, dictionary.size());
    assertTrue(workflowDirectedGraph.getVariables().isEmpty());
    assertSame(node, dictionary.get("42"));
  }

  /**
   * Method under test:
   * {@link WorkflowDirectedGraph#registerToDictionary(String, WorkflowNode)}
   */
  @Test
  void testRegisterToDictionary2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    WorkflowNode node = mock(WorkflowNode.class);

    // Act
    workflowDirectedGraph.registerToDictionary("42", node);

    // Assert
    Map<String, WorkflowNode> dictionary = workflowDirectedGraph.getDictionary();
    assertEquals(1, dictionary.size());
    assertTrue(workflowDirectedGraph.getVariables().isEmpty());
    assertSame(node, dictionary.get("42"));
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph#isRegistered(String)}
   */
  @Test
  void testIsRegistered() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertFalse((new WorkflowDirectedGraph("42")).isRegistered("42"));
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph#getChildren(String)}
   */
  @Test
  void testGetChildren() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    WorkflowDirectedGraph.NodeChildren actualChildren = (new WorkflowDirectedGraph("42")).getChildren("42");

    // Assert
    assertNull(actualChildren.getGateway());
    assertFalse(actualChildren.isChildUnique());
    assertTrue(actualChildren.isEmpty());
    assertTrue(actualChildren.getChildren().isEmpty());
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph#readWorkflowNode(String)}
   */
  @Test
  void testReadWorkflowNode() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertNull((new WorkflowDirectedGraph("42")).readWorkflowNode("42"));
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph#readChildren(String)}
   */
  @Test
  void testReadChildren() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertNull((new WorkflowDirectedGraph("42")).readChildren("42"));
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph#hasSeenBefore(String)}
   */
  @Test
  void testHasSeenBefore() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertFalse((new WorkflowDirectedGraph("42")).hasSeenBefore("42"));
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph#hasSeenBefore(String)}
   */
  @Test
  void testHasSeenBefore2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    workflowDirectedGraph.addParent("42", "Parent");

    // Act and Assert
    assertTrue(workflowDirectedGraph.hasSeenBefore("42"));
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph#getParents(String)}
   */
  @Test
  void testGetParents() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertTrue((new WorkflowDirectedGraph("42")).getParents("42").isEmpty());
  }

  /**
   * Method under test: {@link WorkflowDirectedGraph#getParents(String)}
   */
  @Test
  void testGetParents2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    workflowDirectedGraph.addParent("42", "Parent");

    // Act
    List<String> actualParents = workflowDirectedGraph.getParents("42");

    // Assert
    assertEquals(1, actualParents.size());
    assertEquals("Parent", actualParents.get(0));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)}
   *   <li>{@link WorkflowDirectedGraph#getDictionary()}
   *   <li>{@link WorkflowDirectedGraph#getParents()}
   *   <li>{@link WorkflowDirectedGraph#getStartEvents()}
   *   <li>{@link WorkflowDirectedGraph#getVariables()}
   *   <li>{@link WorkflowDirectedGraph#getVersion()}
   *   <li>{@link WorkflowDirectedGraph#getWorkflowId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    WorkflowDirectedGraph actualWorkflowDirectedGraph = new WorkflowDirectedGraph("42");
    Map<String, WorkflowNode> actualDictionary = actualWorkflowDirectedGraph.getDictionary();
    Map<String, Set<String>> actualParents = actualWorkflowDirectedGraph.getParents();
    List<String> actualStartEvents = actualWorkflowDirectedGraph.getStartEvents();
    Map<String, Object> actualVariables = actualWorkflowDirectedGraph.getVariables();
    Long actualVersion = actualWorkflowDirectedGraph.getVersion();

    // Assert
    assertEquals("42", actualWorkflowDirectedGraph.getWorkflowId());
    assertNull(actualVersion);
    assertTrue(actualStartEvents.isEmpty());
    assertTrue(actualDictionary.isEmpty());
    assertTrue(actualParents.isEmpty());
    assertTrue(actualVariables.isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDirectedGraph#WorkflowDirectedGraph(String, Long)}
   *   <li>{@link WorkflowDirectedGraph#getDictionary()}
   *   <li>{@link WorkflowDirectedGraph#getParents()}
   *   <li>{@link WorkflowDirectedGraph#getStartEvents()}
   *   <li>{@link WorkflowDirectedGraph#getVariables()}
   *   <li>{@link WorkflowDirectedGraph#getVersion()}
   *   <li>{@link WorkflowDirectedGraph#getWorkflowId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    WorkflowDirectedGraph actualWorkflowDirectedGraph = new WorkflowDirectedGraph("42", 1L);
    Map<String, WorkflowNode> actualDictionary = actualWorkflowDirectedGraph.getDictionary();
    Map<String, Set<String>> actualParents = actualWorkflowDirectedGraph.getParents();
    List<String> actualStartEvents = actualWorkflowDirectedGraph.getStartEvents();
    Map<String, Object> actualVariables = actualWorkflowDirectedGraph.getVariables();
    Long actualVersion = actualWorkflowDirectedGraph.getVersion();

    // Assert
    assertEquals("42", actualWorkflowDirectedGraph.getWorkflowId());
    assertEquals(1L, actualVersion.longValue());
    assertTrue(actualStartEvents.isEmpty());
    assertTrue(actualDictionary.isEmpty());
    assertTrue(actualParents.isEmpty());
    assertTrue(actualVariables.isEmpty());
  }
}
