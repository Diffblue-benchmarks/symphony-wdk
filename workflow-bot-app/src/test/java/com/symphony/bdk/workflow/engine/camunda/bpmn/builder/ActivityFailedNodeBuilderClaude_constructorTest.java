package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityFailedNodeBuilderClaude_constructorTest {

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    ActivityFailedNodeBuilder builder = new ActivityFailedNodeBuilder();

    // Then: Instance should be created successfully
    assertThat(builder).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    ActivityFailedNodeBuilder builder = new ActivityFailedNodeBuilder();

    // Then: Instance should be of ActivityFailedNodeBuilder type
    assertThat(builder).isInstanceOf(ActivityFailedNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingActivityNodeBuilder() {
    // When: Creating a new instance
    ActivityFailedNodeBuilder builder = new ActivityFailedNodeBuilder();

    // Then: Instance should extend ActivityNodeBuilder
    assertThat(builder).isInstanceOf(ActivityNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingAbstractNodeBpmnBuilder() {
    // When: Creating a new instance
    ActivityFailedNodeBuilder builder = new ActivityFailedNodeBuilder();

    // Then: Instance should extend AbstractNodeBpmnBuilder
    assertThat(builder).isInstanceOf(AbstractNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    ActivityFailedNodeBuilder builder1 = new ActivityFailedNodeBuilder();
    ActivityFailedNodeBuilder builder2 = new ActivityFailedNodeBuilder();

    // Then: Each instance should be distinct
    assertThat(builder1).isNotSameAs(builder2);
  }
}
