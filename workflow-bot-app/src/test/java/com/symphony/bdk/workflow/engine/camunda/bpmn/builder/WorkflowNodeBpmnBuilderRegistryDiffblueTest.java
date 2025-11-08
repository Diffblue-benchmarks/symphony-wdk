package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WorkflowNodeBpmnBuilderRegistryDiffblueTest {
  @Autowired
  private List<WorkflowNodeBpmnBuilder> list;

  @MockBean
  private WorkflowNodeBpmnBuilder workflowNodeBpmnBuilder;

  @MockBean
  private WorkflowNodeBpmnBuilderRegistry workflowNodeBpmnBuilderRegistry;

  /**
   * Method under test:
   * {@link WorkflowNodeBpmnBuilderRegistry#getBuilder(WorkflowNode)}
   */
  @Test
  void testGetBuilder() {
    // Arrange
    when(workflowNodeBpmnBuilderRegistry.getBuilder(Mockito.<WorkflowNode>any())).thenReturn(workflowNodeBpmnBuilder);

    // Act
    workflowNodeBpmnBuilderRegistry.getBuilder(new WorkflowNode());

    // Assert
    verify(workflowNodeBpmnBuilderRegistry).getBuilder(isA(WorkflowNode.class));
  }
}
