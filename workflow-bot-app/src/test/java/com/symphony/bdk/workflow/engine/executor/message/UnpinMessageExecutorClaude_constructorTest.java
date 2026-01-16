package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class UnpinMessageExecutorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    UnpinMessageExecutor executor = new UnpinMessageExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    UnpinMessageExecutor executor = new UnpinMessageExecutor();

    // Then: Instance should be of UnpinMessageExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(UnpinMessageExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    UnpinMessageExecutor executor1 = new UnpinMessageExecutor();
    UnpinMessageExecutor executor2 = new UnpinMessageExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new UnpinMessageExecutor())
        .doesNotThrowAnyException();
  }
}
