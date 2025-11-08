package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView.WorkflowNodesViewBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowNodesViewBuilder.class})
@ExtendWith(SpringExtension.class)
class WorkflowNodesViewDiffblueTest {
  @Autowired
  private WorkflowNodesViewBuilder workflowNodesViewBuilder;

  /**
   * Test {@link WorkflowNodesView#equals(Object)}, and {@link WorkflowNodesView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesView#equals(Object)}
   *   <li>{@link WorkflowNodesView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesViewBuilder builderResult2 = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult2 = builderResult2.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}, and {@link WorkflowNodesView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesView#equals(Object)}
   *   <li>{@link WorkflowNodesView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(WorkflowNodesView.builder());
    WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.variables(Mockito.<Map<String, Object>>any()))
        .thenReturn(WorkflowNodesView.builder());
    WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder2.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.version(Mockito.<Long>any())).thenReturn(WorkflowNodesView.builder());
    WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.variables(Mockito.<Map<String, Object>>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesViewBuilder workflowNodesViewBuilder3 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder3.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder2);
    WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder3.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.version(Mockito.<Long>any())).thenReturn(WorkflowNodesView.builder());
    WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.variables(Mockito.<Map<String, Object>>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesViewBuilder workflowNodesViewBuilder3 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder3.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder2);
    WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder3.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(null).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.workflowId(Mockito.<String>any())).thenReturn(WorkflowNodesView.builder());
    WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.version(Mockito.<Long>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesViewBuilder workflowNodesViewBuilder3 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder3.variables(Mockito.<Map<String, Object>>any())).thenReturn(workflowNodesViewBuilder2);
    WorkflowNodesViewBuilder workflowNodesViewBuilder4 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder4.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder3);
    WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder4.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(null).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(WorkflowNodesViewBuilder.class);
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    when(workflowNodesViewBuilder.build()).thenReturn(buildResult);
    WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.workflowId(Mockito.<String>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesViewBuilder workflowNodesViewBuilder3 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder3.version(Mockito.<Long>any())).thenReturn(workflowNodesViewBuilder2);
    WorkflowNodesViewBuilder workflowNodesViewBuilder4 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder4.variables(Mockito.<Map<String, Object>>any())).thenReturn(workflowNodesViewBuilder3);
    WorkflowNodesViewBuilder workflowNodesViewBuilder5 = mock(WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder5.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder4);
    WorkflowNodesViewBuilder flowNodesResult2 = workflowNodesViewBuilder5.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesViewBuilder builderResult2 = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult3 = builderResult2.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult3 = flowNodesResult3.variables(new HashMap<>()).version(null).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link WorkflowNodesView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowNodesView.equals(Object)", "int WorkflowNodesView.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to WorkflowNodesView");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesView#WorkflowNodesView(String, Long, Map, List)}
   *   <li>{@link WorkflowNodesView#setFlowNodes(List)}
   *   <li>{@link WorkflowNodesView#setVariables(Map)}
   *   <li>{@link WorkflowNodesView#setVersion(Long)}
   *   <li>{@link WorkflowNodesView#setWorkflowId(String)}
   *   <li>{@link WorkflowNodesView#toString()}
   *   <li>{@link WorkflowNodesView#getFlowNodes()}
   *   <li>{@link WorkflowNodesView#getVariables()}
   *   <li>{@link WorkflowNodesView#getVersion()}
   *   <li>{@link WorkflowNodesView#getWorkflowId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowNodesView.<init>(String, Long, Map, List)", "List WorkflowNodesView.getFlowNodes()",
      "Map WorkflowNodesView.getVariables()", "Long WorkflowNodesView.getVersion()",
      "String WorkflowNodesView.getWorkflowId()", "void WorkflowNodesView.setFlowNodes(List)",
      "void WorkflowNodesView.setVariables(Map)", "void WorkflowNodesView.setVersion(Long)",
      "void WorkflowNodesView.setWorkflowId(String)", "String WorkflowNodesView.toString()"})
  void testGettersAndSetters() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    WorkflowNodesView actualWorkflowNodesView = new WorkflowNodesView("42", 1L, variables, new ArrayList<>());
    ArrayList<NodeView> flowNodes = new ArrayList<>();
    actualWorkflowNodesView.setFlowNodes(flowNodes);
    HashMap<String, Object> variables2 = new HashMap<>();
    actualWorkflowNodesView.setVariables(variables2);
    actualWorkflowNodesView.setVersion(1L);
    actualWorkflowNodesView.setWorkflowId("42");
    String actualToStringResult = actualWorkflowNodesView.toString();
    List<NodeView> actualFlowNodes = actualWorkflowNodesView.getFlowNodes();
    Map<String, Object> actualVariables = actualWorkflowNodesView.getVariables();
    Long actualVersion = actualWorkflowNodesView.getVersion();

    // Assert
    assertEquals("42", actualWorkflowNodesView.getWorkflowId());
    assertEquals("WorkflowNodesView(workflowId=42, version=1, variables={}, flowNodes=[])", actualToStringResult);
    assertEquals(1L, actualVersion.longValue());
    assertTrue(actualFlowNodes.isEmpty());
    assertTrue(actualVariables.isEmpty());
    assertSame(flowNodes, actualFlowNodes);
    assertSame(variables2, actualVariables);
  }

  /**
   * Test WorkflowNodesViewBuilder {@link WorkflowNodesViewBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesViewBuilder#build()}
   *   <li>{@link WorkflowNodesViewBuilder#flowNodes(List)}
   *   <li>{@link WorkflowNodesViewBuilder#variables(Map)}
   *   <li>{@link WorkflowNodesViewBuilder#version(Long)}
   *   <li>{@link WorkflowNodesViewBuilder#workflowId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test WorkflowNodesViewBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowNodesViewBuilder.<init>()", "WorkflowNodesView WorkflowNodesViewBuilder.build()",
      "WorkflowNodesViewBuilder WorkflowNodesViewBuilder.flowNodes(List)", "String WorkflowNodesViewBuilder.toString()",
      "WorkflowNodesViewBuilder WorkflowNodesViewBuilder.variables(Map)",
      "WorkflowNodesViewBuilder WorkflowNodesViewBuilder.version(Long)",
      "WorkflowNodesViewBuilder WorkflowNodesViewBuilder.workflowId(String)"})
  void testWorkflowNodesViewBuilderBuild() {
    // Arrange
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    ArrayList<NodeView> flowNodes = new ArrayList<>();
    WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(flowNodes);
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    WorkflowNodesView actualBuildResult = flowNodesResult.variables(variables).version(1L).workflowId("42").build();

    // Assert
    assertEquals("42", actualBuildResult.getWorkflowId());
    assertEquals(1L, actualBuildResult.getVersion().longValue());
    List<NodeView> flowNodes2 = actualBuildResult.getFlowNodes();
    assertTrue(flowNodes2.isEmpty());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(flowNodes, flowNodes2);
    assertSame(variables, variables2);
  }
}
