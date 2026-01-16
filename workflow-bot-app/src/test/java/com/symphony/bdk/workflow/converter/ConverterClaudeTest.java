package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterClaudeTest {

  // ==================== getSourceClass Tests ====================

  @Test
  void getSourceClass_withConcreteImplementation_shouldReturnSourceClass() {
    // Given: A concrete implementation of Converter
    TestConverter converter = new TestConverter();

    // When: Getting the source class
    Class<String> sourceClass = converter.getSourceClass();

    // Then: Should return the correct source class type (String)
    assertThat(sourceClass).isEqualTo(String.class);
  }

  @Test
  void getSourceClass_withDifferentTypes_shouldReturnCorrectSourceClass() {
    // Given: A Converter with Integer as source type
    IntegerConverter converter = new IntegerConverter();

    // When: Getting the source class
    Class<Integer> sourceClass = converter.getSourceClass();

    // Then: Should return Integer class
    assertThat(sourceClass).isEqualTo(Integer.class);
  }

  // ==================== getTargetClass Tests ====================

  @Test
  void getTargetClass_withConcreteImplementation_shouldReturnTargetClass() {
    // Given: A concrete implementation of Converter
    TestConverter converter = new TestConverter();

    // When: Getting the target class
    Class<StringBuilder> targetClass = converter.getTargetClass();

    // Then: Should return the correct target class type (StringBuilder)
    assertThat(targetClass).isEqualTo(StringBuilder.class);
  }

  @Test
  void getTargetClass_withDifferentTypes_shouldReturnCorrectTargetClass() {
    // Given: A Converter with Double as target type
    IntegerConverter converter = new IntegerConverter();

    // When: Getting the target class
    Class<Double> targetClass = converter.getTargetClass();

    // Then: Should return Double class
    assertThat(targetClass).isEqualTo(Double.class);
  }

  // ==================== applyCollection Tests ====================

  @Test
  void applyCollection_withEmptyList_shouldReturnEmptyList() {
    // Given: An empty list and a Converter
    TestConverter converter = new TestConverter();
    List<String> emptyList = Collections.emptyList();

    // When: Applying the converter to the empty collection
    List<StringBuilder> result = converter.applyCollection(emptyList);

    // Then: Should return an empty list
    assertThat(result).isEmpty();
  }

  @Test
  void applyCollection_withNonEmptyList_shouldConvertAllElements() {
    // Given: A list of strings and a Converter
    TestConverter converter = new TestConverter();
    List<String> sourceList = Arrays.asList("hello", "world", "test");

    // When: Applying the converter to the collection
    List<StringBuilder> result = converter.applyCollection(sourceList);

    // Then: Should convert all elements correctly
    assertThat(result).hasSize(3);
    assertThat(result.get(0).toString()).isEqualTo("HELLO");
    assertThat(result.get(1).toString()).isEqualTo("WORLD");
    assertThat(result.get(2).toString()).isEqualTo("TEST");
  }

  @Test
  void applyCollection_withNullElementsInList_shouldFilterOutNulls() {
    // Given: A Converter that returns null for certain inputs
    NullReturningConverter converter = new NullReturningConverter();
    List<String> sourceList = Arrays.asList("keep", "null", "keep", "null");

    // When: Applying the converter to the collection
    List<String> result = converter.applyCollection(sourceList);

    // Then: Should filter out null results
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly("keep-converted", "keep-converted");
  }

  @Test
  void applyCollection_withAllNullResults_shouldReturnEmptyList() {
    // Given: A Converter that returns null for all inputs
    NullReturningConverter converter = new NullReturningConverter();
    List<String> sourceList = Arrays.asList("null", "null", "null");

    // When: Applying the converter to the collection
    List<String> result = converter.applyCollection(sourceList);

    // Then: Should return an empty list
    assertThat(result).isEmpty();
  }

  @Test
  void applyCollection_withSingleElement_shouldConvertSingleElement() {
    // Given: A list with a single element
    TestConverter converter = new TestConverter();
    List<String> singleElementList = Collections.singletonList("single");

    // When: Applying the converter to the collection
    List<StringBuilder> result = converter.applyCollection(singleElementList);

    // Then: Should return a list with one converted element
    assertThat(result).hasSize(1);
    assertThat(result.get(0).toString()).isEqualTo("SINGLE");
  }

  @Test
  void applyCollection_withMixedContent_shouldPreserveOrder() {
    // Given: A list with mixed content
    TestConverter converter = new TestConverter();
    List<String> sourceList = Arrays.asList("first", "second", "third");

    // When: Applying the converter to the collection
    List<StringBuilder> result = converter.applyCollection(sourceList);

    // Then: Should preserve the order of elements
    assertThat(result).hasSize(3);
    assertThat(result.get(0).toString()).isEqualTo("FIRST");
    assertThat(result.get(1).toString()).isEqualTo("SECOND");
    assertThat(result.get(2).toString()).isEqualTo("THIRD");
  }

  @Test
  void applyCollection_withComplexTypes_shouldConvertCorrectly() {
    // Given: A Converter with Integer to Double conversion
    IntegerConverter converter = new IntegerConverter();
    List<Integer> sourceList = Arrays.asList(1, 2, 3, 4, 5);

    // When: Applying the converter to the collection
    List<Double> result = converter.applyCollection(sourceList);

    // Then: Should convert all integers to doubles correctly
    assertThat(result).hasSize(5);
    assertThat(result).containsExactly(2.0, 4.0, 6.0, 8.0, 10.0);
  }

  // ==================== Test Implementations ====================

  /**
   * Test implementation of Converter that converts String to StringBuilder
   * by transforming to uppercase.
   */
  private static class TestConverter implements Converter<String, StringBuilder> {
    @Override
    public StringBuilder apply(String source) {
      return new StringBuilder(source.toUpperCase());
    }
  }

  /**
   * Test implementation of Converter that converts Integer to Double
   * by multiplying by 2.
   */
  private static class IntegerConverter implements Converter<Integer, Double> {
    @Override
    public Double apply(Integer source) {
      return source.doubleValue() * 2;
    }
  }

  /**
   * Test implementation of Converter that returns null for inputs
   * that equal "null" string, otherwise returns a converted value.
   */
  private static class NullReturningConverter implements Converter<String, String> {
    @Override
    public String apply(String source) {
      if ("null".equals(source)) {
        return null;
      }
      return source + "-converted";
    }
  }
}
