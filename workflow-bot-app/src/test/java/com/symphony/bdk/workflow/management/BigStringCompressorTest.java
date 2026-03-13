package com.symphony.bdk.workflow.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class BigStringCompressorTest {

  private final BigStringCompressor compressor = new BigStringCompressor();

  @Test
  void shouldCompressAndDecompressSimpleString() {
    String original = "Hello, World!";

    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String decompressed = compressor.convertToEntityAttribute(compressed);

    assertThat(decompressed).isEqualTo(original);
  }

  @Test
  void shouldCompressAndDecompressEmptyString() {
    String original = "";

    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String decompressed = compressor.convertToEntityAttribute(compressed);

    assertThat(decompressed).isEqualTo(original);
  }

  @Test
  void shouldCompressAndDecompressLargeString() {
    String original = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ".repeat(100);

    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String decompressed = compressor.convertToEntityAttribute(compressed);

    assertThat(decompressed).isEqualTo(original);
    assertThat(compressed.length).isLessThan(original.getBytes(StandardCharsets.UTF_8).length);
  }

  @Test
  void shouldCompressAndDecompressStringWithSpecialCharacters() {
    String original = "Special chars: \n\t\r äöü 中文 😀";

    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String decompressed = compressor.convertToEntityAttribute(compressed);

    assertThat(decompressed).isEqualTo(original);
  }

  @Test
  void shouldCompressAndDecompressMultilineString() {
    String original = "Line 1\nLine 2\nLine 3\nLine 4\nLine 5";

    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String decompressed = compressor.convertToEntityAttribute(compressed);

    assertThat(decompressed).isEqualTo(original);
  }

  @Test
  void shouldCompressStringToNonEmptyByteArray() {
    String original = "Test string";

    byte[] compressed = compressor.convertToDatabaseColumn(original);

    assertThat(compressed).isNotNull();
    assertThat(compressed.length).isGreaterThan(0);
  }

  @Test
  void shouldDecompressByteArrayToOriginalString() {
    String original = "Another test string";
    byte[] compressed = compressor.convertToDatabaseColumn(original);

    String decompressed = compressor.convertToEntityAttribute(compressed);

    assertThat(decompressed).isEqualTo(original);
  }

  @Test
  void shouldHandleStringWithOnlyWhitespace() {
    String original = "   \n\t  \r\n   ";

    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String decompressed = compressor.convertToEntityAttribute(compressed);

    assertThat(decompressed).isEqualTo(original);
  }

  @Test
  void shouldHandleVeryLongString() {
    String original = "A".repeat(10000);

    byte[] compressed = compressor.convertToDatabaseColumn(original);
    String decompressed = compressor.convertToEntityAttribute(compressed);

    assertThat(decompressed).isEqualTo(original);
    assertThat(compressed.length).isLessThan(original.length());
  }

  @Test
  void shouldThrowRuntimeExceptionWhenDecompressingInvalidData() {
    byte[] invalidData = "not a valid gzip data".getBytes(StandardCharsets.UTF_8);

    assertThatThrownBy(() -> compressor.convertToEntityAttribute(invalidData))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(java.io.IOException.class);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenDecompressingEmptyByteArray() {
    byte[] emptyData = new byte[0];

    assertThatThrownBy(() -> compressor.convertToEntityAttribute(emptyData))
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(java.io.IOException.class);
  }
}
