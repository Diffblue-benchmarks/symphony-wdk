package com.symphony.bdk.workflow.engine.executor;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeUtilsDiffblueTest {
  /**
   * Test {@link DateTimeUtils#toEpochMilli(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateTimeUtils#toEpochMilli(String)}
   */
  @Test
  @DisplayName("Test toEpochMilli(String); when 'null'; then return 'null'")
  void testToEpochMilli_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DateTimeUtils.toEpochMilli(null));
  }
}
