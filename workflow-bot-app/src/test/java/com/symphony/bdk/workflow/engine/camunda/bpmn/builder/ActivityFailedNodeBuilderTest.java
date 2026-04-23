package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.assertj.core.api.Assertions.assertThat;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.junit.jupiter.api.Test;

class ActivityFailedNodeBuilderTest {

  private final ActivityFailedNodeBuilder underTest = new ActivityFailedNodeBuilder();

  @Test
  void shouldReturnActivityFailedEventType() {
    assertThat(underTest.type()).isEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
  }

  @Test
  void shouldConnectToExistingNodeWithBoundaryErrorEvent() {
    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("process")
        .startEvent()
        .serviceTask("existingTask");

    underTest.connectToExistingNode("existingTask", builder);

    BpmnModelInstance model = builder.done();
    assertThat(model).isNotNull();
  }

  @Test
  void shouldBuildBoundaryErrorEventWithScriptTask() {
    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("process")
        .startEvent()
        .serviceTask("parentTask");

    ExecuteScript script = new ExecuteScript();
    script.setId("scriptActivity");
    script.setScript("println 'hello'");

    WorkflowNode element = new WorkflowNode()
        .id("scriptActivity")
        .activity(script);

    AbstractFlowNodeBuilder<?, ?> result = underTest.build(element, "parentTask", builder, null);

    assertThat(result).isNotNull();
  }
}
