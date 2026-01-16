package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for WorkflowDomainConverter constructor.
 * Tests the instantiation of WorkflowDomainConverter.
 */
class WorkflowDomainConverterClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    WorkflowDomainConverter converter = new WorkflowDomainConverter();

    // Then: instance should not be null
    assertThat(converter).isNotNull();
  }

  @Test
  void constructor_multipleInstances_shouldCreateIndependentInstances() {
    // When: creating multiple instances
    WorkflowDomainConverter converter1 = new WorkflowDomainConverter();
    WorkflowDomainConverter converter2 = new WorkflowDomainConverter();

    // Then: instances should be independent
    assertThat(converter1).isNotNull();
    assertThat(converter2).isNotNull();
    assertThat(converter1).isNotSameAs(converter2);
  }
}
