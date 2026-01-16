package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.obo.OboExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class SendMessageExecutorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    SendMessageExecutor executor = new SendMessageExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    SendMessageExecutor executor = new SendMessageExecutor();

    // Then: Instance should be of SendMessageExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(SendMessageExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldExtendOboExecutor() {
    // When: Creating a new instance
    SendMessageExecutor executor = new SendMessageExecutor();

    // Then: Instance should extend OboExecutor
    assertThat(executor).isInstanceOf(OboExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    SendMessageExecutor executor1 = new SendMessageExecutor();
    SendMessageExecutor executor2 = new SendMessageExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new SendMessageExecutor())
        .doesNotThrowAnyException();
  }
}
