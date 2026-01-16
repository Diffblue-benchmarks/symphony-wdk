package com.symphony.bdk.workflow.api.v1.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatusEnumClaude_valueOfTest {

  @Test
  void valueOf_withPending_shouldReturnPendingEnum() {
    // When: Calling valueOf with "PENDING"
    StatusEnum result = StatusEnum.valueOf("PENDING");

    // Then: Should return the PENDING enum constant
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void valueOf_withCompleted_shouldReturnCompletedEnum() {
    // When: Calling valueOf with "COMPLETED"
    StatusEnum result = StatusEnum.valueOf("COMPLETED");

    // Then: Should return the COMPLETED enum constant
    assertThat(result).isEqualTo(StatusEnum.COMPLETED);
  }

  @Test
  void valueOf_withFailed_shouldReturnFailedEnum() {
    // When: Calling valueOf with "FAILED"
    StatusEnum result = StatusEnum.valueOf("FAILED");

    // Then: Should return the FAILED enum constant
    assertThat(result).isEqualTo(StatusEnum.FAILED);
  }

  @Test
  void valueOf_withLowercasePending_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase "pending" should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf("pending"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("StatusEnum.pending");
  }

  @Test
  void valueOf_withLowercaseCompleted_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase "completed" should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf("completed"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("StatusEnum.completed");
  }

  @Test
  void valueOf_withLowercaseFailed_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase "failed" should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf("failed"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("StatusEnum.failed");
  }

  @Test
  void valueOf_withMixedCasePending_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with mixed case "Pending" should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf("Pending"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("StatusEnum.Pending");
  }

  @Test
  void valueOf_withNull_shouldThrowNullPointerException() {
    // When/Then: Calling valueOf with null should throw NullPointerException
    assertThatThrownBy(() -> StatusEnum.valueOf(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void valueOf_withEmptyString_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with empty string should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("StatusEnum.");
  }

  @Test
  void valueOf_withInvalidValue_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with invalid value should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf("INVALID"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("StatusEnum.INVALID");
  }

  @Test
  void valueOf_withWhitespace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with whitespace should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_withPendingAndLeadingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with leading space should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf(" PENDING"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_withPendingAndTrailingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with trailing space should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.valueOf("PENDING "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_returnsSameInstance_whenCalledMultipleTimes() {
    // When: Calling valueOf multiple times with the same value
    StatusEnum result1 = StatusEnum.valueOf("PENDING");
    StatusEnum result2 = StatusEnum.valueOf("PENDING");

    // Then: Should return the same instance (enums are singletons)
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void valueOf_shouldBeConsistentWithEnumName() {
    // Given: All enum values
    StatusEnum[] values = StatusEnum.values();

    // When/Then: valueOf should return the same enum when passed the enum's name
    for (StatusEnum value : values) {
      assertThat(StatusEnum.valueOf(value.name())).isSameAs(value);
    }
  }
}
