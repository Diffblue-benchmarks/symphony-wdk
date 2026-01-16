package com.symphony.bdk.workflow.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AspectConfigurationClaudeTest {

  // Tests for <init>() constructor

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: Creating a new instance of AspectConfiguration
    AspectConfiguration config = new AspectConfiguration();

    // Then: The instance should not be null
    assertThat(config).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance of AspectConfiguration
    AspectConfiguration config = new AspectConfiguration();

    // Then: The instance should be of type AspectConfiguration
    assertThat(config).isInstanceOf(AspectConfiguration.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating two instances of AspectConfiguration
    AspectConfiguration config1 = new AspectConfiguration();
    AspectConfiguration config2 = new AspectConfiguration();

    // Then: Each call should create a distinct instance
    assertThat(config1).isNotSameAs(config2);
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // When: Creating multiple instances of AspectConfiguration
    AspectConfiguration config1 = new AspectConfiguration();
    AspectConfiguration config2 = new AspectConfiguration();
    AspectConfiguration config3 = new AspectConfiguration();

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
    org.assertj.core.api.Assertions.assertThatCode(() -> new AspectConfiguration())
        .doesNotThrowAnyException();
  }
}
