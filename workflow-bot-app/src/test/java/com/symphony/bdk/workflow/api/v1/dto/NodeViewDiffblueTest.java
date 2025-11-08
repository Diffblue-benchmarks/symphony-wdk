package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NodeViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView.ChildView#equals(Object)}
   *   <li>{@link NodeView.ChildView#hashCode()}
   * </ul>
   */
  @Test
  void testChildViewEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    NodeView.ChildView ofResult = NodeView.ChildView.of("42");
    NodeView.ChildView ofResult2 = NodeView.ChildView.of("42");

    // Act and Assert
    assertEquals(ofResult, ofResult2);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView.ChildView#equals(Object)}
   *   <li>{@link NodeView.ChildView#hashCode()}
   * </ul>
   */
  @Test
  void testChildViewEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    NodeView.ChildView ofResult = NodeView.ChildView.of(null);
    NodeView.ChildView ofResult2 = NodeView.ChildView.of(null);

    // Act and Assert
    assertEquals(ofResult, ofResult2);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView.ChildView#equals(Object)}
   *   <li>{@link NodeView.ChildView#hashCode()}
   * </ul>
   */
  @Test
  void testChildViewEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    NodeView.ChildView ofResult = NodeView.ChildView.of("42", "42");
    NodeView.ChildView ofResult2 = NodeView.ChildView.of("42", "42");

    // Act and Assert
    assertEquals(ofResult, ofResult2);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView.ChildView#equals(Object)}
   *   <li>{@link NodeView.ChildView#hashCode()}
   * </ul>
   */
  @Test
  void testChildViewEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    NodeView.ChildView ofResult = NodeView.ChildView.of("42");

    // Act and Assert
    assertEquals(ofResult, ofResult);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult.hashCode());
  }

  /**
   * Method under test: {@link NodeView.ChildView#equals(Object)}
   */
  @Test
  void testChildViewEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    NodeView.ChildView ofResult = NodeView.ChildView.of("Node Id");

    // Act and Assert
    assertNotEquals(ofResult, NodeView.ChildView.of("42"));
  }

  /**
   * Method under test: {@link NodeView.ChildView#equals(Object)}
   */
  @Test
  void testChildViewEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    NodeView.ChildView ofResult = NodeView.ChildView.of(null);

    // Act and Assert
    assertNotEquals(ofResult, NodeView.ChildView.of("42"));
  }

  /**
   * Method under test: {@link NodeView.ChildView#equals(Object)}
   */
  @Test
  void testChildViewEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    NodeView.ChildView ofResult = NodeView.ChildView.of("42", "42");

    // Act and Assert
    assertNotEquals(ofResult, NodeView.ChildView.of("42"));
  }

  /**
   * Method under test: {@link NodeView.ChildView#equals(Object)}
   */
  @Test
  void testChildViewEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    NodeView.ChildView ofResult = NodeView.ChildView.of("42");

    // Act and Assert
    assertNotEquals(ofResult, NodeView.ChildView.of("42", "42"));
  }

  /**
   * Method under test: {@link NodeView.ChildView#equals(Object)}
   */
  @Test
  void testChildViewEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(NodeView.ChildView.of("42"), null);
  }

  /**
   * Method under test: {@link NodeView.ChildView#equals(Object)}
   */
  @Test
  void testChildViewEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(NodeView.ChildView.of("42"), "Different type to ChildView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView.ChildView#ChildView()}
   *   <li>{@link NodeView.ChildView#setCondition(String)}
   *   <li>{@link NodeView.ChildView#setNodeId(String)}
   *   <li>{@link NodeView.ChildView#toString()}
   *   <li>{@link NodeView.ChildView#getCondition()}
   *   <li>{@link NodeView.ChildView#getNodeId()}
   * </ul>
   */
  @Test
  void testChildViewGettersAndSetters() {
    // Arrange and Act
    NodeView.ChildView actualChildView = new NodeView.ChildView();
    actualChildView.setCondition("Condition");
    actualChildView.setNodeId("42");
    String actualToStringResult = actualChildView.toString();
    String actualCondition = actualChildView.getCondition();

    // Assert that nothing has changed
    assertEquals("42", actualChildView.getNodeId());
    assertEquals("Condition", actualCondition);
    assertEquals("NodeView.ChildView(nodeId=42, condition=Condition)", actualToStringResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView.ChildView#ChildView(String)}
   *   <li>{@link NodeView.ChildView#setCondition(String)}
   *   <li>{@link NodeView.ChildView#setNodeId(String)}
   *   <li>{@link NodeView.ChildView#toString()}
   *   <li>{@link NodeView.ChildView#getCondition()}
   *   <li>{@link NodeView.ChildView#getNodeId()}
   * </ul>
   */
  @Test
  void testChildViewGettersAndSetters2() {
    // Arrange and Act
    NodeView.ChildView actualChildView = new NodeView.ChildView("42");
    actualChildView.setCondition("Condition");
    actualChildView.setNodeId("42");
    String actualToStringResult = actualChildView.toString();
    String actualCondition = actualChildView.getCondition();

    // Assert that nothing has changed
    assertEquals("42", actualChildView.getNodeId());
    assertEquals("Condition", actualCondition);
    assertEquals("NodeView.ChildView(nodeId=42, condition=Condition)", actualToStringResult);
  }

  /**
   * Method under test: {@link NodeView.ChildView#of(String)}
   */
  @Test
  void testChildViewOf() {
    // Arrange and Act
    NodeView.ChildView actualOfResult = NodeView.ChildView.of("42");

    // Assert
    assertEquals("42", actualOfResult.getNodeId());
    assertNull(actualOfResult.getCondition());
  }

  /**
   * Method under test: {@link NodeView.ChildView#of(String, String)}
   */
  @Test
  void testChildViewOf2() {
    // Arrange and Act
    NodeView.ChildView actualOfResult = NodeView.ChildView.of("42", "Condition");

    // Assert
    assertEquals("42", actualOfResult.getNodeId());
    assertEquals("Condition", actualOfResult.getCondition());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView#equals(Object)}
   *   <li>{@link NodeView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult2 = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult2.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView#equals(Object)}
   *   <li>{@link NodeView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.group(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder2.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type(null).build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("42").build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group(null).nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    builderResult.nodeId("42");
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(builderResult);
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult2 = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult2.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    builderResult.group("Type");
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(builderResult);
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult2 = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult2.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type(null).build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type(null).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    NodeView.NodeViewBuilder nodeViewBuilder = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder.parents(Mockito.<List<String>>any())).thenReturn(NodeView.builder());
    NodeView.NodeViewBuilder nodeViewBuilder2 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder2.nodeId(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeView.NodeViewBuilder nodeViewBuilder3 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder3.group(Mockito.<String>any())).thenReturn(nodeViewBuilder2);
    NodeView.NodeViewBuilder nodeViewBuilder4 = mock(NodeView.NodeViewBuilder.class);
    when(nodeViewBuilder4.children(Mockito.<List<NodeView.ChildView>>any())).thenReturn(nodeViewBuilder3);
    NodeView.NodeViewBuilder nodeIdResult = nodeViewBuilder4.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group(null).nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    NodeView.NodeViewBuilder nodeIdResult = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to NodeView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView#NodeView()}
   *   <li>{@link NodeView#setChildren(List)}
   *   <li>{@link NodeView#setGroup(String)}
   *   <li>{@link NodeView#setNodeId(String)}
   *   <li>{@link NodeView#setParents(List)}
   *   <li>{@link NodeView#setType(String)}
   *   <li>{@link NodeView#toString()}
   *   <li>{@link NodeView#getChildren()}
   *   <li>{@link NodeView#getGroup()}
   *   <li>{@link NodeView#getNodeId()}
   *   <li>{@link NodeView#getParents()}
   *   <li>{@link NodeView#getType()}
   *   <li>{@link NodeView#toBuilder()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    NodeView actualNodeView = new NodeView();
    ArrayList<NodeView.ChildView> children = new ArrayList<>();
    actualNodeView.setChildren(children);
    actualNodeView.setGroup("Group");
    actualNodeView.setNodeId("42");
    ArrayList<String> parents = new ArrayList<>();
    actualNodeView.setParents(parents);
    actualNodeView.setType("Type");
    String actualToStringResult = actualNodeView.toString();
    List<NodeView.ChildView> actualChildren = actualNodeView.getChildren();
    String actualGroup = actualNodeView.getGroup();
    String actualNodeId = actualNodeView.getNodeId();
    List<String> actualParents = actualNodeView.getParents();
    String actualType = actualNodeView.getType();
    actualNodeView.toBuilder();

    // Assert
    assertEquals("42", actualNodeId);
    assertEquals("Group", actualGroup);
    assertEquals("NodeView(nodeId=42, type=Type, group=Group, parents=[], children=[])", actualToStringResult);
    assertEquals("Type", actualType);
    assertTrue(actualChildren.isEmpty());
    assertTrue(actualParents.isEmpty());
    assertSame(children, actualChildren);
    assertSame(parents, actualParents);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView#NodeView(String, String, String, List, List)}
   *   <li>{@link NodeView#setChildren(List)}
   *   <li>{@link NodeView#setGroup(String)}
   *   <li>{@link NodeView#setNodeId(String)}
   *   <li>{@link NodeView#setParents(List)}
   *   <li>{@link NodeView#setType(String)}
   *   <li>{@link NodeView#toString()}
   *   <li>{@link NodeView#getChildren()}
   *   <li>{@link NodeView#getGroup()}
   *   <li>{@link NodeView#getNodeId()}
   *   <li>{@link NodeView#getParents()}
   *   <li>{@link NodeView#getType()}
   *   <li>{@link NodeView#toBuilder()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    ArrayList<String> parents = new ArrayList<>();

    // Act
    NodeView actualNodeView = new NodeView("42", "Type", "Group", parents, new ArrayList<>());
    ArrayList<NodeView.ChildView> children = new ArrayList<>();
    actualNodeView.setChildren(children);
    actualNodeView.setGroup("Group");
    actualNodeView.setNodeId("42");
    ArrayList<String> parents2 = new ArrayList<>();
    actualNodeView.setParents(parents2);
    actualNodeView.setType("Type");
    String actualToStringResult = actualNodeView.toString();
    List<NodeView.ChildView> actualChildren = actualNodeView.getChildren();
    String actualGroup = actualNodeView.getGroup();
    String actualNodeId = actualNodeView.getNodeId();
    List<String> actualParents = actualNodeView.getParents();
    String actualType = actualNodeView.getType();
    actualNodeView.toBuilder();

    // Assert
    assertEquals("42", actualNodeId);
    assertEquals("Group", actualGroup);
    assertEquals("NodeView(nodeId=42, type=Type, group=Group, parents=[], children=[])", actualToStringResult);
    assertEquals("Type", actualType);
    assertTrue(actualChildren.isEmpty());
    assertTrue(actualParents.isEmpty());
    assertSame(children, actualChildren);
    assertSame(parents2, actualParents);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView.NodeViewBuilder#build()}
   *   <li>{@link NodeView.NodeViewBuilder#children(List)}
   *   <li>{@link NodeView.NodeViewBuilder#group(String)}
   *   <li>{@link NodeView.NodeViewBuilder#nodeId(String)}
   *   <li>{@link NodeView.NodeViewBuilder#parents(List)}
   *   <li>{@link NodeView.NodeViewBuilder#type(String)}
   * </ul>
   */
  @Test
  void testNodeViewBuilderBuild() {
    // Arrange
    NodeView.NodeViewBuilder builderResult = NodeView.builder();
    ArrayList<NodeView.ChildView> children = new ArrayList<>();
    NodeView.NodeViewBuilder nodeIdResult = builderResult.children(children).group("Group").nodeId("42");
    ArrayList<String> parents = new ArrayList<>();

    // Act
    NodeView actualBuildResult = nodeIdResult.parents(parents).type("Type").build();

    // Assert
    assertEquals("42", actualBuildResult.getNodeId());
    assertEquals("Group", actualBuildResult.getGroup());
    assertEquals("Type", actualBuildResult.getType());
    List<NodeView.ChildView> children2 = actualBuildResult.getChildren();
    assertTrue(children2.isEmpty());
    List<String> parents2 = actualBuildResult.getParents();
    assertTrue(parents2.isEmpty());
    assertSame(children, children2);
    assertSame(parents, parents2);
  }
}
