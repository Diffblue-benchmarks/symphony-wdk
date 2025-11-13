package com.symphony.bdk.workflow.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UnauthorizedExceptionDiffblueTest {
  /**
   * Test {@link UnauthorizedException#UnauthorizedException(String)}.
   *
   * <p>Method under test: {@link UnauthorizedException#UnauthorizedException(String)}
   */
  @Test
  @DisplayName("Test new UnauthorizedException(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UnauthorizedException.<init>(String)"})
  void testNewUnauthorizedException() {
    // Arrange and Act
    UnauthorizedException actualUnauthorizedException =
        new UnauthorizedException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualUnauthorizedException.getMessage());
    assertNull(actualUnauthorizedException.getCause());
    assertEquals(0, actualUnauthorizedException.getSuppressed().length);
  }
}
