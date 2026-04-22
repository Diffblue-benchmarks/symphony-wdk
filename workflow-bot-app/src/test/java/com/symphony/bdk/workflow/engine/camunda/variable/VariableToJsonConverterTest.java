package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class VariableToJsonConverterTest {

  private final VariableToJsonConverter converter = new VariableToJsonConverter();

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When a Map value.
   *   <li>Then return escaped JSON string.
   * </ul>
   */
  @Test
  @DisplayName("Test coerceToString(Object); when map value; then return escaped JSON")
  void testCoerceToString_whenMap_thenReturnEscapedJson() {
    // Arrange
    Map<String, Object> value = Map.of("key", "val");

    // Act
    String result = converter.coerceToString(value);

    // Assert
    assertEquals("{\\\"key\\\":\\\"val\\\"}", result);
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When a List value.
   *   <li>Then return escaped JSON array string.
   * </ul>
   */
  @Test
  @DisplayName("Test coerceToString(Object); when list value; then return escaped JSON array")
  void testCoerceToString_whenList_thenReturnEscapedJsonArray() {
    // Arrange
    List<Integer> value = List.of(1, 2, 3);

    // Act
    String result = converter.coerceToString(value);

    // Assert
    assertEquals("[1,2,3]", result);
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When value cannot be serialized to JSON.
   *   <li>Then throw RuntimeException.
   * </ul>
   */
  @Test
  @DisplayName("Test coerceToString(Object); when JSON serialization fails; then throw RuntimeException")
  void testCoerceToString_whenSerializationFails_thenThrowRuntimeException() {
    // Arrange
    UnserializableObject value = new UnserializableObject();

    // Act and Assert
    assertThrows(RuntimeException.class, () -> converter.coerceToString(value));
  }

  @JsonSerialize(using = ThrowingSerializer.class)
  static class UnserializableObject {}

  static class ThrowingSerializer extends StdSerializer<UnserializableObject> {
    ThrowingSerializer() {
      super(UnserializableObject.class);
    }

    @Override
    public void serialize(UnserializableObject value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
      throw new IOException("Simulated serialization failure");
    }
  }
}
