package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Test class for V4ElementActionEventProcessor constructor.
 */
@ExtendWith(MockitoExtension.class)
class V4ElementActionEventProcessorClaude_constructorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: creating a new V4ElementActionEventProcessor instance
    V4ElementActionEventProcessor instance = new V4ElementActionEventProcessor(runtimeService);

    // Then: the instance should not be null
    assertThat(instance).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: creating a new V4ElementActionEventProcessor instance
    V4ElementActionEventProcessor instance = new V4ElementActionEventProcessor(runtimeService);

    // Then: the instance should be of type V4ElementActionEventProcessor
    assertThat(instance).isInstanceOf(V4ElementActionEventProcessor.class);
  }

  @Test
  void constructor_shouldCreateInstanceOfAbstractRealTimeEventProcessor() {
    // When: creating a new V4ElementActionEventProcessor instance
    V4ElementActionEventProcessor instance = new V4ElementActionEventProcessor(runtimeService);

    // Then: the instance should be of type AbstractRealTimeEventProcessor
    assertThat(instance).isInstanceOf(AbstractRealTimeEventProcessor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: creating two instances of V4ElementActionEventProcessor
    V4ElementActionEventProcessor instance1 = new V4ElementActionEventProcessor(runtimeService);
    V4ElementActionEventProcessor instance2 = new V4ElementActionEventProcessor(runtimeService);

    // Then: each call should create a distinct instance
    assertThat(instance1).isNotSameAs(instance2);
  }

  @Test
  void constructor_shouldCreateInstanceWithNoException() {
    // When/Then: creating a new instance should not throw any exception
    assertThatCode(() -> new V4ElementActionEventProcessor(runtimeService))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // When: creating multiple instances of V4ElementActionEventProcessor
    V4ElementActionEventProcessor instance1 = new V4ElementActionEventProcessor(runtimeService);
    V4ElementActionEventProcessor instance2 = new V4ElementActionEventProcessor(runtimeService);
    V4ElementActionEventProcessor instance3 = new V4ElementActionEventProcessor(runtimeService);

    // Then: all instances should be non-null and distinct
    assertThat(instance1).isNotNull();
    assertThat(instance2).isNotNull();
    assertThat(instance3).isNotNull();
    assertThat(instance1).isNotSameAs(instance2);
    assertThat(instance2).isNotSameAs(instance3);
    assertThat(instance1).isNotSameAs(instance3);
  }

  @Test
  void constructor_withNullRuntimeService_shouldCreateInstance() {
    // Given: a null RuntimeService
    RuntimeService nullService = null;

    // When: creating a new V4ElementActionEventProcessor instance with null
    V4ElementActionEventProcessor instance = new V4ElementActionEventProcessor(nullService);

    // Then: the instance should be created successfully (constructor doesn't validate null)
    assertThat(instance).isNotNull();
  }
}
