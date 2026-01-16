package com.symphony.bdk.workflow.engine.camunda.variable;

import com.fasterxml.jackson.databind.JsonDeserializer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class EscapedJsonVariableDeserializerClaude_constructorTest {

  @Test
  void constructor_withListClass_shouldCreateInstance() {
    // When: Constructor is called with List.class
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);

    // Then: Instance should be created successfully
    assertThat(deserializer).isNotNull();
  }

  @Test
  void constructor_withMapClass_shouldCreateInstance() {
    // When: Constructor is called with Map.class
    EscapedJsonVariableDeserializer<Map> deserializer = new EscapedJsonVariableDeserializer<>(Map.class);

    // Then: Instance should be created successfully
    assertThat(deserializer).isNotNull();
  }

  @Test
  void constructor_withListClass_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new EscapedJsonVariableDeserializer<>(List.class))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_withMapClass_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new EscapedJsonVariableDeserializer<>(Map.class))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_calledMultipleTimes_shouldCreateDistinctInstances() {
    // When: Constructor is called multiple times
    EscapedJsonVariableDeserializer<List> deserializer1 = new EscapedJsonVariableDeserializer<>(List.class);
    EscapedJsonVariableDeserializer<List> deserializer2 = new EscapedJsonVariableDeserializer<>(List.class);

    // Then: Each call should create a distinct instance
    assertThat(deserializer1).isNotNull();
    assertThat(deserializer2).isNotNull();
    assertThat(deserializer1).isNotSameAs(deserializer2);
  }

  @Test
  void constructor_withDifferentClasses_shouldCreateDistinctInstances() {
    // When: Constructor is called with different class types
    EscapedJsonVariableDeserializer<List> listDeserializer = new EscapedJsonVariableDeserializer<>(List.class);
    EscapedJsonVariableDeserializer<Map> mapDeserializer = new EscapedJsonVariableDeserializer<>(Map.class);

    // Then: Each call should create a distinct instance
    assertThat(listDeserializer).isNotNull();
    assertThat(mapDeserializer).isNotNull();
    assertThat(listDeserializer).isNotSameAs(mapDeserializer);
  }

  @Test
  void constructor_shouldExtendJsonDeserializer() {
    // When: Constructor is called
    EscapedJsonVariableDeserializer<List> deserializer = new EscapedJsonVariableDeserializer<>(List.class);

    // Then: Instance should extend JsonDeserializer
    assertThat(deserializer).isInstanceOf(JsonDeserializer.class);
  }

  @Test
  void constructor_withSameClass_shouldCreateIdenticalTypedInstances() {
    // When: Constructor is called with same class type
    EscapedJsonVariableDeserializer<List> deserializer1 = new EscapedJsonVariableDeserializer<>(List.class);
    EscapedJsonVariableDeserializer<List> deserializer2 = new EscapedJsonVariableDeserializer<>(List.class);

    // Then: Both instances should be created successfully but be distinct
    assertThat(deserializer1).isNotNull();
    assertThat(deserializer2).isNotNull();
    assertThat(deserializer1).isNotSameAs(deserializer2);
  }
}
