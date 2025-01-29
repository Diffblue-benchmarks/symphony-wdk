package com.symphony.bdk.workflow.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnauthorizedExceptionDiffblueTest {
  /**
   * Test {@link UnauthorizedException#UnauthorizedException(String)}.
   * <p>
   * Method under test:
   * {@link UnauthorizedException#UnauthorizedException(String)}
   */
  @Test
  @DisplayName("Test new UnauthorizedException(String)")
  void testNewUnauthorizedException() {
    // Arrange and Act
    UnauthorizedException actualUnauthorizedException = new UnauthorizedException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualUnauthorizedException.getMessage());
    assertNull(actualUnauthorizedException.getCause());
    assertEquals(0, actualUnauthorizedException.getSuppressed().length);
  }
}
