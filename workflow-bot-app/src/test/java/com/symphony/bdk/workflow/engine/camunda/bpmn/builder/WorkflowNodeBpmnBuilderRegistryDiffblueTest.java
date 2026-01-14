package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
  @Tag("MaintainedByDiffblue")
  void testGetBuilder_thenReturnNull() {
    // Arrange
    WorkflowNodeBpmnBuilderRegistry workflowNodeBpmnBuilderRegistry =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());

    // Act and Assert
    assertNull(workflowNodeBpmnBuilderRegistry.getBuilder(new WorkflowNode()));
  }
}
