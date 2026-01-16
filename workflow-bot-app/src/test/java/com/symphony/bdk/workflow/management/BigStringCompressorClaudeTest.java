package com.symphony.bdk.workflow.management;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BigStringCompressorClaudeTest {

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: Creating a new instance of BigStringCompressor
    BigStringCompressor compressor = new BigStringCompressor();

    // Then: The instance should be created successfully
    assertThat(compressor).isNotNull();
  }

  // ==================== convertToDatabaseColumn Tests ====================

  @Test
  void convertToDatabaseColumn_withSimpleString_shouldCompressSuccessfully() {
    // Given: A BigStringCompressor and a simple string
    BigStringCompressor compressor = new BigStringCompressor();
    String input = "Hello, World!";

    // When: Converting the string to database column (compressing)
    byte[] result = compressor.convertToDatabaseColumn(input);

    // Then: Should return a non-null compressed byte array
    assertThat(result).isNotNull();
    assertThat(result.length).isGreaterThan(0);
    // Compressed data should have GZIP magic number (1f 8b)
    assertThat(result[0]).isEqualTo((byte) 0x1f);
    assertThat(result[1]).isEqualTo((byte) 0x8b);
  }

  @Test
  void convertToDatabaseColumn_withEmptyString_shouldCompressSuccessfully() {
    // Given: A BigStringCompressor and an empty string
    BigStringCompressor compressor = new BigStringCompressor();
    String input = "";

    // When: Converting the empty string to database column
    byte[] result = compressor.convertToDatabaseColumn(input);

    // Then: Should return a valid GZIP compressed byte array (even for empty input)
    assertThat(result).isNotNull();
    assertThat(result.length).isGreaterThan(0);
    // Should still have GZIP magic number
    assertThat(result[0]).isEqualTo((byte) 0x1f);
    assertThat(result[1]).isEqualTo((byte) 0x8b);
  }

  @Test
  void convertToDatabaseColumn_withLongString_shouldCompressSuccessfully() {
    // Given: A BigStringCompressor and a long string
    BigStringCompressor compressor = new BigStringCompressor();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      sb.append("This is a repeating pattern that should compress well. ");
    }
    String input = sb.toString();

    // When: Converting the long string to database column
    byte[] result = compressor.convertToDatabaseColumn(input);

    // Then: Should compress successfully and result should be smaller than original
    assertThat(result).isNotNull();
    assertThat(result.length).isLessThan(input.getBytes(StandardCharsets.UTF_8).length);
  }

  @Test
  void convertToDatabaseColumn_withSpecialCharacters_shouldCompressSuccessfully() {
    // Given: A string with special characters including Unicode
    BigStringCompressor compressor = new BigStringCompressor();
    String input = "Hello 世界! Émojis: 🎉🚀 Special chars: @#$%^&*()";

    // When: Converting to database column
    byte[] result = compressor.convertToDatabaseColumn(input);

    // Then: Should compress successfully
    assertThat(result).isNotNull();
    assertThat(result.length).isGreaterThan(0);
    assertThat(result[0]).isEqualTo((byte) 0x1f);
    assertThat(result[1]).isEqualTo((byte) 0x8b);
  }

  @Test
  void convertToDatabaseColumn_withNewlinesAndTabs_shouldCompressSuccessfully() {
    // Given: A string with newlines and tabs
    BigStringCompressor compressor = new BigStringCompressor();
    String input = "Line 1\nLine 2\tTabbed\r\nWindows newline";

    // When: Converting to database column
    byte[] result = compressor.convertToDatabaseColumn(input);

    // Then: Should compress successfully
    assertThat(result).isNotNull();
    assertThat(result.length).isGreaterThan(0);
  }

  // ==================== convertToEntityAttribute Tests ====================

  @Test
  void convertToEntityAttribute_withValidCompressedData_shouldDecompressSuccessfully() throws Exception {
    // Given: Valid GZIP compressed data
    BigStringCompressor compressor = new BigStringCompressor();
    String original = "Hello, World!";
    byte[] compressed = compressString(original);

    // When: Converting from database column to entity attribute (decompressing)
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should decompress to the original string
    assertThat(result).isEqualTo(original);
  }

  @Test
  void convertToEntityAttribute_withEmptyCompressedString_shouldDecompressToEmptyString() throws Exception {
    // Given: GZIP compressed empty string
    BigStringCompressor compressor = new BigStringCompressor();
    String original = "";
    byte[] compressed = compressString(original);

    // When: Decompressing the empty string
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should return an empty string
    assertThat(result).isEmpty();
  }

  @Test
  void convertToEntityAttribute_withLongCompressedString_shouldDecompressSuccessfully() throws Exception {
    // Given: A compressed long string
    BigStringCompressor compressor = new BigStringCompressor();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      sb.append("Repeating pattern. ");
    }
    String original = sb.toString();
    byte[] compressed = compressString(original);

    // When: Decompressing
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should match the original long string
    assertThat(result).isEqualTo(original);
  }

  @Test
  void convertToEntityAttribute_withSpecialCharacters_shouldDecompressSuccessfully() throws Exception {
    // Given: Compressed string with special characters
    BigStringCompressor compressor = new BigStringCompressor();
    String original = "Hello 世界! Émojis: 🎉🚀 Special chars: @#$%^&*()";
    byte[] compressed = compressString(original);

    // When: Decompressing
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should preserve all special characters
    assertThat(result).isEqualTo(original);
  }

  @Test
  void convertToEntityAttribute_withInvalidGzipData_shouldThrowRuntimeException() {
    // Given: Invalid GZIP data (not compressed)
    BigStringCompressor compressor = new BigStringCompressor();
    byte[] invalidData = "Not a GZIP compressed data".getBytes(StandardCharsets.UTF_8);

    // When/Then: Should throw RuntimeException when trying to decompress invalid data
    assertThatThrownBy(() -> compressor.convertToEntityAttribute(invalidData))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(java.io.IOException.class);
  }

  @Test
  void convertToEntityAttribute_withCorruptedGzipHeader_shouldThrowRuntimeException() {
    // Given: Data with GZIP magic number but corrupted content
    BigStringCompressor compressor = new BigStringCompressor();
    byte[] corruptedData = new byte[]{0x1f, (byte) 0x8b, 0x00, 0x00, 0x00};

    // When/Then: Should throw RuntimeException
    assertThatThrownBy(() -> compressor.convertToEntityAttribute(corruptedData))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(java.io.IOException.class);
  }

  // ==================== Round-trip Tests ====================

  @Test
  void roundTrip_withSimpleString_shouldPreserveOriginalValue() {
    // Given: A BigStringCompressor and a string
    BigStringCompressor compressor = new BigStringCompressor();
    String original = "Test string for round-trip";

    // When: Compressing and then decompressing
    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should get back the original string
    assertThat(result).isEqualTo(original);
  }

  @Test
  void roundTrip_withEmptyString_shouldPreserveEmptyString() {
    // Given: An empty string
    BigStringCompressor compressor = new BigStringCompressor();
    String original = "";

    // When: Compressing and decompressing
    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should get back an empty string
    assertThat(result).isEmpty();
  }

  @Test
  void roundTrip_withLongString_shouldPreserveOriginalValue() {
    // Given: A long string with repeating patterns
    BigStringCompressor compressor = new BigStringCompressor();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 5000; i++) {
      sb.append("Pattern ").append(i).append(" ");
    }
    String original = sb.toString();

    // When: Round-tripping through compression and decompression
    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should preserve the exact original string
    assertThat(result).isEqualTo(original);
  }

  @Test
  void roundTrip_withMultilineString_shouldPreserveNewlines() {
    // Given: A multi-line string
    BigStringCompressor compressor = new BigStringCompressor();
    String original = "Line 1\nLine 2\nLine 3\r\nLine 4";

    // When: Round-tripping
    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should preserve all newlines
    assertThat(result).isEqualTo(original);
  }

  @Test
  void roundTrip_withUnicodeCharacters_shouldPreserveUnicode() {
    // Given: A string with various Unicode characters
    BigStringCompressor compressor = new BigStringCompressor();
    String original = "English, 中文, 日本語, العربية, עברית, Ελληνικά, Русский, 🎉🚀🌟";

    // When: Round-tripping
    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should preserve all Unicode characters exactly
    assertThat(result).isEqualTo(original);
  }

  @Test
  void roundTrip_withJsonString_shouldPreserveJsonStructure() {
    // Given: A JSON string
    BigStringCompressor compressor = new BigStringCompressor();
    String original = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\",\"active\":true}";

    // When: Round-tripping
    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should preserve the JSON structure exactly
    assertThat(result).isEqualTo(original);
  }

  @Test
  void roundTrip_withVeryLargeString_shouldPreserveValue() {
    // Given: A very large string (multiple buffer sizes)
    BigStringCompressor compressor = new BigStringCompressor();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      sb.append("This is line ").append(i).append(" with some additional text to make it longer.\n");
    }
    String original = sb.toString();

    // When: Round-tripping the large string
    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String result = compressor.convertToEntityAttribute(compressed);

    // Then: Should preserve the entire large string
    assertThat(result).isEqualTo(original);
    assertThat(result.length()).isEqualTo(original.length());
  }

  // ==================== Helper Methods ====================

  /**
   * Helper method to compress a string using GZIP (mimics the implementation)
   */
  private byte[] compressString(String input) throws Exception {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
    gzipOutputStream.write(input.getBytes(StandardCharsets.UTF_8));
    gzipOutputStream.close();
    return outputStream.toByteArray();
  }
}
