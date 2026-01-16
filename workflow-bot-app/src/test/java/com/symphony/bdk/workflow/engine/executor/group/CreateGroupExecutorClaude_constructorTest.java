package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CreateGroupExecutorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    CreateGroupExecutor executor = new CreateGroupExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    CreateGroupExecutor executor = new CreateGroupExecutor();

    // Then: Instance should be of CreateGroupExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(CreateGroupExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    CreateGroupExecutor executor1 = new CreateGroupExecutor();
    CreateGroupExecutor executor2 = new CreateGroupExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new CreateGroupExecutor())
        .doesNotThrowAnyException();
  }
}
