package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FormRepliedNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class FormRepliedNodeBuilderDiffblueTest {
  @Autowired
  private FormRepliedNodeBuilder formRepliedNodeBuilder;

  /**
   * Test {@link FormRepliedNodeBuilder#type()}.
   * <p>
   * Method under test: {@link FormRepliedNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.FORM_REPLIED_EVENT, formRepliedNodeBuilder.type());
  }
}
