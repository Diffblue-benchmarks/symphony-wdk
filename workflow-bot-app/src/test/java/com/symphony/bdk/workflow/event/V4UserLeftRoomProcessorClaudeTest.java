package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Test class for V4UserLeftRoomProcessor constructor.
 */
@ExtendWith(MockitoExtension.class)
class V4UserLeftRoomProcessorClaudeTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: creating a new V4UserLeftRoomProcessor instance
    V4UserLeftRoomProcessor instance = new V4UserLeftRoomProcessor(runtimeService);

    // Then: the instance should not be null
    assertThat(instance).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: creating a new V4UserLeftRoomProcessor instance
    V4UserLeftRoomProcessor instance = new V4UserLeftRoomProcessor(runtimeService);

    // Then: the instance should be of type V4UserLeftRoomProcessor
    assertThat(instance).isInstanceOf(V4UserLeftRoomProcessor.class);
  }

  @Test
  void constructor_shouldCreateInstanceOfAbstractRealTimeEventProcessor() {
    // When: creating a new V4UserLeftRoomProcessor instance
    V4UserLeftRoomProcessor instance = new V4UserLeftRoomProcessor(runtimeService);

    // Then: the instance should be of type AbstractRealTimeEventProcessor
    assertThat(instance).isInstanceOf(AbstractRealTimeEventProcessor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: creating two instances of V4UserLeftRoomProcessor
    V4UserLeftRoomProcessor instance1 = new V4UserLeftRoomProcessor(runtimeService);
    V4UserLeftRoomProcessor instance2 = new V4UserLeftRoomProcessor(runtimeService);

    // Then: each call should create a distinct instance
    assertThat(instance1).isNotSameAs(instance2);
  }

  @Test
  void constructor_shouldCreateInstanceWithNoException() {
    // When/Then: creating a new instance should not throw any exception
    assertThatCode(() -> new V4UserLeftRoomProcessor(runtimeService))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // When: creating multiple instances of V4UserLeftRoomProcessor
    V4UserLeftRoomProcessor instance1 = new V4UserLeftRoomProcessor(runtimeService);
    V4UserLeftRoomProcessor instance2 = new V4UserLeftRoomProcessor(runtimeService);
    V4UserLeftRoomProcessor instance3 = new V4UserLeftRoomProcessor(runtimeService);

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

    // When: creating a new V4UserLeftRoomProcessor instance with null
    V4UserLeftRoomProcessor instance = new V4UserLeftRoomProcessor(nullService);

    // Then: the instance should be created successfully (constructor doesn't validate null)
    assertThat(instance).isNotNull();
  }
}
