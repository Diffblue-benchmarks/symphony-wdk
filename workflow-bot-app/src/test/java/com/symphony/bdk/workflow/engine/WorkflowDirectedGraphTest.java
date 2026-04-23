package com.symphony.bdk.workflow.engine;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowDirectedGraphTest {

  @Test
  void shouldInitializeWithWorkflowIdAndVersion() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 42L);

    assertThat(graph.getWorkflowId()).isEqualTo("workflow1");
    assertThat(graph.getVersion()).isEqualTo(42L);
  }

  @Test
  void shouldInitializeWithWorkflowIdOnly() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow2");

    assertThat(graph.getWorkflowId()).isEqualTo("workflow2");
    assertThat(graph.getVersion()).isNull();
  }

  @Test
  void shouldAddParentAndRetrieveViaGetParents() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");

    graph.addParent("child", "parent1");
    graph.addParent("child", "parent2");

    List<String> parents = graph.getParents("child");
    assertThat(parents).containsExactlyInAnyOrder("parent1", "parent2");
  }

  @Test
  void shouldAddStartEvent() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");

    graph.addStartEvent("event1");
    graph.addStartEvent("event2");

    assertThat(graph.getStartEvents()).containsExactly("event1", "event2");
  }

  @Test
  void shouldRegisterToDictionaryAndCheckIsRegistered() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");
    WorkflowNode node = new WorkflowNode().id("node1");

    graph.registerToDictionary("node1", node);

    assertThat(graph.isRegistered("node1")).isTrue();
    assertThat(graph.isRegistered("unknown")).isFalse();
  }

  @Test
  void shouldReturnTrueForIsRegisteredWhenNodeExists() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");
    WorkflowNode node = new WorkflowNode().id("n");

    graph.registerToDictionary("n", node);

    assertThat(graph.isRegistered("n")).isTrue();
  }

  @Test
  void shouldReturnFalseForIsRegisteredWhenNodeAbsent() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");

    assertThat(graph.isRegistered("missing")).isFalse();
  }

  @Test
  void shouldGetChildrenCreatingIfAbsent() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");

    WorkflowDirectedGraph.NodeChildren children = graph.getChildren("node1");

    assertThat(children).isNotNull();
    assertThat(children.getChildren()).isEmpty();
  }

  @Test
  void shouldReadWorkflowNode() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");
    WorkflowNode node = new WorkflowNode().id("node1");
    graph.registerToDictionary("node1", node);

    WorkflowNode result = graph.readWorkflowNode("node1");

    assertThat(result).isSameAs(node);
  }

  @Test
  void shouldReturnNullForReadWorkflowNodeWhenNotFound() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");

    assertThat(graph.readWorkflowNode("missing")).isNull();
  }

  @Test
  void shouldReadChildren() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");
    graph.getChildren("node1").addChild("child1");

    WorkflowDirectedGraph.NodeChildren children = graph.readChildren("node1");

    assertThat(children).isNotNull();
    assertThat(children.getChildren()).containsExactly("child1");
  }

  @Test
  void shouldReturnNullForReadChildrenWhenNotFound() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");

    assertThat(graph.readChildren("missing")).isNull();
  }

  @Test
  void shouldReturnTrueForHasSeenBeforeWhenParentExists() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");
    graph.addParent("node1", "parent1");

    assertThat(graph.hasSeenBefore("node1")).isTrue();
  }

  @Test
  void shouldReturnFalseForHasSeenBeforeWhenNoParent() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");

    assertThat(graph.hasSeenBefore("node1")).isFalse();
  }

  @Test
  void shouldReturnEmptyListForGetParentsWhenNoParentAdded() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");

    List<String> parents = graph.getParents("node1");

    assertThat(parents).isEmpty();
  }

  @Test
  void shouldReturnParentListForGetParents() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf");
    graph.addParent("node1", "p1");

    List<String> parents = graph.getParents("node1");

    assertThat(parents).containsExactly("p1");
  }
}
