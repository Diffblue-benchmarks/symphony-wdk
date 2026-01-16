package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.ext.group.SymphonyGroupBdkExtension;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class WorkflowBotConfigurationClaude_groupExtensionTest {

  // Tests for groupExtension() method

  @Test
  void groupExtension_shouldReturnNonNullInstance() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When: groupExtension is called
    SymphonyGroupBdkExtension extension = config.groupExtension();

    // Then: The result should not be null
    assertThat(extension).isNotNull();
  }

  @Test
  void groupExtension_shouldReturnSymphonyGroupBdkExtensionInstance() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When: groupExtension is called
    SymphonyGroupBdkExtension extension = config.groupExtension();

    // Then: The result should be an instance of SymphonyGroupBdkExtension
    assertThat(extension).isInstanceOf(SymphonyGroupBdkExtension.class);
  }

  @Test
  void groupExtension_shouldCreateNewInstanceOnEachCall() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When: groupExtension is called multiple times
    SymphonyGroupBdkExtension extension1 = config.groupExtension();
    SymphonyGroupBdkExtension extension2 = config.groupExtension();

    // Then: Each call should return a distinct instance
    assertThat(extension1).isNotSameAs(extension2);
  }

  @Test
  void groupExtension_shouldNotThrowException() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When/Then: groupExtension should not throw any exception
    assertThatCode(() -> config.groupExtension())
        .doesNotThrowAnyException();
  }

  @Test
  void groupExtension_calledMultipleTimes_shouldSucceedEachTime() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When: groupExtension is called multiple times
    // Then: Each call should succeed without throwing an exception
    assertThatCode(() -> {
      config.groupExtension();
      config.groupExtension();
      config.groupExtension();
    }).doesNotThrowAnyException();
  }

  @Test
  void groupExtension_withMultipleInstances_shouldCreateIndependentExtensions() {
    // Given: Multiple WorkflowBotConfiguration instances
    WorkflowBotConfiguration config1 = new WorkflowBotConfiguration();
    WorkflowBotConfiguration config2 = new WorkflowBotConfiguration();

    // When: groupExtension is called on different configuration instances
    SymphonyGroupBdkExtension extension1 = config1.groupExtension();
    SymphonyGroupBdkExtension extension2 = config2.groupExtension();

    // Then: Each configuration should return distinct extension instances
    assertThat(extension1).isNotNull();
    assertThat(extension2).isNotNull();
    assertThat(extension1).isNotSameAs(extension2);
  }

  @Test
  void groupExtension_shouldAlwaysReturnNewInstance() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When: groupExtension is called three times
    SymphonyGroupBdkExtension extension1 = config.groupExtension();
    SymphonyGroupBdkExtension extension2 = config.groupExtension();
    SymphonyGroupBdkExtension extension3 = config.groupExtension();

    // Then: All three instances should be distinct from each other
    assertThat(extension1).isNotNull();
    assertThat(extension2).isNotNull();
    assertThat(extension3).isNotNull();
    assertThat(extension1).isNotSameAs(extension2);
    assertThat(extension2).isNotSameAs(extension3);
    assertThat(extension1).isNotSameAs(extension3);
  }

  @Test
  void groupExtension_shouldReturnInstanceOfCorrectType() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When: groupExtension is called
    Object extension = config.groupExtension();

    // Then: The result should be assignable to SymphonyGroupBdkExtension
    assertThat(extension).isInstanceOf(SymphonyGroupBdkExtension.class);
  }

  @Test
  void groupExtension_afterMultipleCalls_shouldMaintainFunctionality() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When: groupExtension is called sequentially multiple times
    SymphonyGroupBdkExtension extension1 = config.groupExtension();
    SymphonyGroupBdkExtension extension2 = config.groupExtension();
    SymphonyGroupBdkExtension extension3 = config.groupExtension();
    SymphonyGroupBdkExtension extension4 = config.groupExtension();

    // Then: All calls should succeed and return distinct instances
    assertThat(extension1).isNotNull();
    assertThat(extension2).isNotNull();
    assertThat(extension3).isNotNull();
    assertThat(extension4).isNotNull();
    assertThat(extension1).isNotSameAs(extension2);
    assertThat(extension1).isNotSameAs(extension3);
    assertThat(extension1).isNotSameAs(extension4);
  }

  @Test
  void groupExtension_shouldCreateInstanceWithDefaultConstructor() {
    // Given: A WorkflowBotConfiguration instance
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();

    // When: groupExtension is called
    SymphonyGroupBdkExtension extension = config.groupExtension();

    // Then: The extension should be created successfully using default constructor
    assertThat(extension).isNotNull();
    assertThat(extension).isInstanceOf(SymphonyGroupBdkExtension.class);
  }
}
