package com.symphony.bdk.workflow.engine.executor.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExecuteRequestUtilsDiffblueTest {
  /**
   * Test {@link ExecuteRequestUtils#encodeQueryParameters(String)}.
   *
   * <ul>
   *   <li>Then return {@code https://example.org/example}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestUtils#encodeQueryParameters(String)}
   */
  @Test
  @DisplayName("Test encodeQueryParameters(String); then return 'https://example.org/example'")
  @Tag("MaintainedByDiffblue")
  void testEncodeQueryParameters_thenReturnHttpsExampleOrgExample() {
    // Arrange, Act and Assert
    assertEquals(
        "https://example.org/example",
        ExecuteRequestUtils.encodeQueryParameters("https://example.org/example"));
  }
}
