package com.symphony.bdk.workflow.engine.camunda.variable;

import org.camunda.bpm.impl.juel.TypeConverterImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VariableToJsonConverterClaudeTest {

  // Test enum for enum conversion tests
  private enum TestEnum {
    VALUE_ONE,
    VALUE_TWO,
    ANOTHER_VALUE
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstance() {
    // When: Constructor is called
    VariableToJsonConverter converter = new VariableToJsonConverter();

    // Then: Instance should be created successfully
    assertThat(converter).isNotNull();
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new VariableToJsonConverter())
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldExtendTypeConverterImpl() {
    // When: Constructor is called
    VariableToJsonConverter converter = new VariableToJsonConverter();

    // Then: Instance should extend TypeConverterImpl
    assertThat(converter).isInstanceOf(TypeConverterImpl.class);
  }

  @Test
  void constructor_calledMultipleTimes_shouldCreateDistinctInstances() {
    // When: Constructor is called multiple times
    VariableToJsonConverter converter1 = new VariableToJsonConverter();
    VariableToJsonConverter converter2 = new VariableToJsonConverter();

    // Then: Each call should create a distinct instance
    assertThat(converter1).isNotNull();
    assertThat(converter2).isNotNull();
    assertThat(converter1).isNotSameAs(converter2);
  }

  // Tests for coerceToString method

  @Test
  void coerceToString_withNullValue_shouldReturnEmptyString() {
    // Given: A converter
    VariableToJsonConverter converter = new VariableToJsonConverter();

    // When: coerceToString is called with null
    String result = converter.coerceToString(null);

    // Then: Should return empty string
    assertThat(result).isEqualTo("");
  }

  @Test
  void coerceToString_withStringValue_shouldReturnSameString() {
    // Given: A converter and a string value
    VariableToJsonConverter converter = new VariableToJsonConverter();
    String input = "test string";

    // When: coerceToString is called with a string
    String result = converter.coerceToString(input);

    // Then: Should return the same string
    assertThat(result).isEqualTo("test string");
  }

  @Test
  void coerceToString_withEmptyString_shouldReturnEmptyString() {
    // Given: A converter
    VariableToJsonConverter converter = new VariableToJsonConverter();

    // When: coerceToString is called with empty string
    String result = converter.coerceToString("");

    // Then: Should return empty string
    assertThat(result).isEqualTo("");
  }

  @Test
  void coerceToString_withStringContainingSpecialCharacters_shouldReturnSameString() {
    // Given: A converter and a string with special characters
    VariableToJsonConverter converter = new VariableToJsonConverter();
    String input = "Special: @#$%^&*(){}[]|\\<>?/~`";

    // When: coerceToString is called
    String result = converter.coerceToString(input);

    // Then: Should return the same string
    assertThat(result).isEqualTo(input);
  }

  @Test
  void coerceToString_withEnum_shouldReturnEnumName() {
    // Given: A converter and an enum value
    VariableToJsonConverter converter = new VariableToJsonConverter();

    // When: coerceToString is called with an enum
    String result = converter.coerceToString(TestEnum.VALUE_ONE);

    // Then: Should return the enum name
    assertThat(result).isEqualTo("VALUE_ONE");
  }

  @Test
  void coerceToString_withDifferentEnumValues_shouldReturnCorrectNames() {
    // Given: A converter
    VariableToJsonConverter converter = new VariableToJsonConverter();

    // When: coerceToString is called with different enum values
    String result1 = converter.coerceToString(TestEnum.VALUE_ONE);
    String result2 = converter.coerceToString(TestEnum.VALUE_TWO);
    String result3 = converter.coerceToString(TestEnum.ANOTHER_VALUE);

    // Then: Should return the correct enum names
    assertThat(result1).isEqualTo("VALUE_ONE");
    assertThat(result2).isEqualTo("VALUE_TWO");
    assertThat(result3).isEqualTo("ANOTHER_VALUE");
  }

  @Test
  void coerceToString_withInteger_shouldReturnEscapedJson() {
    // Given: A converter and an integer
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Integer value = 42;

    // When: coerceToString is called
    String result = converter.coerceToString(value);

    // Then: Should return the integer as a string (JSON serialized)
    assertThat(result).isEqualTo("42");
  }

  @Test
  void coerceToString_withBoolean_shouldReturnEscapedJson() {
    // Given: A converter and boolean values
    VariableToJsonConverter converter = new VariableToJsonConverter();

    // When: coerceToString is called with true
    String resultTrue = converter.coerceToString(true);

    // When: coerceToString is called with false
    String resultFalse = converter.coerceToString(false);

    // Then: Should return boolean values as strings
    assertThat(resultTrue).isEqualTo("true");
    assertThat(resultFalse).isEqualTo("false");
  }

  @Test
  void coerceToString_withDouble_shouldReturnEscapedJson() {
    // Given: A converter and a double value
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Double value = 3.14159;

    // When: coerceToString is called
    String result = converter.coerceToString(value);

    // Then: Should return the double as a string
    assertThat(result).isEqualTo("3.14159");
  }

  @Test
  void coerceToString_withList_shouldReturnEscapedJson() {
    // Given: A converter and a list
    VariableToJsonConverter converter = new VariableToJsonConverter();
    List<String> list = Arrays.asList("item1", "item2", "item3");

    // When: coerceToString is called
    String result = converter.coerceToString(list);

    // Then: Should return escaped JSON array
    // The JSON array [\"item1\",\"item2\",\"item3\"] is escaped
    assertThat(result).isEqualTo("[\\\"item1\\\",\\\"item2\\\",\\\"item3\\\"]");
  }

  @Test
  void coerceToString_withEmptyList_shouldReturnEscapedEmptyArray() {
    // Given: A converter and an empty list
    VariableToJsonConverter converter = new VariableToJsonConverter();
    List<String> list = new ArrayList<>();

    // When: coerceToString is called
    String result = converter.coerceToString(list);

    // Then: Should return escaped empty JSON array
    assertThat(result).isEqualTo("[]");
  }

  @Test
  void coerceToString_withMap_shouldReturnEscapedJson() {
    // Given: A converter and a map
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", "value2");

    // When: coerceToString is called
    String result = converter.coerceToString(map);

    // Then: Should return escaped JSON object
    // The result should be an escaped JSON object containing both key-value pairs
    assertThat(result).contains("\\\"key1\\\":\\\"value1\\\"");
    assertThat(result).contains("\\\"key2\\\":\\\"value2\\\"");
    assertThat(result).startsWith("{");
    assertThat(result).endsWith("}");
  }

  @Test
  void coerceToString_withEmptyMap_shouldReturnEscapedEmptyObject() {
    // Given: A converter and an empty map
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Map<String, String> map = new HashMap<>();

    // When: coerceToString is called
    String result = converter.coerceToString(map);

    // Then: Should return escaped empty JSON object
    assertThat(result).isEqualTo("{}");
  }

  @Test
  void coerceToString_withNestedMap_shouldReturnEscapedJson() {
    // Given: A converter and a nested map
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Map<String, Object> innerMap = new HashMap<>();
    innerMap.put("innerKey", "innerValue");

    Map<String, Object> outerMap = new HashMap<>();
    outerMap.put("outerKey", innerMap);

    // When: coerceToString is called
    String result = converter.coerceToString(outerMap);

    // Then: Should return escaped JSON with nested structure
    assertThat(result).contains("\\\"outerKey\\\"");
    assertThat(result).contains("\\\"innerKey\\\"");
    assertThat(result).contains("\\\"innerValue\\\"");
  }

  @Test
  void coerceToString_withListOfMaps_shouldReturnEscapedJson() {
    // Given: A converter and a list of maps
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Map<String, String> map1 = new HashMap<>();
    map1.put("key1", "value1");

    Map<String, String> map2 = new HashMap<>();
    map2.put("key2", "value2");

    List<Map<String, String>> list = Arrays.asList(map1, map2);

    // When: coerceToString is called
    String result = converter.coerceToString(list);

    // Then: Should return escaped JSON array of objects
    assertThat(result).startsWith("[");
    assertThat(result).endsWith("]");
    assertThat(result).contains("\\\"key1\\\":\\\"value1\\\"");
    assertThat(result).contains("\\\"key2\\\":\\\"value2\\\"");
  }

  @Test
  void coerceToString_withComplexNestedStructure_shouldReturnEscapedJson() {
    // Given: A converter and a complex nested structure
    VariableToJsonConverter converter = new VariableToJsonConverter();

    List<Integer> numbers = Arrays.asList(1, 2, 3);
    Map<String, Object> data = new HashMap<>();
    data.put("name", "test");
    data.put("count", 42);
    data.put("active", true);
    data.put("numbers", numbers);

    // When: coerceToString is called
    String result = converter.coerceToString(data);

    // Then: Should return escaped JSON with all nested elements
    assertThat(result).contains("\\\"name\\\":\\\"test\\\"");
    assertThat(result).contains("\\\"count\\\":42");
    assertThat(result).contains("\\\"active\\\":true");
    assertThat(result).contains("\\\"numbers\\\":[1,2,3]");
  }

  @Test
  void coerceToString_withStringContainingQuotes_shouldReturnSameString() {
    // Given: A converter and a string with quotes
    VariableToJsonConverter converter = new VariableToJsonConverter();
    String input = "String with \"quotes\" inside";

    // When: coerceToString is called
    String result = converter.coerceToString(input);

    // Then: Should return the same string (not escaped, as it's already a String)
    assertThat(result).isEqualTo(input);
  }

  @Test
  void coerceToString_withListContainingNull_shouldHandleNullInJson() {
    // Given: A converter and a list containing null
    VariableToJsonConverter converter = new VariableToJsonConverter();
    List<String> list = new ArrayList<>();
    list.add("value1");
    list.add(null);
    list.add("value2");

    // When: coerceToString is called
    String result = converter.coerceToString(list);

    // Then: Should return escaped JSON with null
    assertThat(result).contains("\\\"value1\\\"");
    assertThat(result).contains("null");
    assertThat(result).contains("\\\"value2\\\"");
  }

  @Test
  void coerceToString_withMapContainingNullValue_shouldHandleNullInJson() {
    // Given: A converter and a map with null value
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Map<String, String> map = new HashMap<>();
    map.put("key1", "value1");
    map.put("key2", null);

    // When: coerceToString is called
    String result = converter.coerceToString(map);

    // Then: Should return escaped JSON with null value
    assertThat(result).contains("\\\"key1\\\":\\\"value1\\\"");
    assertThat(result).contains("\\\"key2\\\":null");
  }

  @Test
  void coerceToString_calledMultipleTimes_shouldReturnConsistentResults() {
    // Given: A converter and the same input
    VariableToJsonConverter converter = new VariableToJsonConverter();
    List<String> list = Arrays.asList("a", "b", "c");

    // When: coerceToString is called multiple times
    String result1 = converter.coerceToString(list);
    String result2 = converter.coerceToString(list);
    String result3 = converter.coerceToString(list);

    // Then: Should return the same result each time
    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);
  }

  @Test
  void coerceToString_withDifferentConverterInstances_shouldProduceSameResult() {
    // Given: Multiple converter instances and the same input
    VariableToJsonConverter converter1 = new VariableToJsonConverter();
    VariableToJsonConverter converter2 = new VariableToJsonConverter();
    Map<String, Integer> map = new HashMap<>();
    map.put("count", 10);

    // When: coerceToString is called on different instances
    String result1 = converter1.coerceToString(map);
    String result2 = converter2.coerceToString(map);

    // Then: Should produce the same result
    assertThat(result1).isEqualTo(result2);
  }

  @Test
  void coerceToString_withLong_shouldReturnEscapedJson() {
    // Given: A converter and a long value
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Long value = 9876543210L;

    // When: coerceToString is called
    String result = converter.coerceToString(value);

    // Then: Should return the long as a string
    assertThat(result).isEqualTo("9876543210");
  }

  @Test
  void coerceToString_withFloat_shouldReturnEscapedJson() {
    // Given: A converter and a float value
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Float value = 2.5f;

    // When: coerceToString is called
    String result = converter.coerceToString(value);

    // Then: Should return the float as a string
    assertThat(result).isEqualTo("2.5");
  }

  @Test
  void coerceToString_withCharacter_shouldReturnEscapedJson() {
    // Given: A converter and a character
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Character value = 'A';

    // When: coerceToString is called
    String result = converter.coerceToString(value);

    // Then: Should return the character as escaped JSON string
    assertThat(result).isEqualTo("\\\"A\\\"");
  }

  @Test
  void coerceToString_withByte_shouldReturnEscapedJson() {
    // Given: A converter and a byte value
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Byte value = (byte) 127;

    // When: coerceToString is called
    String result = converter.coerceToString(value);

    // Then: Should return the byte as a string
    assertThat(result).isEqualTo("127");
  }

  @Test
  void coerceToString_withShort_shouldReturnEscapedJson() {
    // Given: A converter and a short value
    VariableToJsonConverter converter = new VariableToJsonConverter();
    Short value = (short) 1000;

    // When: coerceToString is called
    String result = converter.coerceToString(value);

    // Then: Should return the short as a string
    assertThat(result).isEqualTo("1000");
  }

  @Test
  void coerceToString_withCustomObject_shouldSerializeToEscapedJson() {
    // Given: A converter and a simple custom object
    VariableToJsonConverter converter = new VariableToJsonConverter();
    SimpleTestObject obj = new SimpleTestObject("testName", 123);

    // When: coerceToString is called
    String result = converter.coerceToString(obj);

    // Then: Should return escaped JSON representation of the object
    assertThat(result).contains("\\\"name\\\":\\\"testName\\\"");
    assertThat(result).contains("\\\"value\\\":123");
  }

  @Test
  void coerceToString_withSpecialCharactersInString_shouldNotEscapeForStringType() {
    // Given: A converter and a string with special JSON characters
    VariableToJsonConverter converter = new VariableToJsonConverter();
    String input = "Line1\nLine2\tTabbed\"Quoted\"";

    // When: coerceToString is called
    String result = converter.coerceToString(input);

    // Then: Should return the string as-is (no escaping because input is already a String)
    assertThat(result).isEqualTo(input);
  }

  @Test
  void coerceToString_withZeroValues_shouldReturnCorrectJson() {
    // Given: A converter
    VariableToJsonConverter converter = new VariableToJsonConverter();

    // When: coerceToString is called with various zero values
    String intResult = converter.coerceToString(0);
    String longResult = converter.coerceToString(0L);
    String doubleResult = converter.coerceToString(0.0);
    String floatResult = converter.coerceToString(0.0f);

    // Then: Should return correct JSON representations
    assertThat(intResult).isEqualTo("0");
    assertThat(longResult).isEqualTo("0");
    assertThat(doubleResult).isEqualTo("0.0");
    assertThat(floatResult).isEqualTo("0.0");
  }

  // Helper class for testing custom object serialization
  private static class SimpleTestObject {
    private String name;
    private int value;

    public SimpleTestObject(String name, int value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public int getValue() {
      return value;
    }
  }
}
