package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityNodeBuilderClaude_constructorTest {

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // Then: Instance should be created successfully
    assertThat(builder).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // Then: Instance should be of ActivityNodeBuilder type
    assertThat(builder).isInstanceOf(ActivityNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingAbstractNodeBpmnBuilder() {
    // When: Creating a new instance
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // Then: Instance should extend AbstractNodeBpmnBuilder
    assertThat(builder).isInstanceOf(AbstractNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceImplementingWorkflowNodeBpmnBuilder() {
    // When: Creating a new instance
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // Then: Instance should implement WorkflowNodeBpmnBuilder
    assertThat(builder).isInstanceOf(WorkflowNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    ActivityNodeBuilder builder1 = new ActivityNodeBuilder();
    ActivityNodeBuilder builder2 = new ActivityNodeBuilder();

    // Then: Each instance should be distinct
    assertThat(builder1).isNotSameAs(builder2);
  }
}
