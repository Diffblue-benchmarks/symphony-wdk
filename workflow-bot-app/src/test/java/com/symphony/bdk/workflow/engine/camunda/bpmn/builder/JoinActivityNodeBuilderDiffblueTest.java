package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JoinActivityNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class JoinActivityNodeBuilderDiffblueTest {
  @Autowired
  private JoinActivityNodeBuilder joinActivityNodeBuilder;

  /**
   * Method under test: {@link JoinActivityNodeBuilder#type()}
   */
  @Test
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.JOIN_ACTIVITY, joinActivityNodeBuilder.type());
  }
}
