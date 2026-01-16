package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Test class for V4RoomCreatedProcessor constructor.
 */
@ExtendWith(MockitoExtension.class)
class V4RoomCreatedProcessorClaudeTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: creating a new V4RoomCreatedProcessor instance
    V4RoomCreatedProcessor instance = new V4RoomCreatedProcessor(runtimeService);

    // Then: the instance should not be null
    assertThat(instance).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: creating a new V4RoomCreatedProcessor instance
    V4RoomCreatedProcessor instance = new V4RoomCreatedProcessor(runtimeService);

    // Then: the instance should be of type V4RoomCreatedProcessor
    assertThat(instance).isInstanceOf(V4RoomCreatedProcessor.class);
  }

  @Test
  void constructor_shouldCreateInstanceOfAbstractRealTimeEventProcessor() {
    // When: creating a new V4RoomCreatedProcessor instance
    V4RoomCreatedProcessor instance = new V4RoomCreatedProcessor(runtimeService);

    // Then: the instance should be of type AbstractRealTimeEventProcessor
    assertThat(instance).isInstanceOf(AbstractRealTimeEventProcessor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: creating two instances of V4RoomCreatedProcessor
    V4RoomCreatedProcessor instance1 = new V4RoomCreatedProcessor(runtimeService);
    V4RoomCreatedProcessor instance2 = new V4RoomCreatedProcessor(runtimeService);

    // Then: each call should create a distinct instance
    assertThat(instance1).isNotSameAs(instance2);
  }

  @Test
  void constructor_shouldCreateInstanceWithNoException() {
    // When/Then: creating a new instance should not throw any exception
    assertThatCode(() -> new V4RoomCreatedProcessor(runtimeService))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // When: creating multiple instances of V4RoomCreatedProcessor
    V4RoomCreatedProcessor instance1 = new V4RoomCreatedProcessor(runtimeService);
    V4RoomCreatedProcessor instance2 = new V4RoomCreatedProcessor(runtimeService);
    V4RoomCreatedProcessor instance3 = new V4RoomCreatedProcessor(runtimeService);

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

    // When: creating a new V4RoomCreatedProcessor instance with null
    V4RoomCreatedProcessor instance = new V4RoomCreatedProcessor(nullService);

    // Then: the instance should be created successfully (constructor doesn't validate null)
    assertThat(instance).isNotNull();
  }
}
