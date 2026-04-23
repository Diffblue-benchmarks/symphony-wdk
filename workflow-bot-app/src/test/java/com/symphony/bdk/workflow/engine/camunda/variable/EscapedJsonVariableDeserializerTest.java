package com.symphony.bdk.workflow.engine.camunda.variable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EscapedJsonVariableDeserializerTest {

  @Test
  void shouldReturnSelfWhenPropertyIsNull() throws Exception {
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext ctxt = mock(DeserializationContext.class);

    JsonDeserializer<?> result = deserializer.createContextual(ctxt, null);

    assertThat(result).isSameAs(deserializer);
  }

  @Test
  void shouldReturnNewDeserializerWhenPropertyIsNotNull() throws Exception {
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);
    DeserializationContext ctxt = mock(DeserializationContext.class);
    BeanProperty property = mock(BeanProperty.class);
    JavaType javaType = mock(JavaType.class);
    TypeBindings typeBindings = mock(TypeBindings.class);
    JavaType objectType = TypeFactory.defaultInstance().constructType(Object.class);

    when(property.getType()).thenReturn(javaType);
    when(javaType.getBindings()).thenReturn(typeBindings);
    when(typeBindings.getTypeParameters()).thenReturn(Collections.singletonList(objectType));

    JsonDeserializer<?> result = deserializer.createContextual(ctxt, property);

    assertThat(result).isNotSameAs(deserializer);
    assertThat(result).isInstanceOf(EscapedJsonVariableDeserializer.class);
  }
}
