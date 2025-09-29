package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SignalNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class SignalNodeBuilderDiffblueTest {
  @Autowired private SignalNodeBuilder signalNodeBuilder;

  /**
   * Test {@link SignalNodeBuilder#type()}.
   *
   * <p>Method under test: {@link SignalNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodeType SignalNodeBuilder.type()"})
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.SIGNAL_EVENT, signalNodeBuilder.type());
  }
}
