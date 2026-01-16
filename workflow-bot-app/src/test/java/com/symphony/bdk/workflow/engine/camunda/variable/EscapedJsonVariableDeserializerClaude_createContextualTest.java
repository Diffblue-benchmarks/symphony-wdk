package com.symphony.bdk.workflow.engine.camunda.variable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBindings;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EscapedJsonVariableDeserializerClaude_createContextualTest {

  @Test
  void createContextual_withNullProperty_shouldReturnSameInstance() {
    // Given: A deserializer and null property
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);

    // When: createContextual is called with null property
    JsonDeserializer<?> result = deserializer.createContextual(context, null);

    // Then: Should return the same instance
    assertThat(result).isSameAs(deserializer);
  }

  @Test
  void createContextual_withNullProperty_shouldNotThrowException() {
    // Given: A deserializer and null property
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);

    // When/Then: createContextual should not throw exception
    assertThatCode(() -> deserializer.createContextual(context, null))
        .doesNotThrowAnyException();
  }

  @Test
  void createContextual_withNonNullProperty_shouldReturnNewDeserializer() {
    // Given: A deserializer and a non-null property
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);
    BeanProperty property = createMockPropertyWithTypeParameters(List.class, String.class);

    // When: createContextual is called with non-null property
    JsonDeserializer<?> result = deserializer.createContextual(context, property);

    // Then: Should return a new deserializer instance
    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(deserializer);
    assertThat(result).isInstanceOf(EscapedJsonVariableDeserializer.class);
  }

  @Test
  void createContextual_withNonNullProperty_shouldNotThrowException() {
    // Given: A deserializer and a non-null property
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);
    BeanProperty property = createMockPropertyWithTypeParameters(List.class, String.class);

    // When/Then: createContextual should not throw exception
    assertThatCode(() -> deserializer.createContextual(context, property))
        .doesNotThrowAnyException();
  }

  @Test
  void createContextual_calledMultipleTimesWithNullProperty_shouldReturnSameInstance() {
    // Given: A deserializer
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);

    // When: createContextual is called multiple times with null property
    JsonDeserializer<?> result1 = deserializer.createContextual(context, null);
    JsonDeserializer<?> result2 = deserializer.createContextual(context, null);

    // Then: Should return the same instance each time
    assertThat(result1).isSameAs(deserializer);
    assertThat(result2).isSameAs(deserializer);
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void createContextual_calledMultipleTimesWithNonNullProperty_shouldReturnDistinctInstances() {
    // Given: A deserializer
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);
    BeanProperty property = createMockPropertyWithTypeParameters(List.class, String.class);

    // When: createContextual is called multiple times with non-null property
    JsonDeserializer<?> result1 = deserializer.createContextual(context, property);
    JsonDeserializer<?> result2 = deserializer.createContextual(context, property);

    // Then: Should return distinct instances each time
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
    assertThat(result1).isNotSameAs(result2);
    assertThat(result1).isNotSameAs(deserializer);
    assertThat(result2).isNotSameAs(deserializer);
  }

  @Test
  void createContextual_withMapContainerType_shouldCreateNewDeserializer() {
    // Given: A deserializer for Map and a non-null property
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);
    DeserializationContext context = mock(DeserializationContext.class);
    BeanProperty property = createMockPropertyWithTypeParameters(Map.class, String.class, Object.class);

    // When: createContextual is called
    JsonDeserializer<?> result = deserializer.createContextual(context, property);

    // Then: Should return a new deserializer instance
    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(deserializer);
    assertThat(result).isInstanceOf(EscapedJsonVariableDeserializer.class);
  }

  @Test
  void createContextual_withListContainerType_shouldCreateNewDeserializer() {
    // Given: A deserializer for List and a non-null property
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);
    BeanProperty property = createMockPropertyWithTypeParameters(List.class, String.class);

    // When: createContextual is called
    JsonDeserializer<?> result = deserializer.createContextual(context, property);

    // Then: Should return a new deserializer instance
    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(deserializer);
    assertThat(result).isInstanceOf(EscapedJsonVariableDeserializer.class);
  }

  @Test
  void createContextual_withDifferentProperties_shouldReturnDistinctDeserializers() {
    // Given: A deserializer and two different properties
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);
    BeanProperty property1 = createMockPropertyWithTypeParameters(List.class, String.class);
    BeanProperty property2 = createMockPropertyWithTypeParameters(List.class, Integer.class);

    // When: createContextual is called with different properties
    JsonDeserializer<?> result1 = deserializer.createContextual(context, property1);
    JsonDeserializer<?> result2 = deserializer.createContextual(context, property2);

    // Then: Should return distinct deserializer instances
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void createContextual_returnsEscapedJsonVariableDeserializerType() {
    // Given: A deserializer and a non-null property
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext context = mock(DeserializationContext.class);
    BeanProperty property = createMockPropertyWithTypeParameters(List.class, String.class);

    // When: createContextual is called
    JsonDeserializer<?> result = deserializer.createContextual(context, property);

    // Then: Result should be of correct type
    assertThat(result).isInstanceOf(EscapedJsonVariableDeserializer.class);
    assertThat(result).isInstanceOf(JsonDeserializer.class);
  }

  @Test
  void createContextual_withNullContext_shouldNotThrowWhenPropertyIsNull() {
    // Given: A deserializer with null context
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);

    // When/Then: createContextual should not throw exception
    assertThatCode(() -> deserializer.createContextual(null, null))
        .doesNotThrowAnyException();
  }

  @Test
  void createContextual_withNullContext_shouldReturnSelfWhenPropertyIsNull() {
    // Given: A deserializer with null context
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);

    // When: createContextual is called with null context and null property
    JsonDeserializer<?> result = deserializer.createContextual(null, null);

    // Then: Should return the same instance
    assertThat(result).isSameAs(deserializer);
  }

  @Test
  void createContextual_withNullContext_shouldCreateNewDeserializerWhenPropertyIsNotNull() {
    // Given: A deserializer with null context but non-null property
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    BeanProperty property = createMockPropertyWithTypeParameters(List.class, String.class);

    // When: createContextual is called with null context
    JsonDeserializer<?> result = deserializer.createContextual(null, property);

    // Then: Should return a new deserializer instance
    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(deserializer);
    assertThat(result).isInstanceOf(EscapedJsonVariableDeserializer.class);
  }

  /**
   * Helper method to create a mock BeanProperty with type parameters.
   * This is needed because the createContextual method accesses property.getType().getBindings().getTypeParameters().
   */
  private BeanProperty createMockPropertyWithTypeParameters(Class<?> containerClass, Class<?>... parameterClasses) {
    BeanProperty property = mock(BeanProperty.class);
    JavaType javaType = mock(JavaType.class);
    TypeBindings bindings = mock(TypeBindings.class);

    // Create JavaType array for parameters
    ObjectMapper mapper = new ObjectMapper();
    JavaType[] typeParameters = new JavaType[parameterClasses.length];
    for (int i = 0; i < parameterClasses.length; i++) {
      typeParameters[i] = mapper.getTypeFactory().constructType(parameterClasses[i]);
    }

    // Setup the mock chain
    when(property.getType()).thenReturn(javaType);
    when(javaType.getBindings()).thenReturn(bindings);
    when(bindings.getTypeParameters()).thenReturn(java.util.Arrays.asList(typeParameters));

    return property;
  }
}
