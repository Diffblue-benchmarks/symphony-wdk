package com.symphony.bdk.workflow.engine.executor;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateTimeUtilsTest {

  @Test
  void shouldReturnNullWhenInputIsNull() {
    Long result = DateTimeUtils.toEpochMilli(null);

    assertThat(result).isNull();
  }

  @Test
  void shouldConvertValidIso8601StringToEpochMilli() {
    String iso8601Ts = "2024-01-15T10:30:45Z";

    Long result = DateTimeUtils.toEpochMilli(iso8601Ts);

    assertThat(result).isEqualTo(1705314645000L);
  }

  @Test
  void shouldConvertIso8601StringWithMilliseconds() {
    String iso8601Ts = "2024-01-15T10:30:45.123Z";

    Long result = DateTimeUtils.toEpochMilli(iso8601Ts);

    assertThat(result).isEqualTo(1705314645123L);
  }

  @Test
  void shouldConvertIso8601StringWithTimezone() {
    String iso8601Ts = "2024-01-15T10:30:45+01:00";

    Long result = DateTimeUtils.toEpochMilli(iso8601Ts);

    assertThat(result).isEqualTo(1705311045000L);
  }

  @Test
  void shouldThrowExceptionWhenInvalidFormat() {
    String invalidTs = "invalid-date-string";

    assertThatThrownBy(() -> DateTimeUtils.toEpochMilli(invalidTs))
        .isInstanceOf(DateTimeParseException.class);
  }
}
