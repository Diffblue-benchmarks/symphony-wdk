package com.symphony.bdk.workflow.management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BigStringCompressorTest {

  private BigStringCompressor compressor;

  @BeforeEach
  void setUp() {
    compressor = new BigStringCompressor();
  }

  // ---- convertToDatabaseColumn / compress ----

  @Test
  void shouldCompressStringToDatabaseColumn() {
    // given
    String input = "hello world";

    // when
    byte[] result = compressor.convertToDatabaseColumn(input);

    // then
    assertThat(result).isNotNull();
    assertThat(result.length).isGreaterThan(0);
  }

  @Test
  void shouldRoundTripCompressAndDecompress() {
    // given
    String input = "hello world";

    // when
    byte[] compressed = compressor.convertToDatabaseColumn(input);
    String result = compressor.convertToEntityAttribute(compressed);

    // then
    assertThat(result).isEqualTo(input);
  }

  @Test
  void shouldHandleEmptyString() {
    // given
    String input = "";

    // when
    byte[] compressed = compressor.convertToDatabaseColumn(input);
    String result = compressor.convertToEntityAttribute(compressed);

    // then
    assertThat(result).isEqualTo(input);
  }

  @Test
  void shouldHandleLargeStringExceedingBuffer() {
    // given - string larger than decompress buffer (100 bytes)
    String input = "a".repeat(10000);

    // when
    byte[] compressed = compressor.convertToDatabaseColumn(input);
    String result = compressor.convertToEntityAttribute(compressed);

    // then
    assertThat(result).isEqualTo(input);
  }

  @Test
  void shouldPreserveUnicodeCharacters() {
    // given
    String input = "こんにちは世界";

    // when
    byte[] compressed = compressor.convertToDatabaseColumn(input);
    String result = compressor.convertToEntityAttribute(compressed);

    // then
    assertThat(result).isEqualTo(input);
  }

  // ---- convertToEntityAttribute error handling ----

  @Test
  void shouldThrowRuntimeExceptionWhenDecompressingInvalidData() {
    // given
    byte[] invalidData = new byte[]{1, 2, 3, 4, 5};

    // when / then
    assertThatThrownBy(() -> compressor.convertToEntityAttribute(invalidData))
        .isInstanceOf(RuntimeException.class);
  }
}
