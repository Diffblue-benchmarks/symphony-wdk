package com.symphony.bdk.workflow.scheduled;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RunnableScheduledJobDiffblueTest {
  /**
   * Test {@link Id#id()}.
   *
   * <ul>
   *   <li>Given lambda returning {@code "test-id"}.
   *   <li>Then returns {@code "test-id"}.
   * </ul>
   *
   * <p>Method under test: {@link Id#id()}
   */
  @Test
  @DisplayName("Test Id id(); given lambda returning 'test-id'; then returns 'test-id'")
  void testId_givenLambda_thenReturnsId() {
    // Arrange
    Id id = () -> "test-id";

    // Act
    String result = id.id();

    // Assert
    assertEquals("test-id", result);
  }
}
