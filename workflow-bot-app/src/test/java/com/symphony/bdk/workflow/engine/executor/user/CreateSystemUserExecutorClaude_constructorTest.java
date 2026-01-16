package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CreateSystemUserExecutorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    CreateSystemUserExecutor executor = new CreateSystemUserExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    CreateSystemUserExecutor executor = new CreateSystemUserExecutor();

    // Then: Instance should be of CreateSystemUserExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(CreateSystemUserExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    CreateSystemUserExecutor executor1 = new CreateSystemUserExecutor();
    CreateSystemUserExecutor executor2 = new CreateSystemUserExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new CreateSystemUserExecutor())
        .doesNotThrowAnyException();
  }
}
