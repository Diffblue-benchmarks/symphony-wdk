package com.symphony.bdk.workflow.engine.camunda.variable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VariableToJsonConverterTest {

  private VariableToJsonConverter converter;

  @BeforeEach
  void setUp() {
    converter = new VariableToJsonConverter();
  }

  @Test
  void shouldReturnEmptyStringWhenValueIsNull() {
    String result = converter.coerceToString(null);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnSameValueWhenValueIsString() {
    String input = "test string";

    String result = converter.coerceToString(input);

    assertThat(result).isEqualTo(input);
  }

  @Test
  void shouldReturnEnumNameWhenValueIsEnum() {
    TestEnum enumValue = TestEnum.VALUE_ONE;

    String result = converter.coerceToString(enumValue);

    assertThat(result).isEqualTo("VALUE_ONE");
  }

  @Test
  void shouldSerializeMapAsEscapedJson() {
    Map<String, Object> map = new HashMap<>();
    map.put("key", "value");

    String result = converter.coerceToString(map);

    assertThat(result).contains("\\\"key\\\"");
    assertThat(result).contains("\\\"value\\\"");
  }

  @Test
  void shouldSerializeListAsEscapedJson() {
    List<String> list = List.of("item1", "item2");

    String result = converter.coerceToString(list);

    assertThat(result).contains("\\\"item1\\\"");
    assertThat(result).contains("\\\"item2\\\"");
  }

  @Test
  void shouldSerializeComplexObjectAsEscapedJson() {
    TestObject obj = new TestObject("testName", 42);

    String result = converter.coerceToString(obj);

    assertThat(result).contains("\\\"name\\\"");
    assertThat(result).contains("\\\"testName\\\"");
    assertThat(result).contains("\\\"value\\\"");
    assertThat(result).contains("42");
  }

  @Test
  void shouldSerializeIntegerAsEscapedJson() {
    Integer number = 123;

    String result = converter.coerceToString(number);

    assertThat(result).isEqualTo("123");
  }

  private enum TestEnum {
    VALUE_ONE,
    VALUE_TWO
  }

  private static class TestObject {
    private final String name;
    private final int value;

    TestObject(String name, int value) {
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
