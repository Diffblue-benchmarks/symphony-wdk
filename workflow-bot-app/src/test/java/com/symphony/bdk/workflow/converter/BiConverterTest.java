package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BiConverterTest {

  static class StringIntegerToLongConverter implements BiConverter<String, Integer, Long> {
    @Override
    public Long apply(String s, Integer k) {
      if (s == null) {
        return null;
      }
      return Long.parseLong(s) + k;
    }
  }

  private final StringIntegerToLongConverter converter = new StringIntegerToLongConverter();

  @Test
  void shouldReturnSourceClassWhenGetSourceClassCalled() {
    Class<String> sourceClass = converter.getSourceClass();

    assertThat(sourceClass).isEqualTo(String.class);
  }

  @Test
  void shouldReturnTargetClassWhenGetTargetClassCalled() {
    Class<Long> targetClass = converter.getTargetClass();

    assertThat(targetClass).isEqualTo(Long.class);
  }

  @Test
  void shouldConvertCollectionWhenApplyCollectionCalled() {
    List<String> source = Arrays.asList("1", "2", "3");

    List<Long> result = converter.applyCollection(source, 10);

    assertThat(result).containsExactly(11L, 12L, 13L);
  }

  @Test
  void shouldFilterNullsWhenApplyCollectionContainsNullResults() {
    List<String> source = Arrays.asList("1", null, "3");

    List<Long> result = converter.applyCollection(source, 0);

    assertThat(result).containsExactly(1L, 3L);
  }

  @Test
  void shouldReturnEmptyListWhenApplyCollectionCalledWithEmptySource() {
    List<Long> result = converter.applyCollection(Collections.emptyList(), 5);

    assertThat(result).isEmpty();
  }
}
