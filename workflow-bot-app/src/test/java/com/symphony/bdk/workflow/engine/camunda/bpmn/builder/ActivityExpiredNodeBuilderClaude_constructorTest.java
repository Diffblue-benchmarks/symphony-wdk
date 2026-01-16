package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityExpiredNodeBuilderClaude_constructorTest {

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // Then: Instance should be created successfully
    assertThat(builder).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // Then: Instance should be of ActivityExpiredNodeBuilder type
    assertThat(builder).isInstanceOf(ActivityExpiredNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingActivityNodeBuilder() {
    // When: Creating a new instance
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // Then: Instance should extend ActivityNodeBuilder
    assertThat(builder).isInstanceOf(ActivityNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingAbstractNodeBpmnBuilder() {
    // When: Creating a new instance
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // Then: Instance should extend AbstractNodeBpmnBuilder
    assertThat(builder).isInstanceOf(AbstractNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    ActivityExpiredNodeBuilder builder1 = new ActivityExpiredNodeBuilder();
    ActivityExpiredNodeBuilder builder2 = new ActivityExpiredNodeBuilder();

    // Then: Each instance should be distinct
    assertThat(builder1).isNotSameAs(builder2);
  }
}
