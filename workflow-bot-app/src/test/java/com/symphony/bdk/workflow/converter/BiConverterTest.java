package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BiConverterTest {

  private static class StringToIntegerConverter implements BiConverter<String, Integer, Integer> {
    @Override
    public Integer apply(String s, Integer multiplier) {
      try {
        return Integer.parseInt(s) * multiplier;
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

  @Test
  void getSourceClassShouldReturnFirstTypeArgument() {
    BiConverter<String, Integer, Integer> converter = new StringToIntegerConverter();

    Class<String> sourceClass = converter.getSourceClass();

    assertNotNull(sourceClass);
    assertEquals(String.class, sourceClass);
  }

  @Test
  void getTargetClassShouldReturnThirdTypeArgument() {
    BiConverter<String, Integer, Integer> converter = new StringToIntegerConverter();

    Class<Integer> targetClass = converter.getTargetClass();

    assertNotNull(targetClass);
    assertEquals(Integer.class, targetClass);
  }

  @Test
  void applyCollectionShouldConvertAllElementsInList() {
    BiConverter<String, Integer, Integer> converter = new StringToIntegerConverter();
    List<String> source = Arrays.asList("1", "2", "3");

    List<Integer> result = converter.applyCollection(source, 2);

    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals(2, result.get(0));
    assertEquals(4, result.get(1));
    assertEquals(6, result.get(2));
  }

  @Test
  void applyCollectionShouldFilterOutNullResults() {
    BiConverter<String, Integer, Integer> converter = new StringToIntegerConverter();
    List<String> source = Arrays.asList("1", "invalid", "3");

    List<Integer> result = converter.applyCollection(source, 1);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(1, result.get(0));
    assertEquals(3, result.get(1));
  }

  @Test
  void applyCollectionShouldHandleEmptyList() {
    BiConverter<String, Integer, Integer> converter = new StringToIntegerConverter();
    List<String> source = Collections.emptyList();

    List<Integer> result = converter.applyCollection(source, 1);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}
