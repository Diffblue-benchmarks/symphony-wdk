package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityCompleteNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class ActivityCompleteNodeBuilderDiffblueTest {
  @Autowired
  private ActivityCompleteNodeBuilder activityCompleteNodeBuilder;

  /**
   * Method under test: {@link ActivityCompleteNodeBuilder#type()}
   */
  @Test
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT, activityCompleteNodeBuilder.type());
  }
}
