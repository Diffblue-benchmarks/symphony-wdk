package com.symphony.bdk.workflow.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ConverterTest {

  @Test
  void shouldReturnSourceClassFromGenericInterface() {
    TestConverter converter = new TestConverter();

    Class<String> sourceClass = converter.getSourceClass();

    assertThat(sourceClass).isEqualTo(String.class);
  }

  @Test
  void shouldReturnTargetClassFromGenericInterface() {
    TestConverter converter = new TestConverter();

    Class<Integer> targetClass = converter.getTargetClass();

    assertThat(targetClass).isEqualTo(Integer.class);
  }

  @Test
  void shouldConvertCollectionOfSourceToTarget() {
    TestConverter converter = new TestConverter();
    List<String> sourceList = Arrays.asList("1", "2", "3");

    List<Integer> result = converter.applyCollection(sourceList);

    assertThat(result).containsExactly(1, 2, 3);
  }

  @Test
  void shouldFilterOutNullValuesWhenConvertingCollection() {
    TestConverter converter = new TestConverter();
    List<String> sourceList = Arrays.asList("1", "null", "3");

    List<Integer> result = converter.applyCollection(sourceList);

    assertThat(result).containsExactly(1, 3);
  }

  @Test
  void shouldReturnEmptyListWhenSourceCollectionIsEmpty() {
    TestConverter converter = new TestConverter();
    List<String> sourceList = Arrays.asList();

    List<Integer> result = converter.applyCollection(sourceList);

    assertThat(result).isEmpty();
  }

  /**
   * Test implementation of Converter for testing purposes.
   * Converts String to Integer, returns null for "null" string.
   */
  private static class TestConverter implements Converter<String, Integer> {
    @Override
    public Integer apply(String source) {
      if ("null".equals(source)) {
        return null;
      }
      return Integer.parseInt(source);
    }
  }
}
