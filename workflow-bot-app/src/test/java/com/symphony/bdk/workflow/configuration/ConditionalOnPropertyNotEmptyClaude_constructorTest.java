package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.workflow.configuration.ConditionalOnPropertyNotEmpty.OnPropertyNotEmptyCondition;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Condition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ConditionalOnPropertyNotEmptyClaude_constructorTest {

  // Tests for OnPropertyNotEmptyCondition.<init>() constructor

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: Creating a new instance of OnPropertyNotEmptyCondition
    OnPropertyNotEmptyCondition condition = new OnPropertyNotEmptyCondition();

    // Then: The instance should not be null
    assertThat(condition).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance of OnPropertyNotEmptyCondition
    OnPropertyNotEmptyCondition condition = new OnPropertyNotEmptyCondition();

    // Then: The instance should be of type OnPropertyNotEmptyCondition
    assertThat(condition).isInstanceOf(OnPropertyNotEmptyCondition.class);
  }

  @Test
  void constructor_shouldCreateInstanceImplementingConditionInterface() {
    // When: Creating a new instance of OnPropertyNotEmptyCondition
    OnPropertyNotEmptyCondition condition = new OnPropertyNotEmptyCondition();

    // Then: The instance should implement the Condition interface
    assertThat(condition).isInstanceOf(Condition.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating two instances of OnPropertyNotEmptyCondition
    OnPropertyNotEmptyCondition condition1 = new OnPropertyNotEmptyCondition();
    OnPropertyNotEmptyCondition condition2 = new OnPropertyNotEmptyCondition();

    // Then: Each call should create a distinct instance
    assertThat(condition1).isNotSameAs(condition2);
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // When: Creating multiple instances of OnPropertyNotEmptyCondition
    OnPropertyNotEmptyCondition condition1 = new OnPropertyNotEmptyCondition();
    OnPropertyNotEmptyCondition condition2 = new OnPropertyNotEmptyCondition();
    OnPropertyNotEmptyCondition condition3 = new OnPropertyNotEmptyCondition();

    // Then: All instances should be non-null and distinct
    assertThat(condition1).isNotNull();
    assertThat(condition2).isNotNull();
    assertThat(condition3).isNotNull();
    assertThat(condition1).isNotSameAs(condition2);
    assertThat(condition2).isNotSameAs(condition3);
    assertThat(condition1).isNotSameAs(condition3);
  }

  @Test
  void constructor_shouldCreateInstanceWithNoException() {
    // When/Then: Creating a new instance should not throw any exception
    assertThatCode(() -> new OnPropertyNotEmptyCondition())
        .doesNotThrowAnyException();
  }
}
