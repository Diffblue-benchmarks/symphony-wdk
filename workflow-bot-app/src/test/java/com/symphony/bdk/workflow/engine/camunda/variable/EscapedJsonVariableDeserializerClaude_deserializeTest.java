package com.symphony.bdk.workflow.engine.camunda.variable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBindings;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EscapedJsonVariableDeserializerClaude_deserializeTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void deserialize_withEscapedJsonList_shouldDeserializeToList() throws IOException {
    // Given: A deserializer for List and an escaped JSON list string
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String escapedJson = "\"[1,2,3,4,5]\"";
    JsonParser parser = objectMapper.getFactory().createParser(escapedJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should deserialize to a list
    assertThat(result).isNotNull();
    assertThat(result).containsExactly(1, 2, 3, 4, 5);
  }

  @Test
  void deserialize_withEscapedJsonMap_shouldDeserializeToMap() throws IOException {
    // Given: A deserializer for Map and an escaped JSON object string
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String escapedJson = "\"{\\\"name\\\":\\\"John\\\",\\\"age\\\":30}\"";
    JsonParser parser = objectMapper.getFactory().createParser(escapedJson);

    // When: deserialize is called
    Map result = deserializer.deserialize(parser, null);

    // Then: Should deserialize to a map
    assertThat(result).isNotNull();
    assertThat(result).containsEntry("name", "John");
    assertThat(result).containsEntry("age", 30);
  }

  @Test
  void deserialize_withActualJsonList_shouldDeserializeToList() throws IOException {
    // Given: A deserializer for List and an actual JSON list (not escaped)
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String actualJson = "[10,20,30]";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should deserialize to a list
    assertThat(result).isNotNull();
    assertThat(result).containsExactly(10, 20, 30);
  }

  @Test
  void deserialize_withActualJsonMap_shouldDeserializeToMap() throws IOException {
    // Given: A deserializer for Map and an actual JSON object (not escaped)
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String actualJson = "{\"city\":\"NYC\",\"population\":8000000}";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    Map result = deserializer.deserialize(parser, null);

    // Then: Should deserialize to a map
    assertThat(result).isNotNull();
    assertThat(result).containsEntry("city", "NYC");
    assertThat(result).containsEntry("population", 8000000);
  }

  @Test
  void deserialize_withEmptyEscapedJsonList_shouldReturnEmptyList() throws IOException {
    // Given: A deserializer and an escaped empty JSON list
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String escapedJson = "\"[]\"";
    JsonParser parser = objectMapper.getFactory().createParser(escapedJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should return an empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void deserialize_withEmptyActualJsonList_shouldReturnEmptyList() throws IOException {
    // Given: A deserializer and an actual empty JSON list
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String actualJson = "[]";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should return an empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void deserialize_withEmptyEscapedJsonMap_shouldReturnEmptyMap() throws IOException {
    // Given: A deserializer and an escaped empty JSON object
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String escapedJson = "\"{}\"";
    JsonParser parser = objectMapper.getFactory().createParser(escapedJson);

    // When: deserialize is called
    Map result = deserializer.deserialize(parser, null);

    // Then: Should return an empty map
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void deserialize_withEmptyActualJsonMap_shouldReturnEmptyMap() throws IOException {
    // Given: A deserializer and an actual empty JSON object
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String actualJson = "{}";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    Map result = deserializer.deserialize(parser, null);

    // Then: Should return an empty map
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void deserialize_withNestedEscapedJsonList_shouldDeserializeCorrectly() throws IOException {
    // Given: A deserializer and a nested escaped JSON list
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String escapedJson = "\"[[1,2],[3,4],[5,6]]\"";
    JsonParser parser = objectMapper.getFactory().createParser(escapedJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should deserialize the nested structure
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get(0)).isInstanceOf(List.class);
  }

  @Test
  void deserialize_withNestedActualJsonList_shouldDeserializeCorrectly() throws IOException {
    // Given: A deserializer and a nested actual JSON list
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String actualJson = "[[1,2],[3,4],[5,6]]";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should deserialize the nested structure
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get(0)).isInstanceOf(List.class);
  }

  @Test
  void deserialize_withComplexEscapedJsonMap_shouldDeserializeCorrectly() throws IOException {
    // Given: A deserializer and a complex escaped JSON object
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String escapedJson = "\"{\\\"user\\\":{\\\"name\\\":\\\"Alice\\\",\\\"id\\\":123},\\\"active\\\":true}\"";
    JsonParser parser = objectMapper.getFactory().createParser(escapedJson);

    // When: deserialize is called
    Map result = deserializer.deserialize(parser, null);

    // Then: Should deserialize the complex structure
    assertThat(result).isNotNull();
    assertThat(result).containsKey("user");
    assertThat(result).containsEntry("active", true);
    assertThat(result.get("user")).isInstanceOf(Map.class);
  }

  @Test
  void deserialize_withComplexActualJsonMap_shouldDeserializeCorrectly() throws IOException {
    // Given: A deserializer and a complex actual JSON object
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String actualJson = "{\"user\":{\"name\":\"Alice\",\"id\":123},\"active\":true}";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    Map result = deserializer.deserialize(parser, null);

    // Then: Should deserialize the complex structure
    assertThat(result).isNotNull();
    assertThat(result).containsKey("user");
    assertThat(result).containsEntry("active", true);
    assertThat(result.get("user")).isInstanceOf(Map.class);
  }

  @Test
  void deserialize_withEscapedJsonContainingSpecialCharacters_shouldDeserializeCorrectly() throws IOException {
    // Given: A deserializer and escaped JSON with special characters
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String escapedJson = "\"[\\\"hello\\\\nworld\\\",\\\"test\\\"]\"";
    JsonParser parser = objectMapper.getFactory().createParser(escapedJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should handle special characters correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
  }

  @Test
  void deserialize_withContextualDeserializerHavingContainedType_shouldUseContainedType() throws IOException {
    // Given: A deserializer with containedType set (via createContextual)
    EscapedJsonVariableDeserializer<List> baseDeserializer = new EscapedJsonVariableDeserializer<>(List.class);
    BeanProperty property = createMockPropertyWithTypeParameters(List.class, String.class);
    EscapedJsonVariableDeserializer<?> contextualDeserializer =
        (EscapedJsonVariableDeserializer<?>) baseDeserializer.createContextual(null, property);

    String actualJson = "[\"apple\",\"banana\",\"cherry\"]";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called on the contextual deserializer
    Object result = contextualDeserializer.deserialize(parser, null);

    // Then: Should deserialize using the contained type
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(List.class);
    @SuppressWarnings("unchecked")
    List<String> list = (List<String>) result;
    assertThat(list).containsExactly("apple", "banana", "cherry");
  }

  @Test
  void deserialize_withMixedDataTypes_shouldPreserveTypes() throws IOException {
    // Given: A deserializer and JSON with mixed types
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String actualJson = "[1,\"text\",true,null,3.14]";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should preserve different data types
    assertThat(result).isNotNull();
    assertThat(result).hasSize(5);
    assertThat(result.get(0)).isInstanceOf(Number.class);
    assertThat(result.get(1)).isInstanceOf(String.class);
    assertThat(result.get(2)).isInstanceOf(Boolean.class);
    assertThat(result.get(3)).isNull();
    assertThat(result.get(4)).isInstanceOf(Number.class);
  }

  @Test
  void deserialize_withListOfMaps_shouldDeserializeCorrectly() throws IOException {
    // Given: A deserializer and a list of maps
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String actualJson = "[{\"id\":1,\"name\":\"Alice\"},{\"id\":2,\"name\":\"Bob\"}]";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should deserialize list of maps
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0)).isInstanceOf(Map.class);
    assertThat(result.get(1)).isInstanceOf(Map.class);
  }

  @Test
  void deserialize_withMapContainingList_shouldDeserializeCorrectly() throws IOException {
    // Given: A deserializer and a map containing a list
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String actualJson = "{\"items\":[1,2,3],\"name\":\"test\"}";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    Map result = deserializer.deserialize(parser, null);

    // Then: Should deserialize map containing list
    assertThat(result).isNotNull();
    assertThat(result).containsKey("items");
    assertThat(result).containsEntry("name", "test");
    assertThat(result.get("items")).isInstanceOf(List.class);
  }

  @Test
  void deserialize_withInvalidEscapedJson_shouldThrowIOException() {
    // Given: A deserializer and invalid escaped JSON
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String invalidEscapedJson = "\"[1,2,\"";

    // When/Then: Should throw IOException for invalid JSON
    assertThatThrownBy(() -> {
      JsonParser parser = objectMapper.getFactory().createParser(invalidEscapedJson);
      deserializer.deserialize(parser, null);
    }).isInstanceOf(Exception.class);
  }

  @Test
  void deserialize_calledMultipleTimes_shouldReturnConsistentResults() throws IOException {
    // Given: A deserializer and the same JSON
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String json = "[1,2,3]";

    // When: deserialize is called multiple times
    JsonParser parser1 = objectMapper.getFactory().createParser(json);
    List result1 = deserializer.deserialize(parser1, null);

    JsonParser parser2 = objectMapper.getFactory().createParser(json);
    List result2 = deserializer.deserialize(parser2, null);

    JsonParser parser3 = objectMapper.getFactory().createParser(json);
    List result3 = deserializer.deserialize(parser3, null);

    // Then: All results should be equal
    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);
  }

  @Test
  void deserialize_withEscapedJsonList_shouldNotThrowException() {
    // Given: A deserializer and an escaped JSON list
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String escapedJson = "\"[1,2,3]\"";

    // When/Then: Should not throw exception
    assertThatCode(() -> {
      JsonParser parser = objectMapper.getFactory().createParser(escapedJson);
      deserializer.deserialize(parser, null);
    }).doesNotThrowAnyException();
  }

  @Test
  void deserialize_withActualJsonMap_shouldNotThrowException() {
    // Given: A deserializer and an actual JSON object
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String actualJson = "{\"key\":\"value\"}";

    // When/Then: Should not throw exception
    assertThatCode(() -> {
      JsonParser parser = objectMapper.getFactory().createParser(actualJson);
      deserializer.deserialize(parser, null);
    }).doesNotThrowAnyException();
  }

  @Test
  void deserialize_withEscapedJsonMapContainingNulls_shouldPreserveNulls() throws IOException {
    // Given: A deserializer and escaped JSON with null values
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String escapedJson = "\"{\\\"name\\\":\\\"test\\\",\\\"value\\\":null}\"";
    JsonParser parser = objectMapper.getFactory().createParser(escapedJson);

    // When: deserialize is called
    Map result = deserializer.deserialize(parser, null);

    // Then: Should preserve null values
    assertThat(result).isNotNull();
    assertThat(result).containsEntry("name", "test");
    assertThat(result).containsKey("value");
    assertThat(result.get("value")).isNull();
  }

  @Test
  void deserialize_withActualJsonListContainingNulls_shouldPreserveNulls() throws IOException {
    // Given: A deserializer and actual JSON with null values
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String actualJson = "[1,null,3,null,5]";
    JsonParser parser = objectMapper.getFactory().createParser(actualJson);

    // When: deserialize is called
    List result = deserializer.deserialize(parser, null);

    // Then: Should preserve null values
    assertThat(result).isNotNull();
    assertThat(result).hasSize(5);
    assertThat(result.get(0)).isEqualTo(1);
    assertThat(result.get(1)).isNull();
    assertThat(result.get(2)).isEqualTo(3);
    assertThat(result.get(3)).isNull();
    assertThat(result.get(4)).isEqualTo(5);
  }

  /**
   * Helper method to create a mock BeanProperty with type parameters.
   */
  private BeanProperty createMockPropertyWithTypeParameters(Class<?> containerClass, Class<?>... parameterClasses) {
    BeanProperty property = mock(BeanProperty.class);
    JavaType javaType = mock(JavaType.class);
    TypeBindings bindings = mock(TypeBindings.class);

    ObjectMapper mapper = new ObjectMapper();
    JavaType[] typeParameters = new JavaType[parameterClasses.length];
    for (int i = 0; i < parameterClasses.length; i++) {
      typeParameters[i] = mapper.getTypeFactory().constructType(parameterClasses[i]);
    }

    when(property.getType()).thenReturn(javaType);
    when(javaType.getBindings()).thenReturn(bindings);
    when(bindings.getTypeParameters()).thenReturn(Arrays.asList(typeParameters));

    return property;
  }
}
