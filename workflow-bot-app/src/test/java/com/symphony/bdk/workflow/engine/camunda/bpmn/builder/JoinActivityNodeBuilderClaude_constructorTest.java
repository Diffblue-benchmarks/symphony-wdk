package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JoinActivityNodeBuilderClaude_constructorTest {

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    JoinActivityNodeBuilder builder = new JoinActivityNodeBuilder();

    // Then: Instance should be created successfully
    assertThat(builder).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    JoinActivityNodeBuilder builder = new JoinActivityNodeBuilder();

    // Then: Instance should be of JoinActivityNodeBuilder type
    assertThat(builder).isInstanceOf(JoinActivityNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingAbstractNodeBpmnBuilder() {
    // When: Creating a new instance
    JoinActivityNodeBuilder builder = new JoinActivityNodeBuilder();

    // Then: Instance should extend AbstractNodeBpmnBuilder
    assertThat(builder).isInstanceOf(AbstractNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceImplementingWorkflowNodeBpmnBuilder() {
    // When: Creating a new instance
    JoinActivityNodeBuilder builder = new JoinActivityNodeBuilder();

    // Then: Instance should implement WorkflowNodeBpmnBuilder
    assertThat(builder).isInstanceOf(WorkflowNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    JoinActivityNodeBuilder builder1 = new JoinActivityNodeBuilder();
    JoinActivityNodeBuilder builder2 = new JoinActivityNodeBuilder();

    // Then: Each instance should be distinct
    assertThat(builder1).isNotSameAs(builder2);
  }
}
