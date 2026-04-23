package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {

  private static class StringToLengthConverter implements Converter<String, Integer> {
    @Override
    public Integer apply(String source) {
      return source == null ? null : source.length();
    }
  }

  private final StringToLengthConverter converter = new StringToLengthConverter();

  @Test
  void shouldReturnSourceClassWhenGetSourceClassIsCalled() {
    Class<String> sourceClass = converter.getSourceClass();

    assertThat(sourceClass).isEqualTo(String.class);
  }

  @Test
  void shouldReturnTargetClassWhenGetTargetClassIsCalled() {
    Class<Integer> targetClass = converter.getTargetClass();

    assertThat(targetClass).isEqualTo(Integer.class);
  }

  @Test
  void shouldConvertCollectionWhenApplyCollectionIsCalled() {
    List<String> source = Arrays.asList("hello", "world", "!");

    List<Integer> result = converter.applyCollection(source);

    assertThat(result).containsExactly(5, 5, 1);
  }

  @Test
  void shouldFilterNullsWhenApplyCollectionIsCalledWithNullValues() {
    List<String> source = Arrays.asList("hello", null, "!");

    List<Integer> result = converter.applyCollection(source);

    assertThat(result).containsExactly(5, 1);
  }
}
