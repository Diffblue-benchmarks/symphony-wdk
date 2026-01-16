package com.symphony.bdk.workflow.management.converter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowVersionConverterClaude_constructorTest {

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: Creating a new converter
    WorkflowVersionConverter converter = new WorkflowVersionConverter();

    // Then: The instance should be created successfully
    assertThat(converter).isNotNull();
  }

  @Test
  void constructor_shouldCreateIndependentInstances() {
    // When: Creating multiple converter instances
    WorkflowVersionConverter converter1 = new WorkflowVersionConverter();
    WorkflowVersionConverter converter2 = new WorkflowVersionConverter();

    // Then: Each instance should be independent
    assertThat(converter1).isNotNull();
    assertThat(converter2).isNotNull();
    assertThat(converter1).isNotSameAs(converter2);
  }
}
