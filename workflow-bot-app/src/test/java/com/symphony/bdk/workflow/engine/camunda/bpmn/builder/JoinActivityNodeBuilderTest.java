package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.assertj.core.api.Assertions.assertThat;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.junit.jupiter.api.Test;

class JoinActivityNodeBuilderTest {

  private final JoinActivityNodeBuilder underTest = new JoinActivityNodeBuilder();

  @Test
  void shouldReturnJoinActivityType() {
    assertThat(underTest.type()).isEqualTo(WorkflowNodeType.JOIN_ACTIVITY);
  }

  @Test
  void shouldBuildParallelGateway() {
    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("process")
        .startEvent();

    WorkflowNode element = new WorkflowNode().id("joinGateway");

    AbstractFlowNodeBuilder<?, ?> result = underTest.build(element, null, builder, null);

    assertThat(result).isNotNull();
  }
}
