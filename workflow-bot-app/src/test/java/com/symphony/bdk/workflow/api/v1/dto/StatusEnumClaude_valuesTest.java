package com.symphony.bdk.workflow.api.v1.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusEnumClaude_valuesTest {

  @Test
  void values_shouldReturnAllEnumConstants() {
    // When: Calling values() method
    StatusEnum[] values = StatusEnum.values();

    // Then: All three enum constants should be present
    assertThat(values).hasSize(3);
    assertThat(values).containsExactly(StatusEnum.PENDING, StatusEnum.COMPLETED, StatusEnum.FAILED);
  }

  @Test
  void values_shouldReturnArrayContainingPending() {
    // When: Calling values() method
    StatusEnum[] values = StatusEnum.values();

    // Then: The array should contain PENDING
    assertThat(values).contains(StatusEnum.PENDING);
  }

  @Test
  void values_shouldReturnArrayContainingCompleted() {
    // When: Calling values() method
    StatusEnum[] values = StatusEnum.values();

    // Then: The array should contain COMPLETED
    assertThat(values).contains(StatusEnum.COMPLETED);
  }

  @Test
  void values_shouldReturnArrayContainingFailed() {
    // When: Calling values() method
    StatusEnum[] values = StatusEnum.values();

    // Then: The array should contain FAILED
    assertThat(values).contains(StatusEnum.FAILED);
  }

  @Test
  void values_shouldReturnNonNullArray() {
    // When: Calling values() method
    StatusEnum[] values = StatusEnum.values();

    // Then: The returned array should not be null
    assertThat(values).isNotNull();
  }

  @Test
  void values_shouldReturnNewArrayInstance() {
    // When: Calling values() method twice
    StatusEnum[] values1 = StatusEnum.values();
    StatusEnum[] values2 = StatusEnum.values();

    // Then: Different array instances should be returned (defensive copy)
    assertThat(values1).isNotSameAs(values2);
    assertThat(values1).containsExactly(values2);
  }

  @Test
  void values_shouldReturnArrayInDeclarationOrder() {
    // When: Calling values() method
    StatusEnum[] values = StatusEnum.values();

    // Then: The enum constants should be in the order they are declared
    assertThat(values[0]).isEqualTo(StatusEnum.PENDING);
    assertThat(values[1]).isEqualTo(StatusEnum.COMPLETED);
    assertThat(values[2]).isEqualTo(StatusEnum.FAILED);
  }

  @Test
  void values_modifyingReturnedArray_shouldNotAffectSubsequentCalls() {
    // Given: First call to values()
    StatusEnum[] values1 = StatusEnum.values();

    // When: Modifying the returned array
    values1[0] = StatusEnum.FAILED;

    // And: Calling values() again
    StatusEnum[] values2 = StatusEnum.values();

    // Then: The second call should return unmodified array
    assertThat(values2[0]).isEqualTo(StatusEnum.PENDING);
    assertThat(values2).containsExactly(StatusEnum.PENDING, StatusEnum.COMPLETED, StatusEnum.FAILED);
  }
}
