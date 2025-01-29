package com.symphony.bdk.workflow.engine.executor.request.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResponseDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Response#Response(int, Object)}
   *   <li>{@link Response#getCode()}
   *   <li>{@link Response#getContent()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    Response actualResponse = new Response(1, "Content");
    int actualCode = actualResponse.getCode();

    // Assert
    assertEquals("Content", actualResponse.getContent());
    assertEquals(1, actualCode);
  }
}
