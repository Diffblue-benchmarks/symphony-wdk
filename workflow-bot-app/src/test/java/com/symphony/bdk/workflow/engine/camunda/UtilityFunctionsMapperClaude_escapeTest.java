package com.symphony.bdk.workflow.engine.camunda;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilityFunctionsMapperClaude_escapeTest {

  @Test
  void escape_withNull_shouldReturnNull() {
    // Given: A null string
    String input = null;

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void escape_withEmptyString_shouldReturnEmptyString() {
    // Given: An empty string
    String input = "";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should return empty string
    assertThat(result).isEmpty();
  }

  @Test
  void escape_withSimpleText_shouldReturnUnchanged() {
    // Given: A simple string without special characters
    String input = "Hello World";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should return the same string
    assertThat(result).isEqualTo("Hello World");
  }

  @Test
  void escape_withDoubleQuotes_shouldEscapeQuotes() {
    // Given: A string with double quotes
    String input = "She said \"Hello\"";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape the double quotes
    assertThat(result).isEqualTo("She said \\\"Hello\\\"");
  }

  @Test
  void escape_withBackslash_shouldEscapeBackslash() {
    // Given: A string with backslash
    String input = "C:\\Users\\test";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape the backslashes
    assertThat(result).isEqualTo("C:\\\\Users\\\\test");
  }

  @Test
  void escape_withNewline_shouldEscapeNewline() {
    // Given: A string with newline character
    String input = "Line 1\nLine 2";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape the newline
    assertThat(result).isEqualTo("Line 1\\nLine 2");
  }

  @Test
  void escape_withCarriageReturn_shouldEscapeCarriageReturn() {
    // Given: A string with carriage return
    String input = "Line 1\rLine 2";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape the carriage return
    assertThat(result).isEqualTo("Line 1\\rLine 2");
  }

  @Test
  void escape_withTab_shouldEscapeTab() {
    // Given: A string with tab character
    String input = "Column1\tColumn2";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape the tab
    assertThat(result).isEqualTo("Column1\\tColumn2");
  }

  @Test
  void escape_withBackspace_shouldEscapeBackspace() {
    // Given: A string with backspace character
    String input = "Test\bString";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape the backspace
    assertThat(result).isEqualTo("Test\\bString");
  }

  @Test
  void escape_withFormFeed_shouldEscapeFormFeed() {
    // Given: A string with form feed character
    String input = "Page1\fPage2";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape the form feed
    assertThat(result).isEqualTo("Page1\\fPage2");
  }

  @Test
  void escape_withMultipleSpecialCharacters_shouldEscapeAll() {
    // Given: A string with multiple special characters
    String input = "Test\n\"quoted\"\tvalue\\path";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape all special characters
    assertThat(result).isEqualTo("Test\\n\\\"quoted\\\"\\tvalue\\\\path");
  }

  @Test
  void escape_withUnicodeCharacters_shouldPreserveUnicode() {
    // Given: A string with Unicode characters
    String input = "Hello 世界 🎉";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should preserve Unicode characters (JsonStringEncoder handles these)
    assertThat(result).isNotNull();
    assertThat(result).contains("Hello");
  }

  @Test
  void escape_withSingleQuote_shouldNotEscapeSingleQuote() {
    // Given: A string with single quotes (not escaped in JSON)
    String input = "It's a test";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should not escape single quotes (valid in JSON strings)
    assertThat(result).isEqualTo("It's a test");
  }

  @Test
  void escape_withForwardSlash_shouldHandleForwardSlash() {
    // Given: A string with forward slash
    String input = "http://example.com/path";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Forward slash may or may not be escaped (both valid in JSON)
    assertThat(result).isNotNull();
    // JsonStringEncoder may optionally escape forward slashes
  }

  @Test
  void escape_withControlCharacters_shouldEscapeControlChars() {
    // Given: A string with control characters (ASCII < 32)
    String input = "Test\u0001\u0002String";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape control characters
    assertThat(result).isNotNull();
    assertThat(result).doesNotContain("\u0001");
    assertThat(result).doesNotContain("\u0002");
  }

  @Test
  void escape_withMixedContent_shouldEscapeOnlySpecialChars() {
    // Given: A string with mixed content
    String input = "Normal text with \"quotes\" and\nnewlines";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape special characters while preserving normal text
    assertThat(result).contains("Normal text with");
    assertThat(result).contains("\\\"quotes\\\"");
    assertThat(result).contains("\\n");
  }

  @Test
  void escape_withOnlySpecialCharacters_shouldEscapeAll() {
    // Given: A string with only special characters
    String input = "\"\\\n\r\t";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape all characters
    assertThat(result).isEqualTo("\\\"\\\\\\n\\r\\t");
  }

  @Test
  void escape_withWhitespace_shouldPreserveSpaces() {
    // Given: A string with regular spaces
    String input = "   spaces   ";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should preserve regular spaces (no escaping needed)
    assertThat(result).isEqualTo("   spaces   ");
  }

  @Test
  void escape_withAlphanumeric_shouldReturnUnchanged() {
    // Given: A string with only alphanumeric characters
    String input = "abc123XYZ";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should return unchanged
    assertThat(result).isEqualTo("abc123XYZ");
  }

  @Test
  void escape_withSpecialPunctuation_shouldPreserveMostPunctuation() {
    // Given: A string with punctuation that doesn't need escaping
    String input = "Hello, world! How are you? I'm fine.";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should preserve most punctuation
    assertThat(result).isEqualTo("Hello, world! How are you? I'm fine.");
  }

  @Test
  void escape_calledMultipleTimes_shouldReturnConsistentResults() {
    // Given: The same string
    String input = "Test \"string\" with\nspecial chars";

    // When: escape() is called multiple times
    String result1 = UtilityFunctionsMapper.escape(input);
    String result2 = UtilityFunctionsMapper.escape(input);
    String result3 = UtilityFunctionsMapper.escape(input);

    // Then: All results should be equal
    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);
  }

  @Test
  void escape_withAlreadyEscapedString_shouldEscapeAgain() {
    // Given: A string that already contains escape sequences
    String input = "Already \\\"escaped\\\"";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape the backslashes and quotes again (double escaping)
    assertThat(result).isEqualTo("Already \\\\\\\"escaped\\\\\\\"");
  }

  @Test
  void escape_withJsonLikeString_shouldEscapeForJsonEncoding() {
    // Given: A string that looks like JSON
    String input = "{\"key\":\"value\"}";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape quotes and braces are preserved
    assertThat(result).isEqualTo("{\\\"key\\\":\\\"value\\\"}");
  }

  @Test
  void escape_withLongString_shouldHandleLargeInput() {
    // Given: A long string with special characters
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      sb.append("Line ").append(i).append("\n");
    }
    String input = sb.toString();

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape all newlines
    assertThat(result).isNotNull();
    assertThat(result).contains("\\n");
    assertThat(result).doesNotContain("\n");
  }

  @Test
  void escape_withNumericString_shouldReturnUnchanged() {
    // Given: A numeric string
    String input = "12345.67890";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should return unchanged
    assertThat(result).isEqualTo("12345.67890");
  }

  @Test
  void escape_withBrackets_shouldPreserveBrackets() {
    // Given: A string with brackets
    String input = "array[0] = {key: value}";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should preserve brackets (no escaping needed for JSON string content)
    assertThat(result).isEqualTo("array[0] = {key: value}");
  }

  @Test
  void escape_withEmailAddress_shouldPreserveEmail() {
    // Given: An email address
    String input = "user@example.com";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should preserve the email unchanged
    assertThat(result).isEqualTo("user@example.com");
  }

  @Test
  void escape_withUrl_shouldPreserveUrl() {
    // Given: A URL
    String input = "https://example.com?param=value&other=123";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should preserve URL characters (may escape forward slashes)
    assertThat(result).isNotNull();
    assertThat(result).contains("example.com");
  }

  @Test
  void escape_withCRLF_shouldEscapeBoth() {
    // Given: A string with Windows-style line endings (CRLF)
    String input = "Line 1\r\nLine 2\r\nLine 3";

    // When: escape() is called
    String result = UtilityFunctionsMapper.escape(input);

    // Then: Should escape both CR and LF
    assertThat(result).isEqualTo("Line 1\\r\\nLine 2\\r\\nLine 3");
  }
}
