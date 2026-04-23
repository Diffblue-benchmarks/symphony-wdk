package com.symphony.bdk.workflow.engine.executor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilsTest {

  @Test
  void shouldReturnNullWhenInputIsNull() {
    Long result = DateTimeUtils.toEpochMilli(null);

    assertThat(result).isNull();
  }

  @Test
  void shouldReturnEpochMilliWhenValidIso8601TimestampGiven() {
    Long result = DateTimeUtils.toEpochMilli("2023-01-01T00:00:00Z");

    assertThat(result).isEqualTo(1672531200000L);
  }

  @Test
  void shouldReturnEpochMilliWhenIso8601TimestampWithOffsetGiven() {
    Long result = DateTimeUtils.toEpochMilli("2023-06-15T12:30:00+02:00");

    assertThat(result).isEqualTo(1686825000000L);
  }
}
