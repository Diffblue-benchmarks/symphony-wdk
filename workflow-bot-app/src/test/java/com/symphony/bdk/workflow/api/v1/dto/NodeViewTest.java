package com.symphony.bdk.workflow.api.v1.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NodeViewTest {

  @Test
  void shouldCreateChildViewWithNodeIdOnly() {
    NodeView.ChildView childView = new NodeView.ChildView("nodeId1");

    assertThat(childView.getNodeId()).isEqualTo("nodeId1");
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void shouldCreateChildViewOfWithNodeIdOnly() {
    NodeView.ChildView childView = NodeView.ChildView.of("nodeId2");

    assertThat(childView.getNodeId()).isEqualTo("nodeId2");
    assertThat(childView.getCondition()).isNull();
  }
}
