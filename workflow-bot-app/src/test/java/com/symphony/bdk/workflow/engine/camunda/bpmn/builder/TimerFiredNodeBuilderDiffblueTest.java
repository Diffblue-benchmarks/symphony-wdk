package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TimerFiredNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class TimerFiredNodeBuilderDiffblueTest {
  @Autowired
  private TimerFiredNodeBuilder timerFiredNodeBuilder;

  /**
   * Method under test: {@link TimerFiredNodeBuilder#type()}
   */
  @Test
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.TIMER_FIRED_EVENT, timerFiredNodeBuilder.type());
  }
}
