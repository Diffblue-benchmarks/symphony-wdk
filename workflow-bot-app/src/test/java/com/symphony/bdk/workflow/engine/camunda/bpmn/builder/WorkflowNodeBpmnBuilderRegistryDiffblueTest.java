package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class WorkflowNodeBpmnBuilderRegistryDiffblueTest {
  /**
   * Test {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)}
   */
  @Test
  @DisplayName("Test getBuilder(WorkflowNode); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilder WorkflowNodeBpmnBuilderRegistry.getBuilder(WorkflowNode)"
  })
  void testGetBuilder_thenReturnNull() {
    // Arrange
    WorkflowNodeBpmnBuilderRegistry workflowNodeBpmnBuilderRegistry =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());

    // Act and Assert
    WorkflowNode node = new WorkflowNode();
    node.setElementType(WorkflowNodeType.ACTIVITY);
    assertNull(workflowNodeBpmnBuilderRegistry.getBuilder(node));
  }

  /**
   * Test {@link WorkflowNodeBpmnBuilderRegistry#WorkflowNodeBpmnBuilderRegistry(List)}.
   *
   * <p>Method under test: constructor
   */
  @Test
  @DisplayName("Test constructor with builders; populates factory")
  void testConstructor_withBuilders() {
    // Arrange
    WorkflowNodeBpmnBuilder builder = mock(WorkflowNodeBpmnBuilder.class);
    when(builder.type()).thenReturn(WorkflowNodeType.ACTIVITY);

    // Act
    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(List.of(builder));

    // Assert
    WorkflowNode node = new WorkflowNode();
    node.setElementType(WorkflowNodeType.ACTIVITY);
    assertSame(builder, registry.getBuilder(node));
  }

  /**
   * Test {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)} when builder is registered.
   *
   * <p>Method under test: {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)}
   */
  @Test
  @DisplayName("Test getBuilder(WorkflowNode); returns registered builder")
  void testGetBuilder_returnsRegisteredBuilder() {
    // Arrange
    WorkflowNodeBpmnBuilder mockBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(mockBuilder.type()).thenReturn(WorkflowNodeType.SIGNAL_EVENT);
    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(List.of(mockBuilder));

    WorkflowNode node = new WorkflowNode();
    node.setElementType(WorkflowNodeType.SIGNAL_EVENT);

    // Act and Assert
    assertSame(mockBuilder, registry.getBuilder(node));
  }
}
