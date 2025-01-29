package com.symphony.bdk.workflow.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NotFoundExceptionDiffblueTest {
  /**
   * Test {@link NotFoundException#NotFoundException(String)}.
   * <p>
   * Method under test: {@link NotFoundException#NotFoundException(String)}
   */
  @Test
  @DisplayName("Test new NotFoundException(String)")
  void testNewNotFoundException() {
    // Arrange and Act
    NotFoundException actualNotFoundException = new NotFoundException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualNotFoundException.getMessage());
    assertNull(actualNotFoundException.getCause());
    assertEquals(0, actualNotFoundException.getSuppressed().length);
  }
}
