package com.symphony.bdk.workflow.api.v1.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NodeViewClaudeTest {

  // ==================== ChildView Constructor Tests ====================

  @Test
  void childView_constructor_withNonNullNodeId_shouldSetNodeId() {
    // Given: A non-null nodeId
    String nodeId = "testNode";

    // When: Creating a ChildView using the constructor
    NodeView.ChildView childView = new NodeView.ChildView(nodeId);

    // Then: The nodeId should be set and condition should be null
    assertThat(childView.getNodeId()).isEqualTo("testNode");
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void childView_constructor_withNullNodeId_shouldSetNodeIdToNull() {
    // Given: A null nodeId
    String nodeId = null;

    // When: Creating a ChildView using the constructor
    NodeView.ChildView childView = new NodeView.ChildView(nodeId);

    // Then: Both nodeId and condition should be null
    assertThat(childView.getNodeId()).isNull();
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void childView_constructor_withEmptyNodeId_shouldSetNodeIdToEmptyString() {
    // Given: An empty nodeId
    String nodeId = "";

    // When: Creating a ChildView using the constructor
    NodeView.ChildView childView = new NodeView.ChildView(nodeId);

    // Then: The nodeId should be empty and condition should be null
    assertThat(childView.getNodeId()).isEmpty();
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void childView_constructor_withSpecialCharacters_shouldSetNodeIdCorrectly() {
    // Given: A nodeId with special characters
    String nodeId = "node-123_test.value";

    // When: Creating a ChildView using the constructor
    NodeView.ChildView childView = new NodeView.ChildView(nodeId);

    // Then: The nodeId should be set correctly
    assertThat(childView.getNodeId()).isEqualTo("node-123_test.value");
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void childView_constructor_withLongNodeId_shouldSetNodeIdCorrectly() {
    // Given: A very long nodeId
    String nodeId = "this-is-a-very-long-node-id-with-many-characters-to-test-edge-cases";

    // When: Creating a ChildView using the constructor
    NodeView.ChildView childView = new NodeView.ChildView(nodeId);

    // Then: The nodeId should be set correctly
    assertThat(childView.getNodeId()).isEqualTo(nodeId);
    assertThat(childView.getCondition()).isNull();
  }

  // ==================== ChildView.of(String) Static Factory Method Tests ====================

  @Test
  void childView_ofStaticMethod_withNonNullNodeId_shouldCreateInstanceWithNodeId() {
    // Given: A non-null nodeId
    String nodeId = "testNode";

    // When: Creating a ChildView using the of static method
    NodeView.ChildView childView = NodeView.ChildView.of(nodeId);

    // Then: The nodeId should be set and condition should be null
    assertThat(childView.getNodeId()).isEqualTo("testNode");
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void childView_ofStaticMethod_withNullNodeId_shouldCreateInstanceWithNullNodeId() {
    // Given: A null nodeId
    String nodeId = null;

    // When: Creating a ChildView using the of static method
    NodeView.ChildView childView = NodeView.ChildView.of(nodeId);

    // Then: Both nodeId and condition should be null
    assertThat(childView.getNodeId()).isNull();
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void childView_ofStaticMethod_withEmptyNodeId_shouldCreateInstanceWithEmptyNodeId() {
    // Given: An empty nodeId
    String nodeId = "";

    // When: Creating a ChildView using the of static method
    NodeView.ChildView childView = NodeView.ChildView.of(nodeId);

    // Then: The nodeId should be empty and condition should be null
    assertThat(childView.getNodeId()).isEmpty();
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void childView_ofStaticMethod_withSpecialCharacters_shouldCreateInstanceCorrectly() {
    // Given: A nodeId with special characters
    String nodeId = "node-123_test.value";

    // When: Creating a ChildView using the of static method
    NodeView.ChildView childView = NodeView.ChildView.of(nodeId);

    // Then: The nodeId should be set correctly
    assertThat(childView.getNodeId()).isEqualTo("node-123_test.value");
    assertThat(childView.getCondition()).isNull();
  }

  @Test
  void childView_ofStaticMethod_returnsNewInstance_shouldNotBeSingleton() {
    // Given: A nodeId
    String nodeId = "testNode";

    // When: Creating two ChildView instances using the of static method
    NodeView.ChildView childView1 = NodeView.ChildView.of(nodeId);
    NodeView.ChildView childView2 = NodeView.ChildView.of(nodeId);

    // Then: The two instances should be different objects but have the same nodeId
    assertThat(childView1).isNotSameAs(childView2);
    assertThat(childView1.getNodeId()).isEqualTo(childView2.getNodeId());
  }

  // ==================== Comparison Tests Between Constructor and of() ====================

  @Test
  void childView_constructorAndOfMethod_shouldProduceSameResult() {
    // Given: A nodeId
    String nodeId = "testNode";

    // When: Creating ChildView using both constructor and of method
    NodeView.ChildView viaConstructor = new NodeView.ChildView(nodeId);
    NodeView.ChildView viaOf = NodeView.ChildView.of(nodeId);

    // Then: Both should have the same nodeId and null condition
    assertThat(viaConstructor.getNodeId()).isEqualTo(viaOf.getNodeId());
    assertThat(viaConstructor.getCondition()).isEqualTo(viaOf.getCondition());
  }

  // ==================== Integration Tests with Lombok-generated Methods ====================

  @Test
  void childView_createdWithConstructor_canBeModifiedWithSetters() {
    // Given: A ChildView created with constructor
    NodeView.ChildView childView = new NodeView.ChildView("testNode");

    // When: Modifying the condition using the Lombok-generated setter
    childView.setCondition("someCondition");

    // Then: The condition should be set correctly
    assertThat(childView.getNodeId()).isEqualTo("testNode");
    assertThat(childView.getCondition()).isEqualTo("someCondition");
  }

  @Test
  void childView_createdWithOfMethod_canBeModifiedWithSetters() {
    // Given: A ChildView created with of method
    NodeView.ChildView childView = NodeView.ChildView.of("testNode");

    // When: Modifying the condition using the Lombok-generated setter
    childView.setCondition("someCondition");

    // Then: The condition should be set correctly
    assertThat(childView.getNodeId()).isEqualTo("testNode");
    assertThat(childView.getCondition()).isEqualTo("someCondition");
  }

  @Test
  void childView_equality_withSameNodeIdAndCondition_shouldBeEqual() {
    // Given: Two ChildView instances with same values
    NodeView.ChildView childView1 = new NodeView.ChildView("testNode");
    childView1.setCondition("condition1");

    NodeView.ChildView childView2 = new NodeView.ChildView("testNode");
    childView2.setCondition("condition1");

    // When/Then: They should be equal (Lombok @Data generates equals)
    assertThat(childView1).isEqualTo(childView2);
    assertThat(childView1.hashCode()).isEqualTo(childView2.hashCode());
  }

  @Test
  void childView_equality_withDifferentNodeId_shouldNotBeEqual() {
    // Given: Two ChildView instances with different nodeIds
    NodeView.ChildView childView1 = new NodeView.ChildView("testNode1");
    NodeView.ChildView childView2 = new NodeView.ChildView("testNode2");

    // When/Then: They should not be equal
    assertThat(childView1).isNotEqualTo(childView2);
  }

  @Test
  void childView_equality_withDifferentCondition_shouldNotBeEqual() {
    // Given: Two ChildView instances with same nodeId but different conditions
    NodeView.ChildView childView1 = new NodeView.ChildView("testNode");
    childView1.setCondition("condition1");

    NodeView.ChildView childView2 = new NodeView.ChildView("testNode");
    childView2.setCondition("condition2");

    // When/Then: They should not be equal
    assertThat(childView1).isNotEqualTo(childView2);
  }

  @Test
  void childView_toString_shouldContainNodeIdAndCondition() {
    // Given: A ChildView with both fields set
    NodeView.ChildView childView = new NodeView.ChildView("testNode");
    childView.setCondition("testCondition");

    // When: Calling toString (Lombok @Data generates toString)
    String result = childView.toString();

    // Then: The string should contain both field values
    assertThat(result).contains("testNode");
    assertThat(result).contains("testCondition");
  }
}
