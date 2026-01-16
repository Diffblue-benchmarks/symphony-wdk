package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for DefaultObjectConverter.convertCollection methods.
 * - Testing the two-parameter convertCollection(List, Class) method at lines 98-109.
 * - Testing the three-parameter convertCollection(List, Object, Class) method at lines 113-124.
 * - Testing the three-parameter convertCollection(List, Class, Class) method at lines 131-146.
 * - Testing the four-parameter convertCollection(List, Object, Class, Class) method at lines 150-165.
 */
class DefaultObjectConverterClaude_convertCollectionTest {

  // ==================== Null and Empty Source Tests ====================

  @Test
  void convertCollection_withNullSource_shouldReturnEmptyList() {
    // Given: A converter and null source
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );

    // When: Converting null collection
    List<Integer> result = objectConverter.convertCollection(null, Integer.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollection_withEmptySource_shouldReturnEmptyList() {
    // Given: A converter and empty source
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );

    // When: Converting empty collection
    List<Integer> result = objectConverter.convertCollection(Collections.emptyList(), Integer.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  // ==================== Successful Conversion Tests ====================

  @Test
  void convertCollection_withSingleElement_shouldConvertSuccessfully() {
    // Given: A converter and single-element list
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<String> source = Collections.singletonList("42");

    // When: Converting the collection
    List<Integer> result = objectConverter.convertCollection(source, Integer.class);

    // Then: Should convert successfully
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(42);
  }

  @Test
  void convertCollection_withMultipleElements_shouldConvertAll() {
    // Given: A converter and multiple-element list
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<String> source = Arrays.asList("1", "2", "3", "4", "5");

    // When: Converting the collection
    List<Integer> result = objectConverter.convertCollection(source, Integer.class);

    // Then: Should convert all elements
    assertThat(result).hasSize(5);
    assertThat(result).containsExactly(1, 2, 3, 4, 5);
  }

  @Test
  void convertCollection_withStringToInteger_shouldMaintainOrder() {
    // Given: A converter with ordered input
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<String> source = Arrays.asList("10", "20", "30");

    // When: Converting the collection
    List<Integer> result = objectConverter.convertCollection(source, Integer.class);

    // Then: Should maintain order
    assertThat(result).containsExactly(10, 20, 30);
  }

  @Test
  void convertCollection_withIntegerToString_shouldConvertSuccessfully() {
    // Given: Integer to String converter
    IntegerToStringConverter converter = new IntegerToStringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Integer> source = Arrays.asList(100, 200, 300);

    // When: Converting the collection
    List<String> result = objectConverter.convertCollection(source, String.class);

    // Then: Should convert all successfully
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly("100", "200", "300");
  }

  @Test
  void convertCollection_withDoubleToInteger_shouldConvertSuccessfully() {
    // Given: Double to Integer converter
    DoubleToIntegerConverter converter = new DoubleToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Double> source = Arrays.asList(1.9, 2.5, 3.1);

    // When: Converting the collection
    List<Integer> result = objectConverter.convertCollection(source, Integer.class);

    // Then: Should convert all successfully
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly(1, 2, 3);
  }

  // ==================== Null Element Filtering Tests ====================

  @Test
  void convertCollection_withConverterReturningNull_shouldFilterOutNulls() {
    // Given: A converter that returns null for negative numbers
    NullFilteringConverter converter = new NullFilteringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Integer> source = Arrays.asList(1, -1, 2, -2, 3);

    // When: Converting the collection
    List<String> result = objectConverter.convertCollection(source, String.class);

    // Then: Should filter out nulls (negative numbers)
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly("1", "2", "3");
  }

  @Test
  void convertCollection_withAllNullResults_shouldReturnEmptyList() {
    // Given: A converter that returns null for all elements
    NullFilteringConverter converter = new NullFilteringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Integer> source = Arrays.asList(-1, -2, -3);

    // When: Converting the collection
    List<String> result = objectConverter.convertCollection(source, String.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  // ==================== Error Cases ====================

  @Test
  void convertCollection_withNoConverterRegistered_shouldThrowException() {
    // Given: A DefaultObjectConverter with no converters
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.empty()
    );
    List<String> source = Collections.singletonList("42");

    // When & Then: Converting should throw IllegalArgumentException
    assertThatThrownBy(() -> objectConverter.convertCollection(source, Integer.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollection_withWrongTargetClass_shouldThrowException() {
    // Given: A converter registered for String->Integer but attempting String->Double
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<String> source = Collections.singletonList("42");

    // When & Then: Converting to wrong target class should throw
    assertThatThrownBy(() -> objectConverter.convertCollection(source, Double.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollection_withWrongSourceType_shouldThrowException() {
    // Given: A converter registered for String->Integer but providing Integer source
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Integer> source = Collections.singletonList(42);

    // When & Then: Converting with wrong source type should throw
    assertThatThrownBy(() -> objectConverter.convertCollection(source, Integer.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  // ==================== Large Collection Tests ====================

  @Test
  void convertCollection_withLargeCollection_shouldConvertAll() {
    // Given: A converter and large collection
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );

    // Create a list with 1000 elements
    List<String> source = new java.util.ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      source.add(String.valueOf(i));
    }

    // When: Converting the large collection
    List<Integer> result = objectConverter.convertCollection(source, Integer.class);

    // Then: Should convert all elements
    assertThat(result).hasSize(1000);
    assertThat(result.get(0)).isEqualTo(0);
    assertThat(result.get(999)).isEqualTo(999);
  }

  // ==================== Multiple Converters Tests ====================

  @Test
  void convertCollection_withMultipleConvertersRegistered_shouldUseCorrectOne() {
    // Given: Multiple converters registered
    StringToIntegerConverter stringToInt = new StringToIntegerConverter();
    IntegerToStringConverter intToString = new IntegerToStringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Arrays.asList(stringToInt, intToString),
        Optional.empty()
    );

    // When: Converting with String source
    List<String> stringSource = Arrays.asList("1", "2", "3");
    List<Integer> intResult = objectConverter.convertCollection(stringSource, Integer.class);

    // Then: Should use String->Integer converter
    assertThat(intResult).containsExactly(1, 2, 3);

    // When: Converting with Integer source
    List<Integer> intSource = Arrays.asList(10, 20, 30);
    List<String> stringResult = objectConverter.convertCollection(intSource, String.class);

    // Then: Should use Integer->String converter
    assertThat(stringResult).containsExactly("10", "20", "30");
  }

  // ==================== Custom Object Conversion Tests ====================

  @Test
  void convertCollection_withCustomObjects_shouldConvertSuccessfully() {
    // Given: Custom object converter
    PersonToPersonDtoConverter converter = new PersonToPersonDtoConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Person> source = Arrays.asList(
        new Person("Alice", 30),
        new Person("Bob", 25),
        new Person("Charlie", 35)
    );

    // When: Converting custom objects
    List<PersonDto> result = objectConverter.convertCollection(source, PersonDto.class);

    // Then: Should convert all custom objects
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getName()).isEqualTo("Alice");
    assertThat(result.get(0).getAge()).isEqualTo(30);
    assertThat(result.get(1).getName()).isEqualTo("Bob");
    assertThat(result.get(1).getAge()).isEqualTo(25);
    assertThat(result.get(2).getName()).isEqualTo("Charlie");
    assertThat(result.get(2).getAge()).isEqualTo(35);
  }

  // ==================== Tests for 3-parameter convertCollection (with Object context) ====================

  @Test
  void convertCollection3Param_withNullSource_shouldReturnEmptyList() {
    // Given: A BiConverter and null source
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );

    // When: Converting null collection
    List<Long> result = objectConverter.convertCollection(null, 100, Long.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollection3Param_withEmptySource_shouldReturnEmptyList() {
    // Given: A BiConverter and empty source
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );

    // When: Converting empty collection
    List<Long> result = objectConverter.convertCollection(Collections.emptyList(), 100, Long.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollection3Param_withSingleElement_shouldConvertWithContext() {
    // Given: A BiConverter and single-element list
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Collections.singletonList("10");
    Integer multiplier = 5;

    // When: Converting the collection
    List<Long> result = objectConverter.convertCollection(source, multiplier, Long.class);

    // Then: Should convert using the context (10 * 5 = 50)
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(50L);
  }

  @Test
  void convertCollection3Param_withMultipleElements_shouldConvertAllWithContext() {
    // Given: A BiConverter and multiple-element list
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Arrays.asList("2", "3", "4");
    Integer multiplier = 10;

    // When: Converting the collection
    List<Long> result = objectConverter.convertCollection(source, multiplier, Long.class);

    // Then: Should convert all elements using context
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly(20L, 30L, 40L);
  }

  @Test
  void convertCollection3Param_withDifferentContextValues_shouldUseCorrectContext() {
    // Given: A BiConverter
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Arrays.asList("5", "10");

    // When: Converting with different context values
    List<Long> result1 = objectConverter.convertCollection(source, 2, Long.class);
    List<Long> result2 = objectConverter.convertCollection(source, 3, Long.class);

    // Then: Should use the correct context for each conversion
    assertThat(result1).containsExactly(10L, 20L);
    assertThat(result2).containsExactly(15L, 30L);
  }

  @Test
  void convertCollection3Param_withNullContext_shouldHandleNullContext() {
    // Given: A BiConverter that handles null context
    NullContextBiConverter converter = new NullContextBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Integer> source = Arrays.asList(1, 2, 3);

    // When: Converting with null context
    List<String> result = objectConverter.convertCollection(source, null, String.class);

    // Then: Should handle null context (uses default value)
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly("1-0", "2-0", "3-0");
  }

  @Test
  void convertCollection3Param_withConverterReturningNull_shouldFilterOutNulls() {
    // Given: A BiConverter that returns null for negative results
    FilteringBiConverter converter = new FilteringBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Integer> source = Arrays.asList(5, 3, 10, 2);
    Integer threshold = 4;

    // When: Converting the collection (only values >= threshold pass through)
    List<String> result = objectConverter.convertCollection(source, threshold, String.class);

    // Then: Should filter out nulls (values < threshold)
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly("5", "10");
  }

  @Test
  void convertCollection3Param_withAllNullResults_shouldReturnEmptyList() {
    // Given: A BiConverter that returns null for all elements
    FilteringBiConverter converter = new FilteringBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Integer> source = Arrays.asList(1, 2, 3);
    Integer threshold = 10;

    // When: Converting the collection (all values < threshold)
    List<String> result = objectConverter.convertCollection(source, threshold, String.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollection3Param_withNoConverterRegistered_shouldThrowException() {
    // Given: A DefaultObjectConverter with no BiConverters
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.empty()
    );
    List<String> source = Collections.singletonList("42");

    // When & Then: Converting should throw IllegalArgumentException
    assertThatThrownBy(() -> objectConverter.convertCollection(source, 10, Long.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollection3Param_withWrongTargetClass_shouldThrowException() {
    // Given: A BiConverter registered for String->Long but attempting String->Double
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Collections.singletonList("42");

    // When & Then: Converting to wrong target class should throw
    assertThatThrownBy(() -> objectConverter.convertCollection(source, 10, Double.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollection3Param_withWrongSourceType_shouldThrowException() {
    // Given: A BiConverter registered for String->Long but providing Integer source
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Integer> source = Collections.singletonList(42);

    // When & Then: Converting with wrong source type should throw
    assertThatThrownBy(() -> objectConverter.convertCollection(source, 10, Long.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollection3Param_withLargeCollection_shouldConvertAll() {
    // Given: A BiConverter and large collection
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );

    // Create a list with 1000 elements
    List<String> source = new java.util.ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      source.add(String.valueOf(i));
    }
    Integer multiplier = 2;

    // When: Converting the large collection
    List<Long> result = objectConverter.convertCollection(source, multiplier, Long.class);

    // Then: Should convert all elements
    assertThat(result).hasSize(1000);
    assertThat(result.get(0)).isEqualTo(0L);
    assertThat(result.get(999)).isEqualTo(1998L);
  }

  @Test
  void convertCollection3Param_withMultipleBiConvertersRegistered_shouldUseCorrectOne() {
    // Given: Multiple BiConverters registered
    StringIntegerToLongBiConverter stringToLong = new StringIntegerToLongBiConverter();
    IntegerStringToDoubleBiConverter intToDouble = new IntegerStringToDoubleBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Arrays.asList(stringToLong, intToDouble))
    );

    // When: Converting with String source
    List<String> stringSource = Arrays.asList("5", "10");
    List<Long> longResult = objectConverter.convertCollection(stringSource, 3, Long.class);

    // Then: Should use String->Long BiConverter
    assertThat(longResult).containsExactly(15L, 30L);

    // When: Converting with Integer source
    List<Integer> intSource = Arrays.asList(10, 20);
    List<Double> doubleResult = objectConverter.convertCollection(intSource, "suffix", Double.class);

    // Then: Should use Integer->Double BiConverter
    assertThat(doubleResult).containsExactly(16.0, 26.0);
  }

  @Test
  void convertCollection3Param_withCustomObjects_shouldConvertSuccessfully() {
    // Given: Custom object BiConverter
    PersonPrefixToPersonDtoBiConverter converter = new PersonPrefixToPersonDtoBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Person> source = Arrays.asList(
        new Person("Alice", 30),
        new Person("Bob", 25)
    );
    String prefix = "Mr./Ms. ";

    // When: Converting custom objects with context
    List<PersonDto> result = objectConverter.convertCollection(source, prefix, PersonDto.class);

    // Then: Should convert all custom objects using context
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getName()).isEqualTo("Mr./Ms. Alice");
    assertThat(result.get(0).getAge()).isEqualTo(30);
    assertThat(result.get(1).getName()).isEqualTo("Mr./Ms. Bob");
    assertThat(result.get(1).getAge()).isEqualTo(25);
  }

  @Test
  void convertCollection3Param_maintainsOrder_shouldPreserveElementOrder() {
    // Given: A BiConverter with ordered input
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Arrays.asList("100", "200", "300", "400");

    // When: Converting the collection
    List<Long> result = objectConverter.convertCollection(source, 1, Long.class);

    // Then: Should maintain order
    assertThat(result).containsExactly(100L, 200L, 300L, 400L);
  }

  // ==================== Tests for convertCollection(List, Class, Class) with explicit source class ====================

  @Test
  void convertCollectionWithSourceClass_withNullSource_shouldReturnEmptyList() {
    // Given: A converter and null source
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );

    // When: Converting null collection
    List<Integer> result = objectConverter.convertCollection(null, String.class, Integer.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollectionWithSourceClass_withEmptySource_shouldReturnEmptyList() {
    // Given: A converter and empty source
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );

    // When: Converting empty collection
    List<Integer> result = objectConverter.convertCollection(Collections.emptyList(), String.class, Integer.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollectionWithSourceClass_withSingleElement_shouldConvertSuccessfully() {
    // Given: A converter and single-element list
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<String> source = Collections.singletonList("42");

    // When: Converting the collection with explicit source class
    List<Integer> result = objectConverter.convertCollection(source, String.class, Integer.class);

    // Then: Should convert successfully
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(42);
  }

  @Test
  void convertCollectionWithSourceClass_withMultipleElements_shouldConvertAll() {
    // Given: A converter and multiple-element list
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<String> source = Arrays.asList("10", "20", "30", "40");

    // When: Converting the collection with explicit source class
    List<Integer> result = objectConverter.convertCollection(source, String.class, Integer.class);

    // Then: Should convert all elements
    assertThat(result).hasSize(4);
    assertThat(result).containsExactly(10, 20, 30, 40);
  }

  @Test
  void convertCollectionWithSourceClass_maintainsOrder_shouldPreserveElementOrder() {
    // Given: A converter with ordered input
    IntegerToStringConverter converter = new IntegerToStringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Integer> source = Arrays.asList(5, 10, 15, 20);

    // When: Converting the collection
    List<String> result = objectConverter.convertCollection(source, Integer.class, String.class);

    // Then: Should maintain order
    assertThat(result).containsExactly("5", "10", "15", "20");
  }

  @Test
  void convertCollectionWithSourceClass_withSuperclassAsSourceClass_shouldConvertSuccessfully() {
    // Given: A converter for superclass (Number -> String)
    NumberToStringConverter converter = new NumberToStringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    // Create list with Integer (subclass of Number)
    List<Integer> source = Arrays.asList(100, 200, 300);

    // When: Converting with Number.class as sourceClass (superclass)
    List<String> result = objectConverter.convertCollection(source, Number.class, String.class);

    // Then: Should convert successfully since Integer is assignable to Number
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly("100", "200", "300");
  }

  @Test
  void convertCollectionWithSourceClass_withExactClassMatch_shouldConvertSuccessfully() {
    // Given: A converter with exact class match
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<String> source = Arrays.asList("1", "2", "3");

    // When: Converting with exact source class
    List<Integer> result = objectConverter.convertCollection(source, String.class, Integer.class);

    // Then: Should convert successfully
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly(1, 2, 3);
  }

  @Test
  void convertCollectionWithSourceClass_withConverterReturningNull_shouldFilterOutNulls() {
    // Given: A converter that returns null for negative numbers
    NullFilteringConverter converter = new NullFilteringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Integer> source = Arrays.asList(5, -1, 10, -5, 15);

    // When: Converting the collection
    List<String> result = objectConverter.convertCollection(source, Integer.class, String.class);

    // Then: Should filter out nulls (negative numbers)
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly("5", "10", "15");
  }

  @Test
  void convertCollectionWithSourceClass_withAllNullResults_shouldReturnEmptyList() {
    // Given: A converter that returns null for all elements
    NullFilteringConverter converter = new NullFilteringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Integer> source = Arrays.asList(-1, -2, -3);

    // When: Converting the collection
    List<String> result = objectConverter.convertCollection(source, Integer.class, String.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollectionWithSourceClass_withWrongSourceClass_shouldThrowException() {
    // Given: A converter and a source class that doesn't match the actual elements
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    // List contains Integer but we claim it's String
    List<Integer> source = Collections.singletonList(42);

    // When & Then: Converting should throw IllegalArgumentException
    assertThatThrownBy(() -> objectConverter.convertCollection(source, String.class, Integer.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void convertCollectionWithSourceClass_withSubclassAsSourceClass_shouldThrowException() {
    // Given: A converter for Number and Integer subclass
    NumberToStringConverter converter = new NumberToStringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    // List contains Number but we claim it's Integer (subclass)
    List<Number> source = Arrays.asList(100, 200);

    // When & Then: Should throw because Integer is not assignable from Number
    assertThatThrownBy(() -> objectConverter.convertCollection(source, Integer.class, String.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void convertCollectionWithSourceClass_withNoConverterForSourceClass_shouldThrowException() {
    // Given: A converter for String->Integer but no converter for Double->Integer
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Double> source = Collections.singletonList(42.5);

    // When & Then: Converting should throw IllegalArgumentException
    assertThatThrownBy(() -> objectConverter.convertCollection(source, Double.class, Integer.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollectionWithSourceClass_withNoConverterForTargetClass_shouldThrowException() {
    // Given: A converter for String->Integer but attempting String->Double
    StringToIntegerConverter converter = new StringToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<String> source = Collections.singletonList("42");

    // When & Then: Converting to wrong target class should throw
    assertThatThrownBy(() -> objectConverter.convertCollection(source, String.class, Double.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollectionWithSourceClass_withLargeCollection_shouldConvertAll() {
    // Given: A converter and large collection
    IntegerToStringConverter converter = new IntegerToStringConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );

    // Create a list with 1000 elements
    List<Integer> source = new java.util.ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      source.add(i);
    }

    // When: Converting the large collection
    List<String> result = objectConverter.convertCollection(source, Integer.class, String.class);

    // Then: Should convert all elements
    assertThat(result).hasSize(1000);
    assertThat(result.get(0)).isEqualTo("0");
    assertThat(result.get(999)).isEqualTo("999");
  }

  @Test
  void convertCollectionWithSourceClass_withMultipleConvertersRegistered_shouldUseCorrectOne() {
    // Given: Multiple converters registered
    StringToIntegerConverter stringToInt = new StringToIntegerConverter();
    IntegerToStringConverter intToString = new IntegerToStringConverter();
    DoubleToIntegerConverter doubleToInt = new DoubleToIntegerConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Arrays.asList(stringToInt, intToString, doubleToInt),
        Optional.empty()
    );

    // When: Converting with String source class
    List<String> stringSource = Arrays.asList("5", "10", "15");
    List<Integer> intResult = objectConverter.convertCollection(stringSource, String.class, Integer.class);

    // Then: Should use String->Integer converter
    assertThat(intResult).containsExactly(5, 10, 15);

    // When: Converting with Integer source class
    List<Integer> intSource = Arrays.asList(100, 200, 300);
    List<String> stringResult = objectConverter.convertCollection(intSource, Integer.class, String.class);

    // Then: Should use Integer->String converter
    assertThat(stringResult).containsExactly("100", "200", "300");

    // When: Converting with Double source class
    List<Double> doubleSource = Arrays.asList(1.5, 2.8, 3.1);
    List<Integer> doubleToIntResult = objectConverter.convertCollection(doubleSource, Double.class, Integer.class);

    // Then: Should use Double->Integer converter
    assertThat(doubleToIntResult).containsExactly(1, 2, 3);
  }

  @Test
  void convertCollectionWithSourceClass_withCustomObjects_shouldConvertSuccessfully() {
    // Given: Custom object converter
    PersonToPersonDtoConverter converter = new PersonToPersonDtoConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.singletonList(converter),
        Optional.empty()
    );
    List<Person> source = Arrays.asList(
        new Person("Alice", 30),
        new Person("Bob", 25),
        new Person("Charlie", 35)
    );

    // When: Converting custom objects with explicit source class
    List<PersonDto> result = objectConverter.convertCollection(source, Person.class, PersonDto.class);

    // Then: Should convert all custom objects
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getName()).isEqualTo("Alice");
    assertThat(result.get(0).getAge()).isEqualTo(30);
    assertThat(result.get(1).getName()).isEqualTo("Bob");
    assertThat(result.get(1).getAge()).isEqualTo(25);
    assertThat(result.get(2).getName()).isEqualTo("Charlie");
    assertThat(result.get(2).getAge()).isEqualTo(35);
  }

  // ==================== Tests for 4-parameter convertCollection(List, Object, Class, Class) ====================

  @Test
  void convertCollection4Param_withNullSource_shouldReturnEmptyList() {
    // Given: A BiConverter and null source
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );

    // When: Converting null collection
    List<Long> result = objectConverter.convertCollection(null, 10, String.class, Long.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollection4Param_withEmptySource_shouldReturnEmptyList() {
    // Given: A BiConverter and empty source
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );

    // When: Converting empty collection
    List<Long> result = objectConverter.convertCollection(Collections.emptyList(), 10, String.class, Long.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollection4Param_withSingleElement_shouldConvertWithContextAndSourceClass() {
    // Given: A BiConverter and single-element list
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Collections.singletonList("7");
    Integer multiplier = 3;

    // When: Converting the collection with context and explicit source class
    List<Long> result = objectConverter.convertCollection(source, multiplier, String.class, Long.class);

    // Then: Should convert successfully (7 * 3 = 21)
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(21L);
  }

  @Test
  void convertCollection4Param_withMultipleElements_shouldConvertAllWithContextAndSourceClass() {
    // Given: A BiConverter and multiple-element list
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Arrays.asList("5", "10", "15");
    Integer multiplier = 2;

    // When: Converting the collection
    List<Long> result = objectConverter.convertCollection(source, multiplier, String.class, Long.class);

    // Then: Should convert all elements (5*2=10, 10*2=20, 15*2=30)
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly(10L, 20L, 30L);
  }

  @Test
  void convertCollection4Param_withDifferentContextValues_shouldUseCorrectContext() {
    // Given: A BiConverter
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Arrays.asList("10", "20");

    // When: Converting with different context values
    List<Long> result1 = objectConverter.convertCollection(source, 5, String.class, Long.class);
    List<Long> result2 = objectConverter.convertCollection(source, 10, String.class, Long.class);

    // Then: Should use the correct context for each conversion
    assertThat(result1).containsExactly(50L, 100L);
    assertThat(result2).containsExactly(100L, 200L);
  }

  @Test
  void convertCollection4Param_maintainsOrder_shouldPreserveElementOrder() {
    // Given: A BiConverter with ordered input
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Arrays.asList("1", "2", "3", "4", "5");

    // When: Converting the collection
    List<Long> result = objectConverter.convertCollection(source, 1, String.class, Long.class);

    // Then: Should maintain order
    assertThat(result).containsExactly(1L, 2L, 3L, 4L, 5L);
  }

  @Test
  void convertCollection4Param_withNullContext_shouldHandleNullContext() {
    // Given: A BiConverter that handles null context
    NullContextBiConverter converter = new NullContextBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Integer> source = Arrays.asList(10, 20, 30);

    // When: Converting with null context and explicit source class
    List<String> result = objectConverter.convertCollection(source, null, Integer.class, String.class);

    // Then: Should handle null context (uses default value 0)
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly("10-0", "20-0", "30-0");
  }

  @Test
  void convertCollection4Param_withSuperclassAsSourceClass_shouldConvertSuccessfully() {
    // Given: A BiConverter for Number (superclass)
    NumberIntegerToBooleanBiConverter converter = new NumberIntegerToBooleanBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    // Create list with Integer (subclass of Number)
    List<Integer> source = Arrays.asList(5, 15, 25);
    Integer threshold = 10;

    // When: Converting with Number.class as sourceClass (superclass)
    List<Boolean> result = objectConverter.convertCollection(source, threshold, Number.class, Boolean.class);

    // Then: Should convert successfully since Integer is assignable to Number
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly(false, true, true);
  }

  @Test
  void convertCollection4Param_withConverterReturningNull_shouldFilterOutNulls() {
    // Given: A BiConverter that returns null for certain values
    FilteringBiConverter converter = new FilteringBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Integer> source = Arrays.asList(10, 5, 20, 3, 15);
    Integer threshold = 8;

    // When: Converting the collection (values < threshold return null)
    List<String> result = objectConverter.convertCollection(source, threshold, Integer.class, String.class);

    // Then: Should filter out nulls (values < 8)
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly("10", "20", "15");
  }

  @Test
  void convertCollection4Param_withAllNullResults_shouldReturnEmptyList() {
    // Given: A BiConverter that returns null for all elements
    FilteringBiConverter converter = new FilteringBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Integer> source = Arrays.asList(1, 2, 3, 4);
    Integer threshold = 10;

    // When: Converting the collection (all values < threshold)
    List<String> result = objectConverter.convertCollection(source, threshold, Integer.class, String.class);

    // Then: Should return empty list
    assertThat(result).isNotNull().isEmpty();
  }

  @Test
  void convertCollection4Param_withWrongSourceClass_shouldThrowException() {
    // Given: A BiConverter and a source class that doesn't match the actual elements
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    // List contains Integer but we claim it's String
    List<Integer> source = Collections.singletonList(42);

    // When & Then: Converting should throw IllegalArgumentException
    assertThatThrownBy(() -> objectConverter.convertCollection(source, 10, String.class, Long.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void convertCollection4Param_withSubclassAsSourceClass_shouldThrowException() {
    // Given: A BiConverter for Number
    NumberIntegerToBooleanBiConverter converter = new NumberIntegerToBooleanBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    // List contains Number but we claim it's Integer (subclass)
    List<Number> source = Arrays.asList(100, 200);

    // When & Then: Should throw because Integer is not assignable from Number
    assertThatThrownBy(() -> objectConverter.convertCollection(source, 10, Integer.class, Boolean.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void convertCollection4Param_withNoConverterRegistered_shouldThrowException() {
    // Given: A DefaultObjectConverter with no BiConverters
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.empty()
    );
    List<String> source = Collections.singletonList("42");

    // When & Then: Converting should throw IllegalArgumentException
    assertThatThrownBy(() -> objectConverter.convertCollection(source, 10, String.class, Long.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollection4Param_withNoConverterForSourceClass_shouldThrowException() {
    // Given: A BiConverter for String but attempting Double
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Double> source = Collections.singletonList(42.5);

    // When & Then: Converting should throw IllegalArgumentException
    assertThatThrownBy(() -> objectConverter.convertCollection(source, 10, Double.class, Long.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollection4Param_withNoConverterForTargetClass_shouldThrowException() {
    // Given: A BiConverter for String->Long but attempting String->Double
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Collections.singletonList("42");

    // When & Then: Converting to wrong target class should throw
    assertThatThrownBy(() -> objectConverter.convertCollection(source, 10, String.class, Double.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void convertCollection4Param_withLargeCollection_shouldConvertAll() {
    // Given: A BiConverter and large collection
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );

    // Create a list with 1000 elements
    List<String> source = new java.util.ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      source.add(String.valueOf(i));
    }
    Integer multiplier = 1;

    // When: Converting the large collection
    List<Long> result = objectConverter.convertCollection(source, multiplier, String.class, Long.class);

    // Then: Should convert all elements
    assertThat(result).hasSize(1000);
    assertThat(result.get(0)).isEqualTo(0L);
    assertThat(result.get(999)).isEqualTo(999L);
  }

  @Test
  void convertCollection4Param_withMultipleBiConvertersRegistered_shouldUseCorrectOne() {
    // Given: Multiple BiConverters registered
    StringIntegerToLongBiConverter stringToLong = new StringIntegerToLongBiConverter();
    IntegerStringToDoubleBiConverter intToDouble = new IntegerStringToDoubleBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Arrays.asList(stringToLong, intToDouble))
    );

    // When: Converting with String source class
    List<String> stringSource = Arrays.asList("10", "20");
    List<Long> longResult = objectConverter.convertCollection(stringSource, 2, String.class, Long.class);

    // Then: Should use String->Long BiConverter
    assertThat(longResult).containsExactly(20L, 40L);

    // When: Converting with Integer source class
    List<Integer> intSource = Arrays.asList(5, 10);
    List<Double> doubleResult = objectConverter.convertCollection(intSource, "test", Integer.class, Double.class);

    // Then: Should use Integer->Double BiConverter
    assertThat(doubleResult).containsExactly(9.0, 14.0);
  }

  @Test
  void convertCollection4Param_withCustomObjects_shouldConvertSuccessfully() {
    // Given: Custom object BiConverter
    PersonPrefixToPersonDtoBiConverter converter = new PersonPrefixToPersonDtoBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<Person> source = Arrays.asList(
        new Person("Alice", 30),
        new Person("Bob", 25),
        new Person("Charlie", 35)
    );
    String prefix = "Dr. ";

    // When: Converting custom objects with context and explicit source class
    List<PersonDto> result = objectConverter.convertCollection(source, prefix, Person.class, PersonDto.class);

    // Then: Should convert all custom objects using context
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getName()).isEqualTo("Dr. Alice");
    assertThat(result.get(0).getAge()).isEqualTo(30);
    assertThat(result.get(1).getName()).isEqualTo("Dr. Bob");
    assertThat(result.get(1).getAge()).isEqualTo(25);
    assertThat(result.get(2).getName()).isEqualTo("Dr. Charlie");
    assertThat(result.get(2).getAge()).isEqualTo(35);
  }

  @Test
  void convertCollection4Param_withExactClassMatch_shouldConvertSuccessfully() {
    // Given: A BiConverter with exact class match
    StringIntegerToLongBiConverter converter = new StringIntegerToLongBiConverter();
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(
        Collections.emptyList(),
        Optional.of(Collections.singletonList(converter))
    );
    List<String> source = Arrays.asList("3", "6", "9");

    // When: Converting with exact source class
    List<Long> result = objectConverter.convertCollection(source, 3, String.class, Long.class);

    // Then: Should convert successfully
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly(9L, 18L, 27L);
  }

  // ==================== Test Helper Classes ====================

  /**
   * Test converter: String to Integer
   */
  private static class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer apply(String source) {
      return Integer.parseInt(source);
    }
  }

  /**
   * Test converter: Integer to String
   */
  private static class IntegerToStringConverter implements Converter<Integer, String> {
    @Override
    public String apply(Integer source) {
      return source.toString();
    }
  }

  /**
   * Test converter: Double to Integer
   */
  private static class DoubleToIntegerConverter implements Converter<Double, Integer> {
    @Override
    public Integer apply(Double source) {
      return source.intValue();
    }
  }

  /**
   * Test converter: Integer to String, returns null for negative numbers
   */
  private static class NullFilteringConverter implements Converter<Integer, String> {
    @Override
    public String apply(Integer source) {
      if (source < 0) {
        return null;
      }
      return source.toString();
    }
  }

  /**
   * Test converter: Number to String
   * Supports any Number subclass (Integer, Double, etc.)
   */
  private static class NumberToStringConverter implements Converter<Number, String> {
    @Override
    public String apply(Number source) {
      return source.toString();
    }
  }

  /**
   * Test converter: Person to PersonDto
   */
  private static class PersonToPersonDtoConverter implements Converter<Person, PersonDto> {
    @Override
    public PersonDto apply(Person source) {
      return new PersonDto(source.getName(), source.getAge());
    }
  }

  /**
   * Test domain object
   */
  private static class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }
  }

  /**
   * Test DTO object
   */
  private static class PersonDto {
    private final String name;
    private final int age;

    public PersonDto(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }
  }

  // ==================== BiConverter Test Helper Classes ====================

  /**
   * Test BiConverter: String + Integer context to Long
   * Parses string as integer and multiplies by context
   */
  private static class StringIntegerToLongBiConverter implements BiConverter<String, Integer, Long> {
    @Override
    public Long apply(String source, Integer multiplier) {
      return Long.parseLong(source) * multiplier;
    }
  }

  /**
   * Test BiConverter: Integer + String context to Double
   * Adds the length of the context string to the integer
   */
  private static class IntegerStringToDoubleBiConverter implements BiConverter<Integer, String, Double> {
    @Override
    public Double apply(Integer source, String context) {
      return source.doubleValue() + context.length();
    }
  }

  /**
   * Test BiConverter: Integer + Integer context to String
   * Returns null if source < context, otherwise returns string representation
   */
  private static class FilteringBiConverter implements BiConverter<Integer, Integer, String> {
    @Override
    public String apply(Integer source, Integer threshold) {
      if (source < threshold) {
        return null;
      }
      return source.toString();
    }
  }

  /**
   * Test BiConverter: Integer + Integer context to String
   * Handles null context by using 0 as default
   */
  private static class NullContextBiConverter implements BiConverter<Integer, Integer, String> {
    @Override
    public String apply(Integer source, Integer context) {
      int contextValue = (context == null) ? 0 : context;
      return source + "-" + contextValue;
    }
  }

  /**
   * Test BiConverter: Person + String prefix to PersonDto
   * Adds prefix to person name
   */
  private static class PersonPrefixToPersonDtoBiConverter implements BiConverter<Person, String, PersonDto> {
    @Override
    public PersonDto apply(Person source, String prefix) {
      return new PersonDto(prefix + source.getName(), source.getAge());
    }
  }

  /**
   * Test BiConverter: Number + Integer threshold to Boolean
   * Returns true if number >= threshold, false otherwise
   */
  private static class NumberIntegerToBooleanBiConverter implements BiConverter<Number, Integer, Boolean> {
    @Override
    public Boolean apply(Number source, Integer threshold) {
      return source.intValue() >= threshold;
    }
  }
}
