package com.symphony.bdk.workflow.engine.executor;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class DateTimeUtilsDiffblueTest {
  /**
   * Method under test: {@link DateTimeUtils#toEpochMilli(String)}
   */
  @Test
  void testToEpochMilli() {
    // Arrange, Act and Assert
    assertNull(DateTimeUtils.toEpochMilli(null));
  }
}
