package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WorkflowNodeBpmnBuilderRegistryDiffblueTest {
  @Mock private List<WorkflowNodeBpmnBuilder> list;

  @InjectMocks private WorkflowNodeBpmnBuilderRegistry workflowNodeBpmnBuilderRegistry;

  /**
   * Test {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)}.
   *
   * <ul>
   *   <li>When {@link WorkflowNode} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)}
   */
  @Test
  @DisplayName(
      "Test getBuilder(WorkflowNode); when WorkflowNode (default constructor); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WorkflowNodeBpmnBuilder WorkflowNodeBpmnBuilderRegistry.getBuilder(WorkflowNode)"
  })
  void testGetBuilder_whenWorkflowNode_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(workflowNodeBpmnBuilderRegistry.getBuilder(new WorkflowNode()));
  }
}
