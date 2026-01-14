package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityExpiredNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class ActivityExpiredNodeBuilderDiffblueTest {
  @Autowired private ActivityExpiredNodeBuilder activityExpiredNodeBuilder;

  /**
   * Test {@link ActivityExpiredNodeBuilder#type()}.
   *
   * <p>Method under test: {@link ActivityExpiredNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  @Tag("MaintainedByDiffblue")
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT, activityExpiredNodeBuilder.type());
  }
}
