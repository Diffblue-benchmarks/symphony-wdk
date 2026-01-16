package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class UpdateMessageExecutorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    UpdateMessageExecutor executor = new UpdateMessageExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    UpdateMessageExecutor executor = new UpdateMessageExecutor();

    // Then: Instance should be of UpdateMessageExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(UpdateMessageExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    UpdateMessageExecutor executor1 = new UpdateMessageExecutor();
    UpdateMessageExecutor executor2 = new UpdateMessageExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new UpdateMessageExecutor())
        .doesNotThrowAnyException();
  }
}
