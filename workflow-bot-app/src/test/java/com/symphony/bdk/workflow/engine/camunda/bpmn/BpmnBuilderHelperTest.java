package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BpmnBuilderHelperTest {

  // ---- hasActivitiesOnly ----

  @Test
  void shouldReturnTrueWhenAllChildrenAreActivities() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-1");
    WorkflowNode activityNode = new WorkflowNode().id("act-1").elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("act-1", activityNode);
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("act-1"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-1"));

    // when
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, children);

    // then
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenChildIsSignalEvent() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-2");
    WorkflowNode signalNode = new WorkflowNode().id("sig-1").elementType(WorkflowNodeType.SIGNAL_EVENT);
    graph.registerToDictionary("sig-1", signalNode);
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("sig-1"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-2"));

    // when
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, children);

    // then
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenChildIsFormRepliedEvent() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-3");
    WorkflowNode formNode = new WorkflowNode().id("form-1").elementType(WorkflowNodeType.FORM_REPLIED_EVENT);
    graph.registerToDictionary("form-1", formNode);
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("form-1"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-3"));

    // when
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, children);

    // then
    assertThat(result).isFalse();
  }

  // ---- hasAllConditionalChildren ----

  @Test
  void shouldReturnTrueWhenAllChildrenAreConditional() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-4");
    WorkflowNode node = new WorkflowNode().id("node-1").addIfCondition("parent-1", "condition1");
    graph.registerToDictionary("node-1", node);
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("node-1"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-4"));

    // when
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, children, "parent-1");

    // then
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNotAllChildrenAreConditional() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-5");
    WorkflowNode node1 = new WorkflowNode().id("node-1").addIfCondition("parent-1", "condition1");
    WorkflowNode node2 = new WorkflowNode().id("node-2");
    graph.registerToDictionary("node-1", node1);
    graph.registerToDictionary("node-2", node2);
    WorkflowDirectedGraph.NodeChildren children =
        new WorkflowDirectedGraph.NodeChildren(List.of("node-1", "node-2"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-5"));

    // when
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, children, "parent-1");

    // then
    assertThat(result).isFalse();
  }

  // ---- hasConditionalString ----

  @Test
  void shouldReturnTrueWhenAnyChildIsConditional() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-6");
    WorkflowNode node1 = new WorkflowNode().id("node-1");
    WorkflowNode node2 = new WorkflowNode().id("node-2").addIfCondition("parent-1", "condition1");
    graph.registerToDictionary("node-1", node1);
    graph.registerToDictionary("node-2", node2);
    WorkflowDirectedGraph.NodeChildren children =
        new WorkflowDirectedGraph.NodeChildren(List.of("node-1", "node-2"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-6"));

    // when
    boolean result = BpmnBuilderHelper.hasConditionalString(context, children, "parent-1");

    // then
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNoChildIsConditional() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-7");
    WorkflowNode node = new WorkflowNode().id("node-1");
    graph.registerToDictionary("node-1", node);
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("node-1"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-7"));

    // when
    boolean result = BpmnBuilderHelper.hasConditionalString(context, children, "parent-1");

    // then
    assertThat(result).isFalse();
  }

  // ---- isConditionalLoop ----

  @Test
  void shouldReturnTrueWhenConditionalLoopConditionsMet() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-8");
    WorkflowNode node = new WorkflowNode().id("node-1").elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("node-1", node);
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("node-1"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-8"));
    context.addNodeBuilder("node-1", Bpmn.createExecutableProcess("aux-1").startEvent());

    AbstractFlowNodeBuilder<?, ?> gatewayBuilder =
        Bpmn.createExecutableProcess("proc-9").startEvent().exclusiveGateway();

    // when
    boolean result = BpmnBuilderHelper.isConditionalLoop(gatewayBuilder, context, children);

    // then
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenBuilderIsNotGateway() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-9");
    WorkflowNode node = new WorkflowNode().id("node-1").elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("node-1", node);
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("node-1"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-10"));
    context.addNodeBuilder("node-1", Bpmn.createExecutableProcess("aux-2").startEvent());

    AbstractFlowNodeBuilder<?, ?> serviceTaskBuilder =
        Bpmn.createExecutableProcess("proc-11").startEvent().serviceTask();

    // when
    boolean result = BpmnBuilderHelper.isConditionalLoop(serviceTaskBuilder, context, children);

    // then
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenChildNotAlreadyBuilt() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-10");
    WorkflowNode node = new WorkflowNode().id("node-1").elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("node-1", node);
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("node-1"));
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-12"));
    // node-1 is NOT added to the builder map

    AbstractFlowNodeBuilder<?, ?> gatewayBuilder =
        Bpmn.createExecutableProcess("proc-13").startEvent().exclusiveGateway();

    // when
    boolean result = BpmnBuilderHelper.isConditionalLoop(gatewayBuilder, context, children);

    // then
    assertThat(result).isFalse();
  }

  // ---- hasLoopAfterSubProcess ----

  @Test
  void shouldReturnTrueWhenLoopAfterSubProcessConditionsMet() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-11");
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-14"));
    EventSubProcessBuilder espBuilder =
        Bpmn.createExecutableProcess("proc-esp-1").startEvent().subProcess().embeddedSubProcess()
            .eventSubProcess("esp-1");
    context.cacheEventSubProcessToDone(espBuilder);
    context.addNodeBuilder("child-1", Bpmn.createExecutableProcess("aux-3").startEvent());

    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("child-1"));

    // when
    boolean result =
        BpmnBuilderHelper.hasLoopAfterSubProcess(context, children, WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // then
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNodeTypeIsNotActivityCompletedEvent() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-12");
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-15"));
    EventSubProcessBuilder espBuilder =
        Bpmn.createExecutableProcess("proc-esp-2").startEvent().subProcess().embeddedSubProcess()
            .eventSubProcess("esp-2");
    context.cacheEventSubProcessToDone(espBuilder);
    context.addNodeBuilder("child-1", Bpmn.createExecutableProcess("aux-4").startEvent());

    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("child-1"));

    // when
    boolean result =
        BpmnBuilderHelper.hasLoopAfterSubProcess(context, children, WorkflowNodeType.ACTIVITY);

    // then
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenNoEventSubProcess() {
    // given
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf-13");
    BuildProcessContext context = new BuildProcessContext(graph, Bpmn.createExecutableProcess("proc-16"));
    context.addNodeBuilder("child-1", Bpmn.createExecutableProcess("aux-5").startEvent());

    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(List.of("child-1"));

    // when
    boolean result =
        BpmnBuilderHelper.hasLoopAfterSubProcess(context, children, WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // then
    assertThat(result).isFalse();
  }
}
