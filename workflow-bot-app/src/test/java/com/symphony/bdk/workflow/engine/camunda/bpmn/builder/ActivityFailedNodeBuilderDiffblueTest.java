package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityFailedNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class ActivityFailedNodeBuilderDiffblueTest {
  @Autowired
  private ActivityFailedNodeBuilder activityFailedNodeBuilder;

  /**
   * Test {@link ActivityFailedNodeBuilder#type()}.
   * <p>
   * Method under test: {@link ActivityFailedNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodeType ActivityFailedNodeBuilder.type()"})
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.ACTIVITY_FAILED_EVENT, activityFailedNodeBuilder.type());
  }
}
