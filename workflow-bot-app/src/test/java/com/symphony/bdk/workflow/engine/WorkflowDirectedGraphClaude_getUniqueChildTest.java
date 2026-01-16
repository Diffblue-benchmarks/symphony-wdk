package com.symphony.bdk.workflow.engine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowDirectedGraphClaude_getUniqueChildTest {

  // Tests for NodeChildren.getUniqueChild()
  // Targeting lines 140, 141, 143 in WorkflowDirectedGraph.java

  @Test
  void getUniqueChild_shouldReturnChildWhenExactlyOneChildExists() {
    // Given: A NodeChildren instance with exactly one child
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("uniqueChild");

    // When: Getting the unique child (covers line 140: condition true, line 141: return)
    String result = nodeChildren.getUniqueChild();

    // Then: The unique child should be returned
    assertThat(result).isEqualTo("uniqueChild");
  }

  @Test
  void getUniqueChild_shouldThrowIllegalStateExceptionWhenNoChildren() {
    // Given: A NodeChildren instance with no children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();

    // When/Then: Getting unique child should throw IllegalStateException
    // (covers line 140: condition false, line 143: throw exception)
    assertThatThrownBy(() -> nodeChildren.getUniqueChild())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No children or not unique child");
  }

  @Test
  void getUniqueChild_shouldThrowIllegalStateExceptionWhenMultipleChildren() {
    // Given: A NodeChildren instance with multiple children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");
    nodeChildren.addChild("child2");

    // When/Then: Getting unique child should throw IllegalStateException
    // (covers line 140: condition false, line 143: throw exception)
    assertThatThrownBy(() -> nodeChildren.getUniqueChild())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No children or not unique child");
  }

  @Test
  void getUniqueChild_shouldReturnCorrectChildAfterRemovingToOne() {
    // Given: A NodeChildren instance that initially has multiple children
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.addChild("child1");
    nodeChildren.addChild("child2");
    nodeChildren.addChild("child3");

    // When: Removing children until only one remains
    nodeChildren.removeChild("child2");
    nodeChildren.removeChild("child3");
    String result = nodeChildren.getUniqueChild();

    // Then: The remaining child should be returned (covers line 140: true, line 141: return)
    assertThat(result).isEqualTo("child1");
  }

  @Test
  void getUniqueChild_shouldWorkWithConstructorInitializedSingleChild() {
    // Given: A NodeChildren instance initialized with a single-child list
    WorkflowDirectedGraph.NodeChildren nodeChildren =
        new WorkflowDirectedGraph.NodeChildren(java.util.List.of("singleChild"));

    // When: Getting the unique child (covers line 140: true, line 141: return)
    String result = nodeChildren.getUniqueChild();

    // Then: The child should be returned
    assertThat(result).isEqualTo("singleChild");
  }

  @Test
  void getUniqueChild_shouldThrowExceptionWithConstructorInitializedMultipleChildren() {
    // Given: A NodeChildren instance initialized with multiple children
    WorkflowDirectedGraph.NodeChildren nodeChildren =
        new WorkflowDirectedGraph.NodeChildren(java.util.List.of("child1", "child2", "child3"));

    // When/Then: Getting unique child should throw IllegalStateException
    // (covers line 140: false, line 143: throw)
    assertThatThrownBy(() -> nodeChildren.getUniqueChild())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No children or not unique child");
  }

  @Test
  void getUniqueChild_shouldThrowExceptionWithConstructorInitializedEmptyList() {
    // Given: A NodeChildren instance initialized with an empty list
    WorkflowDirectedGraph.NodeChildren nodeChildren =
        new WorkflowDirectedGraph.NodeChildren(java.util.List.of());

    // When/Then: Getting unique child should throw IllegalStateException
    // (covers line 140: false, line 143: throw)
    assertThatThrownBy(() -> nodeChildren.getUniqueChild())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No children or not unique child");
  }
}
