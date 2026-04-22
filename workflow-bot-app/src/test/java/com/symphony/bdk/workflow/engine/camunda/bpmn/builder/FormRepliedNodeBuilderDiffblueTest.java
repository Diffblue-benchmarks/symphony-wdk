package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FormRepliedNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class FormRepliedNodeBuilderDiffblueTest {
  @Autowired private FormRepliedNodeBuilder formRepliedNodeBuilder;

  /**
   * Test {@link FormRepliedNodeBuilder#type()}.
   *
   * <p>Method under test: {@link FormRepliedNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodeType FormRepliedNodeBuilder.type()"})
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.FORM_REPLIED_EVENT, formRepliedNodeBuilder.type());
  }

  /**
   * Test {@link FormRepliedNodeBuilder.ThrowTimeoutDelegate#notify(org.camunda.bpm.engine.delegate.DelegateExecution)}.
   *
   * <p>Method under test: {@link FormRepliedNodeBuilder.ThrowTimeoutDelegate#notify(org.camunda.bpm.engine.delegate.DelegateExecution)}
   */
  @Test
  @DisplayName("Test ThrowTimeoutDelegate notify(DelegateExecution)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FormRepliedNodeBuilder.ThrowTimeoutDelegate.notify(DelegateExecution)"})
  void testThrowTimeoutDelegateNotify() {
    // Arrange
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate = new FormRepliedNodeBuilder.ThrowTimeoutDelegate();

    // Act and Assert
    BpmnError thrownError = assertThrows(BpmnError.class, () -> delegate.notify(null));
    assertEquals("408", thrownError.getErrorCode());
    assertEquals("Form reply event is timeout.", thrownError.getMessage());
  }
}
