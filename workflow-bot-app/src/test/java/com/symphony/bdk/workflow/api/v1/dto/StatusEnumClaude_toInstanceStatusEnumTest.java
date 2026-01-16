package com.symphony.bdk.workflow.api.v1.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatusEnumClaude_toInstanceStatusEnumTest {

  // ==================== Null Input Tests ====================

  @Test
  void toInstanceStatusEnum_withNull_shouldReturnNull() {
    // When: Calling toInstanceStatusEnum with null
    StatusEnum result = StatusEnum.toInstanceStatusEnum(null);

    // Then: Should return null
    assertThat(result).isNull();
  }

  // ==================== PENDING Mapping Tests ====================

  @Test
  void toInstanceStatusEnum_withPendingUpperCase_shouldReturnPending() {
    // When: Calling toInstanceStatusEnum with "PENDING"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("PENDING");

    // Then: Should return PENDING enum
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void toInstanceStatusEnum_withPendingLowerCase_shouldReturnPending() {
    // When: Calling toInstanceStatusEnum with "pending"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("pending");

    // Then: Should return PENDING enum
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void toInstanceStatusEnum_withPendingMixedCase_shouldReturnPending() {
    // When: Calling toInstanceStatusEnum with "Pending"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("Pending");

    // Then: Should return PENDING enum
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void toInstanceStatusEnum_withActiveUpperCase_shouldReturnPending() {
    // When: Calling toInstanceStatusEnum with "ACTIVE"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("ACTIVE");

    // Then: Should return PENDING enum (ACTIVE maps to PENDING)
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void toInstanceStatusEnum_withActiveLowerCase_shouldReturnPending() {
    // When: Calling toInstanceStatusEnum with "active"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("active");

    // Then: Should return PENDING enum (ACTIVE maps to PENDING)
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void toInstanceStatusEnum_withActiveMixedCase_shouldReturnPending() {
    // When: Calling toInstanceStatusEnum with "Active"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("Active");

    // Then: Should return PENDING enum (ACTIVE maps to PENDING)
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void toInstanceStatusEnum_withPeNdInG_shouldReturnPending() {
    // When: Calling toInstanceStatusEnum with "PeNdInG"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("PeNdInG");

    // Then: Should return PENDING enum (case-insensitive)
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void toInstanceStatusEnum_withAcTiVe_shouldReturnPending() {
    // When: Calling toInstanceStatusEnum with "AcTiVe"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("AcTiVe");

    // Then: Should return PENDING enum (case-insensitive)
    assertThat(result).isEqualTo(StatusEnum.PENDING);
  }

  // ==================== COMPLETED Mapping Tests ====================

  @Test
  void toInstanceStatusEnum_withCompletedUpperCase_shouldReturnCompleted() {
    // When: Calling toInstanceStatusEnum with "COMPLETED"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("COMPLETED");

    // Then: Should return COMPLETED enum
    assertThat(result).isEqualTo(StatusEnum.COMPLETED);
  }

  @Test
  void toInstanceStatusEnum_withCompletedLowerCase_shouldReturnCompleted() {
    // When: Calling toInstanceStatusEnum with "completed"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("completed");

    // Then: Should return COMPLETED enum
    assertThat(result).isEqualTo(StatusEnum.COMPLETED);
  }

  @Test
  void toInstanceStatusEnum_withCompletedMixedCase_shouldReturnCompleted() {
    // When: Calling toInstanceStatusEnum with "Completed"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("Completed");

    // Then: Should return COMPLETED enum
    assertThat(result).isEqualTo(StatusEnum.COMPLETED);
  }

  @Test
  void toInstanceStatusEnum_withCoMpLeTeD_shouldReturnCompleted() {
    // When: Calling toInstanceStatusEnum with "CoMpLeTeD"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("CoMpLeTeD");

    // Then: Should return COMPLETED enum (case-insensitive)
    assertThat(result).isEqualTo(StatusEnum.COMPLETED);
  }

  // ==================== FAILED Mapping Tests ====================

  @Test
  void toInstanceStatusEnum_withFailedUpperCase_shouldReturnFailed() {
    // When: Calling toInstanceStatusEnum with "FAILED"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("FAILED");

    // Then: Should return FAILED enum
    assertThat(result).isEqualTo(StatusEnum.FAILED);
  }

  @Test
  void toInstanceStatusEnum_withFailedLowerCase_shouldReturnFailed() {
    // When: Calling toInstanceStatusEnum with "failed"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("failed");

    // Then: Should return FAILED enum
    assertThat(result).isEqualTo(StatusEnum.FAILED);
  }

  @Test
  void toInstanceStatusEnum_withFailedMixedCase_shouldReturnFailed() {
    // When: Calling toInstanceStatusEnum with "Failed"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("Failed");

    // Then: Should return FAILED enum
    assertThat(result).isEqualTo(StatusEnum.FAILED);
  }

  @Test
  void toInstanceStatusEnum_withFaIlEd_shouldReturnFailed() {
    // When: Calling toInstanceStatusEnum with "FaIlEd"
    StatusEnum result = StatusEnum.toInstanceStatusEnum("FaIlEd");

    // Then: Should return FAILED enum (case-insensitive)
    assertThat(result).isEqualTo(StatusEnum.FAILED);
  }

  // ==================== Invalid Input Tests ====================

  @Test
  void toInstanceStatusEnum_withEmptyString_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with empty string should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status")
        .hasMessageContaining("is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  @Test
  void toInstanceStatusEnum_withInvalidStatus_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with invalid status should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum("INVALID"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status INVALID is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  @Test
  void toInstanceStatusEnum_withRunning_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with "RUNNING" should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum("RUNNING"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status RUNNING is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  @Test
  void toInstanceStatusEnum_withWhitespace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with whitespace should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status")
        .hasMessageContaining("is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  @Test
  void toInstanceStatusEnum_withPendingAndLeadingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with leading space should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum(" PENDING"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status  PENDING is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  @Test
  void toInstanceStatusEnum_withPendingAndTrailingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with trailing space should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum("PENDING "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status PENDING  is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  @Test
  void toInstanceStatusEnum_withPendingAndSurroundingSpaces_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with surrounding spaces should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum(" PENDING "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status")
        .hasMessageContaining("is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  @Test
  void toInstanceStatusEnum_withNumericString_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with numeric string should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum("123"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status 123 is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  @Test
  void toInstanceStatusEnum_withSpecialCharacters_shouldThrowIllegalArgumentException() {
    // When/Then: Calling toInstanceStatusEnum with special characters should throw IllegalArgumentException
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum("PENDING!"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status PENDING! is not known")
        .hasMessageContaining("Allowed values [Completed, Pending, Failed]");
  }

  // ==================== Edge Case Tests ====================

  @Test
  void toInstanceStatusEnum_returnsConsistentResults_whenCalledMultipleTimes() {
    // When: Calling toInstanceStatusEnum multiple times with the same value
    StatusEnum result1 = StatusEnum.toInstanceStatusEnum("PENDING");
    StatusEnum result2 = StatusEnum.toInstanceStatusEnum("PENDING");
    StatusEnum result3 = StatusEnum.toInstanceStatusEnum("pending");

    // Then: Should return the same enum instance
    assertThat(result1).isSameAs(StatusEnum.PENDING);
    assertThat(result2).isSameAs(StatusEnum.PENDING);
    assertThat(result3).isSameAs(StatusEnum.PENDING);
    assertThat(result1).isSameAs(result2).isSameAs(result3);
  }

  @Test
  void toInstanceStatusEnum_activeAndPending_shouldReturnSameEnum() {
    // When: Calling toInstanceStatusEnum with "ACTIVE" and "PENDING"
    StatusEnum activeResult = StatusEnum.toInstanceStatusEnum("ACTIVE");
    StatusEnum pendingResult = StatusEnum.toInstanceStatusEnum("PENDING");

    // Then: Both should return the same PENDING enum instance
    assertThat(activeResult).isSameAs(pendingResult);
    assertThat(activeResult).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void toInstanceStatusEnum_allValidStatuses_shouldMapCorrectly() {
    // When/Then: All valid status strings should map to correct enums
    assertThat(StatusEnum.toInstanceStatusEnum("PENDING")).isEqualTo(StatusEnum.PENDING);
    assertThat(StatusEnum.toInstanceStatusEnum("pending")).isEqualTo(StatusEnum.PENDING);
    assertThat(StatusEnum.toInstanceStatusEnum("ACTIVE")).isEqualTo(StatusEnum.PENDING);
    assertThat(StatusEnum.toInstanceStatusEnum("active")).isEqualTo(StatusEnum.PENDING);
    assertThat(StatusEnum.toInstanceStatusEnum("COMPLETED")).isEqualTo(StatusEnum.COMPLETED);
    assertThat(StatusEnum.toInstanceStatusEnum("completed")).isEqualTo(StatusEnum.COMPLETED);
    assertThat(StatusEnum.toInstanceStatusEnum("FAILED")).isEqualTo(StatusEnum.FAILED);
    assertThat(StatusEnum.toInstanceStatusEnum("failed")).isEqualTo(StatusEnum.FAILED);
  }
}
