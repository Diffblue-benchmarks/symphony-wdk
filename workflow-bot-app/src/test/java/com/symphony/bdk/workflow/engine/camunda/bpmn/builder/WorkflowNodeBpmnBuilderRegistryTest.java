package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkflowNodeBpmnBuilderRegistryTest {

  @Test
  void shouldRegisterBuildersOnConstruction() {
    WorkflowNodeBpmnBuilder activityBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(activityBuilder.type()).thenReturn(WorkflowNodeType.ACTIVITY);

    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(List.of(activityBuilder));

    WorkflowNode element = new WorkflowNode().elementType(WorkflowNodeType.ACTIVITY);
    assertThat(registry.getBuilder(element)).isEqualTo(activityBuilder);
  }

  @Test
  void shouldReturnNullForUnregisteredType() {
    WorkflowNodeBpmnBuilder activityBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(activityBuilder.type()).thenReturn(WorkflowNodeType.ACTIVITY);

    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(List.of(activityBuilder));

    WorkflowNode element = new WorkflowNode().elementType(WorkflowNodeType.SIGNAL_EVENT);
    assertThat(registry.getBuilder(element)).isNull();
  }
}
