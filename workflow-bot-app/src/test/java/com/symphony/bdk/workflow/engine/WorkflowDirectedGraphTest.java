package com.symphony.bdk.workflow.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class WorkflowDirectedGraphTest {

  @Test
  void shouldCreateNodeChildrenWithListConstructor() {
    List<String> childrenList = Arrays.asList("child1", "child2", "child3");

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren(childrenList);

    assertThat(nodeChildren.getChildren()).isEqualTo(childrenList);
    assertThat(nodeChildren.getGateway()).isNull();
  }

  @Test
  void shouldAddChildToNodeChildren() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    WorkflowDirectedGraph.NodeChildren result = nodeChildren.addChild("newChild");

    assertThat(result).isSameAs(nodeChildren);
    assertThat(nodeChildren.getChildren()).contains("newChild");
  }

  @Test
  void shouldRemoveChildFromNodeChildren() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");
    nodeChildren.addChild("child2");

    WorkflowDirectedGraph.NodeChildren result = nodeChildren.removeChild("child1");

    assertThat(result).isSameAs(nodeChildren);
    assertThat(nodeChildren.getChildren()).doesNotContain("child1");
    assertThat(nodeChildren.getChildren()).contains("child2");
  }

  @Test
  void shouldSetGatewayOnNodeChildren() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    WorkflowDirectedGraph.NodeChildren result = nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);

    assertThat(result).isSameAs(nodeChildren);
    assertThat(nodeChildren.getGateway()).isEqualTo(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
  }

  @Test
  void shouldReturnTrueWhenNodeChildrenIsEmpty() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    boolean isEmpty = nodeChildren.isEmpty();

    assertThat(isEmpty).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNodeChildrenIsNotEmpty() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");

    boolean isEmpty = nodeChildren.isEmpty();

    assertThat(isEmpty).isFalse();
  }

  @Test
  void shouldReturnTrueWhenChildIsUnique() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("uniqueChild");

    boolean isUnique = nodeChildren.isChildUnique();

    assertThat(isUnique).isTrue();
  }

  @Test
  void shouldReturnFalseWhenMultipleChildren() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");
    nodeChildren.addChild("child2");

    boolean isUnique = nodeChildren.isChildUnique();

    assertThat(isUnique).isFalse();
  }

  @Test
  void shouldReturnFalseWhenNoChildren() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    boolean isUnique = nodeChildren.isChildUnique();

    assertThat(isUnique).isFalse();
  }

  @Test
  void shouldGetUniqueChildWhenOnlyOneChild() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("uniqueChild");

    String uniqueChild = nodeChildren.getUniqueChild();

    assertThat(uniqueChild).isEqualTo("uniqueChild");
  }

  @Test
  void shouldThrowExceptionWhenGettingUniqueChildWithMultipleChildren() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");
    nodeChildren.addChild("child2");

    assertThatThrownBy(() -> nodeChildren.getUniqueChild())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No children or not unique child");
  }

  @Test
  void shouldThrowExceptionWhenGettingUniqueChildWithNoChildren() {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    assertThatThrownBy(() -> nodeChildren.getUniqueChild())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No children or not unique child");
  }
}
