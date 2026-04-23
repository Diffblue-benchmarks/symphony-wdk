package com.symphony.bdk.workflow.api.v1.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatusEnumTest {

  @Test
  void shouldReturnNullWhenStatusIsNull() {
    assertThat(StatusEnum.toInstanceStatusEnum(null)).isNull();
  }

  @Test
  void shouldReturnPendingWhenStatusIsActive() {
    assertThat(StatusEnum.toInstanceStatusEnum("ACTIVE")).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void shouldReturnPendingWhenStatusIsActiveIgnoringCase() {
    assertThat(StatusEnum.toInstanceStatusEnum("active")).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void shouldReturnPendingWhenStatusIsPending() {
    assertThat(StatusEnum.toInstanceStatusEnum("PENDING")).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void shouldReturnCompletedWhenStatusIsCompleted() {
    assertThat(StatusEnum.toInstanceStatusEnum("COMPLETED")).isEqualTo(StatusEnum.COMPLETED);
  }

  @Test
  void shouldReturnFailedWhenStatusIsFailed() {
    assertThat(StatusEnum.toInstanceStatusEnum("FAILED")).isEqualTo(StatusEnum.FAILED);
  }

  @Test
  void shouldThrowExceptionWhenStatusIsUnknown() {
    assertThatThrownBy(() -> StatusEnum.toInstanceStatusEnum("UNKNOWN"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("UNKNOWN");
  }
}
