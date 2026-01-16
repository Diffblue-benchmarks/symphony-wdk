package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class GetGroupsExecutorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetGroupsExecutor executor = new GetGroupsExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetGroupsExecutor executor = new GetGroupsExecutor();

    // Then: Instance should be of GetGroupsExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(GetGroupsExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetGroupsExecutor executor1 = new GetGroupsExecutor();
    GetGroupsExecutor executor2 = new GetGroupsExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetGroupsExecutor())
        .doesNotThrowAnyException();
  }
}
