package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class PinMessageExecutorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    PinMessageExecutor executor = new PinMessageExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    PinMessageExecutor executor = new PinMessageExecutor();

    // Then: Instance should be of PinMessageExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(PinMessageExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    PinMessageExecutor executor1 = new PinMessageExecutor();
    PinMessageExecutor executor2 = new PinMessageExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new PinMessageExecutor())
        .doesNotThrowAnyException();
  }
}
