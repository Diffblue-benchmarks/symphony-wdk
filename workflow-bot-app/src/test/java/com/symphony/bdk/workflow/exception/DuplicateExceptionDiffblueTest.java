package com.symphony.bdk.workflow.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DuplicateExceptionDiffblueTest {
  /**
   * Test {@link DuplicateException#DuplicateException(String)}.
   *
   * <p>Method under test: {@link DuplicateException#DuplicateException(String)}
   */
  @Test
  @DisplayName("Test new DuplicateException(String)")
  @Tag("MaintainedByDiffblue")
  void testNewDuplicateException() {
    // Arrange and Act
    DuplicateException actualDuplicateException = new DuplicateException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualDuplicateException.getMessage());
    assertNull(actualDuplicateException.getCause());
    assertEquals(0, actualDuplicateException.getSuppressed().length);
  }
}
