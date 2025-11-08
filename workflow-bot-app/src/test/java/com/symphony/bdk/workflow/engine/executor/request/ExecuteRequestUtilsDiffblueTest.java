package com.symphony.bdk.workflow.engine.executor.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ExecuteRequestUtilsDiffblueTest {
  /**
   * Method under test: {@link ExecuteRequestUtils#encodeQueryParameters(String)}
   */
  @Test
  void testEncodeQueryParameters() {
    // Arrange, Act and Assert
    assertEquals("https://example.org/example",
        ExecuteRequestUtils.encodeQueryParameters("https://example.org/example"));
  }
}
