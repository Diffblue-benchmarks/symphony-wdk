package com.symphony.bdk.workflow.engine.camunda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.jupiter.api.Test;

class CamundaTranslatedWorkflowContextTest {

  @Test
  void shouldInitializeAllFieldsWhenConstructed() {
    Workflow workflow = mock(Workflow.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    BpmnModelInstance instance = mock(BpmnModelInstance.class);

    CamundaTranslatedWorkflowContext context =
        new CamundaTranslatedWorkflowContext(workflow, graph, instance);

    assertThat(context.getWorkflow()).isEqualTo(workflow);
    assertThat(context.getWorkflowDirectedGraph()).isEqualTo(graph);
    assertThat(context.getBpmnModelInstance()).isEqualTo(instance);
  }

  @Test
  void shouldReturnBpmnModelInstanceWhenGetterCalled() {
    Workflow workflow = mock(Workflow.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    BpmnModelInstance instance = mock(BpmnModelInstance.class);

    CamundaTranslatedWorkflowContext context =
        new CamundaTranslatedWorkflowContext(workflow, graph, instance);

    assertThat(context.getBpmnModelInstance()).isSameAs(instance);
  }

  @Test
  void shouldBeEqualToItselfWhenCompared() {
    Workflow workflow = mock(Workflow.class);
    WorkflowDirectedGraph graph = mock(WorkflowDirectedGraph.class);
    BpmnModelInstance instance = mock(BpmnModelInstance.class);

    CamundaTranslatedWorkflowContext context =
        new CamundaTranslatedWorkflowContext(workflow, graph, instance);

    assertThat(context).isEqualTo(context);
    assertThat(context.hashCode()).isEqualTo(context.hashCode());
  }
}
