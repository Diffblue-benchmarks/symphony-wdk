package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BiConverterClaudeTest {

  // ==================== getSourceClass Tests ====================

  @Test
  void getSourceClass_withConcreteImplementation_shouldReturnSourceClass() {
    // Given: A concrete implementation of BiConverter
    TestBiConverter converter = new TestBiConverter();

    // When: Getting the source class
    Class<String> sourceClass = converter.getSourceClass();

    // Then: Should return the correct source class type (String)
    assertThat(sourceClass).isEqualTo(String.class);
  }

  @Test
  void getSourceClass_withDifferentTypes_shouldReturnCorrectSourceClass() {
    // Given: A BiConverter with Integer as source type
    IntegerBiConverter converter = new IntegerBiConverter();

    // When: Getting the source class
    Class<Integer> sourceClass = converter.getSourceClass();

    // Then: Should return Integer class
    assertThat(sourceClass).isEqualTo(Integer.class);
  }

  // ==================== getTargetClass Tests ====================

  @Test
  void getTargetClass_withConcreteImplementation_shouldReturnTargetClass() {
    // Given: A concrete implementation of BiConverter
    TestBiConverter converter = new TestBiConverter();

    // When: Getting the target class
    Class<StringBuilder> targetClass = converter.getTargetClass();

    // Then: Should return the correct target class type (StringBuilder)
    assertThat(targetClass).isEqualTo(StringBuilder.class);
  }

  @Test
  void getTargetClass_withDifferentTypes_shouldReturnCorrectTargetClass() {
    // Given: A BiConverter with Double as target type
    IntegerBiConverter converter = new IntegerBiConverter();

    // When: Getting the target class
    Class<Double> targetClass = converter.getTargetClass();

    // Then: Should return Double class
    assertThat(targetClass).isEqualTo(Double.class);
  }

  // ==================== applyCollection Tests ====================

  @Test
  void applyCollection_withEmptyList_shouldReturnEmptyList() {
    // Given: An empty list and a BiConverter
    TestBiConverter converter = new TestBiConverter();
    List<String> emptyList = Collections.emptyList();
    Integer context = 42;

    // When: Applying the converter to the empty collection
    List<StringBuilder> result = converter.applyCollection(emptyList, context);

    // Then: Should return an empty list
    assertThat(result).isEmpty();
  }

  @Test
  void applyCollection_withNonEmptyList_shouldConvertAllElements() {
    // Given: A list of strings and a BiConverter
    TestBiConverter converter = new TestBiConverter();
    List<String> sourceList = Arrays.asList("hello", "world", "test");
    Integer context = 10;

    // When: Applying the converter to the collection
    List<StringBuilder> result = converter.applyCollection(sourceList, context);

    // Then: Should convert all elements correctly
    assertThat(result).hasSize(3);
    assertThat(result.get(0).toString()).isEqualTo("hello-10");
    assertThat(result.get(1).toString()).isEqualTo("world-10");
    assertThat(result.get(2).toString()).isEqualTo("test-10");
  }

  @Test
  void applyCollection_withNullElementsInList_shouldFilterOutNulls() {
    // Given: A BiConverter that returns null for certain inputs
    NullReturningBiConverter converter = new NullReturningBiConverter();
    List<String> sourceList = Arrays.asList("keep", "null", "keep", "null");
    Integer context = 0;

    // When: Applying the converter to the collection
    List<String> result = converter.applyCollection(sourceList, context);

    // Then: Should filter out null results
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly("keep-converted", "keep-converted");
  }

  @Test
  void applyCollection_withAllNullResults_shouldReturnEmptyList() {
    // Given: A BiConverter that returns null for all inputs
    NullReturningBiConverter converter = new NullReturningBiConverter();
    List<String> sourceList = Arrays.asList("null", "null", "null");
    Integer context = 0;

    // When: Applying the converter to the collection
    List<String> result = converter.applyCollection(sourceList, context);

    // Then: Should return an empty list
    assertThat(result).isEmpty();
  }

  @Test
  void applyCollection_withSingleElement_shouldConvertSingleElement() {
    // Given: A list with a single element
    TestBiConverter converter = new TestBiConverter();
    List<String> singleElementList = Collections.singletonList("single");
    Integer context = 99;

    // When: Applying the converter to the collection
    List<StringBuilder> result = converter.applyCollection(singleElementList, context);

    // Then: Should return a list with one converted element
    assertThat(result).hasSize(1);
    assertThat(result.get(0).toString()).isEqualTo("single-99");
  }

  @Test
  void applyCollection_withDifferentContextValues_shouldUseCorrectContext() {
    // Given: A BiConverter that uses the context parameter
    TestBiConverter converter = new TestBiConverter();
    List<String> sourceList = Arrays.asList("a", "b");
    Integer context1 = 1;
    Integer context2 = 2;

    // When: Applying the converter with different context values
    List<StringBuilder> result1 = converter.applyCollection(sourceList, context1);
    List<StringBuilder> result2 = converter.applyCollection(sourceList, context2);

    // Then: Should use the correct context in each case
    assertThat(result1.get(0).toString()).isEqualTo("a-1");
    assertThat(result1.get(1).toString()).isEqualTo("b-1");
    assertThat(result2.get(0).toString()).isEqualTo("a-2");
    assertThat(result2.get(1).toString()).isEqualTo("b-2");
  }

  // ==================== Test Implementations ====================

  /**
   * Test implementation of BiConverter that converts String to StringBuilder
   * with an Integer context appended.
   */
  private static class TestBiConverter implements BiConverter<String, Integer, StringBuilder> {
    @Override
    public StringBuilder apply(String source, Integer context) {
      return new StringBuilder(source).append("-").append(context);
    }
  }

  /**
   * Test implementation of BiConverter that converts Integer to Double
   * with a String context.
   */
  private static class IntegerBiConverter implements BiConverter<Integer, String, Double> {
    @Override
    public Double apply(Integer source, String context) {
      return source.doubleValue() + context.length();
    }
  }

  /**
   * Test implementation of BiConverter that returns null for inputs
   * that equal "null" string, otherwise returns a converted value.
   */
  private static class NullReturningBiConverter implements BiConverter<String, Integer, String> {
    @Override
    public String apply(String source, Integer context) {
      if ("null".equals(source)) {
        return null;
      }
      return source + "-converted";
    }
  }
}
