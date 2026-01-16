package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class GetGroupExecutorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetGroupExecutor executor = new GetGroupExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetGroupExecutor executor = new GetGroupExecutor();

    // Then: Instance should be of GetGroupExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(GetGroupExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetGroupExecutor executor1 = new GetGroupExecutor();
    GetGroupExecutor executor2 = new GetGroupExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetGroupExecutor())
        .doesNotThrowAnyException();
  }
}
