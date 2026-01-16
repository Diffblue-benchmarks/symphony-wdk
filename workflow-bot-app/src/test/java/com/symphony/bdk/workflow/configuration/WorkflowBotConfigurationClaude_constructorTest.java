package com.symphony.bdk.workflow.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class WorkflowBotConfigurationClaude_constructorTest {

  // Tests for <init>() constructor

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: Creating a new instance of WorkflowBotConfiguration
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // Then: The instance should not be null
    assertThat(config).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance of WorkflowBotConfiguration
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // Then: The instance should be of type WorkflowBotConfiguration
    assertThat(config).isInstanceOf(WorkflowBotConfiguration.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating two instances of WorkflowBotConfiguration
    WorkflowBotConfiguration config1 = new WorkflowBotConfiguration();
    WorkflowBotConfiguration config2 = new WorkflowBotConfiguration();

    // Then: Each call should create a distinct instance
    assertThat(config1).isNotSameAs(config2);
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // When: Creating multiple instances of WorkflowBotConfiguration
    WorkflowBotConfiguration config1 = new WorkflowBotConfiguration();
    WorkflowBotConfiguration config2 = new WorkflowBotConfiguration();
    WorkflowBotConfiguration config3 = new WorkflowBotConfiguration();

    // Then: All instances should be non-null and distinct
    assertThat(config1).isNotNull();
    assertThat(config2).isNotNull();
    assertThat(config3).isNotNull();
    assertThat(config1).isNotSameAs(config2);
    assertThat(config2).isNotSameAs(config3);
    assertThat(config1).isNotSameAs(config3);
  }

  @Test
  void constructor_shouldCreateInstanceWithNoException() {
    // When/Then: Creating a new instance should not throw any exception
    assertThatCode(() -> new WorkflowBotConfiguration())
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldInitializeWithNullFields() {
    // When: Creating a new instance of WorkflowBotConfiguration
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // Then: Fields should be null or have default values since @Value injection hasn't occurred
    // We can verify this through the getters provided by @Getter annotation
    assertThat(config.getWorkflowsFolderPath()).isNull();
    assertThat(config.getMonitoringToken()).isNull();
    assertThat(config.getManagementToken()).isNull();
  }
}
