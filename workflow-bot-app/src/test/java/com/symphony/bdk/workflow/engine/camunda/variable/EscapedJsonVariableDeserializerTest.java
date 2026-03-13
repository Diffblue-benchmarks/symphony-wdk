package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBindings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class EscapedJsonVariableDeserializerTest {

  private ObjectMapper mapper;
  private DeserializationContext context;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    context = mock(DeserializationContext.class);
  }

  @Test
  void constructor_shouldCreateInstanceWithContainerType() {
    // Arrange & Act
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);

    // Assert
    assertThat(deserializer).isNotNull();
  }

  @Test
  void createContextual_shouldReturnSameInstanceWhenPropertyIsNull() {
    // Arrange
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);

    // Act
    JsonDeserializer<?> result = deserializer.createContextual(context, null);

    // Assert
    assertThat(result).isSameAs(deserializer);
  }

  @Test
  void createContextual_shouldReturnNewDeserializerWithContainedTypeWhenPropertyIsNotNull() {
    // Arrange
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    BeanProperty property = mock(BeanProperty.class);
    JavaType propertyType = mock(JavaType.class);
    TypeBindings typeBindings = mock(TypeBindings.class);
    JavaType stringType = mapper.getTypeFactory().constructType(String.class);

    when(property.getType()).thenReturn(propertyType);
    when(propertyType.getBindings()).thenReturn(typeBindings);
    when(typeBindings.getTypeParameters()).thenReturn(List.of(stringType));

    // Act
    JsonDeserializer<?> result = deserializer.createContextual(context, property);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(deserializer);
    assertThat(result).isInstanceOf(EscapedJsonVariableDeserializer.class);
  }

  @Test
  void deserialize_shouldDeserializeEscapedJsonString() throws IOException {
    // Arrange
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String escapedJson = "[\"value1\", \"value2\"]";
    String json = "\"" + escapedJson.replace("\"", "\\\"") + "\"";
    JsonParser parser = mapper.getFactory().createParser(json);

    // Act
    List result = deserializer.deserialize(parser, context);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly("value1", "value2");
  }

  @Test
  void deserialize_shouldDeserializeCollectionWithoutContainedType() throws IOException {
    // Arrange
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    String json = "[\"value1\", \"value2\"]";
    JsonParser parser = mapper.getFactory().createParser(json);

    // Act
    List result = deserializer.deserialize(parser, context);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly("value1", "value2");
  }

  @Test
  void deserialize_shouldDeserializeCollectionWithContainedType() throws IOException {
    // Arrange
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    BeanProperty property = mock(BeanProperty.class);
    JavaType propertyType = mock(JavaType.class);
    TypeBindings typeBindings = mock(TypeBindings.class);
    JavaType stringType = mapper.getTypeFactory().constructType(String.class);

    when(property.getType()).thenReturn(propertyType);
    when(propertyType.getBindings()).thenReturn(typeBindings);
    when(typeBindings.getTypeParameters()).thenReturn(List.of(stringType));

    deserializer = (EscapedJsonVariableDeserializer<List>) deserializer.createContextual(context, property);

    String json = "[\"value1\", \"value2\"]";
    JsonParser parser = mapper.getFactory().createParser(json);

    // Act
    List result = deserializer.deserialize(parser, context);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly("value1", "value2");
  }

  @Test
  void deserialize_shouldDeserializeMapFromEscapedJsonString() throws IOException {
    // Arrange
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String escapedJson = "{\\\"key1\\\": \\\"value1\\\", \\\"key2\\\": \\\"value2\\\"}";
    String json = "\"" + escapedJson + "\"";
    JsonParser parser = mapper.getFactory().createParser(json);

    // Act
    Map result = deserializer.deserialize(parser, context);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).containsEntry("key1", "value1");
    assertThat(result).containsEntry("key2", "value2");
  }

  @Test
  void deserialize_shouldDeserializeMapObject() throws IOException {
    // Arrange
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    String json = "{\"key1\": \"value1\", \"key2\": \"value2\"}";
    JsonParser parser = mapper.getFactory().createParser(json);

    // Act
    Map result = deserializer.deserialize(parser, context);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).containsEntry("key1", "value1");
    assertThat(result).containsEntry("key2", "value2");
  }
}
