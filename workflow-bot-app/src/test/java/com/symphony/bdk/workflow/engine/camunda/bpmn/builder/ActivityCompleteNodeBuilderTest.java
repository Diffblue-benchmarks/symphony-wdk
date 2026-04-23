package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityCompleteNodeBuilderTest {

  @Test
  void shouldReturnActivityCompletedEventType() {
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    WorkflowNodeType type = builder.type();

    assertThat(type).isEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
  }
}
