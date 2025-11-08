package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WorkflowNodesViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesView#equals(Object)}
   *   <li>{@link WorkflowNodesView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult2 = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult2 = builderResult2.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesView#equals(Object)}
   *   <li>{@link WorkflowNodesView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(WorkflowNodesView.builder());
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.variables(Mockito.<Map<String, Object>>any()))
        .thenReturn(WorkflowNodesView.builder());
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder2.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.version(Mockito.<Long>any())).thenReturn(WorkflowNodesView.builder());
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.variables(Mockito.<Map<String, Object>>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder3 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder3.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder2);
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder3.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.version(Mockito.<Long>any())).thenReturn(WorkflowNodesView.builder());
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.variables(Mockito.<Map<String, Object>>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder3 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder3.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder2);
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder3.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(null).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder.workflowId(Mockito.<String>any())).thenReturn(WorkflowNodesView.builder());
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.version(Mockito.<Long>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder3 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder3.variables(Mockito.<Map<String, Object>>any())).thenReturn(workflowNodesViewBuilder2);
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder4 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder4.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder3);
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = workflowNodesViewBuilder4.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult2 = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(null).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();
    when(workflowNodesViewBuilder.build()).thenReturn(buildResult);
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder2 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder2.workflowId(Mockito.<String>any())).thenReturn(workflowNodesViewBuilder);
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder3 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder3.version(Mockito.<Long>any())).thenReturn(workflowNodesViewBuilder2);
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder4 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder4.variables(Mockito.<Map<String, Object>>any())).thenReturn(workflowNodesViewBuilder3);
    WorkflowNodesView.WorkflowNodesViewBuilder workflowNodesViewBuilder5 = mock(
        WorkflowNodesView.WorkflowNodesViewBuilder.class);
    when(workflowNodesViewBuilder5.flowNodes(Mockito.<List<NodeView>>any())).thenReturn(workflowNodesViewBuilder4);
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult2 = workflowNodesViewBuilder5
        .flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult2 = flowNodesResult2.variables(new HashMap<>()).version(1L).workflowId("42").build();
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult2 = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult3 = builderResult2.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult3 = flowNodesResult3.variables(new HashMap<>()).version(null).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link WorkflowNodesView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    WorkflowNodesView buildResult = flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to WorkflowNodesView");
  }

  /**
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

    // Assert that nothing has changed
    assertEquals("42", actualWorkflowNodesView.getWorkflowId());
    assertEquals("WorkflowNodesView(workflowId=42, version=1, variables={}, flowNodes=[])", actualToStringResult);
    assertEquals(1L, actualVersion.longValue());
    assertTrue(actualFlowNodes.isEmpty());
    assertTrue(actualVariables.isEmpty());
    assertSame(flowNodes, actualFlowNodes);
    assertSame(variables2, actualVariables);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesView.WorkflowNodesViewBuilder#build()}
   *   <li>{@link WorkflowNodesView.WorkflowNodesViewBuilder#flowNodes(List)}
   *   <li>{@link WorkflowNodesView.WorkflowNodesViewBuilder#variables(Map)}
   *   <li>{@link WorkflowNodesView.WorkflowNodesViewBuilder#version(Long)}
   *   <li>{@link WorkflowNodesView.WorkflowNodesViewBuilder#workflowId(String)}
   * </ul>
   */
  @Test
  void testWorkflowNodesViewBuilderBuild() {
    // Arrange
    WorkflowNodesView.WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();
    ArrayList<NodeView> flowNodes = new ArrayList<>();
    WorkflowNodesView.WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(flowNodes);
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
