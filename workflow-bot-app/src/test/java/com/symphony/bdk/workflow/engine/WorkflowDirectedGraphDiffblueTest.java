package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
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
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with
   * workflowId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#addParent(String, String)}
   */
  @Test
  @DisplayName("Test addParent(String, String); given WorkflowDirectedGraph(String) with workflowId is '42'")
  void testAddParent_givenWorkflowDirectedGraphWithWorkflowIdIs42() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with
   * workflowId is {@code 42} addParent {@code 42} and {@code Parent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#addParent(String, String)}
   */
  @Test
  @DisplayName("Test addParent(String, String); given WorkflowDirectedGraph(String) with workflowId is '42' addParent '42' and 'Parent'")
  void testAddParent_givenWorkflowDirectedGraphWithWorkflowIdIs42AddParent42AndParent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#addStartEvent(String)}
   */
  @Test
  @DisplayName("Test addStartEvent(String)")
  void testAddStartEvent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * <p>
   * Method under test:
   * {@link WorkflowDirectedGraph.NodeChildren#addChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren addChild(String)")
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
   * Test NodeChildren {@link NodeChildren#getUniqueChild()}.
   * <p>
   * Method under test:
   * {@link WorkflowDirectedGraph.NodeChildren#getUniqueChild()}
   */
  @Test
  @DisplayName("Test NodeChildren getUniqueChild()")
  void testNodeChildrenGetUniqueChild() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> nodeChildren.getUniqueChild());
  }

  /**
   * Test NodeChildren getters and setters.
   * <p>
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
  @DisplayName("Test NodeChildren getters and setters")
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
   * Test NodeChildren getters and setters.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Children is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
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
  @DisplayName("Test NodeChildren getters and setters; when ArrayList(); then return Children is ArrayList()")
  void testNodeChildrenGettersAndSetters_whenArrayList_thenReturnChildrenIsArrayList() {
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
   * Test NodeChildren getters and setters.
   * <ul>
   *   <li>When {@code EXCLUSIVE}.</li>
   *   <li>Then return Children is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
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
  @DisplayName("Test NodeChildren getters and setters; when 'EXCLUSIVE'; then return Children is ArrayList()")
  void testNodeChildrenGettersAndSetters_whenExclusive_thenReturnChildrenIsArrayList() {
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
   * Test NodeChildren {@link NodeChildren#isChildUnique()}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph.NodeChildren#isChildUnique()}
   */
  @Test
  @DisplayName("Test NodeChildren isChildUnique()")
  void testNodeChildrenIsChildUnique() {
    // Arrange, Act and Assert
    assertFalse(nodeChildren.isChildUnique());
  }

  /**
   * Test NodeChildren {@link NodeChildren#isEmpty()}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph.NodeChildren#isEmpty()}
   */
  @Test
  @DisplayName("Test NodeChildren isEmpty()")
  void testNodeChildrenIsEmpty() {
    // Arrange, Act and Assert
    assertTrue(nodeChildren.isEmpty());
  }

  /**
   * Test NodeChildren {@link NodeChildren#removeChild(String)}.
   * <p>
   * Method under test:
   * {@link WorkflowDirectedGraph.NodeChildren#removeChild(String)}
   */
  @Test
  @DisplayName("Test NodeChildren removeChild(String)")
  void testNodeChildrenRemoveChild() {
    // Arrange, Act and Assert
    assertSame(nodeChildren, nodeChildren.removeChild("Child"));
  }

  /**
   * Test
   * {@link WorkflowDirectedGraph#registerToDictionary(String, WorkflowNode)}.
   * <p>
   * Method under test:
   * {@link WorkflowDirectedGraph#registerToDictionary(String, WorkflowNode)}
   */
  @Test
  @DisplayName("Test registerToDictionary(String, WorkflowNode)")
  void testRegisterToDictionary() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#isRegistered(String)}
   */
  @Test
  @DisplayName("Test isRegistered(String)")
  void testIsRegistered() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertFalse((new WorkflowDirectedGraph("42")).isRegistered("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#getChildren(String)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#getChildren(String)}
   */
  @Test
  @DisplayName("Test getChildren(String)")
  void testGetChildren() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    WorkflowDirectedGraph.NodeChildren actualChildren = (new WorkflowDirectedGraph("42")).getChildren("42");

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
  void testReadWorkflowNode() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertNull((new WorkflowDirectedGraph("42")).readWorkflowNode("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#readChildren(String)}.
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#readChildren(String)}
   */
  @Test
  @DisplayName("Test readChildren(String)")
  void testReadChildren() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertNull((new WorkflowDirectedGraph("42")).readChildren("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#hasSeenBefore(String)}.
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with
   * workflowId is {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#hasSeenBefore(String)}
   */
  @Test
  @DisplayName("Test hasSeenBefore(String); given WorkflowDirectedGraph(String) with workflowId is '42'; then return 'false'")
  void testHasSeenBefore_givenWorkflowDirectedGraphWithWorkflowIdIs42_thenReturnFalse() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertFalse((new WorkflowDirectedGraph("42")).hasSeenBefore("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#hasSeenBefore(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#hasSeenBefore(String)}
   */
  @Test
  @DisplayName("Test hasSeenBefore(String); then return 'true'")
  void testHasSeenBefore_thenReturnTrue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    workflowDirectedGraph.addParent("42", "Parent");

    // Act and Assert
    assertTrue(workflowDirectedGraph.hasSeenBefore("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraph#getParents(String)} with {@code String}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#getParents(String)}
   */
  @Test
  @DisplayName("Test getParents(String) with 'String'; then return Empty")
  void testGetParentsWithString_thenReturnEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertTrue((new WorkflowDirectedGraph("42")).getParents("42").isEmpty());
  }

  /**
   * Test {@link WorkflowDirectedGraph#getParents(String)} with {@code String}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDirectedGraph#getParents(String)}
   */
  @Test
  @DisplayName("Test getParents(String) with 'String'; then return size is one")
  void testGetParentsWithString_thenReturnSizeIsOne() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
