package com.symphony.bdk.workflow.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class NotFoundExceptionDiffblueTest {
  /**
   * Method under test: {@link NotFoundException#NotFoundException(String)}
   */
  @Test
  void testNewNotFoundException() {
    // Arrange and Act
    NotFoundException actualNotFoundException = new NotFoundException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualNotFoundException.getMessage());
    assertNull(actualNotFoundException.getCause());
    assertEquals(0, actualNotFoundException.getSuppressed().length);
  }
}
