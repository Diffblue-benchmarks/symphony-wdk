package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.symphony.bdk.workflow.api.v1.dto.NodeView.ChildView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class NodeViewDiffblueTest {
  /**
   * Test ChildView {@link ChildView#ChildView(String)}.
   *
   * <p>Method under test: {@link ChildView#ChildView(String)}
   */
  @Test
  @DisplayName("Test ChildView new ChildView(String)")
  @Tag("MaintainedByDiffblue")
  void testChildViewNewChildView() {
    // Arrange and Act
    ChildView actualChildView = new ChildView("42");

    // Assert
    assertEquals("42", actualChildView.getNodeId());
    assertNull(actualChildView.getCondition());
  }

  /**
   * Test ChildView {@link ChildView#of(String)} with {@code nodeId}.
   *
   * <p>Method under test: {@link ChildView#of(String)}
   */
  @Test
  @DisplayName("Test ChildView of(String) with 'nodeId'")
  @Tag("MaintainedByDiffblue")
  void testChildViewOfWithNodeId() {
    // Arrange and Act
    ChildView actualOfResult = ChildView.of("42");

    // Assert
    assertEquals("42", actualOfResult.getNodeId());
    assertNull(actualOfResult.getCondition());
  }
}
