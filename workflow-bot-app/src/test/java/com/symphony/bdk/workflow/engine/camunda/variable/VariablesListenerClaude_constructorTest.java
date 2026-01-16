package com.symphony.bdk.workflow.engine.camunda.variable;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class VariablesListenerClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstance() {
    // When: Constructor is called
    VariablesListener listener = new VariablesListener();

    // Then: Instance should be created successfully
    assertThat(listener).isNotNull();
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new VariablesListener())
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldImplementExecutionListener() {
    // When: Constructor is called
    VariablesListener listener = new VariablesListener();

    // Then: Instance should implement ExecutionListener
    assertThat(listener).isInstanceOf(org.camunda.bpm.engine.delegate.ExecutionListener.class);
  }

  @Test
  void constructor_calledMultipleTimes_shouldCreateDistinctInstances() {
    // When: Constructor is called multiple times
    VariablesListener listener1 = new VariablesListener();
    VariablesListener listener2 = new VariablesListener();

    // Then: Each call should create a distinct instance
    assertThat(listener1).isNotNull();
    assertThat(listener2).isNotNull();
    assertThat(listener1).isNotSameAs(listener2);
  }
}
