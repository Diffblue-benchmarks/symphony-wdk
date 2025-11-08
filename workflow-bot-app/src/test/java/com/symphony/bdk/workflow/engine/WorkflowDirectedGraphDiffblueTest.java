package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {NodeChildren.class})
@ExtendWith(SpringExtension.class)
class WorkflowDirectedGraphDiffblueTest {
  @InjectMocks
  private WorkflowDirectedGraph workflowDirectedGraph;

  @Autowired
  private NodeChildren nodeChildren;

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return Version is {@code null}.</li>
   * </ul>
   * <p>
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
  @DisplayName("Test getters and setters; when '42'; then return Version is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowDirectedGraph.<init>(String)", "void WorkflowDirectedGraph.<init>(String, Long)",
      "Map WorkflowDirectedGraph.getDictionary()", "Map WorkflowDirectedGraph.getParents()",
      "List WorkflowDirectedGraph.getStartEvents()", "Map WorkflowDirectedGraph.getVariables()",
      "Long WorkflowDirectedGraph.getVersion()", "String WorkflowDirectedGraph.getWorkflowId()"})
  void testGettersAndSetters_when42_thenReturnVersionIsNull() {
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
   * Test getters and setters.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return Version longValue is one.</li>
   * </ul>
   * <p>
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
  @DisplayName("Test getters and setters; when one; then return Version longValue is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowDirectedGraph.<init>(String)", "void WorkflowDirectedGraph.<init>(String, Long)",
      "Map WorkflowDirectedGraph.getDictionary()", "Map WorkflowDirectedGraph.getParents()",
      "List WorkflowDirectedGraph.getStartEvents()", "Map WorkflowDirectedGraph.getVariables()",
      "Long WorkflowDirectedGraph.getVersion()", "String WorkflowDirectedGraph.getWorkflowId()"})
  void testGettersAndSetters_whenOne_thenReturnVersionLongValueIsOne() {
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

  /**
   * Test {@link WorkflowDirectedGraph#addParent(String, String)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#addParent(String, String)}
   */
  @Test
  @DisplayName("Test addParent(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowDirectedGraph.addParent(String, String)"})
  void testAddParent() {
    // Arrange and Act
    workflowDirectedGraph.addParent("42", "Parent");

    // Assert
    Map<String, Set<String>> parents = workflowDirectedGraph.getParents();
    assertEquals(1, parents.size());
    Set<String> getResult = parents.get("42");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("Parent"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#addStartEvent(String)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#addStartEvent(String)}
   */
  @Test
  @DisplayName("Test addStartEvent(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowDirectedGraph.addStartEvent(String)"})
  void testAddStartEvent() {
    // Arrange and Act
    workflowDirectedGraph.addStartEvent("Start Event");

    // Assert
    List<String> startEvents = workflowDirectedGraph.getStartEvents();
    assertEquals(1, startEvents.size());
    assertEquals("Start Event", startEvents.get(0));
  }

  /**
   * Test NodeChildren {@link NodeChildren#addChild(String)}.
   * <p>
   * Method under test: {@link NodeChildren#addChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren addChild(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"NodeChildren NodeChildren.addChild(String)"})
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
   * <p>
   * Method under test: {@link NodeChildren#getUniqueChild()}
   */
  @Test
  @DisplayName("Test NodeChildren getUniqueChild()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String NodeChildren.getUniqueChild()"})
  void testNodeChildrenGetUniqueChild() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> nodeChildren.getUniqueChild());
  }

  /**
   * Test NodeChildren getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeChildren#NodeChildren()}
   *   <li>{@link NodeChildren#gateway(Gateway)}
   *   <li>{@link NodeChildren#getChildren()}
   *   <li>{@link NodeChildren#getGateway()}
   * </ul>
   */
  @Test
  @DisplayName("Test NodeChildren getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NodeChildren.<init>()", "void NodeChildren.<init>(Gateway, List)",
      "void NodeChildren.<init>(List)", "NodeChildren NodeChildren.gateway(Gateway)", "List NodeChildren.getChildren()",
      "Gateway NodeChildren.getGateway()"})
  void testNodeChildrenGettersAndSetters() {
    // Arrange and Act
    NodeChildren actualNodeChildren = new NodeChildren();
    NodeChildren actualGatewayResult = actualNodeChildren.gateway(Gateway.EXCLUSIVE);
    List<String> actualChildren = actualNodeChildren.getChildren();

    // Assert
    assertEquals(Gateway.EXCLUSIVE, actualNodeChildren.getGateway());
    assertTrue(actualChildren.isEmpty());
    assertSame(actualNodeChildren, actualGatewayResult);
  }

  /**
   * Test NodeChildren getters and setters.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Children is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeChildren#NodeChildren(List)}
   *   <li>{@link NodeChildren#gateway(Gateway)}
   *   <li>{@link NodeChildren#getChildren()}
   *   <li>{@link NodeChildren#getGateway()}
   * </ul>
   */
  @Test
  @DisplayName("Test NodeChildren getters and setters; when ArrayList(); then return Children is ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NodeChildren.<init>()", "void NodeChildren.<init>(Gateway, List)",
      "void NodeChildren.<init>(List)", "NodeChildren NodeChildren.gateway(Gateway)", "List NodeChildren.getChildren()",
      "Gateway NodeChildren.getGateway()"})
  void testNodeChildrenGettersAndSetters_whenArrayList_thenReturnChildrenIsArrayList() {
    // Arrange
    ArrayList<String> children = new ArrayList<>();

    // Act
    NodeChildren actualNodeChildren = new NodeChildren(children);
    NodeChildren actualGatewayResult = actualNodeChildren.gateway(Gateway.EXCLUSIVE);
    List<String> actualChildren = actualNodeChildren.getChildren();

    // Assert
    assertEquals(Gateway.EXCLUSIVE, actualNodeChildren.getGateway());
    assertTrue(actualChildren.isEmpty());
    assertSame(actualNodeChildren, actualGatewayResult);
    assertSame(children, actualChildren);
  }

  /**
   * Test NodeChildren getters and setters.
   * <ul>
   *   <li>When {@code EXCLUSIVE}.</li>
   *   <li>Then return Children is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeChildren#NodeChildren(Gateway, List)}
   *   <li>{@link NodeChildren#gateway(Gateway)}
   *   <li>{@link NodeChildren#getChildren()}
   *   <li>{@link NodeChildren#getGateway()}
   * </ul>
   */
  @Test
  @DisplayName("Test NodeChildren getters and setters; when 'EXCLUSIVE'; then return Children is ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NodeChildren.<init>()", "void NodeChildren.<init>(Gateway, List)",
      "void NodeChildren.<init>(List)", "NodeChildren NodeChildren.gateway(Gateway)", "List NodeChildren.getChildren()",
      "Gateway NodeChildren.getGateway()"})
  void testNodeChildrenGettersAndSetters_whenExclusive_thenReturnChildrenIsArrayList() {
    // Arrange
    ArrayList<String> children = new ArrayList<>();

    // Act
    NodeChildren actualNodeChildren = new NodeChildren(Gateway.EXCLUSIVE, children);
    NodeChildren actualGatewayResult = actualNodeChildren.gateway(Gateway.EXCLUSIVE);
    List<String> actualChildren = actualNodeChildren.getChildren();

    // Assert
    assertEquals(Gateway.EXCLUSIVE, actualNodeChildren.getGateway());
    assertTrue(actualChildren.isEmpty());
    assertSame(actualNodeChildren, actualGatewayResult);
    assertSame(children, actualChildren);
  }

  /**
   * Test NodeChildren {@link NodeChildren#isChildUnique()}.
   * <p>
   * Method under test: {@link NodeChildren#isChildUnique()}
   */
  @Test
  @DisplayName("Test NodeChildren isChildUnique()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeChildren.isChildUnique()"})
  void testNodeChildrenIsChildUnique() {
    // Arrange, Act and Assert
    assertFalse(nodeChildren.isChildUnique());
  }

  /**
   * Test NodeChildren {@link NodeChildren#isEmpty()}.
   * <p>
   * Method under test: {@link NodeChildren#isEmpty()}
   */
  @Test
  @DisplayName("Test NodeChildren isEmpty()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeChildren.isEmpty()"})
  void testNodeChildrenIsEmpty() {
    // Arrange, Act and Assert
    assertTrue(nodeChildren.isEmpty());
  }

  /**
   * Test NodeChildren {@link NodeChildren#removeChild(String)}.
   * <p>
   * Method under test: {@link NodeChildren#removeChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren removeChild(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"NodeChildren NodeChildren.removeChild(String)"})
  void testNodeChildrenRemoveChild() {
    // Arrange, Act and Assert
    assertSame(nodeChildren, nodeChildren.removeChild("Child"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#registerToDictionary(String, WorkflowNode)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#registerToDictionary(String, WorkflowNode)}
   */
  @Test
  @DisplayName("Test registerToDictionary(String, WorkflowNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowDirectedGraph.registerToDictionary(String, WorkflowNode)"})
  void testRegisterToDictionary() {
    // Arrange
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
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#isRegistered(String)}
   */
  @Test
  @DisplayName("Test isRegistered(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDirectedGraph.isRegistered(String)"})
  void testIsRegistered() {
    // Arrange, Act and Assert
    assertFalse(workflowDirectedGraph.isRegistered("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#getChildren(String)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#getChildren(String)}
   */
  @Test
  @DisplayName("Test getChildren(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"NodeChildren WorkflowDirectedGraph.getChildren(String)"})
  void testGetChildren() {
    // Arrange and Act
    NodeChildren actualChildren = workflowDirectedGraph.getChildren("42");

    // Assert
    assertNull(actualChildren.getGateway());
    assertFalse(actualChildren.isChildUnique());
    assertTrue(actualChildren.isEmpty());
    assertTrue(actualChildren.getChildren().isEmpty());
  }

  /**
   * Test {@link WorkflowDirectedGraph#readWorkflowNode(String)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#readWorkflowNode(String)}
   */
  @Test
  @DisplayName("Test readWorkflowNode(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNode WorkflowDirectedGraph.readWorkflowNode(String)"})
  void testReadWorkflowNode() {
    // Arrange, Act and Assert
    assertNull(workflowDirectedGraph.readWorkflowNode("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#readChildren(String)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#readChildren(String)}
   */
  @Test
  @DisplayName("Test readChildren(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"NodeChildren WorkflowDirectedGraph.readChildren(String)"})
  void testReadChildren() {
    // Arrange, Act and Assert
    assertNull(workflowDirectedGraph.readChildren("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#hasSeenBefore(String)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#hasSeenBefore(String)}
   */
  @Test
  @DisplayName("Test hasSeenBefore(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowDirectedGraph.hasSeenBefore(String)"})
  void testHasSeenBefore() {
    // Arrange, Act and Assert
    assertFalse(workflowDirectedGraph.hasSeenBefore("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#getParents(String)} with {@code String}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#getParents(String)}
   */
  @Test
  @DisplayName("Test getParents(String) with 'String'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List WorkflowDirectedGraph.getParents(String)"})
  void testGetParentsWithString() {
    // Arrange, Act and Assert
    assertTrue(workflowDirectedGraph.getParents("42").isEmpty());
  }
}
