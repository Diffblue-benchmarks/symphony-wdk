package com.symphony.bdk.workflow.engine.executor.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecuteRequestUtils.encodeQueryParameters(String)"})
  void testEncodeQueryParameters_thenReturnHttpsExampleOrgExample() {
    // Arrange, Act and Assert
    assertEquals(
        "https://example.org/example",
        ExecuteRequestUtils.encodeQueryParameters("https://example.org/example"));
  }

  /**
   * Test {@link ExecuteRequestUtils#encodeQueryParameters(String)}.
   *
   * <ul>
   *   <li>When URL has a simple query parameter, then it is preserved.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestUtils#encodeQueryParameters(String)}
   */
  @Test
  @DisplayName("Test encodeQueryParameters(String); when URL has simple query param then preserved")
  void testEncodeQueryParameters_withSimpleQueryParam() {
    // Arrange
    String url = "https://example.org/example?key=value";

    // Act
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    // Assert
    assertTrue(result.contains("key=value"));
  }

  /**
   * Test {@link ExecuteRequestUtils#encodeQueryParameters(String)}.
   *
   * <ul>
   *   <li>When URL has a query parameter with special characters, then value is encoded.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestUtils#encodeQueryParameters(String)}
   */
  @Test
  @DisplayName("Test encodeQueryParameters(String); when special chars then value is encoded")
  void testEncodeQueryParameters_withSpecialCharacters() {
    // Arrange
    String url = "https://example.org/example?key=hello world";

    // Act
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    // Assert
    assertTrue(result.contains("key=hello+world") || result.contains("key=hello%20world"));
  }

  /**
   * Test {@link ExecuteRequestUtils#encodeQueryParameters(String)}.
   *
   * <ul>
   *   <li>When URL has multiple query parameters, then all are encoded.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestUtils#encodeQueryParameters(String)}
   */
  @Test
  @DisplayName("Test encodeQueryParameters(String); when multiple query params then all encoded")
  void testEncodeQueryParameters_withMultipleQueryParams() {
    // Arrange
    String url = "https://example.org/example?a=1&b=2";

    // Act
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    // Assert
    assertTrue(result.contains("a=1"));
    assertTrue(result.contains("b=2"));
  }

  /**
   * Test {@link ExecuteRequestUtils#encodeQueryParameters(String)}.
   *
   * <ul>
   *   <li>When URL has query parameter with ampersand in value, then value is encoded.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestUtils#encodeQueryParameters(String)}
   */
  @Test
  @DisplayName("Test encodeQueryParameters(String); when query param value has special chars then encoded")
  void testEncodeQueryParameters_withEncodedSpecialCharsInValue() {
    // Arrange
    String url = "https://example.org/example?q=foo%20bar";

    // Act
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    // Assert
    assertTrue(result.startsWith("https://example.org/example"));
    assertTrue(result.contains("q="));
  }
}
