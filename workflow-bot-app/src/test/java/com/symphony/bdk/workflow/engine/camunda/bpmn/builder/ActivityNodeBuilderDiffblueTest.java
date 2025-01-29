package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class ActivityNodeBuilderDiffblueTest {
  @Autowired
  private ActivityNodeBuilder activityNodeBuilder;

  /**
   * Test {@link ActivityNodeBuilder#type()}.
   * <p>
   * Method under test: {@link ActivityNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.ACTIVITY, activityNodeBuilder.type());
  }
}
