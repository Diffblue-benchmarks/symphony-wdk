package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class WorkflowNodeBpmnBuilderRegistryDiffblueTest {
  /**
   * Test {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)}.
   * <ul>
   *   <li>When {@link WorkflowNode} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)}
   */
  @Test
  @DisplayName("Test getBuilder(WorkflowNode); when WorkflowNode (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilder WorkflowNodeBpmnBuilderRegistry.getBuilder(WorkflowNode)"})
  void testGetBuilder_whenWorkflowNode_thenReturnNull() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    WorkflowNodeBpmnBuilderRegistry workflowNodeBpmnBuilderRegistry = new WorkflowNodeBpmnBuilderRegistry(
        new ArrayList<>());

    // Act and Assert
    assertNull(workflowNodeBpmnBuilderRegistry.getBuilder(new WorkflowNode()));
  }
}
