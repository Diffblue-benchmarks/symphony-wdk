package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DefaultObjectConverter constructor.
 * Testing the behavior of the constructor that initializes converterMap and biConverterMap.
 */
class DefaultObjectConverterClaude_constructorTest {

  // ==================== Constructor with Empty Lists ====================

  @Test
  void constructor_withEmptyConverterList_shouldCreateInstanceWithoutErrors() {
    // Given: An empty list of converters and empty optional BiConverters
    List<Converter> emptyConverters = Collections.emptyList();
    Optional<List<BiConverter>> emptyBiConverters = Optional.empty();

    // When: Creating a DefaultObjectConverter with empty lists
    DefaultObjectConverter converter = new DefaultObjectConverter(emptyConverters, emptyBiConverters);

    // Then: Should create instance successfully and allow conversion attempts
    assertThat(converter).isNotNull();
  }

  @Test
  void constructor_withEmptyConverterListAndEmptyOptionalBiConverters_shouldAllowConversionAttempts() {
    // Given: An empty list of converters and empty optional BiConverters
    List<Converter> emptyConverters = Collections.emptyList();
    Optional<List<BiConverter>> emptyBiConverters = Optional.of(Collections.emptyList());

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(emptyConverters, emptyBiConverters);

    // Then: Instance should be created successfully
    assertThat(converter).isNotNull();
  }

  // ==================== Constructor with Single Converter ====================

  @Test
  void constructor_withSingleConverter_shouldRegisterAndAllowConversion() {
    // Given: A single converter
    StringToIntegerConverter stringToIntConverter = new StringToIntegerConverter();
    List<Converter> converters = Collections.singletonList(stringToIntConverter);
    Optional<List<BiConverter>> emptyBiConverters = Optional.empty();

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(converters, emptyBiConverters);

    // Then: Should be able to use the registered converter
    Integer result = converter.convert("42", Integer.class);
    assertThat(result).isEqualTo(42);
  }

  // ==================== Constructor with Multiple Converters ====================

  @Test
  void constructor_withMultipleConverters_shouldRegisterAllConverters() {
    // Given: Multiple converters
    StringToIntegerConverter stringToIntConverter = new StringToIntegerConverter();
    IntegerToStringConverter intToStringConverter = new IntegerToStringConverter();
    List<Converter> converters = Arrays.asList(stringToIntConverter, intToStringConverter);
    Optional<List<BiConverter>> emptyBiConverters = Optional.empty();

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(converters, emptyBiConverters);

    // Then: Should be able to use both converters
    Integer intResult = converter.convert("42", Integer.class);
    assertThat(intResult).isEqualTo(42);

    String stringResult = converter.convert(123, String.class);
    assertThat(stringResult).isEqualTo("123");
  }

  @Test
  void constructor_withMultipleConvertersOfDifferentTypes_shouldHandleAllConversions() {
    // Given: Converters for different type pairs
    StringToIntegerConverter stringToIntConverter = new StringToIntegerConverter();
    IntegerToDoubleConverter intToDoubleConverter = new IntegerToDoubleConverter();
    DoubleToStringConverter doubleToStringConverter = new DoubleToStringConverter();

    List<Converter> converters = Arrays.asList(
        stringToIntConverter,
        intToDoubleConverter,
        doubleToStringConverter
    );
    Optional<List<BiConverter>> emptyBiConverters = Optional.empty();

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(converters, emptyBiConverters);

    // Then: Should handle all conversion types
    Integer intResult = converter.convert("10", Integer.class);
    assertThat(intResult).isEqualTo(10);

    Double doubleResult = converter.convert(5, Double.class);
    assertThat(doubleResult).isEqualTo(5.0);

    String stringResult = converter.convert(3.14, String.class);
    assertThat(stringResult).isEqualTo("3.14");
  }

  // ==================== Constructor with BiConverters ====================

  @Test
  void constructor_withSingleBiConverter_shouldRegisterAndAllowConversion() {
    // Given: A BiConverter in the optional list
    StringAndContextToIntegerBiConverter biConverter = new StringAndContextToIntegerBiConverter();
    List<Converter> emptyConverters = Collections.emptyList();
    Optional<List<BiConverter>> biConverters = Optional.of(Collections.singletonList(biConverter));

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(emptyConverters, biConverters);

    // Then: Should be able to use the registered BiConverter
    ConversionContext context = new ConversionContext(10);
    Integer result = converter.convert("5", context, Integer.class);
    assertThat(result).isEqualTo(15); // 5 + context.offset (10)
  }

  @Test
  void constructor_withMultipleBiConverters_shouldRegisterAllBiConverters() {
    // Given: Multiple BiConverters
    StringAndContextToIntegerBiConverter biConverter1 = new StringAndContextToIntegerBiConverter();
    IntegerAndContextToStringBiConverter biConverter2 = new IntegerAndContextToStringBiConverter();

    List<Converter> emptyConverters = Collections.emptyList();
    Optional<List<BiConverter>> biConverters = Optional.of(Arrays.asList(biConverter1, biConverter2));

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(emptyConverters, biConverters);

    // Then: Should be able to use both BiConverters
    ConversionContext context = new ConversionContext(10);
    Integer intResult = converter.convert("5", context, Integer.class);
    assertThat(intResult).isEqualTo(15);

    String stringResult = converter.convert(42, context, String.class);
    assertThat(stringResult).isEqualTo("42-offset:10");
  }

  // ==================== Constructor with Both Converters and BiConverters ====================

  @Test
  void constructor_withBothConvertersAndBiConverters_shouldRegisterBoth() {
    // Given: Both regular Converters and BiConverters
    StringToIntegerConverter regularConverter = new StringToIntegerConverter();
    StringAndContextToIntegerBiConverter biConverter = new StringAndContextToIntegerBiConverter();

    List<Converter> converters = Collections.singletonList(regularConverter);
    Optional<List<BiConverter>> biConverters = Optional.of(Collections.singletonList(biConverter));

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(converters, biConverters);

    // Then: Should be able to use both types
    // Regular converter
    Integer result1 = converter.convert("42", Integer.class);
    assertThat(result1).isEqualTo(42);

    // BiConverter
    ConversionContext context = new ConversionContext(5);
    Integer result2 = converter.convert("10", context, Integer.class);
    assertThat(result2).isEqualTo(15); // 10 + 5
  }

  @Test
  void constructor_withMixedConvertersAndBiConverters_shouldHandleComplexScenario() {
    // Given: Multiple converters of both types
    StringToIntegerConverter stringToInt = new StringToIntegerConverter();
    IntegerToStringConverter intToString = new IntegerToStringConverter();
    StringAndContextToIntegerBiConverter stringBiConverter = new StringAndContextToIntegerBiConverter();
    IntegerAndContextToStringBiConverter intBiConverter = new IntegerAndContextToStringBiConverter();

    List<Converter> converters = Arrays.asList(stringToInt, intToString);
    Optional<List<BiConverter>> biConverters = Optional.of(Arrays.asList(stringBiConverter, intBiConverter));

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(converters, biConverters);

    // Then: Should handle all conversion types correctly
    // Regular converters
    Integer intResult = converter.convert("100", Integer.class);
    assertThat(intResult).isEqualTo(100);

    String stringResult = converter.convert(200, String.class);
    assertThat(stringResult).isEqualTo("200");

    // BiConverters
    ConversionContext context = new ConversionContext(7);
    Integer biIntResult = converter.convert("8", context, Integer.class);
    assertThat(biIntResult).isEqualTo(15); // 8 + 7

    String biStringResult = converter.convert(25, context, String.class);
    assertThat(biStringResult).isEqualTo("25-offset:7");
  }

  // ==================== Constructor with Empty Optional ====================

  @Test
  void constructor_withEmptyOptional_shouldOnlyRegisterRegularConverters() {
    // Given: Regular converters and an empty Optional
    StringToIntegerConverter regularConverter = new StringToIntegerConverter();
    List<Converter> converters = Collections.singletonList(regularConverter);
    Optional<List<BiConverter>> emptyOptional = Optional.empty();

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(converters, emptyOptional);

    // Then: Should work with regular converters
    Integer result = converter.convert("99", Integer.class);
    assertThat(result).isEqualTo(99);
  }

  // ==================== Constructor with Collection Conversion ====================

  @Test
  void constructor_withConverter_shouldSupportCollectionConversion() {
    // Given: A converter that supports collection conversion
    StringToIntegerConverter stringToInt = new StringToIntegerConverter();
    List<Converter> converters = Collections.singletonList(stringToInt);
    Optional<List<BiConverter>> emptyBiConverters = Optional.empty();

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(converters, emptyBiConverters);

    // Then: Should support collection conversion
    List<String> sourceList = Arrays.asList("1", "2", "3");
    List<Integer> resultList = converter.convertCollection(sourceList, Integer.class);

    assertThat(resultList).hasSize(3);
    assertThat(resultList).containsExactly(1, 2, 3);
  }

  @Test
  void constructor_withBiConverter_shouldSupportBiCollectionConversion() {
    // Given: A BiConverter
    StringAndContextToIntegerBiConverter biConverter = new StringAndContextToIntegerBiConverter();
    List<Converter> emptyConverters = Collections.emptyList();
    Optional<List<BiConverter>> biConverters = Optional.of(Collections.singletonList(biConverter));

    // When: Creating a DefaultObjectConverter
    DefaultObjectConverter converter = new DefaultObjectConverter(emptyConverters, biConverters);

    // Then: Should support BiConverter collection conversion
    ConversionContext context = new ConversionContext(100);
    List<String> sourceList = Arrays.asList("1", "2", "3");
    List<Integer> resultList = converter.convertCollection(sourceList, context, Integer.class);

    assertThat(resultList).hasSize(3);
    assertThat(resultList).containsExactly(101, 102, 103);
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
   * Test converter: Integer to Double
   */
  private static class IntegerToDoubleConverter implements Converter<Integer, Double> {
    @Override
    public Double apply(Integer source) {
      return source.doubleValue();
    }
  }

  /**
   * Test converter: Double to String
   */
  private static class DoubleToStringConverter implements Converter<Double, String> {
    @Override
    public String apply(Double source) {
      return source.toString();
    }
  }

  /**
   * Test BiConverter: String + Context to Integer
   */
  private static class StringAndContextToIntegerBiConverter implements BiConverter<String, ConversionContext, Integer> {
    @Override
    public Integer apply(String source, ConversionContext context) {
      return Integer.parseInt(source) + context.getOffset();
    }
  }

  /**
   * Test BiConverter: Integer + Context to String
   */
  private static class IntegerAndContextToStringBiConverter implements BiConverter<Integer, ConversionContext, String> {
    @Override
    public String apply(Integer source, ConversionContext context) {
      return source + "-offset:" + context.getOffset();
    }
  }

  /**
   * Test context class for BiConverter tests
   */
  private static class ConversionContext {
    private final int offset;

    public ConversionContext(int offset) {
      this.offset = offset;
    }

    public int getOffset() {
      return offset;
    }
  }
}
