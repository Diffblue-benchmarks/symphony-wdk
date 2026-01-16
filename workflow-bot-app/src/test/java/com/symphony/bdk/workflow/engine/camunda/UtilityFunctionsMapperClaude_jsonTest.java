package com.symphony.bdk.workflow.engine.camunda;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UtilityFunctionsMapperClaude_jsonTest {

  @Test
  void json_withValidJsonObject_shouldReturnParsedMap() {
    // Given: A valid JSON object string
    String jsonString = "{\"name\":\"John\",\"age\":30}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return a Map with the parsed data
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map.get("name")).isEqualTo("John");
    assertThat(map.get("age")).isEqualTo(30);
  }

  @Test
  void json_withValidJsonArray_shouldReturnParsedList() {
    // Given: A valid JSON array string
    String jsonString = "[1,2,3,4,5]";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return a List with the parsed data
    assertThat(result).isInstanceOf(List.class);
    @SuppressWarnings("unchecked")
    List<Object> list = (List<Object>) result;
    assertThat(list).containsExactly(1, 2, 3, 4, 5);
  }

  @Test
  void json_withValidJsonString_shouldReturnString() {
    // Given: A valid JSON string value
    String jsonString = "\"hello world\"";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return the string value
    assertThat(result).isEqualTo("hello world");
  }

  @Test
  void json_withValidJsonNumber_shouldReturnNumber() {
    // Given: A valid JSON number
    String jsonString = "42";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return the number
    assertThat(result).isEqualTo(42);
  }

  @Test
  void json_withValidJsonBoolean_shouldReturnBoolean() {
    // Given: A valid JSON boolean
    String jsonString = "true";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return the boolean value
    assertThat(result).isEqualTo(true);
  }

  @Test
  void json_withValidJsonNull_shouldReturnNull() {
    // Given: A valid JSON null value
    String jsonString = "null";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void json_withInvalidJson_shouldReturnOriginalString() {
    // Given: An invalid JSON string
    String invalidJson = "not valid json";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(invalidJson);

    // Then: Should return the original string
    assertThat(result).isEqualTo(invalidJson);
  }

  @Test
  void json_withPartiallyValidJson_shouldReturnOriginalString() {
    // Given: A partially valid JSON string (missing closing brace)
    String invalidJson = "{\"name\":\"John\"";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(invalidJson);

    // Then: Should return the original string
    assertThat(result).isEqualTo(invalidJson);
  }

  @Test
  void json_withEmptyString_shouldReturnOriginalString() {
    // Given: An empty string
    String emptyString = "";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(emptyString);

    // Then: Should return the original empty string
    assertThat(result).isEqualTo(emptyString);
  }

  @Test
  void json_withNestedJsonObject_shouldReturnNestedMap() {
    // Given: A nested JSON object
    String jsonString = "{\"person\":{\"name\":\"Alice\",\"address\":{\"city\":\"NYC\",\"zip\":10001}}}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return a nested Map structure
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map).containsKey("person");

    @SuppressWarnings("unchecked")
    Map<String, Object> person = (Map<String, Object>) map.get("person");
    assertThat(person.get("name")).isEqualTo("Alice");

    @SuppressWarnings("unchecked")
    Map<String, Object> address = (Map<String, Object>) person.get("address");
    assertThat(address.get("city")).isEqualTo("NYC");
    assertThat(address.get("zip")).isEqualTo(10001);
  }

  @Test
  void json_withJsonArrayOfObjects_shouldReturnListOfMaps() {
    // Given: A JSON array of objects
    String jsonString = "[{\"id\":1,\"name\":\"A\"},{\"id\":2,\"name\":\"B\"}]";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return a List of Maps
    assertThat(result).isInstanceOf(List.class);
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> list = (List<Map<String, Object>>) result;
    assertThat(list).hasSize(2);
    assertThat(list.get(0).get("id")).isEqualTo(1);
    assertThat(list.get(0).get("name")).isEqualTo("A");
    assertThat(list.get(1).get("id")).isEqualTo(2);
    assertThat(list.get(1).get("name")).isEqualTo("B");
  }

  @Test
  void json_withJsonObjectContainingArray_shouldReturnMapWithList() {
    // Given: A JSON object containing an array
    String jsonString = "{\"items\":[\"apple\",\"banana\",\"cherry\"]}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return a Map containing a List
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;

    @SuppressWarnings("unchecked")
    List<String> items = (List<String>) map.get("items");
    assertThat(items).containsExactly("apple", "banana", "cherry");
  }

  @Test
  void json_withWhitespaceOnlyString_shouldReturnOriginalString() {
    // Given: A string with only whitespace
    String whitespace = "   \n\t  ";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(whitespace);

    // Then: Should return the original string (invalid JSON)
    assertThat(result).isEqualTo(whitespace);
  }

  @Test
  void json_withJsonContainingSpecialCharacters_shouldParsCorrectly() {
    // Given: JSON with special characters and escapes
    String jsonString = "{\"message\":\"Hello\\nWorld\",\"emoji\":\"\\u263A\"}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should parse and handle escapes correctly
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map.get("message")).isEqualTo("Hello\nWorld");
    assertThat(map.get("emoji")).isEqualTo("☺");
  }

  @Test
  void json_withJsonContainingNull_shouldIncludeNullValue() {
    // Given: JSON object with null value
    String jsonString = "{\"name\":\"Bob\",\"middleName\":null,\"age\":25}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should parse with null value preserved
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map.get("name")).isEqualTo("Bob");
    assertThat(map.get("middleName")).isNull();
    assertThat(map.get("age")).isEqualTo(25);
    assertThat(map).containsKey("middleName");
  }

  @Test
  void json_withEmptyJsonObject_shouldReturnEmptyMap() {
    // Given: An empty JSON object
    String jsonString = "{}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return an empty Map
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map).isEmpty();
  }

  @Test
  void json_withEmptyJsonArray_shouldReturnEmptyList() {
    // Given: An empty JSON array
    String jsonString = "[]";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should return an empty List
    assertThat(result).isInstanceOf(List.class);
    @SuppressWarnings("unchecked")
    List<Object> list = (List<Object>) result;
    assertThat(list).isEmpty();
  }

  @Test
  void json_withJsonContainingMixedTypes_shouldParseAllTypes() {
    // Given: JSON with mixed data types
    String jsonString = "{\"string\":\"text\",\"number\":42,\"float\":3.14,\"bool\":true,\"null\":null,\"array\":[1,2,3]}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should parse all types correctly
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map.get("string")).isEqualTo("text");
    assertThat(map.get("number")).isEqualTo(42);
    assertThat(map.get("float")).isEqualTo(3.14);
    assertThat(map.get("bool")).isEqualTo(true);
    assertThat(map.get("null")).isNull();

    @SuppressWarnings("unchecked")
    List<Integer> array = (List<Integer>) map.get("array");
    assertThat(array).containsExactly(1, 2, 3);
  }

  @Test
  void json_withMalformedJsonMissingQuotes_shouldReturnOriginalString() {
    // Given: Malformed JSON without quotes around keys
    String malformedJson = "{name:John,age:30}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(malformedJson);

    // Then: Should return the original string
    assertThat(result).isEqualTo(malformedJson);
  }

  @Test
  void json_withSingleQuotedJson_shouldReturnOriginalString() {
    // Given: JSON with single quotes (invalid in strict JSON)
    String singleQuotedJson = "{'name':'John'}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(singleQuotedJson);

    // Then: Should return the original string (Jackson defaults to strict parsing)
    assertThat(result).isEqualTo(singleQuotedJson);
  }

  @Test
  void json_withJsonContainingLargeNumbers_shouldParseCorrectly() {
    // Given: JSON with large numbers
    String jsonString = "{\"bigInt\":9223372036854775807,\"smallInt\":-9223372036854775808}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should parse large numbers correctly
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map.get("bigInt")).isEqualTo(9223372036854775807L);
    assertThat(map.get("smallInt")).isEqualTo(-9223372036854775808L);
  }

  @Test
  void json_calledMultipleTimes_shouldParseConsistently() {
    // Given: The same JSON string
    String jsonString = "{\"key\":\"value\"}";

    // When: json() is called multiple times
    Object result1 = UtilityFunctionsMapper.json(jsonString);
    Object result2 = UtilityFunctionsMapper.json(jsonString);
    Object result3 = UtilityFunctionsMapper.json(jsonString);

    // Then: All results should be equal (though not necessarily same instance)
    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);

    @SuppressWarnings("unchecked")
    Map<String, Object> map1 = (Map<String, Object>) result1;
    @SuppressWarnings("unchecked")
    Map<String, Object> map2 = (Map<String, Object>) result2;

    assertThat(map1.get("key")).isEqualTo(map2.get("key"));
  }

  @Test
  void json_withJsonContainingUnicodeCharacters_shouldParseCorrectly() {
    // Given: JSON with Unicode characters
    String jsonString = "{\"greeting\":\"Hello 世界\",\"emoji\":\"🎉\"}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should parse Unicode correctly
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map.get("greeting")).isEqualTo("Hello 世界");
    assertThat(map.get("emoji")).isEqualTo("🎉");
  }

  @Test
  void json_withTrailingComma_shouldReturnOriginalString() {
    // Given: JSON with trailing comma (invalid)
    String invalidJson = "{\"name\":\"John\",}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(invalidJson);

    // Then: Should return the original string
    assertThat(result).isEqualTo(invalidJson);
  }

  @Test
  void json_withJsonContainingEmptyStrings_shouldParseCorrectly() {
    // Given: JSON with empty string values
    String jsonString = "{\"empty\":\"\",\"notEmpty\":\"value\"}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should parse with empty string preserved
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> map = (Map<String, Object>) result;
    assertThat(map.get("empty")).isEqualTo("");
    assertThat(map.get("notEmpty")).isEqualTo("value");
  }

  @Test
  void json_withDeeplyNestedStructure_shouldParseCorrectly() {
    // Given: A deeply nested JSON structure
    String jsonString = "{\"level1\":{\"level2\":{\"level3\":{\"level4\":{\"value\":\"deep\"}}}}}";

    // When: json() is called
    Object result = UtilityFunctionsMapper.json(jsonString);

    // Then: Should parse the nested structure correctly
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> level1 = (Map<String, Object>) result;
    @SuppressWarnings("unchecked")
    Map<String, Object> level2 = (Map<String, Object>) level1.get("level1");
    @SuppressWarnings("unchecked")
    Map<String, Object> level3 = (Map<String, Object>) level2.get("level2");
    @SuppressWarnings("unchecked")
    Map<String, Object> level4 = (Map<String, Object>) level3.get("level3");
    @SuppressWarnings("unchecked")
    Map<String, Object> level5 = (Map<String, Object>) level4.get("level4");
    assertThat(level5.get("value")).isEqualTo("deep");
  }
}
