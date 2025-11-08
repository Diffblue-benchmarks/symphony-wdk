package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class WorkflowNodesStateViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesStateView#equals(Object)}
   *   <li>{@link WorkflowNodesStateView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(new ArrayList<>());

    WorkflowNodesStateView workflowNodesStateView2 = new WorkflowNodesStateView();
    workflowNodesStateView2.setError(new HashMap<>());
    workflowNodesStateView2.setGlobalVariables(new VariableView());
    workflowNodesStateView2.setNodes(new ArrayList<>());

    // Act and Assert
    assertEquals(workflowNodesStateView, workflowNodesStateView2);
    int expectedHashCodeResult = workflowNodesStateView.hashCode();
    assertEquals(expectedHashCodeResult, workflowNodesStateView2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowNodesStateView#equals(Object)}
   *   <li>{@link WorkflowNodesStateView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(new ArrayList<>());

    // Act and Assert
    assertEquals(workflowNodesStateView, workflowNodesStateView);
    int expectedHashCodeResult = workflowNodesStateView.hashCode();
    assertEquals(expectedHashCodeResult, workflowNodesStateView.hashCode());
  }

  /**
   * Method under test: {@link WorkflowNodesStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashMap<String, Object> error = new HashMap<>();
    error.put("foo", "42");

    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(error);
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(new ArrayList<>());

    WorkflowNodesStateView workflowNodesStateView2 = new WorkflowNodesStateView();
    workflowNodesStateView2.setError(new HashMap<>());
    workflowNodesStateView2.setGlobalVariables(new VariableView());
    workflowNodesStateView2.setNodes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(workflowNodesStateView, workflowNodesStateView2);
  }

  /**
   * Method under test: {@link WorkflowNodesStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> error = new HashMap<>();
    error.computeIfPresent("foo", mock(BiFunction.class));
    error.put("foo", "42");

    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(error);
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(new ArrayList<>());

    WorkflowNodesStateView workflowNodesStateView2 = new WorkflowNodesStateView();
    workflowNodesStateView2.setError(new HashMap<>());
    workflowNodesStateView2.setGlobalVariables(new VariableView());
    workflowNodesStateView2.setNodes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(workflowNodesStateView, workflowNodesStateView2);
  }

  /**
   * Method under test: {@link WorkflowNodesStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(null);
    workflowNodesStateView.setNodes(new ArrayList<>());

    WorkflowNodesStateView workflowNodesStateView2 = new WorkflowNodesStateView();
    workflowNodesStateView2.setError(new HashMap<>());
    workflowNodesStateView2.setGlobalVariables(new VariableView());
    workflowNodesStateView2.setNodes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(workflowNodesStateView, workflowNodesStateView2);
  }

  /**
   * Method under test: {@link WorkflowNodesStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(new VariableView(new VariablesDomain()));
    workflowNodesStateView.setNodes(new ArrayList<>());

    WorkflowNodesStateView workflowNodesStateView2 = new WorkflowNodesStateView();
    workflowNodesStateView2.setError(new HashMap<>());
    workflowNodesStateView2.setGlobalVariables(new VariableView());
    workflowNodesStateView2.setNodes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(workflowNodesStateView, workflowNodesStateView2);
  }

  /**
   * Method under test: {@link WorkflowNodesStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ArrayList<NodeStateView> nodes = new ArrayList<>();
    NodeStateView.NodeStateViewBuilder durationResult = NodeStateView.builder().duration(null);
    NodeStateView.NodeStateViewBuilder nodeIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .group("Group")
        .instanceId("42")
        .nodeId("42");
    NodeStateView.NodeStateViewBuilder outputsResult = nodeIdResult.outputs(new HashMap<>());
    NodeStateView buildResult = outputsResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    nodes.add(buildResult);

    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(nodes);

    WorkflowNodesStateView workflowNodesStateView2 = new WorkflowNodesStateView();
    workflowNodesStateView2.setError(new HashMap<>());
    workflowNodesStateView2.setGlobalVariables(new VariableView());
    workflowNodesStateView2.setNodes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(workflowNodesStateView, workflowNodesStateView2);
  }

  /**
   * Method under test: {@link WorkflowNodesStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(workflowNodesStateView, null);
  }

  /**
   * Method under test: {@link WorkflowNodesStateView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(workflowNodesStateView, "Different type to WorkflowNodesStateView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link WorkflowNodesStateView}
   *   <li>{@link WorkflowNodesStateView#setError(Map)}
   *   <li>{@link WorkflowNodesStateView#setGlobalVariables(VariableView)}
   *   <li>{@link WorkflowNodesStateView#setNodes(List)}
   *   <li>{@link WorkflowNodesStateView#toString()}
   *   <li>{@link WorkflowNodesStateView#getError()}
   *   <li>{@link WorkflowNodesStateView#getGlobalVariables()}
   *   <li>{@link WorkflowNodesStateView#getNodes()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    WorkflowNodesStateView actualWorkflowNodesStateView = new WorkflowNodesStateView();
    HashMap<String, Object> error = new HashMap<>();
    actualWorkflowNodesStateView.setError(error);
    VariableView globalVariables = new VariableView();
    actualWorkflowNodesStateView.setGlobalVariables(globalVariables);
    ArrayList<NodeStateView> nodes = new ArrayList<>();
    actualWorkflowNodesStateView.setNodes(nodes);
    String actualToStringResult = actualWorkflowNodesStateView.toString();
    Map<String, Object> actualError = actualWorkflowNodesStateView.getError();
    VariableView actualGlobalVariables = actualWorkflowNodesStateView.getGlobalVariables();
    List<NodeStateView> actualNodes = actualWorkflowNodesStateView.getNodes();

    // Assert that nothing has changed
    assertEquals(
        "WorkflowNodesStateView(nodes=[], globalVariables=VariableView(outputs=null, revision=0, updateTime=null),"
            + " error={})",
        actualToStringResult);
    assertTrue(actualNodes.isEmpty());
    assertTrue(actualError.isEmpty());
    assertSame(globalVariables, actualGlobalVariables);
    assertSame(nodes, actualNodes);
    assertSame(error, actualError);
  }
}
