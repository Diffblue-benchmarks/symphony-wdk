package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.NodeView.ChildView;
import com.symphony.bdk.workflow.api.v1.dto.NodeView.NodeViewBuilder;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NodeViewBuilder.class})
@ExtendWith(SpringExtension.class)
class NodeViewDiffblueTest {
  @Autowired
  private NodeViewBuilder nodeViewBuilder;

  /**
   * Test ChildView {@link ChildView#equals(Object)}, and {@link ChildView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ChildView#equals(Object)}
   *   <li>{@link ChildView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ChildView equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ChildView ofResult = ChildView.of("42");
    ChildView ofResult2 = ChildView.of("42");

    // Act and Assert
    assertEquals(ofResult, ofResult2);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult2.hashCode());
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}, and {@link ChildView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ChildView#equals(Object)}
   *   <li>{@link ChildView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ChildView equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ChildView ofResult = ChildView.of(null);
    ChildView ofResult2 = ChildView.of(null);

    // Act and Assert
    assertEquals(ofResult, ofResult2);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult2.hashCode());
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}, and {@link ChildView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ChildView#equals(Object)}
   *   <li>{@link ChildView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ChildView equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    ChildView ofResult = ChildView.of("42", "42");
    ChildView ofResult2 = ChildView.of("42", "42");

    // Act and Assert
    assertEquals(ofResult, ofResult2);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult2.hashCode());
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}, and {@link ChildView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ChildView#equals(Object)}
   *   <li>{@link ChildView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test ChildView equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ChildView ofResult = ChildView.of("42");

    // Act and Assert
    assertEquals(ofResult, ofResult);
    int expectedHashCodeResult = ofResult.hashCode();
    assertEquals(expectedHashCodeResult, ofResult.hashCode());
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ChildView#equals(Object)}
   */
  @Test
  @DisplayName("Test ChildView equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ChildView ofResult = ChildView.of("Node Id");

    // Act and Assert
    assertNotEquals(ofResult, ChildView.of("42"));
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ChildView#equals(Object)}
   */
  @Test
  @DisplayName("Test ChildView equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ChildView ofResult = ChildView.of(null);

    // Act and Assert
    assertNotEquals(ofResult, ChildView.of("42"));
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ChildView#equals(Object)}
   */
  @Test
  @DisplayName("Test ChildView equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ChildView ofResult = ChildView.of("42", "42");

    // Act and Assert
    assertNotEquals(ofResult, ChildView.of("42"));
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ChildView#equals(Object)}
   */
  @Test
  @DisplayName("Test ChildView equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ChildView ofResult = ChildView.of("42");

    // Act and Assert
    assertNotEquals(ofResult, ChildView.of("42", "42"));
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ChildView#equals(Object)}
   */
  @Test
  @DisplayName("Test ChildView equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(ChildView.of("42"), null);
  }

  /**
   * Test ChildView {@link ChildView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ChildView#equals(Object)}
   */
  @Test
  @DisplayName("Test ChildView equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ChildView.equals(Object)", "int ChildView.hashCode()"})
  void testChildViewEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(ChildView.of("42"), "Different type to ChildView");
  }

  /**
   * Test ChildView getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ChildView#ChildView()}
   *   <li>{@link ChildView#setCondition(String)}
   *   <li>{@link ChildView#setNodeId(String)}
   *   <li>{@link ChildView#toString()}
   *   <li>{@link ChildView#getCondition()}
   *   <li>{@link ChildView#getNodeId()}
   * </ul>
   */
  @Test
  @DisplayName("Test ChildView getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ChildView.<init>()", "void ChildView.<init>(String)", "String ChildView.getCondition()",
      "String ChildView.getNodeId()", "void ChildView.setCondition(String)", "void ChildView.setNodeId(String)",
      "String ChildView.toString()"})
  void testChildViewGettersAndSetters() {
    // Arrange and Act
    ChildView actualChildView = new ChildView();
    actualChildView.setCondition("Condition");
    actualChildView.setNodeId("42");
    String actualToStringResult = actualChildView.toString();
    String actualCondition = actualChildView.getCondition();

    // Assert
    assertEquals("42", actualChildView.getNodeId());
    assertEquals("Condition", actualCondition);
    assertEquals("NodeView.ChildView(nodeId=42, condition=Condition)", actualToStringResult);
  }

  /**
   * Test ChildView getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ChildView#ChildView(String)}
   *   <li>{@link ChildView#setCondition(String)}
   *   <li>{@link ChildView#setNodeId(String)}
   *   <li>{@link ChildView#toString()}
   *   <li>{@link ChildView#getCondition()}
   *   <li>{@link ChildView#getNodeId()}
   * </ul>
   */
  @Test
  @DisplayName("Test ChildView getters and setters; when '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ChildView.<init>()", "void ChildView.<init>(String)", "String ChildView.getCondition()",
      "String ChildView.getNodeId()", "void ChildView.setCondition(String)", "void ChildView.setNodeId(String)",
      "String ChildView.toString()"})
  void testChildViewGettersAndSetters_when42() {
    // Arrange and Act
    ChildView actualChildView = new ChildView("42");
    actualChildView.setCondition("Condition");
    actualChildView.setNodeId("42");
    String actualToStringResult = actualChildView.toString();
    String actualCondition = actualChildView.getCondition();

    // Assert
    assertEquals("42", actualChildView.getNodeId());
    assertEquals("Condition", actualCondition);
    assertEquals("NodeView.ChildView(nodeId=42, condition=Condition)", actualToStringResult);
  }

  /**
   * Test ChildView {@link ChildView#of(String)} with {@code nodeId}.
   * <p>
   * Method under test: {@link ChildView#of(String)}
   */
  @Test
  @DisplayName("Test ChildView of(String) with 'nodeId'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ChildView ChildView.of(String)"})
  void testChildViewOfWithNodeId() {
    // Arrange and Act
    ChildView actualOfResult = ChildView.of("42");

    // Assert
    assertEquals("42", actualOfResult.getNodeId());
    assertNull(actualOfResult.getCondition());
  }

  /**
   * Test ChildView {@link ChildView#of(String, String)} with {@code nodeId}, {@code condition}.
   * <p>
   * Method under test: {@link ChildView#of(String, String)}
   */
  @Test
  @DisplayName("Test ChildView of(String, String) with 'nodeId', 'condition'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ChildView ChildView.of(String, String)"})
  void testChildViewOfWithNodeIdCondition() {
    // Arrange and Act
    ChildView actualOfResult = ChildView.of("42", "Condition");

    // Assert
    assertEquals("42", actualOfResult.getNodeId());
    assertEquals("Condition", actualOfResult.getCondition());
  }

  /**
   * Test {@link NodeView#equals(Object)}, and {@link NodeView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView#equals(Object)}
   *   <li>{@link NodeView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult2 = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult2.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link NodeView#equals(Object)}, and {@link NodeView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeView#equals(Object)}
   *   <li>{@link NodeView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.children(Mockito.<List<ChildView>>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeIdResult = nodeViewBuilder.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.group(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeIdResult = nodeViewBuilder2.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type(null).build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("42").build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group(null).nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    NodeViewBuilder builderResult = NodeView.builder();
    builderResult.nodeId("42");
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(builderResult);
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult2 = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult2.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    NodeViewBuilder builderResult = NodeView.builder();
    builderResult.group("Type");
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(builderResult);
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult2 = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult2.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.nodeId(Mockito.<String>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.group(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeIdResult = nodeViewBuilder3.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type(null).build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group("Group").nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type(null).build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    NodeViewBuilder nodeViewBuilder = mock(NodeViewBuilder.class);
    when(nodeViewBuilder.parents(Mockito.<List<String>>any())).thenReturn(NodeView.builder());
    NodeViewBuilder nodeViewBuilder2 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder2.nodeId(Mockito.<String>any())).thenReturn(nodeViewBuilder);
    NodeViewBuilder nodeViewBuilder3 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder3.group(Mockito.<String>any())).thenReturn(nodeViewBuilder2);
    NodeViewBuilder nodeViewBuilder4 = mock(NodeViewBuilder.class);
    when(nodeViewBuilder4.children(Mockito.<List<ChildView>>any())).thenReturn(nodeViewBuilder3);
    NodeViewBuilder nodeIdResult = nodeViewBuilder4.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult2 = builderResult.children(new ArrayList<>()).group(null).nodeId(null);
    NodeView buildResult2 = nodeIdResult2.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link NodeView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link NodeView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean NodeView.equals(Object)", "int NodeView.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    NodeViewBuilder builderResult = NodeView.builder();
    NodeViewBuilder nodeIdResult = builderResult.children(new ArrayList<>()).group("Group").nodeId("42");
    NodeView buildResult = nodeIdResult.parents(new ArrayList<>()).type("Type").build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to NodeView");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NodeView.<init>()", "void NodeView.<init>(String, String, String, List, List)",
      "List NodeView.getChildren()", "String NodeView.getGroup()", "String NodeView.getNodeId()",
      "List NodeView.getParents()", "String NodeView.getType()", "void NodeView.setChildren(List)",
      "void NodeView.setGroup(String)", "void NodeView.setNodeId(String)", "void NodeView.setParents(List)",
      "void NodeView.setType(String)", "NodeViewBuilder NodeView.toBuilder()", "String NodeView.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    NodeView actualNodeView = new NodeView();
    ArrayList<ChildView> children = new ArrayList<>();
    actualNodeView.setChildren(children);
    actualNodeView.setGroup("Group");
    actualNodeView.setNodeId("42");
    ArrayList<String> parents = new ArrayList<>();
    actualNodeView.setParents(parents);
    actualNodeView.setType("Type");
    String actualToStringResult = actualNodeView.toString();
    List<ChildView> actualChildren = actualNodeView.getChildren();
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
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
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
  @DisplayName("Test getters and setters; when '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NodeView.<init>()", "void NodeView.<init>(String, String, String, List, List)",
      "List NodeView.getChildren()", "String NodeView.getGroup()", "String NodeView.getNodeId()",
      "List NodeView.getParents()", "String NodeView.getType()", "void NodeView.setChildren(List)",
      "void NodeView.setGroup(String)", "void NodeView.setNodeId(String)", "void NodeView.setParents(List)",
      "void NodeView.setType(String)", "NodeViewBuilder NodeView.toBuilder()", "String NodeView.toString()"})
  void testGettersAndSetters_when42() {
    // Arrange
    ArrayList<String> parents = new ArrayList<>();

    // Act
    NodeView actualNodeView = new NodeView("42", "Type", "Group", parents, new ArrayList<>());
    ArrayList<ChildView> children = new ArrayList<>();
    actualNodeView.setChildren(children);
    actualNodeView.setGroup("Group");
    actualNodeView.setNodeId("42");
    ArrayList<String> parents2 = new ArrayList<>();
    actualNodeView.setParents(parents2);
    actualNodeView.setType("Type");
    String actualToStringResult = actualNodeView.toString();
    List<ChildView> actualChildren = actualNodeView.getChildren();
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
   * Test NodeViewBuilder {@link NodeViewBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NodeViewBuilder#build()}
   *   <li>{@link NodeViewBuilder#children(List)}
   *   <li>{@link NodeViewBuilder#group(String)}
   *   <li>{@link NodeViewBuilder#nodeId(String)}
   *   <li>{@link NodeViewBuilder#parents(List)}
   *   <li>{@link NodeViewBuilder#type(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test NodeViewBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NodeViewBuilder.<init>()", "NodeView NodeViewBuilder.build()",
      "NodeViewBuilder NodeViewBuilder.children(List)", "NodeViewBuilder NodeViewBuilder.group(String)",
      "NodeViewBuilder NodeViewBuilder.nodeId(String)", "NodeViewBuilder NodeViewBuilder.parents(List)",
      "String NodeViewBuilder.toString()", "NodeViewBuilder NodeViewBuilder.type(String)"})
  void testNodeViewBuilderBuild() {
    // Arrange
    NodeViewBuilder builderResult = NodeView.builder();
    ArrayList<ChildView> children = new ArrayList<>();
    NodeViewBuilder nodeIdResult = builderResult.children(children).group("Group").nodeId("42");
    ArrayList<String> parents = new ArrayList<>();

    // Act
    NodeView actualBuildResult = nodeIdResult.parents(parents).type("Type").build();

    // Assert
    assertEquals("42", actualBuildResult.getNodeId());
    assertEquals("Group", actualBuildResult.getGroup());
    assertEquals("Type", actualBuildResult.getType());
    List<ChildView> children2 = actualBuildResult.getChildren();
    assertTrue(children2.isEmpty());
    List<String> parents2 = actualBuildResult.getParents();
    assertTrue(parents2.isEmpty());
    assertSame(children, children2);
    assertSame(parents, parents2);
  }
}
